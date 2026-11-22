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

        // 1-second countdown engine
        this.swingTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleTick();
            }
        });

        // Action listeners
        this.view.addStartListener(e -> handleStart());
        this.view.addPauseListener(e -> handlePauseResume());
        this.view.addResetListener(e -> handleReset());

        // Preset chip listeners
        this.view.addPreset5Listener(e -> applyPreset(5));
        this.view.addPreset15Listener(e -> applyPreset(15));
        this.view.addPreset25Listener(e -> applyPreset(25));
    }

    private void applyPreset(int minutes) {
        swingTimer.stop();
        model.setTime(0, minutes, 0);

        view.setInputs("0", String.valueOf(minutes), "0");
        view.updateDisplay(model.getFormattedRemainingTime(), 100);
        view.setInputsEnabled(true);
        view.setButtonStates(true, false, "Pause");
    }

    private void handleTick() {
        boolean isFinished = model.tick();
        view.updateDisplay(model.getFormattedRemainingTime(), model.getProgressPercentage());

        if (isFinished) {
            swingTimer.stop();
            view.playAlarm(); // Triggers the modern audio chime
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