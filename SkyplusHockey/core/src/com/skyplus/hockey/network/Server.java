package com.skyplus.hockey.network;

import com.skyplus.hockey.Hockey;
import java.io.IOException;
import java.net.ServerSocket;

public class Server extends GameClient{

    private static ServerSocket serverSocket, voiceServer;


    public Server(GameListener gameListener, String localAddress) {
        super(gameListener, localAddress);
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            voiceServer = new ServerSocket(voicePort);

            Hockey.deviceAPI.showNotification("Created game at "+ localAddress);

            Thread t1 = new Thread(new NormalServerThread());
            t1.start();

//            Thread t2 = new Thread(new VoiceServerThread());
//            t2.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void cancel() {

    }

    // lang nghe client ket noi toi
    private class NormalServerThread implements Runnable {
        @Override
        public void run() {
            try {
                socket = serverSocket.accept();
                if (!socket.getReuseAddress()) {
                    socket.setReuseAddress(true);
                }
                if (!socket.getTcpNoDelay()) {
                    socket.setTcpNoDelay(true);
                }

                Thread t = new Thread(new ReceiveThread());
                t.start();
                gameListener.onConnected();
            } catch (IOException io) {
                Hockey.deviceAPI.showNotification("Failed to create game");
//                MainMenuScreen.debugText = "Failed to create a server:\n " + io.getMessage();
//                callback.onConnectionFailed();
            }
        }
    }

    private class VoiceServerThread implements Runnable {
        @Override
        public void run() {
            try {
                voiceSocket = voiceServer.accept();
                if (!voiceSocket.getReuseAddress()) voiceSocket.setReuseAddress(true);
                if (!voiceSocket.getTcpNoDelay()) voiceSocket.setTcpNoDelay(true);

                Thread t = new Thread(new VoiceReceiveThread());
                t.start();

                voiceServer.close();
            } catch (Exception io) {
//                Gdx.app.log(DeviceAPI.TAG, "VoiceServerThreaderver exception: " + io.toString());
                try {
                    voiceServer.close();
                } catch (Exception e) {}
            }
        }
    }




}