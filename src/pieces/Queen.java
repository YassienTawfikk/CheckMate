/*
The Queen class represents a pawn piece in a game of chess.
It extends the Piece class and inherits its properties and methods.
*/


import java.awt.image.BufferedImage;

public class Queen extends Piece {
    /**
     * Constructor for the Queen class
     *
     * @param board   The Board on which the Queen will be placed
     * @param column  The column index of the Queen's position on the board
     * @param row     The row index of the Queen's position on the board
     * @param isWhite A boolean representing whether the Queen is white or black
     */
    public Queen(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Queen";
        this.isFirstMove = true;

        this.sprite = sheet.getSubimage(pieceWidth, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize - 1, board.tileSize - 1, BufferedImage.SCALE_SMOOTH);
    }

    //Set valid moves of Queen
    public boolean isValidMovement(int column, int row) {

        //Set steps of movement for the Knight
        columnMove = Math.abs(column - this.column);
        rowMove = Math.abs(row - this.row);

        return (rowMove < 8 && columnMove == 0) || (columnMove < 8 && rowMove == 0) || (rowMove == columnMove);
    }

    //Set boundaries for hitting piece because Queen can't bypass any piece
    public boolean moveHitsPiece(int column, int row) {
        columnMove = column - this.column;
        rowMove = row - this.row;

        if (this.column == column || this.row == row) {
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
        } else {
            //Moving Up Left
            if (columnMove < 0 && rowMove < 0) {
                for (int d = 1; d < Math.abs(this.column - column); d++) {
                    if (board.getPiece(this.column - d, this.row - d) != null) {
                        return true;
                    }
                }
            }
            //Moving Up Right
            if (columnMove > 0 && rowMove < 0) {
                for (int d = 1; d < Math.abs(this.column - column); d++) {
                    if (board.getPiece(this.column + d, this.row - d) != null) {
                        return true;
                    }
                }
            }
            //Moving Down Left
            if (columnMove < 0 && rowMove > 0) {
                for (int d = 1; d < Math.abs(this.column - column); d++) {
                    if (board.getPiece(this.column - d, this.row + d) != null) {
                        return true;
                    }
                }
            }
            //Moving Down Right
            if (columnMove > 0 && rowMove > 0) {
                for (int d = 1; d < Math.abs(this.column - column); d++) {
                    if (board.getPiece(this.column + d, this.row + d) != null) {
                        return true;
                    }
                }
            }

        }

        return false;

    }
}

