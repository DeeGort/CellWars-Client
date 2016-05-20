package com.cellwars.client;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Tam�s on 2015-04-30.
 */
public class ClientConnection {

    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    public boolean connect(String ip) {
        try {
            client = new Socket(ip, 3000);
            OutputStream outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);
            InputStream inFromServer = client.getInputStream();
            in = new DataInputStream(inFromServer);

            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void sendMessage(String ... message) throws IOException {
        String joinedMessage = Stream.of(message)
                .collect(Collectors.joining(":"));

        out.writeUTF(joinedMessage);
    }

    public String getMessage() throws IOException {
        return in.readUTF();
    }

}
