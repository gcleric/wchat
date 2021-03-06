package com.wc.wchat;

        import android.content.Context;
        import android.content.IntentFilter;
        import android.net.nsd.NsdManager;
        import android.net.nsd.NsdServiceInfo;
        import android.net.wifi.p2p.WifiP2pManager;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;

public class WChatMainActivity extends AppCompatActivity {
    private WChatHelper mWChatHelper;
    private final IntentFilter mIntentFilter = new IntentFilter();
    static final String TAG = "WChatMainActivity";

    private WifiP2pManager.Channel mChannel;
    private WifiP2pManager mManager;
    private WChatReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WChatReceiver(mManager, mChannel, this);

        // Indicates a change in the Wi-Fi P2P status.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        // Indicates a change in the list of available peers.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        // Indicates the state of Wi-Fi P2P connectivity has changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        // Indicates this device's details have changed.
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
/*
    @Override
    protected void onDestroy() {
        mWChatHelper.tearDown();
        mConnection.tearDown();
        super.onDestroy();
    }

    // NsdHelper's tearDown method
    public void tearDown() {
        mNsdManager.unregisterService(mRegistrationListener);
        mNsdManager.stopServiceDiscovery(mDiscoveryListener);
    }*/
}
