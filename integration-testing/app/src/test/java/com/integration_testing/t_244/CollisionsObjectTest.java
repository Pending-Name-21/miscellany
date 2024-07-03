package com.integration_testing.t_244;

import com.bridge.Game;
import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.ipc.Receiver;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.processinputhandler.MouseEventManager;
import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.render.Frame;
import com.bridge.renderHandler.repository.SoundRepository;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sprite.Coord;
import com.bridge.renderHandler.sprite.Size;
import com.bridge.renderHandler.sprite.Sprite;
import com.integration_testing.App;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.integration_testing.Utils.*;
import static org.junit.jupiter.api.Assertions.*;

class CollisionsObjectTest {
    @Test void VerifyPacmanObjectRendersTest(){
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 80, 80);
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test void VerifyGhostObjectRendersTest(){
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 80, 80);
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test void VerifyPacmanObjectMovesInRectLineRendersTest() throws RenderException {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SocketClient socketClient = new SocketClient(SocketClient.NAMESPACE);
        Transmitter transmitter = new Transmitter(socketClient);

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite sprite = makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 0, 10);
        Game game = makeGame(repository, new SoundRepository());
        try {
            for (int i = 0; i < 200; i++) {
                Coord newPosition = new Coord(i, 10);
                sprite.setPosition(newPosition);
                Frame frame = new Frame(List.of(sprite), List.of());
                transmitter.send(frame);
                game.render();
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test void VerifyGhostObjectMovesInRectLineRendersTest() throws RenderException {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SocketClient socketClient = new SocketClient(SocketClient.NAMESPACE);
        Transmitter transmitter = new Transmitter(socketClient);

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite sprite = makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 0, 10);
        Game game = makeGame(repository, new SoundRepository());
        try {
            for (int i = 0; i < 200; i++) {
                Coord newPosition = new Coord(20, i);
                sprite.setPosition(newPosition);
                Frame frame = new Frame(List.of(sprite), List.of());
                transmitter.send(frame);
                game.render();
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
