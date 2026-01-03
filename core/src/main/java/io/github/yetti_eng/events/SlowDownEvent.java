package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import com.badlogic.gdx.utils.Timer;
/**
 * This event handles items that make the player move slower for a short time.
 */
public class SlowDownEvent extends Event {

    private static final float SLOWED_MULTIPLIER = 0.4f; // Player is 40% slower (I think)
    private static final float DURATION = 10f;           // Effect lasts 10 seconds
    /**
     * Activates the slow down effect.
     * It saves the player's original speed, slows them down, and starts a timer
     * to fix their speed after 10 seconds.
     *
     * @param screen The game screen
     * @param player The player being slowed down
     * @param item   The item that caused the slow down
     * @return true
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        float originalSpeed = player.getSpeed();
        float slowedSpeed = originalSpeed * SLOWED_MULTIPLIER; // correct formula

        // Apply slow effect
        player.setSpeed(slowedSpeed);

        // Remove the item from the map so it can't be picked up again
        item.disable();
        item.hide();

        // negative event record +1
        EventCounter.incrementNegative();

        // reset the speed after the duration ends
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                player.setSpeed(originalSpeed);
            }
        }, DURATION);

        return true;
    }
    /**
     * Takes away 200 points from the score
     */
    @Override
    public int[] getScoreModifier() {
        return new int[]  {0,-200};
    }
}
