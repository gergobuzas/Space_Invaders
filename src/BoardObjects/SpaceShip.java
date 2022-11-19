package BoardObjects;

import Controller.Constants;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SpaceShip extends BoardObject{
    SpaceShipBullet bullet;

    public SpaceShip(){
        this.x = Constants.SPACESHIP_INIT_X;
        this.y = Constants.SPACESHIP_INIT_Y;
        destroyed = false;
        bullet = new SpaceShipBullet(x, y);
        setImage(new ImageIcon("./src/imgs/spaceshipsized.png").getImage());
    }

    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setX(x + 50);
        bullet.setY(y - 30);
    }

    public SpaceShipBullet getBullet() {
        return bullet;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && bullet.getDestroyed()) {
            shoot();
        }
        if (key == KeyEvent.VK_LEFT && x >= Constants.BORDER_LEFT) {
            moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT && x <= Constants.BOARD_WIDTH - Constants.BORDER_RIGHT) {
            moveRight();
        }
    }
}
