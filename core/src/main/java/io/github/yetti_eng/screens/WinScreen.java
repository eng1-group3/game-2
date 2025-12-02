package io.github.yetti_eng.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.LeaderboardEntry;
import io.github.yetti_eng.YettiGame;

import java.util.List;

import static io.github.yetti_eng.YettiGame.scaled;

public class WinScreen implements Screen {
    private final YettiGame game;
    private int score;
    private Leaderboard leaderboard;
    private List topScores;

    public WinScreen(final YettiGame game) {
        this.game = game;
        score = game.calculateFinalScore();
        this.leaderboard = new Leaderboard();
        leaderboard.addToLeaderboard(score);
        System.out.println(score);
        this.topScores = leaderboard.getTopScores();
    }

    @Override
    public void show() {
        game.font.getData().setScale(0.7f);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.4f, 0.2f, 1f);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "You won! :D", 0, scaled(9), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Your score: " + score, 0, scaled(8.2f), scaled(16), Align.center, false);
        game.font.draw(game.batch, "Top scores:", 0, scaled(7.1f), scaled(16), Align.center, false);
        int i = 1;
        for (Object obj : topScores) {
            LeaderboardEntry entry = (LeaderboardEntry) obj;
            game.font.draw(game.batch, entry.getPosition() + ")  " + entry.toString(), scaled(4.5f), scaled(7.1f) - scaled(i*0.9f), scaled(16), Align.left, false);
            i++;
        }
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
