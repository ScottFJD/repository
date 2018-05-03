package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.sportSelect.courseSelect.question.QuestionnaireActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.userinfo.EditUserInfoActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晁东洋 on 2017/4/14.
 * 课程详情页面
 */

public class CourseDetailsActivity extends BaseActivity implements View.OnClickListener {
    private String courseName;
    private String courseID;

    ImageView back;
    private TextView choicen_title,refer_title,custom_text,state_text;

    private RecyclerView user_sport_state_recycler;
    private CustomCourseStateAdapter mStateAdapter;
    List<DataMode> mList = new ArrayList<>();

    private ImageView creat_course;
    @Override
    protected int getContentViewId() {
        return R.layout.layout_course_details;
    }

    @Override
    public void initView() {
        super.initView();
        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        choicen_title = (TextView)findViewById(R.id.choicen_title);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        choicen_title.setTypeface(typeFace_jan);
        refer_title = (TextView)findViewById(R.id.refer_title);
        custom_text = (TextView)findViewById(R.id.custom_text);
        state_text = (TextView)findViewById(R.id.state_text);
        Typeface typeFace_fan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/FANGZHENG.TTF");
        refer_title.setTypeface(typeFace_fan);
        custom_text.setTypeface(typeFace_fan);
        state_text.setTypeface(typeFace_fan);
        choicen_title.setText(courseName.toString().trim());
        back = (ImageView)findViewById(R.id.back);
        creat_course = (ImageView)findViewById(R.id.creat_course);
        creat_course.setOnClickListener(this);
        back.setOnClickListener(this);

        user_sport_state_recycler = (RecyclerView)findViewById(R.id.user_sport_state_recycler);
        //设置布局管理
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        user_sport_state_recycler.setLayoutManager(manager);
        for (int i =0;i<4;i++){
            DataMode dataMode = new DataMode();
            if (i ==0){
                dataMode.name = "泡泡大侠";
                dataMode.content = "刚刚定制成功";
                dataMode.contentimage = R.drawable.course_lose_weight;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
            if (i ==1){
                dataMode.name = "JASHIJD";
                dataMode.content = "刚刚完成训练";
                dataMode.contentimage = R.drawable.course_endurance;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
            if (i ==2){
                dataMode.name = "住在云端";
                dataMode.content = "进入房间开始训练";
                dataMode.contentimage = R.drawable.height_blode;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
            if (i ==3){
                dataMode.name = "打不死的小强";
                dataMode.content = "退出房间A";
                dataMode.contentimage = R.drawable.diabetes;
                dataMode.courseID = "f5ffe5f66cbf4415a18d2a0664291e20";
                mList.add(dataMode);
            }
        }
        mStateAdapter = new CustomCourseStateAdapter(this,mList);
        user_sport_state_recycler.setAdapter(mStateAdapter);


    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //退出
            case R.id.back:
                finish();
                break;

            //创建课程
            case R.id.creat_course:
                if (UserInfo.age <=0 || UserInfo.height <= 0 || UserInfo.weight <= 0){
                    //更新用户信息
                   startActivity(new Intent(CourseDetailsActivity.this, EditUserInfoActivity.class));
                }else {
                    //填写调查问卷
                    Intent intent = new Intent(CourseDetailsActivity.this,QuestionnaireActivity.class);
                    intent.putExtra("courseName",courseName);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }
}
