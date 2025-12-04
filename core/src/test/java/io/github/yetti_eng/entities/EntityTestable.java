package io.github.yetti_eng.entities;

import com.badlogic.gdx.graphics.Texture;

// Entity is an abstract class, so we needed to make it a concrete class in order to test it
public class EntityTestable extends Entity {
    public EntityTestable(Texture tex, float x, float y, float width, float height, float speed, boolean isSolid) {
        super(tex, x, y, width, height, speed, isSolid);
    }
}
