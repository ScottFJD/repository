package net.fitrun.fitrungame.sportSelect.speedy;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
import com.skyfishjy.library.RippleBackground;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.Bluetoothservice.BLESelectDialog;
import net.fitrun.fitrungame.Bluetoothservice.ClientManager;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.ExitHintDialog;
import net.fitrun.fitrungame.sportSelect.SportSelectActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.UserBean;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.view.FilterImageView;
import net.fitrun.fitrungame.view.TipViewNoImage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;
import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;
import static com.inuker.bluetooth.library.Constants.STATUS_DISCONNECTED;
import static net.fitrun.fitrungame.R.id.ble_name;
import static net.fitrun.fitrungame.R.id.distance_number;
import static net.fitrun.fitrungame.R.id.heat_name;
import static net.fitrun.fitrungame.R.id.time_number;
import static net.fitrun.fitrungame.greendao.gen.UserSportInfoDao.Properties.Speed;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.name;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.systemUserId;


/**
 * Created by 晁东洋 on 2017/3/21.
 * 快捷运动选择
 */

public class SpeedySportActivity extends BaseActivity implements View.OnClickListener, Callback<UserBean> {
    private TipViewNoImage fit_notif,palt_notif;
    private ImageView exit_login,back,user_image,speedy_distance_image,speedy_time_image,speedy_heart_image;
    private TextView speedy_distance_number,speedy_distance_type,speedy_time_number,speedy_time_type
            ,speedy_heat_name,speedy_heat_type,stamina_text,speedy_text,course_text,athletics_text;
    private FilterImageView stamina_sport_image,speedy_sport_image,course_sport_image,athletis_sport_image;

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
    //课程的名字
    private String courseName;
    //课程的时长
    private int courseLength;
    //开始速度
    private int startSpeed;
    //最低心率
    private int minHeart;
    //最高心率
    private int maxHeart;
    //最高速度
    private int topSpeed = 0;

    //扫描心率
    RippleBackground rippleBackground;
    FrameLayout search_ble;

    private ImageView new_speedy_warm,new_sport_relax,new_lose_weight,new_competitive;

