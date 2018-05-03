package net.fitrun.fitrungame.sportSelect.courseSelect.userinfo;

import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wx.wheelview.widget.WheelView;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.courseSelect.UserBean;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Created by 晁东洋 on 2017/4/12.
 * 编辑用户信息的界面
 */

public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener, Callback<UserBean> {
    WheelView myWheelView,wheelview_height,year_wheelview,month_wheelview;
    TextView edit_user_title,sex_title,weight_title,height_title,day_title;

    private ImageView question_back,gender_nan,gender_nv,submit_user;
    private TextView gender_nan_text,gender_nv_text;
    //性别0是女，1是男
    private String gender = "";
    @Override
    protected int getContentViewId() {
        return R.layout.layout_edit_user_info;
    }

    @Override
    public void initView() {
        super.initView();
        myWheelView = (WheelView) findViewById(R.id.wheelview);
        wheelview_height = (WheelView) findViewById(R.id.wheelview_height);
        year_wheelview = (WheelView)findViewById(R.id.year_wheelview);
        month_wheelview = (WheelView)findViewById(R.id.month_wheelview);
        question_back = (ImageView)findViewById(R.id.question_back);
        question_back.setOnClickListener(this);
        gender_nan = (ImageView)findViewById(R.id.gender_nan);
        gender_nv = (ImageView)findViewById(R.id.gender_nv);
        submit_user = (ImageView)findViewById(R.id.submit_user);
        submit_user.setOnClickListener(this);
        gender_nan_text = (TextView)findViewById(R.id.gender_nan_text);
        gender_nv_text = (TextView)findViewById(R.id.gender_nv_text);

        gender_nan.setBackgroundResource(R.drawable.width_man);
        gender_nv.setBackgroundResource(R.drawable.width_woman);
        gender_nan_text.setTextColor(getResources().getColor(R.color.white));
        gender_nv_text.setTextColor(getResources().getColor(R.color.white));
        gender_nan.setOnClickListener(this);
        gender_nv.setOnClickListener(this);

        //体重
        myWheelView.setWheelAdapter(new MyWheelAdapter(this));
        myWheelView.setSkin(WheelView.Skin.Holo);
        myWheelView.setWheelData(createArrays());
        myWheelView.setExtraText("斤",getResources().getColor(R.color.border_yello),22,60);
        myWheelView.setWheelSize(3);
        myWheelView.setSelection(55);
        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.backgroundColor = getResources().getColor(R.color.transparent);
        style.textColor = getResources().getColor(R.color.text_white);
        style.textSize = 29;
        style.textAlpha = 255f;
        style.selectedTextColor = getResources().getColor(R.color.border_yello);
        style.selectedTextZoom = 1.5f;
        style.holoBorderColor = getResources().getColor(R.color.line);
        style.holoBorderWidth = 1;
        myWheelView.setStyle(style);
        //身高
        wheelview_height.setWheelAdapter(new MyWheelAdapter(this));
        wheelview_height.setSkin(WheelView.Skin.Holo);
        wheelview_height.setWheelData(createHeightArrays());
        wheelview_height.setExtraText("厘米",getResources().getColor(R.color.border_yello),22,70);
        wheelview_height.setWheelSize(3);
        wheelview_height.setSelection(25);
        WheelView.WheelViewStyle style_height = new WheelView.WheelViewStyle();
        style_height.backgroundColor = getResources().getColor(R.color.transparent);
        style_height.textColor = getResources().getColor(R.color.text_white);
        style_height.textSize = 29;
        style_height.textAlpha = 255f;
        style_height.selectedTextColor = getResources().getColor(R.color.border_yello);
        style_height.selectedTextZoom = 1.5f;
        style_height.holoBorderColor = getResources().getColor(R.color.line);
        style_height.holoBorderWidth =1;
        wheelview_height.setStyle(style_height);
        //生日
        WheelView.WheelViewStyle style_day = new WheelView.WheelViewStyle();
        style_day.backgroundColor = getResources().getColor(R.color.transparent);
        style_day.textColor = getResources().getColor(R.color.text_white);
        style_day.textSize = 29;
        style_day.textAlpha = 255f;
        style_day.selectedTextColor = getResources().getColor(R.color.border_yello);
        style_day.selectedTextZoom = 1.5f;
        style_day.holoBorderColor = getResources().getColor(R.color.line);
        style_day.holoBorderWidth =1;
        //年
        year_wheelview.setWheelAdapter(new MyWheelAdapter(this));
        year_wheelview.setSkin(WheelView.Skin.Holo);
        year_wheelview.setWheelData(createYearArrays());
        year_wheelview.setExtraText("年",getResources().getColor(R.color.border_yello),22,70);
        year_wheelview.setWheelSize(3);
        year_wheelview.setSelection(55);
        year_wheelview.setStyle(style_day);
        //月
        month_wheelview.setWheelAdapter(new MyWheelAdapter(this));
        month_wheelview.setSkin(WheelView.Skin.Holo);
        month_wheelview.setWheelData(createMonthArrays());
        month_wheelview.setExtraText("月",getResources().getColor(R.color.border_yello),22,40);
        month_wheelview.setWheelSize(3);
        month_wheelview.setSelection(5);
        month_wheelview.setStyle(style_day);



        edit_user_title = (TextView)findViewById(R.id.edit_user_title);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        edit_user_title.setTypeface(typeFace_jan);
        sex_title = (TextView)findViewById(R.id.sex_title);
        weight_title = (TextView)findViewById(R.id.weight_title);
        height_title = (TextView)findViewById(R.id.height_title);
        day_title = (TextView)findViewById(R.id.day_title);
        Typeface typeFace_fang =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/FANGZHENG.TTF");
        sex_title.setTypeface(typeFace_fang);
        weight_title.setTypeface(typeFace_fang);
        height_title.setTypeface(typeFace_fang);
        day_title.setTypeface(typeFace_fang);

    }

    private ArrayList<String> createArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 70; i < 300; i++) {
            list.add(""+i);
        }
        return list;
    }
    //身高
    private ArrayList<String> createHeightArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 140; i < 220; i++) {
            list.add("" + i);
        }
        return list;
    }

    //生日
    private List createMonthArrays() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1; i <= 12; i++) {
            list.add("" + i);
        }
        return list;
    }

    private List createYearArrays() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = year-80; i <= year; i++) {
            list.add("" + i);
        }
        return list;
    }
    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_back:
                finish();
                break;
            case R.id.gender_nan:
                gender = "1";
                gender_nan.setBackgroundResource(R.drawable.yello_man);
                gender_nv.setBackgroundResource(R.drawable.width_woman);
                gender_nan_text.setTextColor(getResources().getColor(R.color.border_yello));
                gender_nv_text.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.gender_nv:
                gender = "0";
                gender_nan.setBackgroundResource(R.drawable.width_man);
                gender_nv.setBackgroundResource(R.drawable.yello_woman);
                gender_nan_text.setTextColor(getResources().getColor(R.color.white));
                gender_nv_text.setTextColor(getResources().getColor(R.color.border_yello));
                break;
            //提交用户信息
            case R.id.submit_user:
                if (Utils.isEmpty(gender)){
                    showErrorWithStatus("请选择性别");
                    return;
                }
                //体重
                String  item_weight = (String) myWheelView.getSelectionItem();
                //身高
                String item_height = (String) wheelview_height.getSelectionItem();
                //生日
                String month = null;
                if (month_wheelview.getSelectionItem().toString().length()<2){
                    StringBuffer buffer = new StringBuffer();
                    buffer.append("0");
                    buffer.append(month_wheelview.getSelectionItem());
                    month = buffer.toString();
                }else {
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(month_wheelview.getSelectionItem());
                    month = buffer.toString();
                }
                String birthday = year_wheelview.getSelectionItem()+"-"+month+"-"+"01";
                //更新用户信息
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NetWorkAddress.appPlusNetWork)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NetWorkService service = retrofit.create(NetWorkService.class);
                Log.e("发送的参数",UserInfo.systemUserId);
                Log.e("发送的参数",gender);
                Log.e("发送的参数",Integer.valueOf(item_weight)/2d+"");
                Log.e("发送的参数",item_height);
                Log.e("发送的参数",birthday);
                Call<UserBean> callmessage = service.updateUserWeight(UserInfo.systemUserId,gender,Integer.valueOf(item_weight)/2d+"",item_height,birthday);
                callmessage.enqueue(this);


                break;
        }
    }

    @Override
    public void onResponse(Call<UserBean> call, Response<UserBean> response) {
        UserBean bean = response.body();
        if (bean == null){
            Log.e("bean是空",bean+"");
            return;
        }
        if (bean.getCode()==5122){
            UserInfo.age = bean.getContent().getAge();
            UserInfo.sex = bean.getContent().getGender();
            UserInfo.weight = bean.getContent().getWeight();
            UserInfo.height = bean.getContent().getHeight();
            showSuccessWithStatus("更新信息成功，即将退出");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
            }).start();

        }else {
            showErrorWithStatus(bean.getMessage());
        }
    }

    @Override
    public void onFailure(Call<UserBean> call, Throwable t) {
        showErrorWithStatus("网络异常");
    }
}
