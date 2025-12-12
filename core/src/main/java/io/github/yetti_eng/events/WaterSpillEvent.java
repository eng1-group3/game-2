package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class WaterSpillEvent
    extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if (!item.isUsed()){
            EventCounter.incrementHidden();
            item.show();
            screen.getSlipSfx().play(screen.getGame().volume);
            screen.spawnInteractionMessage("Tripped over in a water spill (" + getScoreModifier()[1] + ")");
            return true;
        }
        return false;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {1,50};
    }
}
