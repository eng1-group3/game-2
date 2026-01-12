package io.github.yetti_eng.events;

import io.github.yetti_eng.events.AssignmentEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AssignmentEventTest {

    @Test
    public void testEventCanBeCreated() {
        AssignmentEvent event = new AssignmentEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        AssignmentEvent event = new AssignmentEvent();
        assertArrayEquals(new int[]{0, 0}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierNoPoints() {
        AssignmentEvent event = new AssignmentEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(0, modifier[1]);
    }
}
