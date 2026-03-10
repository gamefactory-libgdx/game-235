# Figma AI Design Brief: Bubble Pop

---

## 1. Art Style & Color Palette

**Art Style:** Bright, cheerful cartoon with soft, rounded shapes and smooth gradients. Use a playful, child-friendly aesthetic inspired by modern mobile puzzlers (Candy Crush, Bubble Witch) with clean line work and subtle depth via layered shadows and highlights. All elements should feel tactile and bouncy, reinforcing the bubble-popping core mechanic.

**Primary Color Palette:**
- Ocean Theme: #1E90FF (dodger blue), #20B2AA (light sea green), #87CEEB (sky blue)
- Space Theme: #2B1B4D (deep purple), #FF6B9D (hot pink), #00D9FF (cyan)
- Candy Theme: #FFB6C1 (light pink), #FFD700 (gold), #FF69B4 (hot pink)

**Accent Colors:** #FFFFFF (white for highlights/text), #000000 at 20% opacity (subtle shadows)

**Typography:** Rounded, geometric sans-serif (e.g., Quicksand, Nunito, or similar). Heavy weights (700–900) for titles and CTAs, Medium (500) for body text. All text should feel friendly and energetic, never corporate.

---

## 2. App Icon — icon_512.png (512×512px)

**Background:** Radial gradient from #FFD700 (gold, center) to #FF69B4 (hot pink, edges) creating a warm, playful burst effect. Add a subtle vignette shadow at the edges (black, 15% opacity).

