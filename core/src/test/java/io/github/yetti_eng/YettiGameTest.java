package io.github.yetti_eng;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.BeforeEach;
//import static org.mockito.Mockito.mock;

public class YettiGameTest {

    @Test
    public void scaledTest() {
        // Given
        float[] inputs = {3f, -3f, 0f, 1.5f};
        float worldScale = 80;
        
        for (float input : inputs) {
            // When
            float expected = input * worldScale;
            float result = YettiGame.scaled(input);
            // Then
            assertEquals(expected, result, 0.001f, "failed on input = " + input);
        }
    }
    // Needds Mockito
    //@Test
    //public void viewPortScaleTest() {
    //    YettiGame Game = new YettiGame();
    //    Game.create();
    //    System.out.print(Game.viewport.getScreenHeight());
    //    Game.viewport.getScreenHeight();
    //}

    // Will need mockito for this I think
    @Test
    public void createDisposeTest() {

    }

    @Test
    public void pauseTest() {
        YettiGame Game = new YettiGame();

        Game.pause();

        assertTrue(Game.isPaused());
    }

    @Test
    public void resumeTest() {
        YettiGame Game = new YettiGame();

        Game.pause();
        assertTrue(Game.isPaused());

        Game.resume();
        assertTrue(!Game.isPaused());
    }

    @Test
    public void multiplePauseAndResumeTest() {
        YettiGame Game = new YettiGame();

        for (int i = 0; i < 10; i++) {
            Game.pause();
            assertTrue(Game.isPaused());

            Game.resume();
            assertTrue(!Game.isPaused());
        }
    }

    @Test
    public void calculateFinalScoreNormalTest() {
        // Given
        YettiGame Game = new YettiGame();
        Game.score = 50;
        Game.timer = new Timer(250);
        // When
        Game.calculateFinalScore();
        int result = Game.score;
        // Then
        assertEquals(300, result, "Incorrectly calculated score when score = 50 & timer = 250");
    }

    @Test
    public void calculateFinalScoreZeroTest() {
        // Given
        YettiGame Game = new YettiGame();
        Game.score = 0;
        Game.timer = new Timer(0);
        // When
        Game.calculateFinalScore();
        int result = Game.score;
        // Then
        assertEquals(0, result, "Incorrectly calculated score when score = 0 & timer = 0");
    }
}
