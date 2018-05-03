/*
package net.fitrun.fitrungame.sportSelect.speedy;

import android.app.Activity;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;
import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.inuker.bluetooth.library.WnqMacAddress;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleUnnotifyResponse;
import com.inuker.bluetooth.library.connect.response.BleWriteResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.Bluetoothservice.ClientManager;
import net.fitrun.fitrungame.Bluetoothservice.SampleGattAttributes;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.greendao.gen.GreenDaoManager;
import net.fitrun.fitrungame.greendao.gen.UserSportInfoDao;
import net.fitrun.fitrungame.sportSelect.courseSelect.CourseSportStatementActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.SportLog;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UploadSportLog;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserSportInfo;
import net.fitrun.fitrungame.view.LmjDashBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.key;
import static android.R.attr.value;
import static com.baidu.tts.loopj.AsyncHttpClient.log;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;

*/
/**
 * Created by 晁东洋 on 2017/3/28.
 * 快捷运动的界面
 *//*


public class SpeedNoControlSportUIActivity extends BaseActivity implements View.OnClickListener, SpeechSynthesizerListener {
    //获取到的心率
    int heard = 0;
    //跑步的mac地址
    String paobumac = "";
    String shouhuan_mac = "";

    FrameLayout new_sport_visib;
    View calculatorBg;
    View calculatorAnima;
    Button new_sport_start;
    ImageView new_sport_start_bg, countdown_image;
    private List<View> heartbeatViews;

    //初始化运动的信息
    TextView sport_name, course_proge_number,sport_speek;
    ProgressBar pb_progressbar;
    LmjDashBoard heard_das;
    TextView short_heart_number, short_grad_number, short_speed_number, short_kcal_type, short_kcal_number, short_distance_type, short_distance_number;
    TextView minute_text, second_text;
    ImageView video_back;
    //课程的开关按钮
    TextView is_start;
    boolean is0ff = true;
    FrameLayout main_fram;
    //课程的名字
    private String courseName;
    //课程的时长
    private int courseLength;
    //开始速度
    private int startSpeed;
    //课程最高速度
    int topSpeed;
    //最低心率
    private int minHeart;
    //最高心率
    private int maxHeart;

    boolean isstart = false;
    //坡度
    int grade;
    //卡路里
    int kcl;
    //距离
    int dis;

    //停止跑步机
    RelativeLayout stop;

    //循环读取数据的计时器
    private Handler handler = new Handler();
    Runnable runnable;
    //倒计时
    private int i = 0;
    Timer mTimer;

    //计时器的初始值,记录运动的时间
    int sceond_timer = 0;
    //检测心率的倒计时
    Timer timer_xinv;
    //课程的四个阶段
    int aPhase, bPhase, cPhase, dPhase;
    //当前的速度和当前的坡度
    int  current_grade;
    double current_speed;
    int speed;
    //记录运动数据转换为json字符串
    ArrayList<SportLog> sportLogs = new ArrayList<SportLog>();
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    JSONObject tmpObj = null;
    private Timer sportLogTimer;
    //是否是重新计时定时器
    boolean isStart = false;

    //记录总的运动数据，每隔6秒钟记录一次心率，每隔100米记录一次速度
    private Timer sportHeartTimer;

    List<Integer> heartList = new ArrayList<Integer>();
    List<Integer> speedList = new ArrayList<Integer>();
    List<Integer> paceList = new ArrayList<Integer>();
    int x =1;
    UserSportInfoDao userinfoDao;
    //初始化图片加载
    private ImageView man_bg;

    Activity mActivity;


    //百度语音sdk
    private SpeechSynthesizer mSpeechSynthesizer;
    private static final String TAG = "SportSelectActivity";
    private String mSampleDirPath;
    private static final String SAMPLE_DIR_NAME = "baiduTTS";
    private static final String SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female.dat";
    private static final String SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male.dat";
    private static final String TEXT_MODEL_NAME = "bd_etts_text.dat";
    private static final String LICENSE_FILE_NAME = "temp_license";
    private static final String ENGLISH_SPEECH_FEMALE_MODEL_NAME = "bd_etts_speech_female_en.dat";
    private static final String ENGLISH_SPEECH_MALE_MODEL_NAME = "bd_etts_speech_male_en.dat";
    private static final String ENGLISH_TEXT_MODEL_NAME = "bd_etts_text_en.dat";
    private static final int PRINT = 0;
    private static final int UI_CHANGE_INPUT_TEXT_SELECTION = 1;
    private static final int UI_CHANGE_SYNTHES_TEXT_SELECTION = 2;
    */
