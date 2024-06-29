/**
 * The CheckMate class represents the status of the game, specifically if a King is in checkmate or not.
 * *It also calculates if a King is in a check position or not.
 **/


public class CheckMate {
    Board board;


    /**
     * Constructor for CheckMate object, initializes the board
     *
     * @param board the chessboard object
     */
    public CheckMate(Board board) {
        this.board = board;
    }


    /**
     * Checks if a King is in a check position or not
     *
     * @param move The move object which contains the move information
     * @return True if King is in check, false otherwise
     */
    public boolean isKingChecked(Move move) {


        //Get the King piece, parameter shows which king of the two kings on the board
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        // Get the row and column of the King
        int kingColumn = king.column;
        int kingRow = king.row;

        // If there is a selected piece, and it is a King then update the King's row and column with its new position
        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
            kingColumn = move.newColumn;
            kingRow = move.newRow;
        }

        // Check if the King is in check position from King, Queen, Rooks, Bishops, Knights, Pawns.
        return
                hitByKing(king, kingColumn, kingRow) ||

                        hitByQueen(move, king, kingColumn, kingRow) ||

                        hitByRook(move, king, kingColumn, kingRow) ||

                        hitByBishop(move, king, kingColumn, kingRow) ||

                        hitByKnight(move.newColumn, move.newRow, king, kingColumn, kingRow) ||

