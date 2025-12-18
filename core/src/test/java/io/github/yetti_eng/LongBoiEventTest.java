package io.github.yetti_eng;

import io.github.yetti_eng.events.LongBoiEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LongBoiEventTest {

    @Test
    public void testEventCanBeCreated() {
        LongBoiEvent event = new LongBoiEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        LongBoiEvent event = new LongBoiEvent();
        assertArrayEquals(new int[]{0, 100}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierAddsPoints() {
        LongBoiEvent event = new LongBoiEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(100, modifier[1]);
    }
}