    //退出的提示
    ExitHintDialog mExitHintDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_speed_sport;
    }

    @Override
    public void initView() {
        super.initView();
        mActivity = this;
        fit_notif = (TipViewNoImage)findViewById(R.id.fit_notif);
        palt_notif = (TipViewNoImage)findViewById(R.id.palt_notif);
        back = (ImageView)findViewById(R.id.back);
        exit_login = (ImageView)findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);
        user_image = (ImageView)findViewById(R.id.user_image);
        speedy_distance_image = (ImageView)findViewById(R.id.speedy_distance_image);
        speedy_time_image = (ImageView)findViewById(R.id.speedy_time_image);
        speedy_heart_image = (ImageView)findViewById(R.id.speedy_heart_image);
        speedy_distance_number = (TextView)findViewById(R.id.speedy_distance_number);
        speedy_distance_type = (TextView)findViewById(R.id.speedy_distance_type);
        speedy_time_number = (TextView)findViewById(R.id.speedy_time_number);
        speedy_time_type = (TextView)findViewById(R.id.speedy_time_type);
        speedy_heat_name = (TextView)findViewById(R.id.speedy_heat_name);
        speedy_heat_type = (TextView)findViewById(R.id.speedy_heat_type);

        stamina_text = (TextView)findViewById(R.id.stamina_text);
        speedy_text = (TextView)findViewById(R.id.speedy_text);
        course_text = (TextView)findViewById(R.id.course_text);
        athletics_text = (TextView)findViewById(R.id.athletics_text);



        new_speedy_warm = (ImageView)findViewById(R.id.new_speedy_warm);
        new_sport_relax = (ImageView)findViewById(R.id.new_sport_relax);
        new_lose_weight = (ImageView)findViewById(R.id.new_lose_weight);
        new_competitive = (ImageView)findViewById(R.id.new_competitive);
        Glide.with(this).load(R.drawable.new_speedy_warm).into(new_speedy_warm);
        Glide.with(this).load(R.drawable.new_sport_relax).into(new_sport_relax);
        Glide.with(this).load(R.drawable.new_lose_weight).into(new_lose_weight);
        Glide.with(this).load(R.drawable.new_competitive).into(new_competitive);
        new_speedy_warm.setOnClickListener(this);
        new_sport_relax.setOnClickListener(this);
        new_lose_weight.setOnClickListener(this);
        new_competitive.setOnClickListener(this);


        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        speedy_distance_number.setTypeface(typeFace);
        speedy_distance_type.setTypeface(typeFace);
        speedy_time_number.setTypeface(typeFace);
        speedy_time_type.setTypeface(typeFace);
        speedy_heat_name.setTypeface(typeFace);
        speedy_heat_type.setTypeface(typeFace);
        Typeface typeFace_sport =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/FANGZHENG.TTF");
        stamina_text.setTypeface(typeFace_sport);
        speedy_text.setTypeface(typeFace_sport);
        course_text.setTypeface(typeFace_sport);
        athletics_text.setTypeface(typeFace_sport);

        Glide.with(this).load(UserInfo.headimgurl).into(user_image);
        Glide.with(this).load(R.drawable.distance).into(speedy_distance_image);
        Glide.with(this).load(R.drawable.time).into(speedy_time_image);
        Glide.with(this).load(R.drawable.heart).into(speedy_heart_image);

        back.setOnClickListener(this);

        fit_notif.setTipList(generateTips());
        palt_notif.setTipList(generateTips());

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

        mExitHintDialog = new ExitHintDialog(SpeedySportActivity.this);
    }

    @Override
    public void initLoad() {
        super.initLoad();
        //初始化蓝牙连接
        initBluetooth();

    }


    @Override
    protected void onResume() {
        super.onResume();
        //查询用户信息
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<UserBean> call = service.postUserMessage(systemUserId);
        call.enqueue(this);
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
                }else {
                    mLeDeviceListAdapter.clear();
                    progress.setVisibility(View.VISIBLE);

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
                //Log.e("蓝牙打开了","蓝牙打开了");
            }else {
            }
        }

    };

    //蓝牙扫描的状态监听
    final SearchResponse mSearchResponse = new SearchResponse() {
        @Override
        public void onSearchStarted() {
            progress.setVisibility(View.VISIBLE);
            search_ble.setVisibility(View.VISIBLE);
            //Log.e("扫描开始","扫描开始");
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
                        if (Utils.isEmpty(device.getName()) ==false){
                                if (device.getName().substring(0,2).equals("FI") ==true){
                                   // Log.e("扫描连接蓝牙",device.getName()+"信号的强度"+device.rssi);
                                    if (device.rssi > -65){
                                        //开始连接
                                        ClientManager.getClient().stopSearch();
                                        search_ble.setVisibility(View.GONE);
                                        //链接蓝牙
                                        if (Utils.isEmail(device.getAddress()) == true){
                                            return;
                                        }
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
           // Log.e("扫描停止","扫描停止");
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
           // Log.e("扫描关闭","扫描关闭");
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progress.setVisibility(View.GONE);
                    mScanBtn.setChecked(false);
                }
            });

        }
    };

    private boolean stringIsMac(String val){
        String trueMacAddress = "([A-Fa-f0-9]{2}:){5}[A-Fa-f0-9]{2}";
        // 这是真正的MAV地址；正则表达式；  
        if (val.matches(trueMacAddress)){
            return true;
        } else {
            return false;
        }
    }

    //点击蓝牙连接的列表单击事件，进行蓝牙的连接
    final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            final SearchResult device = mLeDeviceListAdapter.getDevice(position);
            if (device == null) return;


        }
    };

    BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            if (courseName.length()>1){
                if (status == STATUS_CONNECTED) {
                    //Log.e("连接到的mac",mac+"");
                    //Log.e("点击的到的mac",mac+"连接成功");

                    if (mDevice.getName().substring(0,2).equals("FI") ==true){
                        shouhuan = true;
                        shouhuan_mac = mDevice.getAddress();
                    }
                    if (shouhuan ==true){
                        ClientManager.getClient().unregisterConnectStatusListener(shouhuan_mac, mConnectStatusListener);
                        //链接成功
                        SharedPreferences pref = getSharedPreferences("MAC",MODE_WORLD_READABLE);
                        String name = pref.getString("paobu_name","");//第二个参数为默认值
                        if (name == null){
                            return;
                        }
                        if (name.equals("Qu") == true || name.equals("FS") == true){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("shouhuan_mac",shouhuan_mac);
                            editor.commit();
                            Intent intent = new Intent(SpeedySportActivity.this,SpeedSportUIActivity.class);
                            intent.putExtra("courseName",courseName);
                            intent.putExtra("courseLength",courseLength+"");
                            intent.putExtra("startSpeed",startSpeed+"");
                            intent.putExtra("minHeart",minHeart+"");
                            intent.putExtra("maxHeart",maxHeart+"");
                            intent.putExtra("topSpeed",topSpeed+"");
                            startActivity(intent);
                            courseName = "";
                            finish();
                        }else if (name.equals("aL") == true){
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("shouhuan_mac",shouhuan_mac);
                            editor.commit();
                            /*Intent intent = new Intent(SpeedySportActivity.this,SpeedNoControlSportUIActivity.class);
                            intent.putExtra("courseName",courseName);
                            intent.putExtra("courseLength",courseLength+"");
                            intent.putExtra("startSpeed",startSpeed+"");
                            intent.putExtra("minHeart",minHeart+"");
                            intent.putExtra("maxHeart",maxHeart+"");
                            intent.putExtra("topSpeed",topSpeed+"");
                            startActivity(intent);*/
                            courseName = "";
                            finish();
                        }


                    }else if (paobu ==false && shouhuan ==true){

                    }

                } else if (status == STATUS_DISCONNECTED) {
                    // showErrorWithStatus("连接失败");
                }
            }


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



    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tips.add("飞跑通知第" + i+"条");
        }
        return tips;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
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
                        startActivity(new Intent(SpeedySportActivity.this, MainActivity.class));
                    }
                },5000);
                break;
            //快速热身
            case R.id.new_speedy_warm:
                //初始运动数据

                courseName = "快速热身";
                courseLength = 15;
                startSpeed = 4;
                minHeart = 130;
                maxHeart = 150;
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
                            .searchBluetoothLeDevice(3000, 10)   // 先扫BLE设备10次，每次3s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }
                break;
            //跑步放松
            case R.id.new_sport_relax:
                //初始运动数据
                courseName = "跑步放松";
                courseLength = 15;
                startSpeed = 4;
                minHeart = 130;
                maxHeart = 150;
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
                            .searchBluetoothLeDevice(10000, 3)   // 先扫BLE设备3次，每次10s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }

                break;
            //减肥体验
            case R.id.new_lose_weight:
                //初始运动数据
                courseName = "减肥体验";
                courseLength = 30;
                startSpeed = 4;
                minHeart = 130;
                maxHeart = 150;
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
                            .searchBluetoothLeDevice(10000, 3)   // 先扫BLE设备3次，每次10s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }
                break;
            //耐力体验
            case R.id.new_competitive:
                //初始运动数据
                courseName = "耐力体验";
                courseLength = 30;
                startSpeed = 6;
                minHeart = 150;
                maxHeart = 170;
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
                            .searchBluetoothLeDevice(10000, 3)   // 先扫BLE设备3次，每次10s
                            .build();
                    ClientManager.getClient().search(request, mSearchResponse);
                }else {
                    //打开蓝牙
                    BluetoothUtils.openBluetooth();
                }
                break;


        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        rippleBackground.stopRippleAnimation();
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        ClientManager.getClient().unregisterConnectStatusListener(shouhuan_mac, mConnectStatusListener);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onResponse(Call<UserBean> call, Response<UserBean> response) {
        UserBean bean = response.body();
        if (bean == null){
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
                speedy_distance_number.setText(bean.getContent().getSumLength()+"");
                speedy_time_number.setText(bean.getContent().getSumTime()+"");
                speedy_heat_name.setText(bean.getContent().getSumCalorie()+"");

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
