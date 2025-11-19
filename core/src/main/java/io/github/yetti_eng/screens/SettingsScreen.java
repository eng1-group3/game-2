package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

public class SettingsScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;

    private Slider volumeSlider;

    private Texture sliderTexture;
    private Texture knobTexture;

    public SettingsScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.viewport, game.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        sliderTexture = new Texture("ui/slider_background.png");
        knobTexture = new Texture("ui/slider_knob.png");

        volumeSlider = new Slider(0.0f, 1.0f, 0.01f, false,
            new Slider.SliderStyle(new TextureRegionDrawable(sliderTexture), new TextureRegionDrawable(knobTexture))
        );
        volumeSlider.setPosition(scaled(4), scaled(3.5f));
        volumeSlider.setWidth(scaled(8));
        volumeSlider.setValue(game.volume);
        volumeSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.volume = volumeSlider.getValue();
            }
        });
        stage.addActor(volumeSlider);

        TextButton menuButton = new TextButton("Return to Menu", new TextButton.TextButtonStyle(null, null, null, game.font));
        menuButton.setPosition(scaled(16) / 2, scaled(2), Align.center);
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(menuButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.4f, 0.4f, 0.4f, 1f);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "Settings", 0, scaled(8), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Volume", 0, scaled(6), scaled(16), Align.center, false);
        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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
