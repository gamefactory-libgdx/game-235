# SOUNDS.md — Audio Library (CC0)

All sounds are pre-copied to `assets/sounds/` by the pipeline.
Load them via `AssetManager` — never use `new Sound()` or `new Music()` directly.

---

## Music tracks — `assets/sounds/music/`

| File | Style | Use for |
|------|-------|---------|
| `sounds/music/music_menu.ogg` | Chiptune 8-bit | Main menu, leaderboard, settings |
| `sounds/music/music_gameplay.ogg` | Chiptune 8-bit | In-game loop (runners, puzzles, general) |
| `sounds/music/music_gameplay_alt.ogg` | Chiptune 8-bit | Tension / boss fights / alternate gameplay |
| `sounds/music/music_gameplay_space.ogg` | Ambient electronic | Space shooters, sci-fi games |
| `sounds/music/music_game_over.ogg` | Chiptune sting | Game over screen — plays ONCE, no loop |

## Sound effects — `assets/sounds/sfx/`

| File | Use for |
|------|---------|
| `sounds/sfx/sfx_button_click.ogg` | Any button press (Play, Retry, Buy, etc.) |
| `sounds/sfx/sfx_button_back.ogg` | Back / cancel / close buttons |
| `sounds/sfx/sfx_toggle.ogg` | Settings toggles (music on/off, sfx on/off) |
| `sounds/sfx/sfx_coin.ogg` | Coin / gem / star collected |
| `sounds/sfx/sfx_jump.ogg` | Player jump / thrust / launch |
| `sounds/sfx/sfx_hit.ogg` | Player hit by obstacle / enemy |
| `sounds/sfx/sfx_game_over.ogg` | Game over event |
| `sounds/sfx/sfx_level_complete.ogg` | Level complete / high score |
| `sounds/sfx/sfx_power_up.ogg` | Power-up collected / activated |
| `sounds/sfx/sfx_shoot.ogg` | Laser / projectile fired |
| `sounds/sfx/sfx_error.ogg` | Not enough coins / invalid action |

---

## Loading in MainGame

```java
// Music (streaming — do NOT use Sound for music)
manager.load("sounds/music/music_menu.ogg",           Music.class);
manager.load("sounds/music/music_gameplay.ogg",       Music.class);
manager.load("sounds/music/music_gameplay_alt.ogg",   Music.class);
manager.load("sounds/music/music_gameplay_space.ogg", Music.class);
manager.load("sounds/music/music_game_over.ogg",      Music.class);

// SFX (fully buffered in memory)
manager.load("sounds/sfx/sfx_button_click.ogg", Sound.class);
manager.load("sounds/sfx/sfx_button_back.ogg",  Sound.class);
manager.load("sounds/sfx/sfx_toggle.ogg",       Sound.class);
manager.load("sounds/sfx/sfx_coin.ogg",         Sound.class);
manager.load("sounds/sfx/sfx_jump.ogg",         Sound.class);
manager.load("sounds/sfx/sfx_hit.ogg",          Sound.class);
manager.load("sounds/sfx/sfx_game_over.ogg",    Sound.class);
manager.load("sounds/sfx/sfx_level_complete.ogg", Sound.class);
manager.load("sounds/sfx/sfx_power_up.ogg",     Sound.class);
manager.load("sounds/sfx/sfx_shoot.ogg",        Sound.class);
manager.load("sounds/sfx/sfx_error.ogg",        Sound.class);
```

## Playing sounds

```java
// Background music (looping) — menu, gameplay
Music menuMusic = game.manager.get("sounds/music/music_menu.ogg", Music.class);
menuMusic.setLooping(true);
menuMusic.setVolume(0.7f);
if (game.musicEnabled) menuMusic.play();

// Game over music — plays ONCE, no loop
Music gameOverMusic = game.manager.get("sounds/music/music_game_over.ogg", Music.class);
gameOverMusic.setLooping(false);
gameOverMusic.setVolume(0.7f);
if (game.musicEnabled) gameOverMusic.play();

// Stop previous music before starting new
if (menuMusic.isPlaying()) menuMusic.stop();

// SFX
Sound clickSfx = game.manager.get("sounds/sfx/sfx_button_click.ogg", Sound.class);
if (game.sfxEnabled) clickSfx.play(1.0f);
```
