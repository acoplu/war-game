import javax.swing.JPanel;
import java.awt.Color;
public class Bullet extends JPanel implements Runnable{
    public static final int WIDTH=10;
    public static final int HEIGHT=10;

    int x;
    int y;
    int direction; // 1 and -1

    public Bullet(int x, int y, int direction) {
        super();

        this.x = x;
        this.y = y;
        this.direction = direction;
        setSize(WIDTH,HEIGHT);

        setBackground(Color.ORANGE);
        setOpaque(true);
    }

    public void run() {
        while(x>-10 && y>-10 && x<550 && y<550) {
            try {
                Thread.sleep(100);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if(direction == 1) {
                setLocation(x+10,y);
                x += 10;
            } else if(direction == -1) {
                setLocation(x-10,y);
                x -= 10;
            }
        }
    }
}
