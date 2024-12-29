/*
The Rook class represents a pawn piece in a game of chess.
It extends the Piece class and inherits its properties and methods.
*/


import java.awt.image.BufferedImage;

public class Rook extends Piece {


    /**
     * Constructor for the Rook class
     *
     * @param board   The Board on which the Rook will be placed
     * @param column  The column index of the Rook's position on the board
     * @param row     The row index of the Rook's position on the board
     * @param isWhite A boolean representing whether the Rook is white or black
     */
    public Rook(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Rook";
        this.isFirstMove = true;

        this.sprite = sheet.getSubimage(pieceWidth * 4, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize - 1, board.tileSize - 1, BufferedImage.SCALE_SMOOTH);

    }

    //Set valid moves of Rook
    public boolean isValidMovement(int column, int row) {

        //Set steps of movement for the Rook
        columnMove = column - this.column;
        rowMove = row - this.row;

        return columnMove == 0 || rowMove == 0;
    }

    //Set boundaries for hitting piece because Rook can't bypass any piece
    public boolean moveHitsPiece(int column, int row) {

        //Set steps of movement for the Rook
        columnMove = column - this.column;
        rowMove = row - this.row;

        //Moving Left
        if (columnMove < 0) {
            for (int c = this.column - 1; c > column; c--) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }

        //Moving Right
        if (columnMove > 0) {
            for (int c = this.column + 1; c < column; c++) {
                if (board.getPiece(c, this.row) != null) {
                    return true;
                }
            }
        }

        //Moving Up
        if (rowMove < 0) {
            for (int r = this.row - 1; r > row; r--) {
                if (board.getPiece(this.column, r) != null) {
                    return true;
                }
            }
        }

        //Moving Down
        if (rowMove > 0) {
            for (int r = this.row + 1; r < row; r++) {
                if (board.getPiece(this.column, r) != null) {
                    return true;
                }
            }
        }

        return false;
    }
}
