package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
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

        table.setFillParent(true);

        table.setDebug(true);

        stage.addActor(table);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(null, null, null, game.font);

        float centre_x = stage.getViewport().getWorldWidth() / 2;
        float centre_y = stage.getViewport().getWorldHeight() / 2;
        float height = stage.getViewport().getWorldHeight();

        TextButton playButton = new TextButton("Play", style);
        playButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new GameScreen(game));
                dispose();
                return true;
            }
        });
        table.add(playButton).pad(10).row();

        TextButton settingsButton = new TextButton("Settings", style);
        settingsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new SettingsScreen(game));
                dispose();
                return true;
            }
        });
        table.add(settingsButton).row();

        TextButton creditsButton = new TextButton("Credits", style);
        creditsButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new CreditsScreen(game));
                dispose();
                return true;
            }
        });
        table.add(creditsButton).pad(10).row();

        TextButton quitButton = new TextButton("Quit", style);
        quitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });
        table.add(quitButton).pad(10).row();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to YettiGame", 0, game.uiViewport.getWorldHeight()-50, game.uiViewport.getWorldWidth(), Align.center, false);
        game.batch.end();

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.uiViewport.update(width, height, true);
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
