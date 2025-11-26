package io.github.yetti_eng.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.yetti_eng.InputHelper;

/**
 * Entity.java handles setting movement of an object and performs collision and property checks.
 *
 * Represents objects including the player, enemy and items (event triggers).
 * Each entity has a movement, position, collision hitbox and property information on visibility,
 * solidity and speed.
 *
 * Called "Sprite" in the architecture documentation; renamed to avoid clash with LibGDX class name
 */
public abstract class Entity extends Sprite {
    private float speed;
    private boolean solid;
    private Vector2 movement;
    private final Rectangle hitbox;

    private boolean visible = true;
    private boolean enabled = true;

    /**
     * Constructor for a new Entity
     * @param tex texture to render with
     * @param x initial x position
     * @param y initial y position
     * @param width the width of the entity
     * @param height the height of the entity
     * @param speed the speed of the entity
     * @param solid whether object should cause collision with player
     */
    public Entity(Texture tex, float x, float y, float width, float height, float speed, boolean solid) {
        super(tex);

        // Set this Entity's bounds and hitbox
        setBounds(x, y, width, height);
        this.hitbox = new Rectangle(x, y, width, height);
        this.speed = speed;
        this.solid = solid;
        this.movement = new Vector2(0, 0);
    }

    /**
     * reset the player movements
     */
    public void resetMovement() {
        movement.set(0, 0);
    }

    public void addMovement(float x, float y) {
        movement.add(x, y);
    }

    /**
     * Convert this Entity's current queued movement into a unit vector
     * (that is, ensure that this Entity moves exactly the distance of 1 tile).
     */
    public void normaliseMovement() {
        movement = InputHelper.makeUnitVector(movement);
    }

    /**
     * Performs this Entity's movement for the current frame.
     * Uses the value of the movement field (which can be set with resetMovement(), addMovement(), etc.)
     * @param delta The delta time this frame.
     * @param speedWasPrecalculated true if the Entity's speed was already accounted for when
     *                              calculating the value of the movement field.
     */
    // Consolidated "moveLeft()", "moveUp()" etc. into a single "doMove()" method to reduce repetition
    public void doMove(float delta, boolean speedWasPrecalculated) {
        // If speed was precalculated, don't account for it again
        float speed = (speedWasPrecalculated ? 1 : getSpeedThisFrame(delta));
        translateX(movement.x * speed);
        translateY(movement.y * speed);
        hitbox.setPosition(getX(), getY());
    }

    /**
     * Performs this Entity's movement for the current frame.
     * Uses the value of the movement field (which can be set with resetMovement(), addMovement(), etc.)
     * @param delta The delta time this frame.
     */
    public void doMove(float delta) {
        doMove(delta, false);
    }

    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Checks whether this entity collides with another specified entity
     * @param other other entity
     * @return true if the two entities collide
     */
    public boolean collidedWith(Entity other) {
        // If disabled, do not check for collisions
        return enabled && hitbox.overlaps(other.getHitbox());
    }

    public void show() {
        visible = true; //shown on screen
    }

    public void hide() {
        visible = false; //hidden from screen
    }

    public boolean isVisible() {
        return visible;
    }

    public void enable() {
        enabled = true; //collision and logic updates apply
    }

    public void disable() {
        enabled = false; //prevents collision and logic updates
    }

    public boolean isEnabled() {
        return enabled;
    }

    public float getSpeedThisFrame(float delta) {
        return speed * delta;
    }

    void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isSolid() {
        return solid;
    }

    public Vector2 getCurrentPos() {
        return new Vector2(getX(), getY());
    }
}
