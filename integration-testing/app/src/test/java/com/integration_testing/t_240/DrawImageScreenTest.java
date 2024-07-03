package com.integration_testing.t_240;

import com.bridge.Game;
import com.bridge.core.exceptions.GameException;
import com.bridge.core.exceptions.renderHandlerExceptions.NonExistentFilePathException;
import com.bridge.gamesettings.AGameSettings;
import com.bridge.initializerhandler.GameInitializer;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.MouseEventManager;
import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.render.RenderManager;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.updatehandler.UpdatePublisher;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

// T-238 Draw an image in the console
public class DrawImageScreenTest {

    @Test
    void verifyThatASpriteDrawsOnScreen() {
        MouseEventManager manager = new MouseEventManager();
        InputVerifier inputVerifier = new InputVerifier(List.of(manager));
        AGameSettings gameSettings = new AGameSettings() {
            @Override
            public boolean isGameOver() {
                return false;
            }
        };

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        try {
            builder.buildCoord(80, 80)
                    .buildPath("sprite-a.png")
                    .buildSize(40.0, 40.0);
        } catch (NonExistentFilePathException e) {
            fail(e);
        }
        builder.assemble();

        Game game = new Game(inputVerifier, gameSettings, new UpdatePublisher(), new RenderManager(repository, new SoundRepository()), new GameInitializer());
        try {
            game.run();
        } catch (GameException e) {
            fail("Game failed to run", e);
        }
    }
}
