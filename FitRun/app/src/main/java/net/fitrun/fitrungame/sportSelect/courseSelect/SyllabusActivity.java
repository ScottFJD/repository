package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bigkoo.svprogresshud.SVProgressHUD;
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
import com.skyfishjy.library.RippleBackground;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.Bluetoothservice.BLESelectDialog;
import net.fitrun.fitrungame.Bluetoothservice.ClientManager;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.speedy.SpeedSportUIActivity;
import net.fitrun.fitrungame.sportSelect.speedy.SpeedySportActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static net.fitrun.fitrungame.R.id.pb_progressbar;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.name;


/**
 * Created by 晁东洋 on 2017/4/17.
 * 查看课程表的页面
 */

public class SyllabusActivity extends BaseActivity implements View.OnClickListener, Callback<CourseTable> {
    private String courseID;
    private ImageView back,go_training;
    private TextView syllabus_title,validTime,sumTime,tab_progess,length
            ,progess_content;
    private ProgressBar progressBar_course;

    private  TextView monday,tuesday,wed,thursday,friday,saturday,sunday
            ,monday_data,tuesday_data,wed_data,thursday_data,friday_data,
            saturday_data,sunday_data;
    private ImageView couser_arraw;

    private RelativeLayout no_course,has_course;
    private TextView has_course_title,has_course_four,has_course_three,has_course_two
            ,has_course_one;

    //有课程的集合
    List<Integer> isCourse ;
    //今天周几
    int s;
    private static final int FIRST_DAY = Calendar.MONDAY;

    /**
     * 连接蓝牙的设置
     * */
    //课程的名字
    private String courseName = "";
    //课程的时长
    private int courseLength;
    //开始速度
    private int startSpeed;
    //最低心率
    private int minHeart;
    //最高心率
    private int maxHeart;
    //最高速度
    private int  topSpeed;

    //扫描心率
    RippleBackground rippleBackground;
    FrameLayout search_ble;


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

    //初始课程的信息
    StartSportBean   bean;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_syllabus;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        Intent intent=getIntent();
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        syllabus_title = (TextView)findViewById(R.id.syllabus_title);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        syllabus_title.setTypeface(typeFace_jan);
        go_training = (ImageView)findViewById(R.id.go_training);
        go_training.setOnClickListener(this);
        go_training.setVisibility(View.GONE);
        validTime = (TextView)findViewById(R.id.validTime);
        sumTime = (TextView)findViewById(R.id.sumTime);
        tab_progess = (TextView)findViewById(R.id.tab_progess);
        length = (TextView)findViewById(R.id.length);
        progess_content = (TextView)findViewById(R.id.progess_content);
        progressBar_course = (ProgressBar)findViewById(R.id.progressBar_course);
        couser_arraw = (ImageView)findViewById(R.id.couser_arraw);



        monday = (TextView)findViewById(R.id.monday);
        tuesday = (TextView)findViewById(R.id.tuesday);
        wed = (TextView)findViewById(R.id.wed);
        thursday = (TextView)findViewById(R.id.thursday);
        friday = (TextView)findViewById(R.id.friday);
        saturday = (TextView)findViewById(R.id.saturday);
        sunday = (TextView)findViewById(R.id.sunday);

        monday_data = (TextView)findViewById(R.id.monday_data);
        tuesday_data = (TextView)findViewById(R.id.tuesday_data);
        wed_data = (TextView)findViewById(R.id.wed_data);
        thursday_data = (TextView)findViewById(R.id.thursday_data);
        friday_data = (TextView)findViewById(R.id.friday_data);
        saturday_data = (TextView)findViewById(R.id.saturday_data);
        sunday_data = (TextView)findViewById(R.id.sunday_data);

        //日期的单击事件
        monday_data.setOnClickListener(this);
        tuesday_data.setOnClickListener(this);
        wed_data.setOnClickListener(this);
        thursday_data.setOnClickListener(this);
        friday_data.setOnClickListener(this);
        saturday_data.setOnClickListener(this);
        sunday_data.setOnClickListener(this);

