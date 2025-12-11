package io.github.yetti_eng;

import io.github.yetti_eng.events.WinEvent;
import io.github.yetti_eng.screens.GameScreen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class
WinEventTest {

    @Test
    public void testEventCanBeCreated() {
        WinEvent event = new WinEvent();
        assertNotNull(event);
    }

    @Test
    public void testActivateReturnsTrue() {
        WinEvent event = new WinEvent();
        GameScreen mockScreen = mock(GameScreen.class);
        YettiGame mockGame = mock(YettiGame.class);
        Timer mockTimer = mock(Timer.class);
        mockGame.timer = mockTimer;
        when(mockScreen.getGame()).thenReturn(mockGame);
        boolean result = event.activate(mockScreen, null, null);
        assertTrue(result);
    }

    @Test
    public void testScoreModifierIsZero() {
        WinEvent event = new WinEvent();
        int[] modifier = event.getScoreModifier();
        assertEquals(0, modifier[0]);
        assertEquals(0, modifier[1]);
    }
}
