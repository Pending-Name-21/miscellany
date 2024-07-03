package com.integration_testing.t_240;

import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Sprite;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.integration_testing.Utils.*;


// T-238 Draw an overlay images in the screen
public class DrawOverlayImagesTest {


    @Test
    void verifyAOnTopB() {
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, "sprite-a.png");
        makeSprite(builder, "sprite-b.png");
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test
    void verifyBOnTopA() {
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, "sprite-a.png");
        Sprite spriteB = makeSprite(builder, "sprite-b.png");
        spriteB.setZ_index(1);
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test
    void verifyMultipleSprites() {
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, "sprite-a.png");
        Sprite spriteB = makeSprite(builder, "sprite-b.png");
        Sprite spriteC = makeSprite(builder, "sprite-a.png");
        Sprite spriteD = makeSprite(builder, "sprite-b.png");
        Sprite spriteE = makeSprite(builder, "sprite-b.png");
        Sprite spriteF = makeSprite(builder, "sprite-a.png");
        Sprite spriteG = makeSprite(builder, "sprite-b.png");
        Sprite spriteH = makeSprite(builder, "sprite-a.png");

        int offset = 80;
        List<Sprite> sprites = List.of(spriteB, spriteC, spriteD, spriteE, spriteF, spriteG, spriteH);
        for (Sprite sprite : sprites) {
            Coord position = sprite.getPosition();
            offset += 10;
            position.setX(offset);
            position.setY(offset);
        }

        runGame(makeGame(repository, new SoundRepository()));
    }
}
