# SPRITES.md — Available Sprite Assets

All sprites are from the **Kenney.nl** free CC0 game asset library.
Load them via `AssetManager` using paths like `"sprites/player_idle.png"`.

## RUNNER / PLATFORMER / JETPACK sprites
Use for: side-scrollers, endless runners, jetpack games, flappy-style games.

| File | Description |
|------|-------------|
| `sprites/player_idle.png` | Player character facing forward (idle/standing) |
| `sprites/player_stand.png` | Player standing (same as idle, alternate) |
| `sprites/player_walk1.png` | Player walk frame 1 — animate with walk2 for walk cycle |
| `sprites/player_walk2.png` | Player walk frame 2 |
| `sprites/player_jump.png` | Player jumping |
| `sprites/player_hurt.png` | Player hit / hurt |
| `sprites/enemy_slime.png` | Green slime enemy |
| `sprites/enemy_fly.png` | Fly / winged enemy |
| `sprites/enemy_bee.png` | Bee enemy |
| `sprites/enemy_saw.png` | Spinning saw blade obstacle |
| `sprites/obstacle_spike.png` | Spikes (floor/ceiling hazard) |
| `sprites/obstacle_rock.png` | Rock obstacle |
| `sprites/obstacle_lava.png` | Lava tile |
| `sprites/tile_ground_mid.png` | Grass ground — middle tile |
| `sprites/tile_ground_left.png` | Grass ground — left edge |
| `sprites/tile_ground_right.png` | Grass ground — right edge |
| `sprites/tile_dirt_mid.png` | Dirt ground — middle tile |
| `sprites/tile_box.png` | Wooden crate tile |
| `sprites/tile_brick.png` | Brown brick tile |
| `sprites/particle_fireball.png` | Fireball particle effect |

## SPACE SHOOTER sprites
Use for: space shooters, asteroid dodge, bullet-hell games.

| File | Description |
|------|-------------|
| `sprites/player_ship.png` | Player spaceship (blue) |
| `sprites/player_ship_alt.png` | Player spaceship alternate (green) |
| `sprites/enemy_ship1.png` | Red enemy ship |
| `sprites/enemy_ship2.png` | Blue enemy ship |
| `sprites/enemy_ship3.png` | Green enemy ship |
| `sprites/enemy_ship4.png` | Black enemy ship |
| `sprites/laser_player.png` | Blue laser (player bullet) |
| `sprites/laser_enemy.png` | Red laser (enemy bullet) |
| `sprites/asteroid_big.png` | Large brown asteroid |
| `sprites/asteroid_med.png` | Medium asteroid |
| `sprites/asteroid_small.png` | Small asteroid |
| `sprites/effect_fire1.png` | Fire/explosion frame 1 |
| `sprites/effect_fire2.png` | Fire/explosion frame 2 |

## RACING sprites
Use for: top-down car racing, lane-dodge, traffic games.

| File | Description |
|------|-------------|
| `sprites/car_player.png` | Player car (yellow) — top-down view |
| `sprites/car_red.png` | Red traffic car |
| `sprites/car_blue.png` | Blue traffic car |
| `sprites/car_green.png` | Green traffic car |
| `sprites/car_black.png` | Black traffic car |

## PUZZLE / BRICK-BREAKER sprites
Use for: brick breaker, Arkanoid, ball bounce, Breakout games.

| File | Description |
|------|-------------|
| `sprites/ball_blue.png` | Blue ball (the main ball) |
| `sprites/ball_yellow.png` | Yellow ball (alternate color) |
| `sprites/ball_grey.png` | Grey ball |
| `sprites/paddle.png` | Standard paddle (white/grey) |
| `sprites/paddle_blue.png` | Blue paddle |
| `sprites/coin_spin1.png` | Coin spin frame 1 |
| `sprites/coin_spin2.png` | Coin spin frame 2 |
| `sprites/back_tile.png` | Background tile for puzzle board |
| `sprites/back_tile2.png` | Background tile variant 2 |

## GENERIC COLLECTIBLES
Use in any game type for coins, gems, stars.