/**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *//*

    private GoogleApiClient mClient;

    //无法控制的蓝牙收到的数据进行处理
    //转了多少圈等于多少米
    double juli_one = 0d;
    double juli_two = 0d;
    double juli_three = 0d;
    //周长
    double DC = 0.000251327408;

    //新的计时任务
    Handler timehandler=new Handler();
    Runnable timerunnable;

    //计算速度10秒执行一次
    Handler speedHandler = new Handler();
    Runnable speedRunnable;


    //一秒钟转了多少圈
    long speedLoop;
    double aftjuli;
    double befjuli;
    double befjulinew;
    //记录变化之前和变化之后的秒数
    boolean isAfter = false;
    HashMap<Integer,Long> speedHasMap = new HashMap<>();
    //外挂计算的速度是多少
    double plug_speed;


    HashMap<Integer,Integer> mHashMap = new HashMap<>();

    @Override
    protected int getContentViewId() {
        return R.layout.layout_speed_sport_ui;

    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        SharedPreferences pref = getSharedPreferences("MAC", MODE_PRIVATE);
        paobumac = pref.getString("paobu_mac", "");//第二个参数为默认值
        shouhuan_mac = pref.getString("shouhuan_mac", "");

        new_sport_visib = (FrameLayout) findViewById(R.id.new_sport_visib);
        man_bg = (ImageView)findViewById(R.id.man_bg);
        Glide.with(this).load(R.mipmap.speed_sport_bg).into(man_bg);

        //开始按钮的动画
        heartbeatViews = new ArrayList<View>();
        calculatorBg = (View) findViewById(R.id.study_tool_calculator_backgroud);
        initAnimationBackground(calculatorBg);
        calculatorAnima = (View) findViewById(R.id.study_tool_calculator_animation);
        heartbeatViews.add(calculatorAnima);
        new_sport_start = (Button) findViewById(R.id.new_sport_start);
        new_sport_start.setOnClickListener(this);

        //获取课程的信息
        //获取课程id
        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        courseLength = Integer.parseInt(intent.getStringExtra("courseLength"));
        startSpeed = Integer.parseInt(intent.getStringExtra("startSpeed"));
        plug_speed = startSpeed;
        minHeart = Integer.parseInt(intent.getStringExtra("minHeart"));
        maxHeart = Integer.parseInt(intent.getStringExtra("maxHeart"));
        topSpeed = Integer.parseInt(intent.getStringExtra("topSpeed"));
        //初始化
        sport_name = (TextView) findViewById(R.id.sport_name);
        sport_speek = (TextView)findViewById(R.id.sport_speek);
        sport_name.setText(courseName);
        pb_progressbar = (ProgressBar) findViewById(R.id.pb_progressbar);
        heard_das = (LmjDashBoard) findViewById(R.id.heard_das);
        short_heart_number = (TextView) findViewById(R.id.short_heart_number);
        short_grad_number = (TextView) findViewById(R.id.short_grad_number);
        short_speed_number = (TextView) findViewById(R.id.short_speed_number);
        short_kcal_type = (TextView) findViewById(R.id.short_kcal_type);
        short_kcal_number = (TextView) findViewById(R.id.short_kcal_number);
        short_distance_type = (TextView) findViewById(R.id.short_distance_type);
        short_distance_number = (TextView) findViewById(R.id.short_distance_number);
        minute_text = (TextView) findViewById(R.id.minute_text);
        second_text = (TextView) findViewById(R.id.second_text);
        video_back = (ImageView) findViewById(R.id.video_back);
        video_back.setOnClickListener(this);
        is_start = (TextView)findViewById(R.id.is_start);
        is_start.setVisibility(View.VISIBLE);
        is_start.setText("点击开始跑步机");
        stop = (RelativeLayout)findViewById(R.id.stop);
        stop.setVisibility(View.GONE);

        main_fram = (FrameLayout)findViewById(R.id.main_fram);

        //初始化开关按钮是显示的
        man_bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return true;
            }
        });
        //设置字体
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/AGENCYB.TTF");
        short_heart_number.setTypeface(typeFace);
        short_grad_number.setTypeface(typeFace);
        short_speed_number.setTypeface(typeFace);
        short_kcal_type.setTypeface(typeFace);
        short_kcal_number.setTypeface(typeFace);
        short_distance_type.setTypeface(typeFace);
        short_distance_number.setTypeface(typeFace);
        //监听距离的变化
        short_distance_number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                befjuli = Double.parseDouble(short_distance_number.getText().toString().trim());
                Log.e("变化之前",befjuli+"");

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e("变化过程",Double.parseDouble(short_distance_number.getText().toString().trim())+"");
            }

            @Override
            public void afterTextChanged(Editable s) {
                aftjuli = Double.parseDouble(short_distance_number.getText().toString().trim());
                Log.e("变化之后",aftjuli+"");

                if (befjuli > aftjuli){
                    befjulinew = befjuli;
                }
            }
        });

        //倒计时
        countdown_image = (ImageView) findViewById(R.id.countdown_image);

        new_sport_start_bg = (ImageView) findViewById(R.id.new_sport_start_bg);
        course_proge_number = (TextView) findViewById(R.id.course_proge_number);
        courseLength = courseLength * 60;
        pb_progressbar.setMax(courseLength);
        aPhase = courseLength * 5 / 100;
        bPhase = courseLength * 10 / 100;
        cPhase = courseLength * 90 / 100;
        dPhase = courseLength;
        if (topSpeed ==0){
            topSpeed = 20;
        }

        int[] fengeData = {minHeart, maxHeart};//分割线的值
        heard_das.setFengeValue(fengeData);

        //初始化语音合成
        initialEnv();
        initialTts();

        isstart = false;
        new_sport_visib.setVisibility(View.GONE);
        is_start.setVisibility(View.GONE);
        new_sport_start_bg.setVisibility(View.GONE);

        //新的计时任务
         timerunnable = new Runnable() {
            @Override
            public void run() {
                timehandler.postDelayed(this, 1000);
                Message msg = new Message();
                msg.what = 1;
                isStart= true;
                SportUpdateLoghandler.sendMessage(msg);
            }
        };
        //延迟10秒钟计算速度
        speedRunnable = new Runnable() {
            @Override
            public void run() {
                speedHandler.postDelayed(this,6000);
                if (isAfter == false){
                    isAfter = true;
                    speedHasMap.put(0,speedLoop);
                }else {
                    isAfter = false;
                    speedHasMap.put(1,speedLoop);
                    Iterator iter = speedHasMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        int key = (int) entry.getKey();
                        long val = (long) entry.getValue();
                        if (key ==1){
                            if (speedHasMap.get(1) - speedHasMap.get(0)>0){
                                DecimalFormat df = new DecimalFormat("0.000000");
                                double dkm = (speedHasMap.get(1) - speedHasMap.get(0)) *DC;
                                double sudu = new BigDecimal(df.format(dkm)).setScale(5,BigDecimal.ROUND_FLOOR).doubleValue();
                                DecimalFormat dfspeed = new DecimalFormat("0.00");
                                double speed = new BigDecimal(df.format(sudu*600)).setScale(1,BigDecimal.ROUND_FLOOR).doubleValue();
                                plug_speed = speed;
                                short_speed_number.setText(speed+"");
                            }
                        }
                    }
                }
            }
        };


    }

    @Override
    public void initLoad() {
        super.initLoad();
        ClientManager.getClient().notify(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.NOCONTROL_HEART), mNotifyRsp);
        ClientManager.getClient().notify(shouhuan_mac, UUID.fromString(SampleGattAttributes.HEART_CLIENT_MY_CONFIG), UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF), mNotifyRsp);
        //注册蓝牙链接状态s
        ClientManager.getClient().registerConnectStatusListener(paobumac, mConnectStatusListener);
        ClientManager.getClient().registerConnectStatusListener(shouhuan_mac, mConnectStatusListener);
        speedList.add(0);
        speak("现在进入我们的体验课程，请开启跑步机并且根据我们的语音提示调整您的跑步速度");

    }

    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {

            if (status == STATUS_CONNECTED) {
                ClientManager.getClient().notify(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.NOCONTROL_HEART), mNotifyRsp);
                ClientManager.getClient().notify(shouhuan_mac, UUID.fromString(SampleGattAttributes.HEART_CLIENT_MY_CONFIG), UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF), mNotifyRsp);

            } else if (status == STATUS_DISCONNECTED) {
                connectDevice(mac);
            }
        }
    };

    //链接蓝牙的模块
    private void connectDevice(String mac) {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(15000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(mac, options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {

                if (code == REQUEST_SUCCESS) {
                    // mAdapter.setGattProfile(profile);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        this.mSpeechSynthesizer.resume();
        super.onResume();


    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mSpeechSynthesizer.pause();
        ClientManager.getClient().unregisterConnectStatusListener(shouhuan_mac, mConnectStatusListener);
        ClientManager.getClient().unnotify(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.NOCONTROL_HEART), mUnnotifyResponse);
        ClientManager.getClient().unnotify(shouhuan_mac, UUID.fromString(SampleGattAttributes.HEART_CLIENT_MY_CONFIG), UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF), mUnnotifyResponse);
        Log.e("断开连接",shouhuan_mac+"");
        ClientManager.getClient().disconnect(shouhuan_mac);
        handler.removeCallbacks(runnable);
        timehandler.removeCallbacks(timerunnable);
        speedHandler.removeCallbacks(speedRunnable);

        if (sportLogTimer != null){
            sportLogTimer.cancel();
        }
        if (timer_xinv != null) {
            timer_xinv.cancel();
            timer_xinv.purge();
        }
        sceond_timer = 0;
        stopHeartBeat();
        //最后更新用户信息
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<UploadSportLog> sportLogCall = service.updateAccountUser(UserInfo.systemUserId,kcl+"",dis+"",sceond_timer+"");
        sportLogCall.enqueue(new Callback<UploadSportLog>() {
            @Override
            public void onResponse(Call<UploadSportLog> call, Response<UploadSportLog> response) {
                if (response.body() == null){
                    return;
                }else {
                }
            }

            @Override
            public void onFailure(Call<UploadSportLog> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onStop() {
        this.mSpeechSynthesizer.stop();
        super.onStop();// ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.disconnect();
    }

    private int unsignedByteToInt(byte b) {
        return b & 0xFF;
    }


    */
