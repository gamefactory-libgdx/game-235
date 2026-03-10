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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.factory.bubblepopeh6p.Constants;
import com.factory.bubblepopeh6p.MainGame;

public class SettingsScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont bodyFont;

    public SettingsScreen(MainGame game) {
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

        // Load saved settings
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        game.musicEnabled = prefs.getBoolean(Constants.PREF_MUSIC, true);
        game.sfxEnabled   = prefs.getBoolean(Constants.PREF_SFX,   true);

        // Fonts
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
        Label header = new Label("SETTINGS", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.86f
        );
        stage.addActor(header);

        final Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        Label.LabelStyle labelStyle = new Label.LabelStyle(bodyFont, Color.WHITE);

        // ── Music row ───────────────────────────────────────────────────
        Label musicLabel = new Label("Music", labelStyle);
        musicLabel.setPosition(Constants.WORLD_WIDTH * 0.12f, Constants.WORLD_HEIGHT * 0.64f);
        stage.addActor(musicLabel);

        final ImageButton musicBtn = new ImageButton(
            new TextureRegionDrawable(game.manager.get("sprites/icon_music_on.png",  Texture.class)),
            new TextureRegionDrawable(game.manager.get("sprites/icon_music_off.png", Texture.class))
        );
        musicBtn.setSize(Constants.BTN_ROUND_SIZE, Constants.BTN_ROUND_SIZE);
        musicBtn.setPosition(
            Constants.WORLD_WIDTH * 0.75f,
            Constants.WORLD_HEIGHT * 0.625f
        );
        musicBtn.setChecked(!game.musicEnabled);
        musicBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.musicEnabled = !musicBtn.isChecked();
                prefs.putBoolean(Constants.PREF_MUSIC, game.musicEnabled);
                prefs.flush();
                if (game.currentMusic != null) {
                    if (game.musicEnabled) game.currentMusic.play();
                    else                   game.currentMusic.pause();
                }
                playSound("sounds/sfx/sfx_toggle.ogg");
            }
        });
        stage.addActor(musicBtn);

        // ── SFX row ──────────────────────────────────────────────────────
        Label sfxLabel = new Label("Sound Effects", labelStyle);
        sfxLabel.setPosition(Constants.WORLD_WIDTH * 0.12f, Constants.WORLD_HEIGHT * 0.54f);
        stage.addActor(sfxLabel);

        final ImageButton sfxBtn = new ImageButton(
            new TextureRegionDrawable(game.manager.get("sprites/icon_sfx_on.png",  Texture.class)),
            new TextureRegionDrawable(game.manager.get("sprites/icon_sfx_off.png", Texture.class))
        );
        sfxBtn.setSize(Constants.BTN_ROUND_SIZE, Constants.BTN_ROUND_SIZE);
        sfxBtn.setPosition(
            Constants.WORLD_WIDTH * 0.75f,
            Constants.WORLD_HEIGHT * 0.525f
        );
        sfxBtn.setChecked(!game.sfxEnabled);
        sfxBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                game.sfxEnabled = !sfxBtn.isChecked();
                prefs.putBoolean(Constants.PREF_SFX, game.sfxEnabled);
                prefs.flush();
                // Play toggle sound only if SFX was just enabled
                if (game.sfxEnabled) playSound("sounds/sfx/sfx_toggle.ogg");
            }
        });
        stage.addActor(sfxBtn);

        // ── Main Menu button ─────────────────────────────────────────────
        TextButton menuBtn = new TextButton("MAIN MENU",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        menuBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        menuBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.10f
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
        bodyFont.dispose();
    }
}
