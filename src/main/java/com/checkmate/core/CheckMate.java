/**
 * The CheckMate class represents the status of the game, specifically if a King
 * is in checkmate or not.
 * *It also calculates if a King is in a check position or not.
 **/

package com.checkmate.core;

import com.checkmate.pieces.*;

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
        // Get the King piece of the moving team
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        // Current King position (updated by simulation in Board.isValidMove)
        int kingColumn = king.column;
        int kingRow = king.row;

        // Note: We do NOT need to manually update King position if it's the moving
        // piece
        // because Board.isValidMove now physically moves the piece before calling this.

        return hitByKing(king, kingColumn, kingRow) ||
                hitByQueen(king, kingColumn, kingRow) ||
                hitByRook(king, kingColumn, kingRow) ||
                hitByBishop(king, kingColumn, kingRow) ||
                hitByKnight(king, kingColumn, kingRow) ||
                hitByPawn(king, kingColumn, kingRow);
    }

    // ... (rest of the file helpers need updating to remove 'move' arg where unused
    // or clean up rays)

    // Helper to check rays without 'selectedPiece' logic (since piece is physically
    // moved)
    private boolean checkByQueen(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection,
            int rowDirection) {
        // column and row correspond to "target square to ignore" logic, but since we
        // disabled it with -99,
        // we can probably simplify.
        // But for minimal diff, let's keep logic but rely on physical block.
        for (int unit = 1; unit < 8; unit++) {
            // Check against phantom target (disabled by -99)
            if (kingColumn - (unit * columnDirection) == column && kingRow - (unit * rowDirection) == row) {
                break;
            }
            Piece piece = board.getPiece(kingColumn - (unit * columnDirection), kingRow - (unit * rowDirection));
            if (piece != null) {
                if (!board.sameTeam(piece, King) && piece.name.equals("Queen")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByQueen(Piece king, int kingColumn, int kingRow) {
        return checkByQueen(-99, -99, king, kingColumn, kingRow, -1, -1) || // Up-Left
                checkByQueen(-99, -99, king, kingColumn, kingRow, -1, 1) || // Down-Left
                checkByQueen(-99, -99, king, kingColumn, kingRow, -1, 0) || // Left
                checkByQueen(-99, -99, king, kingColumn, kingRow, 1, -1) || // Up-Right
                checkByQueen(-99, -99, king, kingColumn, kingRow, 1, 1) || // Down Right
                checkByQueen(-99, -99, king, kingColumn, kingRow, 1, 0) || // Right
                checkByQueen(-99, -99, king, kingColumn, kingRow, 0, -1) || // Up
                checkByQueen(-99, -99, king, kingColumn, kingRow, 0, 1); // Down
    }

    /**
     * Checks if a Rook hits the King in its move path
     */
    private boolean checkByRook(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection,
            int rowDirection) {
        for (int unit = 1; unit < 8; unit++) {
            if (kingColumn + (unit * columnDirection) == column && kingRow + (unit * rowDirection) == row) {
                break;
            }
            Piece piece = board.getPiece(kingColumn + (unit * columnDirection), kingRow + (unit * rowDirection));
            if (piece != null) {
                if (!board.sameTeam(piece, King) && piece.name.equals("Rook")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByRook(Piece king, int kingColumn, int kingRow) {
        return checkByRook(-99, -99, king, kingColumn, kingRow, 1, 0) || // Up
                checkByRook(-99, -99, king, kingColumn, kingRow, 0, 1) || // Right
                checkByRook(-99, -99, king, kingColumn, kingRow, -1, 0) || // Down
                checkByRook(-99, -99, king, kingColumn, kingRow, 0, -1); // Left
    }

    /**
     * Checks if a Bishop hits the King in its move path
     */
    private boolean checkByBishop(int column, int row, Piece King, int kingColumn, int kingRow, int columnDirection,
            int rowDirection) {
        // Start at unit one? Original said unit 3 because it bypassed pieces?
        // No, Bishop should check rays from unit 1!
        // Original code: for (int unit = 3; unit > 0; unit--)
        // Wait, why 3? Maybe assuming Knight jump distance? No, Bishop is diagonal.
        // The original code was VERY weird for Bishop. "Start at unit three ... because
        // can bypass".
        // Bishops CANNOT bypass pieces. Logic error in original code?
        // Or maybe it was checking only short range?
        // Ah, maybe it meant "check 3 squares away"?
        // Let's implement STANDARD diagonal raycast like Queen.
        for (int unit = 1; unit < 8; unit++) {
            if (kingColumn - (unit * columnDirection) == column && kingRow - (unit * rowDirection) == row) {
                break;
            }
            Piece piece = board.getPiece(kingColumn - (unit * columnDirection), kingRow - (unit * rowDirection));
            if (piece != null) {
                if (!board.sameTeam(piece, King) && piece.name.equals("Bishop")) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(Piece king, int kingColumn, int kingRow) {
        return checkByBishop(-99, -99, king, kingColumn, kingRow, -1, -1) || // Up Left
                checkByBishop(-99, -99, king, kingColumn, kingRow, -1, 1) || // Up Right
                checkByBishop(-99, -99, king, kingColumn, kingRow, 1, -1) || // Down Right
                checkByBishop(-99, -99, king, kingColumn, kingRow, 1, 1);// Down Left
    }

    private boolean CheckByKnight(Piece piece, Piece king) {
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("Knight");
    }

    private boolean hitByKnight(Piece King, int kingColumn, int kingRow) {
        return CheckByKnight(board.getPiece(kingColumn - 3, kingRow - 2), King) ||
                CheckByKnight(board.getPiece(kingColumn + 3, kingRow - 2), King) ||
                CheckByKnight(board.getPiece(kingColumn + 2, kingRow - 3), King) ||
                CheckByKnight(board.getPiece(kingColumn + 2, kingRow + 3), King) ||
                CheckByKnight(board.getPiece(kingColumn + 3, kingRow + 2), King) ||
                CheckByKnight(board.getPiece(kingColumn - 3, kingRow + 2), King) ||
                CheckByKnight(board.getPiece(kingColumn - 2, kingRow + 3), King) ||
                CheckByKnight(board.getPiece(kingColumn - 2, kingRow - 3), King);
    }

    private boolean checkByKing(Piece piece, Piece king) {
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("King");
    }

    private boolean hitByKing(Piece king, int kingColumn, int kingRow) {
        // return true for the tiles from the surrounding that are being attacked by the
        // other King, otherwise false.
        return checkByKing(board.getPiece(kingColumn - 1, kingRow - 1), king) || // Up Left
                checkByKing(board.getPiece(kingColumn - 1, kingRow + 1), king) || // Down Left
                checkByKing(board.getPiece(kingColumn - 1, kingRow), king) || // Left
                checkByKing(board.getPiece(kingColumn + 1, kingRow - 1), king) || // Up Right
                checkByKing(board.getPiece(kingColumn + 1, kingRow + 1), king) || // Down Right
                checkByKing(board.getPiece(kingColumn + 1, kingRow), king) || // Right
                checkByKing(board.getPiece(kingColumn, kingRow - 1), king) || // Up
                checkByKing(board.getPiece(kingColumn, kingRow + 1), king); // Down

    }

    private boolean checkByPawn(Piece piece, Piece king) {
        return piece != null && !board.sameTeam(piece, king) && piece.name.equals("Pawn");
    }

    private boolean hitByPawn(Piece king, int kingColumn, int kingRow) {
        // Move Direction in white pieces is inverse to black pieces
        int moveDirection = king.isWhite ? -1 : 1;

        return checkByPawn(board.getPiece(kingColumn + 1, kingRow + moveDirection), king) || // Front Right
                checkByPawn(board.getPiece(kingColumn - 1, kingRow + moveDirection), king); // Front Left
        // Note: Pawn captures diagonally only!
        // Original code checked Front too?
        // checkByPawn(..., board.getPiece(kingColumn, kingRow + moveDirection), king);
        // // Front
        // Pawns CANNOT capture front.
        // Assuming standard Chess rules, remove Front check.
    }

}