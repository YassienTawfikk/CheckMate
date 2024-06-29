import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Chess {
    static Player activePlayer;
    static int[][] oldPieces;
    static int frameSize, blocksize;
    public static JButton activePiece;
    static Frame frame;
    static Block blocks[][] = new Block[8][8];
    static Player player1;
    static Player player2;
    static JLabel label1;
    static JLabel label2;
    static int margin = 150;
    static double time = 5.50;

    // drawing the table constructor
    Chess(int frameSizeP, String player1Name, String player2Name, double playTime) {

        time = playTime;
        frameSize = frameSizeP;
        blocksize = (Chess.frameSize - 10) / 8;
        frame = new Frame(frameSize, "chess Game");

        // initialize the two players with its name   
        player1 = new Player(player1Name, Chess.time);
        player2 = new Player(player2Name, Chess.time);

        // drawing the filled pieces that have not null pieces
        //looping over rows
        for (int row = 0; row <= 7; row++) {
            // condition to be in the rows 1,2,7,8
            if (row < 2 || row > 5) {
                // give the refernce to the player which will owe the piece
                Player player = row < 2 ? player1 : (row > 5 ? player2 : null);
                //looping over columns
                for (int col = 0; col <= 7; col++) {
                    Color color = (col + row)%2 == 1 ? color = new Color(118, 150, 86) : new Color(238, 238, 210);
                    Block label = new Block(color, (col) % 8, row, null);
                    Chess.blocks[row][col] = label;
                    Piece newPiece = null;
                    if (row == 0 || row == 7)
                        switch (col%=8) {
                            case 0:
                                newPiece = new Rook(col, row, player, label);
                                break;

                            case 1:
                                newPiece = new Knight(col, row, player, label);
                                break;

                            case 2:
                                newPiece = new Bishop(col, row, player, label);
                                break;
                            

                            case 3:
                                newPiece = new Queen(col, row, player, label);
                                break;
                            

                            case 4: 
                                newPiece = new King(col, row, player, label);
                                break;

                            case 5:
                                newPiece = new Bishop(col, row, player, label);
                                break;
                            
                            case 6: 
                                newPiece = new Knight(col, row, player, label);
                                break;

                            case 7: 
                                newPiece = new Rook(col, row, player, label);
                                break;  
                        }    
                    else if (row == 1 || row == 6)
                        newPiece = new Pawn(col, row, player, label);
                    //drawing the piece on the board
                    frame.add(newPiece);
                    
                }
            }
        }

        // drawing the unfilled pieces that have null pieces
        for (int row = 2; row <= 5; row++) {
            for (int col = 0; col <= 7; col++) {
                Color color = (col + row) % 2 == 1 ? new Color(118, 150, 86) : new Color(238, 238, 210);
                Block label = new Block(color, (col) % 8, row, null);
                Chess.blocks[row][col] = label;
                new Piece(row, col, null, null, label);
            }
        }

        // drawing the blocks on the table
        for (int row = 0; row <= 7; row++)
            for (int col = 0; col <= 7; col++)
                frame.add(blocks[row][col]);

        // drawing the upper and lower margin for player names
        JLabel label1 = new JLabel();
        JLabel label2 = new JLabel();
        this.label1 = label1;
        this.label2 = label2;
        label1.setForeground(new Color(255, 255, 255));
        label2.setForeground(new Color(255, 255, 255));
        label1.setBackground(new Color(49, 46, 43));
        label2.setBackground(new Color(49, 46, 43));
        label1.setBounds(0, 0, frameSize, 50);
        label2.setBounds(0, frameSize + Chess.margin / 2 - 16, frameSize, Chess.margin / 2);
        label1.setOpaque(true);
        label2.setOpaque(true);

        JLabel name1 = new JLabel(player1.name);
        JLabel name2 = new JLabel(player2.name);
        name1.setForeground(new Color(255, 255, 255));
        name2.setForeground(new Color(255, 255, 255));
        name1.setBackground(new Color(49, 46, 43));
        name2.setBackground(new Color(49, 46, 43));
        name1.setBounds(10, 5, 100, 30);
        name2.setBounds(10, 5, 100, 30);
        name1.setOpaque(true);
        name2.setOpaque(true);
        name1.setFont(new Font("Felix.ttf", Font.BOLD, 20));
        name2.setFont(new Font("Felix.ttf", Font.BOLD, 20));

        JLabel timer1 = new JLabel(caleTimeString(Double.valueOf(player1.remTime).toString()), JLabel.CENTER);
        JLabel timer2 = new JLabel(caleTimeString(Double.valueOf(player2.remTime).toString()), JLabel.CENTER);
        timer1.setFont(new Font("Felix.ttf", Font.BOLD, 18));
        timer2.setFont(new Font("Felix.ttf", Font.BOLD, 18));
        timer1.setForeground(new Color(125, 129, 127));
        timer2.setForeground(new Color(125, 129, 127));
        timer1.setBounds(frameSize - 100, 10, Chess.margin / 2, 30);
        timer2.setBounds(frameSize - 100, 10, Chess.margin / 2, 30);
        timer1.setBackground(new Color(43, 40, 37));
        timer2.setBackground(new Color(43, 40, 37));
        timer1.setOpaque(true);
        timer2.setOpaque(true);
        player1.timerText = timer1;
        player2.timerText = timer2;
        label1.add(timer1);
        label2.add(timer2);

        JLabel eatenPieces1 = new JLabel();
        JLabel eatenPieces2 = new JLabel();
        eatenPieces1.setBounds(30, 35, Chess.frameSize, 20);
        eatenPieces2.setBounds(30, 35, Chess.frameSize, 20);
        eatenPieces1.setBackground(new Color(49, 46, 43));
        eatenPieces2.setBackground(new Color(49, 46, 43));
        eatenPieces1.setForeground(new Color(255, 255, 255));
        eatenPieces2.setForeground(new Color(255, 255, 255));
        eatenPieces1.setOpaque(true);
        eatenPieces2.setOpaque(true);
        frame.add(label2);
        frame.add(label1);

        label1.add(name1);
        label2.add(name2);

        frame.repaint();
        activePlayer = player1;
        player1.runTime();

    }

    // changing the color of the board
    public static void changeColor(int possibleMoves[][]) {
        for (int i = 0; i < possibleMoves.length; i++)
            if (possibleMoves[i][0] > -1 && possibleMoves[i][1] > -1 && possibleMoves[i][0] < 8
                    && possibleMoves[i][1] < 8)
                Chess.blocks[possibleMoves[i][1]][possibleMoves[i][0]].setBackground(new Color(185, 204, 54));
        Chess.frame.repaint();
        Chess.oldPieces = possibleMoves;
    }

    // reseting the color of the table
    public static void resetColor(int possibleMoves[][]) {
        for (int i = 0; i < possibleMoves.length; i++) {
            Color color = (possibleMoves[i][0] + possibleMoves[i][1]) % 2 == 1 ? new Color(118, 150, 86) : new Color(238, 238, 210);
            if (possibleMoves[i][0] > -1 && possibleMoves[i][1] > -1 && possibleMoves[i][0] < 8&& possibleMoves[i][1] < 8)
                Chess.blocks[possibleMoves[i][1]][possibleMoves[i][0]].setBackground(color);
        }
        Chess.frame.repaint();
    }

    public static Block getBlock(int x, int y) {
        if (x > -1 && y > -1 && x < 8 && y < 8) {
            Block block = Chess.blocks[y][x];
            return block;
        }
        return null;
    }

    //convert hh.mm to hh:mm
    public static String caleTimeString(String timeString) {
        double time = Double.parseDouble(timeString);
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static String calcTimeString2(double time) {
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        return String.format("%02d:%02d", hours, minutes);
    }

    public static void movePieces() {
        // Iterate through all the pieces on the board and update their possibleMoves
        // arrays
        for (int row = 0; row <= 7; row++) {
            for (int col = 0; col <= 7; col++) {
                Piece piece = Chess.getBlock(row, col).piece;
                if (piece.value != null) {
                    piece.move_piece(true);
                }
            }
        }     
    }
}