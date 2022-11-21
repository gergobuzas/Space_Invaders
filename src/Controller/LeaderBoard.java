package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * This class represents the leaderboard.
 * It extends the JFrame class.
 * It contains the attributes and methods for the leaderboard.
 * It saves the top10 scores of the players in a file.
 * It also reads the scores from the file.
 * The file, which it handles is called "top10.txt"
 * @author Gergo Buzas
 * @see javax.swing.JPanel
 */
public class LeaderBoard extends JPanel {
    JFrame mainWindow;
    static ArrayList<Score> top10List;

    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * @author Gergo Buzas
     */
    public LeaderBoard() {
        top10List = new ArrayList<>();
        LeaderBoard.load();
    }

    /**
     * This method is the constructor of the class.
     * It initializes the attributes of the class.
     * It creates a window with the top10 scores.
     * @param cmainWindow The main window of the leaderboard. It is the window, where the user can see the top10 scores.
     * @author Gergo Buzas
     */
    public LeaderBoard(JFrame cmainWindow) {
        JButton okButton = new JButton("OK");
        JList<String> top10JList = new JList<>();
        top10List = new ArrayList<>();
        mainWindow = cmainWindow;
        setPreferredSize (new Dimension(400, 600));
        setLayout(new GridLayout(2, 1, 0, 0));
        LeaderBoard.load();

        top10JList.setFont(new Font("Arial", Font.PLAIN, 20));
        ArrayList<String> lines = new ArrayList<>();
        for (int i = 0; i < top10List.size(); i++){
            String name = top10List.get(i).getName();
            String score = Integer.toString(top10List.get(i).getScore());
            String place = Integer.toString(i + 1);
            String line = place + ". " + name + " - " + score;
            lines.add(line);
        }
        top10JList.setListData(lines.toArray(new String[0]));

        add(top10JList);
        add(okButton);

        okButton.addActionListener(e -> mainWindow.setVisible(false));
    }

    /**
     * This method loads the top10 scores from the file.
     * @author Gergo Buzas
     * @throws RuntimeException If the file is not found or the file is corrupted or a class is not found.
     */
    public static void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream("top10.txt");
            ObjectInputStream in = new ObjectInputStream(fileInputStream);
            for (int i = 0; i < 10; i++) {
                top10List.add((Score)in.readObject());
            }
            in.close();
            fileInputStream.close();
        }
        catch (FileNotFoundException e) {
            File top10File = new File("top10.txt");
            for (int i = 0; i < 10; i++) {
                top10List.add(new Score("Noone", 0));
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(top10File);
                ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
                for (Score score: top10List) {
                    out.writeObject(score);
                }
                out.close();
                fileOutputStream.close();
                System.out.println("Serialized data is saved in top10.txt");
            } catch (IOException i) {
                i.printStackTrace();
            }

        }
        catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method saves the top10 scores to the file.
     * @author Gergo Buzas
     * @throws IOException If the file is not found or cannot be opened.
     */
    public static void save() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("top10.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        for (Score score: top10List) {
            out.writeObject(score);
        }
        out.close();
        fileOutputStream.close();
    }

    /**
     * This method returns the top10 scores.
     * @return The top10 scores.
     */
    public static ArrayList<Score> getTop10List() {
        return top10List;
    }

    /**
     * This method opens the leaderboard window.
     * @throws IOException If the file is not found.
     * @throws ClassNotFoundException If a class is not found.
     * @author Gergo Buzas
     * @see LeaderBoard
     */
    public static void main() throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame ("Leaderboard");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add (new LeaderBoard(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    /**
     * This class represents the scores of the players.
     * It implements the Serializable interface.
     * It contains the attributes and methods for the scores.
     * @author Gergo Buzas
     * @see Serializable
     */
    public static class Score implements Serializable {
        private final String name;
        private final int score;

        public Score(String s, int parseInt) {
            name = s;
            score = parseInt;
        }

        public String getName(){
            return name;
        }

        public int getScore(){
            return score;
        }
    }
}
