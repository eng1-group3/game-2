package io.github.yetti_eng.screens;

/**
 * All JavaDoc is new code.
 *
 * The original version did not use a table for the layout of the screen, so created and positioned
 * each label using pixel positioning and then rendered each individually. The new code creates the
 * same labels (with a couple additions) and adds them to a table for positioning, then
 * the whole table is drawn using libGDX 'Stage'.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

/**
 * The {@code LoseScreen} class represents the screen displayed when the player does not escape the maze.
 * <p>
 * It shows a message, the player's score, and an instruction for restarting the game.
 * </p>
 *
 * <p>This class implements LibGDX {@link Screen} interface, shows
 * methods for managing a screen in a game.</p>
 */
public class LoseScreen implements Screen {
    private final YettiGame game;
    private int score;

    private final Stage stage;
    private final Table table;

    /**
     * Sets up the stage and table for layout.
     *
     * @param game The main game object
     */
    public LoseScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
    }

    /**
     * This runs when the screen is shown.
     * It sets up the labels, including a message to the player, their score and an instruction to restart,
     * and adds these to the table.
     */
    @Override
    public void show() {
        score = game.calculateFinalScore();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);

        Label titleLabel = new Label("You Lost! :(", labelStyle);
        Label scoreLabel = new Label("Score: " + score, labelStyle);
        // ------ NEW CODE ---------
        Label restartmessageLabel = new Label("press R to restart", labelStyle);
        // ------------------------

        table.add(titleLabel).pad(10).row();
        table.add(scoreLabel).pad(10).row();
        table.add(restartmessageLabel).pad(10);
    }

    /**
     * Renders the losing screen and checks if button 'R' has been clicked to restart the game
     * each frame.
     *
     * @param delta ??
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.8f, 0f, 0.15f, 0.4f);

        // --------- NEW CODE ---------
        //Reset game variables and return to main menu on pressing R key
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.score = 0;      //  reset score
            EventCounter.reset();  // reset event counter
            game.setScreen(new MenuScreen(game)); //return to main menu
            dispose();
            return;
        }

        stage.draw();
        // ---------------------------
    }

    @Override
    public void resize(int width, int height) {
        // (Changed from 'viewport' to 'uiViewport')
        game.uiViewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
