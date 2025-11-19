package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class DoorEvent extends Event {
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

    @Override
    public int getScoreModifier() {
        return 0;
    }
}
