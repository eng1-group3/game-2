package io.github.yetti_eng.events;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import io.github.yetti_eng.screens.WinScreen;

/**
 * This event handles the exit that makes the player win the game.
 * When the player reaches this, the timer stops and they go to the victory screen.
 */
public class WinEvent extends Event {
    /**
     * Triggers the win condition.
     * Pauses the timer and switches to the win screen to show your score.
     *
     * @param screen The current game screen
     * @param player The player
     * @param item The exit item
     * @return true since the player won
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        YettiGame game = screen.getGame();
        game.timer.pause();
        game.setScreen(new WinScreen(game));
        screen.dispose();
        return true;
    }

    /**
     * No score change, winning the game is the reward.
     */
    @Override
    public int[] getScoreModifier() {
        return new int[] {0,0};
    }
}
