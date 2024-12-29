/*
The Knight class represents a Knight piece in a game of chess.
It extends the Piece class and inherits its properties and methods.
*/


import java.awt.image.BufferedImage;

public class Knight extends Piece {
    /**
     * Constructor for the Knight class
     *
     * @param board   The Board on which the Knight will be placed
     * @param column  The column index of the Knight's position on the board
     * @param row     The row index of the Knight's position on the board
     * @param isWhite A boolean representing whether the Knight is white or black
     */
    public Knight(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Knight";
        this.isFirstMove = true;

        this.sprite = sheet.getSubimage(pieceWidth * 3, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }

    //Set the valid move of the Knight
    public boolean isValidMovement(int column, int row) {
        //Set steps of movement for the Knight
        columnMove = Math.abs(column - this.column);
        rowMove = Math.abs(row - this.row);

        return columnMove * rowMove == 6 && columnMove > 1 && rowMove > 1;
    }
}
