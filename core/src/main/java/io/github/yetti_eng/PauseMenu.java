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
/**
 * This class represents the Pause Menu overlay.
 * It is a table that sits on top of the game when the player presses ESC or press the button.
 * It has a dark background and buttons to Resume, Restart, or Quit.
 */
public class PauseMenu extends Table {

    /**
     * Creates the pause menu user interface.
     *
     * @param game       The main game object (used to switch screens)
     * @param gameScreen The current game screen (used to toggle pause off)
     */
    public PauseMenu(final YettiGame game, final GameScreen gameScreen) {
        setFillParent(true);
        setVisible(false); // Start hidden until the player pauses

        // Create a dark, semi-transparent background
        // This makes a 1x1 pixel image, sets it to black with 85% opacity,
        // and stretches it to cover the whole menu.
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 0.85f);
        pixmap.fill();
        Drawable background = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        setBackground(background);
        pixmap.dispose();

        //Setup Buttons and Text
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(null, null, null, game.font);
        Label.LabelStyle titleStyle = new Label.LabelStyle(game.font, Color.WHITE);


        Label pauseLabel = new Label("GAME PAUSED", titleStyle);

        // Resume Button: Unpauses the game
        TextButton resumeBtn = new TextButton("Resume", buttonStyle);
        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameScreen.togglePause();
            }
        });
        // Restart Button: Starts a fresh GameScreen
        TextButton restartBtn = new TextButton("Restart", buttonStyle);
        restartBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new GameScreen(game));
                gameScreen.dispose();
            }
        });
        // Main Menu Button: Exits to the main menu
        TextButton menuBtn = new TextButton("Main Menu", buttonStyle);
        menuBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MenuScreen(game));
                gameScreen.dispose();
            }
        });

        //Add everything to the table layout
        add(pauseLabel).padBottom(40).row();
        add(resumeBtn).padBottom(20).row();
        add(restartBtn).padBottom(20).row();
        add(menuBtn).padBottom(20).row();
    }
}
