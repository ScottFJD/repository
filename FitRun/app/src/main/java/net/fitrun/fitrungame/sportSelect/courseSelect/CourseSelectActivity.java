package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.Bluetoothservice.ClientManager;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.OnItemClickListener;
import net.fitrun.fitrungame.sportSelect.ExitHintDialog;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.view.TipViewNoImage;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.systemUserId;


/**
 * Created by 晁东洋 on 2017/4/11.
 * 课程选择页面
 */

public class CourseSelectActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, Callback<CourseBean>{
    //初始化控件
    ImageView exit_login,back,new_lose_weight,new_endurance,new_diabetes,new_height_blooad;
    TextView choicen_title;
    ImageView course_user_image,speedy_distance_image,speedy_time_image,speedy_heart_image;
    private TextView speedy_distance_number,speedy_distance_type,speedy_time_number,speedy_time_type
            ,speedy_heat_name,speedy_heat_type,stamina_text,speedy_text,course_text,athletics_text;
    private TipViewNoImage fit_notif,palt_notif;

    //private RecyclerView couesr_recycle;
    private CourseListAdapter mCourseListAdapter;
    List<DataMode> mList = new ArrayList<>();
    //保存所有课程的状态
    List<Boolean> mBooleen = new ArrayList<>();
    CourseBean courseBean;

