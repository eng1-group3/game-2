package io.github.yetti_eng.events;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import com.badlogic.gdx.utils.Timer;

/**
 * This event handles the speed boost item.
 * When collected, it makes the player run much faster for a short time.
 */
public class SpeedUpEvent extends Event {

    private static final float BOOST_MULTIPLIER = 3f; // +300% speed go VOOOOOOOOMMMMMM!!!!!!
    private static final float DURATION = 10f; // lasts 10 seconds

    /**
     * Activates the speed boost.
     * It saves the current speed, triples it, plays a sound effect,
     * and starts a timer to reset the speed back to normal later.
     *
     * @param screen The game screen
     * @param player The player getting the boost
     * @param item   The item being collected
     * @return true
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {

        float originalSpeed = player.getSpeed();
        float boostedSpeed = originalSpeed * BOOST_MULTIPLIER;

        // Apply boost
        player.setSpeed(boostedSpeed);

        // Remove the item from the map so it can't be picked up again
        item.disable();
        item.hide();
        EventCounter.incrementPositive(); //positive event recorded
        //play audio
        screen.getspeedSfx().play(screen.getGame().volume);

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
     * Gives 200 points to the player score.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[]  {0,200};
    }
}
