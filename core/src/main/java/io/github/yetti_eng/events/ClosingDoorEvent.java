package io.github.yetti_eng.events;

// WHOLE FILE NEW CODE

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

/**
 * This event handles a door that closes behind you after a delay.
 * When you walk through it, it starts a timer and then locks itself so you can't go back.
 */
public class ClosingDoorEvent extends Event {
    private enum DoorState {
        OPEN,
        WAITING,
        CLOSED
    }

    private DoorState state = DoorState.OPEN;
    private static final float CLOSE_DELAY = 2.0f;
    private float timeSincePlayerPassed = 0f;
    private boolean playerPassedThrough = false;

    /**
     * Creates a new closing door event.
     *
     * @param doorY The y position of the door
     * @param doorHeight The height of the door
     */
    public ClosingDoorEvent(float doorY, float doorHeight) {
    }

    /**
     * Runs when the player walks through the door.
     * Starts the countdown timer that will close the door after 2 seconds.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The door item
     * @return false since the door don't get used up immediately
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if (state == DoorState.OPEN && !playerPassedThrough) {
            playerPassedThrough = true;
            state = DoorState.WAITING;
            timeSincePlayerPassed = 0f;
        }
        return false;
    }

    /**
     * Checks if enough time has passed to close the door.
     * This gets called every frame to update the timer.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The door item
     * @param delta Time since last frame
     */
    public void checkForAutoClose(GameScreen screen, Player player, Item item, float delta) {
        if (state == DoorState.WAITING) {
            timeSincePlayerPassed += delta;
            if (timeSincePlayerPassed >= CLOSE_DELAY) {
                closeDoor(screen, item);
            }
        }
    }

    /**
     * Closes the door and makes it solid again.
     * Plays a sound and shows a message to let the player know.
     *
     * @param screen The current game screen
     * @param item The door item
     */
    private void closeDoor(GameScreen screen, Item item) {
        state = DoorState.CLOSED;
        EventCounter.incrementHidden();
        item.setSolid(true);
        item.setTexture(screen.getDoorTexture());
        item.enable();
        screen.getDoorSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("The door closes behind you");
    }

    /**
     * No score change for the door closing.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
