package com.integration_testing;

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
import com.bridge.renderHandler.sprite.Sprite;
import com.bridge.updatehandler.UpdatePublisher;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

public class Utils {

    public static Game makeGame(SpriteRepository spriteRepository, SoundRepository soundRepository){
        MouseEventManager manager = new MouseEventManager();
        InputVerifier inputVerifier = new InputVerifier(List.of(manager));
        AGameSettings gameSettings = new AGameSettings() {
            @Override
            public boolean isGameOver() {
                return false;
            }
        };
        return new Game(inputVerifier, gameSettings, new UpdatePublisher(), new RenderManager(spriteRepository, soundRepository), new GameInitializer());
    }

    public static void runGame(Game game) {
        try {
            game.run();
        } catch (GameException e) {
            fail(e);
        }
    }

    public static Sprite makeSprite(SpriteBuilder builder, String path, int x, int y) {
        try {
            builder.buildCoord(80, 80)
                    .buildPath(path)
                    .buildSize(10.0, 10.0);
        } catch (NonExistentFilePathException e) {
            fail(e);
        }
        return builder.assemble();
    }
}