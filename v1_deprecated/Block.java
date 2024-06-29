import java.awt.Color;
import javax.swing.JButton;

public class Block extends JButton {
    int x,y;
    Piece piece = null;
    Block(Color bg,int x,int y,Piece piece){
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.setBounds(x*Chess.blocksize,y*Chess.blocksize + Chess.margin/2,Chess.blocksize,Chess.blocksize);
        this.setBackground(bg);
        this.setOpaque(true);
        this.setVisible(true);
        this.setBorderPainted(false);
    }
}
