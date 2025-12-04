package io.github.yetti_eng.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class EntityTest {
    
    @Test
    public void createEntityTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 1f, 2f, 3f, 4f, 5f, false);

        assertEquals(tex, entity.getTexture());
        assertEquals(new Rectangle(1f,2f,3f,4f), entity.getHitbox());
        assertEquals(5f, entity.getSpeed());
        assertFalse(entity.isSolid());
    }

    @Test
    public void addMovementTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 1f, 2f, 3f, 4f, 5f, false);
        
        entity.addMovement(1f, 2f);
        entity.doMove(0.2f); // Balances out the speed=5f so that the entity moves one pixel in the x directiona and 2 in the y direction

        Vector2 expectedPosition = new Vector2(2f, 4f);
        Rectangle expectedHitbox = new Rectangle(2f, 4f, 3f, 4f);

        assertEquals(expectedPosition, entity.getCurrentPos());
        assertEquals(expectedHitbox, entity.getHitbox());
    }

    @Test void collidedWithTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 1f, 2f, 1f, 1f, 0f, false);
        EntityTestable otherEntity = new EntityTestable(tex, 1.5f, 1.5f, 1f, 1f, 0f, false);

        assertTrue(entity.collidedWith(otherEntity));
    }

    @Test void collidedWithBoundaryTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 0.0001f, 0f, 1f, 2f, 5f, false);
        EntityTestable otherEntity = new EntityTestable(tex, 1f, 1f, 1f, 1f, 0f, false);

        assertTrue(entity.collidedWith(otherEntity));
    }

    @Test void notCollidedWithTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 1f, 1f, 1f, 1f, 0f, false);
        EntityTestable otherEntity = new EntityTestable(tex, 3f, 3f, 1f, 1f, 0f, false);

        assertFalse(entity.collidedWith(otherEntity));
    }

    @Test void notCollidedWithBoundaryTest() {
        Texture tex = mock(Texture.class);
        EntityTestable entity = new EntityTestable(tex, 0f, 0f, 1f, 2f, 5f, false);
        EntityTestable otherEntity = new EntityTestable(tex, 1f, 1f, 1f, 1f, 0f, false);

        assertFalse(entity.collidedWith(otherEntity));
    }
}
