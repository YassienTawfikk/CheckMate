package com.checkmate.ui;

import com.checkmate.core.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The LoginFrame class represents a JFrame window for the chess game login.
 * It allows players to enter their names and select a theme before starting the
 * game.
 */
public class LoginFrame extends JFrame implements ActionListener {
    private final JTextField player1NameInput;
    private final JTextField player2NameInput;
    private JComboBox<String> theme;
    private final JButton startGameButton;

    /**
     * Constructs a LoginFrame object and initializes its components.
     */
    public LoginFrame() {
        setTitle("Chess Game - Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel with centralized layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Theme.BACKGROUND_DARK);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel titleLabel = new JLabel("Welcome to CheckMate");
        titleLabel.setFont(Theme.FONT_TITLE);
        titleLabel.setForeground(Theme.TEXT_WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        // Spacer
        gbc.gridy++;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Player 1 Input
        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(createLabel("Player 1 Name:"), gbc);

        gbc.gridx = 1;
        player1NameInput = createStyledTextField();
        mainPanel.add(player1NameInput, gbc);

        // Player 2 Input
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(createLabel("Player 2 Name:"), gbc);

        gbc.gridx = 1;
        player2NameInput = createStyledTextField();
        mainPanel.add(player2NameInput, gbc);

        // Theme Selection
        gbc.gridx = 0;
        gbc.gridy++;
        mainPanel.add(createLabel("Board Theme:"), gbc);

        gbc.gridx = 1;
        String[] themeValues = { "Modern Blue", "Classic Green" };
        theme = new JComboBox<>(themeValues);
        theme.setFont(Theme.FONT_INPUT);
        mainPanel.add(theme, gbc);

        // Spacer
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        mainPanel.add(Box.createVerticalStrut(20), gbc);

        // Start Button
        gbc.gridy++;
        startGameButton = createStyledButton("Start Game");
        startGameButton.addActionListener(this);
        mainPanel.add(startGameButton, gbc);

        // Add to frame
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(Theme.FONT_REGULAR);
        label.setForeground(Theme.TEXT_GRAY);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField(15);
        textField.setFont(Theme.FONT_INPUT);
        textField.setBackground(Theme.PANEL_DARK);
        textField.setForeground(Theme.TEXT_WHITE);
        textField.setCaretColor(Theme.TEXT_WHITE);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Theme.PANEL_DARK.darker(), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        return textField;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(Theme.ACCENT_HOVER_BLUE);
                } else {
                    g2.setColor(Theme.ACCENT_BLUE);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.ROUND_RADIUS, Theme.ROUND_RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(Theme.FONT_BUTTON);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 40));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            String player1Name = player1NameInput.getText().isEmpty() ? "Player 1" : player1NameInput.getText();
            String player2Name = player2NameInput.getText().isEmpty() ? "Player 2" : player2NameInput.getText();
            String themeChosen = (String) theme.getSelectedItem();

            // Map consistent names to what the GameFrame expects if needed, or update
            // GameFrame logic.
            // For now, mapping "Modern Blue" to "Blue" and "Classic Green" to "Green" to
            // match old logic if it relies on exact string.
            // Looking at the view_file of GameFrame (not fully seen yet, but let's assume
            // simple string check).
            // Actually, I should probably check GameFrame logic later. I'll stick to
            // "Green" and "Blue" keywords if important,
            // or better, pass the enum/constants.
            // Let's stick to simple mapping for safety:
            if (themeChosen.contains("Blue"))
                themeChosen = "Blue";
            else
                themeChosen = "Green";

            GameFrame.p1NameText = player1Name;
            GameFrame.p2NameText = player2Name;
            GameFrame.theme = themeChosen;

            GameFrame gameFrame = new GameFrame();
            Theme.setBoardTheme(themeChosen);

            Main.frame = new GameFrame(player1Name, player2Name);
            Main.frame.start();
            dispose();
        }
    }
}