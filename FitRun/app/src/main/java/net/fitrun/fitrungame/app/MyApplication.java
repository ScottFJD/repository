package net.fitrun.fitrungame.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;


import com.tencent.bugly.crashreport.CrashReport;
import com.zhy.autolayout.config.AutoLayoutConifg;

import net.fitrun.fitrungame.greendao.gen.GreenDaoManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by 晁东洋 on 2016/10/17.
 * 自定义的applicition类
 */

public class MyApplication extends Application {
    private static MyApplication instance;

    private static LinkedList<Activity> activityList;

    private Activity activity;
    private static Context mContext;

    @Override

    public void onCreate()

    {
        super.onCreate();
        instance = this;
        activityList = new LinkedList<Activity>();
        mContext = getApplicationContext();
        GreenDaoManager.getInstance();

        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));

        CrashReport.initCrashReport(getApplicationContext(), "bd177a9f4d", false);

    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }


    public static Context getContext(){
        return mContext;
    }
    public Activity getActivity() {

        return activity;

    }

    public void setActivity(Activity activity) {

        this.activity = activity;

    }

    public static MyApplication getInstance()

    {

        return instance;

    }

    /**

     * Activity关闭时，删除Activity列表中的Activity对象*/

    public void removeActivity(Activity a){

        activityList.remove(a);

    }


    /**

     * 向Activity列表中添加Activity对象*/

    public void addActivity(Activity a){
        activityList.add(a);

    }

    /**

     * 关闭Activity列表中的所有Activity*/

    public void finishActivity(){

        for (Activity activity : activityList) {

            if (null != activity) {
                activity.finish();
            }

        }
        activityList.clear();

        //杀死该应用进程

        android.os.Process.killProcess(android.os.Process.myPid());

    }
}
