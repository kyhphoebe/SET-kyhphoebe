import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    public int count;
    public Border chosen = BorderFactory.createLineBorder(Color.orange, 4);

    public JButton[] buttons = {button1, button2, button3, button4, button5, button6, button7, button8, button9, button10, button11, button12};

    public game() {
        setTitle("SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 750);  //set window size
        setVisible(true);
        setContentPane(mainPanel);
        ActionListener press = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton btn = (JButton) e.getSource();
                btn.setEnabled(false);
                btn.setBorder(chosen);
                count++;
            }
        };
        for (JButton b: buttons) {
            b.addActionListener(press);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new game();
            }
        });
    }
}
