package io.github.yetti_eng.events;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * Base class for all events that happen when the player interacts with items.
 * Each event can change the player's score and run custom logic when activated.
 */
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

    /**
     * Changes the player's score based on the event type.
     * It can add, subtract, multiply, or divide the score depending on what the event does.
     *
     * @param game The current game instance
     */
    // Consolidated "scoreIncrement" and "scoreDecrement" into single "modifyScore" method
    public void modifyScore(final YettiGame game) {
        int combined = game.score + game.timer.getRemainingTime();

        switch(getScoreModifier()[0]) {
            case 0:
                combined += getScoreModifier()[1];
                break;
            case 1:
                combined -= getScoreModifier()[1];
                break;
            case 2:
                combined *= getScoreModifier()[1];
                break;
            case 3:
                combined /= getScoreModifier()[1];
                break;
        }
        game.score = combined - game.timer.getRemainingTime();
    }

    /**
     * @return How many times this event has been triggered
     */
    public int getCounter() {
        return counter;
    }

    /**
     * Adds 1 to the counter when this event gets triggered.
     */
    public void incrementCounter() {
        this.counter += 1;
    }
}