/**
     * 注册通知的回掉
     *//*

    private final BleNotifyResponse mNotifyRsp = new BleNotifyResponse() {
        @Override
        public void onNotify(UUID service, UUID character, final byte[] value) {
            //通知和读写的UUID一样，可以直接用if括号里边的内容。
            if (character.equals(UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF)) == true) {
                //接受到的心率
                int index = ((value[0] & 0x01) == 1) ? 2 : 1;
                int format = (index == 1) ? BluetoothGattCharacteristic.FORMAT_UINT8 : BluetoothGattCharacteristic.FORMAT_UINT16;
                heard = unsignedByteToInt(value[index]);
                short_heart_number.setText(heard + "");
                heard_das.setValue(heard);
                Log.e("接受到的心率通知", String.format("Received heart rate: %d", heard));
            } else {
                if (value != null) {
                    if (Long.parseLong(String.format("%02X",value[1]),16) ==0) {
                        //停止跑步机

                        short_speed_number.setText(0 + "");
                        if (isstart == true){
                            isstart = false;
                            timehandler.removeCallbacks(timerunnable);
                            speedHandler.removeCallbacks(speedRunnable);
                        }

                        if (timer_xinv != null) {
                                timer_xinv.cancel();
                                timer_xinv.purge();
                        }
                    }else {
                        if (isstart == false){
                            isstart = true;
                            Log.e("执行了几次？","执行了很多次");
                            timehandler.postDelayed(timerunnable, 1000);
                            speedHandler.postDelayed(speedRunnable,1000);
                        }
                        double juli = Double.parseDouble(short_distance_number.getText().toString().trim());
                        kcl = (int) (UserInfo.weight *juli * 1.036);
                        short_kcal_number.setText(kcl+ "");

                        if (String.format("%02X", value[0]).equals("03") == true && String.format("%02X", value[1]).equals("00") == false && String.format("%02X", value[2]).equals("00") == true && String.format("%02X", value[3]).equals("00") == true){
                            //启动计数
                            Log.e("转了多少圈？",Long.parseLong(String.format("%02X",value[1]),16)+"");
                            speedLoop = Long.parseLong(String.format("%02X",value[1]),16);
                            DecimalFormat df = new DecimalFormat("0.000");
                            double dkm = Long.parseLong(String.format("%02X",value[1]),16) *DC;
                            juli_one = new BigDecimal(df.format(dkm)).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue()+befjulinew;
                            if (juli_one > befjuli){
                                short_distance_number.setText(juli_one+"");
                            }

                        }else if (String.format("%02X", value[0]).equals("03") == true && String.format("%02X", value[2]).equals("00") == false && String.format("%02X", value[3]).equals("00") == true){
                            Log.e("转了多少圈？",Long.parseLong(String.format("%02X",value[1]),16)+"");
                            speedLoop = Long.parseLong(String.format("%02X",value[1]),16);
                            DecimalFormat df = new DecimalFormat("0.000");
                            double dkm = Long.parseLong(String.format("%02X",value[1]),16) *DC+juli_one;
                            juli_two = new BigDecimal(df.format(dkm)).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue();
                            short_distance_number.setText(juli_two+"");
                        }else if (String.format("%02X", value[0]).equals("03") == true && String.format("%02X", value[2]).equals("00") == false && String.format("%02X", value[3]).equals("00") == false){
                            Log.e("转了多少圈？",Long.parseLong(String.format("%02X",value[1]),16)+"");
                            speedLoop = Long.parseLong(String.format("%02X",value[1]),16);
                            DecimalFormat df = new DecimalFormat("0.000");
                            double dkm = Long.parseLong(String.format("%02X",value[1]),16) *DC+juli_two;
                            juli_three = new BigDecimal(df.format(dkm)).setScale(2,BigDecimal.ROUND_FLOOR).doubleValue();
                            short_distance_number.setText(juli_three+"");
                        }

                    }
                }
            }

            //通知的UUID是单独的不需要拿出来判断

        }

        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
                Log.e("注册Notif成功", "注册Notif成功");
            } else {
                ClientManager.getClient().notify(WnqMacAddress.WNQMAC, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.NOCONTROL_HEART), mNotifyRsp);
                ClientManager.getClient().notify(WnqMacAddress.CUFFMAC, UUID.fromString(SampleGattAttributes.HEART_CLIENT_MY_CONFIG), UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF), mNotifyRsp);
                Log.e("注册Notif失败", "注册Notif失败");
            }
        }
    };
    */
