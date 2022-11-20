package BoardObjects;

import Controller.GameController;

import javax.swing.*;

public class AlienShip extends BoardObject{
    AlienBullet bullet;

    public AlienShip(int x, int y){
        this.x = x;
        this.y = y;
        setImage(new ImageIcon("./src/imgs/alienshipsized.png").getImage());
        bullet = new AlienBullet(x, y);
    }

    public void move(GameController.Direction direction){
        if( direction == GameController.Direction.LEFT){
            moveLeft();
        }
        else {
            moveRight();
        }
    }

    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setX(x);
        bullet.setY(y - 20);
    }


    public AlienBullet getBullet() {
        return bullet;
    }

}
