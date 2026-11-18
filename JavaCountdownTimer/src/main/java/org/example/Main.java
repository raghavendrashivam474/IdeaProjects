package org.example;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // --- 1. Frame Setup ---
                JFrame frame = new JFrame("Countdown Timer");
                frame.setSize(500, 400);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
                frame.setLayout(new BorderLayout(10, 10));

                // --- 2. Timer Display (Center Area) ---
                JLabel timeDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
                timeDisplay.setFont(new Font("Consolas", Font.BOLD, 54));
                timeDisplay.setForeground(new Color(33, 33, 33));
                frame.add(timeDisplay, BorderLayout.CENTER);

                // --- 3. Top Panel: Inputs (Hours, Minutes, Seconds) ---
                JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));

                JLabel lblHours = new JLabel("H:");
                JTextField txtHours = new JTextField("0", 3);
                txtHours.setHorizontalAlignment(JTextField.CENTER);

                JLabel lblMinutes = new JLabel("M:");
                JTextField txtMinutes = new JTextField("0", 3);
                txtMinutes.setHorizontalAlignment(JTextField.CENTER);

                JLabel lblSeconds = new JLabel("S:");
                JTextField txtSeconds = new JTextField("0", 3);
                txtSeconds.setHorizontalAlignment(JTextField.CENTER);

                // Add labels and fields to the input panel
                inputPanel.add(lblHours);
                inputPanel.add(txtHours);
                inputPanel.add(lblMinutes);
                inputPanel.add(txtMinutes);
                inputPanel.add(lblSeconds);
                inputPanel.add(txtSeconds);

                frame.add(inputPanel, BorderLayout.NORTH);

                // --- 4. Bottom Panel: Action Buttons ---
                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

                JButton btnStart = new JButton("Start");
                JButton btnPause = new JButton("Pause");
                JButton btnReset = new JButton("Reset");

                // Style buttons
                btnStart.setPreferredSize(new Dimension(90, 35));
                btnPause.setPreferredSize(new Dimension(90, 35));
                btnReset.setPreferredSize(new Dimension(90, 35));

                buttonPanel.add(btnStart);
                buttonPanel.add(btnPause);
                buttonPanel.add(btnReset);

                frame.add(buttonPanel, BorderLayout.SOUTH);

                // --- 5. Make Frame Visible ---
                frame.setVisible(true);
            }
        });
    }
}