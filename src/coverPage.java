import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class coverPage extends JFrame{
    private JPanel panel1;
    private JButton startButton;
    private JLabel label1;
    private JButton instructionButton;
    private JButton trainingButton;
    Clip music;

    public coverPage() {
        setTitle("Welcome to SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);  //set window size
        setVisible(true);
        setResizable(false);
        setContentPane(panel1);
        music = SoundPlayer.loopMusic("src/bgm.wav");
        ImageIcon original = new ImageIcon(getClass().getResource("setcover.jpg"));
        Image img = original.getImage().getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        label1.setIcon(icon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.stop();
                SoundPlayer.playSound("src/buttonclick.wav");
                dispose();
                new game().setVisible(true);
            }
        });
        instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/buttonclick.wav");
                new instruction().setVisible(true);
            }
        });
        trainingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.stop();
                SoundPlayer.playSound("src/buttonclick.wav");
                dispose();
                new training().setVisible(true);
            }
        });
    }

}