**Central Symbol:** A cluster of three overlapping glossy bubbles in different colors (blue #1E90FF, pink #FF69B4, gold #FFD700), each bubble with a white specular highlight in the upper-left quadrant and a thin darker shadow on the bottom edge. Behind the bubbles, a curved cannon barrel emerging from the bottom-center in a dark charcoal (#333333) with a white outline. The cannon should be tilted upward at 45 degrees, suggesting action and aim.

**Effects:** 
- Inner glow around each bubble (white, 8px blur, 30% opacity) to suggest glass/shininess
- Outer glow around the entire composition (pink #FF69B4, 12px blur, 20% opacity) to make the icon pop against any home screen background
- Subtle drop shadow (black, 4px offset down-right, 20% blur, 25% opacity) for depth

**Overall Mood:** Energetic, inviting, immediately communicates "bubble shooter" without text. Safe zone: all artwork contained within central 400×400px.

---

## 3. Backgrounds (480×854 portrait)

**Background Files Derived from Game Themes:**
1. backgrounds/bg_main.png — Main Menu / Title Screen
2. backgrounds/bg_ocean.png — Ocean Theme
3. backgrounds/bg_space.png — Space Theme
4. backgrounds/bg_candy.png — Candy Theme

---

**backgrounds/bg_main.png (480×854px)**
A vibrant, gradient-rich mashup celebrating all three themes. Top third features a twilight sky gradient from #87CEEB (sky blue) to #FFB347 (peach), with soft white clouds. Middle third shows three horizontal bands: left side features gentle ocean waves in #20B2AA, center shows a starfield with scattered stars and nebulas in purples (#2B1B4D) and cyans (#00D9FF), right side displays candy elements like swirled lollipops and sugar crystals in pinks (#FF69B4) and golds (#FFD700). Bottom third is a warm, unified gradient from #FFD700 to #FF69B4. Add playful decorative bubbles (4–6) scattered throughout in semi-transparent pastel shades, gently floating. No grid or gameplay elements visible.

---

**backgrounds/bg_ocean.png (480×854px)**
Underwater seascape with a deep gradient from #87CEEB (sky blue, top) through #1E90FF (ocean blue, middle) to #0B3D6D (deep navy, bottom). Scattered throughout: translucent coral clusters in orange (#FF6347) and pink (#FFB6C1), swaying seaweed strands in shades of green (#20B2AA), small schools of animated-style fish silhouettes in light blue, bubbles of varying sizes (some opaque, some semi-transparent) drifting upward, and sand ripples at the bottom in tan (#D2B48C). Add subtle light-ray streaks from the top, emanating downward in white at 10% opacity to suggest underwater lighting. Mood: calm, watery, inviting exploration.

---

**backgrounds/bg_space.png (480×854px)**
Deep space scene with a dark gradient background from #1A0033 (nearly black, top) to #2B1B4D (deep purple, bottom). Scatter stars at varying depths: small pinpoint stars in white (#FFFFFF), larger stars with faint halos in pale blue (#ADD8E6), distant nebula clouds in purples (#9370DB) and cyans (#00D9FF) with soft edges. Add 3–4 floating planets (circles) in different sizes and colors: one in orange (#FF6347), one in pale blue (#87CEEB), one in pink (#FF69B4). Include asteroid silhouettes drifting across the scene in dark gray (#444444). Subtle glow effects on planets and nebulas. Mood: mysterious, expansive, cosmic wonder.

---

**backgrounds/bg_candy.png (480×854px)**
Whimsical candy land with a radiant gradient from #FFE4E1 (misty rose, top) to #FFB6C1 (light pink, bottom). Populate with stylized candy elements: oversized lollipops in swirl patterns (red/white, blue/white, yellow/white), candy cane stripes in alternating colors, scattered hard candies (circles) in jewel tones (ruby red #DC143C, sapphire blue #0F52BA, emerald green #50C878). Add wavy, frosting-like ground elements in white and pale pink with smooth, rolling curves. Include faint sugar crystal sparkles (small star shapes) scattered throughout in gold (#FFD700) and light blue (#ADD8E6). Mood: sweet, cheerful, magical, playful.

---

## 4. UI Screens (480×854 portrait)

---

### MainMenuScreen

**Background Used:** backgrounds/bg_main.png

**Header/Title:** "BUBBLE POP" text centered near the top (vertical position: 15% from top), rendered in a bold, rounded sans-serif font (weight 800), size 56px, color #FFFFFF with a subtle drop shadow (black, 2px offset, 3px blur, 30% opacity). Small tagline "Match • Pop • Conquer" beneath in smaller font (24px, weight 500, color #FFD700).

**Buttons:** 
- "PLAY" button centered at 45% vertical, width ~200px, gradient background (#FFD700 to #FF69B4), bold white text (28px, weight 700), rounded corners. Subtle glow effect (#FF69B4, 8px blur).
- "SETTINGS" button bottom-left (80% vertical, 10% horizontal from left), width ~90px, background #1E90FF, white text (18px, weight 600), rounded.
- "LEADERBOARD" button bottom-center (80% vertical, 45% horizontal from left), width ~90px, background #20B2AA, white text (18px, weight 600), rounded.
- "CREDITS" button bottom-right (80% vertical, 85% horizontal from left), width ~90px, background #9370DB, white text (18px, weight 600), rounded.

**Key Elements:** Floating decorative bubbles (6–8) in soft pastel colors distributed across the screen, drifting slowly (suggest animation via opacity variation). Small copyright text at very bottom (12px, #FFFFFF, 60% opacity).

---

### ThemeSelectScreen

**Background Used:** backgrounds/bg_main.png

**Header/Title:** "SELECT THEME" centered at top (10% vertical), 48px, bold white (#FFFFFF) with drop shadow.

**Theme Cards:** Three equally-spaced horizontal cards arranged vertically (30%, 50%, 70% vertical positions):
- **Ocean Card:** Uses a miniature version of bg_ocean.png as background. "OCEAN" label centered in white (32px, bold). Subtle gradient overlay (blue tint, 20% opacity). Unlocked state shows bright white border; locked state shows darker border with a lock icon.
- **Space Card:** Uses a miniature version of bg_space.png. "SPACE" label in white (32px, bold). Subtle gradient overlay (purple tint, 20% opacity).
- **Candy Card:** Uses a miniature version of bg_candy.png. "CANDY" label in white (32px, bold). Subtle gradient overlay (pink tint, 20% opacity).

Each card is tappable and displays a subtle scale-up animation on hover. Cards show stars earned (1–3 star icons) at the bottom-right of each card if theme is unlocked.

**Buttons:**
- "BACK" button bottom-left (85% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

### LevelSelectScreen (Theme Variants)

Due to theme-specific visual styling, three separate screens exist:

---

#### level_select_ocean.png

**Background Used:** backgrounds/bg_ocean.png

**Header/Title:** "OCEAN — LEVELS" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Level Grid:** A 5×6 grid of level buttons (6 rows, 5 columns per row = 30 levels total) occupying the central 70% of the screen (12% to 82% vertical, 10% to 90% horizontal). Each level button:
- Size: ~60×60px with 8px spacing between buttons
- Unlocked levels: Background gradient from #1E90FF to #20B2AA, centered number (20px, bold white)
- Locked levels: Darker background (#0B3D6D), number in gray (60% opacity), small lock icon overlay
- Completed levels with stars: 1–3 small star icons (#FFD700) displayed above the level number
- Hover/tap state: subtle glow (#00D9FF, 6px blur)

**Buttons:**
- "BACK" button bottom-left (90% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

#### level_select_space.png

**Background Used:** backgrounds/bg_space.png

**Header/Title:** "SPACE — LEVELS" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Level Grid:** Same layout and spacing as Ocean variant (5×6 grid). Each level button:
- Unlocked levels: Background gradient from #FF6B9D to #00D9FF, centered number (20px, bold white)
- Locked levels: Darker background (#1A0033), number in gray (60% opacity), small lock icon overlay
- Completed levels with stars: 1–3 small star icons (#FFD700)
- Hover/tap state: subtle glow (#FF6B9D, 6px blur)

**Buttons:**
- "BACK" button bottom-left (90% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

#### level_select_candy.png

**Background Used:** backgrounds/bg_candy.png

**Header/Title:** "CANDY — LEVELS" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Level Grid:** Same 5×6 layout. Each level button:
- Unlocked levels: Background gradient from #FFB6C1 to #FFD700, centered number (20px, bold white)
- Locked levels: Darker background (#FFB6C1, 40% opacity), number in gray (60% opacity), small lock icon overlay
- Completed levels with stars: 1–3 small star icons (#FF69B4)
- Hover/tap state: subtle glow (#FF69B4, 6px blur)

**Buttons:**
- "BACK" button bottom-left (90% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

### GameScreen (Theme Variants)

Three separate screens to reflect unique visual themes:

---

#### game_ocean.png

**Background Used:** backgrounds/bg_ocean.png

**HUD Elements:**
- **Score Display:** Top-left (5% from top, 10% from left), white text (24px, bold) "Score: 0" on a semi-transparent dark blue rounded rectangle background (#0B3D6D, 70% opacity).
- **Level Indicator:** Top-center (5% from top, 45% from left), white text (18px) "Level 5" on semi-transparent background.
- **Moves/Turns Remaining:** Top-right (5% from top, 75% from left), white text (18px) "Moves: 10" on semi-transparent background.
- **Help Icon:** Top-right corner (5% from top, 92% from left), small circular button with "?" symbol, color #1E90FF.

**Game Grid:** Central play area (20% to 75% vertical), filled with a hexagonal/bubble arrangement. Bubbles rendered with glossy, glass-like appearance in theme colors (blues, teals, greens). Each bubble has a white highlight and subtle shadow for depth.

**Cannon:** Bottom-center (85% vertical), a stylized cannon barrel in dark teal (#0B3D6D) with white trim. Next-bubble preview displayed in a small circular frame above the cannon (82% vertical, 50% horizontal), showing the upcoming bubble color.

**Swipe Guide:** Subtle directional arrows or crosshair overlay on the cannon, fading in/out, indicating aiming feedback. Color: light cyan (#00D9FF) at 40% opacity.

**Combo Tracker:** Lower-left area (70% vertical, 10% from left), displays current combo count and multiplier (e.g., "3x Combo • 1.5x Multiplier") in small white text on a semi-transparent blue background.

---

#### game_space.png

**Background Used:** backgrounds/bg_space.png

**HUD Elements:**
- **Score Display:** Top-left (5% from top, 10% from left), white text (24px, bold) "Score: 0" on a semi-transparent dark purple rounded rectangle (#1A0033, 70% opacity).
- **Level Indicator:** Top-center (5% from top, 45% from left), white text (18px) "Level 5" on semi-transparent background.
- **Moves/Turns Remaining:** Top-right (5% from top, 75% from left), white text (18px) "Moves: 10" on semi-transparent background.
- **Help Icon:** Top-right corner (5% from top, 92% from left), small circular button with "?" symbol, color #FF6B9D.

**Game Grid:** Central play area (20% to 75% vertical), hexagonal bubble arrangement with futuristic, glowing bubbles in space theme colors (purples, pinks, cyans). Each bubble features a neon-like glow effect (#00D9FF or #FF6B9D, 6px blur).

**Cannon:** Bottom-center (85% vertical), a sleek, sci-fi cannon design in dark purple (#2B1B4D) with cyan accents and a glowing energy ring. Next-bubble preview in a circular frame above (82% vertical, 50% horizontal).

**Swipe Guide:** Crosshair overlay or energy beam visualization in cyan (#00D9FF) at 40% opacity, pulsing subtly to suggest technology.

**Combo Tracker:** Lower-left (70% vertical, 10% from left), displays "3x Combo • 1.5x Multiplier" in small white text on semi-transparent purple background.

---

#### game_candy.png

**Background Used:** backgrounds/bg_candy.png

**HUD Elements:**
- **Score Display:** Top-left (5% from top, 10% from left), white text (24px, bold) "Score: 0" on a semi-transparent pink rounded rectangle (#FFB6C1, 70% opacity).
- **Level Indicator:** Top-center (5% from top, 45% from left), white text (18px) "Level 5" on semi-transparent background.
- **Moves/Turns Remaining:** Top-right (5% from top, 75% from left), white text (18px) "Moves: 10" on semi-transparent background.
- **Help Icon:** Top-right corner (5% from top, 92% from left), small circular button with "?" symbol, color #FF69B4.

**Game Grid:** Central play area (20% to 75% vertical), hexagonal bubble arrangement with candy-themed, translucent bubbles in warm colors (pinks, golds, reds, pastels). Each bubble has a subtle candy-glass appearance with soft shadows.

**Cannon:** Bottom-center (85% vertical), a whimsical candy-themed cannon (resembles a candy dispenser or lollipop stick) in gold (#FFD700) and pink (#FF69B4) with white accents. Next-bubble preview in a circular candy-wrapper-styled frame above (82% vertical, 50% horizontal).

**Swipe Guide:** Playful arrow or sparkle particles in gold (#FFD700) at 40% opacity, trailing along the swipe path.

**Combo Tracker:** Lower-left (70% vertical, 10% from left), displays "3x Combo • 1.5x Multiplier" in small white text on semi-transparent pink background.

---

### LevelCompleteScreen (Theme Variants)

Three screens reflecting theme aesthetics:

---

#### level_complete_ocean.png

**Background Used:** backgrounds/bg_ocean.png with a semi-transparent overlay (black, 20% opacity) to dim the background and focus attention on the modal.

**Modal Panel:** Centered white rounded rectangle (20% to 80% vertical, 15% to 85% horizontal) with a subtle drop shadow.

**Header:** "LEVEL COMPLETE!" text (40px, bold, color #1E90FF) centered near the top of the modal (25% vertical).

**Star Display:** Three large stars (5cm each, rendered in gold #FFD700) centered horizontally at 40% vertical. Earned stars glow brightly; unearned stars are dim gray (#CCCCCC, 50% opacity).

**Score Display:** "Final Score: 2,450" (24px, bold, color #000000) centered at 50% vertical.

**Score Breakdown (optional):** Small secondary text (14px, color #666666) showing "Base: 1,500 | Combo Bonus: +950" at 55% vertical.

**Buttons:**
- "NEXT LEVEL" button centered at 70% vertical, width ~160px, background gradient (#1E90FF to #20B2AA), white text (20px, bold).
- "RETRY" button below at 78% vertical, width ~160px, background #FF6347, white text (20px, bold).
- "MENU" button top-right corner of modal (22% vertical, 82% horizontal), small background #666666, white text (14px).

---

#### level_complete_space.png

**Background Used:** backgrounds/bg_space.png with semi-transparent black overlay (20% opacity).

**Modal Panel:** Centered white rounded rectangle (20% to 80% vertical, 15% to 85% horizontal) with glow effect (#FF6B9D, 8px blur).

**Header:** "LEVEL COMPLETE!" text (40px, bold, color #FF6B9D) centered at 25% vertical.

**Star Display:** Three large gold stars (#FFD700) centered at 40% vertical. Earned stars have a neon glow (#FF6B9D, 6px blur); unearned stars are dim (#CCCCCC, 50% opacity).

**Score Display:** "Final Score: 2,450" (24px, bold, color #000000) at 50% vertical.

**Score Breakdown:** "Base: 1,500 | Combo Bonus: +950" (14px, color #666666) at 55% vertical.

**Buttons:**
- "NEXT LEVEL" button at 70% vertical, width ~160px, background gradient (#FF6B9D to #00D9FF), white text (20px, bold).
- "RETRY" button at 78% vertical, width ~160px, background #9370DB, white text (20px, bold).
- "MENU" button top-right of modal (22% vertical, 82% horizontal), small background #666666, white text (14px).

---

#### level_complete_candy.png

**Background Used:** backgrounds/bg_candy.png with semi-transparent black overlay (20% opacity).

**Modal Panel:** Centered rounded rectangle (20% to 80% vertical, 15% to 85% horizontal) with background gradient (white to pale pink #FFE4E1), subtle drop shadow.

**Header:** "LEVEL COMPLETE!" text (40px, bold, color #FF69B4) centered at 25% vertical.

**Star Display:** Three large stars (#FFD700) centered at 40% vertical with a sparkle glow effect. Earned stars bright; unearned dim gray (#CCCCCC, 50% opacity).

**Score Display:** "Final Score: 2,450" (24px, bold, color #000000) at 50% vertical.

**Score Breakdown:** "Base: 1,500 | Combo Bonus: +950" (14px, color #666666) at 55% vertical.

**Buttons:**
- "NEXT LEVEL" button at 70% vertical, width ~160px, background gradient (#FFB6C1 to #FFD700), white text (20px, bold).
- "RETRY" button at 78% vertical, width ~160px, background #FF69B4, white text (20px, bold).
- "MENU" button top-right of modal (22% vertical, 82% horizontal), small background #666666, white text (14px).

---

### GameOverScreen

**Background Used:** backgrounds/bg_main.png with a semi-transparent dark overlay (black, 40% opacity) to create a serious, defeated mood.

**Modal Panel:** Centered rounded rectangle (25% to 75% vertical, 15% to 85% horizontal), white background with drop shadow.

**Header:** "GAME OVER" text (44px, bold, color #FF6347) centered near top (28% vertical).

**Final Score Display:** "Final Score: 1,850" (32px, bold, color #000000) centered at 42% vertical.

**Stats Box:** Small secondary information (16px, color #666666) at 50% vertical:
- "Bubbles Matched: 47"
- "Highest Combo: 5x"
- "Lines Cleared: 12"

**Buttons:**
- "RETRY LEVEL" button centered at 65% vertical, width ~180px, background #1E90FF, white text (18px, bold).
- "CHOOSE THEME" button below at 73% vertical, width ~180px, background #20B2AA, white text (18px, bold).
- "MAIN MENU" button at 81% vertical, width ~180px, background #666666, white text (18px, bold).

---

### ComboGuideScreen

**Background Used:** backgrounds/bg_main.png

**Header/Title:** "COMBO GUIDE" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Content Area:** Scrollable section (15% to 80% vertical, 10% to 90% horizontal) with semi-transparent white background (rgba 255,255,255 at 90% opacity). Content includes:

**Section 1 — "What is a Combo?"**
- Explanation text (14px, color #333333): "Match 3+ bubbles of the same color to earn a combo. Chain multiple matches without gaps to build your multiplier!"
- Small illustrative icon or mini-animation suggestion showing 3 bubbles matching and disappearing.

**Section 2 — "Combo Multiplier Tiers"**
- Table-style layout with rows:
  - "1x Combo → 1.0x Multiplier"
  - "3x Combo → 1.5x Multiplier"
  - "5x Combo → 2.0x Multiplier"
  - "10x Combo → 3.0x Multiplier"
- Text in 13px, color #000000, with small colored circles (theme colors) to the left of each row.

**Section 3 — "Scoring Example"**
- "Base Match (4 bubbles): 100 points"
- "With 3x Combo: 100 × 1.5 = 150 points"
- Text in 13px, bold for clarity.

**Section 4 — "Tips"**
- "Plan ahead to chain matches!"
- "Watch the grid descent—time your shots!"
- Text in 13px, color #333333, italicized.

**Buttons:**
- "BACK" button bottom-left (88% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

### LeaderboardScreen

**Background Used:** backgrounds/bg_main.png

**Header/Title:** "LEADERBOARD" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Theme Tabs:** Three horizontal tabs just below header (12% vertical):
- "OCEAN" (background #1E90FF, white text, 14px)
- "SPACE" (background #FF6B9D, white text, 14px)
- "CANDY" (background #FFB6C1, white text, 14px)
- Active tab has a white bottom border (3px); inactive tabs are slightly dimmed (60% opacity).

**Leaderboard List:** Centered below tabs (18% to 75% vertical, 15% to 85% horizontal). Displays top 10 scores in rows. Each row includes:
- **Rank:** Left-aligned number (1–10) in bold (18px, color #000000) with a small medal/star icon for top 3 ranks
- **Player Name:** Next to rank (16px, color #333333), e.g., "Alice_Master"
- **Score:** Right-aligned bold number (18px, color theme-specific: blue for Ocean, pink for Space, pink for Candy) e.g., "15,450"
- **Date:** Small text below score (12px, color #999999) e.g., "2 days ago"

Rows have alternating subtle backgrounds (white and #F5F5F5 light gray) for readability. Small separator line between rows (1px, #CCCCCC).

**Buttons:**
- "BACK" button bottom-left (88% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

### SettingsScreen

**Background Used:** backgrounds/bg_main.png

**Header/Title:** "SETTINGS" centered at top (8% vertical), 44px, bold white (#FFFFFF) with drop shadow.

**Settings Sections:** Vertically stacked (18% to 75% vertical, 15% to 85% horizontal):

**Section 1 — "Audio"** (background pale gray #F5F5F5, padding 12px, rounded corners)
- Label "Music Volume" (14px, bold, color #000000)
- Toggle switch (right side) — ON/OFF state, color #1E90FF when ON
- Label "Sound Effects Volume" (14px, bold, color #000000)
- Toggle switch (right side) — ON/OFF state, color #1E90FF when ON

**Section 2 — "Language"** (background pale gray, padding 12px, rounded)
- Label "Language" (14px, bold, color #000000)
- Dropdown showing "English" (16px, color #333333) with small downward arrow

**Section 3 — "Progress"** (background pale gray, padding 12px, rounded)
- Label "Reset Progress" (14px, bold, color #000000)
- Small descriptive text below (12px, color #666666): "Clear all levels and scores. This cannot be undone."
- Button "RESET" (width ~100px, background #FF6347, white text, 14px, bold)

**Section 4 — "About"** (background pale gray, padding 12px, rounded)
- Label "Version" (14px, color #000000)
- Version number "1.0.0" (14px, color #666666)

**Buttons:**
- "BACK" button bottom-left (88% vertical, 10% horizontal), width ~80px, background #666666, white text (16px).

---

## 5. Export Checklist

All files are 480×854px (portrait) unless otherwise noted. Filenames must match exactly.

### App Icon
- icon_512.png (512×512px)

### Backgrounds
- backgrounds/bg_main.png (480×854px)
- backgrounds/bg_ocean.png (480×854px)
- backgrounds/bg_space.png (480×854px)
- backgrounds/bg_candy.png (480×854px)

### UI Screens
- mainmenu.png (480×854px)
- theme_select.png (480×854px)
- level_select_ocean.png (480×854px)
- level_select_space.png (480×854px)
- level_select_candy.png (480×854px)
- game_ocean.png (480×854px)
- game_space.png (480×854px)
- game_candy.png (480×854px)
- level_complete_ocean.png (480×854px)
- level_complete_space.png (480×854px)
- level_complete_candy.png (480×854px)
- game_over.png (480×854px)
- combo_guide.png (480×854px)
- leaderboard.png (480×854px)
- settings.png (480×854px)

**Total Files: 20**

---

**END OF BRIEF**
