package com.wc.wchat;

import android.net.wifi.p2p.WifiP2pManager;

public class WChatHelper {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;

    public WChatHelper(WifiP2pManager manager, WifiP2pManager.Channel channel) {
        this.mManager = manager;
        this.mChannel = channel;
    }

    public void discoverPeers() {
        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onFailure(int reasonCode) {
            }
        });


    }
}
