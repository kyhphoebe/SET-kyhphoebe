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
        setTitle("Training");
        setSize(700, 600);  //set window size
        setVisible(true);
        setResizable(false);
        setContentPane(mainPanel);
        showSet(generate());

        yesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSet) {
                    message.setForeground(new Color(102, 204, 0));
                    message.setText("Correct!");
                    showSet(generate());
                }
                else {
                    message.setForeground(Color.red);
                    message.setText("This is not a SET");
                }
            }
        });

        noButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isSet) {
                    message.setForeground(Color.red);
                    message.setText("This is a SET");
                }
                else {
                    message.setForeground(new Color(102, 204, 0));
                    message.setText("Correct!");
                    showSet(generate());
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new coverPage().setVisible(true);
            }
        });
    }

    public void showSet(ArrayList<card> set) {
        Collections.shuffle(set);
        left.setIcon(new ImageIcon(set.get(0).getSrc()));
        middle.setIcon(new ImageIcon(set.get(1).getSrc()));
        right.setIcon(new ImageIcon(set.get(2).getSrc()));
    }

    public ArrayList<card> generate() {
        Random rand = new Random();
        int m, n;
        int choice = rand.nextInt(2);
        ArrayList<card> output = new ArrayList<>();

        while (true)
        {
            n = rand.nextInt(81);
            m = rand.nextInt(81);
            if (m != n) {
                break;
            }
        }

        card one = Main.deck.get(n);
        card two = Main.deck.get(m);
        output.add(one);
        output.add(two);

        for (card c: Main.deck) {
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
