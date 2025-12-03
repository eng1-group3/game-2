package io.github.yetti_eng.entities;
import io.github.yetti_eng.InputHelper;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.screens.GameScreen;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class DeanTest {

   /*  @Test
    public void getsPlayerTest() {
        Texture tex = mock(Texture.class);
        Dean dean = new Dean(tex, 0f, 0f);

        YettiGame game = mock(YettiGame.class);
        GameScreen screen = mock(GameScreen.class);
        when(game.getScreen()).thenReturn(screen);
        assertTrue(dean.getsPlayer(game));*/
    //}

    @Test
    public void calculateMovementTest1() {
        Texture tex = mock(Texture.class);
        
        // Deliberately creating a pythgorean triple
        Player player = new Player(tex, 30f, 40f);
        Dean dean = new Dean(tex, 0f, 0f);
        dean.calculateMovement(player);

        // Dean speed is 5, so delta = 1 causes a movement of 5 in the player direction (the hypotenuse of the pythagorean triple)
        dean.doMove(1);
        
        Vector2 expectedPosition = new Vector2(3f, 4f);
        assertEquals(expectedPosition.x, dean.getX(), 0.001f);
        assertEquals(expectedPosition.y, dean.getY(), 0.001f);
    }

    
    @Test
    public void calculateMovementTest2() {
        Texture tex = mock(Texture.class);
        
        Player player = new Player(tex, 10f, 10f);
        Dean dean = new Dean(tex, 65.6f, 75.2f);
        dean.calculateMovement(player);

        dean.doMove(0.2f); // will move by the values in the unit vector as delta * speed = 1f
        
        Vector2 movement = InputHelper.makeUnitVector(new Vector2(player.getX() - dean.getX(), player.getY() - dean.getY()));

        float expectedX = 65.6f + movement.x;
        float expectedY = 75.2f + movement.y;

        assertEquals(expectedX, dean.getX(), 0.001f);
        assertEquals(expectedY, dean.getY(), 0.001f);
    }
}
