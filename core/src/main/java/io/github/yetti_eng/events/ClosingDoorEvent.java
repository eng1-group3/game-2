package io.github.yetti_eng.events;

import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

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

    public ClosingDoorEvent(float doorY, float doorHeight) {
    }

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        if (state == DoorState.OPEN && !playerPassedThrough) {
            playerPassedThrough = true;
            state = DoorState.WAITING;
            timeSincePlayerPassed = 0f;
        }
        return false;
    }

    public void checkForAutoClose(GameScreen screen, Player player, Item item, float delta) {
        if (state == DoorState.WAITING) {
            timeSincePlayerPassed += delta;
            if (timeSincePlayerPassed >= CLOSE_DELAY) {
                closeDoor(screen, item);
            }
        }
    }

    private void closeDoor(GameScreen screen, Item item) {
        state = DoorState.CLOSED;
        EventCounter.incrementHidden();
        item.setSolid(true);
        item.setTexture(screen.getDoorTexture());
        item.enable();
        screen.getDoorSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("The door closes behind you");
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
