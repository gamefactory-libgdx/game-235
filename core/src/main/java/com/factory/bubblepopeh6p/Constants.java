package com.factory.bubblepopeh6p;

public class Constants {

    // World dimensions
    public static final float WORLD_WIDTH  = 480f;
    public static final float WORLD_HEIGHT = 854f;

    // Bubble geometry
    public static final float BUBBLE_RADIUS   = 28f;
    public static final float BUBBLE_DIAMETER = 56f;
    public static final int   GRID_COLS       = 8;

    // Cannon
    public static final float CANNON_X           = WORLD_WIDTH / 2f;
    public static final float CANNON_Y            = 100f;
    public static final float CANNON_WIDTH        = 64f;
    public static final float CANNON_HEIGHT       = 80f;
    public static final float BUBBLE_SHOOT_SPEED  = 700f;
    public static final float CANNON_ANGLE_MIN    = 10f;
    public static final float CANNON_ANGLE_MAX    = 170f;

    // Next bubble preview
    public static final float NEXT_BUBBLE_X = WORLD_WIDTH / 2f;
    public static final float NEXT_BUBBLE_Y = 185f;

    // Grid positioning
    public static final float GRID_TOP_Y   = WORLD_HEIGHT - 120f;
    public static final float GRID_LEFT_X  = (WORLD_WIDTH - GRID_COLS * BUBBLE_DIAMETER) / 2f;

    // Gameplay
    public static final int   MOVES_PER_LEVEL   = 10;
    public static final int   MIN_MATCH_COUNT   = 3;
    public static final int   LEVELS_PER_THEME  = 30;
    public static final int   NUM_THEMES        = 3;
    public static final int   BUBBLE_COLORS     = 5;

    // Scoring
    public static final int   SCORE_PER_BUBBLE  = 100;
    public static final int   SCORE_BONUS_BURST = 50;   // bonus for cascades / isolated drops

    // Combo multiplier tiers (matches must be consecutive shots)
    public static final int   COMBO_THRESHOLD_1 = 3;
    public static final float COMBO_MULT_1      = 1.5f;
    public static final int   COMBO_THRESHOLD_2 = 5;
    public static final float COMBO_MULT_2      = 2.0f;
    public static final int   COMBO_THRESHOLD_3 = 10;
    public static final float COMBO_MULT_3      = 3.0f;

    // Stars thresholds (fraction of max possible score per level)
    public static final float STARS_2_THRESHOLD = 0.5f;
    public static final float STARS_3_THRESHOLD = 0.8f;

    // HUD layout
    public static final float HUD_HEIGHT       = 80f;
    public static final float HUD_PADDING      = 12f;
    public static final float HUD_FONT_SIZE    = 24f;
    public static final float HUD_LABEL_SIZE   = 18f;

    // Button sizes
    public static final float BTN_MAIN_WIDTH       = 240f;
    public static final float BTN_MAIN_HEIGHT      = 70f;
    public static final float BTN_SECONDARY_WIDTH  = 200f;
    public static final float BTN_SECONDARY_HEIGHT = 60f;
    public static final float BTN_ROUND_SIZE       = 60f;
    public static final float BTN_SMALL_WIDTH      = 100f;
    public static final float BTN_SMALL_HEIGHT     = 50f;

    // Level-select card dimensions
    public static final float LEVEL_BTN_SIZE    = 70f;
    public static final float LEVEL_BTN_SPACING = 8f;

    // Theme card dimensions (ThemeSelectScreen)
    public static final float THEME_CARD_WIDTH  = 380f;
    public static final float THEME_CARD_HEIGHT = 130f;

    // Font sizes
    public static final float FONT_TITLE  = 56f;
    public static final float FONT_HEADER = 44f;
    public static final float FONT_BODY   = 18f;
    public static final float FONT_SMALL  = 14f;

    // Theme identifiers
    public static final int THEME_OCEAN = 0;
    public static final int THEME_SPACE = 1;
    public static final int THEME_CANDY = 2;

    // SharedPreferences keys
    public static final String PREFS_NAME              = "GamePrefs";
    public static final String PREF_MUSIC              = "musicEnabled";
    public static final String PREF_SFX                = "sfxEnabled";
    public static final String PREF_HIGH_SCORE_OCEAN   = "highScoreOcean";
    public static final String PREF_HIGH_SCORE_SPACE   = "highScoreSpace";
    public static final String PREF_HIGH_SCORE_CANDY   = "highScoreCandy";
    public static final String PREF_UNLOCKED_OCEAN     = "unlockedOcean";
    public static final String PREF_UNLOCKED_SPACE     = "unlockedSpace";
    public static final String PREF_UNLOCKED_CANDY     = "unlockedCandy";
    public static final String PREF_STARS_PREFIX       = "stars_";  // append theme_level, e.g. "stars_0_3"
    public static final String PREF_SELECTED_THEME     = "selectedTheme";
}
