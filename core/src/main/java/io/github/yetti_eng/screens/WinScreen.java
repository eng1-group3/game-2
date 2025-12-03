package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.EventCounter;
import io.github.yetti_eng.Leaderboard;
import io.github.yetti_eng.LeaderboardEntry;
import io.github.yetti_eng.YettiGame;

import java.util.List;

public class WinScreen implements Screen {
    private final YettiGame game;
    private int score;
    private Leaderboard leaderboard;
    private List topScores;
    private String username = "";
    private boolean typing = true;
    private boolean uniqueUsername = true;

    private final Stage stage;
    private final Table table;

    public WinScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
        score = game.calculateFinalScore();
        this.leaderboard = new Leaderboard();
    }

    @Override
    public void show() {
        table.clear();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);

        Label titleLabel = new Label("You won :D", labelStyle);
        Label scoreLabel = new Label("Score: " + score, labelStyle);
        Label topScoresLabel = new Label("Top Scores: ", labelStyle);

        //add time remaining and 'press R'
        table.add(titleLabel).pad(2).row();
        table.add(scoreLabel).pad(2).row();
        table.add(topScoresLabel).pad(20).row();
        topScores = leaderboard.getTopScores();
        for (Object obj : topScores) {
            LeaderboardEntry entry = (LeaderboardEntry) obj;
            Label leaderboardLabel = new Label(entry.getPosition() + ")  " + entry.toString(), labelStyle);
            table.add(leaderboardLabel).pad(2).left().row();
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.4f, 0.2f, 1f);
        if(typing){
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
        game.batch.begin();
        //Reset game variables and return to main menu on pressing R key
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.score = 0;      //  reset score
            EventCounter.reset();  // reset event counter
            game.setScreen(new MenuScreen(game)); //return to main menu
            dispose();
            return;
        }
        game.batch.end();
        stage.draw();
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
                this.show();
            } else{
                username = "";
                uniqueUsername = false;
            }
        }
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
