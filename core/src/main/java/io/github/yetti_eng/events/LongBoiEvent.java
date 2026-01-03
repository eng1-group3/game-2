package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;


/**
 * This class handles what happens when the player finds a Long Boi item.
 */
public class LongBoiEvent extends Event {
    /**
     * This runs when the player picks up the item.
     * It hides the item, plays a quack sound, and updates the score.
     * If the player has found all 9 ducks, it unlocks the achievement.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item   The item being picked up
     * @return true
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        final int TOTAL_LONG_BOIS = 9;

        EventCounter.incrementPositive();
        EventCounter.incrementLongBoi();
        // Remove the item from the map so it can't be picked up again
        item.disable();
        item.hide();

        // Play the sound and show a text message
        screen.getQuackSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Found Long Boi (+" + getScoreModifier()[1] + ")");
        if (EventCounter.getLongBoiCollected() == TOTAL_LONG_BOIS) {

            // Unlock the achievement
            screen.getGame().achievements.unlock("longboi_master");

            // Player feedback
            screen.spawnLargeMessage("Achievement Unlocked: Duck Dealer!");
        }
        return true;
    }
    /**
     * Gives 100 points to the player score.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0, 100};
    }
}
