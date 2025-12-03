package io.github.yetti_eng.entities;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

public class PlayerTest {
    
    @Test
    public void createPlayerTest() {
        float x = 15f;
        float y = 20f;
        
        Texture tex = mock(Texture.class);
        Player player = new Player(tex, x, y);

        // Cordinates are as expected
        assertEquals(x, player.getX(), 0.01f);
        assertEquals(y, player.getY(), 0.01f);

        // Hitbox is as expected
        Rectangle expectedHitbox = new Rectangle(x, y, 0.9f, 1.6f);
        assertEquals(expectedHitbox, player.getHitbox());

        // Speed is initially set to PLAYER_SPEED = 6f
        assertEquals(6f, player.getSpeed(), 0.01);

        // Player is not solid
        assertFalse(player.isSolid());

        // Player inventory is initially empty
        assertTrue(player.inventory.isEmpty());
    }

    @Test
    public void hasUsedItemTest() {
        Texture tex = mock(Texture.class);
        Player player = new Player(tex, 10f, 20f);

        Item item1 = new Item(null, "item1", tex, 0, 0);
        player.inventory.add(item1);

        assertTrue(player.hasUsedItem("item1"));
        assertFalse(player.hasUsedItem("item2"));
    }

    @Test
    public void hasUsedItemMultipleTest() {
        Texture tex = mock(Texture.class);
        Player player = new Player(tex, 10f, 20f);

        Item item1 = new Item(null, "item1", tex, 0, 0);
        Item item2 = new Item(null, "item2", tex, 0, 0);
        Item item3 = new Item(null, "item3", tex, 0, 0);
        player.inventory.add(item1);
        player.inventory.add(item2);
        player.inventory.add(item3);

        assertTrue(player.hasUsedItem("item1"));
        assertTrue(player.hasUsedItem("item2"));
        assertTrue(player.hasUsedItem("item3"));
        assertFalse(player.hasUsedItem("item4"));
    }
}
