package org.example.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class TimerView {

    private JFrame frame;
    private JTextField txtHours;
    private JTextField txtMinutes;
    private JTextField txtSeconds;
    private JLabel timeDisplay;
    private JProgressBar progressBar;
    private JButton btnStart;
    private JButton btnPause;
    private JButton btnReset;

    public TimerView() {
        initUI();
    }

    private void initUI() {
        // --- Frame Setup ---
        frame = new JFrame("Countdown Timer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 520);
        frame.setMinimumSize(new Dimension(500, 450));
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(new Color(15, 23, 42)); // Slate Dark
        frame.setLayout(new GridBagLayout()); // Keeps Card Centered

        // --- Card Panel ---
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(30, 41, 59));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(51, 65, 85));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(30, 40, 30, 40));
        card.setPreferredSize(new Dimension(440, 380));

        // --- Header Title ---
        JLabel titleLabel = new JLabel("COUNTDOWN TIMER");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(new Color(148, 163, 184));

        // --- Input Fields ---
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        inputPanel.setOpaque(false);

        txtHours = createStyledTextField("0");
        txtMinutes = createStyledTextField("0");
        txtSeconds = createStyledTextField("0");

        inputPanel.add(createInputBlock("HRS", txtHours));
        inputPanel.add(createInputBlock("MIN", txtMinutes));
        inputPanel.add(createInputBlock("SEC", txtSeconds));

        // --- Digital Clock Display ---
        timeDisplay = new JLabel("00:00:00", SwingConstants.CENTER);
        timeDisplay.setAlignmentX(Component.CENTER_ALIGNMENT);
        timeDisplay.setFont(new Font("Consolas", Font.BOLD, 52));
        timeDisplay.setForeground(new Color(56, 189, 248));

        // --- Progress Bar ---
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(100);
        progressBar.setPreferredSize(new Dimension(340, 8));
        progressBar.setMaximumSize(new Dimension(340, 8));
        progressBar.setForeground(new Color(56, 189, 248));
        progressBar.setBackground(new Color(51, 65, 85));
        progressBar.setBorderPainted(false);
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        // --- Action Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        buttonPanel.setOpaque(false);

        btnStart = createStyledButton("Start", new Color(34, 197, 94));
        btnPause = createStyledButton("Pause", new Color(245, 158, 11));
        btnReset = createStyledButton("Reset", new Color(239, 68, 68));
        btnPause.setEnabled(false);

        buttonPanel.add(btnStart);
        buttonPanel.add(btnPause);
        buttonPanel.add(btnReset);

        // Assemble Card
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(inputPanel);
        card.add(Box.createVerticalStrut(25));
        card.add(timeDisplay);
        card.add(Box.createVerticalStrut(15));
        card.add(progressBar);
        card.add(Box.createVerticalStrut(25));
        card.add(buttonPanel);

        frame.add(card);
    }

    // --- UI Mutation Methods (Called by Controller) ---

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public void updateDisplay(String formattedTime, int progressPercentage) {
        timeDisplay.setText(formattedTime);
        progressBar.setValue(progressPercentage);
    }

    public void setInputs(String h, String m, String s) {
        txtHours.setText(h);
        txtMinutes.setText(m);
        txtSeconds.setText(s);
    }

    public void setInputsEnabled(boolean enabled) {
        txtHours.setEnabled(enabled);
        txtMinutes.setEnabled(enabled);
        txtSeconds.setEnabled(enabled);
    }

    public void setButtonStates(boolean startEnabled, boolean pauseEnabled, String pauseText) {
        btnStart.setEnabled(startEnabled);
        btnPause.setEnabled(pauseEnabled);
        btnPause.setText(pauseText);
    }

    public void showDialog(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(frame, message, title, messageType);
    }

    public void playBeep() {
        Toolkit.getDefaultToolkit().beep();
    }

    // --- Input Getters ---

    public String getHoursText() {
        return txtHours.getText().trim();
    }

    public String getMinutesText() {
        return txtMinutes.getText().trim();
    }

    public String getSecondsText() {
        return txtSeconds.getText().trim();
    }

    // --- Listener Attachment Hooks ---

    public void addStartListener(ActionListener listener) {
        btnStart.addActionListener(listener);
    }

    public void addPauseListener(ActionListener listener) {
        btnPause.addActionListener(listener);
    }

    public void addResetListener(ActionListener listener) {
        btnReset.addActionListener(listener);
    }

    // --- Styling Helpers ---

    private JPanel createInputBlock(String labelText, JTextField field) {
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

    private JTextField createStyledTextField(String text) {
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

    private JButton createStyledButton(String text, Color bg) {
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
}