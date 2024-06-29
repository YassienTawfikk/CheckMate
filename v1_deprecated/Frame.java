import javax.swing.JFrame;

public class Frame extends JFrame{
    Frame(int frameSize,String title){
        this.setSize(frameSize,frameSize+22+Chess.margin);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(title);
    }
}
