package io.github.yetti_eng;

import io.github.yetti_eng.events.ClosingDoorEvent;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * Tests for ClosingDoorEvent:
 * - Event can be created
 * - activate() returns false (door always open, closes via timer)
 * - Score modifier is 0 (no points awarded)
 */

public class ClosingDoorEventTest {

    @Test
    public void testEventCanBeCreated() {
        ClosingDoorEvent event = new ClosingDoorEvent(19, 2.2f);
        assertNotNull(event);
    }

    @Test
    public void testActivateReturnsFalse() {
        ClosingDoorEvent event = new ClosingDoorEvent(19, 2.2f);
        boolean result = event.activate(null, null, null);
        assertFalse(result);
    }

    @Test
    public void testScoreModifierIsZero() {
        ClosingDoorEvent event = new ClosingDoorEvent(19, 2.2f);
        assertEquals(0, event.getScoreModifier());
    }
}
