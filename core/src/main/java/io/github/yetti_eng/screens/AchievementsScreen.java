package io.github.yetti_eng.screens;

// WHOLE FILE NEW CODE

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.Achievements;
import io.github.yetti_eng.YettiGame;

/**
 * This screen displays the list of achievements to the player.
 * It shows which ones are unlocked (in green) and which are still locked (in grey).
 */
public class AchievementsScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;
    private final Table table;
    private final Achievements achievementsLogic;

    // Styles for the text (Different colors for locked vs unlocked)
    private Label.LabelStyle titleUnlockedStyle;
    private Label.LabelStyle titleLockedStyle;
    private Label.LabelStyle descUnlockedStyle;
    private Label.LabelStyle descLockedStyle;

    /**
     * Sets up the stage and the table for layout.
     *
     * @param game The main game object
     */
    public AchievementsScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
        achievementsLogic = new Achievements();
    }
    /**
     * This runs when the screen is shown.
     * It sets up the input, creates the font styles (green for unlocked, grey for locked),
     * and adds all the achievements to the table.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        table.setFillParent(true);
        table.clear();
        stage.addActor(table);
        //table.setDebug(true);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(null, null, null, game.font);
        Label.LabelStyle headerStyle = new Label.LabelStyle(game.font, Color.WHITE);

        // Green for unlocked titles
        titleUnlockedStyle = new Label.LabelStyle(game.font, Color.GREEN);
        // Gray for locked titles
        titleLockedStyle = new Label.LabelStyle(game.font, Color.GRAY);

        // White for unlocked descriptions
        descUnlockedStyle = new Label.LabelStyle(game.font, Color.WHITE);
        // Dark Gray for locked descriptions (visible but dimmed)
        descLockedStyle = new Label.LabelStyle(game.font, Color.DARK_GRAY);

        Label titleLabel = new Label("Achievements", headerStyle);
        table.add(titleLabel).padBottom(30).row();

        // Add the specific achievements to the list
        addAchievement("Path Sniffer", "Find the secret path", "path_sniffer");
        addAchievement("Longboi Master", "Get all long bois", "longboi_master");
        addAchievement("Ducktorate Degree", "Get a score of 2000", "ducktorate Degree");
        addAchievement("Turnitin Approved, Soul Freed", "Give the lecturer the assignment", "turnitin Approved, Soul Freed");
        // Create the back button to return to the menu
        TextButton backButton = new TextButton("Back", buttonStyle);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });

        table.add(backButton).padTop(30).center().row();
    }
    /**
     * Method to add a single achievement to the screen.
     * It checks if the achievement is unlocked and uses the correct color style.
     *
     * @param title The name of the achievement
     * @param description What the player needs to do
     * @param key The key used to check if it's unlocked in the preferences
     */
    private void addAchievement(String title, String description, String key) {
        boolean isUnlocked = achievementsLogic.isUnlocked(key);

        Label titleLabel;
        Label descLabel;

        if (isUnlocked) {
            // It's unlocked, so use the Green styles
            titleLabel = new Label(title + " [UNLOCKED]", titleUnlockedStyle);
            descLabel = new Label(description, descUnlockedStyle);
        } else {
            titleLabel = new Label(title + " [LOCKED]", titleLockedStyle);
            descLabel = new Label(description, descLockedStyle);
        }

        table.add(titleLabel).left().padTop(10).row();
        table.add(descLabel).left().padBottom(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
    }
}
