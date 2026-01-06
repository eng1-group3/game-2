package io.github.yetti_eng.events;

import io.github.yetti_eng.events.DoorEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DoorEventTest {

    @Test
    public void testEventCanBeCreated() {
        DoorEvent event = new DoorEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        DoorEvent event = new DoorEvent();
        assertArrayEquals(new int[]{0, 10}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierAddsPoints() {
        DoorEvent event = new DoorEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(10, modifier[1]);
    }
}
