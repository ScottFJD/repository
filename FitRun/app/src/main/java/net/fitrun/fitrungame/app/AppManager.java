package net.fitrun.fitrungame.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.Stack;

/**
 * Created by 晁东洋 on 2016/10/13.
 */

public class AppManager {

    private static Stack<Activity> activityStack;

    private static AppManager instance;



    private AppManager(){}

    /**

     * 单一实例

     */

    public static AppManager getAppManager(){

        if(instance==null){

            instance=new AppManager();

        }

        return instance;

    }

    /**

     * 添加Activity到堆栈中

     */

    public void addActivity(Activity activity){

        if(activityStack==null){

            activityStack=new Stack<Activity>();

        }

        activityStack.add(activity);
        Log.e("添加activity的数量",activityStack.size()+"");

    }

    /**

     * 获取当前Activity（堆栈中最后压入的）

     */

    public Activity currentActivity(){

        Activity activity=activityStack.lastElement();

        return activity;

    }

    /**

     * 结束当前Activity（堆栈中最后压入的）

     */

    public void finishActivity(){

        Activity activity=activityStack.lastElement();

        finishActivity(activity);

    }

    /**

     * 结束指定的Activity

     */

    public void finishActivity(Activity activity){

        if(activity!=null && activityStack != null){

            activityStack.remove(activity);
            Log.e("activity的数量",activityStack.size()+"");

            activity.finish();

            activity=null;

        }

    }

    /**

     * 结束指定类名的Activity

     */

    public void finishActivity(Class<?> cls){

        for (Activity activity : activityStack) {

            if(activity.getClass().equals(cls) ){

                finishActivity(activity);

            }

        }

    }

    /**

     * 结束所有Activity

     */

    public void finishAllActivity(){

        for (int i = 0, size = activityStack.size(); i < size; i++){

            if (null != activityStack.get(i)){

                activityStack.get(i).finish();

            }

        }

        activityStack.clear();

    }

    /**

     * 彻底退出应用程序，安全退出

     *

     */

    public void AppExit(Context context) {

        try {

            finishAllActivity();

            /**这种方法会终止一切和这个程序包关联的，所有共享同一uid的process被kill，所有的activity会被removed

             所有创建的服务会停止，还会发一个广播 Intent.ACTION_PACKAGE_RESTARTED 导致所有注册alarms被stopped, notifications 被removed。

             但是在Android 2.2中新增了一个API可以帮助我们杀死后台进程(Android2.3再次强调其调用的API Level最小为8)killBackgroundProcesses是android.app.ActivityManager类的方法，

             使用时必须在androidmanifest.xml文件中加入KILL_BACKGROUND_PROCESSES这个权限。虽然本类还提供了restartPackage (String packageName) 方法调用的API Level为3，但是SDK中已经标记为deprecated，

             其实他们的原理都是一样的*/

            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

            //activityMgr.restartPackage(context.getPackageName());

            activityMgr.killBackgroundProcesses("com.meijianfang");

            //终止虚拟机

            System.exit(0);

            //杀死进程

            android.os.Process.killProcess(android.os.Process.myPid());

        } catch (Exception e) {	}

    }
}
