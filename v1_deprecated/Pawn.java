import javax.swing.*;
import java.util.List;
import java.awt.GridLayout;
import java.util.ArrayList;

public class Pawn extends Piece {
    Player player;
    boolean hasMoved;

    Pawn(int x, int y, Player player, Block block) {
        super(x, y, player, "pawn", block);
        // reference to the player owned it
        this.player = player;
        // set the initial possible moves
        this.move_piece(true);
        this.hasMoved = false;
        
    }

    // changing the possible moves when piece moves
    @Override
    public void move_piece(Boolean UpdateOnly) {
        int direction = this.player.number == 2 ? -1 : 1; // direction of movement depends on the player's color
        this.possibleMoves = new int[][] {
                { this.x, this.y + direction }
        };
        if (!hasMoved) {
            // add the option to move two squares on the first move
            this.possibleMoves = new int[][] {
                    { this.x, this.y + direction },
                    { this.x, this.y + 2 * direction }
            };
        }

        List<int[]> captures = new ArrayList<>();
        // check for diagonal captures
        if (this.x > 0 && this.y + direction>-1 && this.y + direction<8) {
            Block left = Chess.blocks[this.y + direction][this.x - 1];
            if (left != null && left.piece != null && left.piece.player != player) {
                captures.add(new int[] { this.x - 1, this.y + direction });
            }
        }
        if (this.x < 7&& this.y + direction>-1 && this.y + direction<8) {
            Block right = Chess.blocks[this.y + direction][this.x + 1];
            if (right != null && right.piece != null && right.piece.player != player) {
                captures.add(new int[] { this.x + 1, this.y + direction });
            }
        }
        // add diagonal captures to possible moves
        if (!captures.isEmpty()) {
            this.possibleMoves = captures.toArray(new int[captures.size()][2]);
        }

        if (!UpdateOnly) {
            hasMoved = true;
            this.moves++;
            // check for promotion
            if ((player.number == 2 && this.y == 0) || (player.number == 1 && this.y == 7)) {
                promote();
            }
        }
    }

    // promote the pawn to a different piece
    private void promote() {
        // create a dialog box that prompts the player to choose a piece to promote to
        Object[] options = { "Queen", "Rook", "Bishop", "Knight" };
        int choice = JOptionPane.showOptionDialog(null,
                "Your pawn has reached the opposite end of the board. Choose a piece to promote to:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]);

        // create a new piece based on the player's choice
        Piece newPiece;
        switch (choice) {
            case 0:
                newPiece = new Queen(this.x, this.y, this.player, Chess.blocks[this.y][this.x]);
                break;
            case 1:
                newPiece = new Rook(this.x, this.y, this.player, Chess.blocks[this.y][this.x]);
                break;
            case 2:
                newPiece = new Bishop(this.x, this.y, this.player, Chess.blocks[this.y][this.x]);
                break;
            case 3:
                newPiece = new Knight(this.x, this.y, this.player, Chess.blocks[this.y][this.x]);
                break;
            default:
                System.out.println("Invalid choice. Defaulting to queen promotion.");
                newPiece = new Queen(this.x, this.y, this.player, Chess.blocks[this.y][this.x]);
        }

        // set the possible moves of the new piece
        Chess.blocks[this.y][this.x].piece = newPiece;
        Chess.frame.getContentPane().remove(this);
        newPiece.setBounds((this.x)*Chess.blocksize,(this.y)*Chess.blocksize + Chess.margin/2,Chess.blocksize,Chess.blocksize);
        Chess.frame.add(newPiece);
        Chess.frame.repaint();
        Chess.frame.setComponentZOrder(newPiece, 0);
    }
}
