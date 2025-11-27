package io.github.yetti_eng;


// EXTEND THIS CLASS IF DOING LIBGDX SPECIFIC TESTS
// The commented lines are needed for Mockito (but I couldn't get it to work in our project because I am stupid)

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
import org.junit.jupiter.api.BeforeEach;
 
//import static org.mockito.Mockito.mock;

public abstract class AbstractHeadlessGdxTest {
    @BeforeEach
    public void setup() {
        //Gdx.gl = Gdx.gl20 = mock(GL20.class);
        HeadlessLauncher.main(new String[0]);
    }
}
