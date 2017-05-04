package com.skyplus.hockey.network;

import com.badlogic.gdx.Gdx;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 10-Nov-16.
 */
public class Client extends GameClient {

    ExecutorService pool;

    public Client(GameListener gameListener,String localAddress) {
        super (gameListener,localAddress);
    }

    @Override
    public void run() {
        try {
            socket.close();
            voiceSocket.close();
        } catch (Exception e) {}

        int bitCount = 254;

        pool = Executors.newFixedThreadPool(bitCount);

        String subnet = getLocalSubnet();
        for (int i = 0; i <= bitCount; i++) {
            Runnable task = new ConnectThread(subnet + i);
            pool.submit(task);
        }
        pool.shutdown();

        while (!isConnected() && !pool.isTerminated()) {


        }

        if (isConnected()) {
            if (!pool.isTerminated()) pool.shutdownNow();

            Thread receiveThread = new Thread(new ReceiveThread());
            receiveThread.start();

//            Thread voiceReceiveThread = new Thread(new VoiceReceiveThread());
//            voiceReceiveThread.start();

            gameListener.onConnected();
        } else {
            gameListener.onConnectionFailed();
        }
    }



    @Override
    public void cancel() {
        if (isConnected()) {
            try {
                socket.close();
            } catch (IOException io) {
//                Gdx.app.log(DeviceAPI.TAG, "Failed to close socket: " + io.toString());
            }
        }

        if (pool != null && pool.isShutdown() && !pool.isTerminated()) pool.shutdownNow();
    }

    private class ConnectThread implements Runnable {

        String address;

        public ConnectThread(String address) {
            this.address = address;
        }

        public void run() {
            try {
                Socket s = new Socket(address, port);

//                try {
//                    voiceSocket = new Socket(address, voicePort);
//                    if (!voiceSocket.getReuseAddress()) {
//                        voiceSocket.setReuseAddress(true);
//                    }
//                    if (!voiceSocket.getTcpNoDelay()){
//                        voiceSocket.setTcpNoDelay(true);
//                    }
//                } catch (Exception e) {
//                    Gdx.app.log("game", "VoiceSocket failed: " + e.toString());
//                }

                socket = s;
                if (!socket.getReuseAddress()) {
                    socket.setReuseAddress(true);
                }
                if (!socket.getTcpNoDelay()){
                    socket.setTcpNoDelay(true);
                }

                Gdx.app.log("mygdxgame", "Successfully connected to " + address);
            } catch (IOException io) {
//                MainMenuScreen.debugText += "\nConnection to " + address + " failed";
                Gdx.app.error("Faild","Connect faild "+ address);
            }
        }
    }
}
