package net.fitrun.fitrungame.sportSelect;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.fitrun.fitrungame.R;


/**
 * Created by 晁东洋 on 2017/5/12.
 * 佩戴手环的提示页面
 */

public class ExitHintDialog extends Dialog {

    private Context mContext;
    public ExitHintDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public ExitHintDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ExitHintDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //初始化界面的控件
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        //自定义布局转换成view  
        View view = inflater.inflate(R.layout.layout_adorn_hint, null);
        //设置到当前的窗体  
        setContentView(view);
        ImageView hihtImage = (ImageView)view.findViewById(R.id.hint_image);
        Glide.with(mContext).load(R.drawable.exit_hint).into(hihtImage);
        Window dialogWindow = getWindow();
        //设置圆角样式  
        dialogWindow.setBackgroundDrawableResource(R.drawable.dialog_style);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        // 获取屏幕宽、高用 
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();
        // 高度设置为屏幕的0.6  
        lp.height =(int)(d.heightPixels *0.5);
        lp.width = (int)(d.widthPixels * 0.5);
        dialogWindow.setAttributes(lp);
    }
}
