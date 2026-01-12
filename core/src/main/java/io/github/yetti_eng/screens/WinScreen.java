package io.github.yetti_eng.screens;

/**
 * All JavaDoc is new code.
 *
 * The original version did not use a table for the layout of the screen, so created and positioned
 * each label using pixel positioning. The new code creates the same labels (with an addition) and adds
 * them to a table for positioning, then the whole table is drawn using libGDX 'Stage'.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.LeaderboardEntry;
import io.github.yetti_eng.YettiGame;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.List;

/**
 * The {@code WinScreen} class represents the screen displayed when the player successfully
 * escapes the maze.
 * <p>
 * It shows a congratulatory message and score compared to the leaderboard.
 * It also includes a "Main Menu" button that takes the player back to the main menu.
 * </p>
 *
 * <p>This class implements LibGDX {@link Screen} interface, shows
 * methods for managing a screen in a game.</p>
 */
public class WinScreen implements Screen {
    private final YettiGame game;
    // The player's final score.
    private int score;
    // The leaderboard of top scores.
    private Leaderboard leaderboard;
    // A list of the top scores.
    private List topScores;
    // A string which will contain player's username.
    private String username = "";
    private boolean typing = true;
    private boolean uniqueUsername = true;

    // The stage used to render the UI.
    private final Stage stage;
    // The table used for the layout of UI.
    private final Table table;

    /**
     * Constructs a new {@code WinScreen} instance.
     *
     * @param game the main {@link YettiGame} game instance.
     */
    public WinScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
        score = game.calculateFinalScore();
        // ---- NEW CODE ------
        this.leaderboard = new Leaderboard();
    }

    public WinScreen(final YettiGame game, boolean setUi) {
        this.game = game;
        if (setUi) {
            stage = new Stage(game.uiViewport, game.batch);
            table = new Table();
        } else {
            stage = null;
            table = null;
        }

        score = game.calculateFinalScore();
        this.leaderboard = new Leaderboard();
    }

    // --------- NEW CODE -----------
    public String getUsername() {
        return username;
    }
    // -----------------------------

    /**
     * This method is called when the screen is shown.
     * It initialises elements of the UI and adds them to the table in correct layout.
     */
    @Override
    public void show() {
        table.clear();
        table.setFillParent(true);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        TextButton.TextButtonStyle buttonStyle =
            new TextButton.TextButtonStyle(null, null, null, game.font);

        Label titleLabel = new Label("You won :D", labelStyle);
        Label scoreLabel = new Label("Score: " + score, labelStyle);
        // ---------- NEW CODE ------------
        Label topScoresLabel = new Label("Top Scores:", labelStyle);

        table.add(titleLabel).padTop(10).row();
        table.add(scoreLabel).padTop(5).row();
        table.add(topScoresLabel).padTop(20).row();

        topScores = leaderboard.getTopScores();
        for (Object obj : topScores) {
            LeaderboardEntry entry = (LeaderboardEntry) obj;
            Label lb = new Label(entry.getPosition() + ")  " + entry, labelStyle);
            table.add(lb).padTop(4).left().row();
        }

        TextButton menuBtn = new TextButton("Main Menu", buttonStyle);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.score = 0;
                EventCounter.reset();
                game.setScreen(new MenuScreen(game));
            }
        });

        table.add(menuBtn).padTop(20).center().row();
        // ---------------------------------
    }

    /**
     * Renders the winning screen depending on if the player has entered their username or not.
     *  If not, a different UI is shown than the table setup in 'show' method.
     * @param delta time since last render.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.4f, 0.2f, 1f);
        if(typing){
            game.batch.begin();

            // ----------- NEW CODE ----------
            game.font.draw(game.batch, "Enter your username:", 100, 500);
            game.font.draw(game.batch, username + (typing ? "|" : ""), 100, 450);

            if(!uniqueUsername){
                game.font.draw(game.batch, "Username is taken.", 100, 350);
            }

            game.batch.end();
            handleTyping();
            return;
        }

        //Reset game variables and return to main menu on pressing R key
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            resetGame();
            game.setScreen(new MenuScreen(game)); //return to main menu
            return;
        }
        game.batch.begin();
        game.batch.end();
        stage.draw();
        // ------------------------------
    }

    // -------------------- NEW CODE -------------------

    /*
     * Resets all game variables to their initial state.
     */
    void resetGame() {
        game.score = 0;      //  reset score
        EventCounter.reset();  // reset event counter
    }

    /**
     * ??
     */
    void handleTyping() {
        for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                username += Input.Keys.toString(key);
            }
        }

        for (int key = Input.Keys.NUM_0; key <= Input.Keys.NUM_9; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                username += Input.Keys.toString(key);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !username.isEmpty()) {
            username = username.substring(0, username.length() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(leaderboard.addToLeaderboard(username, score)){
                topScores = leaderboard.getTopScores();
                typing = false;
                this.show();
            } else{
                username = "";
                uniqueUsername = false;
            }
        }
    }
    // -------------------------------------------

    /**
     * Called when the screen is resized.
     *
     * @param width  the new width of the screen in pixels.
     * @param height the new height of the screen in pixels.
     */
    @Override
    public void resize(int width, int height) {
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
