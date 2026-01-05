package io.github.yetti_eng.events;

// WHOLE FILE NEW CODE

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event doubles your score if you completed the assignment first.
 * If you don't have the assignment done, nothing happens.
 */
public class DoubleScoreEvent extends Event {
    /**
     * Checks if the player has the assignment, then doubles their score.
     * Also unlocks an achievement for getting the double score bonus.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The score doubler item
     * @return true if the player had the assignment, false otherwise
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if(player.hasUsedItem("assignment")) {
            EventCounter.incrementPositive();
            item.disable();
            screen.getGame().achievements.unlock("Turnitin Approved, Soul Freed");
            screen.spawnLargeMessage("Achievement Unlocked!");

            return true;
        }
        return false;
    }

    /**
     * Multiplies the score by 2.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {2,2};
    }
}
