package io.github.yetti_eng;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.screens.GameScreen;
import io.github.yetti_eng.screens.MenuScreen;

public class PauseMenu extends Table {

    public PauseMenu(final YettiGame game, final GameScreen gameScreen) {
        setFillParent(true);
        setVisible(false); // Hidden by default

        // Create semi-transparent background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.85f);
        pixmap.fill();
        Drawable background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        setBackground(background);
        pixmap.dispose();


        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(null, null, null, game.font);
        Label.LabelStyle titleStyle = new Label.LabelStyle(game.font, Color.WHITE);


        Label pauseLabel = new Label("GAME PAUSED", titleStyle);

        TextButton resumeBtn = new TextButton("Resume", buttonStyle);
        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.togglePause();
            }
        });

        TextButton restartBtn = new TextButton("Restart", buttonStyle);
        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                gameScreen.dispose();
            }
        });

        TextButton menuBtn = new TextButton("Main Menu", buttonStyle);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                gameScreen.dispose();
            }
        });


        add(pauseLabel).padBottom(40).row();
        add(resumeBtn).padBottom(20).row();
        add(restartBtn).padBottom(20).row();
        add(menuBtn).padBottom(20).row();
    }
}
