package net.fitrun.fitrungame.sportSelect;


import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.tts.client.SpeechError;
import com.baidu.tts.client.SpeechSynthesizer;
import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.SynthesizerTool;
import com.baidu.tts.client.TtsMode;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.listener.BluetoothStateListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothUtils;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.Bluetoothservice.BLESelectDialog;
import net.fitrun.fitrungame.Bluetoothservice.ClientManager;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.athletics.SportAthleticsActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.CourseDetailsActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.CourseSelectActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.UserBean;
import net.fitrun.fitrungame.sportSelect.courseSelect.userinfo.EditUserInfoActivity;
import net.fitrun.fitrungame.sportSelect.endurance.EnduranTestActivity;
import net.fitrun.fitrungame.sportSelect.speedy.SpeedySportActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.view.FilterImageView;
import net.fitrun.fitrungame.view.TipView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.baidu.tts.loopj.AsyncHttpClient.log;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static java.security.CryptoPrimitive.MAC;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.headimgurl;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.name;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.nickname;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.sex;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.sumLength;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.systemUserId;

/**
 * Created by 晁东洋 on 2017/3/13.
 */

public class SportSelectActivity extends BaseActivity implements View.OnClickListener, SpeechSynthesizerListener, Callback<UserBean>{
    private TipView tipView;
    private TextView distance_number,distance_type,time_number,time_type,heat_name,heat_type;
    private TextView speedy_text,speedy_text_e,course_text,course_text_e,
            athletics_text,athletics_text_e,stamina_text,user_name,user_id;
    private FilterImageView stamina_sport_image,speedy_sport_image,course_sport_image,athletis_sport_image;
    private RelativeLayout main_bg;
    private ImageView exit_login,user_image,sex_image,distance_image,time_image,heart_image,ble_connect;
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
    //蓝牙链接的设置
    BLESelectDialog mSelectDialog;
    private Activity mActivity;
    private Handler mHandler = new Handler();
    //跑步机和手环必须全部连接成功
    private boolean paobu = false;
    private boolean shouhuan = false;
    String paobu_mac = "";
    String shouhuan_mac = "";
    private BluetoothDevice mDevice;
    private TextView ble_name;
    private ListView ble_list;
    private ProgressBar progress;
    private ToggleButton mScanBtn;
    private LeDeviceListAdapter mLeDeviceListAdapter;

    //运动选择
    RelativeLayout course_stamina_sport_image,course_speedy_sport_image
            ,course_course_sport_image,course_athletis_sport_image;

    //退出的提示
    ExitHintDialog mExitHintDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_sport_select;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        tipView = (TipView) findViewById(R.id.notif_image);
        tipView.setTipList(generateTips());

        distance_number = (TextView)findViewById(R.id.distance_number);
        distance_type = (TextView)findViewById(R.id.distance_type);
        time_number = (TextView)findViewById(R.id.time_number);
        time_type = (TextView)findViewById(R.id.time_type);
        heat_name = (TextView)findViewById(R.id.heat_name);
        heat_type = (TextView)findViewById(R.id.heat_type);
        speedy_text = (TextView)findViewById(R.id.speedy_text);
        speedy_text_e = (TextView)findViewById(R.id.speedy_text_e);
        course_text = (TextView)findViewById(R.id.course_text);
        course_text_e = (TextView)findViewById(R.id.course_text_e);
        athletics_text = (TextView)findViewById(R.id.athletics_text);
        athletics_text_e = (TextView)findViewById(R.id.athletics_text_e);
        stamina_text = (TextView)findViewById(R.id.stamina_text);
        user_name = (TextView)findViewById(R.id.user_name);
        user_id = (TextView)findViewById(R.id.user_id);
        athletis_sport_image = (FilterImageView)findViewById(R.id.athletis_sport_image);
        stamina_sport_image = (FilterImageView)findViewById(R.id.stamina_sport_image);
        speedy_sport_image = (FilterImageView)findViewById(R.id.speedy_sport_image);
        course_sport_image = (FilterImageView)findViewById(R.id.course_sport_image);
        course_stamina_sport_image = (RelativeLayout)findViewById(R.id.course_stamina_sport_image);
        course_speedy_sport_image = (RelativeLayout)findViewById(R.id.course_speedy_sport_image);
        course_course_sport_image = (RelativeLayout)findViewById(R.id.course_course_sport_image);
        course_athletis_sport_image = (RelativeLayout)findViewById(R.id.course_athletis_sport_image);

