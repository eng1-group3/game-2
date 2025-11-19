package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

import java.util.ArrayList;

import static io.github.yetti_eng.YettiGame.scaled;

public class CreditsScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;

    private final ArrayList<Label> creditLabels = new ArrayList<>();

    public CreditsScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.viewport, game.batch);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        TextButton menuButton = new TextButton("Return to Menu", new TextButton.TextButtonStyle(null, null, null, game.font));
        menuButton.setPosition(scaled(16) / 2, scaled(1), Align.center);
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });
        stage.addActor(menuButton);

        // Left column
        addCreditHyperlinkLabel(
            "LibGDX (Apache License 2.0)", "https://github.com/libgdx/libgdx",
            scaled(0.5f), scaled(7f)
        );
        addCreditHyperlinkLabel(
            "Cool School tileset (CC0 1.0)", "https://opengameart.org/content/cool-school-tileset",
            scaled(0.5f), scaled(6f)
        );
        addCreditHyperlinkLabel(
            "Super Retro World (Custom licence)", "https://gif-superretroworld.itch.io/interior-pack",
            scaled(0.5f), scaled(5f)
        );
        addCreditHyperlinkLabel(
            "Free Pixel Character (CC0 1.0)", "https://kettoman.itch.io/free-pixel-character-base-pack-32x32-top-down-farmer-animations",
            scaled(0.5f), scaled(4f)
        );
        addCreditHyperlinkLabel(
            "Roboto (Open Font License 1.1)", "https://github.com/googlefonts/roboto-3-classic/",
            scaled(0.5f), scaled(3f)
        );
        addCreditHyperlinkLabel(
            "(Roboto copyright notice)", "https://github.com/googlefonts/roboto-3-classic/blob/main/OFL.txt",
            scaled(1.5f), scaled(2f)
        );

        // Right column
        addCreditHyperlinkLabel(
            "Duck Quack (CC0 1.0)", "https://freesound.org/people/Tabby+Gus./sounds/515408/",
            scaled(9f), scaled(7f)
        );
        addCreditHyperlinkLabel(
            "Paper Rustle (CC0 1.0)", "https://freesound.org/people/BenjaminNelan/sounds/353125/",
            scaled(9f), scaled(6f)
        );
        addCreditHyperlinkLabel(
            "dorm door opening (CC0 1.0)", "https://freesound.org/people/pagancow/sounds/15419/",
            scaled(9f), scaled(5f)
        );
        addCreditHyperlinkLabel(
            "Cartoon Quick Zip (CC0 1.0)", "https://freesound.org/people/se2001/sounds/541506/",
            scaled(9f), scaled(4f)
        );
        addCreditHyperlinkLabel(
            "Deep Growl 1 (CC0 1.0)", "https://freesound.org/people/noahpardo/sounds/345733/ ",
            scaled(9f), scaled(3f)
        );
    }

    private void addCreditHyperlinkLabel(String text, String href, float x, float y) {
        Label label = new Label(text, new Label.LabelStyle(game.fontSmall, Color.CYAN.cpy()));
        label.setPosition(x, y);
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI(href);
            }
        });
        creditLabels.add(label);
        stage.addActor(label);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.4f, 0.4f, 0.4f, 1f);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.font.draw(game.batch, "Credits", 0, scaled(8.5f), scaled(16), Align.center, false);

        creditLabels.forEach(l -> l.draw(game.batch, 1.0f));

        game.fontSmall.draw(game.batch, "Click a link to go to the source", scaled(9f), scaled(2.5f));

        game.batch.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
