package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents the main menu of the game.
 * It extends the JPanel class.
 * It contains the attributes and methods for the main menu.
 * @author Gergo Buzas
 * @see javax.swing.JPanel
 */
public class Menu extends JPanel {
    LeaderBoard leaderBoard;

    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * It adds the JMenuItem's to the JMenuBar.
     * It adds the JMenuBar to the JPanel.
     * It adds the buttons to the JPanel.
     * It adds the action listeners to the buttons.
     * It adds the action listeners to the JMenuItem's.
     * @author Gergo Buzas
     * @throws IOException If the Space Invaders image file is not found.
     */
    public Menu() throws IOException {
        leaderBoard = new LeaderBoard();
        JMenu fileMenu = new JMenu ("File");
        JMenuItem newGameItem = new JMenuItem ("New Game");
        JMenuItem leaderboardItem = new JMenuItem ("Leaderboard");
        JMenuItem exitItem = new JMenuItem ("Exit");
        fileMenu.add (newGameItem);
        fileMenu.add (leaderboardItem);
        fileMenu.add (exitItem);

        JMenu helpMenu = new JMenu ("Help");
        JMenuItem contentsItem = new JMenuItem ("Contents");
        JMenuItem aboutItem = new JMenuItem ("About");
        helpMenu.add (contentsItem);
        helpMenu.add (aboutItem);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add (fileMenu);
        menuBar.add (helpMenu);

        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");
        BufferedImage myPicture = ImageIO.read(new File("./src/imgs/logo.png"));
        JLabel titleLabel = new JLabel(new ImageIcon(myPicture));

        setPreferredSize (new Dimension (800, 700));
        setLayout (null);

        //add components
        add (playButton);
        add (leaderBoardButton);
        add (exitButton);
        add (menuBar);
        add (titleLabel);

        playButton.setBounds (155, 395, 495, 155);
        leaderBoardButton.setBounds (155, 565, 300, 115);
        exitButton.setBounds (470, 565, 180, 115);
        menuBar.setBounds (0, 0, 810, 30);
        titleLabel.setBounds (0, 30, 820, 350);

        //add action listeners
        playButton.addActionListener (e -> play());
        leaderBoardButton.addActionListener (e -> leaderboard());
        exitButton.addActionListener (e -> exit());
        newGameItem.addActionListener (e -> play());
        leaderboardItem.addActionListener (e -> leaderboard());
        exitItem.addActionListener (e -> exit());
        contentsItem.addActionListener (e -> {
            try {
                contents();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        aboutItem.addActionListener (e -> about());
    }

    /**
     * This method starts a new game.
     * @author Gergo Buzas
     */
    private void play() {
        SpaceInvaders game = new SpaceInvaders();
        game.setVisible(true);
    }

    /**
     * This method opens the leaderboard.
     * @author Gergo Buzas
     */
    private void leaderboard(){
        try {
            LeaderBoard.main();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This method exits the game.
     * It saves the leaderboard before closing.
     * @author Gergo Buzas
     */
    private void exit(){
        try {
            LeaderBoard.save();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.exit(0);
    }

    /**
     * This method opens the game's GitHub page.
     * @author Gergo Buzas
     * @throws IOException If the page cannot be opened.
     */
    private void contents() throws IOException {
        Desktop.getDesktop().browse(URI.create("https://github.com/gergobuzas/Space_Invaders"));
    }

    /**
     * This method opens the game's "about" info.
     * @author Gergo Buzas
     */
    private void about(){
        JOptionPane.showMessageDialog(null, "This game was created by Gergo Buzas for the \"Introduction " +
                "to Programming 3\" course at the Budapest University of Technology and Economics.\nContact me at \"buzasgergo0615@gmail.com\"");
    }

    /**
     * This method is the main method of the Menu.
     * It creates a new JFrame and adds the Menu to it.
     * It sets the JFrame's attributes.
     * @author Gergo Buzas
     * @throws IOException If the Space Invaders image file is not found.
     */
    public static void main () throws IOException {
        JFrame frame = new JFrame ("Space Invaders");
        frame.setDefaultCloseOperation (WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add (new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        frame.setResizable(false);
    }
}
