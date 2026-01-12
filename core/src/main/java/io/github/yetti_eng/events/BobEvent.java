package io.github.yetti_eng.events;

import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class BobEvent extends Event {

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        screen.spawnInteractionMessage("Bob got you! -100 points");
        // IMPORTANT: don't disable/hide if Bob should remain a hazard
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0,-100};
    }
}
