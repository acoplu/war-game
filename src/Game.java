import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.event.MouseInputListener;

public class Game extends JFrame {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int DIMENSION = 10;

    ArrayList<Player> friends = new ArrayList<>();
    ArrayList<Player> enemies = new ArrayList<>();
    Player aircraft;

    Random generator = new Random();

    public class Enemy extends Thread{
        int move;
        int x;
        int y;
        public Enemy() {
            // Generate random int value from 1 to 50 (exclusive) - [1,50)
            x = generator.nextInt(WIDTH-2);
            y = generator.nextInt(HEIGHT-2);
        }

        public void run() {
            Player enemy = new Player(x*DIMENSION,y*DIMENSION, "enemy");
            enemy.setLocation(enemy.x, enemy.y);
            add(enemy);
            enemies.add(enemy);
        }

    }

    public class Friend extends Thread{
        int move;
        int x;
        int y;
        public Friend() {
            // Generate random int value from 1 to 50 (exclusive) - [1,50)
            x = generator.nextInt(WIDTH-2);
            y = generator.nextInt(HEIGHT-2);
        }

        public void run() {
            Player friend = new Player(x*DIMENSION,y*DIMENSION, "friend");
            friend.setLocation(friend.x, friend.y);
            friends.add(friend);
            add(friend);

            Bullet bull1 = new Bullet(friend.x+10,friend.y,1);
            Bullet bull2 = new Bullet(friend.x-10,friend.y,-1);
            add(bull1);
            add(bull2);
            new Thread(bull1).start();
            new Thread(bull2).start();
        }

    }

    public class AirCraft extends Thread {
        int move;
        int x;
        int y;
        public void run() {
            Game.this.aircraft = new Player(240,240, "aircraft");
            aircraft.setLocation(240,240);
            add(aircraft);
        }

    }

    private void moveAircraft(String to) {
        if(to.equalsIgnoreCase("up") && aircraft.getY() > 0) {
            aircraft.setLocation(aircraft.x,aircraft.y-10);
            aircraft.y -= 10;
        } else if(to.equalsIgnoreCase("down") && aircraft.getY() < 500) {
            aircraft.setLocation(aircraft.x,aircraft.y+10);
            aircraft.y += 10;
        } else if(to.equalsIgnoreCase("left") && aircraft.getX() > 0) {
            aircraft.setLocation(aircraft.x-10,aircraft.y);
            aircraft.x -= 10;
        } else if(to.equalsIgnoreCase("right") && aircraft.getX() < 500) {
            aircraft.setLocation(aircraft.x+10,aircraft.y);
            aircraft.x += 10;
        }
    }

    private class KeyActivities implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_LEFT) {
                System.out.println("left");
                moveAircraft("left");
            } else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
                System.out.println("left");
                moveAircraft("right");
            } else if(e.getKeyCode() == KeyEvent.VK_UP) {
                moveAircraft("up");
            } else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                moveAircraft("down");
            }
        }
        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
    }

    public Game() {
        super(); // default

        setSize(WIDTH*DIMENSION, HEIGHT*DIMENSION);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        addKeyListener(new KeyActivities());

        setVisible(true);
    }

}
