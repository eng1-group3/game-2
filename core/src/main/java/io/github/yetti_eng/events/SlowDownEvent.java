package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import com.badlogic.gdx.utils.Timer;

public class SlowDownEvent extends Event {

    private static final float SLOWED_MULTIPLIER = 0.4f; // Player is 40% slower (I think)
    private static final float DURATION = 10f;           // Effect lasts 10 seconds

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {

        float originalSpeed = player.getSpeed();
        float slowedSpeed = originalSpeed * SLOWED_MULTIPLIER; // correct formula

        // Apply slow effect
        player.setSpeed(slowedSpeed);

        // Consume item
        item.disable();
        item.hide();

        // negative event recorded
        EventCounter.incrementNegative();

        // Reset speed after duration
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                player.setSpeed(originalSpeed);
            }
        }, DURATION);

        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[]  {0,-200};
    }
}
