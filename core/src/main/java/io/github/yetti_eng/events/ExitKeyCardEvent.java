package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * Event triggered when the player collects the exit key card.
 * This event can only be activated once. When triggered, it marks the
 * exit key card as collected, disables the item, and displays an
 * interaction message to the player.
 */
public class ExitKeyCardEvent extends Event {
    /**
     * Activates the exit key card event.
     * If the player already has the exit key card, the event will not
     * activate again.
     *
     * @param screen the current game screen
     * @param player the player interacting with the item
     * @param item   the key card item being collected
     * @return true if the event was activated, false otherwise
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if (WinEvent.hasExitKeycard) return false;

        WinEvent.hasExitKeycard = true;


        item.disable();
        item.hide();


        screen.spawnInteractionMessage("got the key card to leave");
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0, 0};
    }
}

