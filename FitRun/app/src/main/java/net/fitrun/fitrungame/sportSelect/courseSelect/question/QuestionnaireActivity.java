package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.OnItemClickListener;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.sportSelect.courseSelect.AssessmentReportActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 晁东洋 on 2017/4/11.
 * 调查问卷
 */

public class QuestionnaireActivity extends BaseActivity implements View.OnClickListener, Callback<QuestionBean>,OnItemClickListener {
    //初始化控件
    RecyclerView question_recycler;
    ImageView back;
    CustomGridLayoutManager manager;
    QuestionBean bean;
    Handler mHandler;

    private String courseName;
    private String courseID;

    private QuestionRecycleAdapter mRecycleAdapter;
    static HashMap<String,String> question = new HashMap<>();
    @Override
    protected int getContentViewId() {
        return R.layout.layout_question;
    }

    @Override
    public void initView() {
        super.initView();
        EventBus.getDefault().register(this);
        mHandler = new MyHandler();
        question_recycler = (RecyclerView)findViewById(R.id.question_recycler);
        back = (ImageView)findViewById(R.id.question_back);
        manager = new CustomGridLayoutManager(this,CustomGridLayoutManager.HORIZONTAL,false);
        back.setOnClickListener(this);

    }


    @Override
    public void initLoad() {
        super.initLoad();

        Intent intent = getIntent();
        courseName = intent.getStringExtra("courseName");
        if (courseName.equals("减肥课程") == true){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NetWorkAddress.appPlusNetWork)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            NetWorkService service = retrofit.create(NetWorkService.class);
            Call<QuestionBean> call = service.getQuestionList(UserInfo.sex+"");
            call.enqueue(this);
        }

        if (courseName.equals("耐力课程") == true) {

        }
        if (courseName.equals("高血压康复") == true) {

        }
        if (courseName.equals("糖尿病康复") == true) {

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(QuestionEvent event) {
        if (event.postion == -1){
            question.put("-1",event.message+"");
        }else {
            question.put(event.message,event.message+"");
        }

        Message message = new Message();
        message.what =event.postion+1;
        if (event.is ==0){
            mHandler.sendMessage(message);
        }

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //移动到指定的问卷项
            manager.scrollToPositionWithOffset(msg.what,0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.question_back:

                AppManager.getAppManager().finishActivity(QuestionnaireActivity.class);

                break;
        }
    }

    @Override
    public void onResponse(Call<QuestionBean> call, Response<QuestionBean> response) {
        bean = response.body();
        switch (bean.getCode()){
            case 5377:
                mRecycleAdapter = new QuestionRecycleAdapter(this,bean.getContent());
                mRecycleAdapter.setOnItemClickListener(this);
                question_recycler.setAdapter(mRecycleAdapter);
                //设置布局管理
                manager.setScrollEnabled(false);
                question_recycler.setLayoutManager(manager);
                break;
            default:
                showErrorWithStatus(bean.getMessage());
                break;
        }
    }

    @Override
    public void onFailure(Call<QuestionBean> call, Throwable t) {
                showErrorWithStatus("网络访问失败");
    }

    @Override
    public void onItemClick(int id, int position, Object o) {
        List<QuestionBean.ContentBean.QuestionResultsBean> mlist = bean.getContent().get(position).getQuestionResults();
        boolean is = false;
        //下一页
        if (id == R.id.down_question) {
            for (int s = 0; s < mlist.size(); s++) {
                if (question.containsValue(mlist.get(s).getQrid()) == true) {
                    is = true;
                    if (o.toString().equals("最后一项")) {
                        Toast.makeText(QuestionnaireActivity.this, "最后一页的数据", Toast.LENGTH_LONG).show();
                        break;
                    } else {
                        //多选后移动到下一个的问卷项
                        manager.scrollToPositionWithOffset(position + 1, 0);
                        break;
                    }
                } else {

                }
            }
            if (is == false) {
                showErrorWithStatus("请选择一个答案");
            }
        }
        //上一页
        if (id == R.id.up_question){
            if (o.toString().equals("最后一项")){

                Toast.makeText(QuestionnaireActivity.this,"已经是第一项数据",Toast.LENGTH_LONG).show();
            }else {
                //多选后移动到下一个的问卷项
                manager.scrollToPositionWithOffset(position-1,0);
            }
        }

        //查看结果
        if (id == R.id.lock_result){
            Intent wlIntent = new Intent();
            Bundle bData = new Bundle();
            bData.putSerializable("serial",question);
            bData.putString("courseName",courseName);
            wlIntent.setClass(this, AssessmentReportActivity.class);
            wlIntent.putExtras(bData);
            startActivity(wlIntent);
            finish();
        }


    }

    @Override
    public void onClick(int id) {

    }
}