/**
     * 写的回掉
     *//*

    private final BleWriteResponse mWriteRsp = new BleWriteResponse() {
        @Override
        public void onResponse(int code) {
            if (code == REQUEST_SUCCESS) {
            } else {
                Log.e("教程写数据失败", "教程写数据失败");
            }
        }
    };

    */
/**
     * 取消通知的回掉
     *//*

    private final BleUnnotifyResponse mUnnotifyResponse = new BleUnnotifyResponse() {
        @Override
        public void onResponse(int code) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.video_back:
                showWithStatus("正在上传数据请稍后。。。");

                Iterator iter = mHashMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    paceList.add((Integer) entry.getValue());

                }

                handler.removeCallbacks(runnable);
                //停止跑步机
                byte[] bytes = {0x02, 0x53, 0x03, 0x53 ^ 0x03, 0x03};
                ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                        bytes, mWriteRsp);

                if ((double) dis / 1000 >0.01){
                    DecimalFormat df = new DecimalFormat("0.00");
                    if (courseName.contains("课程") == true){
                        Intent mIntent = new Intent(SpeedNoControlSportUIActivity.this,CourseSportStatementActivity.class);
                        mIntent.putIntegerArrayListExtra("heartList", (ArrayList<Integer>) heartList);
                        mIntent.putIntegerArrayListExtra("speedList", (ArrayList<Integer>) speedList);
                        mIntent.putIntegerArrayListExtra("paceList",(ArrayList<Integer>) paceList);
                        mIntent.putExtra("time",sceond_timer+"");
                        mIntent.putExtra("dis",df.format((double) dis / 1000));
                        mIntent.putExtra("kcal",kcl/10+"");
                        mIntent.putExtra("courseName",courseName);
                        mIntent.putExtra("minHeart",minHeart+"");
                        mIntent.putExtra("maxHeart",maxHeart+"");
                        startActivity(mIntent);
                        AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
                        sceond_timer = 0;
                        mHashMap.clear();
                    }else {
                        Intent mIntent = new Intent(SpeedNoControlSportUIActivity.this,SpeedSportStatementActivity.class);
                        mIntent.putIntegerArrayListExtra("heartList", (ArrayList<Integer>) heartList);
                        mIntent.putIntegerArrayListExtra("speedList", (ArrayList<Integer>) speedList);
                        mIntent.putExtra("time",sceond_timer+"");
                        mIntent.putExtra("dis",df.format((double) dis / 1000));
                        mIntent.putExtra("kcal",kcl/10+"");
                        mIntent.putExtra("courseName",courseName);
                        startActivity(mIntent);
                        AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
                        sceond_timer = 0;
                        mHashMap.clear();
                    }

                }else {
                    AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
                    sceond_timer = 0;
                    mHashMap.clear();
                }

                break;
        }

    }

    @Override
    public void onBackPressed() {
        showWithStatus("正在上传数据请稍后。。。");
        handler.removeCallbacks(runnable);
        Iterator iter = mHashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            paceList.add((Integer) entry.getValue());
        }
        //停止跑步机
        byte[] bytes = {0x02, 0x53, 0x03, 0x53 ^ 0x03, 0x03};
        ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                bytes, mWriteRsp);
        if ((double) dis / 1000>0.01){
            DecimalFormat df = new DecimalFormat("0.00");
            if (courseName.contains("课程") == true){
                Intent mIntent = new Intent(SpeedNoControlSportUIActivity.this,CourseSportStatementActivity.class);
                mIntent.putIntegerArrayListExtra("heartList", (ArrayList<Integer>) heartList);
                mIntent.putIntegerArrayListExtra("speedList", (ArrayList<Integer>) speedList);
                mIntent.putIntegerArrayListExtra("paceList",(ArrayList<Integer>) paceList);
                mIntent.putExtra("time",sceond_timer+"");
                mIntent.putExtra("dis",df.format((double) dis / 1000));
                mIntent.putExtra("kcal",kcl/10+"");
                mIntent.putExtra("courseName",courseName);
                mIntent.putExtra("minHeart",minHeart+"");
                mIntent.putExtra("maxHeart",maxHeart+"");
                startActivity(mIntent);
                AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
                sceond_timer = 0;
                mHashMap.clear();
            }else {
                Intent mIntent = new Intent(SpeedNoControlSportUIActivity.this,SpeedSportStatementActivity.class);
                mIntent.putIntegerArrayListExtra("heartList", (ArrayList<Integer>) heartList);
                mIntent.putIntegerArrayListExtra("speedList", (ArrayList<Integer>) speedList);
                mIntent.putExtra("time",sceond_timer+"");
                mIntent.putExtra("dis",df.format((double) dis / 1000));
                mIntent.putExtra("kcal",kcl/10+"");
                mIntent.putExtra("courseName",courseName);
                startActivity(mIntent);
                AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
                sceond_timer = 0;
                mHashMap.clear();
            }
        }else {
            AppManager.getAppManager().finishActivity(SpeedNoControlSportUIActivity.class);
            sceond_timer = 0;
            mHashMap.clear();
        }
        return;


    }

    */
