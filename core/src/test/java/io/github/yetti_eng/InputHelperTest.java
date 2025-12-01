package io.github.yetti_eng;

import org.junit.jupiter.api.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class InputHelperTest {
    InputHelper inputClass = new InputHelper();
    
    @Test
    public void upKeyPressed() {

        when(Gdx.input.isKeyPressed(Keys.UP)).thenReturn(true);

        assertTrue(InputHelper.anyOfTheseKeysPressed(Keys.UP, Keys.W));
    }
}
