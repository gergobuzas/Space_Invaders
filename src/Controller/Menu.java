package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JPanel {

    public Menu() throws IOException {
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

        //construct components
        JButton playButton = new JButton("Play");
        JButton leaderBoardButton = new JButton("Leaderboard");
        JButton exitButton = new JButton("Exit");
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
            try {
                LeaderBoard.main();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });

        exitButton.addActionListener (e -> {
            try {
                LeaderBoard.save();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            System.exit(0);
        });

    }

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
