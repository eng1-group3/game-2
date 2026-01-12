package io.github.yetti_eng.screens;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;

public class WinScreenTest {
    
    @Test
    public void handleTypingSequenceTest() {
        YettiGame game = mock(YettiGame.class);
        when(game.calculateFinalScore()).thenReturn(56);

        WinScreen screen = new WinScreen(game, false);

        Gdx.input = mock(Input.class);

        // Sequence D -> 2 -> 3 -> BACKSPACE results in username == "d2"
        when(Gdx.input.isKeyJustPressed(Keys.D)).thenReturn(true);
        when(Gdx.input.isKeyJustPressed(Keys.NUM_2)).thenReturn(true);
        when(Gdx.input.isKeyJustPressed(Keys.NUM_3)).thenReturn(true);
        when(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)).thenReturn(true);

        screen.handleTyping();

        assertEquals("D2", screen.getUsername());
    }

    @Test
    public void handleTypingBackspaceTest() {
        // Checks that backspacing on a blank username doesn't change the username

        YettiGame game = mock(YettiGame.class);
        when(game.calculateFinalScore()).thenReturn(56);

        WinScreen screen = new WinScreen(game, false);

        Gdx.input = mock(Input.class);

        // Sequence BACKSPACE -> D results in username == "D"
        // Has to be done in 2 calls due to the sequence of key checks
        when(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)).thenReturn(true);
        screen.handleTyping();
        when(Gdx.input.isKeyJustPressed(Keys.BACKSPACE)).thenReturn(false);
        when(Gdx.input.isKeyJustPressed(Keys.D)).thenReturn(true);
        screen.handleTyping();

        assertEquals("D", screen.getUsername());
    }

    @Test
    public void resetGameTest() {
        YettiGame game = mock(YettiGame.class);

        WinScreen screen = new WinScreen(game, false);
        screen.resetGame();

        assertEquals(0, EventCounter.getHiddenCount());
        assertEquals(0, EventCounter.getPositiveCount());
        assertEquals(0, EventCounter.getNegativeCount());
        assertEquals(0, game.score);
    }

    // Not testing leaderboard logic as this is done in LeaderboardTest
}
