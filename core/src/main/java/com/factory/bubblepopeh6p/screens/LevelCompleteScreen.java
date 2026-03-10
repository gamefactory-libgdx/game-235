package com.factory.bubblepopeh6p.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
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

public class LevelCompleteScreen implements Screen {

    private final MainGame game;
    private final int themeIndex;
    private final int levelIndex;
    private final int score;
    private final int stars;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont scoreFont;
    private final BitmapFont bodyFont;

    /**
     * @param themeIndex 0=Ocean, 1=Space, 2=Candy
     * @param levelIndex 0-based level index
     * @param score      final score for the completed level
     * @param stars      stars earned: 1, 2, or 3
     */
    public LevelCompleteScreen(MainGame game, int themeIndex, int levelIndex, int score, int stars) {
        this.game       = game;
        this.themeIndex = themeIndex;
        this.levelIndex = levelIndex;
        this.score      = score;
        this.stars      = stars;

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

        FreeTypeFontGenerator hGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter hp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        hp.size  = 38;
        hp.color = headerColor();
        headerFont = hGen.generateFont(hp);

        FreeTypeFontGenerator.FreeTypeFontParameter sp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sp.size  = 28;
        sp.color = Color.WHITE;
        scoreFont = hGen.generateFont(sp);

        hGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bGen.generateFont(bp);
        bGen.dispose();

        buildUI();
        game.playMusic("sounds/music/music_gameplay_alt.ogg");
    }

    private Color headerColor() {
        switch (themeIndex) {
            case Constants.THEME_SPACE: return new Color(1f, 0.42f, 0.62f, 1f); // hot pink
            case Constants.THEME_CANDY: return new Color(1f, 0.41f, 0.71f, 1f); // hot pink
            default:                    return new Color(0.12f, 0.56f, 1f,   1f); // dodger blue
        }
    }

    private String bgPath() {
        switch (themeIndex) {
            case Constants.THEME_SPACE: return "backgrounds/bg_space.png";
            case Constants.THEME_CANDY: return "backgrounds/bg_candy.png";
            default:                    return "backgrounds/bg_ocean.png";
        }
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
        // Dimmed background
        Image bg = new Image(game.manager.get(bgPath(), Texture.class));
        bg.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        bg.setColor(0.7f, 0.7f, 0.7f, 1f);
        stage.addActor(bg);

        // "LEVEL COMPLETE!" header
        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, headerColor());
        Label header = new Label("LEVEL COMPLETE!", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.70f
        );
        stage.addActor(header);

        // Stars (3 icons — gold if earned, grey if not)
        float starSize  = 60f;
        float starTotal = 3 * starSize + 2 * 16f;
        float starStartX = (Constants.WORLD_WIDTH - starTotal) / 2f;
        float starY      = Constants.WORLD_HEIGHT * 0.57f;

        for (int i = 0; i < 3; i++) {
            Image starImg = new Image(game.manager.get("sprites/icon_star.png", Texture.class));
            starImg.setSize(starSize, starSize);
            starImg.setPosition(starStartX + i * (starSize + 16f), starY);
            if (i >= stars) {
                // Unearned — dim grey
                starImg.setColor(0.4f, 0.4f, 0.4f, 1f);
            } else {
                // Earned — gold tint
                starImg.setColor(1f, 0.84f, 0f, 1f);
            }
            stage.addActor(starImg);
        }

        // Score label
        Label.LabelStyle scoreStyle = new Label.LabelStyle(scoreFont, Color.WHITE);
        Label scoreLabel = new Label("Score: " + score, scoreStyle);
        scoreLabel.setPosition(
            (Constants.WORLD_WIDTH - scoreLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.47f
        );
        stage.addActor(scoreLabel);

        // Level number sub-label
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, new Color(0.8f, 0.8f, 0.8f, 1f));
        Label levelInfo = new Label("Level " + (levelIndex + 1) + " cleared", bodyStyle);
        levelInfo.setPosition(
            (Constants.WORLD_WIDTH - levelInfo.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.41f
        );
        stage.addActor(levelInfo);

        float btnCentreX = (Constants.WORLD_WIDTH - Constants.BTN_MAIN_WIDTH) / 2f;

        // NEXT LEVEL button (if not last level)
        boolean hasNextLevel = (levelIndex + 1) < Constants.LEVELS_PER_THEME;
        if (hasNextLevel) {
            TextButton nextBtn = new TextButton("NEXT LEVEL",
                makeStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png"));
            nextBtn.setSize(Constants.BTN_MAIN_WIDTH, Constants.BTN_MAIN_HEIGHT);
            nextBtn.setPosition(btnCentreX, Constants.WORLD_HEIGHT * 0.30f);
            nextBtn.addListener(new ChangeListener() {
                @Override public void changed(ChangeEvent e, Actor a) {
                    playSound("sounds/sfx/sfx_button_click.ogg");
                    game.setScreen(new GameScreen(game, themeIndex, levelIndex + 1));
                }
            });
            stage.addActor(nextBtn);
        } else {
            // Last level — show "ALL DONE"
            Label.LabelStyle goldStyle = new Label.LabelStyle(bodyFont, new Color(1f, 0.84f, 0f, 1f));
            Label doneLabel = new Label("Theme Complete!", goldStyle);
            doneLabel.setPosition(
                (Constants.WORLD_WIDTH - doneLabel.getPrefWidth()) / 2f,
                Constants.WORLD_HEIGHT * 0.32f
            );
            stage.addActor(doneLabel);
        }

        // RETRY button
        float retryY = hasNextLevel ? Constants.WORLD_HEIGHT * 0.19f : Constants.WORLD_HEIGHT * 0.28f;
        TextButton retryBtn = new TextButton("RETRY",
            makeStyle("sprites/button_red.png", "sprites/button_red_pressed.png"));
        retryBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        retryBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            retryY
        );
        retryBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new GameScreen(game, themeIndex, levelIndex));
            }
        });
        stage.addActor(retryBtn);

        // MAIN MENU button
        TextButton menuBtn = new TextButton("MAIN MENU",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        menuBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        menuBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.07f
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
        scoreFont.dispose();
        bodyFont.dispose();
    }
}
