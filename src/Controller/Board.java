package Controller;

import BoardObjects.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;


public class Board extends JPanel{
    private ArrayList<AlienShip> aliens;
    private SpaceShip player;

    private int direction = -1;
    private int deaths = 0;
    private int score = 0;
    private int multiplier;

    private boolean inGame = true;
    private final Timer timer;
    private final JFrame mainWindow;


    public Board(JFrame cWindow) {
        Constans.BOMB_FREQ = 1000;
        multiplier = 1;
        mainWindow = cWindow;
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        timer = new Timer(Constans.DELAY, new GameCycle());
        timer.start();
        newRound();
    }

    private void newRound() {
        aliens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                AlienShip newAlien = new AlienShip(Constans.ALIEN_INIT_X + 50 * j * 3, Constans.ALIEN_INIT_Y + 50 * i * 2);
                aliens.add(newAlien);
            }
        }
        player = new SpaceShip();
        deaths = 0;
    }



    //DRAWINGS
    private void drawAliens(Graphics g) {
        for (AlienShip alien : aliens) {
            if (alien.getVisible()) {
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
            }
            if (alien.getDestroyed()) {
                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {
        if (player.getVisible()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    private void drawPlayerBullet(Graphics g) {
        if (player.getBullet().getVisible()) {
            g.drawImage(player.getBullet().getImage(), player.getBullet().getX(), player.getBullet().getY(), this);
        }
    }

    private void drawAlienBullets(Graphics g) {
        for (AlienShip alien : aliens) {
            AlienBullet b = alien.getBullet();
            if (!b.getDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
        g.drawString("Score: " + score, 30, 935);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            doDrawing(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void doDrawing(Graphics g) throws IOException {
        g.setColor(Color.black);
        g.fillRect(0, 0, Constans.BOARD_WIDTH, Constans.BOARD_HEIGHT);
        g.setColor(Color.lightGray);
        if (inGame) {
            g.drawLine(0, Constans.GROUND, Constans.BOARD_WIDTH, Constans.GROUND);
            drawAliens(g);
            drawPlayer(g);
            drawPlayerBullet(g);
            drawAlienBullets(g);
            drawScore(g);
        }
        else {
            if (timer.isRunning()) {
                timer.stop();
            }
            gameOver(mainWindow);
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(JFrame mainWindow) throws IOException {
        JFrame gameOverWindow = new JFrame();
        JPanel gameOverScreen = new JPanel();
        JButton okButton =  new JButton("OK");
        BufferedImage gameOverPicture = ImageIO.read(new File("./src/imgs/gameover.jpg"));
        JLabel gameOverLabel = new JLabel(new ImageIcon(gameOverPicture));

        okButton.addActionListener(e -> {
            gameOverWindow.dispose();
            mainWindow.setVisible(false);
        });

        gameOverScreen.add(gameOverLabel);
        gameOverScreen.add(okButton);
        BoxLayout boxlayout = new BoxLayout(gameOverScreen, BoxLayout.X_AXIS);
        gameOverScreen.setLayout(boxlayout);

        gameOverWindow.add(gameOverScreen);
        gameOverWindow.pack();
        gameOverWindow.setTitle("Game Over");
        gameOverWindow.setLocationRelativeTo(null);
        gameOverWindow.setVisible(true);
        gameOverWindow.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

    }







    private void tick() {
        if (deaths == Constans.NUMBER_OF_ALIENS_TO_DESTROY) {
            if (Constans.BOMB_FREQ > 200) {
                Constans.BOMB_FREQ -= 100;
                multiplier += 1;
            }
            else if (Constans.BOMB_FREQ > 50) {
                Constans.BOMB_FREQ -= 10;
                multiplier += 3;
            }
            newRound();
        }

        // player's bullet
        if (player.getBullet().getVisible()) {
            int playerBulletX = player.getBullet().getX();
            int playerBulletY = player.getBullet().getY();
            for (AlienShip alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();
                int alienBulletX = alien.getBullet().getX();
                int alienBulletY = alien.getBullet().getY();

                //Checking whether player's bullet hit an alien
                if (alien.getVisible() && player.getBullet().getVisible()) {
                    if (playerBulletX >= (alienX) && playerBulletX <= (alienX + Constans.ALIEN_WIDTH) &&
                            playerBulletY >= (alienY) && playerBulletY <= (alienY + Constans.ALIEN_HEIGHT)) {
                        alien.die();
                        player.getBullet().die();
                        deaths++;
                        score += 100 * multiplier;
                    }
                }

                //Checking whether player's bullet hit an alien's bullet
                if (alien.getBullet().getVisible() && player.getBullet().getVisible()) {
                    if ((playerBulletX >= (alienBulletX) && playerBulletX <= (alienBulletX + Constans.ALIEN_BULLET_WIDTH) &&
                            playerBulletY >= (alienBulletY) && playerBulletY <= (alienBulletY + Constans.ALIEN_BULLET_HEIGHT)) ||
                            (alienBulletX >= (playerBulletX) && alienBulletX <= (playerBulletX + Constans.ALIEN_BULLET_WIDTH) &&
                                    playerBulletY >= (alienBulletY) && playerBulletY <= (alienBulletY + Constans.ALIEN_BULLET_HEIGHT))
                    ) {
                        alien.getBullet().die();
                        player.getBullet().die();
                    }
                }
            }

            if (player.getBullet().getY() + Constans.MOVE_UP < 0) {
                player.getBullet().die();
            } else {
                player.getBullet().moveUp();
            }
        }

        // Aliens ---- dir = -1 left, dir = 1 right
        for (AlienShip alien : aliens) {
            int x = alien.getX();
            if (x >= Constans.BOARD_WIDTH - Constans.BORDER_RIGHT && direction != -1) {
                direction = -1;
                for (AlienShip a : aliens) {
                    a.moveDown();
                }
            }

            if (x <= Constans.BORDER_LEFT  && direction != 1) {
                direction = 1;
                for (AlienShip a : aliens) {
                    a.moveDown();
                }
            }
        }

        for (AlienShip alien : aliens) {
            if (alien.getVisible()) {
                if (alien.getY() > Constans.GROUND - Constans.ALIEN_HEIGHT) {
                    inGame = false;
                }
                alien.move(direction);
            }
        }

        // AlienBullets
        Random generator = new Random();
        for (AlienShip alien : aliens) {
            int shotDrop = generator.nextInt(Constans.BOMB_FREQ);
            if (shotDrop == 1 && alien.getVisible() && alien.getBullet().getDestroyed()) {
                alien.shoot();
            }

            int bulletX = alien.getBullet().getX();
            int bulletY = alien.getBullet().getY();
            int playerX = player.getX();
            int playerY = player.getY();
            if (player.getVisible() && !alien.getBullet().getDestroyed()) {
                if (bulletX >= (playerX) && bulletX <= (playerX + Constans.SPACESHIP_WIDTH) && bulletY >= (playerY) &&
                        bulletY <= (playerY + Constans.SPACESHIP_HEIGHT)) {
                    player.die();
                    inGame = false;
                    alien.getBullet().die();
                }
            }

            if (!alien.getBullet().getDestroyed()) {
                alien.getBullet().moveDown();
                if (alien.getBullet().getY() >= Constans.GROUND - Constans.ALIEN_BULLET_HEIGHT) {
                    alien.getBullet().die();
                }
            }
        }
    }

    private void doGameCycle() {
        tick();
        repaint();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}
