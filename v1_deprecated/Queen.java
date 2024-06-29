import java.util.List;
import java.util.ArrayList;

public class Queen extends Piece{
    Player player;
    Queen(int x, int y, Player player,Block block) {
        super(x, y, player, "queen",block);
        //reference to the player owned it
        this.player = player;
        //set the initial possible moves
        this.move_piece(true);
    }
    //changing the possible moves when piece moves
    @Override
    public void move_piece(Boolean UpdateOnly){
        if(player.number == 1){
            this.possibleMoves = new int[][]{
                    {this.x+3 , this.y+3},
                    {this.x-3 , this.y+3},
                    {this.x+1 , this.y},
                    {this.x-1 , this.y},
                    {this.x , this.y-7},
                    {this.x , this.y-6},
                    {this.x , this.y-5},
                    {this.x , this.y-4},
                    {this.x , this.y-3},
                    {this.x , this.y-2},
                    {this.x , this.y-1},
                    {this.x , this.y+1},
                    {this.x , this.y+2},
                    {this.x , this.y+3},
                    {this.x , this.y+4},
                    {this.x , this.y+5},
                    {this.x , this.y+6},
                    {this.x , this.y+7},
                    {this.x-7 , this.y},
                    {this.x-6 , this.y},
                    {this.x-5 , this.y},
                    {this.x-4 , this.y},
                    {this.x-3 , this.y},
                    {this.x-2 , this.y},
                    {this.x-1 , this.y},
                    {this.x+1 , this.y},
                    {this.x+2 , this.y},
                    {this.x+3 , this.y},
                    {this.x+4 , this.y},
                    {this.x+5 , this.y},
                    {this.x+6 , this.y},
                    {this.x+7 , this.y},
            };
        }
        else if(player.number == 2){
            this.possibleMoves = new int[][]{
                    {this.x+3 , this.y-3},
                    {this.x-3 , this.y-3},
                    {this.x+1 , this.y},
                    {this.x-1 , this.y},
                    {this.x , this.y-7},
                    {this.x , this.y-6},
                    {this.x , this.y-5},
                    {this.x , this.y-4},
                    {this.x , this.y-3},
                    {this.x , this.y-2},
                    {this.x , this.y-1},
                    {this.x , this.y+1},
                    {this.x , this.y+2},
                    {this.x , this.y+3},
                    {this.x , this.y+4},
                    {this.x , this.y+5},
                    {this.x , this.y+6},
                    {this.x , this.y+7},
                    {this.x-7 , this.y},
                    {this.x-6 , this.y},
                    {this.x-5 , this.y},
                    {this.x-4 , this.y},
                    {this.x-3 , this.y},
                    {this.x-2 , this.y},
                    {this.x-1 , this.y},
                    {this.x+1 , this.y},
                    {this.x+2 , this.y},
                    {this.x+3 , this.y},
                    {this.x+4 , this.y},
                    {this.x+5 , this.y},
                    {this.x+6 , this.y},
                    {this.x+7 , this.y},
            };
        }
        List<int[]> moves = new ArrayList<>();

        // Check squares in the same row
        for (int i = this.x + 1; i < 8; i++) {
            if(Chess.blocks[this.y][i]!=null){
                if (Chess.blocks[this.y][i].piece.value == null) {
                    moves.add(new int[] {i, this.y});
                } else {
                    if (Chess.blocks[this.y][i].piece.player != player) {
                        moves.add(new int[] {i, this.y});
                    }
                    break;
                }
            }
        }
        for (int i = this.x - 1; i >= 0; i--) {
            if(Chess.blocks[this.y][i]!=null){
                if (Chess.blocks[this.y][i].piece.value == null) {
                    moves.add(new int[] {i, this.y});
                } else {
                    if (Chess.blocks[this.y][i].piece.player != player) {
                        moves.add(new int[] {i, this.y});
                    }
                    break;
                }
            }
        }

        // Check squares in the same column
        for (int j = this.y + 1; j < 8; j++) {
            if(Chess.blocks[j][this.x]!=null){
                if (Chess.blocks[j][this.x].piece.value == null) {
                    moves.add(new int[] {this.x, j});
                } else {
                    if (Chess.blocks[j][this.x].piece.player != player) {
                        moves.add(new int[] {this.x, j});
                    }
                    break;
                }
            }
        }
        for (int j = this.y - 1; j >= 0; j--) {
            if(Chess.blocks[j][this.x]!=null){
                if (Chess.blocks[j][this.x].piece.value == null) {
                    moves.add(new int[] {this.x, j});
                } else {
                    if (Chess.blocks[j][this.x].piece.player != player) {
                        moves.add(new int[] {this.x, j});
                    }
                    break;
                }
            }
        }
        this.possibleMoves = moves.toArray(new int[moves.size()][2]);
        if(!UpdateOnly)
        this.moves++;
    }
}
