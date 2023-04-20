import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.FlowLayout;

public class GameFinish extends JFrame {

    public GameFinish(String text) {
        super();

        setSize(200,150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        setResizable(false);

		JLabel label1=new JLabel(text);
        label1.setBounds(50,50,100,50);
        add(label1);

		setVisible(true);
    }
}
