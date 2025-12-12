package io.github.yetti_eng.screens;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Dean;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;

public class GameScreenTest {

    @Test
    public void dontReleaseDeanTest() {
        // Setup (mocking dean so I can verify it's methods are called)
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        Dean dean = mock(Dean.class);
        when(dean.isEnabled()).thenReturn(false);
        screen.dean = dean;

        // Time is above dean release value
        screen.releaseDean(61, false, false);

        // Verify the dean release methods are called
        verify(dean, never()).show();
        verify(dean, never()).enable();
    }

    @Test
    public void boundaryReleaseDeanTest() {
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        Dean dean = mock(Dean.class);

        when(dean.isEnabled()).thenReturn(false);
        screen.dean = dean;
        screen.releaseDean(60, false, false);

        verify(dean).show();
        verify(dean).enable();
    }

    @Test
    public void releaseDeanTest() {
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        Dean dean = mock(Dean.class);

        when(dean.isEnabled()).thenReturn(false);
        screen.dean = dean;
        screen.releaseDean(60, false, false);

        verify(dean).show();
        verify(dean).enable();
    }

    @Test
    public void updateEventCountersTest() {
        // Mocks
        YettiGame game = mock(YettiGame.class);
        GameScreen screen = new GameScreen(game, true);
        screen.hiddenText = mock(Label.class);
        screen.negativeText = mock(Label.class);
        screen.positiveText = mock(Label.class);

        // Set EventCounter values to 1, 2 & 3 (as seen below)
        EventCounter.reset();
        EventCounter.incrementHidden();
        EventCounter.incrementPositive();
        EventCounter.incrementPositive();
        EventCounter.incrementNegative();
        EventCounter.incrementNegative();
        EventCounter.incrementNegative();

        screen.updateEventCounters();

        // Ensure setText is called for each of them with the correct values
        verify(screen.hiddenText).setText("Hidden:1");
        verify(screen.positiveText).setText("Positive:2");
        verify(screen.negativeText).setText("Negative:3");
    }


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
    void clampCameraValidPosTest() {
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
    void clampCameraInvalidPosTest() {
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
