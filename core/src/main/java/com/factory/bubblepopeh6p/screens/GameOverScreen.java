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

public class GameOverScreen implements Screen {

    private final MainGame game;
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final BitmapFont headerFont;
    private final BitmapFont scoreFont;
    private final BitmapFont bodyFont;

    /** @param score final score for this run
     *  @param extra theme index: 0=Ocean, 1=Space, 2=Candy */
    public GameOverScreen(MainGame game, int score, int extra) {
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
        hp.size  = 44;
        hp.color = new Color(1f, 0.39f, 0.28f, 1f); // #FF6347 tomato red
        headerFont = hGen.generateFont(hp);
        hGen.dispose();

        FreeTypeFontGenerator sGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Orbitron-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter sp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        sp.size  = 32;
        sp.color = Color.WHITE;
        scoreFont = sGen.generateFont(sp);
        sGen.dispose();

        FreeTypeFontGenerator bGen = new FreeTypeFontGenerator(Gdx.files.internal("ui/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter bp = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bp.size  = (int) Constants.FONT_BODY;
        bp.color = Color.WHITE;
        bodyFont = bGen.generateFont(bp);
        bGen.dispose();

        // Save new high score if beaten
        Preferences prefs = Gdx.app.getPreferences(Constants.PREFS_NAME);
        String highKey = highScoreKey(extra);
        int best = prefs.getInteger(highKey, 0);
        if (score > best) {
            prefs.putInteger(highKey, score);
            prefs.flush();
            best = score;
        }

        // Also add to global leaderboard
        LeaderboardScreen.addScore(score);

        buildUI(score, best, extra);
        game.playMusicOnce("sounds/music/music_game_over.ogg");
        playSound("sounds/sfx/sfx_game_over.ogg");
    }

    private static String highScoreKey(int theme) {
        switch (theme) {
            case Constants.THEME_SPACE: return Constants.PREF_HIGH_SCORE_SPACE;
            case Constants.THEME_CANDY: return Constants.PREF_HIGH_SCORE_CANDY;
            default:                    return Constants.PREF_HIGH_SCORE_OCEAN;
        }
    }

    private void buildUI(int score, int best, final int theme) {
        // Background with dark overlay
        Image bg = new Image(game.manager.get("backgrounds/bg_main.png", Texture.class));
        bg.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        bg.setColor(0.6f, 0.6f, 0.6f, 1f);
        stage.addActor(bg);

        // "GAME OVER" header
        Label.LabelStyle headerStyle = new Label.LabelStyle(headerFont, new Color(1f, 0.39f, 0.28f, 1f));
        Label headerLabel = new Label("GAME OVER", headerStyle);
        headerLabel.setPosition(
            (Constants.WORLD_WIDTH - headerLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.68f
        );
        stage.addActor(headerLabel);

        // Score
        Label.LabelStyle scoreStyle = new Label.LabelStyle(scoreFont, Color.WHITE);
        Label scoreLabel = new Label("Score: " + score, scoreStyle);
        scoreLabel.setPosition(
            (Constants.WORLD_WIDTH - scoreLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.58f
        );
        stage.addActor(scoreLabel);

        // Personal best
        Label.LabelStyle bodyStyle = new Label.LabelStyle(bodyFont, new Color(1f, 0.84f, 0f, 1f));
        String bestText = (score >= best && best == score) ? "NEW BEST: " + best : "Best: " + best;
        Label bestLabel = new Label(bestText, bodyStyle);
        bestLabel.setPosition(
            (Constants.WORLD_WIDTH - bestLabel.getPrefWidth()) / 2f,
            Constants.WORLD_HEIGHT * 0.51f
        );
        stage.addActor(bestLabel);

        // RETRY button
        TextButton retryBtn = new TextButton("RETRY",
            makeStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png"));
        retryBtn.setSize(Constants.BTN_MAIN_WIDTH, Constants.BTN_MAIN_HEIGHT);
        retryBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_MAIN_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.35f
        );
        retryBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_click.ogg");
                game.setScreen(new ThemeSelectScreen(game));
            }
        });
        stage.addActor(retryBtn);

        // MAIN MENU button
        TextButton menuBtn = new TextButton("MAIN MENU",
            makeStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
        menuBtn.setSize(Constants.BTN_SECONDARY_WIDTH, Constants.BTN_SECONDARY_HEIGHT);
        menuBtn.setPosition(
            (Constants.WORLD_WIDTH - Constants.BTN_SECONDARY_WIDTH) / 2f,
            Constants.WORLD_HEIGHT * 0.22f
        );
        menuBtn.addListener(new ChangeListener() {
            @Override public void changed(ChangeEvent e, Actor a) {
                playSound("sounds/sfx/sfx_button_back.ogg");
                game.setScreen(new MainMenuScreen(game));
            }
        });
        stage.addActor(menuBtn);
    }

    private TextButton.TextButtonStyle makeStyle(String up, String down) {
        TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
        s.font      = bodyFont;
        s.up        = new TextureRegionDrawable(game.manager.get(up,   Texture.class));
        s.down      = new TextureRegionDrawable(game.manager.get(down, Texture.class));
        s.fontColor = Color.WHITE;
        return s;
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
