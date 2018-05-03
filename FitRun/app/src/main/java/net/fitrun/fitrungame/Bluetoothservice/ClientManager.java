package net.fitrun.fitrungame.Bluetoothservice;

import com.inuker.bluetooth.library.BluetoothClient;

import net.fitrun.fitrungame.app.MyApplication;

/**
 * Created by 晁东洋 on 2016/12/28.
 * 蓝牙链接的管理类
 */

public class ClientManager {
    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(MyApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}
