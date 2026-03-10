package com.factory.bubblepopeh6p;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.factory.bubblepopeh6p.screens.MainMenuScreen;

public class MainGame extends Game {

    public SpriteBatch  batch;
    public AssetManager manager;

    public boolean musicEnabled = true;
    public boolean sfxEnabled   = true;
    public Music   currentMusic = null;

    @Override
    public void create() {
        batch   = new SpriteBatch();
        manager = new AssetManager();

        loadAssets();
        manager.finishLoading();

        setScreen(new MainMenuScreen(this));
    }

    private void loadAssets() {
        // Backgrounds
        manager.load("backgrounds/bg_main.png",  Texture.class);
        manager.load("backgrounds/bg_ocean.png", Texture.class);
        manager.load("backgrounds/bg_space.png", Texture.class);
        manager.load("backgrounds/bg_candy.png", Texture.class);

        // UI screens / overlays
        manager.load("ui/mainmenu.png",               Texture.class);
        manager.load("ui/theme_select.png",            Texture.class);
        manager.load("ui/level_select_ocean.png",      Texture.class);
        manager.load("ui/level_select_space.png",      Texture.class);
        manager.load("ui/level_select_candy.png",      Texture.class);
        manager.load("ui/game_ocean.png",              Texture.class);
        manager.load("ui/game_space.png",              Texture.class);
        manager.load("ui/game_candy.png",              Texture.class);
        manager.load("ui/level_complete_ocean.png",    Texture.class);
        manager.load("ui/level_complete_space.png",    Texture.class);
        manager.load("ui/level_complete_candy.png",    Texture.class);
        manager.load("ui/game_over.png",               Texture.class);
        manager.load("ui/combo_guide.png",             Texture.class);
        manager.load("ui/leaderboard.png",             Texture.class);
        manager.load("ui/settings.png",                Texture.class);

        // Buttons
        manager.load("sprites/button_blue.png",           Texture.class);
        manager.load("sprites/button_blue_pressed.png",   Texture.class);
        manager.load("sprites/button_grey.png",           Texture.class);
        manager.load("sprites/button_grey_pressed.png",   Texture.class);
        manager.load("sprites/button_green.png",          Texture.class);
        manager.load("sprites/button_green_pressed.png",  Texture.class);
        manager.load("sprites/button_red.png",            Texture.class);
        manager.load("sprites/button_red_pressed.png",    Texture.class);
        manager.load("sprites/button_yellow.png",         Texture.class);
        manager.load("sprites/button_yellow_pressed.png", Texture.class);
        manager.load("sprites/button_round_blue.png",         Texture.class);
        manager.load("sprites/button_round_blue_pressed.png", Texture.class);
        manager.load("sprites/button_round_grey.png",         Texture.class);

        // Icons
        manager.load("sprites/icon_play.png",         Texture.class);
        manager.load("sprites/icon_pause.png",        Texture.class);
        manager.load("sprites/icon_back.png",         Texture.class);
        manager.load("sprites/icon_home.png",         Texture.class);
        manager.load("sprites/icon_settings.png",     Texture.class);
        manager.load("sprites/icon_sfx_on.png",       Texture.class);
        manager.load("sprites/icon_sfx_off.png",      Texture.class);
        manager.load("sprites/icon_music_on.png",     Texture.class);
        manager.load("sprites/icon_music_off.png",    Texture.class);
        manager.load("sprites/icon_star.png",         Texture.class);
        manager.load("sprites/icon_trophy.png",       Texture.class);
        manager.load("sprites/icon_leaderboard.png",  Texture.class);
        manager.load("sprites/icon_locked.png",       Texture.class);
        manager.load("sprites/icon_unlocked.png",     Texture.class);
        manager.load("sprites/icon_medal.png",        Texture.class);
        manager.load("sprites/icon_close.png",        Texture.class);
        manager.load("sprites/icon_check.png",        Texture.class);
        manager.load("sprites/icon_heart.png",        Texture.class);
        manager.load("sprites/icon_heart_empty.png",  Texture.class);
        manager.load("sprites/icon_timer.png",        Texture.class);

        // Puzzle / bubble sprites
        manager.load("sprites/ball_blue.png",   Texture.class);
        manager.load("sprites/ball_yellow.png", Texture.class);
        manager.load("sprites/ball_grey.png",   Texture.class);
        manager.load("sprites/star.png",        Texture.class);

        // Generic collectibles
        manager.load("sprites/coin_gold.png",   Texture.class);
        manager.load("sprites/gem_blue.png",    Texture.class);
        manager.load("sprites/gem_red.png",     Texture.class);
        manager.load("sprites/gem_green.png",   Texture.class);
        manager.load("sprites/gem_yellow.png",  Texture.class);

        // Music (streaming)
        manager.load("sounds/music/music_menu.ogg",           Music.class);
        manager.load("sounds/music/music_gameplay.ogg",       Music.class);
        manager.load("sounds/music/music_gameplay_alt.ogg",   Music.class);
        manager.load("sounds/music/music_game_over.ogg",      Music.class);

        // SFX (buffered)
        manager.load("sounds/sfx/sfx_button_click.ogg",   Sound.class);
        manager.load("sounds/sfx/sfx_button_back.ogg",    Sound.class);
        manager.load("sounds/sfx/sfx_toggle.ogg",         Sound.class);
        manager.load("sounds/sfx/sfx_coin.ogg",           Sound.class);
        manager.load("sounds/sfx/sfx_hit.ogg",            Sound.class);
        manager.load("sounds/sfx/sfx_game_over.ogg",      Sound.class);
        manager.load("sounds/sfx/sfx_level_complete.ogg", Sound.class);
        manager.load("sounds/sfx/sfx_power_up.ogg",       Sound.class);
        manager.load("sounds/sfx/sfx_shoot.ogg",          Sound.class);
        manager.load("sounds/sfx/sfx_error.ogg",          Sound.class);
    }

    /** Start looping music. Stops any currently playing track first. */
    public void playMusic(String path) {
        if (currentMusic != null && currentMusic.isPlaying()) currentMusic.stop();
        currentMusic = manager.get(path, Music.class);
        currentMusic.setLooping(true);
        currentMusic.setVolume(0.7f);
        if (musicEnabled) currentMusic.play();
    }

    /** Play a one-shot music track (game over sting — must NOT loop). */
    public void playMusicOnce(String path) {
        if (currentMusic != null && currentMusic.isPlaying()) currentMusic.stop();
        currentMusic = manager.get(path, Music.class);
        currentMusic.setLooping(false);
        currentMusic.setVolume(0.7f);
        if (musicEnabled) currentMusic.play();
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        manager.dispose();
    }
}
