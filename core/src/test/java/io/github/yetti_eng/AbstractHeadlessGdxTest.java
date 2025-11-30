package io.github.yetti_eng;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeEach;
 
import static org.mockito.Mockito.mock;

/* Extend this class if LibGDX functions are needed in your tests. */
public abstract class AbstractHeadlessGdxTest {
    @BeforeEach
    public void setup() {
        Gdx.gl = Gdx.gl20 = mock(GL20.class);
        
        new HeadlessApplication(new ApplicationListener() {
            public void create() {}
            public void render() {}
            public void resize(int width, int height) {}
            public void pause() {}
            public void resume() {}
            public void dispose() {}
        });
    }
}
