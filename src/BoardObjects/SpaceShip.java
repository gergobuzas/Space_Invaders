package BoardObjects;

import Controller.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * This class represents the player's ship.
 * It extends the BoardObject class.
 * It also contains the attributes and methods for the player's ship.
 * @author Gergo Buzas
 * @see BoardObject
 */
public class SpaceShip extends BoardObject{
    SpaceShipBullet bullet;

    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * @author Gergo Buzas
     */
    public SpaceShip(){
        this.x = Constants.SPACESHIP_INIT_X;
        this.y = Constants.SPACESHIP_INIT_Y;
        destroyed = false;
        bullet = new SpaceShipBullet(x, y);
        setImage(new ImageIcon("./src/imgs/spaceshipsized.png").getImage());
    }

    /**
     * This method shoots the player's bullet.
     * @author Gergo Buzas
     */
    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setX(x + 43);
        bullet.setY(y - 30);
    }

    /**
     * This method returns the player's bullet.
     * @return The player's bullet.
     * @author Gergo Buzas
     */
    public SpaceShipBullet getBullet() {
        return bullet;
    }

    /**
     * This method listens to the player's input.
     * The 'left arrow' or the 'A' moves the ship to the left.
     * The 'right arrow' or the 'D' moves the ship to the right.
     * The 'space' shoots the bullet.
     * @param e The key event. It contains the key code of the pressed key.
     * @author Gergo Buzas
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && bullet.getDestroyed()) {
            shoot();
        }
        if (key == KeyEvent.VK_LEFT && x >= Constants.BORDER_LEFT) {
            moveLeft();
        }
        if (key == KeyEvent.VK_A && x >= Constants.BORDER_RIGHT) {
            moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT && x <= Constants.BOARD_WIDTH - Constants.BORDER_RIGHT) {
            moveRight();
        }
        if (key == KeyEvent.VK_D && x <= Constants.BOARD_WIDTH - Constants.BORDER_RIGHT) {
            moveRight();
        }
    }
}
