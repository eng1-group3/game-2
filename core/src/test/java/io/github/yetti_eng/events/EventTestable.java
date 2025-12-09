package io.github.yetti_eng.events;

import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

// A basic implementation of the abstract class 'Event' in order to test its methods
public class EventTestable extends Event {

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        int[] toReturn = {1, 2};
        return toReturn;
    }
}