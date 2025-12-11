package io.github.yetti_eng;

import io.github.yetti_eng.events.KeyEvent;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.screens.GameScreen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KeyEventTest {

    @Test
    public void testEventCanBeCreated() {
        KeyEvent event = new KeyEvent();
        assertNotNull(event);
    }

    @Test
    public void testActivateReturnsTrue() {
        KeyEvent event = new KeyEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        boolean result = event.activate(mockScreen, null, mockItem);
        assertTrue(result);
    }

    @Test
    public void testScoreModifier() {
        KeyEvent event = new KeyEvent();
        assertArrayEquals(new int[]{0, 10}, event.getScoreModifier());
    }

    @Test
    public void testActivateDisablesItem() {
        KeyEvent event = new KeyEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        event.activate(mockScreen, null, mockItem);
        verify(mockItem).disable();
    }

    @Test
    public void testActivateHidesItem() {
        KeyEvent event = new KeyEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        event.activate(mockScreen, null, mockItem);
        verify(mockItem).hide();
    }
}