| File | Description |
|------|-------------|
| `sprites/coin_gold.png` | Gold coin |
| `sprites/coin_silver.png` | Silver coin |
| `sprites/coin_bronze.png` | Bronze coin |
| `sprites/star.png` | Star collectible |
| `sprites/gem_blue.png` | Blue gem / crystal |
| `sprites/gem_red.png` | Red gem / ruby |
| `sprites/gem_green.png` | Green gem / emerald |
| `sprites/gem_yellow.png` | Yellow gem / topaz |

## Usage example

```java
// Loading (in your LoadingScreen or MainGame):
manager.load("sprites/player_idle.png", Texture.class);
manager.load("sprites/enemy_slime.png", Texture.class);
manager.load("sprites/coin_gold.png", Texture.class);
manager.finishLoading();

// Rendering (in your GameScreen):
Texture playerTex = manager.get("sprites/player_idle.png", Texture.class);
batch.draw(playerTex, x, y, 64, 64);  // 64x64 world units

// Walk animation:
TextureRegion[] frames = {
    new TextureRegion(manager.get("sprites/player_walk1.png", Texture.class)),
    new TextureRegion(manager.get("sprites/player_walk2.png", Texture.class))
};
Animation<TextureRegion> walkAnim = new Animation<>(0.15f, frames);
```

**Source:** Kenney.nl — CC0 license — free to use in any project.

## UI BUTTONS & ICONS
Use for ALL screens — buttons, settings sliders, HUD icons.
These replace plain `TextButton` with styled, polished Kenney buttons.

### Buttons — 5 colours, each with up (depth shadow) and pressed (flat) state

| File | Colour | Use for |
|------|--------|---------|
| `sprites/button_blue.png` | Blue | Primary action (Play, Start, Confirm) |
| `sprites/button_blue_pressed.png` | Blue flat | Pressed/down state of blue button |
| `sprites/button_grey.png` | Grey | Secondary action (Back, Cancel, Menu) |
| `sprites/button_grey_pressed.png` | Grey flat | Pressed state of grey button |
| `sprites/button_green.png` | Green | Positive action (Buy, Unlock, Yes) |
| `sprites/button_green_pressed.png` | Green flat | Pressed state |
| `sprites/button_red.png` | Red | Destructive / Retry / Alert |
| `sprites/button_red_pressed.png` | Red flat | Pressed state |
| `sprites/button_yellow.png` | Yellow | Highlight / special action |
| `sprites/button_yellow_pressed.png` | Yellow flat | Pressed state |
| `sprites/button_round_blue.png` | Round blue | Small icon buttons (pause, settings) |
| `sprites/button_round_blue_pressed.png` | Round blue flat | Pressed state |
| `sprites/button_round_grey.png` | Round grey | Small secondary icon buttons |

### Slider (for Settings volume/SFX)

| File | Use for |
|------|---------|
| `sprites/slider_track.png` | Slider background track |
| `sprites/slider_fill.png` | Filled portion of slider |
| `sprites/slider_knob.png` | Draggable knob |

### Icons (white, for use on coloured buttons or HUD)

| File | Icon |
|------|------|
| `sprites/icon_play.png` | Play / start |
| `sprites/icon_pause.png` | Pause |
| `sprites/icon_back.png` | Back / return |
| `sprites/icon_home.png` | Main menu / home |
| `sprites/icon_settings.png` | Settings gear |
| `sprites/icon_sfx_on.png` | SFX enabled |
| `sprites/icon_sfx_off.png` | SFX disabled |
| `sprites/icon_music_on.png` | Music enabled |
| `sprites/icon_music_off.png` | Music disabled |
| `sprites/icon_star.png` | Star / rating |
| `sprites/icon_trophy.png` | Trophy / achievement |
| `sprites/icon_leaderboard.png` | Leaderboard |
| `sprites/icon_shop.png` | Shop / store |
| `sprites/icon_locked.png` | Locked item |
| `sprites/icon_unlocked.png` | Unlocked item |
| `sprites/icon_medal.png` | Medal / rank |
| `sprites/icon_close.png` | Close / X |
| `sprites/icon_check.png` | Confirm / checkmark |
| `sprites/icon_heart.png` | Life / health (full heart) |
| `sprites/icon_heart_empty.png` | Life lost (empty / broken heart) |
| `sprites/icon_timer.png` | Timer / hourglass |
### How to use buttons in Scene2D

