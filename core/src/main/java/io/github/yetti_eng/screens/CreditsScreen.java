package io.github.yetti_eng.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.yetti_eng.YettiGame;

public class CreditsScreen implements Screen {
    private final YettiGame game;
    private final Stage stage;
    private final Table table;

    public CreditsScreen(final YettiGame game) {
        this.game = game;
        stage = new Stage(game.uiViewport, game.batch);
        table = new Table();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // make table fill whole window
        table.setFillParent(true);
        table.setDebug(true);

        stage.addActor(table);

        Label.LabelStyle labelStyle = new Label.LabelStyle(game.font, Color.WHITE);
        Label titleLabel = new Label("Credits", labelStyle);

        TextButton menuButton = new TextButton("Return to Menu", new TextButton.TextButtonStyle(null, null, null, game.font));
        menuButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new MenuScreen(game));
                dispose();
                return true;
            }
        });

        Label credit1 = addCreditHyperlinkLabel("LibGDX (Apache 2.0)", "https://github.com/libgdx/libgdx");
        Label credit2 = addCreditHyperlinkLabel("Cool School tileset (CC0 1.0)", "https://opengameart.org/content/cool-school-tileset");
        Label credit3 = addCreditHyperlinkLabel("Retro World (Custom)", "https://gif-superretroworld.itch.io/interior-pack");
        Label credit4= addCreditHyperlinkLabel("Pixel Character (CC0 1.0)", "https://kettoman.itch.io/free-pixel-character-base-pack-32x32-top-down-farmer-animations");
        Label credit5= addCreditHyperlinkLabel("Roboto (Open Font License 1.1)", "https://github.com/googlefonts/roboto-3-classic/");
        Label credit6=addCreditHyperlinkLabel("(Roboto copyright notice)", "https://github.com/googlefonts/roboto-3-classic/blob/main/OFL.txt");
        Label credit7=addCreditHyperlinkLabel("Duck Quack (CC0 1.0)", "https://freesound.org/people/Tabby+Gus./sounds/515408/");
        Label credit8=addCreditHyperlinkLabel("Paper Rustle (CC0 1.0)", "https://freesound.org/people/BenjaminNelan/sounds/353125/");
        Label credit9=addCreditHyperlinkLabel("door opening (CC0 1.0)", "https://freesound.org/people/pagancow/sounds/15419/");
        Label credit10=addCreditHyperlinkLabel("Cartoon Quick Zip (CC0 1.0)", "https://freesound.org/people/se2001/sounds/541506/");
        Label credit11=addCreditHyperlinkLabel("Deep Growl 1 (CC0 1.0)", "https://freesound.org/people/noahpardo/sounds/345733/ ");

        table.add(titleLabel).colspan(2).row();
        table.add(credit1).pad(5);
        table.add(credit2).pad(5).row();
        table.add(credit3).pad(5);
        table.add(credit4).pad(5).row();
        table.add(credit5).pad(5);
        table.add(credit6).pad(5).row();
        table.add(credit7).pad(5);
        table.add(credit8).pad(5).row();
        table.add(credit9).pad(5);
        table.add(credit10).pad(5).row();
        table.add(credit11).pad(5).row();
        table.add(menuButton).colspan(2);
    }

    private Label addCreditHyperlinkLabel(String text, String href) {
        Label label = new Label(text, new Label.LabelStyle(game.fontSmall, Color.CYAN.cpy()));
        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI(href);
            }
        });
        return label;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