/**
     * 按钮的动画
     *//*


    private void initAnimationBackground(View view) {
        view.setAlpha(0.2f);
        view.setScaleX(1.4f);
        view.setScaleY(1.4f);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    */
/**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     *//*

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SpeedSportUI Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    private class HeatbeatThread extends Thread {
        public void run() {
            try {
                sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            while (true) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        for (View view : heartbeatViews) {
                            playHeartbeatAnimation(view);
                        }
                    }
                });
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        ;
    }

    private Thread heartbeatThread;

    */
/**
     * 开始心跳
     *//*

    private void startHeartBeat() {
        if (heartbeatThread == null) {
            heartbeatThread = new HeatbeatThread();
        }
        if (!heartbeatThread.isAlive()) {
            heartbeatThread.start();
        }
    }

    */
/**
     * 停止心跳
     *//*

    private void stopHeartBeat() {
        if (heartbeatThread != null && heartbeatThread.isInterrupted()) {
            heartbeatThread.interrupt();
            heartbeatThread = null;
            System.gc();
        }
    }


    // 按钮模拟心脏跳动
    private void playHeartbeatAnimation(final View heartbeatView) {
        AnimationSet swellAnimationSet = new AnimationSet(true);
        swellAnimationSet.addAnimation(new ScaleAnimation(1.0f, 1.8f, 1.0f, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f));
        swellAnimationSet.addAnimation(new AlphaAnimation(1.0f, 0.3f));

        swellAnimationSet.setDuration(500);
        swellAnimationSet.setInterpolator(new AccelerateInterpolator());
        swellAnimationSet.setFillAfter(true);

        swellAnimationSet.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AnimationSet shrinkAnimationSet = new AnimationSet(true);
                shrinkAnimationSet.addAnimation(new ScaleAnimation(1.8f, 1.0f, 1.8f, 1.0f, Animation.RELATIVE_TO_SELF,
                        0.5f, Animation.RELATIVE_TO_SELF, 0.5f));
                shrinkAnimationSet.addAnimation(new AlphaAnimation(0.3f, 1.0f));
                shrinkAnimationSet.setDuration(1000);
                shrinkAnimationSet.setInterpolator(new DecelerateInterpolator());
                shrinkAnimationSet.setFillAfter(false);
                heartbeatView.startAnimation(shrinkAnimationSet);// 动画结束时重新开始，实现心跳的View
            }
        });

        heartbeatView.startAnimation(swellAnimationSet);
    }

    */
