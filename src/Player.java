import javax.swing.JLabel;
import java.awt.Color;

public class Player extends JLabel{

    public static final int WIDTH = 10;
    public static final int HEIGHT = 10;
    int x;
    int y;
    String type; // Can be friend, enemy or aircraft
    boolean isDead = false;

    public Player(int startX, int startY, String type) {
        this.x = startX;
        this.y = startY;
        this.type = type;

        setSize(WIDTH, HEIGHT);

        if(type.equalsIgnoreCase("enemy"))
            setBackground(Color.BLACK);
        else if(type.equalsIgnoreCase("friend"))
            setBackground(Color.GREEN);
        else if(type.equalsIgnoreCase("aircraft"))
            setBackground(Color.RED);

        setOpaque(true);
    }

    public String toString() {
        return ("X: "+x+" Y: "+y+" Type: "+type+" Dead: "+isDead);
    }

}
