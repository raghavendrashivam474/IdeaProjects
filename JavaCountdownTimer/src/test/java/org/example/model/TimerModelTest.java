package org.example.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TimerModel Unit Tests")
public class TimerModelTest {

    private TimerModel model;

    @BeforeEach
    void setUp() {
        model = new TimerModel();
    }

    @Test
    @DisplayName("TC-01: Initial state should be IDLE with 0 seconds")
    void testInitialState() {
        assertTrue(model.isIdle());
        assertEquals(TimerModel.State.IDLE, model.getCurrentState());
        assertEquals(0, model.getRemainingSeconds());
        assertEquals("00:00:00", model.getFormattedRemainingTime());
    }

    @Test
    @DisplayName("TC-02: Starting timer transitions state to RUNNING")
    void testStartTimer() {
        model.setTime(0, 1, 0); // 1 minute = 60s
        model.start();

        assertTrue(model.isRunning());
        assertEquals(TimerModel.State.RUNNING, model.getCurrentState());
        assertEquals(60, model.getRemainingSeconds());
    }

    @Test
    @DisplayName("TC-03: Pausing timer transitions state to PAUSED")
    void testPauseTimer() {
        model.setTime(0, 1, 0);
        model.start();
        model.pause();

        assertTrue(model.isPaused());
        assertEquals(TimerModel.State.PAUSED, model.getCurrentState());
    }

    @Test
    @DisplayName("TC-04: Resuming timer transitions state back to RUNNING")
    void testResumeTimer() {
        model.setTime(0, 1, 0);
        model.start();
        model.pause();
        model.resume();

        assertTrue(model.isRunning());
        assertEquals(TimerModel.State.RUNNING, model.getCurrentState());
    }

    @Test
    @DisplayName("TC-05: Tick decreases remaining time by exactly 1 second")
    void testCountdownTick() {
        model.setTime(0, 0, 5); // 5 seconds
        model.start();

        boolean isFinished = model.tick();

        assertFalse(isFinished);
        assertEquals(4, model.getRemainingSeconds());
        assertEquals("00:00:04", model.getFormattedRemainingTime());
    }

    @Test
    @DisplayName("TC-06: Boundary condition - Tick returns true when reaching 0 and state resets to IDLE")
    void testBoundaryZeroCondition() {
        model.setTime(0, 0, 1); // 1 second
        model.start();

        boolean isFinished = model.tick(); // Reaches 0

        assertTrue(isFinished);
        assertEquals(0, model.getRemainingSeconds());
        assertTrue(model.isIdle());
    }

    @Test
    @DisplayName("TC-07: Progress bar percentage calculates correctly")
    void testProgressCalculation() {
        model.setTime(0, 0, 200); // 200 seconds total

        // 100% at start
        assertEquals(100, model.getProgressPercentage());

        // Tick down 100 times (50% remaining)
        for (int i = 0; i < 100; i++) {
            model.tick();
        }

        assertEquals(100, model.getRemainingSeconds());
        assertEquals(50, model.getProgressPercentage());
    }

    @Test
    @DisplayName("TC-08: Time formatting outputs correct HH:MM:SS format")
    void testTimeFormatting() {
        model.setTime(1, 23, 45); // 1h 23m 45s = 5025 seconds
        assertEquals("01:23:45", model.getFormattedRemainingTime());
    }

    @Test
    @DisplayName("TC-09: Reset restores original default state")
    void testReset() {
        model.setTime(0, 10, 0);
        model.start();
        model.tick();
        model.reset();

        assertTrue(model.isIdle());
        assertEquals(0, model.getRemainingSeconds());
        assertEquals(0, model.getTotalDurationSeconds());
        assertEquals("00:00:00", model.getFormattedRemainingTime());
        assertEquals(100, model.getProgressPercentage());
    }

    @Test
    @DisplayName("TC-10: Initializing with zero duration behaves correctly")
    void testZeroDuration() {
        model.setTime(0, 0, 0);
        assertEquals(0, model.getRemainingSeconds());
        assertEquals(100, model.getProgressPercentage());
        assertEquals("00:00:00", model.getFormattedRemainingTime());
    }
}