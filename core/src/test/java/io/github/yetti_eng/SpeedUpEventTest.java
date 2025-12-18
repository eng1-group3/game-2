package io.github.yetti_eng;

import io.github.yetti_eng.events.SpeedUpEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SpeedUpEventTest {

    @Test
    public void testEventCanBeCreated() {
        SpeedUpEvent event = new SpeedUpEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        SpeedUpEvent event = new SpeedUpEvent();
        assertArrayEquals(new int[]{0, 200}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierAddsPoints() {
        SpeedUpEvent event = new SpeedUpEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(200, modifier[1]);
    }
}
