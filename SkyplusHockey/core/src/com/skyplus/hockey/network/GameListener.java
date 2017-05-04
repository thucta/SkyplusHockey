package com.skyplus.hockey.network;

import com.skyplus.hockey.objects.DeviceAPI;

public interface GameListener {


    void onConnected();
    void onDisconnected();

    void onConnectionFailed();

    void onMessageReceived(String message);

}
