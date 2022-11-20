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
        top10JList.setListData(new String[]{
                "1.     " + top10List.get(0).getName() + "    " + top10List.get(0).getScore(),
                "2.     " + top10List.get(1).getName() + "    " + top10List.get(1).getScore(),
                "3.     " + top10List.get(2).getName() + "    " + top10List.get(2).getScore(),
                "4.     " + top10List.get(3).getName() + "    " + top10List.get(3).getScore(),
                "5.     " + top10List.get(4).getName() + "    " + top10List.get(4).getScore(),
                "6.     " + top10List.get(5).getName() + "    " + top10List.get(5).getScore(),
                "7.     " + top10List.get(6).getName() + "    " + top10List.get(6).getScore(),
                "8.     " + top10List.get(7).getName() + "    " + top10List.get(7).getScore(),
                "9.     " + top10List.get(8).getName() + "    " + top10List.get(8).getScore(),
                "10.   " + top10List.get(9).getName() + "    " + top10List.get(9).getScore()
        });

        add(top10JList);
        add(okButton);

        okButton.addActionListener(e -> mainWindow.setVisible(false));
    }

    private static void load() {
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
