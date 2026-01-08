package io.github.yetti_eng.screens;

/** All JavaDoc for methods apart from spawnLargeMessage(), spawnInteractionMessage() and
 * getGame() methods are new.*/

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.*;
import io.github.yetti_eng.entities.Dean;
import io.github.yetti_eng.entities.Entity;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.events.*;

import java.util.ArrayList;

public class GameScreen implements Screen {
    public final YettiGame game;
    private final Stage stage;

    private static final int TIMER_LENGTH = 300; // 300s = 5min

    // Textures for the player sprite depending on movement direction.
    private Texture playerTexUp;
    private Texture playerTexDown;
    private Texture playerTexLeft;
    private Texture playerTexRight;

    // Textures for events
    private Texture yetiTexture;
    private Texture exitTexture;
    private Texture checkinCodeTexture;
    private Texture doorTexture;
    private Texture doorframeTexture;
    private Texture longBoiTexture;
    private Texture waterSpillTexture;
    private Texture pauseTexture;
    // ----- NEW CODE -------
    private Texture lecturerTexture;
    private Texture assignmentTexture;
    private Texture wallSolidTexture;
    private Texture wallPassableTexture;
    private Texture slowDownTexture;
    private Texture speedBoostTexture;
    // ----------------------

    private MapManager mapManager;
    // Camera for the gameplay.
    OrthographicCamera camera;
    // ------- NEW CODE ---------
    // Camera for the UI.
    OrthographicCamera interfaceCamera;
    // Table for the layout of the UI.
    private Table table;

    float mapWidth;
    float mapHeight;

    // Table for the pause menu.
    private PauseMenu pauseMenu;
    // ------------------------

    // Sound effects for events.
    private Sound quackSfx;
    private Sound paperSfx;
    private Sound doorSfx;
    private Sound slipSfx;
    private Sound growlSfx;
    public Sound speedSfx;
    // Player sprite.
    Player player;
    // Dean sprite.
    Dean dean;
    private Item exit;
    // List of events/ entities in game.
    final ArrayList<Entity> entities = new ArrayList<>();

    // Text for event counters.
    Label hiddenText;
    Label negativeText;
    Label positiveText;
    // Text for game timer.
    private Label timerText;
    // ------------ NEW CODE ------------
    // Text for game score.
    private Label scoreText;
    // ---------------------------------
    // List of messages to display in gameplay.
    final ArrayList<Label> messages = new ArrayList<>();

