package net.fitrun.fitrungame.Bluetoothservice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import net.fitrun.fitrungame.R;

/**
 * Created by 晁东洋 on 2016/10/31.
 * 蓝牙连接
 */

public class BLESelectDialog extends Dialog {
    private Context mContext;
    private OnItemClickListener mListener;
    private View customView;
    public View getCustomView() {
        return customView;
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }


    public BLESelectDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BLESelectDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        //自定义布局转换成view  
        customView = inflater.inflate(R.layout.layout_ble_select, null);

    }

    protected BLESelectDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置到当前的窗体  
        setContentView(customView);
        init();
    }
    //初始化控件
    private void init() {

        //设置圆角样式  
        Window dialogWindow = getWindow();
        dialogWindow.setBackgroundDrawableResource(R.drawable.dialog_style);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics();// 获取屏幕宽、高用  
        // 高度设置为屏幕的0.6  
        lp.height = (int) (d.heightPixels * 0.6);
        lp.width = (int) (d.widthPixels * 0.5);
        dialogWindow.setAttributes(lp);
    }
}
