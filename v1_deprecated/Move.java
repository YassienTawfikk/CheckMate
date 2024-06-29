import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Move implements MouseListener ,MouseMotionListener {
    Piece source;
    Move(Piece source){
        this.source = source;
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if(Chess.activePiece==null){
            Chess.activePiece = this.source;
            //this function will clarify to us the possible moves of the piece
            if(this.source.possibleMoves!=null)
            Chess.changeColor(this.source.possibleMoves);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(Chess.activePiece != null){
            Chess.activePiece = this.source;
            Chess.activePiece.setBounds((e.getXOnScreen() - Chess.blocksize/2)/64*Chess.blocksize,((e.getYOnScreen() - Chess.margin/2 - Chess.blocksize/2)/64)*Chess.blocksize + Chess.margin/2,Chess.blocksize,Chess.blocksize);
            Chess.frame.repaint();
        }      
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        //return the ordinary colors to the board
        Chess.resetColor(Chess.oldPieces);

        //check if the move isvalid or not
        boolean canMove = false;
        for (int[] pair : this.source.possibleMoves) {
            if (pair[0] == (e.getXOnScreen() - Chess.blocksize/2)/64 && pair[1] == (e.getYOnScreen() - Chess.margin/2 - Chess.blocksize/2)/64 && (Chess.getBlock((e.getXOnScreen() - Chess.blocksize/2)/64,(e.getYOnScreen() - Chess.margin/2 - Chess.blocksize/2)/64).piece != null ? Chess.getBlock((e.getXOnScreen() - Chess.blocksize/2)/64,(e.getYOnScreen() - Chess.margin/2 - Chess.blocksize/2)/64).piece.player != this.source.player : true) && Chess.activePlayer == this.source.player) {
                canMove = true;
                break;
            }
        }

        if(canMove){
            
            //stop the time of the player
            this.source.player.stopTime();
            //run the time for the other player
            if(this.source.player.number == 1){
                Chess.activePlayer = Chess.player2;
                Chess.player2.runTime();
            }
            else{
                Chess.activePlayer = Chess.player1;
                Chess.player1.runTime();
            } 

            Chess.frame.repaint();
            //changing coordinates of the piece
            //Chess.getBlock(this.source.x,this.source.y).piece = null;
            int oldX = this.source.x;
            int oldY = this.source.y;
            this.source.x = (e.getXOnScreen() - Chess.blocksize/2)/64;
            this.source.y = (e.getYOnScreen() - Chess.margin/2 - Chess.blocksize/2)/64;
            //updating the coordinates of the piece
            this.source.move_piece(false);
            
            if(Chess.activePiece!=null){
                //eat pieces condtion (check if there a piece in that position and the piece is not for the same player)
                if(Chess.getBlock(this.source.x,this.source.y).piece.value != null && 
                    Chess.getBlock(this.source.x,this.source.y).piece!=this.source &&
                    (Chess.getBlock(this.source.x,this.source.y).piece.player != this.source.player)
                ){
                    //remove from the board
                    Chess.frame.getContentPane().remove(Chess.getBlock(this.source.x,this.source.y).piece);
                    //add the eaten piece to the player that eat it
                    this.source.player.eaten_pieces[this.source.player.eaten_pieces_num] = Chess.getBlock(this.source.x,this.source.y).piece;
                    this.source.player.eaten_pieces_num++;
                    Player opponent;
                    if(this.source.player.number == 1)
                        opponent = Chess.player2;
                    else
                        opponent = Chess.player1;
                    new EatenPiece(Chess.getBlock(this.source.x,this.source.y).piece.value,opponent,this.source.player);
                    Chess.frame.repaint();
                }
                //change coordinates of the piece on the board
                this.source.setBounds((this.source.x)*Chess.blocksize,(this.source.y)*Chess.blocksize + Chess.margin/2,Chess.blocksize,Chess.blocksize);
                //add the refernce to the new block to the piece
                Chess.getBlock(this.source.x,this.source.y).piece = this.source;
                //remove the refernce to the piece from old block
                Chess.getBlock(oldX,oldY).piece = null;
                //add the refernce to the piece to the new block
                this.source.block = Chess.getBlock(this.source.x,this.source.y); 
                new Piece(oldX, oldY, null, null, Chess.getBlock(oldX,oldY));
            }
            Chess.movePieces();
        }
        else{
            this.source.setBounds(this.source.x*Chess.blocksize,this.source.y*Chess.blocksize  + Chess.margin/2,Chess.blocksize,Chess.blocksize);
        }
        //set active piece to null as we release it
        Chess.activePiece = null;
        Chess.frame.repaint();
    }

    //unnecessary methods

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }
}