package io.github.yetti_eng;

import io.github.yetti_eng.events.WaterSpillEvent;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.screens.GameScreen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class
WaterSpillEventTest {

    @Test
    public void testEventCanBeCreated() {
        WaterSpillEvent event = new WaterSpillEvent();
        assertNotNull(event);
    }

    @Test
    public void testActivateReturnsTrueWhenNotUsed() {
        WaterSpillEvent event = new WaterSpillEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        when(mockItem.isUsed()).thenReturn(false);
        boolean result = event.activate(mockScreen, null, mockItem);
        assertTrue(result);
    }

    @Test
    public void testActivateReturnsFalseWhenAlreadyUsed() {
        WaterSpillEvent event = new WaterSpillEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        when(mockItem.isUsed()).thenReturn(true);
        boolean result = event.activate(mockScreen, null, mockItem);
        assertFalse(result);
    }

    @Test
    public void testScoreModifier() {
        WaterSpillEvent event = new WaterSpillEvent();
        assertArrayEquals(new int[]{1, 50}, event.getScoreModifier());
    }

    @Test
    public void testActivateShowsItem() {
        WaterSpillEvent event = new WaterSpillEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        Item mockItem = mock(Item.class);
        when(mockItem.isUsed()).thenReturn(false);
        event.activate(mockScreen, null, mockItem);
        verify(mockItem).show();
    }
}
