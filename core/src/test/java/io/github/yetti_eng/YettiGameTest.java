package io.github.yetti_eng;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class YettiGameTest {

    @Test
    public void scaledTest() {
        float worldScale = 80;
        float[] testValues = {3f, -3f, 0f, 1.5f};

        for (float testValue : testValues) {
            float expected = testValue * worldScale;
            float result = YettiGame.scaled(testValue);
            assertEquals(expected, result, 0.001f);
        }
    }
}
