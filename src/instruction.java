import javax.swing.*;

public class instruction extends JFrame {
    private JPanel mainPanel;
    private JLabel image;
    private JLabel upperLabel;
    private JLabel lowerLabel;
    private JLabel middleLabel;

    public instruction() {
        setTitle("Instruction");
        setSize(700, 600);  //set window size
        setVisible(true);
        setResizable(false);
        setContentPane(mainPanel);
        upperLabel.setText("The goal of this game is to identify the SET on the game board.");
        middleLabel.setText("Each card in this game includes 4 features: ");
        lowerLabel.setText("<html>A SET consists of 3 cards in which each of the cards' features, looked at one by one, are the same on each card, or, all different from each card.</html>");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new instruction();
            }
        });
    }
}
