package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class DoubleScoreEvent extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if(player.hasUsedItem("assignment")) {
            EventCounter.incrementPositive();
            item.disable();
            screen.spawnInteractionMessage("Submitted! Score doubled!");
            return true;
        }
        return false;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {2,2};
    }
}
