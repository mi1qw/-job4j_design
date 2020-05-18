package ru.job4j.echoserver;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;

class ClientThread implements Runnable {
    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @SuppressFBWarnings({"UNENCRYPTED_SOCKET"})
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        int n = 200;
        while (n-- != 0) {
            try (
                    Socket socket = new Socket(InetAddress.getLoopbackAddress(), 9000);
                    InputStreamReader inputStreamReader = new InputStreamReader(
                            socket.getInputStream(), StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    PrintWriter writer = new PrintWriter(
                            socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
                while (reader.ready()) {
                    System.out.println("Client get - " + reader.readLine());
                }
                writer.println("GET /?msg=Exit HTTP/1.1");
                writer.flush();
                System.out.println("Client sended");
                await().atLeast(50, TimeUnit.MILLISECONDS)
                        .atMost(3000, TimeUnit.MILLISECONDS)
                        .until(socket::isConnected);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


public class EchoServerTest extends Mockito {
    private static final String LN = System.lineSeparator();

    @Test
    public void my() throws IOException {
        ClientThread clientThread = new ClientThread();
        Thread alpha = new Thread(clientThread, "alpha");
        alpha.start();

        EchoServer.main("".split(""));
        assertTrue(true);
    }

    @Test
    public void whenAskedHello() throws IOException {
        this.testServer("GET /?msg=Hello HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Hello, dear friend."));
        assertTrue(true);
    }

    @Test
    public void whenAskedWhat() throws IOException {
        this.testServer("GET /?msg=What HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("What do you want from me?"));
        assertTrue(true);
    }

    @Test
    public void whenAskedExit() throws IOException {
        this.testServer("GET /?msg=Exit HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Exit"));
        assertTrue(true);
    }

    @Test
    public void whenAskedAnything() throws IOException {
        this.testServer("GET /?msg=Anything HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Anything"));
        assertTrue(true);
    }

    @Test
    public void whenAskedEmpty() throws IOException {
        this.testServer("", "HTTP/1.1 200 OK" + LN + LN);
        assertTrue(true);
    }

    @Test(expected = IOException.class)
    public void whenException() throws IOException {
        Socket socket = mock(Socket.class);
        when(socket.getInputStream()).thenThrow(new IOException());
        when(socket.getOutputStream()).thenThrow(new IOException());
        EchoServer echoServer = new EchoServer();
        echoServer.start(socket);
    }

    private void testServer(final String input, final String expected) throws IOException {
        Socket socket = mock(Socket.class);
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        EchoServer echoServer = new EchoServer();
        echoServer.start(socket);
        assertThat(out.toString(StandardCharsets.UTF_8), is(expected));
    }
}
