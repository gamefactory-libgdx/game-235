package com.factory.bubblepopeh6p.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.factory.bubblepopeh6p.Constants;
import com.factory.bubblepopeh6p.MainGame;

public class GameScreen implements Screen {

    // ── Constants ─────────────────────────────────────────────────────────────
    private static final int   EMPTY         = -1;
    private static final int   GRID_ROWS     = 10;
    private static final float ROW_HEIGHT    = Constants.BUBBLE_DIAMETER * 0.866f; // hex row spacing
    private static final float DANGER_Y      = Constants.CANNON_Y + Constants.CANNON_HEIGHT + 30f;

    // Bubble color textures indexed 0..4
    private static final String[] BUBBLE_TEXTURES = {
        "sprites/ball_blue.png",
        "sprites/ball_yellow.png",
        "sprites/ball_grey.png",
        "sprites/gem_red.png",
        "sprites/gem_green.png"
    };

    // ── Fields ────────────────────────────────────────────────────────────────
    private final MainGame game;
    private final int themeIndex;
    private final int levelIndex;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage hudStage;
    private final ShapeRenderer shapeRenderer;

    private BitmapFont hudFont;
    private BitmapFont smallFont;

    // Grid: [row][col] = color index or EMPTY
    private final int[][] grid = new int[GRID_ROWS][Constants.GRID_COLS];

    // Shooting bubble
    private float  shootX, shootY;   // current position
    private float  shootVX, shootVY; // velocity (world units/sec)
    private int    shootColor;
    private boolean bubbleInFlight = false;

    // Next bubble
    private int nextBubbleColor;

    // Cannon aim angle (degrees, 0 = right, 90 = up)
    private float cannonAngle = 90f;
    private boolean aiming    = false;

    // State
    private int     score       = 0;
    private int     movesLeft;
    private int     comboCount  = 0;
    private boolean paused      = false;
    private boolean gameEnded   = false;  // prevents double-ending

    // Combo
    private float   comboAlpha  = 0f;     // fade timer for combo label
    private String  comboText   = "";

    // HUD labels (updated each frame)
    private Label scoreLabel;
    private Label movesLabel;
    private Label levelLabel;
    private Label comboLabel;

    // Number of active colors for this level (increases with level)
    private final int numColors;

    // ── Constructor ───────────────────────────────────────────────────────────

