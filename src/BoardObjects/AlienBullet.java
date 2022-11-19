package BoardObjects;

import javax.swing.*;

public class AlienBullet extends BoardObject{

    public AlienBullet(int x, int y) {
        this.x = x;
        this.y = y;
        destroyed = true;
        visible = false;
        setImage(new ImageIcon("./src/imgs/alienbulletsized.png").getImage());
    }

}
