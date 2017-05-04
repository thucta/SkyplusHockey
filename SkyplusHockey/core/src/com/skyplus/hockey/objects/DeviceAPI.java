package com.skyplus.hockey.objects;

public interface DeviceAPI {

    public String TAG = "mygdxgame";

    public String getIpAddress();

    public boolean isConnectedToLocalNetwork();

    public  void showNotification(String message);

    public  void startRecording();

    public void transmit(byte[] message);

    public int getBufferSize();
}