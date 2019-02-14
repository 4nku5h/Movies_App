package com.ankush.shrivastava.ankush.Internet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectivity extends BroadcastReceiver{
    Connectivity connectivity;
    public InternetConnectivity(Connectivity connectivity){
        this.connectivity=connectivity;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        //Boolean isDisconnected=intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,false);
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        final NetworkInfo wifi = connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        /*NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();*/
        Boolean isConnected = wifi != null && wifi.isConnected() ;

        if(!isConnected)
            connectivity.onNetworkStatusChanged(false);
        else
            connectivity.onNetworkStatusChanged(true);
    }
}
