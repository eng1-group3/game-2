package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;



public class LongBoiEvent extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        final int TOTAL_LONG_BOIS = 9;

        EventCounter.incrementPositive();
        EventCounter.incrementLongBoi();

        item.disable();
        item.hide();
        screen.getQuackSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Found Long Boi (+" + getScoreModifier()[1] + ")");
        if (EventCounter.getLongBoiCollected() == TOTAL_LONG_BOIS) {

            // Unlock the achievement
            screen.getGame().achievements.unlock("longwall_master");

            // Player feedback
            screen.spawnLargeMessage("🏆 Achievement Unlocked: Long-Boi Master!");
        }
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0, 100};
    }
}
