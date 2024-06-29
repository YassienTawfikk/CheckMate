import javax.swing.JOptionPane;

public class GameOver {
    GameOver(String playerName){
        String message = playerName + "has owned";
        JOptionPane.showMessageDialog(Chess.frame, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);

        // Wait for the user to click the "OK" button
        int option = JOptionPane.showConfirmDialog(Chess.frame, "Do you want to close the game?", "Confirm Exit", JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION) {
            Chess.frame.dispose();
        }
    }
}
