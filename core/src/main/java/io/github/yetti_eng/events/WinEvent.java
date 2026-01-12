package io.github.yetti_eng.events;

import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;
import io.github.yetti_eng.screens.WinScreen;

/**
 * Event triggered when the player interacts with the exit.
 * <p>
 * The player can only win if the exit key card has been collected.
 * If the key card is missing, a locked message is shown once.
 */
public class WinEvent extends Event {

    /** Tracks whether the player has collected the exit key card. */
    public static boolean hasExitKeycard = false;

    /** Prevents the locked door message from appearing multiple times. */
    private static boolean shownLockedMsg = false;

    /**
     * Attempts to trigger the win condition.
     * If the player has the key card, the game timer is paused and
     * the win screen is displayed.
     *
     * @param screen the current game screen
     * @param player the player interacting with the exit
     * @param item   the exit item
     * @return true if the win condition was triggered, false otherwise
     */
    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {

        if (!hasExitKeycard) {
            if (!shownLockedMsg) {
                screen.spawnInteractionMessage("Door locked. You need the key card.");
                shownLockedMsg = true;
            }
            return false;
        }

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
        return new int[] {0, 0};
    }
}
