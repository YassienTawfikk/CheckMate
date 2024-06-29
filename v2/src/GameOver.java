import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GameOver extends JFrame {
    private JTextField player1NameInput;
    private JTextField player2NameInput;
    private JComboBox<String> playTimeComboBox;
    private JButton startGameButton;
    private BufferedImage backgroundImage;
    GameOver() {
        setTitle("Game Over");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Load the background image
        try {
            backgroundImage = ImageIO.read(new File("v2/src/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a custom JPanel that draws the background image
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Set the content pane to the custom JPanel
        setContentPane(panel);
        JPanel gameOverPanel = new JPanel();
        JLabel name1Label = new JLabel("Game Over");
        name1Label.setForeground(Color.WHITE);
        name1Label.setFont(new Font("Arial", Font.PLAIN, 36));
        name1Label.setBackground(new Color(0, 0, 0, 0));
        name1Label.setOpaque(false);
        gameOverPanel.setBackground(new Color(0, 0, 0, 0));
        gameOverPanel.add(name1Label);
        gameOverPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        gameOverPanel.setForeground(Color.WHITE);


        JPanel mainPanel = new JPanel(new GridLayout(1, 1));
        mainPanel.add(gameOverPanel);

        add(mainPanel);

        setVisible(true);
    }

}