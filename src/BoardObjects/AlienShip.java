package BoardObjects;

import Controller.GameController;

import javax.swing.*;

/**
 * This class represents the alien's ship.
 * It extends the BoardObject class.
 * It also contains the attributes and methods for the alien's ship.
 * @author Gergo Buzas
 * @see BoardObject
 */
public class AlienShip extends BoardObject{
    AlienBullet bullet;

/**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * @param x The x coordinate of the alien's ship.
     * @param y The y coordinate of the alien's ship.
     * @author Gergo Buzas
     */
    public AlienShip(int x, int y){
        this.x = x;
        this.y = y;
        setImage(new ImageIcon("./src/imgs/alienshipsized.png").getImage());
        bullet = new AlienBullet(x, y);
    }

    /**
     * This method moves the alien's ship.
     * @param direction The direction where the ship should go.
     * @author Gergo Buzas
     */
    public void move(GameController.Direction direction){
        if( direction == GameController.Direction.LEFT){
            moveLeft();
        }
        else {
            moveRight();
        }
    }

    /**
     * This method shoots the alien's bullet.
     * @author Gergo Buzas
     */
    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setX(x);
        bullet.setY(y - 20);
    }


    /**
     * This method returns the alien's bullet.
     * @return The alien's bullet.
     * @author Gergo Buzas
     */
    public AlienBullet getBullet() {
        return bullet;
    }

}
