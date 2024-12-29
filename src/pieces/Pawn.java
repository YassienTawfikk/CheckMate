/*
The Pawn class represents a pawn piece in a game of chess.
It extends the Piece class and inherits its properties and methods.
*/


import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.util.ArrayList;

public class Pawn extends Piece {


    /**
     * Constructor for the Pawn class
     *
     * @param board   The Board on which the Pawn will be placed
     * @param column  The column index of the Pawn's position on the board
     * @param row     The row index of the Pawn's position on the board
     * @param isWhite A boolean representing whether the Pawn is white or black
     */
    public Pawn(Board board, int column, int row, boolean isWhite) {
        super(board);
        this.board = board;
        this.column = column;
        this.row = row;
        this.xPos = column * board.tileSize;
        this.yPos = row * board.tileSize;
        this.isWhite = isWhite;
        this.name = "Pawn";
        this.isFirstMove = true;
        this.rowEnd = isWhite ? 0 : 7;

        // The sprite for the pawn piece is retrieved from a larger image file
        // and scaled down to the size of a single tile on the board.
        this.sprite = sheet.getSubimage(pieceWidth * 5, isWhite ? 0 : pieceHeight, pieceWidth, pieceHeight).getScaledInstance(board.tileSize, board.tileSize, BufferedImage.SCALE_SMOOTH);
    }


    //Set valid moves of Pawn
    public boolean isValidMovement(int column, int row) {

        //To check if the way for the Pawn is clear or not in each direction
        boolean isNorthOccupied, isNorthEastOccupied, isNorthWestOccupied, isFarNorthOccupied;
        //Movements of Pawn in columns & rows
        int columnMove, rowMove;


        //Declare variables by a way (if the piece is white) & inverse way (if the piece is black) to have the same return conditions


        //Move Direction in white pieces is inverse to black pieces
        int moveDirection = isWhite ? 1 : -1;

        //Set steps of movement for the Pawn
        columnMove = (this.column - column) * moveDirection;
        rowMove = (this.row - row) * moveDirection;

        //Way in front direction by one step
        isNorthOccupied = board.getPiece(this.column, this.row - moveDirection) != null;

        //Way in front direction by two steps
        isFarNorthOccupied = board.getPiece(this.column, this.row - 2 * moveDirection) != null;

        //Way in Front Right direction
        isNorthEastOccupied = board.getPiece(this.column + moveDirection, this.row - moveDirection) != null;

        //Way in Front Left direction
        isNorthWestOccupied = board.getPiece(this.column - moveDirection, this.row - moveDirection) != null;


        //First move of pawn can be one or two tiles in column direction
        if (isFirstMove) {
            if ((isNorthOccupied || isFarNorthOccupied) && isNorthEastOccupied && isNorthWestOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == 1 || columnMove == -1);
            } else if ((isNorthOccupied || isFarNorthOccupied) && isNorthEastOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == -1);
            } else if ((isNorthOccupied || isFarNorthOccupied) && isNorthWestOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == 1);
            } else if ((isNorthOccupied || isFarNorthOccupied)) {
                return (rowMove == 1) && (columnMove == 0);
            } else if (isNorthEastOccupied && isNorthWestOccupied) {
                return (columnMove == 0 && ((rowMove == 2) || (rowMove == 1))) || (rowMove == 1 && (columnMove == 1 || columnMove == -1));
            } else if (isNorthEastOccupied) {
                return (columnMove == 0 && ((rowMove == 2) || (rowMove == 1))) || (rowMove == 1 && (columnMove == -1));
            } else if (isNorthWestOccupied) {
                return (columnMove == 0 && ((rowMove == 2) || (rowMove == 1))) || (rowMove == 1 && (columnMove == 1));
            } else {
                return (columnMove == 0 && ((rowMove == 2) || (rowMove == 1)));
            }
        }
        //If it's not the first move it can only move one step
        else {
            if (isNorthOccupied && isNorthEastOccupied && isNorthWestOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == 1 || columnMove == -1);
            } else if (isNorthOccupied && isNorthEastOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == -1);
            } else if (isNorthOccupied && isNorthWestOccupied) {
                return (rowMove == 1) && (columnMove == 0 || columnMove == 1);
            } else if (isNorthOccupied) {
                return (rowMove == 1) && (columnMove == 0);
            } else if (isNorthEastOccupied && isNorthWestOccupied) {
                return (columnMove == 0 && rowMove == 1) || (rowMove == 1 && (columnMove == 1 || columnMove == -1));
            } else if (isNorthEastOccupied) {
                return (columnMove == 0 && rowMove == 1) || (rowMove == 1 && (columnMove == -1));
            } else if (isNorthWestOccupied) {
                return (columnMove == 0 && rowMove == 1) || (rowMove == 1 && (columnMove == 1));
            } else {
                return (columnMove == 0 && rowMove == 1);
            }
        }
    }
}
