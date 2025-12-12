package io.github.yetti_eng;

import com.badlogic.gdx.graphics.Texture;
import io.github.yetti_eng.events.HiddenWallEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class HiddenWallEventTest {

    @Test
    public void testEventCanBeCreated() {
        Texture mockTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockTexture);
        assertNotNull(event);
    }

    @Test
    public void testScoreModifier() {
        Texture mockTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockTexture);
        assertArrayEquals(new int[]{0, 200}, event.getScoreModifier());
    }

    @Test
    public void testScoreModifierAddsPoints() {
        Texture mockTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockTexture);
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(200, modifier[1]);
    }
}
