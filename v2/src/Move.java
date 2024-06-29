/**
 * The Move class represents a move made by a chess piece on the board.
 */


public class Move {
    public static int counter = 0; //Count the total number of the moves in the game

    // Instance variables to represent the old and new position of a piece after a move.
    int oldColumn, newColumn;
    int oldRow, newRow;
    Piece piece; // Instance variable to represent the piece that made the move.

    Piece capture; // Instance variable to represent the piece that was captured (if any) during the move.

    /**
     * Constructor for creating a new Move object.
     *
     * @param board     the board on which the move is being made
     * @param piece     the piece making the move
     * @param newColumn the new column position of the piece
     * @param newRow    the new row position of the piece
     */
    public Move(Board board, Piece piece, int newColumn, int newRow) {

        this.oldColumn = piece.column;
        this.oldRow = piece.row;
        this.newColumn = newColumn;
        this.newRow = newRow;
        this.piece = piece;
        this.capture = board.getPiece(newColumn, newRow);
    }

}
