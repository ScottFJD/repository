package net.fitrun.fitrungame.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.fitrun.fitrungame.R;

import static net.fitrun.fitrungame.R.id.keyboard_1;


/**
 * Created by 晁东洋 on 2017/3/18.
 * 查找房间的弹出dialog
 */

public class FindRoomDialog extends Dialog implements View.OnClickListener{

    private Context mcontext;
    //声明自定义的接口  
    private ClickListenerInterface clickListenerInterface;
    private View castoumView;
    //自定义接口  
    public interface ClickListenerInterface {
        public void click(int id);
    }
    //接口的回调方法，以供实现接口调用  
    public void setOnCallbackLister(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    public FindRoomDialog(Context context) {
        super(context);
        this.mcontext = context;
    }

    public FindRoomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected FindRoomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        initView();

    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mcontext);
        castoumView = inflater.inflate(R.layout.layout_custom_keyboardy,null);
        setContentView(castoumView);
        //键盘的点击事件
        Button keyboard_0 = (Button)castoumView.findViewById(R.id.keyboard_0);
        Button keyboard_1 = (Button)castoumView.findViewById(R.id.keyboard_1);
        Button keyboard_2 = (Button)castoumView.findViewById(R.id.keyboard_2);
        Button keyboard_3 = (Button)castoumView.findViewById(R.id.keyboard_3);
        Button keyboard_4 = (Button)castoumView.findViewById(R.id.keyboard_4);
        Button keyboard_5 = (Button)castoumView.findViewById(R.id.keyboard_5);
        Button keyboard_6 = (Button)castoumView.findViewById(R.id.keyboard_6);
        Button keyboard_7 = (Button)castoumView.findViewById(R.id.keyboard_7);
        Button keyboard_8 = (Button)castoumView.findViewById(R.id.keyboard_8);
        Button keyboard_9 = (Button)castoumView.findViewById(R.id.keyboard_9);
        StrokeTextView keyboard_clean = (StrokeTextView)castoumView.findViewById(R.id.keyboard_clean);
        StrokeTextViewd keyboard_delete = (StrokeTextViewd)castoumView.findViewById(R.id.keyboard_delete);
        ImageView close_dialoag = (ImageView)castoumView.findViewById(R.id.close_dialoag);

        keyboard_0.setOnClickListener(this);
        keyboard_1.setOnClickListener(this);
        keyboard_2.setOnClickListener(this);
        keyboard_3.setOnClickListener(this);
        keyboard_4.setOnClickListener(this);
        keyboard_5.setOnClickListener(this);
        keyboard_6.setOnClickListener(this);
        keyboard_7.setOnClickListener(this);
        keyboard_8.setOnClickListener(this);
        keyboard_9.setOnClickListener(this);
        keyboard_clean.setOnClickListener(this);
        keyboard_delete.setOnClickListener(this);
        close_dialoag.setOnClickListener(this);

        Window dialogWindow = getWindow();
        //设置圆角样式  
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mcontext.getResources().getDisplayMetrics();// 获取屏幕宽、高用  
        // 高度设置为屏幕的0.6  
        //lp.height =(int)(d.heightPixels * 0.6);
        lp.width = (int)(d.widthPixels * 0.56);
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        clickListenerInterface.click(v.getId());
    }

    public View getCustomView() {
        return castoumView;
    }
}
