package io.github.yetti_eng.events;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import io.github.yetti_eng.screens.WinScreen;

public class WinEvent extends Event {
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        YettiGame game = screen.getGame();
        game.setScreen(new WinScreen(game));
        screen.dispose();
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
