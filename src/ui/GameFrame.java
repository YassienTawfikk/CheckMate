
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class GameFrame extends JFrame {
    private Board board;
    private JPanel infoCard;
    private JPanel p1info;
    private JPanel p2info;
    private JPanel p2timeFrame;
    private JPanel p1timeFrame;
    private JLabel p1Timer;
    private JLabel p2Timer;
    private JPanel p2NameFrame;
    private JPanel p1NameFrame;
    private JLabel p2Name;
    private JLabel p1Name;
    private JPanel p1Eaten;
    private JPanel p2Eaten;
    private JPanel p1EatenR1;
    private JPanel p1EatenR2;
    private JPanel p1EatenR3;
    private JPanel p1EatenR4;
    private JPanel p2EatenR1;
    private JPanel p2EatenR2;
    private JPanel p2EatenR3;
    private JPanel p2EatenR4;

    public static String[] p1EatenPieces = new String[16];
    public static String[] p2EatenPieces = new String[16];

    public static String p1NameText, p2NameText, theme;

    public GameFrame() {
        // Set the default close operation to exit the application when the window is
        // closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set the title of the window
        setTitle("Check-Mate");

        // Set the background color Background
        getContentPane().setBackground(new Color(34, 32, 29));

        // Create a new JPanel with a GridBagLayout as the container for the Board panel
        JPanel boardPanel = new JPanel(new GridBagLayout());
        boardPanel.setOpaque(false);
        boardPanel.setBorder(BorderFactory.createEmptyBorder()); // remove border

        // Create a new JPanel with a FlowLayout as the container for the Board panel
        JPanel boardContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        boardContainer.setOpaque(false);
        boardContainer.setBorder(BorderFactory.createEmptyBorder()); // remove border

        // Create a new Board object
        board = new Board();

        // Add the board to the board container
        boardContainer.add(board);

        // Add the board container to the center of the board panel using GridBagLayout
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.fill = GridBagConstraints.CENTER;
        boardPanel.add(boardContainer, c);

        // Add the board panel to the center of the content pane
        add(boardPanel, BorderLayout.CENTER);

        // Create a new JPanel for the info card
        infoCard.setPreferredSize(new Dimension(200, getHeight())); // set preferred size
        infoCard.setLayout(new BoxLayout(infoCard, BoxLayout.Y_AXIS)); // align children vertically

        // Add components to the info card
        p1Name.setText(p1NameText);
        p2Name.setText(p2NameText);

        infoCard.add(p1info);
        infoCard.add(p2info);

        // p1Eaten.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        // p2Eaten.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
        p1Eaten.setLayout(new BoxLayout(p1Eaten, BoxLayout.Y_AXIS));
        p2Eaten.setLayout(new BoxLayout(p2Eaten, BoxLayout.Y_AXIS));
        p1Eaten.setMaximumSize(new Dimension(400, Integer.MAX_VALUE)); // Set maximum width of panel
        p2Eaten.setMaximumSize(new Dimension(400, Integer.MAX_VALUE)); // Set maximum width of panel
        // p2Eaten.setLayout(new BoxLayout(p2Eaten, BoxLayout.X_AXIS));

        p1Eaten.setOpaque(false);
        p2Eaten.setOpaque(false);
        p1EatenR1.setOpaque(false);
        p2EatenR2.setOpaque(false);
        p1EatenR3.setOpaque(false);
        p2EatenR4.setOpaque(false);
        p1EatenR1.setOpaque(false);
        p2EatenR1.setOpaque(false);
        p1EatenR2.setOpaque(false);
        p2EatenR3.setOpaque(false);
        p1EatenR4.setOpaque(false);
        // p1Eaten.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // p2Eaten.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Add the info card to the east (right) of the content pane
        add(infoCard, BorderLayout.EAST);
        repaint();

        // Set the space between the board and the info card to zero
        ((BorderLayout) getContentPane().getLayout()).setHgap(0);

        // Pack the JFrame to its minimum size
        pack();

        // Center the JFrame on the screen
        setLocationRelativeTo(null);

        // Set the maximum size of the JFrame to the preferred size
        setMaximumSize(getPreferredSize());

        // Set the minimum size of the JFrame to the size of the board and info card
        setMinimumSize(new Dimension(8 * 85 + 175, 8 * 85));
    }

    public void start() {
        // Set the JFrame to be visible
        setVisible(true);
    }

    public void drawEatenPiece(String eatenPiece, boolean isWhite) {
        // counting the number of row to be added
        int count = 0;
        for (int i = 0; i < (!isWhite ? GameFrame.p1EatenPieces.length : GameFrame.p2EatenPieces.length); i++)
            if ((!isWhite ? GameFrame.p1EatenPieces[i] : GameFrame.p2EatenPieces[i]) != null)
                count++;
        // Create a new rowPanel with a FlowLayout
        JPanel rowPanel;
        if (isWhite)
            if (count <= 4)
                rowPanel = p1EatenR1;
            else if (count <= 8)
                rowPanel = p1EatenR2;
            else if (count <= 12)
                rowPanel = p1EatenR3;
            else
                rowPanel = p1EatenR4;
        else if (count <= 4)
            rowPanel = p2EatenR1;
        else if (count <= 8)
            rowPanel = p2EatenR2;
        else if (count <= 12)
            rowPanel = p2EatenR3;
        else
            rowPanel = p2EatenR4;

        rowPanel.setOpaque(false);
        rowPanel.setBackground(new Color(33, 37, 55));
        rowPanel.setMaximumSize(new Dimension(300, 30)); // Set maximum width and height of rowPanel
        JLabel label = new JLabel();
        ImageIcon icon;
        String color = isWhite ? "white" : "black";
        icon = new ImageIcon("resources/images/Pieces/" + color + "/" + eatenPiece + ".png");
        Image image = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        label.setIcon(icon);
        label.setOpaque(false);
        label.setBorder(BorderFactory.createEmptyBorder()); // set null border
        label.setPreferredSize(new Dimension(30, 30)); // Set preferred size of JLabel
        rowPanel.add(label);
        repaint();
        // Pack the JFrame to its minimum size
        pack();
    }
}
