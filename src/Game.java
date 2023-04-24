import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;
import java.awt.Color;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    ArrayList<Player> friends = new ArrayList<>();
    ArrayList<Player> enemies = new ArrayList<>();
    Player aircraft = new Player(240,240, "aircraft");

    Random generator = new Random();

    public class Enemy extends Thread{
        int move;
        int fireDelay = 0;
        int x;
        int y;
        
        public Enemy() {
            boolean flag = true;
            while(flag) {
                x = generator.nextInt(WIDTH/10 - 2)*10;
                y = generator.nextInt(HEIGHT/10 - 4)*10;

                if(noPlayerThere(x,y))
                    flag = false;
            }
        }
        
        public void enemySleep() {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
            fireDelay++;
        }

        public void run() {
            Player enemy = new Player(x,y, "enemy");
            enemy.setLocation(enemy.x, enemy.y);
            add(enemy);
            enemies.add(enemy);

            while(!enemy.isDead) {
                move = generator.nextInt(5);
                if(move == 1 && enemy.y > 0 && noPlayerThere(enemy.x, enemy.y-10)) {
                    enemy.setLocation(enemy.x,enemy.y-10);
                    enemy.y -= 10;
                    enemySleep();
                } else if(move == 2 && enemy.y < (HEIGHT-50) && noPlayerThere(enemy.x, enemy.y+10)) {
                    enemy.setLocation(enemy.x,enemy.y+10);
                    enemy.y += 10;
                    enemySleep();
                } else if(move == 3 && enemy.x > 0 && noPlayerThere(enemy.x-10, enemy.y)) {
                    enemy.setLocation(enemy.x-10,enemy.y);
                    enemy.x -= 10;
                    enemySleep();
                } else if(move == 4 && enemy.x < (WIDTH-30) && noPlayerThere(enemy.x+10, enemy.y)) {
                    enemy.setLocation(enemy.x+10,enemy.y);
                    enemy.x += 10;
                    enemySleep();
                }
                if(fireDelay == 2) {
                    Bullet enemyFireRight = new Bullet(enemy.x+10, enemy.y, 1, "enemy");
                    Bullet enemyFireLeft = new Bullet(enemy.x-10, enemy.y, -1, "enemy");

                    add(enemyFireRight);
                    add(enemyFireLeft);

                    new Thread(enemyFireRight).start();
                    new Thread(enemyFireLeft).start();
                    fireDelay = 0;
                }
            }
            if(enemies.size()==0)
                new GameFinish("Oyunu kazandınız");
        }
    }

    public class Friend extends Thread{
        int move;
        int fireDelay = 0;
        int x;
        int y;

        public Friend() {
            boolean flag = true;
            while(flag) {
                x = generator.nextInt(WIDTH/10 - 2)*10;
                y = generator.nextInt(HEIGHT/10 - 4)*10;

                if(noPlayerThere(x,y))
                    flag = false;
            }
        }

        public void friendSleep() {
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {
                System.out.println(e.getMessage());
            }
            fireDelay++;
        }

        public void run() {
            Player friend = new Player(x,y, "friend");
            friend.setLocation(friend.x, friend.y);
            friends.add(friend);
            add(friend);

            while(!friend.isDead) {
                move = generator.nextInt(5);
                if(move == 1 && friend.y > 0 && noPlayerThere(friend.x, friend.y-10)) {
                    friend.setLocation(friend.x,friend.y-10);
                    friend.y -= 10;
                    friendSleep();
                } else if(move == 2 && friend.y < (HEIGHT-50) && noPlayerThere(friend.x, friend.y+10)) {
                    friend.setLocation(friend.x,friend.y+10);
                    friend.y += 10;
                    friendSleep();
                } else if(move == 3 && friend.x > 0 && noPlayerThere(friend.x-10, friend.y)) {
                    friend.setLocation(friend.x-10,friend.y);
                    friend.x -= 10;
                    friendSleep();
                } else if(move == 4 && friend.x < (WIDTH-30) && noPlayerThere(friend.x+10, friend.y)) {
                    friend.setLocation(friend.x+10,friend.y);
                    friend.x += 10;
                    friendSleep();
                }
                if(fireDelay == 2) {
                    Bullet friendFireRight = new Bullet(friend.x+10, friend.y, 1, "friend");
                    Bullet friendFireLeft = new Bullet(friend.x-10, friend.y, -1, "friend");

                    add(friendFireRight);
                    add(friendFireLeft);

                    new Thread(friendFireRight).start();
                    new Thread(friendFireLeft).start();
                    fireDelay = 0;
                }
            }
        }
    }

    public class AirCraft extends Thread {

        public void run() {
            aircraft.setLocation(240,240);
            add(aircraft);
        }

    }

    public class Bullet extends JLabel implements Runnable{
        public static final int WIDTH=5;
        public static final int HEIGHT=5;

        int x;
        int y;
        int direction; // 1 and -1
        String type;

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
            if(type.equalsIgnoreCase("friend") ||type.equalsIgnoreCase("aircraft"))
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
                    for(int i=0; i<enemies.size(); i++) {
                        if (this.x == enemies.get(i).x && this.y == enemies.get(i).y) {
                            enemies.get(i).isDead = true;
                            Game.this.remove(enemies.get(i));
                            enemies.remove(enemies.get(i));
                            Game.this.remove(this);
                        }
                    }
                    for(int i=0; i<friends.size(); i++) {
                        if (friends.get(i).x == x && friends.get(i).y == y) {
                            Game.this.remove(this);
                        }
                    }
                    if (x==aircraft.x && y == aircraft.y) {
                        Game.this.remove(this);
                    }
                }
            else if(type.equalsIgnoreCase("enemy")) {
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
                    if (x==aircraft.x && y == aircraft.y) {
                        aircraft.isDead = true;
                        Game.this.remove(aircraft);
                        Game.this.remove(this);
                        new GameFinish("Oyunu kaybettiniz");
                    }
                    for(int i=0; i<friends.size(); i++) {
                        if (friends.get(i).x == x && friends.get(i).y == y) {
                            friends.get(i).isDead = true;
                            Game.this.remove(friends.get(i));
                            friends.remove(friends.get(i));
                            Game.this.remove(this);
                        }
                    }
                    for(int i=0; i<enemies.size(); i++) {
                        if (this.x == enemies.get(i).x && this.y == enemies.get(i).y) {
                            Game.this.remove(this);
                        }
                    }
                }
            }
        }
    }

    private void moveAircraft(String to) {
        if(to.equalsIgnoreCase("up") && aircraft.y > 0 && noPlayerThere(aircraft.x, aircraft.y-10)) {
            aircraft.setLocation(aircraft.x,aircraft.y-10);
            aircraft.y -= 10;
        } else if(to.equalsIgnoreCase("down") && aircraft.y < (HEIGHT-50) && noPlayerThere(aircraft.x, aircraft.y+10)) {
            aircraft.setLocation(aircraft.x,aircraft.y+10);
            aircraft.y += 10;
        } else if(to.equalsIgnoreCase("left") && aircraft.x > 0 && noPlayerThere(aircraft.x-10, aircraft.y)) {
            aircraft.setLocation(aircraft.x-10,aircraft.y);
            aircraft.x -= 10;
        } else if(to.equalsIgnoreCase("right") && aircraft.x < (WIDTH-30) && noPlayerThere(aircraft.x+10, aircraft.y)) {
            aircraft.setLocation(aircraft.x+10,aircraft.y);
            aircraft.x += 10;
        }
    }

    private void aircraftFire() {
        Bullet aircraftFireRight = new Bullet(aircraft.x+10, aircraft.y, 1, "aircraft");
        Bullet aircraftFireLeft = new Bullet(aircraft.x-10, aircraft.y, -1, "aircraft");

        add(aircraftFireRight);
        add(aircraftFireLeft);

        new Thread(aircraftFireRight).start();
        new Thread(aircraftFireLeft).start();
    }

    private boolean noPlayerThere(int x, int y) {
        for(int i=0; i<enemies.size(); i++) {
            if(enemies.get(i).x == x && enemies.get(i).y == y)
                return false;
        }

        for(int i=0; i<friends.size(); i++) {
            if(friends.get(i).x == x && friends.get(i).y == y)
                return false;
        }

        return x != aircraft.x || y != aircraft.y;
    }

    private int playerThere(int x, int y) {
        for(int i=0; i<enemies.size(); i++) {
            if(enemies.get(i).x == x && enemies.get(i).y == y)
                return i;
        }

        for(int i=0; i<friends.size(); i++) {
            if(friends.get(i).x == x && friends.get(i).y == y)
                return i;
        }

        if(x == aircraft.x && y == aircraft.y)
            return -1;

        return -2;
    }

    private class KeyActivities implements KeyListener {

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_A) {
                moveAircraft("left");
            } else if(e.getKeyCode() == KeyEvent.VK_D) {
                moveAircraft("right");
            } else if(e.getKeyCode() == KeyEvent.VK_W) {
                moveAircraft("up");
            } else if(e.getKeyCode() == KeyEvent.VK_S) {
                moveAircraft("down");
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {}
        @Override
        public void keyReleased(KeyEvent e) {}
    }

    private class MouseActivities implements MouseInputListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            aircraftFire();
        }

        @Override
        public void mousePressed(MouseEvent e) {}
        @Override
        public void mouseReleased(MouseEvent e) {}
        @Override
        public void mouseEntered(MouseEvent e) {}
        @Override
        public void mouseExited(MouseEvent e) {}
        @Override
        public void mouseDragged(MouseEvent e) {}
        @Override
        public void mouseMoved(MouseEvent e) {}
    }

    public Game() {
        super(); // default

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        addKeyListener(new KeyActivities());

        addMouseListener(new MouseActivities());
        addMouseMotionListener(new MouseActivities());

        setVisible(true);
    }

}