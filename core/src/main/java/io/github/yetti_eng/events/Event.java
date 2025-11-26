package io.github.yetti_eng.events;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public abstract class Event {
    private int counter = 0;
    /**
     * Activate this event.
     * Should return true only if the triggering Item is used up during activation.
     *
     * @param item The item triggering this event.
     * @param player The current player object.
     * @return true if the Item was successfully interacted with
     *         (the Event activated successfully); false otherwise.
     */
    public abstract boolean activate(final GameScreen screen, Player player, Item item);

    /**
     * @return Index 0 of the returned array returns a value which corresponds to the operator,
     * Index 1 of the returned array returns the operand
     *
     */
    public abstract int[] getScoreModifier();

    // Consolidated "scoreIncrement" and "scoreDecrement" into single "modifyScore" method
    public void modifyScore(final YettiGame game) {
        switch(getScoreModifier()[0]) {
            case 0:
                game.score += getScoreModifier()[1];
                break;
            case 1:
                game.score -= getScoreModifier()[1];
                break;
            case 2:
                game.score *= getScoreModifier()[1];
                break;
            case 3:
                game.score /= getScoreModifier()[1];
                break;
        }
    }


    public int getCounter() {
        return counter;
    }

    public void incrementCounter() {
        this.counter += 1;
    }
}
