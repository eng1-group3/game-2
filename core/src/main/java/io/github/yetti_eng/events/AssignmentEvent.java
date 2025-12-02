package io.github.yetti_eng.events;

import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class AssignmentEvent extends Event{
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        item.disable();
        item.hide();
        screen.getPaperSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Completed Assignment!");
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
