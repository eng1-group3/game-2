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

        Rectangle expectedHitbox = new Rectangle(x, y, 0.9f, 1.6f);
        assertEquals(expectedHitbox, player.getHitbox());
        assertEquals(6f, player.getSpeed(), 0.01);

        
    }
}