```java
// Load in MainGame or LoadingScreen:
manager.load("sprites/button_blue.png", Texture.class);
manager.load("sprites/button_blue_pressed.png", Texture.class);
manager.load("sprites/button_grey.png", Texture.class);
manager.load("sprites/button_grey_pressed.png", Texture.class);
// load other buttons/icons you'll use...

// Helper — call once per screen to build a reusable style:
private TextButton.TextButtonStyle makeButtonStyle(String upFile, String downFile) {
    TextButton.TextButtonStyle s = new TextButton.TextButtonStyle();
    s.font = game.skin.getFont("default-font"); // or your BitmapFont
    s.up   = new TextureRegionDrawable(manager.get(upFile,   Texture.class));
    s.down = new TextureRegionDrawable(manager.get(downFile, Texture.class));
    s.fontColor = Color.WHITE;
    return s;
}

// Create styled buttons:
TextButton playBtn = new TextButton("PLAY",
    makeButtonStyle("sprites/button_blue.png", "sprites/button_blue_pressed.png"));
playBtn.setSize(240, 70);

TextButton backBtn = new TextButton("BACK",
    makeButtonStyle("sprites/button_grey.png", "sprites/button_grey_pressed.png"));
backBtn.setSize(200, 60);

// Icon button (pause — round blue with icon overlay):
Image pauseIcon = new Image(manager.get("sprites/icon_pause.png", Texture.class));
// place over a button_round_blue background, or use ImageTextButton
```

**Rules:**
- Always provide BOTH `up` and `down` drawables — never use the same texture for both
- Blue = primary action, Grey = secondary/back, Green = buy/confirm, Red = retry/danger
- Standard sizes: main buttons 240×70, secondary 200×60, round icon buttons 60×60

## PLAYER SKIN VARIANTS (for ShopScreen)
Use for runner/jetpack/platformer games. Default = yellow, purchasable = green/pink/blue.

| File | Skin | Frames |
|------|------|--------|
| `sprites/player_idle.png` | Yellow (default) | idle |
| `sprites/player_walk1.png` | Yellow (default) | walk frame 1 |
| `sprites/player_walk2.png` | Yellow (default) | walk frame 2 |
| `sprites/player_jump.png` | Yellow (default) | jump |
| `sprites/player_hurt.png` | Yellow (default) | hurt |
| `sprites/player_idle_green.png` | Green skin | idle |
| `sprites/player_walk1_green.png` | Green skin | walk frame 1 |
| `sprites/player_walk2_green.png` | Green skin | walk frame 2 |
| `sprites/player_jump_green.png` | Green skin | jump |
| `sprites/player_hurt_green.png` | Green skin | hurt |
| `sprites/player_idle_pink.png` | Pink skin | idle |
| `sprites/player_walk1_pink.png` | Pink skin | walk frame 1 |
| `sprites/player_walk2_pink.png` | Pink skin | walk frame 2 |
| `sprites/player_jump_pink.png` | Pink skin | jump |
| `sprites/player_hurt_pink.png` | Pink skin | hurt |
| `sprites/player_idle_blue.png` | Blue skin | idle |
| `sprites/player_walk1_blue.png` | Blue skin | walk frame 1 |
| `sprites/player_walk2_blue.png` | Blue skin | walk frame 2 |
| `sprites/player_jump_blue.png` | Blue skin | jump |
| `sprites/player_hurt_blue.png` | Blue skin | hurt |

For **racing** games — use `car_player.png` (yellow/default), `car_red.png`, `car_blue.png` as skins.
For **space** games — use `player_ship.png` (blue/default), `player_ship_alt.png` (green) as skins.
