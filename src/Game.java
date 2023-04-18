import javax.swing.JFrame;
import java.awt.Graphics;

public class Game extends JFrame {

    public class Enemy extends Thread{

    }

    public class Friend extends Thread{

    }

    public class AirCraft extends Thread{

    }

    public Game() {
        super(); // default

        setSize(500,500);

        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);
    }

}