    //退出的提示
    ExitHintDialog mExitHintDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_course_select;
    }

    @Override
    public void initView() {
        super.initView();
        back = (ImageView)findViewById(R.id.back);
        exit_login = (ImageView)findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);
        back.setOnClickListener(this);
        new_lose_weight = (ImageView)findViewById(R.id.new_lose_weight);
        new_endurance = (ImageView)findViewById(R.id.new_endurance);
        new_diabetes = (ImageView)findViewById(R.id.new_diabetes);
        new_height_blooad = (ImageView)findViewById(R.id.new_height_blooad);
        new_lose_weight.setOnClickListener(this);
        new_endurance.setOnClickListener(this);
        new_diabetes.setOnClickListener(this);
        new_height_blooad.setOnClickListener(this);

        Glide.with(this).load(R.drawable.new_lose_weight_bg).into(new_lose_weight);
        Glide.with(this).load(R.drawable.new_endurance_course_bg).into(new_endurance);
        Glide.with(this).load(R.drawable.new_diabetes_course_bg).into(new_diabetes);
        Glide.with(this).load(R.drawable.new_height_blooad_bg).into(new_height_blooad);

        choicen_title = (TextView)findViewById(R.id.choicen_title);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        choicen_title.setTypeface(typeFace_jan);
        course_user_image = (ImageView)findViewById(R.id.course_user_image);
        speedy_distance_image = (ImageView)findViewById(R.id.speedy_distance_image);
        speedy_time_image = (ImageView)findViewById(R.id.speedy_time_image);
        speedy_heart_image = (ImageView)findViewById(R.id.speedy_heart_image);
        speedy_distance_number = (TextView)findViewById(R.id.speedy_distance_number);
        speedy_distance_type = (TextView)findViewById(R.id.speedy_distance_type);
        speedy_time_number = (TextView)findViewById(R.id.speedy_time_number);
        speedy_time_type = (TextView)findViewById(R.id.speedy_time_type);
        speedy_heat_name = (TextView)findViewById(R.id.speedy_heat_name);
        speedy_heat_type = (TextView)findViewById(R.id.speedy_heat_type);
        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        speedy_distance_number.setTypeface(typeFace);
        speedy_distance_type.setTypeface(typeFace);
        speedy_time_number.setTypeface(typeFace);
        speedy_time_type.setTypeface(typeFace);
        speedy_heat_name.setTypeface(typeFace);
        speedy_heat_type.setTypeface(typeFace);

        fit_notif = (TipViewNoImage)findViewById(R.id.fit_notif);
        palt_notif = (TipViewNoImage)findViewById(R.id.palt_notif);
        fit_notif.setTipList(generateTips());
        palt_notif.setTipList(generateTips());

       /* couesr_recycle = (RecyclerView)findViewById(R.id.couesr_recycle);
        //设置布局管理
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        couesr_recycle.setLayoutManager(manager);
       */

        /*for (int i =0;i<4;i++){
            DataMode dataMode = new DataMode();
            if (i ==0){
                dataMode.name = "减肥课程";
                dataMode.content = "LOSE WEIGHT";
                dataMode.contentimage = R.drawable.course_lose_weight;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
            if (i ==1){
                dataMode.name = "耐力课程";
                dataMode.content = "ENDURANCE";
                dataMode.contentimage = R.drawable.course_endurance;
                dataMode.courseID = "c9c0dd840d3f4802bd2a4e2deced3051";
                mList.add(dataMode);
            }
            if (i ==2){
                dataMode.name = "高血压康复";
                dataMode.content = "PRESSURE";
                dataMode.contentimage = R.drawable.height_blode;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
            if (i ==3){
                dataMode.name = "糖尿病康复";
                dataMode.content = "DIABETES";
                dataMode.contentimage = R.drawable.diabetes;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
        }*/

        mExitHintDialog = new ExitHintDialog(CourseSelectActivity.this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<CourseBean> call = service.queryCourse(UserInfo.systemUserId);
        call.enqueue(this);

        //查询用户信息
        Retrofit retrofit_user = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service_user = retrofit_user.create(NetWorkService.class);
        Call<UserBean> call_user = service_user.postUserMessage(systemUserId);
        call_user.enqueue(new Callback<UserBean>() {
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
        });
    }

    @Override
    public void initLoad() {
        super.initLoad();
        Glide.with(this).load(UserInfo.headimgurl).into(course_user_image);
        Glide.with(this).load(R.drawable.distance).into(speedy_distance_image);
        Glide.with(this).load(R.drawable.time).into(speedy_time_image);
        Glide.with(this).load(R.drawable.heart).into(speedy_heart_image);
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
                        startActivity(new Intent(CourseSelectActivity.this, MainActivity.class));
                    }
                },5000);
                break;
            //减肥课程
            case R.id.new_lose_weight:
                if (courseBean == null){
                    showErrorWithStatus("课程访问失败");
                    return;
                }
                if (courseBean.getContent().get(0).isCreatedCourse() == false){
                    if (mBooleen.contains(true)){
                        showErrorWithStatus("您已经创建过其他课程，不能在创建当前课程");
                        return;
                    }else {
                        Intent intent = new Intent(CourseSelectActivity.this,CourseDetailsActivity.class);
                        intent.putExtra("courseName","减肥课程");
                        startActivity(intent);
                    }

                }else {
                    if (courseBean.getContent().get(0).getCourseDescription().toString().equals("减肥课程")== true){
                        SharedPreferences pref = getSharedPreferences("MAC",MODE_WORLD_READABLE);
                        String  name = pref.getString("shouhuan_mac","");//第二个参数为默认值
                        if (name.length() >0){
                            ClientManager.getClient().disconnect(name);
                        }
                        Intent intent=new Intent();
                        intent.putExtra("courseName","减肥课程");
                        intent.setClass(getBaseContext(), SyllabusActivity.class);
                        startActivity(intent);
                    }

                }

                break;
            //耐力课程
            case R.id.new_endurance:
                if (courseBean == null){
                    showErrorWithStatus("课程访问失败");
                    return;
                }
                if (courseBean.getContent().get(1).isCreatedCourse() == false){
                    if (mBooleen.contains(true)){
                        showErrorWithStatus("您已经创建过其他课程，不能在创建当前课程");
                        return;
                    }else {
                        Intent intent = new Intent(CourseSelectActivity.this,CourseDetailsActivity.class);
                        intent.putExtra("courseName","耐力课程");
                        startActivity(intent);
                    }

                }else {
                    if (courseBean.getContent().get(1).getCourseDescription().toString().equals("耐力课程")== true){
                        Intent intent=new Intent();
                        intent.setClass(getBaseContext(), SyllabusActivity.class);
                        startActivity(intent);
                    }

                }
                break;
            //糖尿病课程
            case R.id.new_diabetes:
                if (courseBean == null){
                    showErrorWithStatus("课程访问失败");
                    return;
                }
                if (courseBean.getContent().get(3).isCreatedCourse() == false){
                    if (mBooleen.contains(true)){
                        showErrorWithStatus("您已经创建过其他课程，不能在创建当前课程");
                        return;
                    }else {
                        Intent intent = new Intent(CourseSelectActivity.this,CourseDetailsActivity.class);
                        intent.putExtra("courseName","糖尿病康复");
                        startActivity(intent);
                    }

                }else {
                    if (courseBean.getContent().get(3).getCourseDescription().toString().equals("糖尿病康复")== true){
                        Intent intent=new Intent();
                        intent.setClass(getBaseContext(), SyllabusActivity.class);
                        startActivity(intent);
                    }

                }
                break;
            //高血压课程
            case R.id.new_height_blooad:
                if (courseBean == null){
                    showErrorWithStatus("课程访问失败");
                    return;
                }
                if (courseBean.getContent().get(2).isCreatedCourse() == false){
                    if (mBooleen.contains(true)){
                        showErrorWithStatus("您已经创建过其他课程，不能在创建当前课程");
                        return;
                    }else {
                        Intent intent = new Intent(CourseSelectActivity.this,CourseDetailsActivity.class);
                        intent.putExtra("courseName","高血压康复");
                        startActivity(intent);
                    }

                }else {
                    if (courseBean.getContent().get(2).getCourseDescription().toString().equals("高血压康复")== true){
                        Intent intent=new Intent();
                        intent.setClass(getBaseContext(), SyllabusActivity.class);
                        startActivity(intent);
                    }

                }
                break;

        }
    }

    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tips.add("飞跑通知第" + i+"条");
        }
        return tips;
    }

    @Override
    public void onItemClick(int id, int position, Object o) {

       /* if (courseBean.getContent().get(position).isCreatedCourse() == false){
            if (mBooleen.contains(true)){
                showErrorWithStatus("您已经创建过其他课程，不能在创建当前课程");
                return;
            }else {
                Intent intent = new Intent(CourseSelectActivity.this,CourseDetailsActivity.class);
                intent.putExtra("courseName",courseBean.getContent().get(position).getCourseDescription().toString().trim());
                startActivity(intent);
            }

        }else {
            if (courseBean.getContent().get(position).getCourseDescription().toString().equals("减肥课程")== true){
                Intent intent=new Intent();
                intent.setClass(getBaseContext(), SyllabusActivity.class);
                startActivity(intent);
            }

        }*/

    }

    @Override
    public void onClick(int id) {

    }

    @Override
    public void onResponse(Call<CourseBean> call, Response<CourseBean> response) {
        courseBean = response.body();
        if (courseBean == null){
            return;
        }
        switch (courseBean.getCode()) {
            case 5129:
                for (int x =0; x<courseBean.getContent().size();x++){
                    mBooleen.add(courseBean.getContent().get(x).isCreatedCourse());
                }
                /*mCourseListAdapter = new CourseListAdapter(this,courseBean.getContent());
                mCourseListAdapter.setOnItemClickListener(this);
                couesr_recycle.setAdapter(mCourseListAdapter);*/

                break;
        }
    }

    @Override
    public void onFailure(Call<CourseBean> call, Throwable t) {

    }

}
