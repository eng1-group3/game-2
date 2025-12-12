package io.github.yetti_eng;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class EventCounterTest {

    @BeforeEach
    /* Beacuse EventCounter is a static class, it will have different values
    depending on what order the tests are executed in. This is not good
    testing practise, so to fix this the EventCounter counts are set to
    zero before each test. */
    public void resetCount() {
        EventCounter.reset();
        // Assertions to confirm reset has worked as intended (as this has not been tested yet)
        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }
    
    @Test
    public void initiallyZeroTest() {

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }

    @Test
    public void incrementHiddenTest() {
        EventCounter.incrementHidden();

        assertEquals(1, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());

        EventCounter.incrementHidden();

        assertEquals(2, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }

    @Test
    public void incrementPositiveTest() {
        EventCounter.incrementPositive();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(1, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());

        EventCounter.incrementPositive();
        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(2, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }

    @Test
    public void incrementNegativeTest() {
        EventCounter.incrementNegative();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(1, EventCounter.getNegativeCount());

        EventCounter.incrementNegative();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(2, EventCounter.getNegativeCount());
    }

    @Test
    public void incrementAllTest() {
        EventCounter.incrementHidden();
        EventCounter.incrementPositive();
        EventCounter.incrementNegative();

        assertEquals(1, EventCounter.getHiddenCount());
        assertEquals(1, EventCounter.getPositiveCount());
        assertEquals(1, EventCounter.getNegativeCount());
    }

    @Test
    public void increaseAllVariousAmountsTest() {
        for (int i = 0; i < 2; i ++) {EventCounter.incrementHidden();};
        for (int i = 0; i < 4; i ++) {EventCounter.incrementPositive();};
        for (int i = 0; i < 3; i ++) {EventCounter.incrementNegative();};

        assertEquals(2, EventCounter.getHiddenCount());
        assertEquals(4, EventCounter.getPositiveCount());
        assertEquals(3, EventCounter.getNegativeCount());
    }

    @Test
    public void resetWhenZeroTest() {
        EventCounter.reset();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }

    @Test
    public void resetTest() {
        for (int i = 0; i < 2; i ++) {EventCounter.incrementHidden();};
        for (int i = 0; i < 4; i ++) {EventCounter.incrementPositive();};
        for (int i = 0; i < 3; i ++) {EventCounter.incrementNegative();};

        EventCounter.reset();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
    }
}
