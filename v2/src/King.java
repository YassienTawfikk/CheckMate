/**
 * The King class represents a king chess piece.
 * It extends the Piece class and inherits its attributes and methods.
 */



import java.awt.image.BufferedImage;


public class King extends Piece {

    /**
     * Constructor for the King class
     *
     * @param board   The Board on which the King will be placed
     * @param column  The column index of the King's position on the board
     * @param row     The row index of the King's position on the board
     * @param isWhite A boolean representing whether the King is white or black
     */
    public King(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "King";
        this.isFirstMove = true;

        // get the sprite for the king piece from the sheet and scale it to fit the tile size
        this.sprite = sheet.getSubimage(0, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize - 1, board.tileSize - 1, BufferedImage.SCALE_SMOOTH);

    }

    //Set valid moves of King
    public boolean isValidMovement(int column, int row) {

        //Set steps of movement for the King
        columnMove = Math.abs(column - this.column);
        rowMove = Math.abs(row - this.row);

        return  (((columnMove <= 1 && rowMove <= 1) && ((columnMove == 0 && rowMove == 1) || (columnMove == 1 && rowMove == 0) || (columnMove == rowMove))) || canRightCastle(column, row) || canLeftCastle(column, row));
    }

    //Castle the King to the right
    private boolean canRightCastle(int column, int row) {

        //Row of the King and Rook based on the color of the piece
        int kingRow = isWhite ? 7 : 0;

        Piece rightRook = board.getPiece(7, kingRow);

        //Check if it isn't first move for the rook or the king
        if (this.isFirstMove && rightRook != null && rightRook.isFirstMove) {
            return board.getPiece(5, kingRow) == null && board.getPiece(6, kingRow) == null && column - this.column == 2 && row == this.row;
        }

        return false;
    }

    //Castle the King to the left
    private boolean canLeftCastle(int column, int row) {

        //Row of the King and Rook based on the color of the piece
        int kingRow = isWhite ? 7 : 0;

        Piece leftRook = board.getPiece(0, kingRow);

        //Check if it isn't first move for the rook or the king
        if (this.isFirstMove && leftRook != null && leftRook.isFirstMove) {
            return board.getPiece(1, kingRow) == null && board.getPiece(2, kingRow) == null && board.getPiece(3, kingRow) == null && column - this.column == -2 && row == this.row;
        }
        return false;
    }
}
