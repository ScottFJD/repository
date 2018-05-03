package com.inuker.bluetooth.library;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.receiver.listener.BluetoothBondListener;
import com.inuker.bluetooth.library.utils.BluetoothLog;

import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.name;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

/**
 * Created by dingjikerbo on 16/4/8.
 */
public class BluetoothService extends Service {

    private static Context mContext;
    BluetoothClient client;
    private Timer sportHeartTimer;
    String name = " ";
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BluetoothLog.v(String.format("BluetoothService onCreate"));
        mContext = getApplicationContext();
        BluetoothContext.set(mContext);
        //注册蓝牙链接状态s
        client = new BluetoothClient(mContext);
        if (sportHeartTimer == null){
            sportHeartTimer = new Timer(true);
            sportHeartTimer.schedule(mHeartTask,0,2000);
        }

        Log.e("启动服务","执行了onCreate()方法");
    }


    private boolean stringIsMac(String val){
        String trueMacAddress = "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}";
        // 这是真正的MAV地址；正则表达式；  
        if (val.matches(trueMacAddress)){
            return true;
        } else {
            return false;
        }
    }
    /**
     * 每次间隔6秒钟记录一次心率
     * */
    private Handler SportHearthandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Log.e("服务监听到的","name的值是多少"+name);
                if (name.length()>0){
                    boolean isMAC = stringIsMac(name);
                    if (isMAC){
                        client.registerConnectStatusListener(name, mConnectStatusListener);
                    } else {
                    }

                }else {
                    SharedPreferences pref = getSharedPreferences("MAC",MODE_WORLD_READABLE);
                    name = pref.getString("paobu_mac","");//第二个参数为默认值
                    if (name.length() >0){
                        boolean isMAC = stringIsMac(name);
                        if (isMAC){
                            connectDevice(name);
                        }else {

                        }

                    }

                }
            }
        }
    };


    //任务  
    private TimerTask mHeartTask = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            SportHearthandler.sendMessage(msg);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("启动服务","onStartCommand()方法");

        return START_STICKY;

    }
    //链接蓝牙的模块
    private void connectDevice(String mac) {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(30000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        client.connect(mac,options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {

                if (code == REQUEST_SUCCESS) {
                    // mAdapter.setGattProfile(profile);
                }
            }
        });
    }
    @Override
    public IBinder onBind(Intent intent) {
        BluetoothLog.v(String.format("BluetoothService onBind"));
        return BluetoothServiceImpl.getInstance();
    }


    BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {

            if (status == STATUS_CONNECTED) {
                Log.e("服务监听到的当前连接的mac",""+mac);

            } else if (status == STATUS_DISCONNECTED) {
                // showErrorWithStatus("连接失败");

                Log.e("服务监听到的当前断开的mac",""+mac);
                if (name.length() >0){
                    boolean isMAC = stringIsMac(name);
                    if (isMAC){
                        connectDevice(name);
                    }else {

                    }

                }
            }

        }
    };
}
