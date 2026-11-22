package org.example.model;

public class TimerModel {

    // Possible states of the timer
    public enum State {
        IDLE,
        RUNNING,
        PAUSED
    }

    private int totalDurationSeconds;
    private int remainingSeconds;
    private State currentState;

    public TimerModel() {
        this.totalDurationSeconds = 0;
        this.remainingSeconds = 0;
        this.currentState = State.IDLE;
    }

    /**
     * Initializes the timer with user-selected hours, minutes, and seconds.
     */
    public void setTime(int hours, int minutes, int seconds) {
        this.totalDurationSeconds = (hours * 3600) + (minutes * 60) + seconds;
        this.remainingSeconds = this.totalDurationSeconds;
        this.currentState = State.IDLE;
    }

    /**
     * Ticks the timer down by one second.
     * @return true if the timer just hit 00:00:00 (finished), false otherwise.
     */
    public boolean tick() {
        if (remainingSeconds > 0) {
            remainingSeconds--;
            if (remainingSeconds == 0) {
                currentState = State.IDLE;
                return true; // Finished
            }
        }
        return false;
    }

    /**
     * Resets the timer back to zero and IDLE state.
     */
    public void reset() {
        this.totalDurationSeconds = 0;
        this.remainingSeconds = 0;
        this.currentState = State.IDLE;
    }

    // State Transitions
    public void start() {
        this.currentState = State.RUNNING;
    }

    public void pause() {
        this.currentState = State.PAUSED;
    }

    public void resume() {
        this.currentState = State.RUNNING;
    }

    // Queries & Getters
    public State getCurrentState() {
        return currentState;
    }

    public boolean isRunning() {
        return currentState == State.RUNNING;
    }

    public boolean isPaused() {
        return currentState == State.PAUSED;
    }

    public boolean isIdle() {
        return currentState == State.IDLE;
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public int getTotalDurationSeconds() {
        return totalDurationSeconds;
    }

    /**
     * Calculates the progress bar percentage (0 to 100).
     */
    public int getProgressPercentage() {
        if (totalDurationSeconds == 0) {
            return 100;
        }
        return (int) (((double) remainingSeconds / totalDurationSeconds) * 100);
    }

    /**
     * Helper to format remaining seconds into HH:MM:SS.
     */
    public String getFormattedRemainingTime() {
        int hours = remainingSeconds / 3600;
        int minutes = (remainingSeconds % 3600) / 60;
        int seconds = remainingSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}