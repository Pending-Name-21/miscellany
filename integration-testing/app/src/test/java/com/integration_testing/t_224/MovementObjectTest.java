package com.integration_testing.t_224;

import com.bridge.core.exceptions.renderHandlerExceptions.RenderException;
import com.bridge.ipc.Receiver;
import com.bridge.ipc.SocketClient;
import com.bridge.ipc.Transmitter;
import com.bridge.processinputhandler.InputVerifier;
import com.bridge.processinputhandler.KeyboardEventManager;
import com.bridge.renderHandler.builders.SpriteBuilder;
import com.bridge.renderHandler.repository.SpriteRepository;
import com.bridge.renderHandler.sprite.Sprite;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.List;

import static com.integration_testing.Utils.makeSprite;
import static com.integration_testing.t_224.SocketUtils.*;

public class MovementObjectTest {
    SpriteObject sprite;
    KeyboardEventManager keyboardEventManager;
    Transmitter transmitter;

    @BeforeEach
    void setUp() {
        //init keyboard event manager
        keyboardEventManager = new KeyboardEventManager();
        transmitter = new Transmitter(null);

        Receiver receiver = new Receiver();
        receiver.addBuffer(keyboardEventManager);

        AtomicBoolean atomicBoolean = new AtomicBoolean(true);

        //start socketServer and wait connection
        startServer(receiver, atomicBoolean);
        boolean successConnection = false;
        while (!successConnection) {
            if (isSocketRunning()) {
                Path socketPath = Path.of(SCREEN_SOCKET);
                SocketClient socketClient = new SocketClient(socketPath);
                transmitter = new Transmitter(socketClient);
                successConnection = true;
            } else {
                try {
                    System.out.println("Waiting for screen...");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Test
    void VerifyPacmanObjectMovesWithKeyboardInputRendersTest() {
        //sprite
        SpriteRepository repository = new SpriteRepository();
        String projectPath = Paths.get("").toAbsolutePath().toString();
        SpriteBuilder builder = new SpriteBuilder(repository);
        Sprite sprite1 = makeSprite(builder, projectPath + "/src/test/resources/pacman.png", 50, 50);

        // subscribe sprite into keyboard event manager
        try {
            sprite = new SpriteObject(transmitter, sprite1);
        } catch (RenderException e) {
            throw new RuntimeException(e);
        }
        keyboardEventManager.subscribe(sprite);

        //game loop
        InputVerifier verifier = new InputVerifier(List.of(keyboardEventManager));
        while (true) {
            verifier.check();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
