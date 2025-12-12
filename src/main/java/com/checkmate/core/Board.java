package com.checkmate.core;

import com.checkmate.pieces.*;
import com.checkmate.ui.GameFrame;
import com.checkmate.ui.GameOver;
import com.checkmate.ui.Theme; // Import Theme
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Board class definition
public class Board extends JPanel {

    static ArrayList<Piece> gamePieceList = new ArrayList<>();

    public int tileSize = 80;
    public Piece selectedPiece;

    CheckMate checkMate = new CheckMate(this);
    int rows = 8;
    int columns = 8;
    int vmCircleRadius = 25; // Adjusted size

    Input input = new Input(this);

    public Board() {
        // Remove hardcoded logic, use Theme
        this.setPreferredSize(new Dimension(columns * tileSize, rows * tileSize));
        this.addMouseListener(input);
        this.addMouseMotionListener(input);
        addPiece();
    }

    public Piece getPiece(int column, int row) {
        for (Piece piece : gamePieceList)
            if ((piece.column == column) && (piece.row == row))
                return piece;
        return null;
    }

    public boolean isValidMove(Move move) {
        // Basic Validation
        if (sameTeam(move.piece, move.capture))
            return false;
        if (!(move.newColumn < 8 && move.newColumn >= 0) && !(move.newRow < 8 && move.newRow >= 0))
            return false;
        if (!move.piece.isValidMovement(move.newColumn, move.newRow))
            return false;
        if (move.piece.moveHitsPiece(move.newColumn, move.newRow))
            return false;

        // Simulation for Check
        int oldCol = move.piece.column;
        int oldRow = move.piece.row;
        Piece captured = getPiece(move.newColumn, move.newRow);

        // Temporarily apply
        if (captured != null) {
            gamePieceList.remove(captured);
        }
        move.piece.column = move.newColumn;
        move.piece.row = move.newRow;

        // Check safety
        boolean kingSafe = !checkMate.isKingChecked(move);

        // Revert
        move.piece.column = oldCol;
        move.piece.row = oldRow;
        if (captured != null) {
            gamePieceList.add(captured);
        }

        return kingSafe;
    }

    public boolean sameTeam(Piece p1, Piece p2) {
        if (p1 == null || p2 == null)
            return false;
        return p1.isWhite == p2.isWhite;
    }

    public boolean validTurn() {
        if (selectedPiece == null)
            return false;
        return Move.counter % 2 == 0 == selectedPiece.isWhite;
    }

    Piece findKing(boolean isWhite) {
        for (Piece piece : gamePieceList)
            if (isWhite == piece.isWhite && piece.name.equals("King"))
                return piece;
        return null;
    }

    public void makeMove(Move move) {
        if (move.piece != null) {
            move.piece.column = move.newColumn;
            move.piece.row = move.newRow;
            move.piece.xPos = move.newColumn * tileSize;
            move.piece.yPos = move.newRow * tileSize;

            capture(move.capture, false);
            move.piece.isFirstMove = false;

            if (move.piece.name.equals("Pawn") && move.piece.row == move.piece.rowEnd) {
                pawnPromote(move);
            }
            if (move.piece.name.equals("King") && move.piece.isFirstMove) {
                kingCastle(move);
            }
            // Update Turn Label in UI
            if (Main.frame instanceof GameFrame) {
                ((GameFrame) Main.frame).updateTurnLabel(Move.counter % 2 != 0); // Next turn
            }
        }
        if (isGameOver(move)) {
            String winner = move.piece.isWhite ? "White" : "Black";
            new GameOver(winner);
            Main.frame.dispose();
        }
    }

