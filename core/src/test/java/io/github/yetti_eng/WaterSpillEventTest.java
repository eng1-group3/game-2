package io.github.yetti_eng;

import io.github.yetti_eng.events.WaterSpillEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WaterSpillEventTest {

    @Test
    public void testEventCanBeCreated() {
        WaterSpillEvent event = new WaterSpillEvent();
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        WaterSpillEvent event = new WaterSpillEvent();
        assertArrayEquals(new int[]{1, 50}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierSubtractsPoints() {
        WaterSpillEvent event = new WaterSpillEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(1, modifier[0]);
        assertEquals(50, modifier[1]);
    }
}
