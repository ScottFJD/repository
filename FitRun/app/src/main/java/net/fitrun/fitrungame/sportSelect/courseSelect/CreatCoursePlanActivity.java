package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 晁东洋 on 2017/4/17.
 * 创建课程计划
 */

public class CreatCoursePlanActivity extends BaseActivity implements View.OnClickListener, Callback<Message> {
    private ImageView back,creat_bg,creat_plan;
    private TextView course_name,cycle_id;
    private EditText edit_kg;
    private RelativeLayout cycle_select,athletis_item_one,athletis_item_two,athletis_item_three
            ,athletis_item_four,athletis_item_five,athletis_item_six;
    private PopupWindow mPopupWindow;
    private View popupView;
    private TextView monday,tuesday,wed,thursday,friday,saturday,sunday;
    private int mtime1 = 0;
    private int mtime2 = 0;
    private int mtime3 = 0;
    private int mtime4 = 0;
    private int mtime5 = 0;
    private int mtime6 = 0;
    private int mtime7 = 0;
    private String startTime;
    private String courseParams;
    private String period = "1";

    private String courseName;
    private String courseID;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_creat_course_plan;
    }

    @Override
    public void initView() {
        super.initView();
        back = (ImageView)findViewById(R.id.back);
        back.setOnClickListener(this);
        creat_bg = (ImageView)findViewById(R.id.creat_bg);
        //Glide.with(this).load(R.drawable.question_tybg).into(creat_bg);
        course_name = (TextView)findViewById(R.id.course_name);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        course_name.setTypeface(typeFace_jan);
        edit_kg = (EditText)findViewById(R.id.edit_kg);
        cycle_select = (RelativeLayout)findViewById(R.id.cycle_select);
        cycle_select.setOnClickListener(this);
        cycle_id = (TextView)findViewById(R.id.cycle_id);
        creat_plan = (ImageView)findViewById(R.id.creat_plan);
        creat_plan.setOnClickListener(this);
        //周期选择
        popupView = getLayoutInflater().inflate(R.layout.layout_cycle_popu_item, null);
        athletis_item_one = (RelativeLayout)popupView.findViewById(R.id.athletis_item_one);
        athletis_item_two = (RelativeLayout)popupView.findViewById(R.id.athletis_item_two);
        athletis_item_three = (RelativeLayout)popupView.findViewById(R.id.athletis_item_three);
        athletis_item_four = (RelativeLayout)popupView.findViewById(R.id.athletis_item_four);
        athletis_item_five = (RelativeLayout)popupView.findViewById(R.id.athletis_item_five);
        athletis_item_six = (RelativeLayout)popupView.findViewById(R.id.athletis_item_six);
        athletis_item_one.setOnClickListener(this);
        athletis_item_two.setOnClickListener(this);
        athletis_item_three.setOnClickListener(this);
        athletis_item_four.setOnClickListener(this);
        athletis_item_five.setOnClickListener(this);
        athletis_item_six.setOnClickListener(this);
        mPopupWindow = new PopupWindow(popupView,250, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPopupWindow.setElevation(20);
        }
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        monday  = (TextView)findViewById(R.id.monday);
        tuesday = (TextView)findViewById(R.id.tuesday);
        wed = (TextView)findViewById(R.id.wed);
        thursday = (TextView)findViewById(R.id.thursday);
        friday = (TextView)findViewById(R.id.friday);
        saturday = (TextView)findViewById(R.id.saturday);
        sunday = (TextView)findViewById(R.id.sunday);

        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wed.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        sunday.setOnClickListener(this);

    }

    @Override
    public void initLoad() {
        super.initLoad();
        Date date = new Date(System.currentTimeMillis());
        startTime = Utils.getPersonDat(date)+"";

        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        course_name.setText(courseName);
    }

    @Override
    protected void onDestroy() {
        mtime1 = 0;
        mtime2 = 0;
        mtime3 = 0;
        mtime4 = 0;
        mtime5 = 0;
        mtime6 = 0;
        mtime7 = 0;
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.cycle_select:
                mPopupWindow.showAsDropDown(v,0,-10);
                break;
            case R.id.creat_plan:
                courseParams = edit_kg.getText().toString().trim();
                if (Utils.isEmpty(courseParams)){
                    edit_kg.setError("输入减肥目标");
                    return;
                }
                List<Integer> list = new ArrayList<>();
                list.add(mtime1);
                list.add(mtime2);
                list.add(mtime3);
                list.add(mtime4);
                list.add(mtime5);
                list.add(mtime6);
                list.add(mtime7);
                StringBuffer buffer = new StringBuffer();
                for (int i =0; i<7;i++){
                    if (list.get(i) != 0){
                        buffer.append(list.get(i));
                        buffer.append(",");
                    }
                }
                if (buffer.length()>0){
                    buffer.deleteCharAt(buffer.length()-1);
                }else {
                    showErrorWithStatus("请选择锻炼的日期");
                    return;
                }

                //设置课程
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NetWorkAddress.appPlusNetWork)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NetWorkService service = retrofit.create(NetWorkService.class);
                Log.e("选择的周期",buffer.toString());
                Call<Message> call = service.postSetCourse(UserInfo.systemUserId,startTime,period,buffer.toString(),courseParams);
                call.enqueue(this);
                break;
            case R.id.athletis_item_one:
                cycle_id.setText("1个月");
                period = "1";
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_two:
                cycle_id.setText("2个月");
                period = "2";
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_three:
                cycle_id.setText("3个月");
                period = "3";
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_four:
                cycle_id.setText("4个月");
                period = "4";
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_five:
                cycle_id.setText("5个月");
                period = "5";
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_six:
                cycle_id.setText("6个月");
                period = "6";
                mPopupWindow.dismiss();
                break;
            case R.id.monday:
                if (mtime1 ==0) {
                    mtime1 =1;
                    monday.setBackgroundResource(R.drawable.round_text);
                    monday.setTextColor(getResources().getColor(R.color.black));
                }else {
                    mtime1 =0;
                    monday.setTextColor(getResources().getColor(R.color.white));
                    monday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.tuesday:
                if (mtime2 ==0) {
                    mtime2 =2;
                    tuesday.setTextColor(getResources().getColor(R.color.black));
                    tuesday.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime2 =0;
                    tuesday.setTextColor(getResources().getColor(R.color.white));
                    tuesday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.wed:
                if (mtime3 ==0) {
                    mtime3 =3;
                    wed.setTextColor(getResources().getColor(R.color.black));
                    wed.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime3 =0;
                    wed.setTextColor(getResources().getColor(R.color.white));
                    wed.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.thursday:
                if (mtime4 ==0) {
                    mtime4 =4;
                    thursday.setTextColor(getResources().getColor(R.color.black));
                    thursday.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime4 =0;
                    thursday.setTextColor(getResources().getColor(R.color.white));
                    thursday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.friday:
                if (mtime5 ==0) {
                    mtime5 =5;
                    friday.setTextColor(getResources().getColor(R.color.black));
                    friday.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime5 =0;
                    friday.setTextColor(getResources().getColor(R.color.white));
                    friday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.saturday:
                if (mtime6 ==0) {
                    mtime6 =6;
                    saturday.setTextColor(getResources().getColor(R.color.black));
                    saturday.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime6 =0;
                    saturday.setTextColor(getResources().getColor(R.color.white));
                    saturday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
            case R.id.sunday:
                if (mtime7 ==0) {
                    mtime7 =7;
                    sunday.setTextColor(getResources().getColor(R.color.black));
                    sunday.setBackgroundResource(R.drawable.round_text);
                }else {
                    mtime7 =0;
                    sunday.setTextColor(getResources().getColor(R.color.white));
                    sunday.setBackgroundResource(R.drawable.normal_bg);
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<Message> call, Response<Message> response) {
        Message message = response.body();
        if (message == null){
            showErrorWithStatus("访问失败");
            return;
        }
        switch (message.getCode()){
            case 4866:
                showSuccessWithStatus("设置成功，即将跳转");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            Intent intent=new Intent();
                            intent.putExtra("extra",courseID);
                            intent.setClass(getBaseContext(), SyllabusActivity.class);
                            startActivity(intent);
                            finish();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                break;
            default:
                showErrorWithStatus(message.getMessage());
                break;
        }
    }

    @Override
    public void onFailure(Call<Message> call, Throwable t) {

    }
}
