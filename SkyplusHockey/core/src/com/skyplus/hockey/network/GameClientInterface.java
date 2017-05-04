package com.skyplus.hockey.network;

public interface GameClientInterface extends Runnable {
    int port = 50012;
    int voicePort = 20112;

    boolean isConnected();

    void onConnected();

    void disconnect();

    void sendMessage(String message);

    void sendVoiceMessage(byte[] message);

    void cancel();

    @Override
    void run();
}