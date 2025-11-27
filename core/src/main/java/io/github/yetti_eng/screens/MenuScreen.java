package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

public class MenuScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;
    private final Table table;

    public MenuScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // make table fill whole window
        table.setFillParent(true);
        //table.setDebug(true);

        stage.addActor(table);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null, null, null, game.font);
        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);

        Label titleLabel = new Label("Welcome to YettiGame", labelStyle);

        TextButton playButton = new TextButton("Play", style);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });

        TextButton settingsButton = new TextButton("Settings", style);
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
                dispose();
                return true;
            }
        });

        TextButton creditsButton = new TextButton("Credits", style);
        creditsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CreditsScreen(game));
                dispose();
                return true;
            }
        });

        TextButton quitButton = new TextButton("Quit", style);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        table.add(titleLabel).padBottom(20).row();
        table.add(playButton).pad(10).row();
        table.add(settingsButton).pad(10).row();
        table.add(creditsButton).pad(10).row();
        table.add(quitButton).pad(10).row();

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
