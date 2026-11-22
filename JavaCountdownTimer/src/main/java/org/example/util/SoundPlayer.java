package org.example.util;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundPlayer {

    private static final String SOUND_RESOURCE_PATH = "/sounds/alarm.wav";

    /**
     * Plays the alarm sound. Looks for a WAV file in resources first;
     * falls back to a clean synthesized chime if the file is not found.
     */
    public static void playAlarmSound() {
        new Thread(() -> {
            try {
                InputStream is = SoundPlayer.class.getResourceAsStream(SOUND_RESOURCE_PATH);
                if (is != null) {
                    // Play from resource file
                    try (BufferedInputStream bis = new BufferedInputStream(is);
                         AudioInputStream audioStream = AudioSystem.getAudioInputStream(bis)) {

                        Clip clip = AudioSystem.getClip();
                        clip.open(audioStream);
                        clip.start();
                    }
                } else {
                    // Fallback: Generate pleasant two-tone chime (523.25 Hz C5 -> 659.25 Hz E5 -> 783.99 Hz G5)
                    playSynthesizedChime();
                }
            } catch (Exception e) {
                // Last-resort fallback to system beep if audio device fails
                java.awt.Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }

    /**
     * Synthesizes a pleasant ascending Major Chord chime in software.
     */
    private static void playSynthesizedChime() {
        try {
            float sampleRate = 44100f;
            byte[] buf = new byte[44100 * 2]; // ~1 second buffer

            // Generate a 3-tone pleasant notification chime (C5, E5, G5)
            double[] frequencies = {523.25, 659.25, 783.99};
            int samplesPerTone = (int) (sampleRate * 0.25); // 250ms each

            int totalSamples = samplesPerTone * frequencies.length;
            for (int i = 0; i < totalSamples; i++) {
                int toneIdx = i / samplesPerTone;
                double freq = frequencies[toneIdx];
                double angle = 2.0 * Math.PI * i / (sampleRate / freq);

                // Add soft decay envelope to avoid harsh clicking
                double decay = 1.0 - ((double) (i % samplesPerTone) / samplesPerTone);
                buf[i] = (byte) (Math.sin(angle) * 100 * decay);
            }

            AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, false);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                line.open(format);
                line.start();
                line.write(buf, 0, totalSamples);
                line.drain();
            }
        } catch (Exception ignored) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }
}