/**
     * 每次间隔一分钟上传一次用户运动数据
     *//*

    private Handler SportLoghandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                int count = sportLogs.size();
                for(int i = 0; i < count; i++)
                {
                    tmpObj = new JSONObject();
                 */
/*   try {
                        tmpObj.put("systemUserId" , sportLogs.get(i).getSystemUserId());
                        tmpObj.put("type", sportLogs.get(i).getType());
                        tmpObj.put("time", sportLogs.get(i).getTime());
                        tmpObj.put("heartRate" , sportLogs.get(i).getHeartRate());
                        tmpObj.put("speed", sportLogs.get(i).getSpeed());
                        tmpObj.put("gradient", sportLogs.get(i).getGradient());
                        tmpObj.put("calorie", sportLogs.get(i).getCalorie());
                        tmpObj.put("length", sportLogs.get(i).getLength());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*//*

                    jsonArray.put(tmpObj);
                    tmpObj = null;
                }
                sportLogs.clear();
               // 将JSONArray转换得到String
                Log.e("转换后的数据",jsonArray.toString());
                uplodLog(jsonArray.toString());

            }
        }
    };


    //任务  
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            Message msg = new Message();
            msg.what = 1;
            SportLoghandler.sendMessage(msg);
        }
    };

    */
/**
     * 每次间隔6秒钟记录一次心率
     * *//*

    private Handler SportHearthandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                //每隔100米记录一次速度
                speedList.add((int)current_speed);
                heartList.add(heard);
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


    */
