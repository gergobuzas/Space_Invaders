package Controller;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Menu extends JPanel {
    LeaderBoard leaderBoard;

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

    private void play() {
        SpaceInvaders game = new SpaceInvaders();
        game.setVisible(true);
    }

    private void leaderboard(){
        try {
            LeaderBoard.main();
        } catch (IOException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void exit(){
        try {
            LeaderBoard.save();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        System.exit(0);
    }

    private void contents() throws IOException {
        Desktop.getDesktop().browse(URI.create("https://github.com/gergobuzas/Space_Invaders"));
    }

    private void about(){
        JOptionPane.showMessageDialog(null, "This game was created by Gergo Buzas for the \"Introduction " +
                "to Programming 3\" course at the Budapest University of Technology and Economics.\nContact me at \"buzasgergo0615@gmail.com\"");
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
