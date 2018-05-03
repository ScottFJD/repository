package net.fitrun.fitrungame.startApp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import net.fitrun.fitrungame.MainActivity;

/**
 * Created by 晁东洋 on 2017/3/13.
 */

public class BootBroadcastReceiver extends BroadcastReceiver {
    static final String ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)){
               Intent mainIntent= new Intent(context,MainActivity.class);
               mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               //启动app
               context.startActivity(mainIntent);
        }
    }
}
