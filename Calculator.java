import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame {
    private JTextField display;
    private double result = 0;
    private String lastCommand = "=";
    private boolean start = true;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 400);
        setLayout(new BorderLayout());

        display = new JTextField("0");
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.WHITE);
        display.setFont(new Font("Arial", Font.PLAIN, 40));
        display.setHorizontalAlignment(JTextField.RIGHT);
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(5, 4, 2, 2));
        buttonPanel.setBackground(Color.BLACK);

        String[] buttonLabels = {
            "C", "()", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "+/-", "0", ".", "="
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 20));
            button.setFocusPainted(false);
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.WHITE);
            if (label.matches("[0-9.]")) {
                button.addActionListener(new NumberListener());
            } else {
                button.addActionListener(new CommandListener());
            }
            if (label.equals("=")) {
                button.setBackground(new Color(0, 128, 0)); // Green color
            }
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
    }

    private class NumberListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String digit = event.getActionCommand();
            if (start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + digit);
        }
    }

    private class CommandListener implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (start) {
                if (command.equals("-")) {
                    display.setText(command);
                    start = false;
                } else {
                    lastCommand = command;
                }
            } else {
                calculate(Double.parseDouble(display.getText()));
                lastCommand = command;
                start = true;
            }
        }
    }

    public void calculate(double x) {
        switch (lastCommand) {
            case "+": result += x; break;
            case "-": result -= x; break;
            case "×": result *= x; break;
            case "÷": result /= x; break;
            case "=": result = x; break;
        }
        display.setText("" + result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calc = new Calculator();
            calc.setVisible(true);
        });
    }
}