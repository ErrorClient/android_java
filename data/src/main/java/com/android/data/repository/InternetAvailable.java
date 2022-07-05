package com.android.data.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;

public class InternetAvailable {

    private final Context context;
    InternetAvailable(Context context) {
        this.context = context;
    }

    public boolean execute() {
//        Log.d("AAA", "InternetAvailable+ ");
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("8.8.8.8", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

//            Log.d("AAA", "InternetAvailable true");
            return true;
        } catch (IOException e) {
//            Log.d("AAA", "InternetAvailable false");
            return false; }
    }
//        Log.d("AAA", "InternetAvailable+ ");
//
//        ConnectivityManager connectivityManager =
//                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        Network networkCapabilities = connectivityManager.getActiveNetwork();
//        if (networkCapabilities == null) return false;
//
//        NetworkCapabilities activeNow = connectivityManager.getNetworkCapabilities(networkCapabilities);
//        if (activeNow == null) return false;
//
//        Log.d("AAA", "InternetAvailable return+ ");
//        return  (
//                activeNow.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
//                activeNow.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
//                activeNow.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
//        );
//        }
}
