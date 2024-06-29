/*
The Board class defines the chess board with a set number of rows and columns.
The class extends JPanel and overrides the paintComponent method to draw the board and the pieces on it.
It also contains methods to add and remove pieces, capture opponent pieces, check the validity of a move and get a piece object at a given location on the board.
 */


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Board class definition
public class Board extends JPanel {

    // Declare Resizeable array to hold all the pieces present on the board.
    static ArrayList<Piece> gamePieceList = new ArrayList<>();

    public int tileSize = 80; // size of each tile in pixels
    public Piece selectedPiece; //Piece selected by the mouse

    CheckMate checkMate = new CheckMate(this);
    int rows = 8; // number of rows on the chess board
    int columns = 8; // number of columns on the chess board
    int vmCircleRadius = 30; //Valid Moves Circle Radius


    // Define two colors (Dark & Light) for the tiles




    Input input = new Input(this);//Define object from Class Input
    Color color1,color2;

    // Board constructor
    public Board() {
        if(GameFrame.theme == "Green"){
            color1 = new Color(118, 150, 86);
            color2 = new Color(225, 225, 225);
        }
        else if(GameFrame.theme == "Blue"){
            color1 = new Color(11, 15, 86);
            color2 = new Color(225, 225, 225);
        }
        // Set the size of the board panel
        this.setPreferredSize(new Dimension(columns * tileSize, rows * tileSize));

        //Adds the specified mouse events for the game
        this.addMouseListener(input);
        this.addMouseMotionListener(input);

        addPiece();//Call the method for adding pieces on the board
    }


    //Method return a piece held in a specific tile
    public Piece getPiece(int column, int row) {
        for (Piece piece : gamePieceList)
            if ((piece.column == column) && (piece.row == row))
                return piece;
        return null; //If there is no pieces found in this tile
    }

    //Method to check if move that will be made is valid
    public boolean isValidMove(Move move) {

        //Make sure the piece eating and eaten not at the same team
        if (sameTeam(move.piece, move.capture)) {
            return false;
        }

        //Set the Limits for the game board
        if (!(move.newColumn < 8 && move.newColumn >= 0) && !(move.newRow < 8 && move.newRow >= 0)) {
            return false;
        }

        //Check if it's piece's valid move
        if (!move.piece.isValidMovement(move.newColumn, move.newRow)) {
            return false;
        }

        //Hit another piece check (for pieces that can not bypass another piece)
        if (move.piece.moveHitsPiece(move.newColumn, move.newRow)) {
            return false;
        }

        //Check if the move will make or keep the King Checked
        return !checkMate.isKingChecked(move);
    }


