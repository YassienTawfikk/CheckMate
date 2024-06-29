/*
The Bishop class represents a pawn piece in a game of chess.
It extends the Piece class and inherits its properties and methods.
*/



import java.awt.image.BufferedImage;

/**
 * This class represents a Bishop chess piece and extends the Piece class
 */
public class Bishop extends Piece {

    /**
     * Constructor for the Bishop class
     *
     * @param board   The Board on which the Bishop will be placed
     * @param column  The column index of the Bishop's position on the board
     * @param row     The row index of the Bishop's position on the board
     * @param isWhite A boolean representing whether the Bishop is white or black
     */
    public Bishop(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Bishop";
        this.isFirstMove=true;

        // Get the Bishop's sprite from the sprite sheet and scale it to the appropriate size
        this.sprite = sheet.getSubimage(pieceWidth * 2, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize - 1, board.tileSize - 1, BufferedImage.SCALE_SMOOTH);
    }

    //Set valid moves of Bishop
    public boolean isValidMovement(int column, int row) {

        //Set steps of movement for the Bishop
        columnMove = Math.abs(column - this.column);
        rowMove = Math.abs(row - this.row);

        //Check clearance of the path for Bishop to change it's color
        boolean rightClear = board.getPiece(this.column + 1, this.row) == null;
        boolean leftClear = board.getPiece(this.column - 1, this.row) == null;

        if (rightClear && leftClear) {
            return (columnMove <= 3 && rowMove <= 3 && columnMove == rowMove) || (columnMove == 1 && rowMove == 0);
        } else if (rightClear) {
            return (columnMove <= 3 && rowMove <= 3 && columnMove == rowMove) || (column - this.column == 1 && rowMove == 0);
        } else if (leftClear) {
            return (columnMove <= 3 && rowMove <= 3 && columnMove == rowMove) || (column - this.column == -1 && rowMove == 0);
        } else {
            return (columnMove <= 3 && rowMove <= 3 && columnMove == rowMove);
        }
    }

}
