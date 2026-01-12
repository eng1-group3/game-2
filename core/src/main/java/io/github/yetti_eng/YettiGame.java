package io.github.yetti_eng;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import io.github.yetti_eng.screens.MenuScreen;

// Called "Game" in the architecture documentation; renamed to avoid clash with LibGDX class name
/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class YettiGame extends Game {
    private static final float WORLD_SCALE = 80;

    public SpriteBatch batch;
    public FillViewport gameViewport;
    // --------- NEW CODE ----------
    public ScreenViewport uiViewport;
    // ----------------------

    private FreeTypeFontGenerator robotoGenerator;
    public BitmapFont font;
    public BitmapFont fontSmall;
    public BitmapFont fontBordered;
    public BitmapFont fontBorderedSmall;

    public float volume = 1.0f;
    private boolean paused;
    // ------ NEW CODE --------------
    public Achievements achievements;
    // ----------------------

    public Timer timer;
    public int score;

    // (New JavaDoc)
    /**
     * Called when the game is first created.
     * Initializes rendering resources, sets up the camera and viewport, then transitions
     * to the main menu screen.
     */
    @Override
    public void create() {
        batch = new SpriteBatch();
        // ------- NEW CODE -------
        achievements = new Achievements();
        // aspect ratio = width/height.
        float aspectRatio = (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        gameViewport = new FillViewport(30*aspectRatio,30);

        uiViewport = new ScreenViewport();
        // -----------------------

        robotoGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto.ttf"));

        var fontParameter = new FreeTypeFontParameter();
        fontParameter.size = 50;
        fontParameter.color = Color.WHITE.cpy();
        font = robotoGenerator.generateFont(fontParameter);

        fontParameter.size = 40;
        fontSmall = robotoGenerator.generateFont(fontParameter);

        fontParameter.size = 45;
        fontParameter.color = Color.BLACK.cpy();
        fontParameter.borderColor = Color.WHITE.cpy();
        fontParameter.borderWidth = 2;
        fontBorderedSmall = robotoGenerator.generateFont(fontParameter);

        fontParameter.size = 50;
        fontParameter.borderWidth = 4;
        fontBordered = robotoGenerator.generateFont(fontParameter);

        setScreen(new MenuScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        robotoGenerator.dispose();
        font.dispose();
        fontBordered.dispose();
        fontBorderedSmall.dispose();
        // ------- NEW CODE --------
        if (achievements != null) {
            achievements.deleteFile();
        }
        // ---------------------

    }

    /**
     * @param original The original size, given the dimensions of the game's 16x9 grid.
     * @return The scaled size based on the program's WORLD_SCALE.
     */
    public static float scaled(float original) {
        return original * WORLD_SCALE;
    }

    /**
     * @return true if the game is currently paused; false otherwise.
     */
    public boolean isPaused() {
        return paused;
    }

    @Override
    public void pause() {
        paused = true;
        super.pause();
    }

    @Override
    public void resume() {
        paused = false;
        super.resume();
    }

    /**
     * @return The final score for the game.
     */
    public int calculateFinalScore() {
        score += timer.getRemainingTime();
        return score;
    }

    public BitmapFont getFontBordered() {
        return fontBordered;
    }
}
