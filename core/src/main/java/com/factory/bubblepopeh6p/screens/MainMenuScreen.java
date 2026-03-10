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

public class MainMenuScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont titleFont;
    private final BitmapFont bodyFont;

    public MainMenuScreen(MainGame game) {
        this.game = game;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                // Main menu — consume BACK without re-navigating (already home)
                return keycode == Input.Keys.BACK;
            }
        }));

        // Fonts
        FreeTypeFontGenerator titleGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter tp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        tp.size  = (int) Constants.FONT_TITLE;
        tp.color = Color.WHITE;
        titleFont = titleGen.generateFont(tp);
        titleGen.dispose();

        FreeTypeFontGenerator bodyGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bodyGen.generateFont(bp);
        bodyGen.dispose();

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

        // Title
        Label.LabelStyle titleStyle = new Label.LabelStyle(titleFont, Color.WHITE);
        Label titleLabel = new Label("BUBBLE POP", titleStyle);
        titleLabel.setPosition(
            (Constants.WORLD_WIDTH - titleLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.79f
        );
        stage.addActor(titleLabel);

        // Tagline
        Label.LabelStyle goldStyle = new Label.LabelStyle(bodyFont, new Color(1f, 0.84f, 0f, 1f));
        Label tagline = new Label("Match \u2022 Pop \u2022 Conquer", goldStyle);
        tagline.setPosition(
            (Constants.WORLD_WIDTH - tagline.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.73f
        );
        stage.addActor(tagline);

        // PLAY
        TextButton playBtn = new TextButton("PLAY",
            makeStyle("sprites/button_yellow.png", "sprites/button_yellow_pressed.png"));
        playBtn.setSize(Constants.BTN_MAIN_WIDTH, Constants.BTN_MAIN_HEIGHT);
        playBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_MAIN_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.52f
        );
        playBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new ThemeSelectScreen(game));
            }
        });
        stage.addActor(playBtn);

        // SETTINGS
        TextButton settingsBtn = new TextButton("SETTINGS",
            makeStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png"));
        settingsBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        settingsBtn.setPosition(
            Constants.WORLD_WIDTH * 0.05f,
            Constants.WORLD_HEIGHT * 0.17f
        );
        settingsBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new SettingsScreen(game));
            }
        });
        stage.addActor(settingsBtn);

        // LEADERBOARD
        TextButton lbBtn = new TextButton("LEADERS",
            makeStyle("sprites/button_green.png", "sprites/button_green_pressed.png"));
        lbBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        lbBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.17f
        );
        lbBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new LeaderboardScreen(game));
            }
        });
        stage.addActor(lbBtn);
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
        titleFont.dispose();
        bodyFont.dispose();
    }
}
