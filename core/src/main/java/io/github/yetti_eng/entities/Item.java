package io.github.yetti_eng.entities;

import com.badlogic.gdx.graphics.Texture;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.events.Event;
import io.github.yetti_eng.screens.GameScreen;

public class Item extends Entity {
    private final Event event;
    private boolean used;

    public final String ID;

    public Item(Event event, String ID, Texture tex, float x, float y, float width, float height, boolean hidden, boolean solid) {
        super(tex, x, y, width, height, 0.0f, solid);
        this.event = event;
        this.ID = ID;
        if (hidden) hide();
    }

    public Item(Event event, String ID, Texture tex, float x, float y, float width, float height) {
        this(event, ID, tex, x, y, width, height, false, false);
    }

    public Item(Event event, String ID, Texture tex, float x, float y) {
        this(event, ID, tex, x, y, 1, 1);
    }

    public final void interact(final YettiGame game, final GameScreen screen, Player player) {
        boolean justUsed = event.activate(screen, player, this);
        if (justUsed) {
            used = true;
            player.inventory.add(this);
            event.modifyScore(game);
        }
    }

    public boolean isUsed() {
        return used;
    }

    public Event getEvent() {
        return event;
    }

    @Override
    public void setSolid(boolean solid) {
        super.setSolid(solid);
    }


}
