package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class IncreasePointsEvent extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        EventCounter.incrementPositive(); //positive event recorded
        item.disable();
        item.hide();
        screen.getQuackSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Found Long Boi (+" + getScoreModifier()[1] + ")");
        return true;
    }

    /**
     *Returns the number of points the player gains from this event
     *
     * @return score modifier
     */
    @Override
    public int[] getScoreModifier() {
        // TODO placeholder value
        return new int[] {0, 100};
    }
}
