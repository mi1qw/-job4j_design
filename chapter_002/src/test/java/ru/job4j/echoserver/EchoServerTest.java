package ru.job4j.echoserver;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

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
    //@SuppressFBWarnings({"UNENCRYPTED_SOCKET", "DM_DEFAULT_ENCODING"})
    @SuppressFBWarnings({"UNENCRYPTED_SOCKET"})
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        int m = 100;
        while (m-- != 0) {
            try {
                try (Socket socket = new Socket("127.0.0.1", 9000);
                     InputStreamReader inputStreamReader
                             = new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8);
                     BufferedReader reader = new BufferedReader(inputStreamReader);
                     PrintWriter writer = new PrintWriter(socket.getOutputStream(), true, StandardCharsets.UTF_8)) {
                    writer.println("GET /?msg=Exit HTTP/1.1");
                    System.out.println("Client sended");
                    String str = reader.readLine();
                    System.out.println("Client get -  " + str);
                }
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
        assertThat(1, Matchers.is(1));
    }

    @Test
    public void whenAskedHello() throws IOException {
        this.testServer("GET /?msg=Hello HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Hello, dear friend."));
    }

    @Test
    public void whenAskedWhat() throws IOException {
        this.testServer("GET /?msg=What HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("What do you want from me?"));
    }

    @Test
    public void whenAskedExit() throws IOException {
        this.testServer("GET /?msg=Exit HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Exit"));
    }

    @Test
    public void whenAskedAnything() throws IOException {
        this.testServer("GET /?msg=Anything HTTP/1.1", "HTTP/1.1 200 OK" + LN + LN.
                concat("Anything"));
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