    public boolean isGameOver(Move lastMove) {
        // The player who just moved (lastMove.piece.isWhite) is checking if the
        // opponent
        // (!lastMove.piece.isWhite) is mated.
        boolean opponentIsWhite = !lastMove.piece.isWhite;
        Piece opponentKing = findKing(opponentIsWhite);

        // 1. Check if ANY valid move exists for the opponent
        // Create a copy of the list to avoid ConcurrentModificationException during
        // simulation
        // (as isValidMove modifies the list temporarily)
        for (Piece piece : new java.util.ArrayList<>(gamePieceList)) {
            // Check only opponent pieces
            if (piece.isWhite == opponentIsWhite) {
                // Try every possible move for this piece
                for (int c = 0; c < columns; c++) {
                    for (int r = 0; r < rows; r++) {
                        Move testMove = new Move(this, piece, c, r);
                        if (isValidMove(testMove)) {
                            // If we find ONE valid move, game is not over.
                            return false;
                        }
                    }
                }
            }
        }

        // 2. No valid moves found.
        // If King is in check -> Checkmate.
        // If King NOT in check -> Stalemate (we treat as game over too, but maybe
        // different message?)
        // For simplicity, treating "No Valid Moves + In Check" as Checkmate.

        // Check if opponent King is currently in check.
        // We can create a dummy move to pass to isKingChecked, or use findKing directly
        // in CheckMate.
        // Assuming checkMate.isKingChecked(dummyMove) works based on current board
        // state.

        // Wait, isKingChecked takes a 'Move' object to hypothetically update board.
        // But here we want to check CURRENT state.
        // If we pass a dummy move that basically means "King stays where he is",
        // isKingChecked logic:
        // if (board.selectedPiece != null && board.selectedPiece.name.equals("King")) {
        // ... update positsion ... }
        // If we pass a move with piece=King, newCol=curCol, newRow=curRow.

        Move checkStatusMove = new Move(this, opponentKing, opponentKing.column, opponentKing.row);
        if (checkMate.isKingChecked(checkStatusMove)) {
            return true; // Checkmate
        } else {
            // Stalemate!
            // System.out.println("Stalemate");
            // User asked for "Game Over and what color won".
            // Ideally we return true here too?
            // Main logic in Input.java says: if (isGameOver) -> Winner is lastMover.
            // If Stalemate, last Mover didn't win.
            // But for now, returning false for Stalemate to avoid confusion?
            // Or returning true but handling winner differently?
            // User goal: "game over isn't detected". Usually implies checkmate.
            // Let's return true for checkmate only for now to solve the bug.
            return false;
        }
    }

    public void capture(Piece piece, Boolean promotion) {
        gamePieceList.remove(piece);
        if (piece != null) {
            String[] eatenPiecesArr = piece.isWhite ? GameFrame.p2EatenPieces : GameFrame.p1EatenPieces;
            int count = 0;
            for (String eatenPiece : eatenPiecesArr)
                if (eatenPiece != null)
                    count++;
            if (count < eatenPiecesArr.length) {
                eatenPiecesArr[count] = piece.name;
            }

            if (Main.frame instanceof GameFrame) {
                // If piece is white (captured by black), we want to update Player 2's panel
                // (Black player)
                // promotion logic seems inverted in original: promotion ? !piece.isWhite :
                // piece.isWhite
                // Let's stick to simpler logic: The 'eaten pieces' panel for Player X shows the
                // pieces of Player Y that X captured.
                // Our refactored GameFrame.drawEatenPiece(..., isWhiteGetter)
                // If piece.isWhite is true (White piece captured), then Black (Player 2)
                // captured it. isWhiteGetter = false.
                // So pass false.
                ((GameFrame) Main.frame).drawEatenPiece(piece.name, !piece.isWhite);
            }
        }
    }

    public boolean canProtect(int column, int row, Piece piece) {
        return isValidMove(new Move(this, piece, column, row));
    }

    public void pawnPromote(Move move) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Promote Pawn", true);
        dialog.setUndecorated(true);
        dialog.setLayout(new GridLayout(1, 4, 10, 10));
        dialog.setSize(400, 120);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(Theme.PANEL_DARK);
        ((JPanel) dialog.getContentPane()).setBorder(BorderFactory.createLineBorder(Theme.ACCENT_BLUE, 2));

        String[] pieces = { "Queen", "Rook", "Bishop", "Knight" };
        String color = move.piece.isWhite ? "white" : "black";

        for (String p : pieces) {
            JButton btn = new JButton();
            btn.setFocusPainted(false);
            btn.setBackground(Theme.BACKGROUND_DARK);
            btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Load Icon
            try {
                java.net.URL url = getClass().getResource("/images/Pieces/" + color + "/" + p + ".png");
                if (url != null) {
                    Image img = new ImageIcon(url).getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                    btn.setIcon(new ImageIcon(img));
                } else {
                    btn.setText(p);
                    btn.setForeground(Theme.TEXT_WHITE);
                }
            } catch (Exception e) {
                btn.setText(p);
            }

            btn.addActionListener(e -> {
                switch (p) {
                    case "Queen":
                        gamePieceList.add(new Queen(this, move.newColumn, move.newRow, move.piece.isWhite));
                        break;
                    case "Rook":
                        gamePieceList.add(new Rook(this, move.newColumn, move.newRow, move.piece.isWhite));
                        break;
                    case "Bishop":
                        gamePieceList.add(new Bishop(this, move.newColumn, move.newRow, move.piece.isWhite));
                        break;
                    case "Knight":
                        gamePieceList.add(new Knight(this, move.newColumn, move.newRow, move.piece.isWhite));
                        break;
                }
                dialog.dispose();
            });
            dialog.add(btn);
        }

