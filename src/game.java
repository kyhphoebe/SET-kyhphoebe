import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class game extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button7;
    private JButton button10;
    private JButton button5;
    private JButton button8;
    private JButton button11;
    private JButton button6;
    private JButton button9;
    private JButton button12;
    private JButton restartButton;
    private JButton hintButton;
    private JLabel time;
    private JLabel remaining;
    private JLabel score;
    private JPanel mainPanel;
    private JPanel board;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    public ArrayList<Integer> selected = new ArrayList<>();
    public Border green = BorderFactory.createLineBorder(Color.green, 4);
    public Border orange = BorderFactory.createLineBorder(Color.orange, 4);
    public Border red = BorderFactory.createLineBorder(Color.red, 4);
    public Border empty = BorderFactory.createEmptyBorder(4,4,4,4);

    public ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15));
    public card[] displayed = new card[15];
    public ArrayList<Integer> existingSet = new ArrayList<>();

    public game() {
        setTitle("SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,600);  //set window size
        setVisible(true);
        setContentPane(mainPanel);
        setResizable(false);
        start();
        checkSetPresent();

        ActionListener press = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for (JButton b: buttons) {
                    b.setBorder(empty);
                }
                JButton btn = (JButton) e.getSource();
                btn.setEnabled(false);
                selected.add(buttons.indexOf(btn));
                if (selected.size() == 3) {
                    boolean b = check(selected);
                    if (b) {
                        for (int n: selected) {
                            buttons.get(n).setBorder(green);
                        }

                        SwingUtilities.invokeLater(() -> {
                            try {
                                Thread.sleep(1000);
                            }
                            catch(Exception ex) {
                                System.out.println("error");
                            }
                        });

                        for (int i: selected) {
                            displayed[i] = Main.pile.pop();
                            buttons.get(i).setBorder(empty);
                            buttons.get(i).setEnabled(true);
                            buttons.get(i).setIcon(new ImageIcon(displayed[i].getSrc()));
                        }

                        if (!checkSetPresent()) {
                            button13.setVisible(true);
                            button14.setVisible(true);
                            button15.setVisible(true);
                            displayed[12] = Main.pile.pop();
                            displayed[13] = Main.pile.pop();
                            displayed[14] = Main.pile.pop();
                            buttons.get(12).setIcon(new ImageIcon(displayed[12].getSrc()));
                            buttons.get(13).setIcon(new ImageIcon(displayed[13].getSrc()));
                            buttons.get(14).setIcon(new ImageIcon(displayed[14].getSrc()));
                        }

                    }
                    else {
                        for (int n: selected) {
                            buttons.get(n).setBorder(red);
                        }

                        SwingUtilities.invokeLater(() -> {
                            try {
                                Thread.sleep(1000);
                            }
                            catch(Exception ex) {
                                System.out.println("error");
                            }
                        });

                        for (int i: selected) {
                            buttons.get(i).setBorder(empty);
                            buttons.get(i).setEnabled(true);
                        }
                    }
                    selected.clear();
                }
            }
        };
        for (JButton b: buttons) {
            b.addActionListener(press);
        }
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new game().setVisible(true);
            }
        });
        hintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                for(Integer n: existingSet) {
                    buttons.get(n).setBorder(orange);
                }
            }
        });
    }

    public void start(){
        initialize();
        for (int i=0; i<12; i++) {
            displayed[i] = Main.pile.pop();
            buttons.get(i).setIcon(new ImageIcon(displayed[i].getSrc()));
            button13.setVisible(false);
            button14.setVisible(false);
            button15.setVisible(false);
        }
    }

    public void initialize(){
        Collections.shuffle(Main.deck);
        Main.pile.clear();
        for(card c: Main.deck) {
            Main.pile.add(c);
        }
    }

    public boolean check(ArrayList<Integer> list){
        card one = displayed[list.get(0)];
        card two = displayed[list.get(1)];
        card three = displayed[list.get(2)];
        boolean flag = compareInt(one.getNum(), two.getNum(), three.getNum()) && compareString(one.getShape(), two.getShape(), three.getShape()) && compareString(one.getColor(), two.getColor(), three.getColor()) && compareString(one.getFilling(), two.getFilling(), three.getFilling());
        return flag;
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

    public boolean checkSetPresent() {
        ArrayList<Integer> index = new ArrayList<>();
        for (int i=0; i<10; i++) {
            for (int j=i+1; j<11; j++) {
                for (int k=j+1; k<12; k++) {
                    index.clear();
                    index.add(i);
                    index.add(j);
                    index.add(k);
                    if (check(index)) {
                        existingSet.clear();
                        for(Integer in: index) {
                            existingSet.add(in);
                        }
                        return true;
                    }
                }
            }
        }
        System.out.println("false");
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new game();
            }
        });
    }
}
