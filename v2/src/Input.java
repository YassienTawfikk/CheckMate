

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// This class handles user input through mouse clicks and drags
public class Input extends MouseAdapter {

    // The board object that this input is associated with
    Board board;

    // The number of clicks received so far
    private int clickCount = 1;

    // Constructor that takes a board object
    public Input(Board board) {
        this.board = board;
    }

    // Method called when mouse is pressed down
    @Override
    public void mousePressed(MouseEvent e) {
        //First Mouse Click
        if (clickCount % 2 == 0) {

            //Increment for click counter
            clickCount++;

            // Calculate the column and row of the tile that was clicked
            int column = e.getX() / board.tileSize;
            int row = e.getY() / board.tileSize;

            // Get the piece object that is on that tile
            Piece pieceXY = board.getPiece(column, row);

            // If a piece was found, select it
            if (pieceXY != null) {
                board.selectedPiece = pieceXY;
            }
            if (board.selectedPiece != null) {
                // Repaint the board to show the updated piece position
                board.repaint();
            }
        }
        //Second Mouse click
        else {

            //Increment for click counter
            clickCount++;
            // Calculate the column and row of the tile that the mouse was released on
            int column = e.getX() / board.tileSize;
            int row = e.getY() / board.tileSize;

            // If a piece is currently selected
            if (board.selectedPiece != null) {
                // Create a Move object representing the piece's movement
                Move move = new Move(board, board.selectedPiece, column, row);

                // If it's valid move and turn, make the move on the board
                if (board.isValidMove(move) && board.validTurn()) {
                    Move.counter++; //counter increment to switch turns
                    board.makeMove(move);
                }
                // If the move is invalid, return the piece to its original position
                else {
                    board.selectedPiece.yPos = board.selectedPiece.row * board.tileSize;
                    board.selectedPiece.xPos = board.selectedPiece.column * board.tileSize;
                }
            }

            // Deselect the piece and repaint the board
            board.selectedPiece = null;
            board.repaint();
        }
    }

}
