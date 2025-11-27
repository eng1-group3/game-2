package io.github.yetti_eng;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class TimerTest {
    
    @Test
    public void createTimerTest() {
        Timer timer = new Timer(300);

        int time_remaining = timer.getRemainingTime();

        assertEquals(300, time_remaining, "Timer has the incorrect duration when created");
        assertFalse(timer.isActive(), "Timer shouldn't be active when created");
        assertFalse(timer.isFinished(), "Timer shouldn't be finished when created");
    }

    @Test
    public void createTimerZeroTest() {
        Timer timer = new Timer(0);

        int time_remaining = timer.getRemainingTime();

        assertEquals(0, time_remaining);
        assertFalse(timer.isActive(), "Timer shouldn't be active when created");
        assertFalse(timer.isFinished(), "Timer shouldn't be finished when created");
    }

    @Test
    public void createNegativeTimerTest() {
        assertThrows(AssertionError.class, () -> new Timer(-10));
    }

    @Test
    public void playTest() {
        Timer timer = new Timer(100);

        timer.play();

        assertTrue(timer.isActive(), "Timer has failed to activate after play was called.");
    }

    @Test
    public void pauseTest() {
        Timer timer = new Timer(100);
        
        timer.pause();

        assertFalse(timer.isActive(), "Timer should deactivate when paused");
    }

    @Test
    public void doublePlayTest() {
        Timer timer = new Timer(100);

        timer.play();
        timer.play();

        assertTrue(timer.isActive());
    }

    @Test
    public void doublePauseTest() {
        Timer timer = new Timer(100);

        timer.pause();
        timer.pause();

        assertFalse(timer.isActive());
    }

    @Test
    public void playPauseTest() {
        Timer timer = new Timer(100);

        for (int i = 0; i < 10; i++) {
            timer.pause();
            assertFalse(timer.isActive());
            timer.play();
            assertTrue(timer.isActive());
        }
    }

    public void hasNotElapsedTest() {
        Timer timer = new Timer(100);
        timer.play();

        assertFalse(timer.hasElapsed());
    }

    @Test
    public void hasElapsedTest() {
        Timer timer = new Timer(0);
        timer.play();

        assertTrue(timer.hasElapsed());
    }

    @Test
    public void remainingTimeWhenInactiveTest() {
        Timer timer = new Timer(100);

        assertFalse(timer.isActive());
        assertEquals(100, timer.getRemainingTime());
    }

    @Test
    public void remainingTimeWhenActiveTest() {
        Timer timer = new Timer(100);
        timer.play();

        assertTrue(timer.isActive());
        assertEquals(100, timer.getRemainingTime());
    }
}
