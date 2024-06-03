import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class training extends JFrame{
    private JButton yesButton;
    private JButton noButton;
    private JLabel left;
    private JLabel middle;
    private JLabel right;
    private JLabel title;
    private JPanel mainPanel;
    private JLabel message;
    private JButton backButton;
    public boolean isSet;
    public JLabel[] display = {left, middle, right};
    public ArrayList<card> set = new ArrayList<>();

    public training() {
        /**
         * initialize page and generate three cards to start training
         */
        setTitle("Training");
        setSize(700, 600);  //set window size
        setVisible(true);
        setResizable(false);
        setContentPane(mainPanel);
        showSet(generate());

        /**
         * Yes button: click yes if user thinks the shown cards are a SET
         */
        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSet) {
                    message.setForeground(new Color(102, 204, 0));
                    message.setText("Correct!");
                    SoundPlayer.playSound("src/correct.wav");
                    showSet(generate());
                }
                else {
                    message.setForeground(Color.red);
                    message.setText("This is not a SET");
                    SoundPlayer.playSound("src/incorrect.wav");
                }
            }
        });

        /**
         * No button: click no if user thinks the shown cards are not a SET
         */
        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSet) {
                    message.setForeground(Color.red);
                    message.setText("This is a SET");
                    SoundPlayer.playSound("src/incorrect.wav");
                }
                else {
                    message.setForeground(new Color(102, 204, 0));
                    message.setText("Correct!");
                    SoundPlayer.playSound("src/correct.wav");
                    showSet(generate());
                }
            }
        });

        /**
         * Back button: click to return to cover page
         */
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/buttonclick.wav");
                dispose();
                new coverPage().setVisible(true);
            }
        });
    }

    /**
     * to display the three cards on the screen
     * @param set is an arraylist of cards generated from generate()
     */
    public void showSet(ArrayList<card> set) {
        Collections.shuffle(set);
        left.setIcon(new ImageIcon(set.get(0).getSrc()));
        middle.setIcon(new ImageIcon(set.get(1).getSrc()));
        right.setIcon(new ImageIcon(set.get(2).getSrc()));
    }

    /**
     * randomly generate three cards that are either a SET or very close (3 out of 4 features are satisfied) to a SET
     * @return returns an arraylist of three cards to be displayed
     */
    public ArrayList<card> generate() {
        Random rand = new Random();
        int m, n;
        int choice = rand.nextInt(2); //0 stands for not a SET and 1 stands for SET
        ArrayList<card> output = new ArrayList<>();

        while (true) {
            n = rand.nextInt(81);
            m = rand.nextInt(81); //randomly pick two cards
            if (m != n) {
                break;
            }
        }

        card one = Main.deck.get(n);
        card two = Main.deck.get(m);
        output.add(one);
        output.add(two);

        for (card c: Main.deck) { //loop through the remaining cards in deck and find the first satisfying card
            if (!(c.equals(m) || c.equals(n))){
                if (choice == 1 && check(one, two, c) == 4) {
                    output.add(c);
                    isSet = true;
                    break;
                }
                else if (choice == 0 && check(one, two, c) == 3) {
                    output.add(c);
                    isSet = false;
                    break;
                }
            }
        }
        return output;
    }

    /**
     * method used for counting the number of satisfying features for the three cards
     * @param one
     * @param two
     * @param three
     * @return count of satisfying features (0 - 4)
     */
    public int check(card one, card two, card three){
        int count = 0;
        if (compareInt(one.getNum(), two.getNum(), three.getNum())) {
            count++;
        }
        if (compareString(one.getShape(), two.getShape(), three.getShape())) {
            count ++;
        }
        if (compareString(one.getColor(), two.getColor(), three.getColor())) {
            count++;
        }
        if (compareString(one.getFilling(), two.getFilling(), three.getFilling())) {
            count++;
        };

        return count;
    }

    /**
     * method for comparing color, shape and filling of the cards (String type variables)
     * @param s1
     * @param s2
     * @param s3
     * @return whether strings are all same or all different or not
     */
    public boolean compareString(String s1, String s2, String s3) {
        if (s1.equals(s2) && s2.equals(s3)) {
            return true;
        }
        else if (!s1.equals(s2) && !s1.equals(s3) && !s2.equals(s3)) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * method for comparing number of shapes of the cards (int type variable)
     * @param n1
     * @param n2
     * @param n3
     * @return whether numbers are all same or all different or not
     */
    public boolean compareInt(int n1, int n2, int n3) {
        if (n1 == n2 && n2 == n3) {
            return true;
        }
        else if (!(n1 == n2) && !(n2 == n3) && !(n1 == n3)) {
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new training();
            }
        });
    }
}
