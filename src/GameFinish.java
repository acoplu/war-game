import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FlowLayout;

public class GameFinish extends JFrame {

    public GameFinish(String text) {
        super();

        setSize(300,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setResizable(false);

		JLabel label1=new JLabel(text);
        add(label1);

		setVisible(true);
    }
}
