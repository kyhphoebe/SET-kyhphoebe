import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class coverPage extends JFrame{
    private JPanel panel1;
    private JButton startButton;
    private JLabel label1;

    public coverPage() {
        setTitle("Welcome to SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 750);  //set window size
        setVisible(true);
        setContentPane(panel1);
        ImageIcon original = new ImageIcon(getClass().getResource("setcover.jpg"));
        Image img = original.getImage().getScaledInstance(1000, 250, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        label1.setIcon(icon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new game().setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new coverPage();
            }
        });
    }
}
