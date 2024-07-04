package com.integration_testing.t_224;

import com.bridge.ipc.Receiver;
import com.bridge.ipc.SocketServer;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.UnixDomainSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketUtils {

    public static final Path NAMESPACE =
            Path.of(System.getProperty("java.io.tmpdir"), "test-events-socket.sock");
    public static final String SCREEN_SOCKET = "/tmp/socket_console";

    public static void startServer(Receiver receiver, AtomicBoolean atomicBoolean) {
        try {
            Files.deleteIfExists(NAMESPACE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SocketServer socketServer = new SocketServer(receiver, NAMESPACE, atomicBoolean);
        Thread thread = new Thread(socketServer);
        thread.start();
        while (!Files.exists(NAMESPACE)) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }

    public static boolean isSocketRunning() {
        Path socketPath = Path.of(SCREEN_SOCKET);
        SocketAddress address = UnixDomainSocketAddress.of(socketPath);

        try (SocketChannel ignored = SocketChannel.open(address)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
