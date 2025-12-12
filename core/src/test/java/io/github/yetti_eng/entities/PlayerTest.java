package io.github.yetti_eng.entities;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.Texture;

public class PlayerTest {

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
