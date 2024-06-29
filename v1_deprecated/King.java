import java.util.List;
import java.util.ArrayList;

public class King extends Piece {
    Player player;
    private boolean hasMoved;
    King(int x, int y, Player player,Block block) {
        super(x, y, player, "king",block);
        this.hasMoved = false;
        //reference to the player owned it
        this.player = player;
        this.move_piece(true);
    }
    //changine the possible moves when piece moves
    @Override
    public void move_piece(Boolean UpdateOnly){

        this.possibleMoves = new int[][] {
            {this.x - 1, this.y - 1},
            {this.x - 1, this.y},
            {this.x - 1, this.y + 1},
            {this.x, this.y - 1},
            {this.x, this.y + 1},
            {this.x + 1, this.y - 1},
            {this.x + 1, this.y},
            {this.x + 1, this.y + 1}
        };

        List<int[]> moves = new ArrayList<>();

        // Check squares one square away from the King
        for (int i = this.x - 1; i <= this.x + 1; i++) {
            for (int j = this.y - 1; j <= this.y + 1; j++) {
                if(Chess.getBlock(i,j)!=null){
                    if (i >= 0 && i < 8 && j >= 0 && j < 8 && !(i == this.x && j == this.y)) {
                        if (Chess.getBlock(i,j).piece == null || Chess.getBlock(i,j).piece.player != player) {
                            moves.add(new int[] {i, j});
                        }
                    }
                }
                
            }
        }

        // Check for castling move
        if (!this.hasMoved && !this.isInCheck()) {
            if (Chess.blocks[this.y][0].piece != null && !Chess.blocks[this.y][0].piece.hasMoved && Chess.blocks[this.y][1].piece == null && Chess.blocks[this.y][2].piece == null && Chess.blocks[this.y][3].piece == null) {
                boolean safe = true;
                for (int i = 0; i < 8; i++) {
                    Piece piece = Chess.blocks[this.y][i].piece;
                    if (piece != null && piece.player != player && piece.isAttackingSquare(this.y, 2)) {
                        safe = false;
                        break;
                    }
                }
                if (safe) {
                    moves.add(new int[] {this.x - 2, this.y});
                }
            }
            if(Chess.blocks[this.y][7]!=null&&Chess.blocks[this.y][7]!=null&&Chess.blocks[this.y][6]!=null&&Chess.blocks[this.y][5]!=null){
                if (Chess.blocks[this.y][7].piece != null && !Chess.blocks[this.y][7].piece.hasMoved && Chess.blocks[this.y][6].piece == null && Chess.blocks[this.y][5].piece == null) {
                    boolean safe = true;
                    for (int i = 0; i < 8; i++) {
                        Piece piece = Chess.blocks[this.y][i].piece;
                        if (piece != null && piece.player != player && piece.isAttackingSquare(this.y, 6)) {
                            safe = false;
                            break;
                        }
                    }
                    if (safe) {
                        moves.add(new int[] {this.x + 2, this.y});
                    }
                }
            }
        }

        // Remove squares that are under attack by an opponent's piece
        List<int[]> safeMoves = new ArrayList<>();
        for (int[] move : moves) {
            boolean safe = true;
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    Piece piece = Chess.blocks[i][j].piece;
                    if (piece != null && piece.player != player && piece.isAttackingSquare(move[1], move[0])) {
                        safe = false;
                        break;
                    }
                }
                if (!safe) {
                    break;
                }
            }
            if (safe) {
                safeMoves.add(move);
            }
        }
        this.possibleMoves = safeMoves.toArray(new int[safeMoves.size()][2]);
        if(!UpdateOnly)
        this.moves++;
    }

    // Check if the King is in check
    public boolean isInCheck() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(Chess.blocks[i][j]!=null){
                    Piece piece = Chess.blocks[i][j].piece;
                    if (piece != null && piece.player != player && piece.isAttackingSquare(this.y, this.x)) {
                        new GameOver(Chess.player1.name);
                        return true;
                    }
                }
                
            }
        }
        return false;
    }

}