/**
     * 每次间隔1秒钟更新一次数据
     * *//*

    private Handler SportUpdateLoghandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                sceond_timer++;
                //转了多少圈
                minute_text.setText(Utils.secToTime(sceond_timer).substring(0, 2));
                second_text.setText(Utils.secToTime(sceond_timer).substring(3, 5));
                Log.e("执行了吗？22","执行了吗？22");
                SportLog sportLog = new SportLog(UserInfo.systemUserId, "5", sceond_timer + "", heard + "", speed + "", grade + "", kcl + "", short_distance_number.getText().toString());
                sportLogs.add(sportLog);
                if (sportLogTimer == null){
                    sportLogTimer = new Timer(true);
                    sportLogTimer.schedule(mTask,60*1000,60*1000);
                }
                if (sportHeartTimer == null){
                    sportHeartTimer = new Timer(true);
                    sportHeartTimer.schedule(mHeartTask,0,6000);
                }

                //记录心率等的数据
                if (sceond_timer >10 &&current_speed>1){
                    int pac = (int)(3600/current_speed);
                    Log.e("插入数据库的配速的秒数","当前秒数 = " + sceond_timer +"pac配速 = " +pac);
                    insertUser(null,current_speed,heard,pac);
                }
                //数据库记录完毕
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_progressbar.setProgress(sceond_timer);
                        // 创建一个数值格式化对象  
                        NumberFormat numberFormat = NumberFormat.getInstance();
                        // 设置精确到小数点后2位  
                        numberFormat.setMaximumFractionDigits(2);
                        String result = numberFormat.format((float) sceond_timer / (float) courseLength * 100);
                        course_proge_number.setText(result);
                    }
                });
                Log.e("计数器的数据3", sceond_timer + "");
                if (sceond_timer == 4 ) {
                    int sp_b = startSpeed * 10;
                    int bg_b = current_grade;
                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                            bytes, mWriteRsp);
                    if (courseName.contains("课程") == true){
                        speak("现在是热身阶段。您可以活动上肢，热身越充分，运动效果越好！");
                        sport_speek.setText("现在是热身阶段。您可以活动上肢，热身越充分，运动效果越好！");
                    }else {
                        speak("现在开始体验我们的"+courseName+"。您可以活动上肢，热身越充分，运动效果越好！");
                        sport_speek.setText("现在开始体验我们的"+courseName+"。您可以活动上肢，热身越充分，运动效果越好！");
                    }


                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sport_speek.setText("");
                        }
                    },8000);
                }
                if (sceond_timer == aPhase) {
                    speak("接下来进入快走阶段，请注意调节您的步伐！\n");
                    sport_speek.setText("接下来进入快走阶段，请注意调节您的步伐！");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sport_speek.setText("");
                        }
                    },8000);
                    int sp_b = startSpeed * 10 + 20;

                    int bg_b = current_grade;
                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                            bytes, mWriteRsp);
                }
                if (sceond_timer == bPhase) {
                    speak("下面我们进入今天的正式阶段。");
                    sport_speek.setText("下面我们进入今天的正式阶段。");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sport_speek.setText("");
                        }
                    },8000);
                    //进入跑步阶段后，每隔一分钟进行一次心率的检测，用来调整跑步的速度
                    timer_xinv = new Timer();
                    timer_xinv.scheduleAtFixedRate(new TimerTask() {
                        @Override
                        public void run() {
                            //超过设定的最大心率，减小速度
                            if (heard > maxHeart) {
                                if (current_speed*10 >= 10) {
                                    speak("您当前的运动强度过高，会影响运动效果，我们会适当降低一点速度，请注意调节步伐！");
                                    mUIHandler.sendEmptyMessage(0);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep(8000);
                                                mUIHandler.sendEmptyMessage(3);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }).start();

                                    if (heard - maxHeart >=20){
                                        int sp_b_c = (int)(current_speed*10) - 5;
                                        int bg_b_c = current_grade;
                                        byte[] bytes_c = {0x02, 0x53, 0x02, (byte) sp_b_c, (byte) bg_b_c, (byte) (83 ^ 2 ^ sp_b_c ^ bg_b_c), 0x03};
                                        ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                                bytes_c, mWriteRsp);
                                    }else if ( 10 <=heard - maxHeart && heard - maxHeart < 20){
                                        int sp_b_c = (int)(current_speed*10) - 3;
                                        int bg_b_c = current_grade;
                                        byte[] bytes_c = {0x02, 0x53, 0x02, (byte) sp_b_c, (byte) bg_b_c, (byte) (83 ^ 2 ^ sp_b_c ^ bg_b_c), 0x03};
                                        ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                                bytes_c, mWriteRsp);
                                    }else {
                                        int sp_b_c = (int)(current_speed*10) - 1;
                                        int bg_b_c = current_grade;
                                        byte[] bytes_c = {0x02, 0x53, 0x02, (byte) sp_b_c, (byte) bg_b_c, (byte) (83 ^ 2 ^ sp_b_c ^ bg_b_c), 0x03};
                                        ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                                bytes_c, mWriteRsp);
                                    }


                                }
                            }
                            //大于最高速度增加坡度
                            if (current_speed * 10 >= topSpeed * 10 && heard < minHeart  ){
                                int sp_b_n = (int) (current_speed*10);

                                int bg_b_add = current_grade +1 ;
                                byte[] bytes_add = { 0x02, 0x53,0x02,(byte)sp_b_n,(byte)bg_b_add, (byte) (83^2^sp_b_n^ bg_b_add),0x03};
                                ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                        bytes_add, mWriteRsp);
                            }
                            //小于最低心率，加速
                            if (heard < minHeart && current_speed*10 < topSpeed * 10) {
                                speak("您当前的运动强度过低，我们适当给您加点速度，请注意调节步伐，注意安全！");
                                mUIHandler.sendEmptyMessage(1);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(8000);
                                            mUIHandler.sendEmptyMessage(3);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();

                                Log.e("当前的速度",current_speed*10+"");
                                if (minHeart - heard >=20){
                                    int sp_b = (int) (current_speed*10) + 5;
                                    int bg_b = current_grade;
                                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                            bytes, mWriteRsp);
                                }else if ( 10 <=minHeart - heard && minHeart - heard < 20){
                                    int sp_b = (int) (current_speed*10) + 3;
                                    int bg_b = current_grade;
                                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                            bytes, mWriteRsp);
                                }else {
                                    int sp_b = (int) (current_speed*10) + 1;
                                    int bg_b = current_grade;
                                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                                            bytes, mWriteRsp);
                                }


                            }
                            if (minHeart < heard && heard < maxHeart) {
                                speak("您当前的运动强度非常合适，请注意保持！");
                                mUIHandler.sendEmptyMessage(2);

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Thread.sleep(8000);
                                            mUIHandler.sendEmptyMessage(3);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }

                        }
                    }, 10000, 30000);

                }
                if (sceond_timer == cPhase) {
                    speak("运动即将结束，我们降低一点速度,开始放松阶段");
                    sport_speek.setText("运动即将结束，我们降低一点速度,开始放松阶段");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sport_speek.setText("");
                        }
                    },8000);
                    if (timer_xinv != null) {
                        timer_xinv.cancel();
                        timer_xinv.purge();
                    }
                    int sp_b = startSpeed * 10;
                    int bg_b = current_grade;
                    byte[] bytes = {0x02, 0x53, 0x02, (byte) sp_b, (byte) bg_b, (byte) (83 ^ 2 ^ sp_b ^ bg_b), 0x03};
                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                            bytes, mWriteRsp);
                }

                if (sceond_timer >= courseLength) {
                    Log.e("计数器的数据4", sceond_timer + "");
                    timehandler.removeCallbacks(timerunnable);
                    speedHandler.removeCallbacks(speedRunnable);
                    if (timer_xinv != null) {
                        timer_xinv.cancel();
                        timer_xinv.purge();
                    }

                    //停止跑步机
                    byte[] bytes = {0x02, 0x53, 0x03, 0x53 ^ 0x03, 0x03};
                    ClientManager.getClient().write(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT),
                            bytes, mWriteRsp);


                }

            }
        }
    };



   Handler mUIHandler = new Handler(){
       @Override
       public void handleMessage(Message msg) {
           super.handleMessage(msg);
           switch (msg.what){
               case 0:
                   sport_speek.setText("您当前的运动强度过高，会影响运动效果，我们会适当降低一点速度，请注意调节步伐！");
                   break;
               case 1:
                   sport_speek.setText("您当前的运动强度过低，我们适当给您加点速度，请注意调节步伐，注意安全!");
                   break;
               case 2:
                   sport_speek.setText("您当前的运动强度非常合适，请注意保持！");
                   break;
               case 3:
                   sport_speek.setText("");
                   break;
           }
       }
   };

    private void insertUser(Long id, double speed, int heart, int pace) {
        userinfoDao = GreenDaoManager.getInstance().getSession().getUserSportInfoDao();
        UserSportInfo userSportInfo = new UserSportInfo(id,speed,heart,pace);

        userinfoDao.insert(userSportInfo);


    }

    */
