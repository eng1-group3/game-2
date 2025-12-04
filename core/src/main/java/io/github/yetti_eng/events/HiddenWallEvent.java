package io.github.yetti_eng.events;

import com.badlogic.gdx.graphics.Texture;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.screens.GameScreen;

public class HiddenWallEvent extends Event {
    private final Texture passableTexture;

    public HiddenWallEvent(Texture passableTexture) {
        this.passableTexture = passableTexture;
    }

    @Override
    public boolean activate(GameScreen screen, Player player, Item item) {
        EventCounter.incrementHidden();
        item.setTexture(passableTexture);
        item.setSolid(false);
        screen.getGame().achievements.unlock("wall_crasher");
        screen.spawnInteractionMessage("achievement unlocked");
        return true;
    }

    @Override
    public int[] getScoreModifier() {
        return new int[] {0, 200};
    }
}
