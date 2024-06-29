
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class EatenPiece extends JLabel{
    EatenPiece(String type,Player opponent,Player player){
        ImageIcon icon;
        icon = new ImageIcon("finalproject_chessgame-checkmate/Game/pieces/"+(player.number == 2?"white":"black")+"/"+type+".png"); 
        Image image = icon.getImage().getScaledInstance(25,25, Image.SCALE_SMOOTH);
        icon = new ImageIcon(image);
        this.setIcon(icon);
        this.setVisible(true);
        this.setOpaque(false);
        this.setBounds(player.eaten_pieces_num*20,35,20,20);
        if(opponent.number == 2)
            Chess.label1.add(this);
        else
            Chess.label2.add(this);
        Chess.frame.repaint();
    }
}
