package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 晁东洋 on 2017/4/14.
 * 运动评估报告页面
 */

public class AssessmentReportActivity extends BaseActivity implements View.OnClickListener, Callback<QuestionResult> {
    ImageView back,enact_course;
    TextView assessment_title,grade_number,evaluate_title,evaluate_desc
            ,grade_text,bmi_count,grade_desc,shar_text;

    private String courseName;
    private String courseID;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_assessment_report;
    }

    @Override
    public void initView() {
        super.initView();
        back = (ImageView)findViewById(R.id.back);
        assessment_title = (TextView)findViewById(R.id.assessment_title);
        evaluate_title = (TextView)findViewById(R.id.evaluate_title);
        shar_text = (TextView)findViewById(R.id.shar_text);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        assessment_title.setTypeface(typeFace_jan);
        evaluate_title.setTypeface(typeFace_jan);
        shar_text.setTypeface(typeFace_jan);
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        grade_number = (TextView)findViewById(R.id.grade_number);
        grade_number.setTypeface(typeFace);
        evaluate_desc = (TextView)findViewById(R.id.evaluate_desc);
        back.setOnClickListener(this);

        enact_course = (ImageView)findViewById(R.id.enact_course);
        enact_course.setOnClickListener(this);

        grade_text = (TextView)findViewById(R.id.grade_text);
        bmi_count = (TextView)findViewById(R.id.bmi_count);
        grade_desc = (TextView)findViewById(R.id.grade_desc);
        grade_number = (TextView)findViewById(R.id.grade_number);



    }

    @Override
    public void initLoad() {
        super.initLoad();
        Bundle bundle = getIntent().getExtras();
        Serializable data = bundle.getSerializable("serial");
        courseName = bundle.getString("courseName");
        if (data != null) {
            HashMap<String, String> map = (HashMap<String, String>) data;
            Iterator iter = map.entrySet().iterator();
            StringBuffer buffer = new StringBuffer();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String key = (String) entry.getKey();
                String val = (String) entry.getValue();
                if (Utils.isEmpty(val) ==false){
                    buffer.append(val+",");
                }
            }
            buffer.deleteCharAt(buffer.length()-1);
            Log.e("选取的结果信息",buffer.toString());
            if (courseName.equals("减肥课程") == true) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(NetWorkAddress.appPlusNetWork)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                NetWorkService service = retrofit.create(NetWorkService.class);
                Call<QuestionResult> call = service.queryQusetionResult(UserInfo.systemUserId,buffer.toString());
                call.enqueue(this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.enact_course:
                if (courseName.equals("减肥课程") == true) {
                    Intent intent = new Intent(getBaseContext(), CreatCoursePlanActivity.class);
                    intent.putExtra("courseName", courseName);
                    startActivity(intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<QuestionResult> call, Response<QuestionResult> response) {
        QuestionResult bean = response.body();
        if (bean == null){
            return;
        }
        switch (bean.getCode()){
            case 5378:
                evaluate_desc.setText(bean.getContent().getAdvice());
                grade_number.setText(bean.getContent().getScore()+"");
                bmi_count.setText(bean.getContent().getBmi()+"");
                grade_text.setText(bean.getContent().getBimType()+"");
                grade_desc.setText(bean.getContent().getDes());
                break;
            default:
                showErrorWithStatus(bean.getMessage()+"");
                showErrorWithStatus(bean.getMessage());
                break;
        }
    }

    @Override
    public void onFailure(Call<QuestionResult> call, Throwable t) {

    }
}
