package io.github.yetti_eng.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;

/**
 * This class represents the player character that you control in the game.
 * It keeps track of the player's inventory and checks if items have been collected.
 */
public class Player extends Entity {
    private static final float PLAYER_SPEED = 6f;

    final ArrayList<Item> inventory = new ArrayList<>();

    /**
     * Creates a new player at the given position.
     * Sets up the player's size, speed, and collision box.
     *
     * @param tex The texture to use for the player
     * @param x Starting x position
     * @param y Starting y position
     */
    public Player(Texture tex, float x, float y) {
        super(tex, x, y, 0.9f, 1.6f, PLAYER_SPEED, false);
        setHitbox(new Rectangle(x, y, 0.7f, 1.2f));
    }

    /**
     * Checks if the player has picked up a specific item.
     *
     * @param itemID The unique ID of the item to check for
     * @return true if the player has it in their inventory, false otherwise
     */
    public boolean hasUsedItem(String itemID) {
        return inventory.stream().anyMatch(i -> i.ID.equals(itemID));
    }
}
