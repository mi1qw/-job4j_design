package ru.job4j.echoserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(final String[] args) throws IOException {
        boolean stopServer = false;
        try (ServerSocket server = new ServerSocket(9000)) {

            while (true) {
                Socket socket = server.accept();
                try (OutputStream out = socket.getOutputStream();
                     BufferedReader in = new BufferedReader(
                             new InputStreamReader(socket.getInputStream()))) {
                    String str;
                    while (in.ready()) {
                        str = in.readLine();
                        System.out.println(str);
                        if (str.matches(".*/\\?msg=Bye.*")) {
                            stopServer = true;
                        }
                    }
                    out.write("HTTP/1.1 200 OK\r\n\\".getBytes());
                    if (stopServer) {
                        break;
                    }
                }
            }
            System.out.println("Server is stoped");
        }
    }
}
