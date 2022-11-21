package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class LeaderBoard extends JPanel {
    JFrame mainWindow;
    static ArrayList<Score> top10List;

    public LeaderBoard() {
        top10List = new ArrayList<>();
        LeaderBoard.load();
    }

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

    public static void save() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("top10.txt");
        ObjectOutputStream out = new ObjectOutputStream(fileOutputStream);
        for (Score score: top10List) {
            out.writeObject(score);
        }
        out.close();
        fileOutputStream.close();
    }

    public static ArrayList<Score> getTop10List() {
        return top10List;
    }

    public static void main() throws IOException, ClassNotFoundException {
        JFrame frame = new JFrame ("Leaderboard");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.getContentPane().add (new LeaderBoard(frame));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }




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
