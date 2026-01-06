package io.github.yetti_eng.events;

import io.github.yetti_eng.events.SlowDownEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SlowDownEventTest {

    @Test
    public void testEventCanBeCreated() {
        SlowDownEvent event = new SlowDownEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        SlowDownEvent event = new SlowDownEvent();
        assertArrayEquals(new int[]{0, -200}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierSubtractsPoints() {
        SlowDownEvent event = new SlowDownEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(-200, modifier[1]);
    }
}
