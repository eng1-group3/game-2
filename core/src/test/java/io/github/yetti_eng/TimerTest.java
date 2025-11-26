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
}
