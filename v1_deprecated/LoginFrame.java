import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {
    private JTextField player1NameInput;
    private JTextField player2NameInput;
    private JTextField playTimeInput;
    private JButton startGameButton;

    LoginFrame() {
        setTitle("Chess Game Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel player1Panel = new JPanel();
        player1Panel.add(new JLabel("Player 1 Name: "));
        player1NameInput = new JTextField(15);
        player1Panel.add(player1NameInput);

        JPanel player2Panel = new JPanel();
        player2Panel.add(new JLabel("Player 2 Name: "));
        player2NameInput = new JTextField(15);
        player2Panel.add(player2NameInput);

        JPanel playTimePanel = new JPanel();
        playTimePanel.add(new JLabel("Theme: "));
        playTimeInput = new JTextField(5);
        playTimePanel.add(playTimeInput);

        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(this);

        JPanel mainPanel = new JPanel(new GridLayout(4, 1));
        mainPanel.add(player1Panel);
        mainPanel.add(player2Panel);
        mainPanel.add(playTimePanel);
        mainPanel.add(startGameButton);
        add(mainPanel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startGameButton) {
            String player1Name = player1NameInput.getText();
            String player2Name = player2NameInput.getText();
            double playTime = Double.parseDouble(playTimeInput.getText());
            Chess chess = new Chess(480, player1Name, player2Name, playTime);
            // set the promotion choice for each player's pawn
            //chess.player1.setPromotionChoice(PawnPromotionDialog.showDialog(chess.frame, chess.player1));
            //chess.player2.setPromotionChoice(PawnPromotionDialog.showDialog(chess.frame, chess.player2));
            dispose();
        }
    }
}