package io.github.yetti_eng.events;

import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class KeyEvent extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        item.disable();
        item.hide();
        screen.getPaperSfx().play(screen.getGame().volume);
        screen.spawnInteractionMessage("Got check-in code");
        return true;
    }

    @Override
    public int getScoreModifier() {
        return 0;
    }
}
