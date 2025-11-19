package io.github.yetti_eng;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public final class InputHelper {
    public static boolean anyOfTheseKeysPressed(int... keys) {
        for (int key : keys) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }

    public static boolean moveUpPressed() {
        return anyOfTheseKeysPressed(Input.Keys.UP, Input.Keys.W);
    }

    public static boolean moveDownPressed() {
        return anyOfTheseKeysPressed(Input.Keys.DOWN, Input.Keys.S);
    }

    public static boolean moveLeftPressed() {
        return anyOfTheseKeysPressed(Input.Keys.LEFT, Input.Keys.A);
    }

    public static boolean moveRightPressed() {
        return anyOfTheseKeysPressed(Input.Keys.RIGHT, Input.Keys.D);
    }

    /**
     * @param vec The vector to convert into a unit vector.
     * @return A unit vector (magnitude = 1) with the same angle as vec.
     */
    public static Vector2 makeUnitVector(Vector2 vec) {
        if (vec.isZero()) return vec;
        // Use Pythagoras' to find hypotenuse (x^2 + y^2 = hyp^2)
        float hyp = (float) Math.sqrt(Math.pow(vec.x, 2) + Math.pow(vec.y, 2));
        // Use hyp to calculate new unit vector (x/hyp, y/hyp)
        return new Vector2(vec.x / hyp, vec.y / hyp);
    }
}
