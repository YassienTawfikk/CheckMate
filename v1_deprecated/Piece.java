import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Piece extends JButton{
    public int x,y;
    int [][]possibleMoves;
    public String value;
    int moves = 0; 
    Player player;
    Block block;
    boolean hasMoved = true;
    Piece(int x,int y,Player player,String text,Block block){
        this.x = x;
        this.y = y;
        this.value = text;
        //reference to the block
        this.block = block;
        //reference to the piece on the block
        block.piece = this;
        if(text!=null){
            //setting the piece icon
            ImageIcon icon;
            icon = new ImageIcon("finalproject_chessgame-checkmate/Game/pieces/"+(player.number == 2?"white":"black")+"/"+text+".png"); 
            Image image = icon.getImage().getScaledInstance(Chess.blocksize - 10, Chess.blocksize - 10, Image.SCALE_SMOOTH);
            icon = new ImageIcon(image);
            this.setIcon(icon);
            //linking the piece to its player
            player.pieces[player.pieces.length - 1] = this;
            this.player = player;
        }
        //drawing the piece on the frame
        this.setBounds(x*Chess.blocksize,y*Chess.blocksize + Chess.margin/2,Chess.blocksize,Chess.blocksize);
        this.setVisible(true);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.addMouseListener(new Move(this));
        this.addMouseMotionListener(new Move(this));
    }

    public void move_piece(Boolean UpdateOnly){}

    public int[] checkBypass(int x,int y){
        Boolean canByPassX = false;
        Boolean canByPassY = false;
        for (int row = -7;row<=7;row++) {
            //check if there a obstacle piece
            if(Chess.getBlock(x,y-row)!=null){
                if(Chess.getBlock(x,y-row).piece.value==null)
                    canByPassY = true;
                else
                    canByPassY = false;
                    break;
            }
        }
        for (int col = -7;col<=7;col++) {
            //check if there a obstacle piece
            if(Chess.getBlock(x-col,y)!=null){
                if(Chess.getBlock(x-col,y).piece.value==null)
                    canByPassX = true;
                else
                    break;
            }
        }
        
        //return ((Chess.getBlock(x,y) != null ? Chess.getBlock(x,y).piece.value != null : false && canByPassX == false && canByPassY == false)? new int[]{-1,-1} : new int[]{x,y});
        return ( canByPassX == true && canByPassY == true)? new int[]{x,y} : new int[]{-1,-1};
    }

    
    // Check if a square is being attacked by the King
    public boolean isAttackingSquare(int x, int y) {
        if (Math.abs(this.x - x) != Math.abs(this.y - y)) {
            return false; // The Bishop can only attack squares on its diagonals
        }
        int dx = (x - this.x > 0) ? 1 : -1; // Determine the direction of movement in the x-axis
        int dy = (y - this.y > 0) ? 1 : -1; // Determine the direction of movement in the y-axis
        int i = this.x + dx;
        int j = this.y + dy;
        while (i != x && j != y) {
            if(Chess.blocks[j][i]!=null)
            if (Chess.blocks[j][i].piece != null) {
                return false; // The Bishop's path is blocked by another piece
            }
            i += dx;
            j += dy;
        }
        return ((Chess.blocks[y][x]!=null ? Chess.blocks[y][x].piece == null : false) || (Chess.blocks[y][x]!=null ? Chess.blocks[y][x].piece.player != this.player : false));
    }
}
