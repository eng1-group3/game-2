package io.github.yetti_eng.events;

import com.badlogic.gdx.graphics.Texture;
import io.github.yetti_eng.events.HiddenWallEvent;
import io.github.yetti_eng.entities.Item;
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
    public void testActivateReturnsTrue() {
        Texture mockTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockTexture);
        Item mockItem = mock(Item.class);
        boolean result = event.activate(mock(io.github.yetti_eng.screens.GameScreen.class), null, mockItem);
        assertTrue(result);
    }

    @Test
    public void testScoreModifierIsZero() {
        Texture mockTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockTexture);
        assertArrayEquals(new int[]{0, 0}, event.getScoreModifier());
    }

    @Test
    public void testActivateMakesItemNonSolid() {
        Texture mockPassableTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockPassableTexture);
        Item mockItem = mock(Item.class);
         
        event.activate(mock(io.github.yetti_eng.screens.GameScreen.class), null, mockItem);
        verify(mockItem).setSolid(false);
    }

    @Test
    public void testActivateChangesTexture() {
        Texture mockPassableTexture = mock(Texture.class);
        HiddenWallEvent event = new HiddenWallEvent(mockPassableTexture);
        Item mockItem = mock(Item.class);
        event.activate(mock(io.github.yetti_eng.screens.GameScreen.class), null, mockItem);
        verify(mockItem).setTexture(mockPassableTexture);
    }
}