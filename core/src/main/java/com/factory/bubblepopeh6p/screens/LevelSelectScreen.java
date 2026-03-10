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

public class LevelSelectScreen implements Screen {

    private final MainGame game;
    private final int themeIndex;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont numFont;
    private final BitmapFont bodyFont;

    public LevelSelectScreen(MainGame game, int themeIndex) {
        this.game       = game;
        this.themeIndex = themeIndex;

        camera   = new OrthographicCamera();
        viewport = new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera);
        stage    = new Stage(viewport, game.batch);

        Gdx.input.setInputProcessor(new InputMultiplexer(stage, new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (keycode == Input.Keys.BACK) {
                    game.setScreen(new ThemeSelectScreen(game));
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

        FreeTypeFontGenerator nGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter np = new FreeTypeFontGenerator.FreeTypeFontParameter();
        np.size  = 18;
        np.color = Color.WHITE;
        numFont = nGen.generateFont(np);
        nGen.dispose();

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

    private String bgPath() {
        switch (themeIndex) {
            case Constants.THEME_SPACE: return "backgrounds/bg_space.png";
            case Constants.THEME_CANDY: return "backgrounds/bg_candy.png";
            default:                    return "backgrounds/bg_ocean.png";
        }
    }

    private String themeName() {
        switch (themeIndex) {
            case Constants.THEME_SPACE: return "SPACE";
            case Constants.THEME_CANDY: return "CANDY";
            default:                    return "OCEAN";
        }
    }

    private void buildUI() {
        // Background
        Image bg = new Image(game.manager.get(bgPath(), Texture.class));
        bg.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        stage.addActor(bg);

        // Header
        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, Color.WHITE);
        Label header = new Label(themeName() + " \u2014 LEVELS", headerStyle);
        header.setPosition(
            (Constants.WORLD_WIDTH - header.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.87f
        );
        stage.addActor(header);

        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        // Highest unlocked level key per theme
        String unlockedKey = (themeIndex == Constants.THEME_OCEAN)
            ? Constants.PREF_UNLOCKED_OCEAN
            : (themeIndex == Constants.THEME_SPACE ? Constants.PREF_UNLOCKED_SPACE : Constants.PREF_UNLOCKED_CANDY);
        int highestUnlocked = prefs.getInteger(unlockedKey, 0); // 0 = only level 0 unlocked

        // 5 columns × 6 rows = 30 levels
        int cols = 5;
        int rows = 6;
        float btnSize    = Constants.LEVEL_BTN_SIZE;
        float btnSpacing = Constants.LEVEL_BTN_SPACING;
        float gridW      = cols * btnSize + (cols - 1) * btnSpacing;
        float startX     = (Constants.WORLD_WIDTH - gridW) / 2f;
        float startY     = Constants.WORLD_HEIGHT * 0.78f;

        Label.LabelStyle numStyle    = new Label.LabelStyle(numFont, Color.WHITE);
        Label.LabelStyle lockedStyle = new Label.LabelStyle(numFont, new Color(0.5f, 0.5f, 0.5f, 1f));
        Label.LabelStyle starStyle   = new Label.LabelStyle(bodyFont, new Color(1f, 0.84f, 0f, 1f));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                final int levelIndex = row * cols + col;
                if (levelIndex >= Constants.LEVELS_PER_THEME) break;

                float btnX = startX + col * (btnSize + btnSpacing);
                float btnY = startY - row * (btnSize + btnSpacing);

                boolean isUnlocked = (levelIndex <= highestUnlocked);
                int stars = prefs.getInteger(Constants.PREF_STARS_PREFIX + themeIndex + "_" + levelIndex, 0);

                // Button background
                String upTex   = isUnlocked ? "sprites/button_blue.png"   : "sprites/button_grey.png";
                String downTex = isUnlocked ? "sprites/button_blue_pressed.png" : "sprites/button_grey_pressed.png";

                TextButton btn = new TextButton(String.valueOf(levelIndex + 1),
                    makeStyle(upTex, downTex, isUnlocked ? numFont : numFont));
                btn.setSize(btnSize, btnSize);
                btn.setPosition(btnX, btnY);
                if (!isUnlocked) btn.getLabel().setColor(0.5f, 0.5f, 0.5f, 1f);

                if (isUnlocked) {
                    final int lvl = levelIndex;
                    btn.addListener(new ChangeListener() {
                        @Override public void changed(ChangeEvent e, Actor a) {
                            playSound("sounds/sfx/sfx_button_click.ogg");
                            game.setScreen(new GameScreen(game, themeIndex, lvl));
                        }
                    });
                }
                stage.addActor(btn);

                // Stars below button
                if (stars > 0) {
                    StringBuilder sb = new StringBuilder();
                    for (int s = 0; s < stars; s++) sb.append('\u2605');
                    Label starLabel = new Label(sb.toString(), starStyle);
                    starLabel.setFontScale(0.7f);
                    starLabel.setPosition(
                        btnX + (btnSize - starLabel.getPrefWidth() * 0.7f) / 2f,
                        btnY - 14f
                    );
                    stage.addActor(starLabel);
                }

                // Lock icon for locked levels
                if (!isUnlocked) {
                    Image lockIcon = new Image(game.manager.get("sprites/icon_locked.png", Texture.class));
                    lockIcon.setSize(20, 20);
                    lockIcon.setPosition(btnX + btnSize / 2f - 10f, btnY + btnSize / 2f - 10f);
                    stage.addActor(lockIcon);
                }
            }
        }

        // BACK button
        TextButton backBtn = new TextButton("BACK",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png", bodyFont));
        backBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        backBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.04f
        );
        backBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new ThemeSelectScreen(game));
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
        numFont.dispose();
        bodyFont.dispose();
    }
}
