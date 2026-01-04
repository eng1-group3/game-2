package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

/**
 * The {@code SettingsScreen} class represents the screen displayed when the player pauses the game.
 * <p>
 * It shows a 'Resume' button, 'Restart' button and a "Main Menu" button that takes the player back to the main menu.
 * </p>
 *
 * <p>This class implements LibGDX {@link Screen} interface, shows
 * methods for managing a screen in a game.</p>
 */
public class SettingsScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;
    private final Table table;

    private Slider volumeSlider;

    private Texture sliderTexture;
    private Texture knobTexture;

    /**
     * Sets up the stage and table for layout.
     *
     * @param game The main game object
     */
    public SettingsScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
    }

    /**
     * This runs when the screen is shown.
     * It sets up the input, creates the button and slider for volume,
     * and adds these to the table.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // make table fill whole window
        table.setFillParent(true);
        //table.setDebug(true);

        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        Label titleLabel = new Label("Settings", labelStyle);
        Label volumeLabel = new Label("Volume", labelStyle);

        sliderTexture = new Texture("ui/slider_background.png");
        knobTexture = new Texture("ui/slider_knob.png");

        volumeSlider = new Slider(0.0f, 1.0f, 0.01f, false,
            new Slider.SliderStyle(new TextureRegionDrawable(sliderTexture), new TextureRegionDrawable(knobTexture))
        );
        volumeSlider.setValue(game.volume);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.volume = volumeSlider.getValue();
            }
        });

        TextButton menuButton = new TextButton("Return to Menu", new TextButton.TextButtonStyle(null, null, null, game.font));
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });

        table.add(titleLabel).pad(20).row();
        table.add(volumeLabel).pad(10).row();
        table.add(volumeSlider).pad(10).fillX().row();
        table.add(menuButton).pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.4f, 0.4f, 0.4f, 1f);
        stage.draw();
    }

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
    public void dispose() {
        stage.dispose();
        sliderTexture.dispose();
        knobTexture.dispose();
    }
}
