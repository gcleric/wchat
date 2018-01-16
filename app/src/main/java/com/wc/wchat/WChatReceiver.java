package com.wc.wchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class WChatReceiver extends BroadcastReceiver implements WifiP2pManager.PeerListListener {

    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
    private WChatMainActivity mActivity;
    private List<WifiP2pDevice> mPeers;


    public WChatReceiver(WifiP2pManager manager, WifiP2pManager.Channel channel, WChatMainActivity activity) {
        super();
        this.mManager = manager;
        this.mChannel = channel;
        this.mActivity = activity;

        this.mPeers = new ArrayList<>();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            // Check to see if Wi-Fi is enabled and notify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wifi P2P is enabled
            } else {
                // Wi-Fi P2P is not enabled
            }

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            // Call WifiP2pManager.requestPeers() to get a list of current peers
            if (mManager != null) {
                mManager.requestPeers(mChannel, this);
            }
            Log.d(WChatMainActivity.TAG, "P2P peers changed");

        } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
            // Respond to new connection or disconnections
        } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
            // Respond to this device's wifi state changing
        }
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {

        List<WifiP2pDevice> refreshedPeers = (List<WifiP2pDevice>) wifiP2pDeviceList.getDeviceList();
        if (!refreshedPeers.equals(mPeers)) {
            mPeers.clear();
            mPeers.addAll(refreshedPeers);

            // If an AdapterView is backed by this data, notify it
            // of the change. For instance, if you have a ListView of
            // available peers, trigger an update.
           // ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();

            // Perform any other updates needed based on the new list of
            // peers connected to the Wi-Fi P2P network.
        }

        if (mPeers.size() == 0) {
            Log.d(WChatMainActivity.TAG, "No devices found");
        }
    }
}
