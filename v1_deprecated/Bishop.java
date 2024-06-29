import java.util.List;
import java.util.ArrayList;

public class Bishop extends Piece{
    Player player;
    Bishop(int x, int y, Player player,Block block) {
        super(x, y, player, "bishop",block);
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
                    {this.x+3 , this.y+3},
                    {this.x-3 , this.y+3},
                    {this.x+1 , this.y},
                    {this.x-1 , this.y},
            };
        }
        else if(player.number == 2){
            this.possibleMoves = new int[][]{
                    {this.x+3 , this.y-3},
                    {this.x-3 , this.y-3},
                    {this.x+1 , this.y},
                    {this.x-1 , this.y},
            };
        }
        if(!UpdateOnly)
        this.moves++;
    }
}