/**
     * 每次隔一分钟上传一次用户信息
     * *//*

    public void uplodLog(String json){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.baseNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<UploadSportLog> sportLogCall = service.updateUser(json);
        sportLogCall.enqueue(new Callback<UploadSportLog>() {
            @Override
            public void onResponse(Call<UploadSportLog> call, Response<UploadSportLog> response) {
                if (response.body() == null){
                    return;
                }else {

                }

            }

            @Override
            public void onFailure(Call<UploadSportLog> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        this.mSpeechSynthesizer.release();
        ClientManager.getClient().unregisterConnectStatusListener(shouhuan_mac, mConnectStatusListener);
        ClientManager.getClient().unnotify(paobumac, UUID.fromString(SampleGattAttributes.NOCONTROL_CONFIG), UUID.fromString(SampleGattAttributes.NOCONTROL_HEART), mUnnotifyResponse);
        ClientManager.getClient().unnotify(shouhuan_mac, UUID.fromString(SampleGattAttributes.HEART_CLIENT_MY_CONFIG), UUID.fromString(SampleGattAttributes.HEART_MY_NOTIF), mUnnotifyResponse);
        Log.e("断开连接",shouhuan_mac+"");
        ClientManager.getClient().disconnect(shouhuan_mac);
        handler.removeCallbacks(runnable);
        timehandler.removeCallbacks(timerunnable);

        if (sportLogTimer != null){
            sportLogTimer.cancel();
        }
        if (timer_xinv != null) {
            timer_xinv.cancel();
            timer_xinv.purge();
        }
        sceond_timer = 0;
        super.onDestroy();

    }


    */
/**
     * 百度语音功能实现
     *//*

    //百度语音sdk


    //初始化
    private void initialEnv() {
        if (mSampleDirPath == null) {
            String sdcardPath = Environment.getExternalStorageDirectory().toString();
            mSampleDirPath = sdcardPath + "/" + SAMPLE_DIR_NAME;
        }
        makeDir(mSampleDirPath);
        copyFromAssetsToSdcard(false, SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/" + SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, TEXT_MODEL_NAME, mSampleDirPath + "/" + TEXT_MODEL_NAME);
        copyFromAssetsToSdcard(false, LICENSE_FILE_NAME, mSampleDirPath + "/" + LICENSE_FILE_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_SPEECH_MALE_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_SPEECH_MALE_MODEL_NAME);
        copyFromAssetsToSdcard(false, "english/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath + "/"
                + ENGLISH_TEXT_MODEL_NAME);
    }

    */
/**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     *//*

    private void copyFromAssetsToSdcard(boolean isCover, String source, String dest) {
        File file = new File(dest);
        if (isCover || (!isCover && !file.exists())) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = getResources().getAssets().open(source);
                String path = dest;
                fos = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = is.read(buffer, 0, 1024)) >= 0) {
                    fos.write(buffer, 0, size);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void makeDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private void initialTts() {
        this.mSpeechSynthesizer = SpeechSynthesizer.getInstance();
        this.mSpeechSynthesizer.setContext(this);
        this.mSpeechSynthesizer.setSpeechSynthesizerListener(this);
        // 文本模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_TEXT_MODEL_FILE, mSampleDirPath + "/"
                + TEXT_MODEL_NAME);
        // 声学模型文件路径 (离线引擎使用)
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_SPEECH_MODEL_FILE, mSampleDirPath + "/"
                + SPEECH_FEMALE_MODEL_NAME);
        // 本地授权文件路径,如未设置将使用默认路径.设置临时授权文件路径，LICENCE_FILE_NAME请替换成临时授权文件的实际路径，仅在使用临时license文件时需要进行设置，如果在[应用管理]中开通了正式离线授权，不需要设置该参数，建议将该行代码删除（离线引擎）
        // 如果合成结果出现临时授权文件将要到期的提示，说明使用了临时授权文件，请删除临时授权即可。
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_TTS_LICENCE_FILE, mSampleDirPath + "/"
                + LICENSE_FILE_NAME);
        // 请替换为语音开发者平台上注册应用得到的App ID (离线授权)
        this.mSpeechSynthesizer.setAppId("9420554"*/
/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*//*
);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("BM2xomRopS2voE4hvSyaRcs1",
                "kTxX9EGjoehIapSFKhg5cO1eR2jMXHsa"*/
/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*//*
);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        */
/*AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            Log.e(TAG,"auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e(TAG,"auth failed errorMsg=" + errorMsg);
        }*//*


        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        Log.e(TAG, "loadEnglishModel result=" + result);

        //打印引擎信息和model基本信息
        printEngineInfo();
    }


    */
/**
     * 打印引擎so库版本号及基本信息和model文件的基本信息
     *//*

    private void printEngineInfo() {
        log.e(TAG, "EngineVersioin=" + SynthesizerTool.getEngineVersion());
        log.e(TAG, "EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + TEXT_MODEL_NAME);
        log.e(TAG, "textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        log.e(TAG, "speechModelInfo=" + speechModelInfo);
    }

    //播放语音
    private void speak(String text) {
        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(text)) {
            text = "欢迎使用百度语音合成SDK,百度语音为你提供支持。";
        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            Log.e(TAG, "error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
        }
    }

    @Override
    public void onSynthesizeStart(String s) {

    }

    @Override
    public void onSynthesizeDataArrived(String s, byte[] bytes, int i) {

    }

    @Override
    public void onSynthesizeFinish(String s) {

    }

    @Override
    public void onSpeechStart(String s) {

    }

    @Override
    public void onSpeechProgressChanged(String s, int i) {

    }

    @Override
    public void onSpeechFinish(String s) {

    }

    @Override
    public void onError(String s, SpeechError speechError) {

    }


}
*/