    /**
     * Sets up the game and stage for the UI.
     *
     * @param game the main game object.
     */
    public GameScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
    }

    public GameScreen(final YettiGame game, boolean skipStage) {
        this.game = game;
        this.stage = null;
    }

    /**
     * This method is run when the screen is shown. It initialises all attributes and creates the layout
     * of the UI.
     */
    @Override
    public void show() {
        // ---------- NEW CODE -----------
        // This makes the game start if not paused (there was a bug where the game would be in a paused state)
        if (game.isPaused()) {
            game.resume();
        }
        EventCounter.reset();
        //this to fix the bug of the game not reseting or going back to the mainmenu  the score after resting (i know there might be a better way but this works so do not touch :). )
        game.score = 0;
        // ------------------------------

        playerTexUp = new Texture("character/player_up.png");
        playerTexDown = new Texture("character/player_down.png");
        playerTexLeft = new Texture("character/player_left.png");
        playerTexRight = new Texture("character/player_right.png");
        yetiTexture = new Texture("character/yeti.png");

        exitTexture = new Texture("item/exit.png");
        checkinCodeTexture = new Texture("item/checkin_code.png");
        doorTexture = new Texture("item/door.png");
        doorframeTexture = new Texture("item/doorframe.png");
        longBoiTexture = new Texture("item/long_boi.png");
        waterSpillTexture = new Texture("item/water_spill.png");
        // ---------- NEW CODE -----------
        lecturerTexture = new Texture("character/lecturer.png");
        assignmentTexture = new Texture("item/assignment.png");
        wallSolidTexture = new Texture("item/walls_hidden.png");
        wallPassableTexture = new Texture("item/walls_hidden_low_opacity.png");
        speedBoostTexture = new Texture("item/speed.png");
        slowDownTexture = new Texture("item/slow_down.png");
        pauseTexture = new Texture("ui/pause.png");

        // (Following lines until line 168 edited, not strictly new)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.gameViewport.getWorldWidth(), game.gameViewport.getWorldHeight());
        game.gameViewport.setCamera(camera);

        interfaceCamera = new OrthographicCamera();
        interfaceCamera.setToOrtho(false, game.uiViewport.getWorldWidth(), game.uiViewport.getWorldHeight());
        game.uiViewport.setCamera(interfaceCamera);
        mapManager = new MapManager(camera);
        mapManager.loadMap("map/map.tmx");

        mapWidth = mapManager.getMapWidth();
        mapHeight = mapManager.getMapHeight();

        table = new Table();
        table.setFillParent(true);
        stage.addActor(table);
        //table.setDebug(true);
        table.top().left();

        // ---------------------------------

        quackSfx = Gdx.audio.newSound(Gdx.files.internal("audio/duck_quack.mp3"));
        paperSfx = Gdx.audio.newSound(Gdx.files.internal("audio/paper_rustle.wav"));
        doorSfx = Gdx.audio.newSound(Gdx.files.internal("audio/dorm_door_opening.wav"));
        slipSfx = Gdx.audio.newSound(Gdx.files.internal("audio/cartoon_quick_slip.wav"));
        growlSfx = Gdx.audio.newSound(Gdx.files.internal("audio/deep_growl_1.wav"));
        // -------- NEW CODE ---------
        speedSfx = Gdx.audio.newSound(Gdx.files.internal("audio/power_up.wav"));
        // ---------------------------
        player = new Player(playerTexDown, 55, 25);
        exit = new Item(new WinEvent(), "exit", exitTexture, 80, 54, 2, 2.2f);
        dean = new Dean(yetiTexture, -2, 4.5f);
        dean.disable();
        dean.hide();

        entities.add(new Item(new KeyEvent(), "checkin_code", checkinCodeTexture, 45, 33, 1.5f, 1.5f));
        entities.add(new Item(new DoorEvent(), "door", doorTexture, 44, 21, 2, 2.2f, false, true));
        entities.add(new Item(new WaterSpillEvent(), "water_spill", waterSpillTexture, 59, 11, 3f, 3f, true, true));
        // --------- NEW CODE ---------------
        entities.add(new Item(new DoubleScoreEvent(), "lecturer", lecturerTexture, 11, 46, 3f, 3f, false, false));
        entities.add(new Item(new AssignmentEvent(), "assignment", assignmentTexture, 24, 32, 3f, 3f, false, false));
        entities.add(new Item(new SpeedUpEvent(), "speed_up", speedBoostTexture, 58, 2, 2f, 2f));
        entities.add(new Item(new SlowDownEvent(), "slow_down", slowDownTexture, 2.5f, 6, 2f, 2f));
        entities.add(new Item(new ClosingDoorEvent(19, 2.2f), "closing_door", doorframeTexture, 12, 19, 2, 2.2f, false, false));
        // (Code before had one long boi, so all other ones are new)
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 2.5f, 8.5f, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 25, 46, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 26, 46, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 25, 48, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 26, 48, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 24, 47, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 60, 44, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 29, 27, 1.5f, 1.5f));
        entities.add(new Item(new LongBoiEvent(), "long_boi", longBoiTexture, 48, 41, 1.5f, 1.5f));

        // Hidden wall that becomes passable when touched
        HiddenWallEvent wallEvent = new HiddenWallEvent(wallPassableTexture);
        entities.add(new Item(wallEvent, "hidden_wall", wallSolidTexture, 31, 17, 2f, 2f, false, true));

        // (Next 3 lines are unchanged, and the after that until line 228, are only edited.
        //start new timer
        game.timer = new Timer(TIMER_LENGTH);
        game.timer.play();
        //create labels and position timer and event counters on screen
        timerText = new Label(null, new Label.LabelStyle(game.font, Color.WHITE.cpy()));
        scoreText = new Label(null, new Label.LabelStyle(game.font, Color.WHITE.cpy()));
        hiddenText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        negativeText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        positiveText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));

        table.add(timerText).expandX().left().padLeft(10);
        table.add(positiveText).pad(10);
        table.add(negativeText).pad(10);
        table.add(hiddenText).pad(10);

        // -------------------------
        Gdx.input.setInputProcessor(stage);
        Button pauseButton = new Button(new TextureRegionDrawable(pauseTexture));
        // (Here we edited the pause button to be within a table, so removed lines which positioned it.)
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                togglePause();
            }
        });

        // ---------- NEW CODE ------------
        pauseMenu = new PauseMenu(game, this);
        stage.addActor(pauseMenu);

        table.add(pauseButton).width(50f).height(50f).pad(10).row();
        table.add(scoreText).pad(10).bottom().left().expandY();
        // ------------------------------
    }

    /**
     * Called every frame to everything to the screen.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        input(delta); //handles player input
        logic(delta); //handles collisions and events
        draw(delta); //draws map and entities to screen
        postLogic(delta); // Used for logic that should happen after rendering, normally screen changes
    }

    /**
     * Checks if buttons have been pressed for player movement and updates player texture
     * accordingly. Also checks if the player is colliding with a map wall.
     *
     * @param delta the time in seconds since the last render.
     */
    private void input(float delta) {
        float dx = 0, dy = 0;
        float currentX = player.getX();
        float currentY = player.getY();
        float speed = player.getSpeedThisFrame(delta);

        player.resetMovement();
        //vertical movement
        if (InputHelper.moveUpPressed()) {
            dy += speed;
            player.setTexture(playerTexUp);
        }
        if (InputHelper.moveDownPressed()) {
            dy -= speed;
            player.setTexture(playerTexDown);
        }
        //horizontal movement
        if (InputHelper.moveRightPressed()) {
            dx += speed;
            player.setTexture(playerTexRight);
        }
        if (InputHelper.moveLeftPressed()) {
            dx -= speed;
            player.setTexture(playerTexLeft);
        }

        Rectangle hitbox = player.getHitbox();
        //tests if collision occurs after x movement
        hitbox.setPosition(currentX + dx, currentY);
        if (!mapManager.isRectInvalid(player.getHitbox())) {
            player.addMovement(dx, 0);
        }
        //tests if collision occurs after y movement
        hitbox.setPosition(currentX, currentY + dy);
        if (!mapManager.isRectInvalid(player.getHitbox())) {
            player.addMovement(0, dy);
        }
        //sets the hitbox to correct player location
        hitbox.setPosition(player.getX(), player.getY());
    }

    /**
     * Performs the logic of the game e.g. detecting collisions with events, camera movement, remaining time
     * and score, and whether to pause the game or not.
     *
     * @param delta the time in seconds since the last render.
     */
    private void logic(float delta) {
        Vector2 currentPos = player.getCurrentPos(); //save initial position of player
        //move only if game isn't paused
        if (!game.isPaused()) {
            player.doMove(delta, true);
            if (dean.isEnabled()) {
                dean.calculateMovement(player);
                dean.doMove(delta);
            }
        }

        // (All code within detectCollisions is not new, just put into method)
        // Detect player collisions
        detectCollisions(currentPos);

        // ------------ NEW CODE ------------
        // Centre camera on player
        camera.position.set(currentPos.x, currentPos.y, 0);

        // Clamp camera to edges of screen
        clampCamera();
        // ---------------------------------

        // (All code within calculateTimeRemaining is not new, just put into method)
        // Calculate remaining time
        int timeRemaining = calculateTimeRemaining();

        // ----------- NEW CODE -----------
        // Update score
        scoreText.setText(game.score + timeRemaining);
        scoreText.setStyle(new Label.LabelStyle(game.fontBordered, Color.WHITE));

        int totalScore = game.score + game.timer.getRemainingTime();

        if (totalScore >= 2000 && !game.achievements.isUnlocked("ducktorate Degree")) {
            game.achievements.unlock("ducktorate Degree");
            spawnLargeMessage("Achievement Unlocked!");
        }
        // ------------------------------

        // Updates event counters
        hiddenText.setText("Hidden:" + EventCounter.getHiddenCount());
        positiveText.setText("Positive:" + EventCounter.getPositiveCount());
        negativeText.setText("Negative:" + EventCounter.getNegativeCount());

        // ---------- NEW CODE -------------
        entities.forEach(e -> {
            if (e instanceof Item item && item.ID.equals("closing_door")) {
                ((ClosingDoorEvent) item.getEvent()).checkForAutoClose(this, player, item, delta);
            }
        });
        // -------------------------------

        // (All code within releaseDean is not new, just put into method)
        // Release the Dean if the timer is at 60 or less
        releaseDean(timeRemaining, true, true);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (game.isPaused()) {
                game.resume();
            } else {
                togglePause();
            }
        }
    }

    /**
     * Draws all elements to the screen. First draws all game elements based on the game camera and then
     * draws UI elements based on the UI camera.
     *
     * @param delta time in seconds since last render.
     */
    private void draw(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        // game world
        game.gameViewport.apply();

        camera.update();

        //draw map
        mapManager.render();

        //main camera with map and entities
        game.batch.setProjectionMatrix(game.gameViewport.getCamera().combined);
        game.batch.begin();
        // Draw only visible entities
        entities.forEach(e -> {
            if (e.isVisible()) e.draw(game.batch);
        });
        // Draw exit, player, and dean on top of other entities
        if (exit.isVisible()) exit.draw(game.batch);
        if (player.isVisible()) player.draw(game.batch);
        if (dean.isVisible()) dean.draw(game.batch);
        game.batch.end();

        // (For following code related to the UI, removed drawing each label individually as now in the table.)
        //separate user interface camera for text on screen
        game.uiViewport.apply();
        game.batch.setProjectionMatrix(interfaceCamera.combined);
        game.batch.begin();
        //draws messages fading out in an upwards direction
        for (Label l : messages) {
            l.setY(l.getY() + 1);
            l.getColor().add(0, 0, 0, -0.003f);
            l.draw(game.batch, 1);
        }
        messages.removeIf(l -> l.getColor().a <= 0);

        game.batch.end();

        // Finally, draw elements on the stage (clickable elements)
        stage.draw();
    }

    /**
     * Checks if player has ended the game by exiting successfully, being caught by the dean or timer running out.
     *
     * @param delta the time in seconds since last render.
     */
    private void postLogic(float delta) {
        // Exit collision
        if (player.collidedWith(exit) && exit.isEnabled()) {
            exit.interact(game, this, player);
            return;
        }
        // Dean collision
        if (player.collidedWith(dean) && dean.isEnabled()) {
            dean.getsPlayer(game);
            return;
        }
        // Timer runs out then player loses
        if (game.timer.hasElapsed()) {
            game.timer.finish();
            game.setScreen(new LoseScreen(game));
            dispose();
            return;
        }
    }


    @Override
    public void resize(int width, int height) {
        game.gameViewport.update(width, height, true);
        // ----- NEW CODE ------
        game.uiViewport.update(width, height, true);
        // --------------------
    }

    @Override
    public void pause() {
        game.timer.pause();
    }

    @Override
    public void resume() {
        game.timer.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        playerTexUp.dispose();
        playerTexDown.dispose();
        playerTexLeft.dispose();
        playerTexRight.dispose();
        yetiTexture.dispose();

        exitTexture.dispose();
        checkinCodeTexture.dispose();
        doorTexture.dispose();
        doorframeTexture.dispose();
        longBoiTexture.dispose();
        waterSpillTexture.dispose();
        // --------- NEW CODE ---------
        lecturerTexture.dispose();
        assignmentTexture.dispose();
        wallSolidTexture.dispose();
        wallPassableTexture.dispose();
        // ----------------------

        pauseTexture.dispose();
        mapManager.dispose();
        stage.dispose();

        quackSfx.dispose();
        paperSfx.dispose();
        doorSfx.dispose();
        slipSfx.dispose();
        growlSfx.dispose();
        // (Following line new)
        speedSfx.dispose();
    }

    /**
     * Method to check whether the dean should be released (if the timer is 60 seconds or less).
     *
     * @param timeRemaining the time in seconds left on the timer.
     * @param playSound boolean which is true if sound of dean being released needs to be played.
     * @param displayMessage boolean which is true if message of dean being released needs to be shown.
     */
    public void releaseDean(int timeRemaining, boolean playSound, boolean displayMessage) {
        if (timeRemaining <= 60 && !dean.isEnabled()) {
            if (playSound) {
                growlSfx.play(game.volume);
            }
            ;
            if (displayMessage) {
                spawnLargeMessage("Run! The dean is coming!");
            }
            ;
            dean.show();
            dean.enable();
        }
    }

    /**
     * Spawn a larger text label at the edge of the screen
     * that floats upwards and fades out. Used to alert the player.
     *
     * @param text The text that should be displayed.
     */
    public void spawnLargeMessage(String text) {
        Label label = new Label(text, new Label.LabelStyle(game.getFontBordered(), Color.WHITE.cpy()));
        // (Following line edited to use interfaceCamera.)
        label.setPosition( interfaceCamera.viewportWidth - 15, label.getHeight(), Align.right);
        messages.add(label);
    }

    /**
     * Spawn a text label at the centre of the screen
     * that floats upwards and fades out. Used to alert the player.
     *
     * @param text The text that should be displayed.
     */
    public void spawnInteractionMessage(String text) {
        Label label = new Label(text, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        // (Following line edited to use interfaceCamera.)
        label.setPosition(interfaceCamera.viewportWidth - 15, label.getHeight(), Align.right);
        messages.add(label);
    }

    /**
     * Fetches the remaining time left on the timer and updates the label to this time.
     *
     * @return time left of the timer.
     */
    public int calculateTimeRemaining() {
        int timeRemaining = game.timer.getRemainingTime();
        String text = game.timer.formatTimer(game.timer.getRemainingTime());
        timerText.setText(text);
        timerText.setStyle(new Label.LabelStyle(game.fontBordered, (game.timer.isActive() ? Color.WHITE : Color.RED).cpy()));
        return timeRemaining;
    }

    // --------- NEW CODE -----------
    /**
     * Updates the labels on the UI for each event counter.
     */
    void updateEventCounters() {
        hiddenText.setText("Hidden:" + EventCounter.getHiddenCount());
        positiveText.setText("Positive:" + EventCounter.getPositiveCount());
        negativeText.setText("Negative:" + EventCounter.getNegativeCount());
    }
    // --------------------------

    /**
     * Detects collision between the player and events.
     *
     * @param currentPos the player's current position.
     */
    void detectCollisions(Vector2 currentPos) {
        entities.forEach(e -> {
            if (player.collidedWith(e) && e.isEnabled()) {
                // Check for collision with solid objects
                if (e.isSolid()) {
                    //set the position of player to previous position if collision
                    player.setPosition(currentPos.x, currentPos.y);
                }
                // Check for interaction with items
                if (e instanceof Item item && !item.isUsed()) {
                    item.interact(game, this, player);
                }
            }
        });
    }

    // ---------- NEW CODE --------------
    /**
     * Clamps the camera so that when the player gets to the edge of the map, the camera stops
     * moving.
     */
    void clampCamera() {
        // Define camera and viewport variables
        float minCameraX = game.gameViewport.getWorldWidth() / 2;
        float minCameraY = game.gameViewport.getWorldHeight() / 2;

        float maxCameraX = mapWidth - minCameraX;
        float maxCameraY = mapHeight - minCameraY;

        // Only clamp if map is larger than viewport in each dimension
        if (mapWidth >= game.gameViewport.getWorldWidth()) {
            camera.position.x = MathUtils.clamp(
                camera.position.x,
                minCameraX,
                maxCameraX
            );
        }
        if (mapHeight >= game.gameViewport.getWorldHeight()) {
            camera.position.y = MathUtils.clamp(
                camera.position.y,
                minCameraY,
                maxCameraY
            );
        }
    }
    // ---------------------------------

    public Texture getDoorframeTexture() {
        return doorframeTexture;
    }

    public Texture getDoorTexture() {
        return doorTexture;
    }

    public Sound getQuackSfx() {
        return quackSfx;
    }

    public Sound getPaperSfx() {
        return paperSfx;
    }

    public Sound getDoorSfx() {
        return doorSfx;
    }

    public Sound getSlipSfx() {
        return slipSfx;
    }

    // ------------- NEW CODE -----------
    public Sound getspeedSfx() {
        return speedSfx;
    }
    // ------------------------------

    /**
     * @return The current YettiGame object.
     */
    public YettiGame getGame() {
        return game;
    }

    /**
     * Pauses or resumes the game based on if game currently paused or not.
     */
    public void togglePause() {
        if (game.isPaused()) {

            game.resume();
            pauseMenu.setVisible(false); // Hide the menu
            table.setVisible(true);      // Show the HUD/Table
        } else {

            game.pause();
            pauseMenu.setVisible(true);  // Show the menu
            table.setVisible(false);     // Hide the HUD/Table
        }
    }
}