                        hitByPawn(move.newColumn, move.newRow, king, kingColumn, kingRow);
    }


    /**
     * Determines whether a king piece is checking the other king.
     *
     * @param piece The First King which attacks.
     * @param king  The Second king which is attacked.
     * @return true if the piece is checking the  king, false otherwise.
     */
    private boolean checkByKing(Piece piece, Piece king) {

        // The piece must not be null, must be of the opposite team, and must be a king.
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("King");
    }


    /**
     * Method to get tiles Under-attack by the opposite king.
     *
     * @param king       The attacked king.
     * @param kingColumn The column where the king is located.
     * @param kingRow    The row where the king is located.
     * @return true if the opponent's king is in check, false otherwise.
     */
    private boolean hitByKing(Piece king, int kingColumn, int kingRow) {
        // return true for the tiles from the surrounding that are being attacked by the other King, otherwise false.
        return
                checkByKing(board.getPiece(kingColumn - 1, kingRow - 1), king) ||           //Up Left
                        checkByKing(board.getPiece(kingColumn - 1, kingRow + 1), king) ||   //Down Left
                        checkByKing(board.getPiece(kingColumn - 1, kingRow), king) ||            //Left
                        checkByKing(board.getPiece(kingColumn + 1, kingRow - 1), king) ||   //Up Right
                        checkByKing(board.getPiece(kingColumn + 1, kingRow + 1), king) ||   //Down Right
                        checkByKing(board.getPiece(kingColumn + 1, kingRow), king) ||            //Right
                        checkByKing(board.getPiece(kingColumn, kingRow - 1), king) ||              //Up
                        checkByKing(board.getPiece(kingColumn, kingRow + 1), king);                //Down

    }


    /**
     * Determines if a Queen can check a King.
     *
     * @param column          The valid column moves of every piece at the same team with attacked King
     * @param row             The valid row moves of every piece at the same team with attacked King
     * @param King            The king piece being checked
     * @param kingColumn      The column coordinate of the king
     * @param kingRow         The row coordinate of the king
     * @param columnDirection The direction the queen is moving in the column
     * @param rowDirection    The direction the queen is moving in the row
     * @return true if the queen can hit the king in the given direction, false otherwise
     **/
    private boolean checkByQueen(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection, int rowDirection) {
        // unit variable increase to scan checks in every direction
        for (int unit = 1; unit < 8; unit++) {


            //If you have a piece that covers the King, break
            if (kingColumn - (unit * columnDirection) == column && kingRow - (unit * rowDirection) == row) {
                break;
            }

            //Get each piece at tiles in Diagonal direction of the King to scan checkmates
            Piece piece = board.getPiece(kingColumn - (unit * columnDirection), kingRow - (unit * rowDirection));

            //Make sure there is a piece is in this tile
            if (piece != null && piece != board.selectedPiece) {

                //If the piece is Queen, and it's not the same team with the attacked King, so it's checkmate
                if (!board.sameTeam(piece, King) && piece.name.equals("Queen")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }


    /**
     * Method to get tiles Under-attack by Queen.
     *
     * @param move       The move object which contains the move information
     * @param king       The attacked king.
     * @param kingColumn The column where the king is located.
     * @param kingRow    The row where the king is located.
     * @return true if the opponent's king is in check, false otherwise.
     */
    private boolean hitByQueen(Move move, Piece king, int kingColumn, int kingRow) {
        return checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, -1) ||  //Up-Left Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, 1) ||  //Down-Left Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, 0) ||  //Left Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, -1) ||  //Up-Right Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, 1) ||   //Down Right Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, 0) ||   //Right Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, 0, -1) ||  //Up Direction
                checkByQueen(move.newColumn, move.newRow, king, kingColumn, kingRow, 0, 1);     //Down Direction
    }


    /**
     * Checks if a Rook hits the King in its move path
     *
     * @param column          The valid column moves of every piece at the same team with attacked King
     * @param row             The valid row moves of every piece at the same team with attacked King
     * @param King            The King being checked
     * @param kingColumn      The column of the King
     * @param kingRow         The row of the King
     * @param columnDirection The value of column direction the King is moving in (-1, 0 or 1)
     * @param rowDirection    The value of row direction the King is moving in (-1, 0 or 1)
     * @return True if the Rook hits the King in its move path, false otherwise
     */
    private boolean checkByRook(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection, int rowDirection) {

        // unit variable increase to scan checks in every direction
        for (int unit = 1; unit < 8; unit++) {

            //If you have a piece that covers the King, break
            if (kingColumn + (unit * columnDirection) == column && kingRow + (unit * rowDirection) == row) {
                break;
            }

            //Get each piece at tiles in Diagonal direction of the King to scan checkmates
            Piece piece = board.getPiece(kingColumn + (unit * columnDirection), kingRow + (unit * rowDirection));


            //Make sure there is a piece is in this tile
            if (piece != null && piece != board.selectedPiece) {

                //If the piece is Rook, and it's not the same team with the attacked King, so it's checkmate
                if (!board.sameTeam(piece, King) && piece.name.equals("Rook")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }


    /**
     * Method to get tiles Under-attack by rook.
     *
     * @param move       The move object which contains the move information
     * @param king       The attacked King.
     * @param kingColumn The column where the King is located.
     * @param kingRow    The row where the King is located.
     * @return true if the opponent's King is in check, false otherwise.
     */
    private boolean hitByRook(Move move, Piece king, int kingColumn, int kingRow) {
        return checkByRook(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, 0) ||   //Up
                checkByRook(move.newColumn, move.newRow, king, kingColumn, kingRow, 0, 1) ||  //Right
                checkByRook(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, 0) || //Down
                checkByRook(move.newColumn, move.newRow, king, kingColumn, kingRow, 0, -1);   //Left
    }


    /**
     * Checks if a Bishop hits the King in its move path
     *
     * @param column          The valid column moves of every piece at the same team with attacked King
     * @param row             The valid row moves of every piece at the same team with attacked King
     * @param King            The King being checked
     * @param kingColumn      The column of the King
     * @param kingRow         The row of the King
     * @param columnDirection The value of column direction the King is moving in (-1, 0 or 1)
     * @param rowDirection    The value of row direction the King is moving in (-1, 0 or 1)
     * @return True if the Bishop can hit the King in its move path, false otherwise
     */
    private boolean checkByBishop(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection, int rowDirection) {

        //Start at unit three because the Bishop can bypass other pieces.
        for (int unit = 3; unit > 0; unit--) {
            if (kingColumn - (unit * columnDirection) == column && kingRow - (unit * rowDirection) == row) {
                break;
            }
            Piece piece = board.getPiece(kingColumn - (unit * columnDirection), kingRow - (unit * rowDirection));
            if (piece != null && piece != board.selectedPiece) {
                if (!board.sameTeam(piece, King) && piece.name.equals("Bishop")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to get tiles Under-attack by Bishop.
     *
     * @param king       The attacked king.
     * @param kingColumn The column where the king is located.
     * @param kingRow    The row where the king is located.
     * @return true if the opponent's king is in check, false otherwise.
     */
    private boolean hitByBishop(Move move, Piece king, int kingColumn, int kingRow) {
        return checkByBishop(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, -1) ||//Up Left
                checkByBishop(move.newColumn, move.newRow, king, kingColumn, kingRow, -1, 1) ||//Up Right
                checkByBishop(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, -1) ||//Down Right
                checkByBishop(move.newColumn, move.newRow, king, kingColumn, kingRow, 1, 1);//Down Left
    }


    /**
     * Determines if a Queen can check a King.
     *
     * @param piece  the Knight piece the attacks the king
     * @param king   the attacked king
     * @param column the valid column moves of every piece at the same team with King
     * @param row    the valid row moves of every piece at the same team with King
     * @return true if the Knight can hit the king in the move path, false otherwise
     **/
    private boolean CheckByKnight(Piece piece, Piece king, int column, int row) {

        //Return true if the piece is Knight and not the same team with attacked King, and can't be eaten by any piece,otherwise return false.
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("Knight") && !(piece.column == column && piece.row == row);
    }

    /**
     * Method to get tiles Under-attack by Knight.
     *
     * @param column     The valid column moves of every piece at the same team with attacked King
     * @param row        The valid row moves of every piece at the same team with attacked King
     * @param kingColumn The column where the king is located.
     * @param kingRow    The row where the king is located.
     * @return true if the King is in check by the Knight, false otherwise.
     */
    private boolean hitByKnight(int column, int row, Piece King, int kingColumn, int kingRow) {
        return
                CheckByKnight(board.getPiece(kingColumn - 3, kingRow - 2), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn + 3, kingRow - 2), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn + 2, kingRow - 3), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn + 2, kingRow + 3), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn + 3, kingRow + 2), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn - 3, kingRow + 2), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn - 2, kingRow + 3), King, column, row) ||
                        CheckByKnight(board.getPiece(kingColumn - 2, kingRow - 3), King, column, row);
    }


    /**
     * Determines if a Pawn can check a King.
     *
     * @param column The valid column moves of every piece at the same team with King
     * @param row    The valid row moves of every piece at the same team with King
     * @param king   The attacked King
     * @param piece  The Pawn piece the attacks the King
     * @return true if the Pawn can hit the king in the move path, false otherwise
     **/
    private boolean checkByPawn(int column, int row, Piece piece, Piece king) {

        //Return true if the piece is Pawn and not the same team with attacked King, and can't be eaten by any piece,otherwise return false.
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("Pawn") && !(piece.column == column && piece.row == row);
    }


    /**
     * Method to get tiles Under-attack by Pawn.
     *
     * @param king       The attacked king.
     * @param kingColumn The column where the king is located.
     * @param kingRow    The row where the king is located.
     * @return true if the King is in check by the Pawn, false otherwise.
     */
    private boolean hitByPawn(int column, int row, Piece king, int kingColumn, int kingRow) {

        //Move Direction in white pieces is inverse to black pieces
        int moveDirection = king.isWhite ? -1 : 1;

        return checkByPawn(column, row, board.getPiece(kingColumn + 1, kingRow + moveDirection), king) ||     //Front Right Diagonal
                checkByPawn(column, row, board.getPiece(kingColumn - 1, kingRow + moveDirection), king) ||    //Front Left Diagonal
                checkByPawn(column, row, board.getPiece(kingColumn, kingRow + moveDirection), king);                 //Front
    }

}