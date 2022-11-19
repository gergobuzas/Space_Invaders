package BoardObjects;

import javax.swing.*;

public class SpaceShipBullet extends BoardObject {

    public SpaceShipBullet(int cx, int cy){
        this.x = cx;
        this.y = cy;
        destroyed = true;
        setImage(new ImageIcon("src/imgs/spaceshipbulletsized.png").getImage());
    }

}
