package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles picking up the check-in code.
 * The player needs this code to unlock certain doors in the game.
 */
public class KeyEvent extends Event {
    /**
     * Runs when the player picks up the check-in code.
     * Hides the item, plays a paper sound, and shows a message.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The check-in code item
     * @return true since the item gets used up
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {


        // negative event record +1
        EventCounter.incrementNegative();
        // Remove the item from the map so it can't be picked up again
        item.disable();
        item.hide();

        screen.getPaperSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Got check-in code");
        return true;
    }
    /**
     * Gives 100 points to the player score.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0,100};
    }
}
