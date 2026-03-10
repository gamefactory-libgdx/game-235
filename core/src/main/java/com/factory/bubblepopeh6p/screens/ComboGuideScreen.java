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

public class ComboGuideScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont sectionFont;
    private final BitmapFont bodyFont;
    private final BitmapFont smallFont;

    public ComboGuideScreen(MainGame game) {
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

        FreeTypeFontGenerator.FreeTypeFontParameter sec = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sec.size  = 20;
        sec.color = new Color(0.12f, 0.56f, 1f, 1f); // blue
        sectionFont = hGen.generateFont(sec);

        hGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = new Color(0.2f, 0.2f, 0.2f, 1f);
        bodyFont = bGen.generateFont(bp);

        FreeTypeFontGenerator.FreeTypeFontParameter sp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sp.size  = (int) Constants.FONT_SMALL;
        sp.color = new Color(0.3f, 0.3f, 0.3f, 1f);
        smallFont = bGen.generateFont(sp);

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
        Label header = new Label("COMBO GUIDE", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.87f
        );
        stage.addActor(header);

        // Content panel background (semi-transparent white)
        // Simulated with a tinted image at reduced alpha
        Image panel = new Image(game.manager.get("backgrounds/bg_main.png", Texture.class));
        panel.setSize(Constants.WORLD_WIDTH * 0.88f, Constants.WORLD_HEIGHT * 0.65f);
        panel.setPosition(
            (Constants.WORLD_WIDTH - panel.getWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.16f
        );
        panel.setColor(1f, 1f, 1f, 0.15f);
        stage.addActor(panel);

        float contentX  = Constants.WORLD_WIDTH * 0.08f;
        float currentY  = Constants.WORLD_HEIGHT * 0.79f;
        float lineGap   = 22f;
        float sectionGap = 14f;

        Label.LabelStyle secStyle  = new Label.LabelStyle(sectionFont, new Color(0.12f, 0.56f, 1f, 1f));
        Label.LabelStyle textStyle = new Label.LabelStyle(bodyFont,    new Color(0.95f, 0.95f, 0.95f, 1f));
        Label.LabelStyle smStyle   = new Label.LabelStyle(smallFont,   new Color(0.85f, 0.85f, 0.85f, 1f));
        Label.LabelStyle goldStyle = new Label.LabelStyle(bodyFont,    new Color(1f, 0.84f, 0f, 1f));

        // Section 1 — What is a Combo?
        currentY = addLabel("What is a Combo?", secStyle, contentX, currentY);
        currentY -= lineGap;
        currentY = addLabel("Match 3+ bubbles of the same color to earn a combo.", textStyle, contentX, currentY);
        currentY -= lineGap;
        currentY = addLabel("Chain multiple matches in a row to build your multiplier!", textStyle, contentX, currentY);
        currentY -= lineGap + sectionGap;

        // Section 2 — Multiplier tiers
        currentY = addLabel("Combo Multiplier Tiers", secStyle, contentX, currentY);
        currentY -= lineGap;

        String[][] tiers = {
            {"1x Combo",  "1.0x Multiplier"},
            {"3x Combo",  "1.5x Multiplier"},
            {"5x Combo",  "2.0x Multiplier"},
            {"10x Combo", "3.0x Multiplier"},
        };
        for (String[] tier : tiers) {
            // Bullet star icon
            Image starIcon = new Image(game.manager.get("sprites/icon_star.png", Texture.class));
            starIcon.setSize(16, 16);
            starIcon.setColor(1f, 0.84f, 0f, 1f);
            starIcon.setPosition(contentX, currentY + 2f);
            stage.addActor(starIcon);

            Label comboTierLabel = new Label(tier[0] + "  \u2192  " + tier[1], goldStyle);
            comboTierLabel.setPosition(contentX + 22f, currentY);
            stage.addActor(comboTierLabel);
            currentY -= lineGap;
        }
        currentY -= sectionGap;

        // Section 3 — Scoring example
        currentY = addLabel("Scoring Example", secStyle, contentX, currentY);
        currentY -= lineGap;
        currentY = addLabel("Base match (4 bubbles): 400 points", textStyle, contentX, currentY);
        currentY -= lineGap;
        currentY = addLabel("With 3x Combo: 400 \u00d7 1.5 = 600 points", textStyle, contentX, currentY);
        currentY -= lineGap + sectionGap;

        // Section 4 — Tips
        currentY = addLabel("Tips", secStyle, contentX, currentY);
        currentY -= lineGap;
        currentY = addLabel("Plan ahead to chain matches!", smStyle, contentX, currentY);
        currentY -= lineGap;
        addLabel("Watch your move count — use them wisely!", smStyle, contentX, currentY);

        // BACK button
        TextButton backBtn = new TextButton("BACK",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        backBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        backBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.05f
        );
        backBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(backBtn);
    }

    /** Adds a label and returns the Y position to use for the next element (below this label). */
    private float addLabel(String text, Label.LabelStyle style, float x, float y) {
        Label label = new Label(text, style);
        label.setPosition(x, y);
        stage.addActor(label);
        return y - label.getPrefHeight();
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
        sectionFont.dispose();
        bodyFont.dispose();
        smallFont.dispose();
    }
}
