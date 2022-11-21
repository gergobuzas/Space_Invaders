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

/**
 * This class represents the game.
 * The game is a JPanel.
 * It contains the attributes and methods for the game.
 * @author Gergo Buzas
 * @see javax.swing.JPanel
 */
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


    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * It makes the game visible.
     * It starts the timer, which is responsible for the game's speed.
     * It also adds the key listener to the game.
     * It also adds the action listener to the timer.
     * @param cWindow The main window of the game.
     * @author Gergo Buzas
     */
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

    /**
     * This method starts a new round.
     * It initializes the attributes of the class.
     * It also adds the aliens to the game.
     * It also adds the player to the game.
     * It also resets the aliens' death counter.
     * @author Gergo Buzas
     */
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


    /**
     * This method paints the aliens.
     * @param g The graphics object of the game. It is used to draw the aliens.
     * @author Gergo Buzas
     */
    private void drawAliens(Graphics g) {
        for (AlienShip alien : aliens) {
            if (alien.getDestroyed()) {
                alien.die();
            }
            else
                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
        }
    }

    /**
     * This method draws the player's ship.
     * @param g The graphics object of the game. It is used to draw the player's ship.
     * @author Gergo Buzas
     */
    private void drawPlayer(Graphics g) {
        if (!player.getDestroyed()) {
            g.drawImage(player.getImage(), player.getX(), player.getY(), this);
        }
    }

    /**
     * This method draws the player's bullet.
     * @param g The graphics object of the game. It is used to draw the bullet.
     * @author Gergo Buzas
     */
    private void drawPlayerBullet(Graphics g) {
        if (!player.getBullet().getDestroyed()) {
            g.drawImage(player.getBullet().getImage(), player.getBullet().getX(), player.getBullet().getY(), this);
        }
    }

    /**
     * This method draws the aliens' bullets.
     * @param g The graphics object. It is used to draw the bullets.
     * @author Gergo Buzas
     */
    private void drawAlienBullets(Graphics g) {
        for (AlienShip alien : aliens) {
            AlienBullet b = alien.getBullet();
            if (!b.getDestroyed()) {
                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    /**
     * This method draws the score.
     * @param g The graphics object of the game. It is used to draw the score.
     * @author Gergo Buzas
     */
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

    /**
     * This method draws the game.
     * It draws the aliens.
     * It draws the player.
     * It draws the player's bullet.
     * It draws the aliens' bullets.
     * It draws the score.
     * It is in sync by the "Toolkit.getDefaultToolkit().sync()" method.
     * @param g The graphics object of the game.
     * @throws IOException If the image file is not found.
     * @author Gergo Buzas
     */
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

    /**
     * This method is called when the game is over.
     * It sends the game over message.
     * If the score is in the top 10 highest, it asks the player's name, so that their name can be put on the leaderboard.
     * If the score is not in the top 10 highest, it just sends the game over message.
     * @throws IOException If the image file is not found.
     * @author Gergo Buzas
     */
    private void gameOver() throws IOException {
        for (int i = 0; i < LeaderBoard.getTop10List().size(); i++) {
            if (score > LeaderBoard.getTop10List().get(i).getScore()) {
                gameOverTop10(i);
                return;
            }
        }
        gameOverNotTop10(this.mainWindow);
    }

    /**
     * This method is called when the score is in the top 10 highest.
     * @param idx The index of the score in the top 10 highest. It is used to put the player's name on the leaderboard.
     * @throws IOException If the score cannot be saved to top10.txt
     * @author Gergo Buzas
     */
    private void gameOverTop10(int idx) throws IOException {
        String name = JOptionPane.showInputDialog("You got the "+ (idx + 1) + ". highest score!\nEnter your name:");
        LeaderBoard.getTop10List().add(idx, new LeaderBoard.Score(name, score));
        LeaderBoard.getTop10List().remove(10);
        LeaderBoard.save();
        mainWindow.setVisible(false);
    }

    /**
     * This method is called when the score is not in the top 10 highest.
     * @param mainWindow The main window of the game. It is used to close the main window (Game) after pressing the "OK" button.
     * @throws IOException If the image file is not found.
     * @author Gergo Buzas
     */
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







    /**
     * This method is called to step the game.
     * It is called by the GameCycle, which is then called by the Timer.
     * It checks if the game's round is over.
     * It moves the aliens.
     * It moves the aliens' bullets.
     * It moves the player's bullet.
     * It checks if the player's bullet hits an alien.
     * It checks if the player's bullet hits an alien's bullet.
     * It checks if the aliens' bullets hit the player.
     * It checks if the aliens hit the player.
     * It checks if the aliens hit the ground.
     * @author Gergo Buzas
     */
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

        //Moves the player's bullet.
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

            //Checking whether player's bullet hit the top
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

    /**
     * This method is called when the timer calls it.
     * It calls the tick method.
     * It repaints the game.
     * @author Gergo Buzas
     */
    private void doGameCycle() {
        tick();
        repaint();
    }

    /**
     * This method represents the game's cycle.
     * @author Gergo Buzas
     */
    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }


    /**
     * This class is used to handle the keyboard input.
     * @author Gergo Buzas
     */
    private class TAdapter extends KeyAdapter {
        /**
         * This method is called when a key is pressed.
         * @param e the event to be processed
         * @author Gergo Buzas
         */
        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }

    /**
     * This enum represents the direction of the aliens.
     * @author Gergo Buzas
     */
    public enum Direction {
        LEFT, RIGHT
    }
}
