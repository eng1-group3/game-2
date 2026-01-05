package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles a hidden water spill on the floor.
 * When the player steps on it, they trip and lose points.
 */
public class WaterSpillEvent
    extends Event {
    /**
     * Runs when the player steps on the water spill for the first time.
     * Makes the spill visible, plays a slip sound, and takes away points.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The water spill item
     * @return true if this is the first time stepping on it, false if already used
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if (!item.isUsed()){
            EventCounter.incrementHidden();
            item.show();
            screen.getSlipSfx().play(screen.getGame().volume);
            screen.spawnInteractionMessage("Tripped over in a water spill (" + getScoreModifier()[1] + ")");
            return true;
        }
        return false;
    }

    /**
     * Takes away 50 points as a penalty for tripping.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {1,50};
    }
}
