package org.example;

import org.example.controller.TimerController;
import org.example.model.TimerModel;
import org.example.view.TimerView;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Enable anti-aliased font rendering
        System.setProperty("awt.useSystemAAFontSettings", "on");
        System.setProperty("swing.aatext", "true");

        SwingUtilities.invokeLater(() -> {
            // Instantiate MVC components
            TimerModel model = new TimerModel();
            TimerView view = new TimerView();
            new TimerController(model, view);

            // Display the UI
            view.setVisible(true);
        });
    }
}