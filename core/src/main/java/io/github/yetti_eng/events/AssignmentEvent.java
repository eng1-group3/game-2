package io.github.yetti_eng.events;

import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles completing an assignment.
 * You need to finish this assignment to unlock other stuff later in the game.
 */
public class AssignmentEvent extends Event{
    /**
     * Runs when the player picks up the assignment.
     * Hides the item and plays a paper sound to show it was completed.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The assignment item
     * @return true since the assignment gets completed
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        item.disable();
        item.hide();
        screen.getPaperSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Completed Assignment!");
        return true;
    }

    /**
     * No points for completing the assignment, but you need it for other events.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
