import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    ArrayList<Player> friends = new ArrayList<>();
    ArrayList<Player> enemies = new ArrayList<>();
    Player aircraft = new Player(240,240, "aircraft");

    ArrayList<Bullet> bullets = new ArrayList<>();

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

                    bullets.add(enemyFireRight);
                    bullets.add(enemyFireLeft);
                    add(enemyFireRight);
                    add(enemyFireLeft);

                    new Thread(enemyFireRight).start();
                    new Thread(enemyFireLeft).start();
                    fireDelay = 0;
                }
                for(int i=0; i<bullets.size(); i++) {
                    if(bullets.get(i).x == enemy.x && bullets.get(i).y == enemy.y) {
                        if(bullets.get(i).type.equalsIgnoreCase("friend") || bullets.get(i).type.equalsIgnoreCase("aircraft")) {
                            enemy.isDead = true;
                            enemies.remove(enemy);
                            remove(enemy);
                        }
                    }
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

                    bullets.add(friendFireRight);
                    bullets.add(friendFireLeft);
                    add(friendFireRight);
                    add(friendFireLeft);

                    new Thread(friendFireRight).start();
                    new Thread(friendFireLeft).start();
                    fireDelay = 0;
                }
                for(int i=0; i<bullets.size(); i++) {
                    if(bullets.get(i).x == friend.x && bullets.get(i).y == friend.y) {
                        if(bullets.get(i).type.equalsIgnoreCase("enemy")) {
                            friend.isDead = true;
                            friends.remove(friend);
                            remove(friend);
                        }
                    }
                }
            }
        }
    }

    public class AirCraft extends Thread {

        public void run() {
            aircraft.setLocation(240,240);
            add(aircraft);

            while(!aircraft.isDead) {
                for(int i=0; i<bullets.size(); i++) {
                    if(bullets.get(i).x == aircraft.x && bullets.get(i).y == aircraft.y) {
                        if(bullets.get(i).type.equalsIgnoreCase("enemy")) {
                            aircraft.isDead = true;
                            remove(aircraft);
                        }
                        remove(bullets.get(i));
                        bullets.remove(bullets.get(i));
                    }
                }
            }
            new GameFinish("Oyunu kaybettiniz");
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

        bullets.add(aircraftFireRight);
        bullets.add(aircraftFireLeft);
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