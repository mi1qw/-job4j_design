package ru.job4j.echoserver;

import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EchoServerTest extends Mockito {
    private static final String LN = System.lineSeparator();

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
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        EchoServer echoServer = new EchoServer();
        echoServer.start(socket);
        assertThat(out.toString(), is(expected));
    }
}
