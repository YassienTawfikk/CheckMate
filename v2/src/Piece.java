/**
 * This class represents the base Piece in the chess game, defining its attributes and behavior.
 */


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

// Piece class definition
public class Piece {

    // piece attributes
    public int column, row;
    public int xPos, yPos;
    public boolean isWhite;
    public boolean isFirstMove;
    public int rowEnd;
    public String name;


    //public int value;


    //Movements of Piece in columns & rows
    int columnMove, rowMove;

    BufferedImage sheet;

    Image sprite;
    Board board;

    // Load sprite sheet for the pieces
    {
        try {
            sheet = ImageIO.read(Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("pieces1.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Define dimensions of each piece on the sprite sheet
    protected int pieceWidth = sheet.getWidth() / 6;
    protected int pieceHeight = sheet.getHeight() / 2;

    /**
     * Constructor for creating a new Piece object and initializing the Board object.
     *
     * @param board The board object where the Piece is to be placed.
     */
    public Piece(Board board) {
        this.board = board;
    }


    /**
     * Method to draw the Piece on the board.
     *
     * @param g2d The graphics object used to draw the Piece.
     */
    public void paint(Graphics2D g2d) {
        g2d.drawImage(sprite, xPos, yPos, null);
    }


    //Create The two following methods to override on it in every piece's class

    /**
     * Checks if a given movement is valid for a Piece object.
     *
     * @param column The column to which the Piece is to be moved.
     * @param row    The row to which the Piece is to be moved.
     * @return true if the movement is valid, false otherwise.
     */
    public boolean isValidMovement(int column, int row) {
        return true;
    }

    /**
     * Checks if a Piece object will hit another Piece object when moved to a given location.
     *
     * @param column The column to which the Piece is to be moved.
     * @param row    The row to which the Piece is to be moved.
     * @return true if the movement hits another Piece, false otherwise.
     */

    public boolean moveHitsPiece(int column, int row) {
        return false;
    }

    /**
     * Checks if a Piece object can attack another Piece object at a given location.
     *
     * @param column The column of the Piece to be attacked.
     * @param row    The row of the Piece to be attacked.
     * @return true if the Piece can attack the other Piece, false otherwise.
     */
    public boolean canAttack(int column, int row) {
        // Check if the given location is occupied by a Piece of the opposite color
        Piece piece = board.getPiece(column, row);
        if (piece != null && piece.isWhite != this.isWhite) {
            // Check if the Piece can move to the given location
            return isValidMovement(column, row);
        }
        return false;
    }
}