        //有没有课程
        no_course = (RelativeLayout)findViewById(R.id.no_course);
        has_course = (RelativeLayout)findViewById(R.id.no_course);
        has_course_title = (TextView)findViewById(R.id.has_course_title);
        has_course_one = (TextView)findViewById(R.id.has_course_one);
        has_course_two = (TextView)findViewById(R.id.has_course_two);
        has_course_three = (TextView)findViewById(R.id.has_course_three);
        has_course_four = (TextView)findViewById(R.id.has_course_four);

        //连接蓝牙的动画
        rippleBackground=(RippleBackground)findViewById(R.id.content);
        rippleBackground.startRippleAnimation();
        search_ble = (FrameLayout)findViewById(R.id.search_ble);
        search_ble.setVisibility(View.GONE);
        SharedPreferences pref = getSharedPreferences("MAC",MODE_WORLD_READABLE);
        String  name = pref.getString("shouhuan_mac","");//第二个参数为默认值
        if (name != null){
            if (name.length() >0){
                ClientManager.getClient().disconnect(name);
            }
        }

    }

    @Override
    public void initLoad() {
        super.initLoad();
        // 初始化可选日期
        initData();

        //初始化蓝牙连接
        initBluetooth();

        //查询此次课程运动的数据
        querycourse();
    }

    //查询课程初始运动数据
    private void querycourse(){
        //查询本次课程的初始运动数据
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<StartSportBean> call = service.getStartSport(UserInfo.systemUserId);
        call.enqueue(new Callback<StartSportBean>() {
            @Override
            public void onResponse(Call<StartSportBean> call, Response<StartSportBean> response) {
                if (response != null){
                    bean = response.body();
                    if (bean == null){
                        go_training.setVisibility(View.GONE);
                        return;
                    }
                    switch (bean.getCode()){
                        case 4868:
                            go_training.setVisibility(View.VISIBLE);
                            courseLength = bean.getContent().getLength();
                            //初始运动数据
                            startSpeed = bean.getContent().getStartSpeed();
                            minHeart = bean.getContent().getMinHeartRate();
                            maxHeart = bean.getContent().getMaxHeartRate();
                            topSpeed = bean.getContent().getTopSpeed();
                            has_course_one.setText("1,热身"+courseLength * 5 / 100+"分钟");
                            has_course_two.setText("2,快走"+courseLength * 5 / 100+"分钟");
                            has_course_three.setText("3,正式跑步"+courseLength * 90 / 100+"分钟");
                            has_course_four.setText("4,放松调整"+courseLength * 10 / 100+"分钟");
                            break;
                        default:
                            go_training.setVisibility(View.GONE);
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<StartSportBean> call, Throwable t) {
                go_training.setVisibility(View.GONE);
            }
        });
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
                }else {
                    progress.setVisibility(View.VISIBLE);

                }

            }
        });

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
            search_ble.setVisibility(View.VISIBLE);
            Log.e("扫描开始","扫描开始");
        }

        @Override
        public void onDeviceFounded(final SearchResult device) {
            if(device.scanRecord == null){
                return;
            }
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("扫描连接蓝牙地址是空",device+"");
                    if (device != null){
                        if (Utils.isEmpty(device.getName()) ==false){
                            if (device.getName().substring(0,2).equals("FI") ==true ){
                                Log.e("扫描连接蓝牙",device.getName()+"信号的强度"+device.rssi);
                                if (device.rssi > -65){
                                    //开始连接
                                    ClientManager.getClient().stopSearch();
                                    search_ble.setVisibility(View.GONE);
                                    //链接蓝牙
                                    if (Utils.isEmail(device.getAddress()) == true){
                                        return;
                                    }
                                    //链接蓝牙
                                    mDevice = BluetoothUtils.getRemoteDevice(device.getAddress());
                                    boolean isMAC = stringIsMac(device.getAddress());
                                    if (isMAC){
                                        ClientManager.getClient().registerConnectStatusListener(device.getAddress(), mConnectStatusListener);
                                        connectDevice(device.getAddress());
                                    }else {

                                    }

                                }

                            }
                        }
                    }
                }
            });

        }

        @Override
        public void onSearchStopped() {
            Log.e("扫描停止","扫描停止");
            search_ble.setVisibility(View.GONE);
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
            Log.e("扫描关闭","扫描关闭");
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                    mScanBtn.setChecked(false);
                }
            });

        }
    };


    //链接蓝牙的模块
    private void connectDevice(String address) {
        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(15000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(address,options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {

                if (code == REQUEST_SUCCESS) {
                    // mAdapter.setGattProfile(profile);
                }
            }
        });
    }

    BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (courseName.length() >1){
                if (status == STATUS_CONNECTED) {
                    Log.e("连接到的mac",mac+"");
                    Log.e("点击的到的mac",mac+"连接成功");

                    if (mDevice.getName().substring(0,2).equals("FI") ==true){
                        shouhuan = true;
                        shouhuan_mac = mDevice.getAddress();
                    }
                    if (shouhuan ==true){
                        //链接成功
                        SharedPreferences pref = SyllabusActivity.this.getSharedPreferences("MAC",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("shouhuan_mac",shouhuan_mac);
                        editor.commit();
                        Log.e("快捷界面在执行wwww",courseName);
                        Intent intent = new Intent(SyllabusActivity.this,CourseSportUIActivity.class);
                        intent.putExtra("courseName",courseName);
                        intent.putExtra("courseLength",courseLength+"");
                        intent.putExtra("startSpeed",startSpeed+"");
                        intent.putExtra("minHeart",minHeart+"");
                        intent.putExtra("maxHeart",maxHeart+"");
                        intent.putExtra("topSpeed",topSpeed+"");
                        startActivity(intent);
                        courseName = "";
                        finish();
                    }else if (paobu ==false && shouhuan ==true){

                    }

                } else if (status == STATUS_DISCONNECTED) {
                    // showErrorWithStatus("连接失败");
                }
            }


        }
    };



    private static void setToFirstDay(Calendar calendar){
        while (calendar.get(Calendar.DAY_OF_WEEK) != FIRST_DAY){
            calendar.add(Calendar.DATE,-1);
        }
    }

    private void printWeekdays() {
        Calendar calendar = Calendar.getInstance();
        setToFirstDay(calendar);
        for (int i = 0; i < 7; i++){
            printDay(calendar);
            calendar.add(Calendar.DATE,1);
        }
    }


    private void initData() {
        has_course.setVisibility(View.GONE);
        no_course.setVisibility(View.VISIBLE);
        printWeekdays();
        s = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
        Log.e("今天周几",s+"");
        switch (s){
            case 1:
                monday.setText("今天");
                setLayout(couser_arraw,35,0);
                monday.setTextColor(getResources().getColor(R.color.white));
                monday_data.setTextColor(getResources().getColor(R.color.black));
                monday_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 2:
                tuesday.setText("今天");
                setLayout(couser_arraw,169,0);
                tuesday.setTextColor(getResources().getColor(R.color.white));
                tuesday_data.setTextColor(getResources().getColor(R.color.black));
                tuesday_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 3:
                wed.setText("今天");
                setLayout(couser_arraw,309,0);
                wed.setTextColor(getResources().getColor(R.color.white));
                wed_data.setTextColor(getResources().getColor(R.color.black));
                wed_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 4:
                thursday.setText("今天");
                RelativeLayout.LayoutParams labelParams = (RelativeLayout.LayoutParams)couser_arraw.getLayoutParams();
                labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // 设置水平居中
                labelParams.addRule(RelativeLayout.CENTER_VERTICAL, 0); // 取消垂直居中
                couser_arraw.setLayoutParams(labelParams);
                thursday.setTextColor(getResources().getColor(R.color.white));
                thursday_data.setTextColor(getResources().getColor(R.color.black));
                thursday_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 5:
                friday.setText("今天");
                setLayout(couser_arraw,578,0);
                friday.setTextColor(getResources().getColor(R.color.white));
                friday_data.setTextColor(getResources().getColor(R.color.black));
                friday_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 6:
                saturday.setText("今天");
                setLayout(couser_arraw,712,0);
                saturday.setTextColor(getResources().getColor(R.color.white));
                saturday_data.setTextColor(getResources().getColor(R.color.black));
                saturday_data.setBackgroundResource(R.drawable.round_text);
                break;
            case 0:
                sunday.setText("今天");
                setLayout(couser_arraw,847,0);
                sunday.setTextColor(getResources().getColor(R.color.white));
                sunday_data.setTextColor(getResources().getColor(R.color.black));
                sunday_data.setBackgroundResource(R.drawable.round_text);
                break;
        }
    }

    private void printDay(Calendar calendar) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd EE");
        String date_a = dateFormat.format(calendar.getTime());
        String date_wek = date_a.substring(date_a.length()-2);
        String date = date_a.substring(6,8);
        Log.e("date","获得的结果是");
        switch (date_wek){
            case "周一":
                monday_data.setText(date);
                break;
            case "周二":
                tuesday_data.setText(date);
                break;
            case "周三":
                wed_data.setText(date);
                break;
            case "周四":
                thursday_data.setText(date);
                break;
            case "周五":
                friday_data.setText(date);
                break;
            case "周六":
                saturday_data.setText(date);
                break;
            case "周日":
                sunday_data.setText(date);
                break;


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.go_training:
                name = "课程";
                //开始进入课程界面训练
                ble_name.setText("设置连接蓝牙手环");
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
                    //初始化蓝牙连接
                    SearchRequest request = new SearchRequest.Builder()
                            .searchBluetoothLeDevice(3000, 10)   // 先扫BLE设备3次，每次10s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }
                break;

            case R.id.monday_data:
                int number = Integer.parseInt(monday_data.getText().toString().trim());
                setLayout(couser_arraw,35,0);
                if (isCourse.contains(number) == true){
                    has_course.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (monday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                    has_course.setVisibility(View.GONE);
                    no_course.setVisibility(View.VISIBLE);
                    go_training.setVisibility(View.GONE);
                }

                break;
            case R.id.tuesday_data:
                setLayout(couser_arraw,169,0);
                int number_tu = Integer.parseInt(tuesday_data.getText().toString().trim());
                if (isCourse.contains(number_tu) == true){
                    has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (tuesday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                    has_course.setVisibility(View.GONE);
                    has_course_title.setVisibility(View.GONE);
                    has_course_one.setVisibility(View.GONE);
                    has_course_two.setVisibility(View.GONE);
                    has_course_three.setVisibility(View.GONE);
                    has_course_four.setVisibility(View.GONE);
                    no_course.setVisibility(View.VISIBLE);
                    go_training.setVisibility(View.GONE);
                }
                break;
            case R.id.wed_data:
                setLayout(couser_arraw,309,0);
                int number_wed = Integer.parseInt(wed_data.getText().toString().trim());
                if (isCourse.contains(number_wed) == true){
                    has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (wed.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                    has_course.setVisibility(View.GONE);
                    has_course_title.setVisibility(View.GONE);
                    has_course_one.setVisibility(View.GONE);
                    has_course_two.setVisibility(View.GONE);
                    has_course_three.setVisibility(View.GONE);
                    has_course_four.setVisibility(View.GONE);
                    no_course.setVisibility(View.VISIBLE);
                    go_training.setVisibility(View.GONE);
                }
                break;
            case R.id.thursday_data:
                RelativeLayout.LayoutParams labelParams = (RelativeLayout.LayoutParams)couser_arraw.getLayoutParams();
                labelParams.addRule(RelativeLayout.CENTER_HORIZONTAL); // 设置水平居中
                labelParams.addRule(RelativeLayout.CENTER_VERTICAL, 0); // 取消垂直居中
                couser_arraw.setLayoutParams(labelParams);
                int number_thursday = Integer.parseInt(thursday_data.getText().toString().trim());
                if (isCourse.contains(number_thursday) == true){
                    has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (thursday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                     has_course.setVisibility(View.GONE);
                        has_course_title.setVisibility(View.GONE);
                        has_course_one.setVisibility(View.GONE);
                        has_course_two.setVisibility(View.GONE);
                        has_course_three.setVisibility(View.GONE);
                        has_course_four.setVisibility(View.GONE);
                     no_course.setVisibility(View.VISIBLE);
                     go_training.setVisibility(View.GONE);
                }
                break;
            case R.id.friday_data:
                setLayout(couser_arraw,578,0);
                int number_friday = Integer.parseInt(friday_data.getText().toString().trim());
                if (isCourse.contains(number_friday) == true){
                    has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (friday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                    has_course.setVisibility(View.GONE);
                    has_course_title.setVisibility(View.GONE);
                    has_course_one.setVisibility(View.GONE);
                    has_course_two.setVisibility(View.GONE);
                    has_course_three.setVisibility(View.GONE);
                    has_course_four.setVisibility(View.GONE);
                    no_course.setVisibility(View.VISIBLE);
                    go_training.setVisibility(View.GONE);
                }
                break;
            case R.id.saturday_data:
                setLayout(couser_arraw,712,0);
                int number_saturday = Integer.parseInt(saturday_data.getText().toString().trim());
                if (isCourse.contains(number_saturday) == true){
                    has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                    no_course.setVisibility(View.GONE);
                    if (saturday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                     has_course.setVisibility(View.GONE);
                    has_course_title.setVisibility(View.GONE);
                    has_course_one.setVisibility(View.GONE);
                    has_course_two.setVisibility(View.GONE);
                    has_course_three.setVisibility(View.GONE);
                    has_course_four.setVisibility(View.GONE);
                     no_course.setVisibility(View.VISIBLE);
                     go_training.setVisibility(View.GONE);
                }
                break;
            case R.id.sunday_data:
                setLayout(couser_arraw,847,0);
                int number_sunday = Integer.parseInt(sunday_data.getText().toString().trim());
                if (isCourse.contains(number_sunday) == true){
                      has_course.setVisibility(View.VISIBLE);
                    has_course_title.setVisibility(View.VISIBLE);
                    has_course_one.setVisibility(View.VISIBLE);
                    has_course_two.setVisibility(View.VISIBLE);
                    has_course_three.setVisibility(View.VISIBLE);
                    has_course_four.setVisibility(View.VISIBLE);
                      no_course.setVisibility(View.GONE);
                    if (sunday.getText().toString().trim().equals("今天") == true){
                        go_training.setVisibility(View.VISIBLE);
                    }else {
                        go_training.setVisibility(View.GONE);
                    }
                }else {
                      has_course.setVisibility(View.GONE);
                    has_course_title.setVisibility(View.GONE);
                    has_course_one.setVisibility(View.GONE);
                    has_course_two.setVisibility(View.GONE);
                    has_course_three.setVisibility(View.GONE);
                    has_course_four.setVisibility(View.GONE);
                      no_course.setVisibility(View.VISIBLE);
                      go_training.setVisibility(View.GONE);
                }
                break;
        }
    }

    /* 
     * 设置控件所在的位置YY，并且不改变宽高， 
     * XY为绝对位置 
     */
    public static void setLayout(View view,int x,int y){
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, x+margin.width, y+ margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<CourseTable> call = service.postCourseTable(UserInfo.systemUserId);
        call.enqueue(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        rippleBackground.stopRippleAnimation();
        ClientManager.getClient().unregisterConnectStatusListener(shouhuan_mac, mConnectStatusListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResponse(Call<CourseTable> call, Response<CourseTable> response) {
        CourseTable courseTable = response.body();
        if (courseTable == null){
            return;

        }
        if (courseTable.getContent() == null){
            return;
        }
        switch (courseTable.getCode()){
            case 4869:
                courseName = courseTable.getContent().getCourseName();
                go_training.setVisibility(View.VISIBLE);
                syllabus_title.setText(courseTable.getContent().getCourseName());
                if (courseTable.getContent().getValidTime() <=0){
                    validTime.setText("0");
                }else {
                    validTime.setText(courseTable.getContent().getValidTime()+"");
                }

                sumTime.setText(courseTable.getContent().getSumTime()+"");
                int valid = courseTable.getContent().getValidTime();
                if (valid <= 0){
                    valid =0;
                }else {

                }
                int sumtime = courseTable.getContent().getSumTime();
                float proges = (float)valid/sumtime * 100;
                int p = (int) proges;
                tab_progess.setText(p + "%");
                length.setText(courseTable.getContent().getLength()+"");
                progess_content.setText(courseTable.getContent().getInformation());
                progressBar_course.setMax(courseTable.getContent().getSumTime());
                progressBar_course.setProgress(valid);
                List<CourseTable.ContentBean.DayExerciseMsgsBean> dayExerciseMsgs= courseTable.getContent().getDayExerciseMsgs();
                isCourse = new ArrayList<>();
                int currentData = 1;
                for (int x=0; x<dayExerciseMsgs.size();x++) {
                    if (dayExerciseMsgs.get(x).isCurrentDay()==true)
                    currentData = dayExerciseMsgs.get(x).getDayNo();
                }
                for (int x=0; x<dayExerciseMsgs.size();x++){
                    if (dayExerciseMsgs.get(x).isValid() ==true){
                        isCourse.add(dayExerciseMsgs.get(x).getDayNo());
                    }
                    if (dayExerciseMsgs.get(x).isCurrentDay()==true ){
                        if (dayExerciseMsgs.get(x).isValid() ==true){
                            has_course.setVisibility(View.VISIBLE);
                            has_course_title.setVisibility(View.VISIBLE);
                            has_course_one.setVisibility(View.VISIBLE);
                            has_course_two.setVisibility(View.VISIBLE);
                            has_course_three.setVisibility(View.VISIBLE);
                            has_course_four.setVisibility(View.VISIBLE);
                            no_course.setVisibility(View.GONE);
                            go_training.setVisibility(View.VISIBLE);
                        }else {
                            has_course.setVisibility(View.GONE);
                            has_course_title.setVisibility(View.GONE);
                            has_course_one.setVisibility(View.GONE);
                            has_course_two.setVisibility(View.GONE);
                            has_course_three.setVisibility(View.GONE);
                            has_course_four.setVisibility(View.GONE);
                            no_course.setVisibility(View.VISIBLE);
                            go_training.setVisibility(View.GONE);
                        }
                    }
                    //显示去训练的按钮
                    //has_set_course.setVisibility(View.VISIBLE);
                    if (dayExerciseMsgs.get(x).isValid() ==true){
                        switch (x){
                            case 0:
                                Log.e("第一天的日期",dayExerciseMsgs.get(x).getDayNo()+"当天的日期"+currentData);
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    monday_data.setTextColor(getResources().getColor(R.color.black));
                                    monday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    monday_data.setTextColor(getResources().getColor(R.color.black));
                                    monday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    monday_data.setTextColor(getResources().getColor(R.color.black));
                                    monday_data.setBackgroundResource(R.drawable.round_text);
                                }
                                break;
                            case 1:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    tuesday_data.setTextColor(getResources().getColor(R.color.black));
                                    tuesday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    tuesday_data.setTextColor(getResources().getColor(R.color.black));
                                    tuesday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    tuesday_data.setTextColor(getResources().getColor(R.color.black));
                                    tuesday_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;
                            case 2:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    wed_data.setTextColor(getResources().getColor(R.color.black));
                                    wed_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    wed_data.setTextColor(getResources().getColor(R.color.black));
                                    wed_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    wed_data.setTextColor(getResources().getColor(R.color.black));
                                    wed_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;
                            case 3:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    thursday_data.setTextColor(getResources().getColor(R.color.black));
                                    thursday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    thursday_data.setTextColor(getResources().getColor(R.color.black));
                                    thursday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    thursday_data.setTextColor(getResources().getColor(R.color.black));
                                    thursday_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;
                            case 4:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    friday_data.setTextColor(getResources().getColor(R.color.black));
                                    friday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    friday_data.setTextColor(getResources().getColor(R.color.black));
                                    friday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    friday_data.setTextColor(getResources().getColor(R.color.black));
                                    friday_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;
                            case 5:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    saturday_data.setTextColor(getResources().getColor(R.color.black));
                                    saturday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    saturday_data.setTextColor(getResources().getColor(R.color.black));
                                    saturday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    saturday_data.setTextColor(getResources().getColor(R.color.black));
                                    saturday_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;
                            case 6:
                                if (dayExerciseMsgs.get(x).getDayNo() < currentData){
                                    sunday_data.setTextColor(getResources().getColor(R.color.black));
                                    sunday_data.setBackgroundResource(R.drawable.round_gray_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() > currentData){
                                    sunday_data.setTextColor(getResources().getColor(R.color.black));
                                    sunday_data.setBackgroundResource(R.drawable.round_ff_text);
                                }else if (dayExerciseMsgs.get(x).getDayNo() == currentData){
                                    sunday_data.setTextColor(getResources().getColor(R.color.black));
                                    sunday_data.setBackgroundResource(R.drawable.round_text);
                                }

                                break;

                        }
                    }
                }
                break;
            default:
                showErrorWithStatus(courseTable.getMessage());
                break;
        }
    }

    @Override
    public void onFailure(Call<CourseTable> call, Throwable t) {

    }
}
