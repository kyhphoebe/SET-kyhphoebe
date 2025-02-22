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
    private JCheckBox checkbox;
    Clip music;

    public coverPage() {
        /**
         * initialize cover page
         */
        setTitle("Welcome to SET!");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);  //set window size
        setVisible(true);
        setResizable(false);
        setContentPane(panel1);
        music = SoundPlayer.loopMusic("src/sounds/bgm.wav");
        ImageIcon original = new ImageIcon(getClass().getResource("setcover.jpg"));
        Image img = original.getImage().getScaledInstance(700, 200, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(img);
        label1.setIcon(icon);

        /**
         * Start button: click to start a SET game
         */
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.stop();
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                dispose();
                new game(checkbox.isSelected()).setVisible(true);
            }
        });

        /**
         * Instruction button: click to view instructions
         */
        instructionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                new instruction().setVisible(true);
            }
        });

        /**
         * Training button: click to start training
         */
        trainingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                music.stop();
                SoundPlayer.playSound("src/sounds/buttonclick.wav");
                dispose();
                new training().setVisible(true);
            }
        });
    }

}
