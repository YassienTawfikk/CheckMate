import java.util.List;
import java.util.ArrayList;

public class Knight extends Piece{
    Player player;
    Knight(int x, int y, Player player,Block block) {
        super(x, y, player, "knight",block);
        //reference to the player owned it
        this.player = player;
        //set the initial possible moves
        this.move_piece(true);
    }
    //changine the possible moves when piece moves
    @Override
    public void move_piece(Boolean UpdateOnly){
        if(player.number == 1){
            this.possibleMoves = new int[][]{
                {this.x+2 , this.y+3},
                {this.x+3 , this.y+2},
                {this.x-2 , this.y+3},
                {this.x-3 , this.y+2},
            };
        }
        else if(player.number == 2){
            this.possibleMoves = new int[][]{
                {this.x+2 , this.y-3},
                {this.x+3 , this.y-2},
                {this.x-2 , this.y-3},
                {this.x-3 , this.y-2},
            };
        }
        if(!UpdateOnly)
        this.moves++;
    }
}
