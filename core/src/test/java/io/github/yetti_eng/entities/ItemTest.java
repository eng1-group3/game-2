package io.github.yetti_eng.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.events.Event;
import io.github.yetti_eng.screens.GameScreen;

public class ItemTest {
    
    @Test
    public void interactNotUsedTest() {
        // Given
        Event event = mock(Event.class);
        Item item = new Item(event, "item1", mock(Texture.class), 1f, 1f);
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = mock(GameScreen.class);
        Texture tex = mock(Texture.class);
        Player player = new Player(tex, 10f, 20f);
        when(event.activate(screen, player, item)).thenReturn(false);

        // When
        item.interact(game, screen, player);

        // Then
        assertFalse(item.isUsed());
        assertFalse(player.hasUsedItem("item1"));
    }

    @Test
    public void interactUsedTest() {
        // Given
        Event event = mock(Event.class);
        Item item = new Item(event, "item1", mock(Texture.class), 1f, 1f);
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = mock(GameScreen.class);
        Texture tex = mock(Texture.class);
        Player player = new Player(tex, 10f, 20f);
        when(event.activate(screen, player, item)).thenReturn(true);

        // When
        item.interact(game, screen, player);

        // Then
        assertTrue(item.isUsed());
        assertTrue(player.hasUsedItem("item1"));
        // ModifyScore logic will be tested elsewhere
    }
}
