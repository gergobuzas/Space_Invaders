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


public class GameController extends JPanel{
    ArrayList<AlienShip> aliens;
    SpaceShip player;

    Direction direction = Direction.LEFT;
    int deaths;
    int score;
    int multiplier;

    boolean inGame = true;
    final Timer timer;
    final JFrame mainWindow;


    public GameController(JFrame cWindow) {
        Constants.BOMB_FREQ = 1000;
        multiplier = 1;
        deaths = 0;
        score = 0;
        mainWindow = cWindow;
        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        timer = new Timer(Constants.DELAY, new GameCycle());
        timer.start();
        newRound();
    }

    private void newRound() {
        aliens = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {
                AlienShip newAlien = new AlienShip(Constants.ALIEN_INIT_X + 50 * j * 3, Constants.ALIEN_INIT_Y + 50 * i * 2);
                aliens.add(newAlien);
            }
        }
        player = new SpaceShip();
        deaths = 0;
    }



    //DRAWINGS
    private void drawAliens(Graphics g) {
        for (AlienShip alien : aliens) {
            if (alien.getDestroyed()) {
                alien.die();
            }
            else
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        }
    }

    private void drawPlayer(Graphics g) {
        if (!player.getDestroyed()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    private void drawPlayerBullet(Graphics g) {
        if (!player.getBullet().getDestroyed()) {
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
        g.fillRect(0, 0, Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        g.setColor(Color.lightGray);
        if (inGame) {
            g.drawLine(0, Constants.GROUND, Constants.BOARD_WIDTH, Constants.GROUND);
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
            gameOver();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver() throws IOException {
        for (int i = 0; i < LeaderBoard.getTop10List().size(); i++) {
            if (score > LeaderBoard.getTop10List().get(i).getScore()) {
                gameOverTop10(i);
                return;
            }
        }
        gameOverNotTop10(this.mainWindow);
    }


    private void gameOverTop10(int idx) throws IOException {
        String name = JOptionPane.showInputDialog("You got the "+ (idx + 1) + ". highest score!\nEnter your name:");
        LeaderBoard.getTop10List().add(idx, new LeaderBoard.Score(name, score));
        LeaderBoard.getTop10List().remove(10);
        LeaderBoard.save();
        mainWindow.setVisible(false);
    }

    private void gameOverNotTop10(JFrame mainWindow) throws IOException {
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
        gameOverWindow.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }








    private void tick() {
        if (deaths == Constants.NUMBER_OF_ALIENS_TO_DESTROY) {
            if (Constants.BOMB_FREQ > 200) {
                Constants.BOMB_FREQ -= 100;
                multiplier += 1;
            }
            else if (Constants.BOMB_FREQ > 50) {
                Constants.BOMB_FREQ -= 10;
                multiplier += 3;
            }
            newRound();
        }

        // player's bullet
        if (!player.getBullet().getDestroyed()) {
            int playerBulletX = player.getBullet().getX();
            int playerBulletY = player.getBullet().getY();
            for (AlienShip alien : aliens) {
                int alienX = alien.getX();
                int alienY = alien.getY();
                int alienBulletX = alien.getBullet().getX();
                int alienBulletY = alien.getBullet().getY();

                //Checking whether player's bullet hit an alien
                if (!alien.getDestroyed() && !player.getBullet().getDestroyed()) {
                    if (playerBulletX >= (alienX) && playerBulletX <= (alienX + Constants.ALIEN_WIDTH) &&
                            playerBulletY >= (alienY) && playerBulletY <= (alienY + Constants.ALIEN_HEIGHT)) {
                        alien.die();
                        player.getBullet().die();
                        deaths++;
                        score += 100 * multiplier;
                    }
                }

                //Checking whether player's bullet hit an alien's bullet
                if (!alien.getBullet().getDestroyed() && !player.getBullet().getDestroyed()) {
                    if ((playerBulletX >= (alienBulletX) && playerBulletX <= (alienBulletX + Constants.ALIEN_BULLET_WIDTH) &&
                            playerBulletY >= (alienBulletY) && playerBulletY <= (alienBulletY + Constants.ALIEN_BULLET_HEIGHT)) ||
                            (alienBulletX >= (playerBulletX) && alienBulletX <= (playerBulletX + Constants.ALIEN_BULLET_WIDTH) &&
                                    playerBulletY >= (alienBulletY) && playerBulletY <= (alienBulletY + Constants.ALIEN_BULLET_HEIGHT))
                    ) {
                        alien.getBullet().die();
                        player.getBullet().die();
                    }
                }
            }

            if (player.getBullet().getY() + Constants.MOVE_UP < 0) {
                player.getBullet().die();
            } else {
                player.getBullet().moveUp();
            }
        }

        // Aliens
        for (AlienShip alien : aliens) {
            int x = alien.getX();
            if (x >= Constants.BOARD_WIDTH - Constants.BORDER_RIGHT && direction != Direction.LEFT) {
                direction = Direction.LEFT;
                for (AlienShip a : aliens) {
                    a.moveDown();
                }
            }

            if (x <= Constants.BORDER_LEFT  && direction != Direction.RIGHT) {
                direction = Direction.RIGHT;
                for (AlienShip a : aliens) {
                    a.moveDown();
                }
            }
        }

        for (AlienShip alien : aliens) {
            if (!alien.getDestroyed()) {
                if (alien.getY() > Constants.GROUND - Constants.ALIEN_HEIGHT) {
                    inGame = false;
                }
                alien.move(direction);
            }
        }

        // AlienBullets
        Random generator = new Random();
        for (AlienShip alien : aliens) {
            int shotDrop = generator.nextInt(Constants.BOMB_FREQ);
            if (shotDrop == 1 && !alien.getDestroyed() && alien.getBullet().getDestroyed()) {
                alien.shoot();
            }

            int bulletX = alien.getBullet().getX();
            int bulletY = alien.getBullet().getY();
            int playerX = player.getX();
            int playerY = player.getY();
            if (!player.getDestroyed() && !alien.getBullet().getDestroyed()) {
                if (bulletX >= (playerX) && bulletX <= (playerX + Constants.SPACESHIP_WIDTH) && bulletY >= (playerY) &&
                        bulletY <= (playerY + Constants.SPACESHIP_HEIGHT)) {
                    player.die();
                    inGame = false;
                    alien.getBullet().die();
                }
            }

            if (!alien.getBullet().getDestroyed()) {
                alien.getBullet().moveDown();
                if (alien.getBullet().getY() >= Constants.GROUND - Constants.ALIEN_BULLET_HEIGHT) {
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

    public enum Direction {
        LEFT, RIGHT
    }
}
