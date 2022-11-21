package BoardObjects;

import javax.swing.*;

/**
 * This class represents the alien's bullet.
 * It extends the BoardObject class.
 * @author Gergo Buzas
 * @see BoardObject
 */
public class AlienBullet extends BoardObject{

    /**
     * This method is the constructor of the class.
     * @param x The x coordinate of the bullet.
     * @param y The y coordinate of the bullet.
     * @author Gergo Buzas
     */
    public AlienBullet(int x, int y) {
        this.x = x;
        this.y = y;
        destroyed = true;
        setImage(new ImageIcon("./src/imgs/alienbulletsized.png").getImage());
    }

}
