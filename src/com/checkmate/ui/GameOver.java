package com.checkmate.ui;

import com.checkmate.core.Main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import java.io.IOException;

public class GameOver extends JFrame implements ActionListener {

    private JButton playAgainButton;
    private JButton exitButton;
    private Image backgroundImage;

    public GameOver(String winner) {
        setTitle("Game Over");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setUndecorated(true); // Modern floating dialog look

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(Theme.BACKGROUND_DARK);
        mainPanel.setBorder(BorderFactory.createLineBorder(Theme.ACCENT_BLUE, 2));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        JLabel titleLabel = new JLabel("Checkmate!");
        titleLabel.setFont(Theme.FONT_TITLE.deriveFont(32f));
        titleLabel.setForeground(Theme.TEXT_WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy++;
        JLabel winnerLabel = new JLabel(winner + " Wins!");
        winnerLabel.setFont(Theme.FONT_TITLE.deriveFont(24f));
        winnerLabel.setForeground(Theme.ACCENT_BLUE);
        winnerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(winnerLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.weightx = 0.5;

        playAgainButton = createStyledButton("Play Again");
        playAgainButton.addActionListener(this);
        mainPanel.add(playAgainButton, gbc);

        gbc.gridx = 1;
        exitButton = createStyledButton("Exit");
        exitButton.setBackground(new Color(220, 38, 38)); // Red for exit
        exitButton.addActionListener(this);
        mainPanel.add(exitButton, gbc);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), Theme.ROUND_RADIUS, Theme.ROUND_RADIUS);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setFont(Theme.FONT_BUTTON);
        button.setBackground(Theme.ACCENT_BLUE);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 40));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playAgainButton) {
            Main.frame.dispose(); // Close current game
            dispose(); // Close
            try {
                backgroundImage = ImageIO.read(
                        java.util.Objects.requireNonNull(getClass().getResourceAsStream("/images/background.jpg")));
            } catch (IOException ex) { // Changed variable name to avoid conflict if 'e' was used elsewhere
                ex.printStackTrace();
            }
            new LoginFrame(); // Restart
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }
}