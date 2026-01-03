package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class KeyEvent extends Event {
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
