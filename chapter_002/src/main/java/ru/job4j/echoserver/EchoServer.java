package ru.job4j.echoserver;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Map;

//@SuppressFBWarnings("NP_DEREFERENCE_OF_READLINE_VALUE")
public class EchoServer {
    private static final String LN = System.lineSeparator();
    private final Map<String, String> map = Map.of(
            "Hello", "Hello, dear friend.",
            "Exit", "Exit",
            "What", "What do you want from me?"
    );
    private static final String BEGINREG = "^GET /\\?msg=";
    private static final String ENDNREG = " HTTP.*\\z";

    public static void main(final String[] args) throws IOException {
        new EchoServer().server();
    }

    @SuppressFBWarnings("UNENCRYPTED_SERVER_SOCKET")
    private void server() throws IOException {
        String answer = "";
        try (ServerSocket server = new ServerSocket(9000)) {
            while (!"Exit".equals(answer)) {
                answer = start(server.accept());
            }
        }
        System.out.println("Server is stoped");
    }

    @SuppressFBWarnings("RCN_REDUNDANT_NULLCHECK_WOULD_HAVE_BEEN_A_NPE")
    protected final String start(final Socket socket) throws IOException {
        String answer = "";
        try (OutputStream out = socket.getOutputStream();
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String str;
            while (in.ready() && !"Exit".equals(answer) && (str = in.readLine()) != null) {
                System.out.println("Server " + str);
                answer = getMesage(str);
            }
            out.write(("HTTP/1.1 200 OK" + LN + LN).getBytes(StandardCharsets.UTF_8));
            out.write(answer.getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return answer;
    }

    private String getMesage(final String mesage) {
        String str = mesage.replaceAll(BEGINREG, "");
        str = str.replaceAll(ENDNREG, "");
        String answer = map.get(str);
        if (answer == null) {
            answer = str;
        }
        return answer;
    }
}