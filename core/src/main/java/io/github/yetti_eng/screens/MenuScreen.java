package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

public class MenuScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;

    public MenuScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null, null, null, game.font);

        TextButton playButton = new TextButton("Play", style);
        playButton.setPosition(scaled(16) / 2, scaled(6f), Align.center);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(playButton);

        TextButton settingsButton = new TextButton("Settings", style);
        settingsButton.setPosition(scaled(16) / 2, scaled(4.5f), Align.center);
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(settingsButton);

        TextButton creditsButton = new TextButton("Credits", style);
        creditsButton.setPosition(scaled(16) / 2, scaled(3f), Align.center);
        creditsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CreditsScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(creditsButton);

        TextButton quitButton = new TextButton("Quit", style);
        quitButton.setPosition(scaled(16) / 2, scaled(1.5f), Align.center);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        stage.addActor(quitButton);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to YettiGame", 0, scaled(8), scaled(16), Align.center, false);
        game.batch.end();

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
    }
}
