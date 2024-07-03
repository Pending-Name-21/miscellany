package com.integration_testing.t_240;

import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import org.junit.jupiter.api.Test;

import static com.integration_testing.Utils.*;

// T-238 Draw an image in the console
public class DrawImageScreenTest {


    @Test
    void verifyThatASpriteDrawsOnScreen() {
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, "sprite-a.png");
        runGame(makeGame(repository, new SoundRepository()));
    }
}
