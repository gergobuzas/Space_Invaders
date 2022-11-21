package BoardObjects;

import javax.swing.*;

/**
 * This class represents the player's bullet.
 * It extends the BoardObject class.
 * @author Gergo Buzas
 * @see BoardObject
 */
public class SpaceShipBullet extends BoardObject {

    /**
     * This method is the constructor of the class.
     * @param cx The x coordinate of the bullet.
     * @param cy The y coordinate of the bullet.
     * @author Gergo Buzas
     */
    public SpaceShipBullet(int cx, int cy){
        this.x = cx;
        this.y = cy;
        destroyed = true;
        setImage(new ImageIcon("src/imgs/spaceshipbulletsized.png").getImage());
    }

}
