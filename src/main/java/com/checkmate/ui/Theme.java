package com.checkmate.ui;

import java.awt.Color;
import java.awt.Font;

public class Theme {

    // Colors
    public static final Color BACKGROUND_DARK = new Color(30, 30, 30); // Dark Gray
    public static final Color PANEL_DARK = new Color(45, 45, 45); // Slightly Lighter Gray
    public static final Color TEXT_WHITE = new Color(240, 240, 240);
    public static final Color TEXT_GRAY = new Color(180, 180, 180);
    public static final Color ACCENT_BLUE = new Color(59, 130, 246); // Modern Blue
    public static final Color ACCENT_HOVER_BLUE = new Color(37, 99, 235); // Darker Blue for hover

    // Board Colors
    // Board Colors
    public static Color TILE_LIGHT = new Color(238, 238, 210); // Classic Light
    public static Color TILE_DARK = new Color(100, 116, 139); // Slate Blue/Gray
    public static Color MOVE_HIGHLIGHT = new Color(100, 255, 100, 150); // Transparent Green

    public static void setBoardTheme(String themeName) {
        if (themeName.contains("Green")) {
            TILE_LIGHT = new Color(238, 238, 210);
            TILE_DARK = new Color(118, 150, 86); // Classic Chess.com Green
            MOVE_HIGHLIGHT = new Color(246, 246, 105, 180); // Yellowish highlight
        } else if (themeName.contains("Blue")) {
            TILE_LIGHT = new Color(238, 238, 210);
            TILE_DARK = new Color(100, 116, 139); // Slate Blue
            MOVE_HIGHLIGHT = new Color(100, 255, 100, 150); // Greenish highlight
        } else if (themeName.contains("Brown")) {
            TILE_LIGHT = new Color(240, 217, 181);
            TILE_DARK = new Color(181, 136, 99); // Wood brown
            MOVE_HIGHLIGHT = new Color(100, 255, 100, 150);
        } else if (themeName.contains("Gray") || themeName.contains("Dark")) {
            TILE_LIGHT = new Color(140, 140, 140);
            TILE_DARK = new Color(80, 80, 80);
            MOVE_HIGHLIGHT = new Color(100, 255, 100, 150);
        }
    }

    // Fonts
    public static Font FONT_TITLE;
    public static Font FONT_REGULAR;
    public static Font FONT_BUTTON;
    public static Font FONT_INPUT;

    static {
        try {
            // Load custom font
            Font font = Font.createFont(Font.TRUETYPE_FONT,
                    java.util.Objects.requireNonNull(Theme.class.getResourceAsStream("/fonts/Felix.ttf")));
            java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            // Initialize fonts with sizes matching usages
            FONT_TITLE = font.deriveFont(Font.BOLD, 48f); // Was 24, but 48 looks better for title
            FONT_REGULAR = font.deriveFont(Font.PLAIN, 18f); // Was 16
            FONT_BUTTON = font.deriveFont(Font.BOLD, 14f);
            FONT_INPUT = font.deriveFont(Font.PLAIN, 14f);

        } catch (Exception e) {
            e.printStackTrace();
            // Fallback fonts
            FONT_TITLE = new Font("SansSerif", Font.BOLD, 48);
            FONT_REGULAR = new Font("SansSerif", Font.PLAIN, 18);
            FONT_BUTTON = new Font("SansSerif", Font.BOLD, 14);
            FONT_INPUT = new Font("SansSerif", Font.PLAIN, 14);
        }
    }

    // Shapes
    public static final int ROUND_RADIUS = 15;
}
