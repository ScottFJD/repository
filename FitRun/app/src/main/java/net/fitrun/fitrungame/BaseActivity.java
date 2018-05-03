package net.fitrun.fitrungame;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.zhy.autolayout.AutoLayoutActivity;

import net.fitrun.fitrungame.app.AppManager;

/**
 * Created by 晁东洋 on 2017/3/13.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private InputMethodManager imm;
    View main;
    private SVProgressHUD mSVProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        main = getLayoutInflater().inflate(getContentViewId(),null);
        setContentView(main);
        AppManager.getAppManager().addActivity(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mSVProgressHUD = new SVProgressHUD(this);
        initView();
        initLoad();
    }

    protected abstract int getContentViewId();

    //初始化控件
    public void initView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    //初始化数据
    public void initLoad() {

    }

    //成功提示
    public void showSuccessWithStatus(String text) {

        mSVProgressHUD.showSuccessWithStatus(text);

    }

    //加载提示
    public void showWithStatus(String text) {

        mSVProgressHUD.showWithStatus(text);

    }

    //关闭动画
    public void closeWith(){
        mSVProgressHUD.dismiss();
    }

    //失败提示
    public void showErrorWithStatus(String text) {

        mSVProgressHUD.showErrorWithStatus(text, SVProgressHUD.SVProgressHUDMaskType.GradientCancel);

    }


    //禁止弹出软键盘
    public final void hideIMM(View editView) {
        imm.hideSoftInputFromWindow(editView.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        AppManager.getAppManager().finishActivity(this);
    }


}