    public GameScreen(MainGame game, int themeIndex, int levelIndex) {
        this.game       = game;
        this.themeIndex = themeIndex;
        this.levelIndex = levelIndex;
        this.movesLeft  = Constants.MOVES_PER_LEVEL + Math.min(levelIndex / 5, 5); // extra moves at higher levels

        // Scale active colors with level difficulty
        numColors = Math.min(3 + levelIndex / 10, Constants.BUBBLE_COLORS);

        camera        = new OrthographicCamera();
        viewport      = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        hudStage      = new Stage(viewport, game.batch);
        shapeRenderer = new ShapeRenderer();

        Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (paused || gameEnded || bubbleInFlight) return false;
                updateAimAngle(screenX, screenY);
                aiming = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (paused || gameEnded || !aiming) return false;
                updateAimAngle(screenX, screenY);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (paused || gameEnded || !aiming || bubbleInFlight) return false;
                aiming = false;
                shootBubble();
                return true;
            }
        }));

        buildFonts();
        initGrid();
        pickNextBubble();
        buildHUD();

        game.playMusic("sounds/music/music_gameplay.ogg");
    }

    // ── Font / HUD setup ──────────────────────────────────────────────────────

    private void buildFonts() {
        FreeTypeFontGenerator hGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter hp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        hp.size  = (int) Constants.HUD_FONT_SIZE;
        hp.color = Color.WHITE;
        hudFont  = hGen.generateFont(hp);

        FreeTypeFontGenerator.FreeTypeFontParameter sp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sp.size      = (int) Constants.HUD_LABEL_SIZE;
        sp.color     = Color.WHITE;
        smallFont    = hGen.generateFont(sp);

        hGen.dispose();
    }

    private void buildHUD() {
        // Background (drawn in render(), not via actor, so we don't add it to stage here)

        Label.LabelStyle hudStyle   = new Label.LabelStyle(hudFont, Color.WHITE);
        Label.LabelStyle smallStyle = new Label.LabelStyle(smallFont, Color.WHITE);
        Label.LabelStyle comboStyle = new Label.LabelStyle(smallFont, new Color(1f, 0.84f, 0f, 1f));

        scoreLabel = new Label("Score: 0", hudStyle);
        scoreLabel.setPosition(Constants.HUD_PADDING, Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT + 20f);
        hudStage.addActor(scoreLabel);

        movesLabel = new Label("Moves: " + movesLeft, smallStyle);
        movesLabel.setPosition(
            Constants.WORLD_WIDTH - movesLabel.getPrefWidth() - Constants.HUD_PADDING,
            Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT + 22f
        );
        hudStage.addActor(movesLabel);

        levelLabel = new Label("Level " + (levelIndex + 1), smallStyle);
        levelLabel.setPosition(
            (Constants.WORLD_WIDTH - levelLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT + 22f
        );
        hudStage.addActor(levelLabel);

        comboLabel = new Label("", comboStyle);
        comboLabel.setPosition(Constants.HUD_PADDING, Constants.CANNON_Y + Constants.CANNON_HEIGHT + 60f);
        hudStage.addActor(comboLabel);

        // Pause button (round blue)
        TextButton pauseBtn = new TextButton("II", makePauseStyle());
        pauseBtn.setSize(Constants.BTN_ROUND_SIZE, Constants.BTN_ROUND_SIZE);
        pauseBtn.setPosition(
            Constants.WORLD_WIDTH - Constants.BTN_ROUND_SIZE - Constants.HUD_PADDING,
            Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT + 10f
        );
        pauseBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                paused = true;
                game.setScreen(new PauseScreen(game, GameScreen.this, themeIndex, levelIndex));
            }
        });
        hudStage.addActor(pauseBtn);

        // Help button (?) — opens ComboGuideScreen
        TextButton helpBtn = new TextButton("?", makePauseStyle());
        helpBtn.setSize(Constants.BTN_ROUND_SIZE, Constants.BTN_ROUND_SIZE);
        helpBtn.setPosition(
            Constants.WORLD_WIDTH - (Constants.BTN_ROUND_SIZE + Constants.HUD_PADDING) * 2f,
            Constants.WORLD_HEIGHT - Constants.HUD_HEIGHT + 10f
        );
        helpBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                paused = true;
                game.setScreen(new ComboGuideScreen(game));
            }
        });
        hudStage.addActor(helpBtn);
    }

    private TextButton.TextButtonStyle makePauseStyle() {
        TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
        s.font      = smallFont;
        s.up        = new TextureRegionDrawable(game.manager.get("sprites/button_round_blue.png",         Texture.class));
        s.down      = new TextureRegionDrawable(game.manager.get("sprites/button_round_blue_pressed.png", Texture.class));
        s.fontColor = Color.WHITE;
        return s;
    }

    // ── Grid initialisation ───────────────────────────────────────────────────

    private void initGrid() {
        // Clear grid
        for (int r = 0; r < GRID_ROWS; r++)
            for (int c = 0; c < Constants.GRID_COLS; c++)
                grid[r][c] = EMPTY;

        // Fill rows based on level: more rows for harder levels
        int filledRows = 3 + Math.min(levelIndex / 3, 5);
        long seed = (long) themeIndex * 10000L + levelIndex * 137L;

        for (int r = 0; r < filledRows; r++) {
            int colsInRow = (r % 2 == 0) ? Constants.GRID_COLS : Constants.GRID_COLS - 1;
            for (int c = 0; c < colsInRow; c++) {
                seed = seed * 1664525L + 1013904223L;
                grid[r][c] = (int) ((seed & 0x7FFFFFFF) % numColors);
            }
        }
    }

    private void pickNextBubble() {
        nextBubbleColor = MathUtils.random(numColors - 1);
    }

    // ── Aiming ────────────────────────────────────────────────────────────────

    private void updateAimAngle(int screenX, int screenY) {
        // Unproject screen coords to world
        Vector2 world = new Vector2(screenX, screenY);
        viewport.unproject(world);
        float dx = world.x - Constants.CANNON_X;
        float dy = world.y - Constants.CANNON_Y;
        float angle = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        cannonAngle = MathUtils.clamp(angle, Constants.CANNON_ANGLE_MIN, Constants.CANNON_ANGLE_MAX);
    }

    // ── Shooting ──────────────────────────────────────────────────────────────

    private void shootBubble() {
        shootColor = nextBubbleColor;
        pickNextBubble();
        shootX = Constants.CANNON_X;
        shootY = Constants.CANNON_Y + Constants.CANNON_HEIGHT / 2f;
        float rad = cannonAngle * MathUtils.degreesToRadians;
        shootVX = MathUtils.cos(rad) * Constants.BUBBLE_SHOOT_SPEED;
        shootVY = MathUtils.sin(rad) * Constants.BUBBLE_SHOOT_SPEED;
        bubbleInFlight = true;
        movesLeft--;
        playSound("sounds/sfx/sfx_shoot.ogg");
    }

    // ── Update ────────────────────────────────────────────────────────────────

    private void update(float delta) {
        if (!bubbleInFlight) return;

        shootX += shootVX * delta;
        shootY += shootVY * delta;

        // Bounce off left/right walls
        float leftBound  = Constants.GRID_LEFT_X + Constants.BUBBLE_RADIUS;
        float rightBound = Constants.GRID_LEFT_X + Constants.GRID_COLS * Constants.BUBBLE_DIAMETER - Constants.BUBBLE_RADIUS;
        if (shootX - Constants.BUBBLE_RADIUS < Constants.GRID_LEFT_X) {
            shootX  = Constants.GRID_LEFT_X + Constants.BUBBLE_RADIUS;
            shootVX = -shootVX;
        } else if (shootX + Constants.BUBBLE_RADIUS > Constants.GRID_LEFT_X + Constants.GRID_COLS * Constants.BUBBLE_DIAMETER) {
            shootX  = Constants.GRID_LEFT_X + Constants.GRID_COLS * Constants.BUBBLE_DIAMETER - Constants.BUBBLE_RADIUS;
            shootVX = -shootVX;
        }

        // Check collision with ceiling
        if (shootY + Constants.BUBBLE_RADIUS >= Constants.GRID_TOP_Y) {
            landBubble(0, findNearestCol(shootX, 0));
            return;
        }

        // Check collision with existing bubbles
        for (int r = 0; r < GRID_ROWS; r++) {
            int colsInRow = colsForRow(r);
            for (int c = 0; c < colsInRow; c++) {
                if (grid[r][c] == EMPTY) continue;
                float bx = bubbleWorldX(r, c);
                float by = bubbleWorldY(r);
                float dist = Vector2.dst(shootX, shootY, bx, by);
                if (dist < Constants.BUBBLE_DIAMETER - 2f) {
                    // Place adjacent to collided bubble
                    placeNearBubble(r, c);
                    return;
                }
            }
        }

        // Fell below danger zone without hitting anything — lost shot
        if (shootY < DANGER_Y - 60f) {
            bubbleInFlight = false;
            comboCount = 0;
            checkGameOver();
        }
    }

    private int colsForRow(int row) {
        return (row % 2 == 0) ? Constants.GRID_COLS : Constants.GRID_COLS - 1;
    }

    private float bubbleWorldX(int row, int col) {
        float offset = (row % 2 == 1) ? Constants.BUBBLE_RADIUS : 0f;
        return Constants.GRID_LEFT_X + offset + col * Constants.BUBBLE_DIAMETER + Constants.BUBBLE_RADIUS;
    }

    private float bubbleWorldY(int row) {
        return Constants.GRID_TOP_Y - row * ROW_HEIGHT - Constants.BUBBLE_RADIUS;
    }

    private int findNearestCol(float worldX, int row) {
        float offset = (row % 2 == 1) ? Constants.BUBBLE_RADIUS : 0f;
        int col = Math.round((worldX - Constants.GRID_LEFT_X - offset - Constants.BUBBLE_RADIUS) / Constants.BUBBLE_DIAMETER);
        return MathUtils.clamp(col, 0, colsForRow(row) - 1);
    }

    /** Find best empty cell adjacent to the bubble at (hitRow, hitCol) for placement. */
    private void placeNearBubble(int hitRow, int hitCol) {
        // Try neighbours of hit cell; pick the one closest to the flying bubble
        int bestRow = -1, bestCol = -1;
        float bestDist = Float.MAX_VALUE;

        for (int[] nb : neighbours(hitRow, hitCol)) {
            int nr = nb[0], nc = nb[1];
            if (nr < 0 || nr >= GRID_ROWS || nc < 0 || nc >= colsForRow(nr)) continue;
            if (grid[nr][nc] != EMPTY) continue;
            float nx = bubbleWorldX(nr, nc);
            float ny = bubbleWorldY(nr);
            float d  = Vector2.dst(shootX, shootY, nx, ny);
            if (d < bestDist) { bestDist = d; bestRow = nr; bestCol = nc; }
        }

        if (bestRow >= 0) {
            landBubble(bestRow, bestCol);
        } else {
            // No adjacent empty — try row above hit
            int aboveRow = hitRow - 1;
            if (aboveRow >= 0) {
                int col = findNearestCol(shootX, aboveRow);
                if (grid[aboveRow][col] == EMPTY) {
                    landBubble(aboveRow, col);
                    return;
                }
            }
            // Give up: place in first empty row
            for (int r = GRID_ROWS - 1; r >= 0; r--) {
                int col = findNearestCol(shootX, r);
                if (col < colsForRow(r) && grid[r][col] == EMPTY) {
                    landBubble(r, col);
                    return;
                }
            }
            bubbleInFlight = false;
            comboCount = 0;
        }
    }

    private void landBubble(int row, int col) {
        bubbleInFlight = false;
        if (row < 0 || row >= GRID_ROWS || col < 0 || col >= colsForRow(row)) {
            comboCount = 0;
            checkGameOver();
            return;
        }
        grid[row][col] = shootColor;
        checkMatches(row, col);
    }

    // ── Match detection ───────────────────────────────────────────────────────

    private void checkMatches(int landRow, int landCol) {
        int color = grid[landRow][landCol];
        Array<int[]> matched = new Array<>();
        boolean[][] visited  = new boolean[GRID_ROWS][Constants.GRID_COLS];
        bfsColor(landRow, landCol, color, visited, matched);

        if (matched.size >= Constants.MIN_MATCH_COUNT) {
            comboCount++;
            float multiplier = comboMultiplier();
            int pts = Math.round(matched.size * Constants.SCORE_PER_BUBBLE * multiplier);
            score += pts;

            // Remove matched bubbles
            for (int[] cell : matched) grid[cell[0]][cell[1]] = EMPTY;

            // Remove orphans (bubbles not connected to ceiling)
            int orphans = removeOrphans();
            score += orphans * Math.round(Constants.SCORE_PER_BUBBLE * 0.5f);

            // Show combo text
            if (comboCount >= Constants.COMBO_THRESHOLD_1) {
                comboText  = comboCount + "x Combo \u2022 " + multiplier + "x";
                comboAlpha = 2.5f; // seconds visible
            }

            playSound("sounds/sfx/sfx_level_complete.ogg");

            // Check win
            if (isGridEmpty()) {
                endLevel(true);
                return;
            }
        } else {
            comboCount = 0;
        }

        checkGameOver();
    }

    private float comboMultiplier() {
        if (comboCount >= Constants.COMBO_THRESHOLD_3) return Constants.COMBO_MULT_3;
        if (comboCount >= Constants.COMBO_THRESHOLD_2) return Constants.COMBO_MULT_2;
        if (comboCount >= Constants.COMBO_THRESHOLD_1) return Constants.COMBO_MULT_1;
        return 1.0f;
    }

    private void bfsColor(int startRow, int startCol, int color,
                          boolean[][] visited, Array<int[]> result) {
        if (startRow < 0 || startRow >= GRID_ROWS) return;
        if (startCol < 0 || startCol >= colsForRow(startRow)) return;
        if (visited[startRow][startCol]) return;
        if (grid[startRow][startCol] != color) return;
        visited[startRow][startCol] = true;
        result.add(new int[]{startRow, startCol});
        for (int[] nb : neighbours(startRow, startCol)) {
            bfsColor(nb[0], nb[1], color, visited, result);
        }
    }

    /** Remove bubbles not reachable from the top row. Returns count removed. */
    private int removeOrphans() {
        boolean[][] reachable = new boolean[GRID_ROWS][Constants.GRID_COLS];
        // Flood fill from every filled cell in row 0
        for (int c = 0; c < colsForRow(0); c++) {
            if (grid[0][c] != EMPTY) markReachable(0, c, reachable);
        }
        int count = 0;
        for (int r = 0; r < GRID_ROWS; r++)
            for (int c = 0; c < colsForRow(r); c++)
                if (grid[r][c] != EMPTY && !reachable[r][c]) {
                    grid[r][c] = EMPTY;
                    count++;
                }
        return count;
    }

    private void markReachable(int row, int col, boolean[][] reachable) {
        if (row < 0 || row >= GRID_ROWS) return;
        if (col < 0 || col >= colsForRow(row)) return;
        if (reachable[row][col]) return;
        if (grid[row][col] == EMPTY) return;
        reachable[row][col] = true;
        for (int[] nb : neighbours(row, col)) markReachable(nb[0], nb[1], reachable);
    }

    /** Returns the 6 hex neighbours of (row, col). */
    private int[][] neighbours(int row, int col) {
        boolean oddRow = (row % 2 == 1);
        return new int[][] {
            {row - 1, oddRow ? col     : col - 1},  // upper-left
            {row - 1, oddRow ? col + 1 : col    },  // upper-right
            {row,     col - 1},                     // left
            {row,     col + 1},                     // right
            {row + 1, oddRow ? col     : col - 1},  // lower-left
            {row + 1, oddRow ? col + 1 : col    }   // lower-right
        };
    }

    private boolean isGridEmpty() {
        for (int r = 0; r < GRID_ROWS; r++)
            for (int c = 0; c < colsForRow(r); c++)
                if (grid[r][c] != EMPTY) return false;
        return true;
    }

    // ── Game-over / level-complete checks ─────────────────────────────────────

    private void checkGameOver() {
        if (gameEnded) return;
        // Grid reached danger line
        for (int c = 0; c < colsForRow(GRID_ROWS - 1); c++) {
            if (grid[GRID_ROWS - 1][c] != EMPTY) {
                if (bubbleWorldY(GRID_ROWS - 1) - Constants.BUBBLE_RADIUS < DANGER_Y) {
                    endGame();
                    return;
                }
            }
        }
        // Out of moves
        if (movesLeft <= 0 && !bubbleInFlight) {
            endGame();
        }
    }

    private void endGame() {
        if (gameEnded) return;
        gameEnded = true;
        playSound("sounds/sfx/sfx_game_over.ogg");
        game.setScreen(new GameOverScreen(game, score, themeIndex));
    }

    private void endLevel(boolean success) {
        if (gameEnded) return;
        gameEnded = true;
        playSound("sounds/sfx/sfx_level_complete.ogg");

        // Compute stars
        int maxScore = Constants.LEVELS_PER_THEME * Constants.SCORE_PER_BUBBLE * 10;
        float ratio  = (float) score / Math.max(maxScore, 1);
        int stars;
        if (ratio >= Constants.STARS_3_THRESHOLD)      stars = 3;
        else if (ratio >= Constants.STARS_2_THRESHOLD) stars = 2;
        else                                            stars = 1;

        // Persist stars and unlock next level
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        String starKey = Constants.PREF_STARS_PREFIX + themeIndex + "_" + levelIndex;
        int prevStars  = prefs.getInteger(starKey, 0);
        if (stars > prevStars) prefs.putInteger(starKey, stars);

        // Unlock next level in this theme
        String unlockedKey = (themeIndex == Constants.THEME_OCEAN) ? Constants.PREF_UNLOCKED_OCEAN
                           : (themeIndex == Constants.THEME_SPACE)  ? Constants.PREF_UNLOCKED_SPACE
                           :                                           Constants.PREF_UNLOCKED_CANDY;
        int prevUnlocked = prefs.getInteger(unlockedKey, 0);
        if (levelIndex + 1 > prevUnlocked) prefs.putInteger(unlockedKey, levelIndex + 1);
        prefs.flush();

        LeaderboardScreen.addScore(score);
        game.setScreen(new LevelCompleteScreen(game, themeIndex, levelIndex, score, stars));
    }

    // ── Render ────────────────────────────────────────────────────────────────

    @Override
    public void render(float delta) {
        if (!paused && !gameEnded) update(delta);
        if (comboAlpha > 0f) {
            comboAlpha -= delta;
            comboLabel.setText(comboAlpha > 0f ? comboText : "");
        }

        // Update HUD labels
        scoreLabel.setText("Score: " + score);
        movesLabel.setText("Moves: " + movesLeft);
        movesLabel.setX(Constants.WORLD_WIDTH - movesLabel.getPrefWidth() - Constants.HUD_PADDING - Constants.BTN_ROUND_SIZE - 8f);

        ScreenUtils.clear(0, 0, 0, 1);

        // Draw background via batch
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        Texture bgTex = game.manager.get(bgPath(), Texture.class);
        game.batch.draw(bgTex, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        // Draw grid bubbles
        for (int r = 0; r < GRID_ROWS; r++) {
            for (int c = 0; c < colsForRow(r); c++) {
                if (grid[r][c] == EMPTY) continue;
                Texture tex = game.manager.get(BUBBLE_TEXTURES[grid[r][c]], Texture.class);
                float bx = bubbleWorldX(r, c) - Constants.BUBBLE_RADIUS;
                float by = bubbleWorldY(r)     - Constants.BUBBLE_RADIUS;
                game.batch.draw(tex, bx, by, Constants.BUBBLE_DIAMETER, Constants.BUBBLE_DIAMETER);
            }
        }

        // Draw flying bubble
        if (bubbleInFlight) {
            Texture tex = game.manager.get(BUBBLE_TEXTURES[shootColor], Texture.class);
            game.batch.draw(tex,
                shootX - Constants.BUBBLE_RADIUS,
                shootY - Constants.BUBBLE_RADIUS,
                Constants.BUBBLE_DIAMETER, Constants.BUBBLE_DIAMETER);
        }

        // Draw next-bubble preview
        Texture nextTex = game.manager.get(BUBBLE_TEXTURES[nextBubbleColor], Texture.class);
        game.batch.draw(nextTex,
            Constants.NEXT_BUBBLE_X - Constants.BUBBLE_RADIUS,
            Constants.NEXT_BUBBLE_Y - Constants.BUBBLE_RADIUS,
            Constants.BUBBLE_DIAMETER, Constants.BUBBLE_DIAMETER);

        // Draw cannon (tinted ball_grey as placeholder)
        Texture cannonTex = game.manager.get("sprites/ball_grey.png", Texture.class);
        game.batch.draw(cannonTex,
            Constants.CANNON_X - Constants.CANNON_WIDTH / 2f,
            Constants.CANNON_Y,
            Constants.CANNON_WIDTH, Constants.CANNON_HEIGHT);

        game.batch.end();

        // Draw aim line via ShapeRenderer
        if (aiming && !bubbleInFlight) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1f, 1f, 1f, 0.4f);
            float rad = cannonAngle * MathUtils.degreesToRadians;
            float x1 = Constants.CANNON_X;
            float y1 = Constants.CANNON_Y + Constants.CANNON_HEIGHT;
            float x2 = x1 + MathUtils.cos(rad) * 200f;
            float y2 = y1 + MathUtils.sin(rad) * 200f;
            shapeRenderer.line(x1, y1, x2, y2);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        // HUD overlay
        hudStage.act(paused ? 0f : delta);
        hudStage.draw();
    }

    private String bgPath() {
        switch (themeIndex) {
            case Constants.THEME_SPACE: return "backgrounds/bg_space.png";
            case Constants.THEME_CANDY: return "backgrounds/bg_candy.png";
            default:                    return "backgrounds/bg_ocean.png";
        }
    }

    private void playSound(String path) {
        if (game.sfxEnabled) game.manager.get(path, Sound.class).play(1.0f);
    }

    // ── Screen lifecycle ──────────────────────────────────────────────────────

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void show()   {}
    @Override public void hide()   {}
    @Override public void pause()  { paused = true; }
    @Override public void resume() { paused = false; }

    @Override
    public void dispose() {
        hudStage.dispose();
        shapeRenderer.dispose();
        hudFont.dispose();
        smallFont.dispose();
    }

    // ── Pause resume support (called by PauseScreen) ──────────────────────────

    public void resumeGame() {
        paused = false;
        Gdx.input.setInputProcessor(new InputMultiplexer(hudStage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (paused || gameEnded || bubbleInFlight) return false;
                updateAimAngle(screenX, screenY);
                aiming = true;
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (paused || gameEnded || !aiming) return false;
                updateAimAngle(screenX, screenY);
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (paused || gameEnded || !aiming || bubbleInFlight) return false;
                aiming = false;
                shootBubble();
                return true;
            }
        }));
    }

    public int getThemeIndex()  { return themeIndex; }
    public int getLevelIndex()  { return levelIndex; }
}
