package io.github.yetti_eng;

import io.github.yetti_eng.events.KeyEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class KeyEventTest {

    @Test
    public void testEventCanBeCreated() {
        KeyEvent event = new KeyEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        KeyEvent event = new KeyEvent();
        assertArrayEquals(new int[]{0, 10}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierAddsPoints() {
        KeyEvent event = new KeyEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(10, modifier[1]);
    }
}
