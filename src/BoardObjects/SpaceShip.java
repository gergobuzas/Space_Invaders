package BoardObjects;

import Controller.Constans;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class SpaceShip extends BoardObject{
    SpaceShipBullet bullet;

    public SpaceShip(){
        this.x = Constans.SPACESHIP_INIT_X;
        this.y = Constans.SPACESHIP_INIT_Y;
        destroyed = false;
        visible = true;
        bullet = new SpaceShipBullet(x, y);
        setImage(new ImageIcon("./src/imgs/spaceshipsized.png").getImage());
    }

    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setVisible(true);
        bullet.setX(x + 50);
        bullet.setY(y - 30);
    }

    public SpaceShipBullet getBullet() {
        return bullet;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && !bullet.getVisible() && bullet.getDestroyed()) {
            shoot();
        }
        if (key == KeyEvent.VK_LEFT && x >= Constans.BORDER_LEFT) {
            moveLeft();
        }
        if (key == KeyEvent.VK_RIGHT && x <= Constans.BOARD_WIDTH - Constans.BORDER_RIGHT) {
            moveRight();
        }
    }
}
