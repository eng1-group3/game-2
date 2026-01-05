package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles a locked door that needs the check-in code to open.
 * If the player don't have the code, the door stays locked.
 */
public class DoorEvent extends Event {
    /**
     * Tries to unlock the door if the player has the check-in code.
     * Changes the door texture to an open doorframe and plays a sound.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The door item
     * @return true if the door was unlocked, false if player doesn't have the code
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        // If the player has got the check-in code, unlock the door
        if (player.hasUsedItem("checkin_code")) {
            EventCounter.incrementNegative(); //increment negative counter
            screen.getDoorSfx().play(screen.getGame().volume);
            screen.spawnInteractionMessage("Unlocked door with check-in code");
            item.disable();
            item.setTexture(screen.getDoorframeTexture());
            return true;
        }
        return false; //no effect on item without key
    }

    /**
     * Gives 10 points for unlocking the door.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0,10};
    }
}
