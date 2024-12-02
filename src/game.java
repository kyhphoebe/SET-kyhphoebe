import javax.swing.*;
import javax.swing.Timer;
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
    private JPanel mainPanel;
    private JPanel board;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JLabel message;
    private JButton quitButton;
    public ArrayList<Integer> selected = new ArrayList<>();
    public Border green = BorderFactory.createLineBorder(Color.green, 4);
    public Border orange = BorderFactory.createLineBorder(Color.orange, 4);
    public Border red = BorderFactory.createLineBorder(Color.red, 4);
    public Border empty = BorderFactory.createEmptyBorder(4,4,4,4);
    public ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12, button13, button14, button15));
    public card[] displayed = new card[15];
    public ArrayList<Integer> existingSet = new ArrayList<>();
    public boolean extra = false;
    public Timer timer;
    public Timer countdown;
    private int hours = 0;
    private int minutes = 0;
    private int seconds = 0;
    public int remainingCards = 69;
    public int streak = 0;

    public game(boolean challenge) {
        /**
         * initialize game and update existing SET & timers
         */
        setTitle("SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,600);  //set window size
        setVisible(true);
        setContentPane(mainPanel);
        setResizable(false);
        SoundPlayer.playSound("src/sounds/GameStart Sound.wav");
        start();
        if (!checkSetPresent()) {
            addExtraSlot();
        }
        message.setText(" ");
        remaining.setText("Remaining Cards: " + remainingCards);
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        countdown = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                countdown();
                if (minutes == 0 && seconds == 0) {
                    countdown.stop();
                    SoundPlayer.playSound("src/sounds/womp.wav");
                    message.setForeground(Color.red);
                    message.setText("Time's Up");
                    for (JButton b: buttons) {
                        b.setEnabled(false);
                        hintButton.setEnabled(false);
                    }
                }
            }
        });
        if (challenge) {
            minutes = 8;
            countdown.start();
        }
        else {
            timer.start();
        }

        Timer disable = new Timer(20000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                hintButton.setEnabled(true);
            }
        });
        disable.setRepeats(false);

        /**
         * method when card buttons are pressed
         */
        ActionListener press = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                for (JButton b: buttons) { //clear the borders
                    b.setBorder(empty);
                }
                message.setText(" ");
                JButton btn = (JButton) e.getSource();
                btn.setEnabled(false); //disable the button after being pressed
                selected.add(buttons.indexOf(btn)); //add the index of the button to arraylist to keep track of buttons pressed
                if (selected.size() == 3) {
                    boolean b = check(selected); //check the three cards selected
                    if (b) {

                        //play different sound according to streak
                        streak++;
                        if (streak == 3) {
                            SoundPlayer.playSound("src/sounds/amazing.wav");
                        }
                        else if (streak >= 6 && streak % 3 == 0){
                            SoundPlayer.playSound("src/sounds/unbelievable.wav");
                        }
                        else {
                            SoundPlayer.playSound("src/sounds/correct.wav");
                        }

                        message.setForeground(new Color(102, 204, 0));
                        message.setText("SET!");
                        for (int n: selected) {
                            buttons.get(n).setBorder(green);
                        }

                        //delay

                        if (remainingCards == 0) { //if there is no remaining cards in stack
                            for (int i: selected) {
                                displayed[i] = null;
                                buttons.get(i).setBorder(empty);
                                buttons.get(i).setEnabled(false);
                                buttons.get(i).setIcon(null);
                            }
                        }
                        else {
                            if (extra) { //if extra card slots are added (need to get rid of extra slots if SET presents)
                                extra = false;
                                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                                for (int i: selected) { //clear the extra slots
                                    displayed[i] = null;
                                    buttons.get(i).setEnabled(true);
                                    buttons.get(i).setBorder(empty);
                                }
                                for (int j=12; j<=14; j++) { //put the extra cards into the 12 slot board
                                    if (displayed[j] != null) {
                                        for (int k=0; k<=11; k++) {
                                            if (displayed[k] == null) {
                                                displayed[k] = displayed[j];
                                                buttons.get(k).setIcon(new ImageIcon(displayed[k].getSrc()));
                                                displayed[j] = null;
                                                break;
                                            }
                                        }
                                    }
                                }
                                button13.setVisible(false);
                                button14.setVisible(false);
                                button15.setVisible(false);
                            }
                            else { //if no extra slots are evoked
                                remainingCards -= 3;
                                remaining.setText("Remaining Cards: " + remainingCards);
                                for (int i : selected) {
                                    displayed[i] = Main.pile.pop();
                                    buttons.get(i).setBorder(empty);
                                    buttons.get(i).setEnabled(true);
                                    buttons.get(i).setIcon(new ImageIcon(displayed[i].getSrc()));
                                }
                            }
                        }

                        if (!checkSetPresent()) { //if there is no set presented on board (need extra slots)
                            if (remainingCards == 0){ //if there is no card in stack - game over
                                SoundPlayer.playSound("src/sounds/gameover.wav");
                                for (JButton bu: buttons) {
                                    bu.setEnabled(false);
                                    hintButton.setEnabled(false);
                                }
                                message.setForeground(new Color(102, 204, 0));
                                if (challenge) {
                                    message.setText("You won!");
                                    countdown.stop();
                                }
                                else {
                                    message.setText("Game Over!");
                                    timer.stop();
                                }
                                return;
                            }
                            SoundPlayer.playSound("src/sounds/buttonclick.wav");
                            addExtraSlot();
                        }
                    }
                    else {
                        SoundPlayer.playSound("src/sounds/incorrect.wav");
                        streak = 0;
                        for (int n: selected) {
                            buttons.get(n).setBorder(red);
                        }
                        message.setForeground(Color.red);
                        message.setText("Not a SET");

                        //delay

                        for (int i: selected) {
                            buttons.get(i).setBorder(empty);
                            buttons.get(i).setEnabled(true);
                        }
                    }
                    selected.clear(); //clear selected arraylist after checking s SET
                }
            }
        };
        for (JButton b: buttons) {
            b.addActionListener(press);
        }

        /**
         * Restart button: click to restart game
         */
        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                dispose();
                new game(challenge).setVisible(true);
            }
        });

        /**
         * Hint button: click to highlight an existing SET
         */
        hintButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/sounds/hint.wav");
                for(Integer n: existingSet) {
                    buttons.get(n).setBorder(orange);
                }
                hintButton.setEnabled(false);
                disable.start();
            }
        });

        /**
         * Quit button: click to return to cover page
         */
        quitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                dispose();
                new coverPage().setVisible(true);
            }
        });
    }

    /**
     * shuffle deck and create stack
     */
    public void initialize(){
        Collections.shuffle(Main.deck);
        Main.pile.clear();
        for(card c: Main.deck) {
            Main.pile.add(c);
        }
    }

    /**
     * initializes the board
     */
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

    /**
     * timer function
     */
    public void updateTime() {
        seconds++;
        if (seconds == 60) {
            seconds = 0;
            minutes++;
        }
        if (minutes == 60) {
            minutes = 0;
            hours++;
        }

        SwingUtilities.invokeLater(() -> {
            time.setText("Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
        });
    }

    /**
     * countdown function
     */
    public void countdown() {
        seconds--;
        if (seconds == -1) {
            seconds = 59;
            minutes--;
        }

        SwingUtilities.invokeLater(() -> {
            time.setText("Time: " + String.format("%02d:%02d:%02d", hours, minutes, seconds));
        });
    }

    /**
     * method that checks whether the cards corresponding with the arraylist are a SET
     * @param list a list that records the index of selected cards (selected)
     * @return whether is a SET
     */
    public boolean check(ArrayList<Integer> list){
        boolean flag;
        card one = displayed[list.get(0)];
        card two = displayed[list.get(1)];
        card three = displayed[list.get(2)];
        if (one != null && two != null && three != null) {
            flag = compareInt(one.getNum(), two.getNum(), three.getNum()) && compareString(one.getShape(), two.getShape(), three.getShape()) && compareString(one.getColor(), two.getColor(), three.getColor()) && compareString(one.getFilling(), two.getFilling(), three.getFilling());
        }
        else {
            flag = false;
        }
        return flag;
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

    /**
     * algorithm that loops through all three-card combination of cards on board and see if there is a SET
     * If so, update existing set
     * @return return whether SET exists
     */
    public boolean checkSetPresent() {
        ArrayList<Integer> index = new ArrayList<>();
        existingSet.clear();
        for (int i=0; i<13; i++) {
            for (int j=i+1; j<14; j++) {
                for (int k=j+1; k<15; k++) {
                    index.clear();
                    index.add(i);
                    index.add(j);
                    index.add(k);
                    if (check(index)) {
                        for(Integer in: index) {
                            existingSet.add(in);
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * method for adding three extra slots when there is no SET on board
     */
    public void addExtraSlot() {
        extra = true;
        remainingCards -= 3;
        remaining.setText("Remaining Cards: " + remainingCards);
        button13.setVisible(true);
        button14.setVisible(true);
        button15.setVisible(true);
        displayed[12] = Main.pile.pop();
        displayed[13] = Main.pile.pop();
        displayed[14] = Main.pile.pop();
        buttons.get(12).setIcon(new ImageIcon(displayed[12].getSrc()));
        buttons.get(13).setIcon(new ImageIcon(displayed[13].getSrc()));
        buttons.get(14).setIcon(new ImageIcon(displayed[14].getSrc()));
        checkSetPresent();
    }

    /**
     * calls method when game is over
     */
    public void gameOver() {
        SoundPlayer.playSound("src/sounds/gameover.wav");
        message.setText("Game Over!");
        for (JButton b: buttons) {
            b.setEnabled(false);
            hintButton.setEnabled(false);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new game(false);
            }
        });
    }
}
