import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class Main extends JFrame {
  private JTextField firstNumber;
  private JTextField secondNumber;
  private JTextField result;
  private ButtonGroup operations;
  private JRadioButton add;
  private JRadioButton subtract;
  private JRadioButton multiply;
  private JRadioButton divide;
  private JLabel equalSign;
  
  public static void main(String[] args) {
	   Main window = new Main();
	   window.setVisible(true);
	}

  public Main() {
    setTitle("Calculator");
    setSize(400, 150);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setLocationRelativeTo(null);
    Container content = getContentPane();
    content.setBackground(Color.LIGHT_GRAY);
    content.setLayout(new FlowLayout());

    firstNumber = new JTextField(10);
    content.add(firstNumber);

    operations = new ButtonGroup();
    add = new JRadioButton("+");
    subtract = new JRadioButton("-");
    multiply = new JRadioButton("*");
    divide = new JRadioButton("/");
    operations.add(add);
    operations.add(subtract);
    operations.add(multiply);
    operations.add(divide);
    
    JPanel radioButtonPanel = new JPanel();
    radioButtonPanel.setLayout(new BoxLayout(radioButtonPanel, BoxLayout.Y_AXIS));
    radioButtonPanel.add(add);
    radioButtonPanel.add(subtract);
    radioButtonPanel.add(multiply);
    radioButtonPanel.add(divide);
    content.add(radioButtonPanel, BorderLayout.WEST);
    
    secondNumber = new JTextField(10);
    content.add(secondNumber);

    equalSign = new JLabel("=");
    content.add(equalSign);

    result = new JTextField(10);
    result.setEditable(false);
    content.add(result);

    add.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int num1 = Integer.parseInt(firstNumber.getText());
          int num2 = Integer.parseInt(secondNumber.getText());
          int res = num1 + num2;
          result.setText(String.valueOf(res));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(Main.this, "Invalid input. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    subtract.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int num1 = Integer.parseInt(firstNumber.getText());
          int num2 = Integer.parseInt(secondNumber.getText());
          int res = num1 - num2;
          result.setText(String.valueOf(res));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(Main.this, "Invalid input. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    multiply.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int num1 = Integer.parseInt(firstNumber.getText());
          int num2 = Integer.parseInt(secondNumber.getText());
          int res = num1 * num2;
          result.setText(String.valueOf(res));
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(Main.this, "Invalid input. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });

    divide.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          int num1 = Integer.parseInt(firstNumber.getText());
          int num2 = Integer.parseInt(secondNumber.getText());
          if (num2 == 0) {
            JOptionPane.showMessageDialog(Main.this, "Cannot divide by zero.", "Error", JOptionPane.ERROR_MESSAGE);
          } else {
            double res = (double) num1 / num2;
            result.setText(String.valueOf(res));
          }
        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(Main.this, "Invalid input. Please enter an integer.", "Error", JOptionPane.ERROR_MESSAGE);
        }
      }
    });
  }
}