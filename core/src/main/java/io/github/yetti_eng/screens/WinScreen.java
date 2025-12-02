package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private String username = "";
    private boolean typing = true;
    private boolean uniqueUsername = true;

    public WinScreen(final YettiGame game) {
        this.game = game;
        score = game.calculateFinalScore();
        this.leaderboard = new Leaderboard();
    }

    @Override
    public void show() {
        game.font.getData().setScale(0.7f);
    }

    @Override
    public void render(float delta) {
        if(typing){
            ScreenUtils.clear(0.15f, 0.4f, 0.2f, 1f);
            game.batch.begin();

            game.font.draw(game.batch, "Enter your username:", 100, 500);
            game.font.draw(game.batch, username + (typing ? "|" : ""), 100, 450);

            if(!uniqueUsername){
                game.font.draw(game.batch, "Username is taken.", 100, 350);
            }

            game.batch.end();
            handleTyping();
            return;
        }

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
            game.font.draw(game.batch, entry.getPosition() + ")  " + entry.toString(), scaled(5.3f), scaled(7.1f) - scaled(i*0.9f), scaled(16), Align.left, false);
            i++;
        }
        game.batch.end();
    }

    private void handleTyping() {
        for (int key = Input.Keys.A; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                username += Input.Keys.toString(key);
            }
        }

        for (int key = Input.Keys.NUM_0; key <= Input.Keys.NUM_9; key++) {
            if (Gdx.input.isKeyJustPressed(key)) {
                username += Input.Keys.toString(key);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE) && !username.isEmpty()) {
            username = username.substring(0, username.length() - 1);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            if(leaderboard.addToLeaderboard(username, score)){
                topScores = leaderboard.getTopScores();
                typing = false;
            } else{
                username = "";
                uniqueUsername = false;
            }
        }
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
