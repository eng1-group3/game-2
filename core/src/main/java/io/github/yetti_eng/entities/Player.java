package io.github.yetti_eng.entities;

import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

public class Player extends Entity {
    private static final float PLAYER_SPEED = 6f;

    final ArrayList<Item> inventory = new ArrayList<>();

    public Player(Texture tex, float x, float y) {
        super(tex, x, y, 0.9f, 1.6f, PLAYER_SPEED, false);
    }

    public boolean hasUsedItem(String itemID) {
        return inventory.stream().anyMatch(i -> i.ID.equals(itemID));
    }
}
