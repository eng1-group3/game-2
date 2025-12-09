package io.github.yetti_eng.screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;

public class GameScreenTest {
    @Test
    public void detectCollisionsTest1() {
        YettiGame game = mock(YettiGame.class);
        Player player = mock(Player.class);
        Item item = mock(Item.class);
        
        // Other class methods used in the detectCollisions method
        when(player.collidedWith(item)).thenReturn(true);
        when(item.isEnabled()).thenReturn(true);
        when(item.isSolid()).thenReturn(true);
        when(item.isUsed()).thenReturn(false);

        // Setup
        GameScreen screen = new GameScreen(game, true);
        screen.player = player;
        screen.entities.add(item);

        // Verify the player position is reset to the values the method is called with
        screen.detectCollisions(new Vector2(5.0f, 6.0f));
        verify(player).setPosition(5.0f, 6.0f);
        verify(item).interact(game, screen, player);
    }

    @Test
    public void detectCollisionsTest2() {
        YettiGame game = mock(YettiGame.class);
        Player player = mock(Player.class);
        Item item = mock(Item.class);
        
        // Other class methods used in the detectCollisions method
        when(player.collidedWith(item)).thenReturn(true);
        when(item.isEnabled()).thenReturn(true);
        when(item.isSolid()).thenReturn(false);
        when(item.isUsed()).thenReturn(true);

        // Setup
        GameScreen screen = new GameScreen(game, true);
        screen.player = player;
        screen.entities.add(item);

        // Verify the player position is reset to the values the method is called with
        screen.detectCollisions(new Vector2(5.0f, 6.0f));
        verify(player, never()).setPosition(5.0f, 6.0f);
        verify(item, never()).interact(game, screen, player);
    }

    @Test
    void clampCameraValidTest() {
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        screen.camera = new OrthographicCamera();

        game.gameViewport = mock(FillViewport.class);
        when(game.gameViewport.getWorldWidth()).thenReturn(200f); // minCameraX = 100
        when(game.gameViewport.getWorldHeight()).thenReturn(100f); // minCameraY = 50
    
        screen.mapWidth = 500f; // maxCameraX = 400
        screen.mapHeight = 400f; // maxCamearY = 350

        // A valid camera position
        screen.camera.position.x = 300;
        screen.camera.position.y = 200;

        screen.clampCamera();

        assertEquals(300, screen.camera.position.x, 0.001f);
        assertEquals(200, screen.camera.position.y, 0.001f);
    }

    @Test
    void clampCameraInValidTest() {
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        screen.camera = new OrthographicCamera();

        game.gameViewport = mock(FillViewport.class);
        when(game.gameViewport.getWorldWidth()).thenReturn(200f); // minCameraX = 100
        when(game.gameViewport.getWorldHeight()).thenReturn(100f); // minCameraY = 50
    
        screen.mapWidth = 500f; // maxCameraX = 400
        screen.mapHeight = 400f; // maxCamearY = 350

        // An invalid camera position that needs to be clamped
        screen.camera.position.x = 600;
        screen.camera.position.y = 45;

        screen.clampCamera();

        // Checking values have been clamped
        assertEquals(400, screen.camera.position.x, 0.001f);
        assertEquals(50, screen.camera.position.y, 0.001f);
    }
}
