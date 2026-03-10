package com.factory.bubblepopeh6p.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.factory.bubblepopeh6p.Constants;
import com.factory.bubblepopeh6p.MainGame;

public class LeaderboardScreen implements Screen {

    private static final int    MAX_ENTRIES   = 10;
    private static final String LB_KEY_PREFIX = "lb_score_";

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont rowFont;
    private final BitmapFont bodyFont;

    // ── Static helper ────────────────────────────────────────────────────────

    /** Insert score into the global top-10 saved in SharedPreferences. */
    public static void addScore(int score) {
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int[] scores = loadScores(prefs);

        // Insertion sort — find position for new score
        int insertAt = MAX_ENTRIES; // default: doesn't make top 10
        for (int i = 0; i < MAX_ENTRIES; i++) {
            if (score > scores[i]) {
                insertAt = i;
                break;
            }
        }

        if (insertAt < MAX_ENTRIES) {
            // Shift entries down to make room
            for (int i = MAX_ENTRIES - 1; i > insertAt; i--) {
                prefs.putInteger(LB_KEY_PREFIX + i, scores[i - 1]);
            }
            prefs.putInteger(LB_KEY_PREFIX + insertAt, score);
            prefs.flush();
        }
    }

    private static int[] loadScores(Preferences prefs) {
        int[] scores = new int[MAX_ENTRIES];
        for (int i = 0; i < MAX_ENTRIES; i++) {
            scores[i] = prefs.getInteger(LB_KEY_PREFIX + i, 0);
        }
        return scores;
    }

    // ── Constructor ──────────────────────────────────────────────────────────

    public LeaderboardScreen(MainGame game) {
        this.game = game;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new MainMenuScreen(game));
                    return true;
                }
                return false;
            }
        }));

        // Fonts
        FreeTypeFontGenerator hGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter hp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        hp.size  = (int) Constants.FONT_HEADER;
        hp.color = Color.WHITE;
        headerFont = hGen.generateFont(hp);
        hGen.dispose();

        FreeTypeFontGenerator rGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter rp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        rp.size  = 20;
        rp.color = Color.WHITE;
        rowFont = rGen.generateFont(rp);
        rGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bGen.generateFont(bp);
        bGen.dispose();

        buildUI();
        game.playMusic("sounds/music/music_menu.ogg");
    }

    private TextButton.TextButtonStyle makeStyle(String up, String down) {
        TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
        s.font      = bodyFont;
        s.up        = new TextureRegionDrawable(game.manager.get(up,   Texture.class));
        s.down      = new TextureRegionDrawable(game.manager.get(down, Texture.class));
        s.fontColor = Color.WHITE;
        return s;
    }

    private void buildUI() {
        // Background
        Image bg = new Image(game.manager.get("backgrounds/bg_main.png", Texture.class));
        bg.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage.addActor(bg);

        // Header
        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, Color.WHITE);
        Label header = new Label("LEADERBOARD", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.86f
        );
        stage.addActor(header);

        // Trophy icon
        Image trophy = new Image(game.manager.get("sprites/icon_trophy.png", Texture.class));
        trophy.setSize(48, 48);
        trophy.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f - 60f,
            Constants.WORLD_HEIGHT * 0.862f
        );
        stage.addActor(trophy);

        // Score rows
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        int[] scores = loadScores(prefs);

        Label.LabelStyle rowStyle = new Label.LabelStyle(rowFont, Color.WHITE);
        Label.LabelStyle dimStyle = new Label.LabelStyle(rowFont, new Color(0.6f, 0.6f, 0.6f, 1f));
        Label.LabelStyle goldStyle = new Label.LabelStyle(rowFont, new Color(1f, 0.84f, 0f, 1f));

        float startY = Constants.WORLD_HEIGHT * 0.76f;
        float rowH   = 54f;

        for (int i = 0; i < MAX_ENTRIES; i++) {
            float rowY = startY - i * rowH;
            boolean hasScore = scores[i] > 0;

            // Rank
            String rank = (i + 1) + ".";
            Label.LabelStyle rankStyle = (i < 3 && hasScore) ? goldStyle : (hasScore ? rowStyle : dimStyle);
            Label rankLabel = new Label(rank, rankStyle);
            rankLabel.setPosition(Constants.WORLD_WIDTH * 0.10f, rowY);
            stage.addActor(rankLabel);

            // Medal for top 3
            if (i < 3 && hasScore) {
                Image medal = new Image(game.manager.get("sprites/icon_medal.png", Texture.class));
                medal.setSize(28, 28);
                medal.setPosition(Constants.WORLD_WIDTH * 0.18f, rowY + 2f);
                stage.addActor(medal);
            }

            // Score value
            String scoreText = hasScore ? String.valueOf(scores[i]) : "---";
            Label scoreLabel = new Label(scoreText, hasScore ? rowStyle : dimStyle);
            scoreLabel.setPosition(
                Constants.WORLD_WIDTH * 0.88f - scoreLabel.getPrefWidth(),
                rowY
            );
            stage.addActor(scoreLabel);
        }

        // MAIN MENU button
        TextButton menuBtn = new TextButton("MAIN MENU",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        menuBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        menuBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.06f
        );
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(menuBtn);
    }

    private void playSound(String path) {
        if (game.sfxEnabled) game.manager.get(path, Sound.class).play(1.0f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { viewport.update(w, h, true); }
    @Override public void show()   {}
    @Override public void hide()   {}
    @Override public void pause()  {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        stage.dispose();
        headerFont.dispose();
        rowFont.dispose();
        bodyFont.dispose();
    }
}
