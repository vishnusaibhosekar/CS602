import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class DumbChecker {

    public static void main(String[] args) {
        UIManager.put("OptionPane.background", new Color(48, 48, 48));
        UIManager.put("Panel.background", new Color(48, 48, 48));
        UIManager.put("OptionPane.messageForeground", Color.white);
        UIManager.put("Button.background", new Color(64, 64, 64));
        UIManager.put("Button.foreground", Color.white);
        JFrame frame = new JFrame("Dumb Checker");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
        panel.setBackground(new Color(50, 50, 50));
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private static String capitalizeFirstChar(String name) {
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    private static void placeComponents(JPanel panel) {
        panel.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.anchor = GridBagConstraints.WEST;

        JLabel userNameLabel = new JLabel("User Name");
        userNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userNameLabel.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(userNameLabel, constraints);

        JTextField userNameText = new JTextField(12);
        userNameText.setFont(new Font("Arial", Font.PLAIN, 16));
        userNameText.setBackground(new Color(70, 70, 70));
        userNameText.setForeground(Color.WHITE);
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(userNameText, constraints);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(new Color(0, 153, 153));
        submitButton.setForeground(Color.WHITE);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(15, 5, 5, 5);
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, constraints);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userName = userNameText.getText();
                int result;
                do {
                    result = JOptionPane.showOptionDialog(null, capitalizeFirstChar(userName) + ", are you dumb?", "Let's see", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
                } while (result == JOptionPane.NO_OPTION);
                JOptionPane.showInternalMessageDialog(null, "\uD83D\uDE1C I knew it");
                try {
                  Thread.sleep(1000);
                } catch (InterruptedException ex) {
                  ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }
}