        course_stamina_sport_image.setOnClickListener(this);
        course_speedy_sport_image.setOnClickListener(this);
        course_course_sport_image.setOnClickListener(this);
        course_athletis_sport_image.setOnClickListener(this);
        main_bg = (RelativeLayout)findViewById(R.id.main_bg);
        sex_image = (ImageView)findViewById(R.id.sex_image);
        user_image = (ImageView)findViewById(R.id.user_image);
        user_image.setOnClickListener(this);

        main_bg.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mSelectDialog.show();
                progress.setVisibility(View.GONE);
                ble_name.setText("设置连接跑步机");
                if (ContextCompat.checkSelfPermission(getBaseContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    //需要授权
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mActivity.requestPermissions(
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                1);
                    }
                }
                if (BluetoothUtils.isBluetoothEnabled()){
                    mScanBtn.setChecked(false);
                    //ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }
                return true;
            }
        });

        distance_image = (ImageView)findViewById(R.id.distance_image);
        time_image = (ImageView)findViewById(R.id.time_image);
        heart_image = (ImageView)findViewById(R.id.heart_image);
        exit_login = (ImageView)findViewById(R.id.exit_login);
        ble_connect = (ImageView)findViewById(R.id.ble_connect);
        exit_login.setOnClickListener(this);
        main_bg.setBackgroundResource(R.mipmap.sport_bg);

        if (sex ==1){
            Glide.with(this).load(R.drawable.man).into(sex_image);
        }else {
            Glide.with(this).load(R.drawable.woman).into(sex_image);
        }
        Glide.with(this).load(headimgurl).into(user_image);
        user_name.setText(nickname+"");
        if (systemUserId.length() >6){
            user_id.setText(systemUserId.substring(0,6));
        }
        Glide.with(this).load(R.drawable.connect_no).into(ble_connect);
        Glide.with(this).load(R.drawable.distance).into(distance_image);
        Glide.with(this).load(R.drawable.time).into(time_image);
        Glide.with(this).load(R.drawable.heart).into(heart_image);
        Glide.with(this).load(R.drawable.stamina_aport).into(stamina_sport_image);
        Glide.with(this).load(R.drawable.speedy_sport).into(speedy_sport_image);
        Glide.with(this).load(R.drawable.course).into(course_sport_image);
        Glide.with(this).load(R.drawable.athletics).into(athletis_sport_image);

        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        Typeface typeFace_sport =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/FANGZHENG.TTF");
        distance_number.setTypeface(typeFace);
        distance_type.setTypeface(typeFace);
        time_number.setTypeface(typeFace);
        time_type.setTypeface(typeFace);
        heat_name.setTypeface(typeFace);
        heat_type.setTypeface(typeFace);
        speedy_text.setTypeface(typeFace_sport);
        course_text.setTypeface(typeFace_sport);
        athletics_text.setTypeface(typeFace_sport);
        stamina_text.setTypeface(typeFace_sport);
        //初始化语音合成
        initialEnv();
        initialTts();
        speak("");
        //初始化蓝牙连接
        initBluetooth();

    }

    @Override
    public void initLoad() {
        super.initLoad();
        stamina_sport_image.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        athletis_sport_image.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        //提示佩戴手环
        AdornHintDialog hintDialog = new AdornHintDialog(SportSelectActivity.this);
        hintDialog.show();
        mExitHintDialog = new ExitHintDialog(SportSelectActivity.this);
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
     * 蓝牙连接的初始化
     * */
    private void initBluetooth() {
        //注册蓝牙状态服务
        mLeDeviceListAdapter = new LeDeviceListAdapter();
        ClientManager.getClient().registerBluetoothStateListener(mBluetoothStateListener);
         //显示自定义的Dialog
        mSelectDialog = new BLESelectDialog(this,android.R.style.Theme_DeviceDefault_Dialog_Alert);
        View view = mSelectDialog.getCustomView();
        ble_name = (TextView)view.findViewById(R.id.ble_name);
        ble_list = (ListView) view.findViewById(R.id.ble_list);
        progress = (ProgressBar)view.findViewById(R.id.progressBar1);
        mScanBtn = (ToggleButton)view.findViewById(R.id.tBtn_scan);
        mScanBtn.setChecked(false);
        progress.setVisibility(View.GONE);
        mScanBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked == false){
                    progress.setVisibility(View.GONE);
                    ClientManager.getClient().stopSearch();
                }else {
                    mLeDeviceListAdapter.clear();
                    progress.setVisibility(View.VISIBLE);
                    //初始化蓝牙连接
                    SearchRequest request = new SearchRequest.Builder()
                            .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }

            }
        });

        //连接蓝牙的事件
        ble_list.setAdapter(mLeDeviceListAdapter);
        ble_list.setOnItemClickListener(mOnItemClickListener);


    }


    //蓝牙状态的监听
    private final BluetoothStateListener mBluetoothStateListener = new BluetoothStateListener() {
        @Override
        public void onBluetoothStateChanged(boolean openOrClosed) {
            if (openOrClosed == true){
                Log.e("蓝牙打开了","蓝牙打开了");
            }else {
                Log.e("蓝牙关闭了","蓝牙关闭了");
            }
        }

    };

    //蓝牙扫描的状态监听
    final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            progress.setVisibility(View.VISIBLE);
        }

        @Override
        public void onDeviceFounded(final SearchResult device) {
            if(device.scanRecord == null){
                return;
            }
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (device != null){
                        if (Utils.isEmpty(device.getName()) ==false && device.getName().length()>2){
                            if (ble_name.getText().toString().trim().equals("设置连接跑步机") == true){

                                if (device.getName().substring(0,2).equals("FS") ==true || device.getName().substring(0,2).equals("Qu") ==true || device.getName().substring(0,2).equals("aL") ==true){
                                   Log.e("连接跑步机",device.getName()+"");
                                    mLeDeviceListAdapter.addDevice(device);
                                    mLeDeviceListAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                }
            });

        }

        @Override
        public void onSearchStopped() {

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                    mScanBtn.setChecked(false);
                }
            });
        }

        @Override
        public void onSearchCanceled() {
            Log.e("扫描结束","扫描结束");
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                    mScanBtn.setChecked(false);
                }
            });

        }
    };



    //点击蓝牙连接的列表单击事件，进行蓝牙的连接
    final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final SearchResult device = mLeDeviceListAdapter.getDevice(position);
            if (device == null) return;
            //链接蓝牙
            mDevice = BluetoothUtils.getRemoteDevice(device.getAddress());
            //注册蓝牙链接状态s
            boolean isMAC = stringIsMac(mDevice.getAddress());
            if (isMAC){
                ClientManager.getClient().registerConnectStatusListener(mDevice.getAddress(), mConnectStatusListener);
                ClientManager.getClient().stopSearch();
                connectDevice();
            }else {

            }

        }
    };

    BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {

            if (status == STATUS_CONNECTED) {
                Glide.with(SportSelectActivity.this).load(R.drawable.connect_yes).into(ble_connect);
                Log.e("连接到的mac",mac+"");
                if (mDevice == null){
                    return;
                }

                if (mDevice.getName().substring(0,2).equals("FS") ==true || mDevice.getName().substring(0,2).equals("Qu") ==true || mDevice.getName().substring(0,2).equals("aL") ==true ){
                    paobu = true;
                    paobu_mac = mDevice.getAddress();
                }
                if (paobu ==true){
                    //链接成功
                    SharedPreferences pref = SportSelectActivity.this.getSharedPreferences("MAC",MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("paobu_mac",mDevice.getAddress());
                    editor.putString("paobu_name",mDevice.getName().substring(0,2));
                    editor.commit();
                    //Toast.makeText(SportSelectActivity.this,"连接跑步机成功,点击屏幕取消扫描",Toast.LENGTH_LONG).show();
                    mScanBtn.setChecked(false);
                    //mSelectDialog.dismiss();
                }else if (paobu ==false && shouhuan ==true){

                }

            } else if (status == STATUS_DISCONNECTED) {
                Glide.with(SportSelectActivity.this).load(R.drawable.connect_no).into(ble_connect);
                // showErrorWithStatus("连接失败");
                Log.e("连接断开重新连接","重新连接断开了");
                Log.e("跑步的mac",""+paobu_mac);
                Log.e("手环的mac",""+shouhuan_mac);
                Log.e("当前断开的mac",""+mac);
                BleConnectOptions options = new BleConnectOptions.Builder()
                        .setConnectRetry(3)
                        .setConnectTimeout(15000)
                        .setServiceDiscoverRetry(3)
                        .setServiceDiscoverTimeout(10000)
                        .build();

                ClientManager.getClient().connect(paobu_mac,options, new BleConnectResponse() {
                    @Override
                    public void onResponse(int code, BleGattProfile profile) {

                        if (code == REQUEST_SUCCESS) {
                            // mAdapter.setGattProfile(profile);
                        }
                    }
                });

            }

        }
    };

    //链接蓝牙的模块
    private void connectDevice() {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(15000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(mDevice.getAddress(),options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {

                if (code == REQUEST_SUCCESS) {
                    // mAdapter.setGattProfile(profile);
                }
            }
        });
    }


    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tips.add("飞跑通知第" + i+"条");
        }
        return tips;
    }

    @Override
    protected void onPause() {
        this.mSpeechSynthesizer.pause();
        super.onPause();
        ClientManager.getClient().stopSearch();
    }

    @Override
    protected void onResume() {
        this.mSpeechSynthesizer.resume();
        super.onResume();
        SharedPreferences pref = getSharedPreferences("MAC",MODE_WORLD_READABLE);
        String mac = pref.getString("paobu_mac","");//第二个参数为默认值
        if (mac == null){
            return;
        }
        if (mac.length() >0){
            int status = ClientManager.getClient().getConnectStatus(mac);
            if (status ==2){
                Glide.with(SportSelectActivity.this).load(R.drawable.connect_yes).into(ble_connect);
            }else {
                ClientManager.getClient().registerConnectStatusListener(mac, mConnectStatusListener);
                BleConnectOptions options = new BleConnectOptions.Builder()
                        .setConnectRetry(3)
                        .setConnectTimeout(15000)
                        .setServiceDiscoverRetry(3)
                        .setServiceDiscoverTimeout(10000)
                        .build();

                ClientManager.getClient().connect(mac,options, new BleConnectResponse() {
                    @Override
                    public void onResponse(int code, BleGattProfile profile) {

                        if (code == REQUEST_SUCCESS) {
                            // mAdapter.setGattProfile(profile);
                        }
                    }
                });
                Glide.with(SportSelectActivity.this).load(R.drawable.connect_no).into(ble_connect);
            }
        }

        //查询用户信息
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<UserBean> call = service.postUserMessage(systemUserId);
        call.enqueue(this);


    }

    @Override
    protected void onStop() {
        this.mSpeechSynthesizer.stop();
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        this.mSpeechSynthesizer.release();

        super.onDestroy();
    }
    /**
     * 点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //在线竞技
            case R.id.course_athletis_sport_image:
               startActivity(new Intent(getBaseContext(),SportAthleticsActivity.class));
                break;
            //体能测试
            case R.id.course_stamina_sport_image:
                startActivity(new Intent(getBaseContext(), EnduranTestActivity.class));
                break;
            //快捷运动
            case R.id.course_speedy_sport_image:
                startActivity(new Intent(getBaseContext(),SpeedySportActivity.class));
                break;
            //课程选择的界面
            case R.id.course_course_sport_image:
                startActivity(new Intent(getBaseContext(),CourseSelectActivity.class));
                break;
            //退出登录
            case R.id.exit_login:
                mExitHintDialog.show();
                //提示佩戴手环
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitHintDialog.dismiss();
                        UserInfo userInfo = new UserInfo("","","",0,"");
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(SportSelectActivity.this, MainActivity.class));
                    }
                },5000);
                break;
            //修改用户的信息
            case R.id.user_image:
                //更新用户信息
                startActivity(new Intent(SportSelectActivity.this, EditUserInfoActivity.class));
                break;

        }
    }

    @Override
    public void onBackPressed() {
        mExitHintDialog.show();
        //提示佩戴手环
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mExitHintDialog.dismiss();
                UserInfo userInfo = new UserInfo("","","",0,"");
                AppManager.getAppManager().finishAllActivity();
                startActivity(new Intent(SportSelectActivity.this, MainActivity.class));
            }
        },5000);
    }

    /**
     * 百度语音功能实现
     * */
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

    /**
     * 将sample工程需要的资源文件拷贝到SD卡中使用（授权文件为临时授权文件，请注册正式授权）
     *
     * @param isCover 是否覆盖已存在的目标文件
     * @param source
     * @param dest
     */
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
        this.mSpeechSynthesizer.setAppId("9420554"/*这里只是为了让Demo运行使用的APPID,请替换成自己的id。*/);
        // 请替换为语音开发者平台注册应用得到的apikey和secretkey (在线授权)
        this.mSpeechSynthesizer.setApiKey("BM2xomRopS2voE4hvSyaRcs1",
                "kTxX9EGjoehIapSFKhg5cO1eR2jMXHsa"/*这里只是为了让Demo正常运行使用APIKey,请替换成自己的APIKey*/);
        // 发音人（在线引擎），可用参数为0,1,2,3。。。（服务器端会动态增加，各值含义参考文档，以文档说明为准。0--普通女声，1--普通男声，2--特别男声，3--情感男声。。。）
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_SPEAKER, "0");
        // 设置Mix模式的合成策略
        this.mSpeechSynthesizer.setParam(SpeechSynthesizer.PARAM_MIX_MODE, SpeechSynthesizer.MIX_MODE_DEFAULT);
        // 授权检测接口(只是通过AuthInfo进行检验授权是否成功。)
        // AuthInfo接口用于测试开发者是否成功申请了在线或者离线授权，如果测试授权成功了，可以删除AuthInfo部分的代码（该接口首次验证时比较耗时），不会影响正常使用（合成使用时SDK内部会自动验证授权）
        /*AuthInfo authInfo = this.mSpeechSynthesizer.auth(TtsMode.MIX);

        if (authInfo.isSuccess()) {
            Log.e(TAG,"auth success");
        } else {
            String errorMsg = authInfo.getTtsError().getDetailMessage();
            Log.e(TAG,"auth failed errorMsg=" + errorMsg);
        }*/

        // 初始化tts
        mSpeechSynthesizer.initTts(TtsMode.MIX);
        // 加载离线英文资源（提供离线英文合成功能）
        int result =
                mSpeechSynthesizer.loadEnglishModel(mSampleDirPath + "/" + ENGLISH_TEXT_MODEL_NAME, mSampleDirPath
                        + "/" + ENGLISH_SPEECH_FEMALE_MODEL_NAME);
        Log.e(TAG,"loadEnglishModel result=" + result);

        //打印引擎信息和model基本信息
        printEngineInfo();
    }


    /**
     * 打印引擎so库版本号及基本信息和model文件的基本信息
     */
    private void printEngineInfo() {
        log.e(TAG,"EngineVersioin=" + SynthesizerTool.getEngineVersion());
        log.e(TAG,"EngineInfo=" + SynthesizerTool.getEngineInfo());
        String textModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + TEXT_MODEL_NAME);
        log.e(TAG,"textModelInfo=" + textModelInfo);
        String speechModelInfo = SynthesizerTool.getModelInfo(mSampleDirPath + "/" + SPEECH_FEMALE_MODEL_NAME);
        log.e(TAG,"speechModelInfo=" + speechModelInfo);
    }

    //播放语音
    private void speak(String text) {
        //需要合成的文本text的长度不能超过1024个GBK字节。
        if (TextUtils.isEmpty(text)) {
            text = "欢迎来到飞跑智能运动平台。";
        }
        int result = this.mSpeechSynthesizer.speak(text);
        if (result < 0) {
            Log.e(TAG,"error,please look up error code in doc or URL:http://yuyin.baidu.com/docs/tts/122 ");
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

    @Override
    public void onResponse(Call<UserBean> call, Response<UserBean> response) {
        UserBean bean = response.body();
        if (bean == null){
            return;
        }

        if (response == null){
            return;
        }

        switch (bean.getCode()){
            case 5121:
                    UserInfo.age = bean.getContent().getAge();
                    UserInfo.weight = bean.getContent().getWeight();
                    UserInfo.height = bean.getContent().getHeight();
                    UserInfo.sumTime = bean.getContent().getSumTime();
                    UserInfo.sumLength = bean.getContent().getSumLength();
                    UserInfo.sumCalorie = bean.getContent().getSumCalorie();
                    distance_number.setText(bean.getContent().getSumLength()+"");
                    time_number.setText(bean.getContent().getSumTime()+"");
                    heat_name.setText(bean.getContent().getSumCalorie()+"");
                break;
            default:
                showErrorWithStatus(bean.getMessage());
                break;
        }
    }

    @Override
    public void onFailure(Call<UserBean> call, Throwable t) {
            showErrorWithStatus("网络异常");
    }

    /**
     * 蓝牙扫描之后的列表数据适配器
     * */

    /**
     * 扫描蓝牙数据适配器
     * */

    private class LeDeviceListAdapter extends BaseAdapter {
        private ArrayList<SearchResult> mLeDevices;
        private LayoutInflater mInflator;

        public LeDeviceListAdapter() {
            mLeDevices = new ArrayList<SearchResult>();
            mInflator = mActivity.getLayoutInflater();
        }

        public void addDevice(SearchResult device) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device);
            }
        }
        public SearchResult getDevice(int position) {
            return mLeDevices.get(position);
        }
        public void clear() {
            mLeDevices.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mLeDevices.size();
        }

        @Override
        public SearchResult getItem(int i) {
            return mLeDevices.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder;

            if (view == null) {
                view = mInflator.inflate(R.layout.ble_listitem_device, null);
                viewHolder = new ViewHolder();
                viewHolder.deviceAddress = (TextView) view.findViewById(R.id.device_address);
                viewHolder.deviceName = (TextView) view.findViewById(R.id.device_name);
                viewHolder.deviceRssi = (TextView) view.findViewById(R.id.txt_rssi);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }

            SearchResult device = mLeDevices.get(i);
            final String deviceName = device.getName();
            final int deviceRssi = device.rssi;
            if (deviceName != null && deviceName.length() > 0) {
                viewHolder.deviceName.setText("名称" + deviceName);
                viewHolder.deviceRssi.setText("Rss" + deviceRssi);
            } else {
                viewHolder.deviceName.setText("未知的蓝牙设备");
                viewHolder.deviceAddress.setText("");
            }

            return view;
        }
    }
    private static class ViewHolder {
        TextView deviceName;
        TextView deviceAddress;
        TextView deviceRssi;
    }


}
