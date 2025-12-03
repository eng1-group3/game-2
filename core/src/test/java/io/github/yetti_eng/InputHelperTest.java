package io.github.yetti_eng;

import org.junit.jupiter.api.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.*;
import com.badlogic.gdx.math.Vector2;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class InputHelperTest {
    
    @BeforeEach
    void setup() {
        Gdx.input = mock(Input.class);
    }
    InputHelper inputClass = new InputHelper();

    @Test
    public void anyOfTheseKeysPressedValidTest() {
        
        when(Gdx.input.isKeyPressed(Keys.UP)).thenReturn(true);

        assertTrue(InputHelper.anyOfTheseKeysPressed(Keys.UP, Keys.LEFT, Keys.A));
    }

    @Test
    public void anyOfTheseKeysPressedInvalidTest() {
        
        when(Gdx.input.isKeyPressed(Keys.S)).thenReturn(true);

        assertFalse(InputHelper.anyOfTheseKeysPressed(Keys.UP, Keys.LEFT, Keys.A));
    }
    
    @Test
    public void moveUpPressedUpKeyTest() {

        when(Gdx.input.isKeyPressed(Keys.UP)).thenReturn(true);

        assertTrue(InputHelper.moveUpPressed());
    }

    @Test
    public void wKeymoveUpPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.W)).thenReturn(true);

        assertTrue(InputHelper.moveUpPressed());
    }

    @Test
    public void rightKeyMoveRightPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.RIGHT)).thenReturn(true);

        assertTrue(InputHelper.moveRightPressed());
    }

    @Test
    public void dKeyMoveRightPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.D)).thenReturn(true);

        assertTrue(InputHelper.moveRightPressed());
    }

    @Test
    public void leftKeyMoveLeftPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.LEFT)).thenReturn(true);

        assertTrue(InputHelper.moveLeftPressed());
    }

    @Test
    public void aKeyMoveLeftPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.A)).thenReturn(true);

        assertTrue(InputHelper.moveLeftPressed());
    }

    @Test
    public void downKeyMoveDownPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.DOWN)).thenReturn(true);

        assertTrue(InputHelper.moveDownPressed());
    }

    @Test
    public void sKeyMoveDownPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.S)).thenReturn(true);

        assertTrue(InputHelper.moveDownPressed());
    }

    @Test
    public void moveUpNotPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.DOWN)).thenReturn(true);

        assertFalse(InputHelper.moveUpPressed());
    }

    @Test
    public void moveRightNotPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.LEFT)).thenReturn(true);

        assertFalse(InputHelper.moveRightPressed());
    }

    @Test
    public void moveLeftNotPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.RIGHT)).thenReturn(true);

        assertFalse(InputHelper.moveLeftPressed());
    }

    @Test
    public void moveDownNotPressedTest() {

        when(Gdx.input.isKeyPressed(Keys.UP)).thenReturn(true);

        assertFalse(InputHelper.moveDownPressed());
    }

    @Test
    public void makeUnitVectorZeroTest() {
        Vector2 vec = new Vector2(0,0);

        assertEquals(vec, InputHelper.makeUnitVector(vec));
    }

    @Test
    public void makeUnitVectorTest() {
        Vector2 inputVec = new Vector2(1.5f, 2.7f);

        Vector2 unitVec = InputHelper.makeUnitVector(inputVec);

        // Unit vector returned has length 1
        assertEquals(1, unitVec.len(), 0.01f);

        // Unit vector has the same angle as the original vector
        assertEquals(inputVec.angleDeg(), unitVec.angleDeg(), 0.01f);
    }

    @Test
    public void makeUnitVectorNegativeTest() {
        Vector2 inputVec = new Vector2(-1.3f, -1.4f);

        Vector2 unitVec = InputHelper.makeUnitVector(inputVec);

        // Unit vector returned has length 1
        assertEquals(1, unitVec.len(), 0.01f);

        // Unit vector has the same angle as the original vector
        assertEquals(inputVec.angleDeg(), unitVec.angleDeg(), 0.01f);
    }
}
