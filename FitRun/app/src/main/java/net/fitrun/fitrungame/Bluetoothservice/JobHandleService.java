package net.fitrun.fitrungame.Bluetoothservice;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.inuker.bluetooth.library.BluetoothService;

import java.util.List;

@SuppressLint("NewApi")
public class JobHandleService extends JobService {
	private int kJobId = 0;
	@Override
	public void onCreate() {
		super.onCreate();
		Log.i("INFO", "jobService create");
		
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("INFO", "jobService start");
		scheduleJob(getJobInfo());
		return START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	@Override
	public boolean onStartJob(JobParameters params) {
		// TODO Auto-generated method stub
		Log.i("INFO", "job start");
//		scheduleJob(getJobInfo());
		boolean isLocalServiceWork = isServiceWork(this, "com.dn.process.service.LocalService");
		boolean isRemoteServiceWork = isServiceWork(this, "com.dn.process.service.RemoteService");
//		Log.i("INFO", "localSericeWork:"+isLocalServiceWork);
//		Log.i("INFO", "remoteSericeWork:"+isRemoteServiceWork);
		if(!isLocalServiceWork||
		   !isRemoteServiceWork){
			this.startService(new Intent(this,BluetoothService.class));
			//Toast.makeText(this, "process start", Toast.LENGTH_SHORT).show();
		}
		return true;
	}

	@Override
	public boolean onStopJob(JobParameters params) {
		Log.i("INFO", "job stop");
//		Toast.makeText(this, "process stop", Toast.LENGTH_SHORT).show();
		scheduleJob(getJobInfo());
		return true;
	}

	/** Send job to the JobScheduler. */
    public void scheduleJob(JobInfo t) {
        Log.i("INFO", "Scheduling job");
        JobScheduler tm =
                (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        tm.schedule(t);
    }
    
    public JobInfo getJobInfo(){
    	JobInfo.Builder builder = new JobInfo.Builder(kJobId++, new ComponentName(this, JobHandleService.class));
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        builder.setRequiresCharging(false);
        builder.setRequiresDeviceIdle(false);
        builder.setPeriodic(10);
        return builder.build();
    }
    
    
    /** 
     * �ж�ĳ�������Ƿ��������еķ��� 
     *  
     * @param mContext 
     * @param serviceName 
     *            �ǰ���+��������������磺net.loonggg.testbackstage.TestService�� 
     * @return true�����������У�false�������û���������� 
     */  
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;  
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {  
            return false;  
        }  
        for (int i = 0; i < myList.size(); i++) {  
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {  
                isWork = true;  
                break;  
            }  
        }  
        return isWork;  
    }  
}
