package com.skyplus.hockey;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.skyplus.hockey.objects.DeviceAPI;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

/**
 * Created by TruongNN  on 3/24/2017.
 *
 */
public class AndroidLauncher extends AndroidApplication implements DeviceAPI {

	private int minBufferSize = 0;
	private final int maxBufferSize = 4096;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		boolean s = isConnectedToLocalNetwork();
		initialize(new Hockey(this), config);
	}

	// get ip cua thiet bi
	@Override
	public String getIpAddress() {
		WifiManager wifiMan = (WifiManager) getContext().getSystemService(getContext().WIFI_SERVICE);
		int ip = wifiMan.getConnectionInfo().getIpAddress();

		if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
			ip = Integer.reverseBytes(ip);
		}

		byte[] ipByteArray = BigInteger.valueOf(ip).toByteArray();

		String ipAddress;
		try {
			ipAddress = InetAddress.getByAddress(ipByteArray).getHostAddress();
		} catch (UnknownHostException e) {
			ipAddress = "Unable to get host address";
		}

		return ipAddress;
	}

	// kiem tra thiet bi co ket noi mang chua
	@Override
	public boolean isConnectedToLocalNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getContext().getSystemService(getContext().CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		return info != null && info.isConnected();
	}

	@Override
	public void showNotification(final String message) {
		handler.post(new Runnable()
		{
			@Override
			public void run() {
				Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void startRecording() {

	}

	@Override
	public void transmit(byte[] message) {

	}

	@Override
	public int getBufferSize() {
		return Math.max(minBufferSize, maxBufferSize);
	}


}
