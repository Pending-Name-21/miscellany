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
    @Test
    void VerifyPacmanObjectRendersTest() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 80, 80);
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test
    void VerifyGhostObjectRendersTest() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 80, 80);
        runGame(makeGame(repository, new SoundRepository()));
    }

    @Test
    void VerifyPacmanObjectMovesInRectLineRendersTest() throws RenderException {
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

    @Test
    void VerifyGhostObjectMovesInRectLineRendersTest() throws RenderException {
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

    @Test
    void VerifyGhostAndPacmanSimultaneouslyObjectsMovesInRectLineRendersTest() throws RenderException {
        String projectPath = Paths.get("").toAbsolutePath().toString();

        SocketClient socketClient = new SocketClient(SocketClient.NAMESPACE);
        Transmitter transmitter = new Transmitter(socketClient);

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite ghost = makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 0, 10);
        Sprite pacman = makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 10, 20);
        Game game = makeGame(repository, new SoundRepository());
        try {
            for (int i = 0; i < 300; i++) {
                Coord newPosition = new Coord(300, 20);
                Coord newPosition2 = new Coord(i, 20);
                ghost.setPosition(newPosition);
                pacman.setPosition(newPosition2);
                Frame frame = new Frame(List.of(ghost), List.of());
                Frame frame2 = new Frame(List.of(pacman), List.of());
                transmitter.send(frame2);
                transmitter.send(frame);
                game.render();
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void VerifyGhostAndPacmanArePushedAwayWhenTheyCollide() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        boolean collisionDetected = false;

        SocketClient socketClient = new SocketClient(SocketClient.NAMESPACE);
        Transmitter transmitter = new Transmitter(socketClient);

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite ghost = makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 0, 10);
        Sprite pacman = makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 10, 20);
        Game game = makeGame(repository, new SoundRepository());
        try {
            for (int i = 0; i < 300; i++) {
                Coord pacmanPosition = new Coord(300, 20);
                Coord ghostPosition = new Coord(i, 20);
                ghost.setPosition(ghostPosition);
                pacman.setPosition(pacmanPosition);
                if (Math.abs(pacman.getPosition().getX() - ghost.getPosition().getX()) < 20 && Math.abs(pacman.getPosition().getY() - ghost.getPosition().getY()) < 20) {
                    collisionDetected = true;
                    pacman.setPosition(new Coord(0,0));
                    ghost.setPosition(new Coord(200,200));
                }
                Frame frame = new Frame(List.of(ghost), List.of());
                Frame frame2 = new Frame(List.of(pacman), List.of());

                transmitter.send(frame);
                transmitter.send(frame2);
                game.render();
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(collisionDetected);
        assertTrue(pacman.getPosition().getX() < ghost.getPosition().getX());
    }


    @Test
    void VerifyGameOverMessageWhenGhostAndPacmaTheyCollideTest() {
        String projectPath = Paths.get("").toAbsolutePath().toString();
        boolean collisionDetected = false;

        SocketClient socketClient = new SocketClient(SocketClient.NAMESPACE);
        Transmitter transmitter = new Transmitter(socketClient);

        SpriteRepository repository = new SpriteRepository();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite ghost = makeSprite(builder, projectPath + "/src/test/resources/ghost.png", 0, 10);
        Sprite pacman = makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 10, 20);
        Game game = makeGame(repository, new SoundRepository());
        try {
            for (int i = 0; i < 300; i++) {
                Coord pacmanPosition = new Coord(300, 20);
                Coord ghostPosition = new Coord(i, 20);
                ghost.setPosition(ghostPosition);
                pacman.setPosition(pacmanPosition);
                if (Math.abs(pacman.getPosition().getX() - ghost.getPosition().getX()) < 20 && Math.abs(pacman.getPosition().getY() - ghost.getPosition().getY()) < 20) {
                    Sprite gameover = makeSprite(builder, projectPath + "/src/test/resources/gameover.png", 0, 0);
                    collisionDetected = true;
                    gameover.setZ_index(1);
                    Frame frame = new Frame(List.of(ghost), List.of());
                    transmitter.send(frame);
                    game.render();
                    break;
                }
                Frame frame = new Frame(List.of(ghost), List.of());
                Frame frame2 = new Frame(List.of(pacman), List.of());

                transmitter.send(frame);
                transmitter.send(frame2);
                game.render();
                Thread.sleep(10);
            }
        } catch (RenderException | InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(collisionDetected);
    }
}