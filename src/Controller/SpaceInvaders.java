package Controller;
import javax.swing.*;

/**
 * This class represents the window of the game.
 * @author Gergo Buzas
 * @see JFrame
 */
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
