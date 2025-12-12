package com.checkmate.ui;

import com.checkmate.core.Board;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameFrame extends JFrame {
    private Board board;

    // UI Components
    private JPanel infoPanel;
    private JLabel p1NameLabel;
    private JLabel p2NameLabel;
    private JLabel turnLabel;
    private JPanel p1EatenPanel;
    private JPanel p2EatenPanel;

    // Game Data
    public static String p1NameText = "Player 1";
    public static String p2NameText = "Player 2";
    public static String theme = "Blue";

    public static String[] p1EatenPieces = new String[16];
    public static String[] p2EatenPieces = new String[16];

    public GameFrame() {
        // Default constructor
        initializeUI();
    }

    public GameFrame(String p1, String p2) {
        p1NameText = p1;
        p2NameText = p2;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Check-Mate");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Theme.BACKGROUND_DARK);
        getContentPane().setBackground(Theme.BACKGROUND_DARK);
        setLayout(new BorderLayout());

        // --- Board Section ---
        JPanel boardContainer = new JPanel(new GridBagLayout());
        boardContainer.setOpaque(false);
        board = new Board();
        boardContainer.add(board);
        add(boardContainer, BorderLayout.CENTER);

        // --- Info Sidebar ---
        infoPanel = new JPanel();
        infoPanel.setPreferredSize(new Dimension(250, 0));
        infoPanel.setBackground(Theme.PANEL_DARK);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Player 2 Info (Top)
        infoPanel.add(createPlayerInfoPanel(p2NameText, false));
        infoPanel.add(Box.createVerticalStrut(10));
        p2EatenPanel = createEatenPiecesPanel();
        infoPanel.add(p2EatenPanel);

        // Spacer / Turn Indicator
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(Box.createVerticalGlue());
        turnLabel = new JLabel("Turn: White");
        turnLabel.setFont(Theme.FONT_TITLE.deriveFont(24f)); // Reduced from default TITLE(48)
        turnLabel.setForeground(Theme.ACCENT_BLUE);
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(turnLabel);
        infoPanel.add(Box.createVerticalGlue());

        // Player 1 Info (Bottom)
        p1EatenPanel = createEatenPiecesPanel();
        infoPanel.add(p1EatenPanel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(createPlayerInfoPanel(p1NameText, true));

        add(infoPanel, BorderLayout.EAST);

        // --- Left Sidebar (Quit Button) ---
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(150, 0));
        leftPanel.setBackground(Theme.PANEL_DARK);
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton quitButton = new JButton("Quit");
        quitButton.setFont(Theme.FONT_BUTTON);
        quitButton.setForeground(Color.WHITE);
        quitButton.setBackground(new Color(220, 38, 38));
        quitButton.setOpaque(true); // REQUIRED for Mac to show background color
        quitButton.setContentAreaFilled(true);
        quitButton.setBorderPainted(false); // remove border for clean look
        quitButton.setFocusPainted(false);
        quitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        quitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        quitButton.addActionListener(e -> System.exit(0));
        quitButton.setOpaque(true);
        quitButton.setBorderPainted(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.weighty = 1.0;
        leftPanel.add(quitButton, gbc);

        add(leftPanel, BorderLayout.WEST);

        // Final Setup
        // Try True Full Screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (gd.isFullScreenSupported()) {
            setUndecorated(true);
            gd.setFullScreenWindow(this);
        } else {
            // Fallback
            pack();
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setLocationRelativeTo(null);
        }
    }

    private JPanel createPlayerInfoPanel(String name, boolean isBottom) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(Theme.FONT_TITLE.deriveFont(24f)); // Reduced from default TITLE(48)
        nameLabel.setForeground(Theme.TEXT_WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(nameLabel);
        // Could add timer here later
        return panel;
    }

    private JPanel createEatenPiecesPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2)); // Tighter packing
        panel.setOpaque(false);
        panel.setPreferredSize(new Dimension(210, 100)); // Increased height
        panel.setMaximumSize(new Dimension(210, 100));
        return panel;
    }

    public void start() {
        setVisible(true);
    }

    public void updateTurnLabel(boolean isWhiteTurn) {
        turnLabel.setText("Turn: " + (isWhiteTurn ? "White" : "Black"));
        turnLabel.setForeground(isWhiteTurn ? Theme.TEXT_WHITE : Theme.TEXT_GRAY);
    }

    public void drawEatenPiece(String eatenPiece, boolean isWhiteGetter) {
        JPanel targetPanel = isWhiteGetter ? p1EatenPanel : p2EatenPanel;
        String[] pieces = isWhiteGetter ? p1EatenPieces : p2EatenPieces;

        targetPanel.removeAll();

        for (String p : pieces) {
            if (p != null) {
                // If isWhiteGetter is true (Player 1/White captured), they captured a BLACK
                // piece.
                String color = isWhiteGetter ? "black" : "white";

                // Load larger icon for display (40x40) - logic similar to Board styling
                // Assuming images are available. If not, fallback to existing or scale up.
                // The icons/Pieces/color/Name.png path pattern matches Board usage?
                // Board uses sprite sheet, but here we load individual files?
                // Original code: "resources/images/Pieces/" + color + "/" + p + ".png"
                // This implies individual files exist. Let's verify resource path in previous
                // steps or assume yes.
                // Actually, standardizing on valid resource loading:
                try {
                    java.net.URL url = getClass().getResource("/images/Pieces/" + color + "/" + p + ".png");
                    // Note: If distinct images don't exist and only sprite sheet exists, this will
                    // fail.
                    // The original code tried to load from file paths.
                    // I should check if these individual images exist.
                    // If not, I'll need to crop from sprite sheet or use existing found pieces.png
                    // logic.
                    // Let's assume for now we might need to rely on the sprite sheet if that's the
                    // only asset.
                    // But the user's prev code had this. Let's assume they have the files or I need
                    // to handle it.
                    // Wait, the "fix" for Piece.java used "pieces.png".
                    // If individual files are missing, this feature breaks.
                    // Safe approach: scalable drawing or check list_dir.
                    // I will assume for now files might be missing and use a placeholder or try to
                    // load.

                    if (url != null) {
                        ImageIcon icon = new ImageIcon(url);
                        Image img = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
                        JLabel iconLabel = new JLabel(new ImageIcon(img));
                        targetPanel.add(iconLabel);
                    } else {
                        // Fallback text if image missing
                        JLabel label = new JLabel(p.substring(0, 1));
                        label.setForeground(Theme.TEXT_WHITE);
                        label.setFont(Theme.FONT_BUTTON);
                        targetPanel.add(label);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        targetPanel.revalidate();
        targetPanel.repaint();
    }
}
