package org.example;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    // --- State Variables ---
    private static Timer swingTimer;
    private static int totalDuration = 0;
    private static int totalSeconds = 0;
    private static boolean isPaused = false;

    public static void main(String[] args) {
        // Enable anti-aliasing for smooth text rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            // --- 1. Main Frame ---
            JFrame frame = new JFrame("Countdown Timer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 520);
            frame.setMinimumSize(new Dimension(500, 450));
            frame.setLocationRelativeTo(null);
            frame.getContentPane().setBackground(new Color(15, 23, 42)); // Deep Slate Dark Background

            // Use GridBagLayout so the inner card stays perfectly centered at ANY screen size / maximized
            frame.setLayout(new GridBagLayout());

            // --- 2. Main Card Panel ---
            JPanel card = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(30, 41, 59)); // Slate Card Background
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                    g2.setColor(new Color(51, 65, 85)); // Subtle Border
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                    g2.dispose();
                }
            };
            card.setOpaque(false);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(new EmptyBorder(30, 40, 30, 40));
            card.setPreferredSize(new Dimension(440, 380));

            // --- 3. Header Title ---
            JLabel titleLabel = new JLabel("COUNTDOWN TIMER");
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
            titleLabel.setForeground(new Color(148, 163, 184)); // Muted silver

            // --- 4. Input Row (Hours, Minutes, Seconds) ---
            JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
            inputPanel.setOpaque(false);

            JTextField txtHours = createStyledTextField("0");
            JTextField txtMinutes = createStyledTextField("0");
            JTextField txtSeconds = createStyledTextField("0");

            inputPanel.add(createInputBlock("HRS", txtHours));
            inputPanel.add(createInputBlock("MIN", txtMinutes));
            inputPanel.add(createInputBlock("SEC", txtSeconds));

            // --- 5. Digital Clock Display ---
            JLabel timeDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
            timeDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
            timeDisplay.setFont(new Font("Consolas", Font.BOLD, 52));
            timeDisplay.setForeground(new Color(56, 189, 248)); // Neon Cyan

            // --- 6. Progress Bar ---
            JProgressBar progressBar = new JProgressBar(0, 100);
            progressBar.setValue(100);
            progressBar.setPreferredSize(new Dimension(340, 8));
            progressBar.setMaximumSize(new Dimension(340, 8));
            progressBar.setForeground(new Color(56, 189, 248));
            progressBar.setBackground(new Color(51, 65, 85));
            progressBar.setBorderPainted(false);
            progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

            // --- 7. Buttons ---
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
            buttonPanel.setOpaque(false);

            JButton btnStart = createStyledButton("Start", new Color(34, 197, 94));  // Green
            JButton btnPause = createStyledButton("Pause", new Color(245, 158, 11)); // Amber
            JButton btnReset = createStyledButton("Reset", new Color(239, 68, 68));  // Red

            btnPause.setEnabled(false);

            buttonPanel.add(btnStart);
            buttonPanel.add(btnPause);
            buttonPanel.add(btnReset);

            // Assemble Card Components with Spacing
            card.add(titleLabel);
            card.add(Box.createVerticalStrut(20));
            card.add(inputPanel);
            card.add(Box.createVerticalStrut(25));
            card.add(timeDisplay);
            card.add(Box.createVerticalStrut(15));
            card.add(progressBar);
            card.add(Box.createVerticalStrut(25));
            card.add(buttonPanel);

            // Add Card to Centered Frame Layout
            frame.add(card);

            // --- 8. Timer Engine Logic ---
            swingTimer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (totalSeconds > 0) {
                        totalSeconds--;
                        timeDisplay.setText(formatTime(totalSeconds));

                        // Update Progress Bar
                        if (totalDuration > 0) {
                            int percent = (int) (((double) totalSeconds / totalDuration) * 100);
                            progressBar.setValue(percent);
                        }
                    } else {
                        // Timer Complete
                        swingTimer.stop();
                        progressBar.setValue(0);
                        Toolkit.getDefaultToolkit().beep();
                        JOptionPane.showMessageDialog(frame, "⏰ Time's Up!", "Countdown Finished", JOptionPane.INFORMATION_MESSAGE);

                        // Reset Controls
                        btnStart.setEnabled(true);
                        btnPause.setEnabled(false);
                        btnPause.setText("Pause");
                        txtHours.setEnabled(true);
                        txtMinutes.setEnabled(true);
                        txtSeconds.setEnabled(true);
                    }
                }
            });

            // --- 9. Start Button Action ---
            btnStart.addActionListener(e -> {
                try {
                    int h = Integer.parseInt(txtHours.getText().trim());
                    int m = Integer.parseInt(txtMinutes.getText().trim());
                    int s = Integer.parseInt(txtSeconds.getText().trim());

                    if (h < 0 || m < 0 || s < 0 || (h == 0 && m == 0 && s == 0)) {
                        JOptionPane.showMessageDialog(frame, "Please enter a duration greater than 0.", "Invalid Time", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    totalSeconds = (h * 3600) + (m * 60) + s;
                    totalDuration = totalSeconds;

                    timeDisplay.setText(formatTime(totalSeconds));
                    progressBar.setValue(100);

                    // Lock Inputs
                    txtHours.setEnabled(false);
                    txtMinutes.setEnabled(false);
                    txtSeconds.setEnabled(false);
                    btnStart.setEnabled(false);
                    btnPause.setEnabled(true);
                    btnPause.setText("Pause");
                    isPaused = false;

                    swingTimer.start();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            // --- 10. Pause / Resume Button Action ---
            btnPause.addActionListener(e -> {
                if (!isPaused) {
                    swingTimer.stop();
                    isPaused = true;
                    btnPause.setText("Resume");
                } else {
                    swingTimer.start();
                    isPaused = false;
                    btnPause.setText("Pause");
                }
            });

            // --- 11. Reset Button Action ---
            btnReset.addActionListener(e -> {
                swingTimer.stop();
                isPaused = false;
                totalSeconds = 0;
                totalDuration = 0;

                timeDisplay.setText("00:00:00");
                progressBar.setValue(100);
                txtHours.setText("0");
                txtMinutes.setText("0");
                txtSeconds.setText("0");

                txtHours.setEnabled(true);
                txtMinutes.setEnabled(true);
                txtSeconds.setEnabled(true);
                btnStart.setEnabled(true);
                btnPause.setEnabled(false);
                btnPause.setText("Pause");
            });

            // Show window
            frame.setVisible(true);
        });
    }

    // --- UI Helper Methods for Modern Design ---

    private static JPanel createInputBlock(String labelText, JTextField field) {
        JPanel block = new JPanel();
        block.setLayout(new BoxLayout(block, BoxLayout.Y_AXIS));
        block.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 10));
        label.setForeground(new Color(148, 163, 184));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        field.setAlignmentX(Component.CENTER_ALIGNMENT);

        block.add(field);
        block.add(Box.createVerticalStrut(4));
        block.add(label);
        return block;
    }

    private static JTextField createStyledTextField(String text) {
        JTextField tf = new JTextField(text, 2);
        tf.setFont(new Font("Segoe UI", Font.BOLD, 18));
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setBackground(new Color(15, 23, 42));
        tf.setForeground(Color.WHITE);
        tf.setCaretColor(Color.WHITE);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(51, 65, 85), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        return tf;
    }

    private static JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(95, 36));
        return btn;
    }

    private static String formatTime(int totalSecs) {
        int hours = totalSecs / 3600;
        int minutes = (totalSecs % 3600) / 60;
        int seconds = totalSecs % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}