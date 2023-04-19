import javax.swing.JLabel;
import java.awt.Color;
public class Bullet extends JLabel implements Runnable{
    public static final int WIDTH=5;
    public static final int HEIGHT=5;

    int x;
    int y;
    int direction; // 1 and -1
    String type = "";

    public Bullet(int x, int y, int direction, String type) {
        super();

        this.x = x;
        this.y = y;
        this.direction = direction;
        this.type = type;
        setSize(WIDTH,HEIGHT);

        if(type.equalsIgnoreCase("enemy"))
            setBackground(Color.BLUE);
        else if(type.equalsIgnoreCase("friend"))
            setBackground(Color.MAGENTA);
        else if(type.equalsIgnoreCase("aircraft"))
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

    public String toString() {
        return ("X: "+x+" Y: "+y+" Type: "+type+" Direction: "+direction);
    }

}
