package io.github.yetti_eng.events;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import com.badlogic.gdx.utils.Timer;

public class SpeedUp extends Event {

    private static final float BOOST_MULTIPLIER = 3f; // +10%
    private static final float DURATION = 10f; // lasts 10 seconds

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {

        float originalSpeed = player.getSpeed();
        float boostedSpeed = originalSpeed * BOOST_MULTIPLIER;

        // Apply boost
        player.setSpeed(boostedSpeed);

        // Consume item
        item.disable();
        item.hide();

        // Schedule automatic reset
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                player.setSpeed(originalSpeed);
            }
        }, DURATION);

        return true;
    }

    @Override
    public int getScoreModifier() {
        return 0;
    }
}
