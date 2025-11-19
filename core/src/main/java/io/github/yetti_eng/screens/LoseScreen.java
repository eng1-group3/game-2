package io.github.yetti_eng.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import static io.github.yetti_eng.YettiGame.scaled;

public class LoseScreen implements Screen {
    private final YettiGame game;
    private int score;

    public LoseScreen(final YettiGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        score = game.calculateFinalScore();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.4f, 0.15f, 0.2f, 1f);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "You lost :(", 0, scaled(5.5f), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Score: " + score, 0, scaled(4.5f), scaled(16), Align.center, false);
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
