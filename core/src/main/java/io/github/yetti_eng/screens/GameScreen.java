package io.github.yetti_eng.screens;

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
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.InputHelper;
import io.github.yetti_eng.MapManager;
import io.github.yetti_eng.Timer;
import io.github.yetti_eng.YettiGame;
import io.github.yetti_eng.entities.Dean;
import io.github.yetti_eng.entities.Entity;
import io.github.yetti_eng.entities.Item;
import io.github.yetti_eng.entities.Player;
import io.github.yetti_eng.events.*;
import io.github.yetti_eng.EventCounter;

import java.util.ArrayList;

import static io.github.yetti_eng.YettiGame.scaled;

public class GameScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;

    private static final int TIMER_LENGTH = 300; // 300s = 5min

    private Texture playerTexUp;
    private Texture playerTexDown;
    private Texture playerTexLeft;
    private Texture playerTexRight;
    private Texture yetiTexture;

    private Texture exitTexture;
    private Texture checkinCodeTexture;
    private Texture doorTexture;
    private Texture doorframeTexture;
    private Texture longBoiTexture;
    private Texture waterSpillTexture;
    private Texture pauseTexture;
    private Texture lecturerTexture;
    private Texture assignmentTexture;

    private MapManager mapManager;
    private OrthographicCamera camera;

    private OrthographicCamera interfaceCamera;

    private Sound quackSfx;
    private Sound paperSfx;
    private Sound doorSfx;
    private Sound slipSfx;
    private Sound growlSfx;

    private Player player;
    private Dean dean;
    private Item exit;
    private final ArrayList<Entity> entities = new ArrayList<>();

    private Label hiddenText;
    private Label negativeText;
    private Label positiveText;
    private Label timerText;
    private Label scoreText;
    private final ArrayList<Label> messages = new ArrayList<>();
    private Button pauseButton;

    public GameScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.viewport, game.batch);
    }

    @Override
    public void show() {
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
        lecturerTexture = new Texture("character/lecturer.png");
        assignmentTexture = new Texture("item/assignment.png");

        pauseTexture = new Texture("ui/pause.png");

        camera = new  OrthographicCamera();
        camera.setToOrtho(false, 90, 60);
        interfaceCamera = new  OrthographicCamera();
        interfaceCamera.setToOrtho(false, scaled(16), scaled(9));
        mapManager = new MapManager(camera);
        mapManager.loadMap("map/map.tmx");

        quackSfx = Gdx.audio.newSound(Gdx.files.internal("audio/duck_quack.mp3"));
        paperSfx = Gdx.audio.newSound(Gdx.files.internal("audio/paper_rustle.wav"));
        doorSfx = Gdx.audio.newSound(Gdx.files.internal("audio/dorm_door_opening.wav"));
        slipSfx = Gdx.audio.newSound(Gdx.files.internal("audio/cartoon_quick_slip.wav"));
        growlSfx = Gdx.audio.newSound(Gdx.files.internal("audio/deep_growl_1.wav"));

        player = new Player(playerTexDown, 55, 25);
        exit = new Item(new WinEvent(), "exit", exitTexture, 80, 54, 2, 2.2f);
        dean = new Dean(yetiTexture, -2, 4.5f);
        dean.disable();
        dean.hide();

        entities.add(new Item(new KeyEvent(), "checkin_code", checkinCodeTexture, 45, 33, 1.5f, 1.5f));
        entities.add(new Item(new DoorEvent(), "door", doorTexture, 44, 21, 2, 2.2f, false, true));
        entities.add(new Item(new IncreasePointsEvent(), "long_boi", longBoiTexture, 2.5f, 8.5f, 1.5f, 1.5f));
        entities.add(new Item(new HiddenDeductPointsEvent(), "water_spill", waterSpillTexture, 59, 11, 3f, 3f, true, true));
        entities.add(new Item(new DoubleScoreEvent(), "lecturer", lecturerTexture, 11, 46, 3f, 3f, false, false));
        entities.add(new Item(new AssignmentEvent(), "assignment", assignmentTexture, 24, 32, 3f, 3f, false, false));


        entities.add(new Item(new ClosingDoorEvent(19, 2.2f), "closing_door", doorframeTexture, 12, 19, 2, 2.2f, false, false));

        //start new timer
        game.timer = new Timer(TIMER_LENGTH);
        game.timer.play();
        //create labels and position timer and event counters on screen
        timerText = new Label(null, new Label.LabelStyle(game.font, Color.WHITE.cpy()));
        timerText.setPosition(0, scaled(8.5f));
        scoreText = new Label(null, new Label.LabelStyle(game.font, Color.WHITE.cpy()));
        scoreText.setPosition(225, scaled(8.5f));
        hiddenText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        hiddenText.setPosition(scaled(4f), scaled(8.5f));
        negativeText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        negativeText.setPosition(scaled(7f), scaled(8.5f));
        positiveText = new Label(null, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        positiveText.setPosition(scaled(10f), scaled(8.5f));

        Gdx.input.setInputProcessor(stage);
        pauseButton = new Button(new TextureRegionDrawable(pauseTexture));
        pauseButton.setSize(48, 48);
        pauseButton.setPosition(scaled(15.6f), scaled(8.6f), Align.center);
        pauseButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (game.isPaused()) {
                    game.resume();
                } else {
                    game.pause();
                }
            }
        });
        stage.addActor(pauseButton);
    }

    @Override
    public void render(float delta) {
        input(delta); //handles player input
        logic(delta); //handles collisions and events
        draw(delta); //draws map and entities to screen
        postLogic(delta); // Used for logic that should happen after rendering, normally screen changes
    }

    private void input(float delta) {
        float dx = 0, dy = 0;
        float currentX = player.getX();
        float currentY = player.getY();
        float speed = player.getSpeedThisFrame(delta);

        player.resetMovement();
        //vertical movement
        if (InputHelper.moveUpPressed()) {
            dy  += speed;
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
        hitbox.setPosition(currentX, currentY + dy );
        if (!mapManager.isRectInvalid(player.getHitbox())) {
            player.addMovement(0, dy);
        }
        //sets the hitbox to correct player location
        hitbox.setPosition(player.getX(), player.getY());
    }

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

        // Detect collision with objects
        entities.forEach(e -> {
            if (player.collidedWith(e) && e.isEnabled()) {
                // Check for collision with solid objects
                if (e.isSolid()) {
                    //set the position of player to previous position if collision
                    player.setPosition(currentPos.x, currentPos.y);
                }
                // Check for interaction with items
                if (e instanceof Item item) {
                    item.interact(game, this, player);
                }
            }
        });

        // Clamp to edges of screen
        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        float playerWidth = player.getWidth();
        float playerHeight = player.getHeight();

        player.setX(MathUtils.clamp(player.getX(), 0, worldWidth - playerWidth));
        player.setY(MathUtils.clamp(player.getY(), 0, worldHeight - playerHeight));

        // Calculate remaining time
        int timeRemaining = game.timer.getRemainingTime();
        String text = (timeRemaining / 60) + ":" + String.format("%02d", timeRemaining % 60);
        timerText.setText(text);
        timerText.setStyle(new Label.LabelStyle(game.fontBordered, (game.timer.isActive() ? Color.WHITE : Color.RED).cpy()));

        //score
        scoreText.setText(game.score);
        scoreText.setStyle(new Label.LabelStyle(game.fontBordered, Color.WHITE));

        //updates event counters
        hiddenText.setText("Hidden:" + EventCounter.getHiddenCount());
        positiveText.setText("Positive:" + EventCounter.getPositiveCount());
        negativeText.setText("Negative:" + EventCounter.getNegativeCount());

        entities.forEach(e -> {
            if (e instanceof Item item && item.ID.equals("closing_door")) {
                ((ClosingDoorEvent) item.getEvent()).checkForAutoClose(this, player, item, delta);
            }
        });

        // Release the Dean if the timer is at 60 or less
        if (timeRemaining <= 60 && !dean.isEnabled()) {
            growlSfx.play(game.volume);
            spawnLargeMessage("Run! The dean is coming!");
            dean.show();
            dean.enable();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            if (game.isPaused()) {
                game.resume();
            } else {
                game.pause();
            }
        }
    }

    private void draw(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        camera.update();
        //draw map
        mapManager.render();
        game.viewport.apply();

        //main camera with map and entities
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        // Draw only visible entities
        entities.forEach(e -> { if (e.isVisible()) e.draw(game.batch); });
        // Draw exit, player, and dean on top of other entities
        if (exit.isVisible()) exit.draw(game.batch);
        if (player.isVisible()) player.draw(game.batch);
        if (dean.isVisible()) dean.draw(game.batch);
        game.batch.end();

        //separate user interface camera for text on screen
        game.batch.setProjectionMatrix(interfaceCamera.combined);
        game.batch.begin();

        if (game.isPaused()) {
            game.fontBordered.draw(
                game.batch, "PAUSED",
                0, interfaceCamera.viewportHeight / 2, interfaceCamera.viewportWidth,
                Align.center, false
            );
        }

        //draw timer and event counters to screen
        timerText.draw(game.batch, 1.0f);
        scoreText.draw(game.batch, 1.0f);
        hiddenText.draw(game.batch, 1.0f);
        positiveText.draw(game.batch, 1.0f);
        negativeText.draw(game.batch, 1.0f);

        //draws messages fading out in an upwards direction
        for (Label l : messages) {
            l.setY(l.getY()+1);
            l.getColor().add(0, 0, 0, -0.01f);
            l.draw(game.batch, 1);
        }
        messages.removeIf(l -> l.getColor().a <= 0);

        game.batch.end();

        // Finally, draw elements on the stage (clickable elements)
        stage.draw();
    }

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
        game.viewport.update(width, height, true);
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
    public void hide() {}

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

        pauseTexture.dispose();
        mapManager.dispose();
        stage.dispose();

        quackSfx.dispose();
        paperSfx.dispose();
        doorSfx.dispose();
        slipSfx.dispose();
        growlSfx.dispose();
    }

    /**
     * Spawn a text label at the centre of the screen
     * that floats upwards and fades out. Used to alert the player.
     * @param text The text that should be displayed.
     */
    public void spawnLargeMessage(String text) {
        Label label = new Label(text, new Label.LabelStyle(game.fontBordered, Color.WHITE.cpy()));
        label.setPosition(scaled(8), scaled(4.5f), Align.center);
        messages.add(label);
    }

    /**
     * Spawn a small text label at the bottom right of the screen
     * that floats upwards and fades out. Used when interacting with Items.
     * @param text The text that should be displayed.
     */
    public void spawnInteractionMessage(String text) {
        Label label = new Label(text, new Label.LabelStyle(game.fontBorderedSmall, Color.WHITE.cpy()));
        label.setPosition(interfaceCamera.viewportWidth, label.getHeight(), Align.right);
        messages.add(label);
    }

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

    /**
     * @return The current YettiGame object.
     */
    public YettiGame getGame() {
        return game;
    }
}
