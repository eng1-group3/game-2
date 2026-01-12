package io.github.yetti_eng.events;

import io.github.yetti_eng.events.DoubleScoreEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoubleScoreEventTest {

    @Test
    public void testEventCanBeCreated() {
        DoubleScoreEvent event = new DoubleScoreEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        DoubleScoreEvent event = new DoubleScoreEvent();
        assertArrayEquals(new int[]{2, 2}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierMultiplies() {
        DoubleScoreEvent event = new DoubleScoreEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(2, modifier[0]);
        assertEquals(2, modifier[1]);
    }
}
