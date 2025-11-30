package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.Timer;

import static io.github.yetti_eng.YettiGame.scaled;

public class WinScreen implements Screen {
    private final YettiGame game;
    private int score;


    public WinScreen(final YettiGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        score = game.calculateFinalScore();
    }

    @Override
    public void render(float delta) {

        //Reset game variables and return to main menu on pressing R key
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.score = 0;      //  reset score
            EventCounter.reset();  // reset event counter
            game.setScreen(new MenuScreen(game)); //return to main menu
            dispose();
            return;
        }

        ScreenUtils.clear(0f, 0.4f, 0f, 1f); // green color
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.font.draw(game.batch, "You WIN", 0, scaled(6.5f), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Score: " + score, 0, scaled(4.5f), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Time remaining: " + game.timer.formatTimer(game.timer.getRemainingTime()), 0, scaled(3.5f), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Press R to return to menu", 0, scaled(2f), scaled(16), Align.center, false);
        game.batch.end();
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
    public void dispose() {}
}
