package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Menu extends JPanel {

    public Menu() throws IOException {
        //construct preComponents
        JMenu fileMenu = new JMenu ("File");
        JMenuItem new_gameItem = new JMenuItem ("New Game");
        fileMenu.add (new_gameItem);
        JMenuItem leaderboardItem = new JMenuItem ("Leaderboard");
        fileMenu.add (leaderboardItem);
        JMenuItem exitItem = new JMenuItem ("Exit");
        fileMenu.add (exitItem);
        JMenu helpMenu = new JMenu ("Help");
        JMenuItem contentsItem = new JMenuItem ("Contents");
        helpMenu.add (contentsItem);
        JMenuItem aboutItem = new JMenuItem ("About");
        helpMenu.add (aboutItem);

        //construct components
        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add (fileMenu);
        menuBar.add (helpMenu);
        BufferedImage myPicture = ImageIO.read(new File("./src/imgs/logo.png"));
        JLabel titleLabel = new JLabel(new ImageIcon(myPicture));

        //adjust size and set layout
        setPreferredSize (new Dimension (800, 700));
        setLayout (null);

        //add components
        add (playButton);
        add (leaderBoardButton);
        add (exitButton);
        add (menuBar);
        add (titleLabel);

        //set component bounds (only needed by Absolute Positioning)
        playButton.setBounds (155, 395, 495, 155);
        leaderBoardButton.setBounds (155, 565, 300, 115);
        exitButton.setBounds (470, 565, 180, 115);
        menuBar.setBounds (0, 0, 810, 30);
        titleLabel.setBounds (0, 30, 820, 350);

        //add action listeners
        playButton.addActionListener (e -> {
            SpaceInvaders game = new SpaceInvaders();
            game.setVisible(true);
        });

        leaderBoardButton.addActionListener (e -> {
                System.out.println ("Leaderboard was pressed.");
        });

        exitButton.addActionListener (e -> {
            System.exit(0);
        });

    }

    public static void main () throws IOException {
        JFrame frame = new JFrame ("Space Invaders");
        frame.setDefaultCloseOperation (EXIT_ON_CLOSE);
        frame.getContentPane().add (new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible (true);
        frame.setResizable(false);
    }
}