        dialog.setVisible(true);
        capture(move.piece, true);
    }

    private void kingCastle(Move move) {
        if (move.piece.column == 6) {
            Piece rook = getPiece(7, move.piece.row);
            if (rook != null) {
                rook.column = 5;
                rook.xPos = rook.column * tileSize;
            }
        } else if (move.piece.column == 2) {
            Piece rook = getPiece(0, move.piece.row);
            if (rook != null) {
                rook.column = 3;
                rook.xPos = rook.column * tileSize;
            }
        }
    }

    public boolean isSquareUnderAttack(int col, int row, boolean byWhite) {
        for (Piece piece : gamePieceList) {
            if (piece.isWhite == byWhite) {
                if (piece.canAttack(col, row)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addPiece() {
        gamePieceList.clear(); // Clear list on new game?
        // Reuse logic but ensure list is fresh or static handled correctly.
        // Original was static list. If we create new Board(), we might duplicate if not
        // cleared.
        // But gamePieceList is static in original code... that's bad design but I must
        // follow it or fix it.
        // Fix: clear it.

        for (int c = 0; c < columns; c++)
            gamePieceList.add(new Pawn(this, c, 1, false));
        for (int c = 0; c < columns; c++)
            gamePieceList.add(new Pawn(this, c, 6, true));

        gamePieceList.add(new King(this, 4, 0, false));
        gamePieceList.add(new King(this, 4, 7, true));
        gamePieceList.add(new Queen(this, 3, 0, false));
        gamePieceList.add(new Queen(this, 3, 7, true));

        gamePieceList.add(new Bishop(this, 5, 0, false));
        gamePieceList.add(new Bishop(this, 2, 0, false));
        gamePieceList.add(new Bishop(this, 5, 7, true));
        gamePieceList.add(new Bishop(this, 2, 7, true));

        gamePieceList.add(new Knight(this, 1, 0, false));
        gamePieceList.add(new Knight(this, 6, 0, false));
        gamePieceList.add(new Knight(this, 1, 7, true));
        gamePieceList.add(new Knight(this, 6, 7, true));

        gamePieceList.add(new Rook(this, 0, 0, false));
        gamePieceList.add(new Rook(this, 7, 0, false));
        gamePieceList.add(new Rook(this, 0, 7, true));
        gamePieceList.add(new Rook(this, 7, 7, true));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw Board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                g2d.setColor((c + r) % 2 == 0 ? Theme.TILE_LIGHT : Theme.TILE_DARK);
                g2d.fillRect(c * tileSize, r * tileSize, tileSize, tileSize);
            }
        }

        // Draw Highlights
        if (this.selectedPiece != null) {
            // Highlight selected tile
            g2d.setColor(new Color(255, 255, 0, 100)); // Semi-transparent yellow
            g2d.fillRect(selectedPiece.column * tileSize, selectedPiece.row * tileSize, tileSize, tileSize);

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    if (isValidMove(new Move(this, selectedPiece, c, r)) && validTurn()) {
                        Piece target = getPiece(c, r);
                        if (target == null) {
                            // Valid empty move - visual dot
                            g2d.setColor(Theme.MOVE_HIGHLIGHT);
                            int padding = (tileSize - vmCircleRadius) / 2;
                            g2d.fillOval(c * tileSize + padding, r * tileSize + padding, vmCircleRadius,
                                    vmCircleRadius);
                        } else {
                            // Valid capture - corner indicators
                            g2d.setColor(new Color(255, 69, 58, 200)); // Red highlight
                            Graphics2D g2 = (Graphics2D) g2d.create();
                            g2.setStroke(new BasicStroke(4));
                            g2.drawRect(c * tileSize + 2, r * tileSize + 2, tileSize - 4, tileSize - 4);
                            g2.dispose();
                        }
                    }
                }
            }
        }

        // Draw Pieces
        for (Piece piece : gamePieceList) {
            piece.paint(g2d);
        }
    }
}
