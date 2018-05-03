package net.fitrun.fitrungame.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.fitrun.fitrungame.R;

import static net.fitrun.fitrungame.R.id.no_password;
import static net.fitrun.fitrungame.R.id.radio_40;
import static net.fitrun.fitrungame.R.id.radio_8;
import static net.fitrun.fitrungame.R.id.yse_password;


/**
 * Created by 晁东洋 on 2017/3/18.
 * 查找房间的弹出dialog
 */

public class CreateRoomDialog extends Dialog implements View.OnClickListener{

    private Context mcontext;
    //声明自定义的接口  
    private ClickListenerInterface clickListenerInterface;
    private View castoumView;
    //自定义接口  
    public interface ClickListenerInterface {
        public void click(int id,View view);
    }
    //接口的回调方法，以供实现接口调用  
    public void setOnCallbackLister(ClickListenerInterface clickListenerInterface){
        this.clickListenerInterface = clickListenerInterface;
    }

    public CreateRoomDialog(Context context) {
        super(context);
        this.mcontext = context;
    }

    public CreateRoomDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CreateRoomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
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
        castoumView = inflater.inflate(R.layout.layout_create_room,null);
        setContentView(castoumView);
        final Window dialogWindow = getWindow();
        //设置圆角样式  
        dialogWindow.setBackgroundDrawableResource(R.color.transparent);
        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        final DisplayMetrics d = mcontext.getResources().getDisplayMetrics();// 获取屏幕宽、高用

        MyRadioGroup limit_number = (MyRadioGroup)castoumView.findViewById(R.id.limit_number);
        MyRadioGroup limit_loack = (MyRadioGroup)castoumView.findViewById(R.id.limit_loack);
        final TextView yse_password = (RadioButton)castoumView.findViewById(R.id.yse_password);
        final TextView no_password = (RadioButton)castoumView.findViewById(R.id.no_password);
        limit_number.setCheckWithoutNotif(R.id.radio_8);
        limit_loack.setCheckWithoutNotif(R.id.no_password);
        //初始化控件
        ImageView creatRoom_close_dialoag = (ImageView)castoumView.findViewById(R.id.creatRoom_close_dialoag);
        RelativeLayout athletics_room_select = (RelativeLayout)castoumView.findViewById(R.id.athletics_room_select);
        EditText edit_password = (EditText)castoumView.findViewById(R.id.edit_password);

        athletics_room_select.setOnClickListener(this);
        creatRoom_close_dialoag.setOnClickListener(this);
        lp.height =(int)(d.heightPixels * 0.86);
        lp.width = (int)(d.widthPixels * 0.56);
        dialogWindow.setAttributes(lp);

    }
    @Override
    public void onClick(View v) {
        clickListenerInterface.click(v.getId(),v);
    }

    public View getCustomView() {
        return castoumView;
    }
}
