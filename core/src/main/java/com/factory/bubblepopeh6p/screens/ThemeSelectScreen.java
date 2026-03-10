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

public class ThemeSelectScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont cardFont;
    private final BitmapFont bodyFont;

    public ThemeSelectScreen(MainGame game) {
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

        FreeTypeFontGenerator hGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter hp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        hp.size  = (int) Constants.FONT_HEADER;
        hp.color = Color.WHITE;
        headerFont = hGen.generateFont(hp);
        hGen.dispose();

        FreeTypeFontGenerator cGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter cp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        cp.size  = 28;
        cp.color = Color.WHITE;
        cardFont = cGen.generateFont(cp);
        cGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bGen.generateFont(bp);
        bGen.dispose();

        buildUI();
        game.playMusic("sounds/music/music_menu.ogg");
    }

    private TextButton.TextButtonStyle makeStyle(String up, String down, BitmapFont font) {
        TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
        s.font      = font;
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
        Label header = new Label("SELECT THEME", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.86f
        );
        stage.addActor(header);

        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        // Ocean always unlocked; Space unlocks after Ocean level 5; Candy after Space level 5
        boolean spaceUnlocked  = prefs.getInteger(Constants.PREF_UNLOCKED_SPACE, 0) >= 5
                                  || prefs.getInteger(Constants.PREF_UNLOCKED_OCEAN, 1) >= 5;
        boolean candyUnlocked  = prefs.getInteger(Constants.PREF_UNLOCKED_CANDY, 0) >= 5
                                  || prefs.getInteger(Constants.PREF_UNLOCKED_SPACE, 0) >= 5;

        // Theme card positions (vertical centre of each card)
        float[] cardCentreY = { Constants.WORLD_HEIGHT * 0.68f, Constants.WORLD_HEIGHT * 0.48f, Constants.WORLD_HEIGHT * 0.28f };
        int[]    themes     = { Constants.THEME_OCEAN, Constants.THEME_SPACE, Constants.THEME_CANDY };
        String[] bgPaths    = { "backgrounds/bg_ocean.png", "backgrounds/bg_space.png", "backgrounds/bg_candy.png" };
        String[] labels     = { "OCEAN",                    "SPACE",                    "CANDY" };
        boolean[] unlocked  = { true,                       spaceUnlocked,              candyUnlocked };

        float cardW = Constants.THEME_CARD_WIDTH;
        float cardH = Constants.THEME_CARD_HEIGHT;
        float cardX = (Constants.WORLD_WIDTH - cardW) / 2f;

        Label.LabelStyle cardLabelStyle   = new Label.LabelStyle(cardFont, Color.WHITE);
        Label.LabelStyle lockedLabelStyle = new Label.LabelStyle(cardFont, new Color(0.6f, 0.6f, 0.6f, 1f));

        for (int i = 0; i < 3; i++) {
            final int themeIndex = themes[i];
            float cardY = cardCentreY[i] - cardH / 2f;

            // Background thumbnail
            Image cardBg = new Image(game.manager.get(bgPaths[i], Texture.class));
            cardBg.setSize(cardW, cardH);
            cardBg.setPosition(cardX, cardY);
            if (!unlocked[i]) cardBg.setColor(0.4f, 0.4f, 0.4f, 1f);
            stage.addActor(cardBg);

            // Theme label centered on card
            Label cardLabel = new Label(labels[i], unlocked[i] ? cardLabelStyle : lockedLabelStyle);
            cardLabel.setPosition(
                cardX + (cardW - cardLabel.getPrefWidth()) / 2f,
                cardY + (cardH - cardLabel.getPrefHeight()) / 2f
            );
            stage.addActor(cardLabel);

            // Stars earned
            if (unlocked[i]) {
                int starsTotal = 0;
                for (int lvl = 0; lvl < Constants.LEVELS_PER_THEME; lvl++) {
                    starsTotal += prefs.getInteger(Constants.PREF_STARS_PREFIX + themeIndex + "_" + lvl, 0);
                }
                Label starsLabel = new Label(starsTotal + " \u2605", new Label.LabelStyle(bodyFont, new Color(1f, 0.84f, 0f, 1f)));
                starsLabel.setPosition(
                    cardX + cardW - starsLabel.getPrefWidth() - 12f,
                    cardY + 8f
                );
                stage.addActor(starsLabel);
            } else {
                // Lock icon
                Image lockIcon = new Image(game.manager.get("sprites/icon_locked.png", Texture.class));
                lockIcon.setSize(40, 40);
                lockIcon.setPosition(cardX + cardW / 2f - 20f, cardY + cardH / 2f - 20f);
                stage.addActor(lockIcon);
            }

            // Invisible button overlay to handle tap
            if (unlocked[i]) {
                TextButton cardBtn = new TextButton("",
                    makeStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png", bodyFont));
                cardBtn.setSize(cardW, cardH);
                cardBtn.setPosition(cardX, cardY);
                cardBtn.getStyle().up   = null;
                cardBtn.getStyle().down = null;
                cardBtn.addListener(new ChangeListener() {
                    @Override public void changed(ChangeEvent e, Actor a) {
                        playSound("sounds/sfx/sfx_button_click.ogg");
                        game.setScreen(new LevelSelectScreen(game, themeIndex));
                    }
                });
                stage.addActor(cardBtn);
            }
        }

        // BACK button
        TextButton backBtn = new TextButton("BACK",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png", bodyFont));
        backBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        backBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.06f
        );
        backBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backBtn);
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
        cardFont.dispose();
        bodyFont.dispose();
    }
}
