package com.mycompany.killingthecentipedeui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class KillingTheCentipedeUI extends JFrame {

    private int life = 3;
    private int centipedeRow;
    private int centipedeCol;
    private JLabel statusLabel;
    private JButton[][] buttons;

    public KillingTheCentipedeUI() {
        setTitle("Killing the Centipede");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initializeGame();

        // Set up 3x3 button grid
        JPanel gridPanel = new JPanel(new GridLayout(3, 3));
        buttons = new JButton[3][3];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 40));
                button.setBackground(Color.LIGHT_GRAY);  // Default color
                buttons[row][col] = button;
                gridPanel.add(button);

                // Add action listener to each button
                final int r = row;
                final int c = col;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        handleButtonClick(r, c, button);
                    }
                });
            }
        }

        add(statusLabel, BorderLayout.NORTH);
        add(gridPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void initializeGame() {
        // Randomly place the centipede
        Random rand = new Random();
        centipedeRow = rand.nextInt(3);
        centipedeCol = rand.nextInt(3);
        life = 3;

        // Initialize status label
        statusLabel = new JLabel("You have 3 hearts. Find the centipede!", JLabel.CENTER);
        statusLabel.setFont(new Font("Serif", Font.BOLD, 18));
    }

    private void handleButtonClick(int row, int col, JButton button) {
        if (row == centipedeRow && col == centipedeCol) {
            button.setBackground(Color.GREEN); // Indicate hit
            button.setText("✓");
            showWinDialog();
        } else {
            button.setBackground(Color.RED); // Indicate miss
            button.setText("X");
            life--;
            statusLabel.setText("Missed! Hearts remaining: " + life);
            if (life == 0) {
                showLossDialog();
            }
        }
    }

    private void resetGame() {
        initializeGame();
        statusLabel.setText("You have 3 hearts. Find the centipede!");

        // Reset each button
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
                buttons[row][col].setBackground(Color.LIGHT_GRAY);
                buttons[row][col].setEnabled(true);
            }
        }
    }

    private void disableButtons() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(false);
            }
        }
    }

    private void showWinDialog() {
        disableButtons();
        int option = JOptionPane.showOptionDialog(
                this,
                "Congratulations! You hit the centipede!",
                "You Win!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[]{"Play Again"},
                "Play Again"
        );

        if (option == JOptionPane.OK_OPTION) {
            resetGame();
        }
    }

    private void showLossDialog() {
        disableButtons();
        int option = JOptionPane.showOptionDialog(
                this,
                "Nice try! You've run out of hearts.",
                "Game Over",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.ERROR_MESSAGE,
                null,
                new Object[]{"Play Again"},
                "Play Again"
        );

        if (option == JOptionPane.OK_OPTION) {
            resetGame();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new KillingTheCentipedeUI();
            }
        });
    }
}
