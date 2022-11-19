package BoardObjects;

import javax.swing.*;

public class AlienShip extends BoardObject{
    AlienBullet bullet;

    public AlienShip(int x, int y){
        this.x = x;
        this.y = y;
        setImage(new ImageIcon("./src/imgs/alienshipsized.png").getImage());
        bullet = new AlienBullet(x, y);
    }

    public void move(int direction){
        if( direction == -1){
            moveLeft();
        }
        else {
            moveRight();
        }
    }

    public void shoot(){
        bullet.setDestroyed(false);
        bullet.setX(x + 20);
        bullet.setY(y);
    }


    public AlienBullet getBullet() {
        return bullet;
    }

}
