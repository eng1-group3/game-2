package io.github.yetti_eng.events;

// WHOLE FILE NEW CODE

import com.badlogic.gdx.graphics.Texture;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles a secret wall that you can walk through.
 * When you find it, the wall becomes passable and you get a big score bonus.
 */
public class HiddenWallEvent extends Event {
    private final Texture passableTexture;

    /**
     * Creates a new hidden wall event.
     *
     * @param passableTexture The texture to show when the wall becomes passable
     */
    public HiddenWallEvent(Texture passableTexture) {
        this.passableTexture = passableTexture;
    }

    /**
     * Runs when the player discovers the hidden wall.
     * Changes the wall texture and makes it so you can walk through it.
     * Also unlocks an achievement for finding the secret path.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The hidden wall item
     * @return true since the wall becomes passable
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        EventCounter.incrementHidden();
        item.setTexture(passableTexture);
        item.setSolid(false);
        screen.getGame().achievements.unlock("path_sniffer");
        screen.spawnLargeMessage("Achievement Unlocked!");
        return true;
    }

    /**
     * Gives 200 points for finding the secret wall.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0, 200};
    }
}
