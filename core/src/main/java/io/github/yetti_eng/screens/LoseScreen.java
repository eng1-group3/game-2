package io.github.yetti_eng.screens;

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

public class LoseScreen implements Screen {
    private final YettiGame game;
    private int score;

    private final Stage stage;
    private final Table table;

    public LoseScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
    }

    @Override
    public void show() {
        score = game.calculateFinalScore();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);

        Label titleLabel = new Label("You Lost! :(", labelStyle);
        Label scoreLabel = new Label("Score: " + score, labelStyle);

        // add time remaining
        // add 'press R'
        table.add(titleLabel).pad(10).row();
        table.add(scoreLabel).pad(10);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.8f, 0f, 0.15f, 0.4f);

        //Reset game variables and return to main menu on pressing R key
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.score = 0;      //  reset score
            EventCounter.reset();  // reset event counter
            game.setScreen(new MenuScreen(game)); //return to main menu
            dispose();
            return;
        }

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
    public void dispose() {}
}