    //Method to check if two pieces are on same team or not
    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null) {
            //Make sure there is a piece on the specified tile
            return false;
        }
        return p1.isWhite == p2.isWhite;
    }

    //Method to set the turns of play
    public boolean validTurn() {
        return Move.counter % 2 == 0 == selectedPiece.isWhite;
    }


    //Method that to get the King
    Piece findKing(boolean isWhite) {
        for (Piece piece : gamePieceList)
            if (isWhite == piece.isWhite && piece.name.equals("King"))
                return piece;
        return null;
    }


    //Method to move a piece
    public void makeMove(Move move) {
        if(move.piece!=null){
            // Update the position of the piece being moved
            move.piece.column = move.newColumn;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newColumn * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            capture(move.capture,false); // Capture enemy's piece presented in the selected tile
            //Set the 'isFirstMove' boolean to false
            move.piece.isFirstMove = false;
            //Pawn Promotion if it reaches the End Row
            if (move.piece.name.equals("Pawn") && move.piece.row == move.piece.rowEnd) {
                pawnPromote(move);
            }
            //Open the option for King Castling if it's King first move
            if (move.piece.name.equals("King") && move.piece.isFirstMove) {
                kingCastle(move);
            }
        }
        // Check if the move puts the current player's king in check
        if (isGameOver(move)) {
            // The current player's king is in check, so the game is over.
            //JOptionPane.showMessageDialog(null, "game-over");
            new GameOver();
            Main.frame.dispose();
            return;
        }
    }


    public boolean isGameOver(Move move) {

        // Find the player's king
        Piece king = findKing(!move.piece.isWhite);
        if (king == null) {
            return false;
        }

        boolean attacked = false;
        boolean ptotected_piece = false,hasValidMoves=false;

        // Check if the king is attacked by any of the enemy pieces
        for (Piece piece : gamePieceList) {
            if (piece.isWhite == move.piece.isWhite) {
                if (piece.canAttack(king.column, king.row)) {;
                    attacked = true;
                    break;
                }
            }
        }

        // Check if the king is protected by any of the player's pieces
        for (Piece piece : gamePieceList) {
            if (piece.isWhite != move.piece.isWhite) {
                if (canProtect(king.column, king.row,piece)) {
                    ptotected_piece = true;
                    break;
                }
            }
        }

        // Check if the king has any valid moves to escape the check
        for (int i = king.column - 1; i <= king.column + 1; i++) {
            for (int j = king.row - 1; j <= king.row + 1; j++) {
                if (i == king.column && j == king.row) {
                    continue;
                }
                if (i >= 0 && i < 8 && j >= 0 && j < 8) {

                    if (isValidMove(new Move(this, king, i, j))) {
                        hasValidMoves = true;
                        break;
                    }
                }
            }


        }

        /*if(isValidMove(new Move(this, king, king.column-1, king.row-1)) ||
                isValidMove(new Move(this, king, king.column-1, king.row+1)) ||
                isValidMove(new Move(this, king, king.column-1, king.row)) ||
                isValidMove(new Move(this, king, king.column+1, king.row-1)) ||
                isValidMove(new Move(this, king, king.column+1, king.row+1)) ||
                isValidMove(new Move(this, king, king.column+1, king.row)) ||
                isValidMove(new Move(this, king, king.column, king.row-1))||
                isValidMove(new Move(this, king, king.column, king.row+1))){
            hasValidMoves = true;
        }*/


        System.out.println(attacked);
        System.out.println(ptotected_piece);
        System.out.println(hasValidMoves);
        if(attacked){
            if(ptotected_piece){
                return false;
            }
            else{
                if(hasValidMoves){
                    return false;
                }
                else{
                    return true;
                }

            }
        }
        else{
            return false;
        }

    }

    /**
     * Returns the position of the king of the specified color.
     *
     * @param isWhite true if the king is white, false if the king is black.
     * @return the position of the king.
     */
    public int[] getKingPosition(boolean isWhite) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = getPiece(col, row);
                if (piece instanceof King && piece.isWhite == isWhite) {
                    System.out.println("case 2");
                    return new int[]{col, row};

                }
            }
        }
        return null; // King not found on the board
    }



    // Helper method to check if the current player's king is in check


    public void capture(Piece piece,Boolean promotion) {
        //Method to remove the eaten piece from the game
        //Remove the piece from the resizeable array
        gamePieceList.remove(piece);
        //drawing eaten pieces on the player panel
        if (piece!=null){
            String[] eatenPiecesArr = piece.isWhite ? GameFrame.p2EatenPieces : GameFrame.p1EatenPieces;
            //pushing the new eaten piece to its list
            int count = 0;
            for (String eatenPiece : eatenPiecesArr)
                if (eatenPiece != null) count++;
            eatenPiecesArr[count]=piece.name;
            //drawing it to the main frame
            Main.frame.drawEatenPiece(piece.name,promotion?!piece.isWhite:piece.isWhite);
        }
    }

    public boolean canProtect(int column, int row,Piece piece) {
        // Check if the piece can move to the specified tile to protect the king
        return isValidMove(new Move(this, piece, column, row ));
        /*return isValidMove(new Move(this, piece, column - 1, row - 1)) ||
                isValidMove(new Move(this, piece, column - 1, row + 1)) ||
                isValidMove(new Move(this, piece, column - 1, row)) ||
                isValidMove(new Move(this, piece, column + 1, row - 1)) ||
                isValidMove(new Move(this, piece, column + 1, row + 1)) ||
                isValidMove(new Move(this, piece, column + 1, row)) ||
                isValidMove(new Move(this, piece, column, row - 1)) ||
                isValidMove(new Move(this, piece, column,row + 1));*/
    }

    //Method to Castle the King with the rook
    //Method to Promote the pawn to Queen
    public void pawnPromote(Move move) {
        // Display a menu to the player asking them which piece they want to promote the pawn to.
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(null, "Choose a piece to promote the pawn to", "Pawn Promotion", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        // Create a new piece of that type and place it on the square where the pawn was located.
        switch (choice) {
            case 0:
                gamePieceList.add(new Queen(this, move.newColumn, move.newRow, move.piece.isWhite));
                break;
            case 1:
                gamePieceList.add(new Rook(this, move.newColumn, move.newRow, move.piece.isWhite));
                break;
            case 2:
                gamePieceList.add(new Bishop(this, move.newColumn, move.newRow, move.piece.isWhite));
                break;
            case 3:
                gamePieceList.add(new Knight(this, move.newColumn, move.newRow, move.piece.isWhite));
                break;
        }

        //gamePieceList.remove(move.capture);
        capture(move.piece,true);
    }


    private void kingCastle(Move move) {

        // If the king is castling to the right
        if (move.piece.column == 6) {
            // Get the rook piece located at the right edge of the board
            Piece rook = getPiece(7, move.piece.row);
            // Move the rook to the left of the king
            rook.column = 5;
            rook.xPos = rook.column * tileSize;
        } else if (move.piece.column == 2) {
            // Get the rook piece located at the right edge of the board
            Piece rook = getPiece(0, move.piece.row);
            // Move the rook to the left of the king
            rook.column = 3;
            rook.xPos = rook.column * tileSize;
        }
    }

    //Put the pieces on the board
    public void addPiece() {
        //Adding Pieces to the Resizeable Game array

        //Black Pawns
        for (int c = 0; c < columns; c++) {
            gamePieceList.add(new Pawn(this, c, 1, false));
        }
        //White Pawns
        for (int c = 0; c < columns; c++) {
            gamePieceList.add(new Pawn(this, c, 6, true));
        }
        //Black King
        gamePieceList.add(new King(this, 4, 0, false));
        //White King
        gamePieceList.add(new King(this, 4, 7, true));
        //Black Queen
        gamePieceList.add(new Queen(this, 3, 0, false));
        //White Queen
        gamePieceList.add(new Queen(this, 3, 7, true));
        //Black Bishop
        gamePieceList.add(new Bishop(this, 5, 0, false));
        gamePieceList.add(new Bishop(this, 2, 0, false));
        //White Bishop
        gamePieceList.add(new Bishop(this, 5, 7, true));
        gamePieceList.add(new Bishop(this, 2, 7, true));
        //Black Knights
        gamePieceList.add(new Knight(this, 1, 0, false));
        gamePieceList.add(new Knight(this, 6, 0, false));
        //White Knights
        gamePieceList.add(new Knight(this, 1, 7, true));
        gamePieceList.add(new Knight(this, 6, 7, true));
        //Black Rook
        gamePieceList.add(new Rook(this, 0, 0, false));
        gamePieceList.add(new Rook(this, 7, 0, false));
        //White Rook
        gamePieceList.add(new Rook(this, 0, 7, true));
        gamePieceList.add(new Rook(this, 7, 7, true));
    }

    // Override the paintComponent method to draw the game
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Draw the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                // Set the color of the current tile based on its position on the board
                g2d.setColor((c + r) % 2 == 0 ? color2 : color1);
                // Fill the current tile with the selected color
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        //Make a shape for valid moves of the selected piece with  circle
        if (this.selectedPiece != null) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    //Check if it's valid Move
                    if (isValidMove(new Move(this, selectedPiece, c, r))) {

                        //Check if it's valid turn to play
                        if (validTurn()) {

                            //Check if there is an enemy's piece on this tile
                            if (getPiece(c, r) == null) {

                                // Set the fill color to green and fill the circle
                                g2d.setColor(new Color(20, 150, 0, 255));
                                g2d.fillRoundRect(c * tileSize + 25, r * tileSize + 25, vmCircleRadius, vmCircleRadius, vmCircleRadius, vmCircleRadius);

                                // Set the stroke color to black and draw the round rectangle with a border
                                g2d.setStroke(new BasicStroke(1));
                                g2d.setColor(Color.BLACK);
                                g2d.drawRoundRect(c * tileSize + 25, r * tileSize + 25, vmCircleRadius, vmCircleRadius, vmCircleRadius, vmCircleRadius);
                            }
                            //If there is enemy piece on this tile
                            else {

                                //Set the fill color to red and fill the tile
                                g2d.setColor(Color.BLACK);
                                g2d.setStroke(new BasicStroke(5));
                                g2d.drawRect(c * tileSize, r * tileSize, tileSize, tileSize);
                                g2d.setColor(new Color(126, 0, 0));
                                g2d.fillRect(c * tileSize + 2, r * tileSize + 2, tileSize - 4, tileSize - 4);
                            }
                        }
                        //if it's not valid turn
                        else {

                            //Check if there is an enemy's piece on this tile
                            if (getPiece(c, r) == null) {

                                // Set the fill color to yellow and fill the circle
                                g2d.setColor(new Color(255, 248, 46, 255));
                                g2d.fillRoundRect(c * tileSize + 25, r * tileSize + 25, vmCircleRadius, vmCircleRadius, vmCircleRadius, vmCircleRadius);

                                // Set the stroke color to yellow and draw the round rectangle with a border
                                g2d.setStroke(new BasicStroke(1));
                                g2d.setColor(new Color(255, 248, 46));
                                g2d.drawRoundRect(c * tileSize + 25, r * tileSize + 25, vmCircleRadius, vmCircleRadius, vmCircleRadius, vmCircleRadius);

                            }
                            //If there is enemy piece on this tile
                            else {

                                //Set the fill color to yellow and fill the tile
                                g2d.setColor(Color.BLACK);
                                g2d.setStroke(new BasicStroke(5));
                                g2d.drawRect(c * tileSize, r * tileSize, tileSize, tileSize);
                                g2d.setColor(new Color(255, 248, 46));
                                g2d.fillRect(c * tileSize + 2, r * tileSize + 2, tileSize - 4, tileSize - 4);
                            }
                        }
                    }
                }
            }
        }


        if (selectedPiece != null && checkMate.isKingChecked(new Move(this, selectedPiece, selectedPiece.column, selectedPiece.row))) {
            boolean kingCheckedIsWhite = (Move.counter % 2 == 0);
            int kingColumn = findKing(kingCheckedIsWhite).column;
            int kingRow = findKing(kingCheckedIsWhite).row;
            //Set the fill color to red and fill the tile
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(5));
            g2d.drawRoundRect(kingColumn * tileSize, kingRow * tileSize, tileSize, tileSize, tileSize, tileSize);
            g2d.setColor(new Color(255, 51, 59));
            g2d.fillOval(kingColumn * tileSize + 2, kingRow * tileSize + 2, tileSize - 4, tileSize - 4);
        }


        //Draw each piece on the Board
        for (Piece piece : gamePieceList) {
            piece.paint(g2d);
        }
    }
}
