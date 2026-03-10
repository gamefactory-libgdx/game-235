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

/**
 * Pause overlay. Holds a reference to the active GameScreen so it can be
 * resumed without losing any game state.
 */
public class PauseScreen implements Screen {

    private final MainGame game;
    private final GameScreen gameScreen;   // kept alive — resume returns to it
    private final int themeIndex;
    private final int levelIndex;

    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont bodyFont;

    public PauseScreen(MainGame game, GameScreen gameScreen, int themeIndex, int levelIndex) {
        this.game        = game;
        this.gameScreen  = gameScreen;
        this.themeIndex  = themeIndex;
        this.levelIndex  = levelIndex;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    doResume();
                    return true;
                }
                return false;
            }
        }));

        FreeTypeFontGenerator hGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter hp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        hp.size  = (int) Constants.FONT_HEADER;
        hp.color = Color.WHITE;
        headerFont = hGen.generateFont(hp);
        hGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bGen.generateFont(bp);
        bGen.dispose();

        buildUI();
        // Pause the current music rather than stopping it
        if (game.currentMusic != null && game.currentMusic.isPlaying()) {
            game.currentMusic.pause();
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
        // Dimmed game background (re-draw the theme background at reduced alpha)
        String bgPath;
        switch (themeIndex) {
            case Constants.THEME_SPACE: bgPath = "backgrounds/bg_space.png"; break;
            case Constants.THEME_CANDY: bgPath = "backgrounds/bg_candy.png"; break;
            default:                    bgPath = "backgrounds/bg_ocean.png";
        }
        Image bg = new Image(game.manager.get(bgPath, Texture.class));
        bg.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        bg.setColor(0.3f, 0.3f, 0.3f, 1f);
        stage.addActor(bg);

        // "PAUSED" header
        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, Color.WHITE);
        Label header = new Label("PAUSED", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.70f
        );
        stage.addActor(header);

        float btnCentreX = (Constants.WORLD_WIDTH - Constants.BTN_MAIN_WIDTH) / 2f;

        // RESUME button
        TextButton resumeBtn = new TextButton("RESUME",
            makeStyle("sprites/button_green.png", "sprites/button_green_pressed.png"));
        resumeBtn.setSize(Constants.BTN_MAIN_WIDTH, Constants.BTN_MAIN_HEIGHT);
        resumeBtn.setPosition(btnCentreX, Constants.WORLD_HEIGHT * 0.54f);
        resumeBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                doResume();
            }
        });
        stage.addActor(resumeBtn);

        // RESTART button
        TextButton restartBtn = new TextButton("RESTART",
            makeStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png"));
        restartBtn.setSize(Constants.BTN_MAIN_WIDTH, Constants.BTN_MAIN_HEIGHT);
        restartBtn.setPosition(btnCentreX, Constants.WORLD_HEIGHT * 0.42f);
        restartBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new GameScreen(game, themeIndex, levelIndex));
            }
        });
        stage.addActor(restartBtn);

        // MAIN MENU button
        TextButton menuBtn = new TextButton("MAIN MENU",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        menuBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        menuBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.30f
        );
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(menuBtn);
    }

    private void doResume() {
        // Resume music if it was playing
        if (game.musicEnabled && game.currentMusic != null) {
            game.currentMusic.play();
        }
        game.setScreen(gameScreen);
        gameScreen.resumeGame();
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
        bodyFont.dispose();
    }
}
