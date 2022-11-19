package Controller;
import javax.swing.*;

public class SpaceInvaders extends JFrame {
    public SpaceInvaders() {
        add(new GameController(this));
        setTitle("Space Invaders");
        setSize(Constants.BOARD_WIDTH, Constants.BOARD_HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
