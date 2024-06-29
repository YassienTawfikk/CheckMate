import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The LoginFrame class represents a JFrame window for the chess game login.
 * It allows players to enter their names and select a theme before starting the game.
 */
public class LoginFrame extends JFrame implements ActionListener {
    private final JTextField player1NameInput;
    private final JTextField player2NameInput;
    private JComboBox<String> theme;
    private final JButton startGameButton;
    private BufferedImage backgroundImage;

    /**
     * Constructs a LoginFrame object and initializes its components.
     */
    public LoginFrame() {
        setTitle("Chess Game Login");
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
        setContentPane(panel);

        // Player 1 Panel
        JPanel player1Panel = createPlayerPanel("Player 1 Name: ");
        player1NameInput = new JTextField(15);
        player1Panel.add(player1NameInput);

        // Player 2 Panel
        JPanel player2Panel = createPlayerPanel("Player 2 Name: ");
        player2NameInput = new JTextField(15);
        player2Panel.add(player2NameInput);

        // Theme Panel
        JPanel themePanel = createThemePanel();

        // Start Game Button
        startGameButton = createStartGameButton();

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);
        mainPanel.add(themePanel);
        mainPanel.add(startGameButton);

        // Create a wrapper panel with BorderLayout
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);

        // Add the wrapper panel to the content pane
        getContentPane().add(wrapperPanel);

        // Center the frame on the screen
        setLocationRelativeTo(null);

        setVisible(true);
    }


    /**
     * Creates a JPanel for player input with the given label.
     *
     * @param labelName the label for the player input field
     * @return the created JPanel
     */
    private JPanel createPlayerPanel(String labelName) {
        JPanel playerPanel = new JPanel();
        JLabel nameLabel = new JLabel(labelName);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        playerPanel.add(nameLabel);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        playerPanel.setOpaque(true);
        playerPanel.setBackground(Color.BLACK);
        playerPanel.setForeground(Color.WHITE);
        return playerPanel;
    }

    /**
     * Creates a JPanel for selecting the theme.
     *
     * @return the created JPanel
     */
    private JPanel createThemePanel() {
        JPanel themePanel = new JPanel();
        themePanel.setBackground(Color.BLACK);
        JLabel themeLabel = new JLabel("Theme: ");
        themeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        themeLabel.setForeground(Color.WHITE);
        themePanel.add(themeLabel);

        String[] themeValues = {"Green", "Blue"};
        theme = new JComboBox<>(themeValues);
        themePanel.add(theme);

        return themePanel;
    }

    /**
     * Creates a JButton for starting the game.
     *
     * @return the created JButton
     */
    private JButton createStartGameButton() {
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(this);
        startButton.setFont(new Font("Arial", Font.PLAIN, 18));
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return startButton;
    }

    /**
     * Handles the actionPerformed event for the start game button.
     *
     * @param e the ActionEvent object
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            String player1Name = player1NameInput.getText().isEmpty() ? "Player 1" : player1NameInput.getText();
            String player2Name = player2NameInput.getText().isEmpty() ? "Player 2" : player2NameInput.getText();
            String themeChosen = (String) theme.getSelectedItem();

            GameFrame.p1NameText = player1Name;
            GameFrame.p2NameText = player2Name;
            GameFrame.theme = themeChosen;

            // Create a new GameFrame object
            GameFrame gameFrame = new GameFrame();
            Main.frame = gameFrame;

            // Call the start method to make the GameFrame visible
            gameFrame.start();

            // Set the promotion choice for each player's pawn
            dispose();
        }
    }
}