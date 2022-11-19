package Controller;
import javax.swing.*;

public class SpaceInvaders extends JFrame {
    public SpaceInvaders() {
        add(new Board(this));
        setTitle("Space Invaders");
        setSize(Constans.BOARD_WIDTH, Constans.BOARD_HEIGHT);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
    }
}
