package com.skyplus.hockey.network;

import com.badlogic.gdx.Gdx;
import com.skyplus.hockey.Hockey;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

abstract public class GameClient implements GameClientInterface {

    protected static Socket socket, voiceSocket;
    protected String localAddress;
    protected GameListener gameListener;

    public GameClient(GameListener gameListener,String localAddress) {

        this.localAddress = localAddress;
        this.gameListener = gameListener;
    }

    @Override
    abstract public void run();



    @Override
    abstract public void cancel();

    public String getLocalSubnet() {
        String[] bytes = localAddress.split("\\.");
        return bytes[0] + "." + bytes[1] + "." + bytes[2] + ".";
    }

    @Override
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }

    @Override
    public void onConnected() {
        gameListener.onConnected();
    }

    @Override
    public void disconnect() {
        sendMessage("Disconnect");
        try {
            socket.close();
            voiceSocket.close();
        } catch (IOException io) {
        }
    }

    protected class ReceiveThread implements Runnable {
        public void run() {
            Gdx.app.log("Receiving","Message " + socket.getPort() + " " + socket.getInetAddress());
            while (isConnected()) {
                try {
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                    String message = in.readUTF();
                    Gdx.app.log("message",message);
                    gameListener.onMessageReceived(message);

                } catch (Exception io) {
//                    Gdx.app.log(DeviceAPI.TAG, "Disconnected");
//                    callback.onDisconnected();
                }
            }
            Gdx.app.error("Closed","Khong co nghe");
            disconnect();
        }
    }

    protected class VoiceReceiveThread implements Runnable {
        public void run() {

//            callback.getDeviceAPI().startRecording();

            while (isConnected()) {
                try {
                    byte[] message = new byte[Hockey.deviceAPI.getBufferSize()];
                    DataInputStream dis = new DataInputStream(voiceSocket.getInputStream());
                    dis.read(message);
//                    callback.getDeviceAPI().transmit(message);
                } catch (IOException io) {
                }
            }
        }
    }
    @Override
    public void sendMessage(String message) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Gdx.app.log("Receiving","Message " + socket.getPort() + " " + socket.getInetAddress());
            oos.writeUTF(message);
            oos.flush();
        } catch (IOException io) {
//            Gdx.app.log(DeviceAPI.TAG, "Failed to send message");
        }
    }

    @Override
    public void sendVoiceMessage(byte[] message) {
        try {
            DataOutputStream dos = new DataOutputStream(voiceSocket.getOutputStream());
            dos.write(message, 0, message.length);
            dos.flush();
            Gdx.app.log("Receiving","Message " + socket.getPort() + " " + socket.getInetAddress());
//            Gdx.app.log(DeviceAPI.TAG, "Packet sent to: " + packet.getAddress());

        } catch (Exception e) {
//            Gdx.app.log(DeviceAPI.TAG, "Failed to send voice: " + e.toString());
        }
    }
}