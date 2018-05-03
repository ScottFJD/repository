package net.fitrun.fitrungame.sportSelect.endurance;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.sportSelect.ExitHintDialog;
import net.fitrun.fitrungame.sportSelect.SportSelectActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.view.DashboardViewHeart;
import net.fitrun.fitrungame.view.DashboardViewSpeed;

import java.util.Random;

import okhttp3.OkHttpClient;

import static net.fitrun.fitrungame.R.id.speedy_heat_name;
import static net.fitrun.fitrungame.R.id.speedy_heat_type;
import static net.fitrun.fitrungame.R.id.speedy_time_number;
import static net.fitrun.fitrungame.R.id.speedy_time_type;
import static net.fitrun.fitrungame.R.id.syllabus_title;

/**
 * Created by 晁东洋 on 2017/4/18.
 * 体能测试
 */

public class EnduranTestActivity extends BaseActivity implements View.OnClickListener{
    private ImageView back,exit_login;
    private TextView enduran_title,this_title,speedy_distance_number,speedy_distance_type
            ,speedy_heart_number,speedy_heart_type;

    private DashboardViewHeart dashboard_view_heart;
    private DashboardViewSpeed dashboard_view_speed;
    private boolean isAnimFinished = true;

    private TextView heart_number,heart_name,speed_number,speed_name;

    //退出登录的按钮
    private AlertView mAlertView;

    //退出的提示
    ExitHintDialog mExitHintDialog;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_enduran_test;
    }

    @Override
    public void initView() {
        super.initView();
        back = (ImageView)findViewById(R.id.back);
        exit_login = (ImageView)findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);
        back.setOnClickListener(this);
        this_title = (TextView)findViewById(R.id.this_title);
        enduran_title = (TextView)findViewById(R.id.enduran_title);
        Typeface typeFace_jan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/JINGDIANZONG.TTF");
        enduran_title.setTypeface(typeFace_jan);
        Typeface typeFace_fan =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/FANGZHENG.TTF");
        this_title.setTypeface(typeFace_fan);

        speedy_distance_number = (TextView)findViewById(R.id.speedy_distance_number);
        speedy_distance_type = (TextView)findViewById(R.id.speedy_distance_type);
        speedy_heart_number = (TextView)findViewById(R.id.speedy_heart_number);
        speedy_heart_type = (TextView)findViewById(R.id.speedy_heart_type);
        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        speedy_distance_number.setTypeface(typeFace);
        speedy_distance_type.setTypeface(typeFace);
        speedy_heart_number.setTypeface(typeFace);
        speedy_heart_type.setTypeface(typeFace);

        //心率
        dashboard_view_heart = (DashboardViewHeart)findViewById(R.id.dashboard_view_heart);
        dashboard_view_heart.setOnClickListener(this);
        heart_number = (TextView)findViewById(R.id.heart_number);
        speed_number = (TextView)findViewById(R.id.speed_number);
        speed_number.setTypeface(typeFace);
        heart_number.setTypeface(typeFace);
        heart_name = (TextView)findViewById(R.id.heart_name);
        speed_name = (TextView)findViewById(R.id.speed_name);
        speed_name.setTypeface(typeFace_fan);
        heart_name.setTypeface(typeFace_fan);

        //速度
        dashboard_view_speed = (DashboardViewSpeed)findViewById(R.id.dashboard_view_speed);
        dashboard_view_speed.setOnClickListener(this);
        mExitHintDialog = new ExitHintDialog(EnduranTestActivity.this);

    }

    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.exit_login:
                mExitHintDialog.show();
                //提示佩戴手环
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitHintDialog.dismiss();
                        UserInfo userInfo = new UserInfo("","","",0,"");
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(EnduranTestActivity.this, MainActivity.class));
                    }
                },5000);
                break;
            case R.id.dashboard_view_heart:
                if (isAnimFinished) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(dashboard_view_heart, "mRealTimeValue",
                            dashboard_view_heart.getVelocity(), new Random().nextInt(180));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            heart_number.setText(value+"");
                            dashboard_view_heart.setVelocity(value);
                        }
                    });
                    animator.start();
                }

                break;
            case R.id.dashboard_view_speed:
                if (isAnimFinished) {
                    ObjectAnimator animator = ObjectAnimator.ofInt(dashboard_view_speed, "mRealTimeValue",
                            dashboard_view_speed.getVelocity(), new Random().nextInt(13));
                    animator.setDuration(1500).setInterpolator(new LinearInterpolator());
                    animator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            isAnimFinished = false;
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            isAnimFinished = true;
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            isAnimFinished = true;
                        }
                    });
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            speed_number.setText(value + "");
                            dashboard_view_speed.setVelocity(value);
                        }
                    });
                    animator.start();
                }
                break;
        }
    }
}
