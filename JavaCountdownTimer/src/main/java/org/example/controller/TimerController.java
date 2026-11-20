package org.example.controller;

import org.example.model.TimerModel;
import org.example.view.TimerView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerController {

    private final TimerModel model;
    private final TimerView view;
    private final Timer swingTimer;

    public TimerController(TimerModel model, TimerView view) {
        this.model = model;
        this.view = view;

        // Initialize 1-second background tick
        this.swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTick();
            }
        });

        // Attach event listeners to the view
        this.view.addStartListener(e -> handleStart());
        this.view.addPauseListener(e -> handlePauseResume());
        this.view.addResetListener(e -> handleReset());
    }

    private void handleTick() {
        boolean isFinished = model.tick();
        view.updateDisplay(model.getFormattedRemainingTime(), model.getProgressPercentage());

        if (isFinished) {
            swingTimer.stop();
            view.playBeep();
            view.showDialog("⏰ Time's Up!", "Countdown Finished", JOptionPane.INFORMATION_MESSAGE);
            view.setInputsEnabled(true);
            view.setButtonStates(true, false, "Pause");
        }
    }

    private void handleStart() {
        try {
            int h = Integer.parseInt(view.getHoursText());
            int m = Integer.parseInt(view.getMinutesText());
            int s = Integer.parseInt(view.getSecondsText());

            if (h < 0 || m < 0 || s < 0 || (h == 0 && m == 0 && s == 0)) {
                view.showDialog("Please enter a duration greater than 0.", "Invalid Time", JOptionPane.WARNING_MESSAGE);
                return;
            }

            model.setTime(h, m, s);
            model.start();

            view.updateDisplay(model.getFormattedRemainingTime(), 100);
            view.setInputsEnabled(false);
            view.setButtonStates(false, true, "Pause");

            swingTimer.start();
        } catch (NumberFormatException ex) {
            view.showDialog("Please enter numbers only.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handlePauseResume() {
        if (model.isRunning()) {
            swingTimer.stop();
            model.pause();
            view.setButtonStates(false, true, "Resume");
        } else if (model.isPaused()) {
            model.resume();
            swingTimer.start();
            view.setButtonStates(false, true, "Pause");
        }
    }

    private void handleReset() {
        swingTimer.stop();
        model.reset();

        view.updateDisplay("00:00:00", 100);
        view.setInputs("0", "0", "0");
        view.setInputsEnabled(true);
        view.setButtonStates(true, false, "Pause");
    }
}