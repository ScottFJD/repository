package net.fitrun.fitrungame.sportSelect.athletics;


import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.NetWork.NetWorkAddress;
import net.fitrun.fitrungame.NetWork.NetWorkService;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.adapter.FriendsListAdapter;
import net.fitrun.fitrungame.adapter.RoomDividerItemDecoration;
import net.fitrun.fitrungame.adapter.RoomListAdapter;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.OnItemClickListener;
import net.fitrun.fitrungame.sportSelect.ExitHintDialog;
import net.fitrun.fitrungame.sportSelect.SportSelectActivity;
import net.fitrun.fitrungame.sportSelect.courseSelect.UserBean;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.view.CreateRoomDialog;
import net.fitrun.fitrungame.view.FindRoomDialog;
import net.fitrun.fitrungame.view.MyRadioGroup;
import net.fitrun.fitrungame.view.NotifTipView;
import net.fitrun.fitrungame.view.NotifTipView_n;
import net.fitrun.fitrungame.view.StrokeTextViewd;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static net.fitrun.fitrungame.R.id.athletics_heat_type;
import static net.fitrun.fitrungame.R.id.distance_number;
import static net.fitrun.fitrungame.R.id.heat_name;
import static net.fitrun.fitrungame.R.id.sex_image;
import static net.fitrun.fitrungame.R.id.speedy_distance_number;
import static net.fitrun.fitrungame.R.id.speedy_heat_name;
import static net.fitrun.fitrungame.R.id.speedy_time_number;
import static net.fitrun.fitrungame.R.id.time_number;
import static net.fitrun.fitrungame.R.id.user_id;
import static net.fitrun.fitrungame.R.mipmap.select;
import static net.fitrun.fitrungame.R.mipmap.select_no;
import static net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo.systemUserId;


/**
 * Created by 晁东洋 on 2017/3/15.
 * 在线竞技
 */

public class SportAthleticsActivity extends BaseActivity implements View.OnClickListener, OnItemClickListener, Callback<UserBean> {
    private TextView athletics_distance_number, athletics_distance_type, athletics_time_number, athletics_time_type, athletics_heat_name, athletics_heat_type, speed_id, my_friends, join_room_text, athletics_user_name, athletics_user_id;
    private NotifTipView notif_image;
    private NotifTipView_n notif_image_two;
    private Button quick_join, find_room;
    private RecyclerView room_recyclerview, findes_recyclerview;
    private RoomListAdapter mRoomListAdapter;
    private FriendsListAdapter mFriendsListAdapter;
    private List<String> mlist;
    private RelativeLayout naili_contest, kcl_contest, speed_contest, relay_contest, join_room;
    private TextView naili_contest_text, kcl_contest_text, speed_contest_text, relay_contest_text;
    private RelativeLayout room_athletis_item_one, room_athletis_item_two, room_athletis_item_three, room_athletis_item_four, athletics_select, athletis_item_one, athletis_item_two, athletis_item_three, athletis_item_four, create_room;
    private PopupWindow mPopupWindow, createRoomPopupWindow;
    private View popupView;
    private MyRadioGroup limit_number, limit_loack;
    private FindRoomDialog roomDialog;
    private CreateRoomDialog createRoomDialog;
    private TextView number_1, number_2, number_3, number_4, number_5, number_6, room_speed_id;
    private RadioButton radio_4, radio_8, radio_12, radio_20, radio_24, radio_28, radio_32, radio_40, yse_password, no_password;
    private RelativeLayout athletics_main_bg, finend_bg, firends_bg_rea, title_bg_ral, up_firends_bg;
    private LinearLayout top_lin, title_bg, grade_bg_lin;
    private ImageView athletics_user_image, athletics_sex_image;

    //定义一个textView的集合保存数据
    List<TextView> mTextViews;

    //退出当前页面以及系统
    ImageView back, exit_login;

    //退出的提示
    ExitHintDialog mExitHintDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient mClient;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_sport_athletics;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void initView() {
        super.initView();

        athletics_distance_number = (TextView) findViewById(R.id.athletics_distance_number);
        athletics_distance_type = (TextView) findViewById(R.id.athletics_distance_type);
        athletics_time_number = (TextView) findViewById(R.id.athletics_time_number);
        athletics_time_type = (TextView) findViewById(R.id.athletics_time_type);
        athletics_heat_name = (TextView) findViewById(R.id.athletics_heat_name);
        athletics_heat_type = (TextView) findViewById(R.id.athletics_heat_type);
        join_room_text = (TextView) findViewById(R.id.join_room_text);
        athletics_user_name = (TextView) findViewById(R.id.athletics_user_name);
        athletics_user_id = (TextView) findViewById(R.id.athletics_user_id);
        join_room_text.setTextColor(getResources().getColor(R.color.white));
        my_friends = (TextView) findViewById(R.id.my_friends);
        speed_id = (TextView) findViewById(R.id.speed_id);
        speed_id.setText("耐力竞技");
        naili_contest = (RelativeLayout) findViewById(R.id.naili_contest);
        naili_contest.setOnClickListener(this);
        kcl_contest = (RelativeLayout) findViewById(R.id.kcl_contest);
        kcl_contest.setOnClickListener(this);
        speed_contest = (RelativeLayout) findViewById(R.id.speed_contest);
        speed_contest.setOnClickListener(this);
        relay_contest = (RelativeLayout) findViewById(R.id.relay_contest);
        relay_contest.setOnClickListener(this);
        athletics_select = (RelativeLayout) findViewById(R.id.athletics_select);
        athletics_select.setOnClickListener(this);
        join_room = (RelativeLayout) findViewById(R.id.join_room);
        join_room.setBackgroundResource(R.mipmap.join_bg);
        create_room = (RelativeLayout) findViewById(R.id.create_room);
        create_room.setOnClickListener(this);
        //竞技的选项
        popupView = getLayoutInflater().inflate(R.layout.layout_athletics_popu_item, null);
        athletis_item_one = (RelativeLayout) popupView.findViewById(R.id.athletis_item_one);
        athletis_item_two = (RelativeLayout) popupView.findViewById(R.id.athletis_item_two);
        athletis_item_three = (RelativeLayout) popupView.findViewById(R.id.athletis_item_three);
        athletis_item_four = (RelativeLayout) popupView.findViewById(R.id.athletis_item_four);
        athletis_item_one.setOnClickListener(this);
        athletis_item_two.setOnClickListener(this);
        athletis_item_three.setOnClickListener(this);
        athletis_item_four.setOnClickListener(this);

        mPopupWindow = new PopupWindow(popupView, 250, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setElevation(20);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        View view = getLayoutInflater().inflate(R.layout.layout_room_athletics_popu_item, null);
        room_athletis_item_one = (RelativeLayout) view.findViewById(R.id.room_athletis_item_one);
        room_athletis_item_two = (RelativeLayout) view.findViewById(R.id.room_athletis_item_two);
        room_athletis_item_three = (RelativeLayout) view.findViewById(R.id.room_athletis_item_three);
        room_athletis_item_four = (RelativeLayout) view.findViewById(R.id.room_athletis_item_four);
        room_athletis_item_one.setOnClickListener(this);
        room_athletis_item_two.setOnClickListener(this);
        room_athletis_item_three.setOnClickListener(this);
        room_athletis_item_four.setOnClickListener(this);
        createRoomPopupWindow = new PopupWindow(view, 300, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        createRoomPopupWindow.setTouchable(true);
        createRoomPopupWindow.setOutsideTouchable(true);
        createRoomPopupWindow.setElevation(20);
        createRoomPopupWindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));


        naili_contest_text = (TextView) findViewById(R.id.naili_contest_text);
        kcl_contest_text = (TextView) findViewById(R.id.kcl_contest_text);
        speed_contest_text = (TextView) findViewById(R.id.speed_contest_text);
        relay_contest_text = (TextView) findViewById(R.id.relay_contest_text);
        naili_contest.setBackgroundResource(R.mipmap.select);
        kcl_contest.setBackgroundResource(select_no);
        speed_contest.setBackgroundResource(select_no);
        relay_contest.setBackgroundResource(select_no);
        naili_contest_text.setTextColor(getResources().getColor(R.color.black));
        kcl_contest_text.setTextColor(getResources().getColor(R.color.white));
        speed_contest_text.setTextColor(getResources().getColor(R.color.white));
        relay_contest_text.setTextColor(getResources().getColor(R.color.white));
        //设置字体颜色
        Typeface typeFace_sport = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/FANGZHENG.TTF");
        naili_contest_text.setTypeface(typeFace_sport);
        kcl_contest_text.setTypeface(typeFace_sport);
        speed_contest_text.setTypeface(typeFace_sport);
        relay_contest_text.setTypeface(typeFace_sport);
        speed_id.setTypeface(typeFace_sport);
        my_friends.setTypeface(typeFace_sport);

        room_recyclerview = (RecyclerView) findViewById(R.id.room_recyclerview);
        findes_recyclerview = (RecyclerView) findViewById(R.id.findes_recyclerview);
        room_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        findes_recyclerview.setLayoutManager(new LinearLayoutManager(this));

        //测试数据
        mlist = new ArrayList<>();
        mlist.add("00001");
        mlist.add("00002");
        mlist.add("00003");
        mlist.add("00004");
        mlist.add("00005");
        mlist.add("00006");
        mlist.add("00007");
        mlist.add("00008");
        mlist.add("00009");
        mRoomListAdapter = new RoomListAdapter(getBaseContext(), mlist);
        room_recyclerview.setAdapter(mRoomListAdapter);
        mRoomListAdapter.setOnItemClickListener(this);
        room_recyclerview.addItemDecoration(new RoomDividerItemDecoration(this, RoomDividerItemDecoration.VERTICAL_LIST));
        //好友列表
        mFriendsListAdapter = new FriendsListAdapter(getBaseContext(), mlist);
        findes_recyclerview.setAdapter(mFriendsListAdapter);
        findes_recyclerview.addItemDecoration(new RoomDividerItemDecoration(this, RoomDividerItemDecoration.VERTICAL_LIST));


        //设置字体
        Typeface typeFace = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/AGENCYB.TTF");
        athletics_distance_number.setTypeface(typeFace);
        athletics_distance_type.setTypeface(typeFace);
        athletics_time_number.setTypeface(typeFace);
        athletics_time_type.setTypeface(typeFace);
        athletics_heat_name.setTypeface(typeFace);
        athletics_heat_type.setTypeface(typeFace);

        notif_image = (NotifTipView) findViewById(R.id.notif_image);
        notif_image.setTipList(generateTips());
        notif_image_two = (NotifTipView_n) findViewById(R.id.notif_image_two);
        notif_image_two.setTipList(generateTips());
        quick_join = (Button) findViewById(R.id.quick_join);
        find_room = (Button) findViewById(R.id.find_room);
        quick_join.setOnClickListener(this);
        find_room.setOnClickListener(this);
        //查找房间的弹出框
        roomDialog = new FindRoomDialog(SportAthleticsActivity.this);
        //创建房间弹出框
        createRoomDialog = new CreateRoomDialog(SportAthleticsActivity.this);
        //加载背景图片
        athletics_main_bg = (RelativeLayout) findViewById(R.id.athletics_main_bg);
        athletics_main_bg.setBackgroundResource(R.drawable.athletics_bg);
        top_lin = (LinearLayout) findViewById(R.id.top_lin);
        title_bg = (LinearLayout) findViewById(R.id.title_bg);
        grade_bg_lin = (LinearLayout) findViewById(R.id.grade_bg_lin);
        finend_bg = (RelativeLayout) findViewById(R.id.finend_bg);
        firends_bg_rea = (RelativeLayout) findViewById(R.id.firends_bg_rea);
        title_bg_ral = (RelativeLayout) findViewById(R.id.title_bg_ral);
        up_firends_bg = (RelativeLayout) findViewById(R.id.up_firends_bg);

        top_lin.setBackgroundResource(R.drawable.top_buton);
        naili_contest.setBackgroundResource(R.drawable.select);
        kcl_contest.setBackgroundResource(R.drawable.select_no);
        speed_contest.setBackgroundResource(R.drawable.select_no);
        relay_contest.setBackgroundResource(R.drawable.select_no);
        grade_bg_lin.setBackgroundResource(R.drawable.grade_bg);
        title_bg.setBackgroundResource(R.drawable.title_bg);
        finend_bg.setBackgroundResource(R.drawable.title_bg);
        firends_bg_rea.setBackgroundResource(R.drawable.firends_bg);
        title_bg_ral.setBackgroundResource(R.drawable.title_bg);
        up_firends_bg.setBackgroundResource(R.drawable.firends_bg);
        athletics_user_image = (ImageView) findViewById(R.id.athletics_user_image);
        athletics_sex_image = (ImageView) findViewById(R.id.athletics_sex_image);
        Glide.with(this).load(UserInfo.headimgurl).into(athletics_user_image);
        if (UserInfo.sex == 1) {
            Glide.with(this).load(R.drawable.man).into(athletics_sex_image);
        } else {
            Glide.with(this).load(R.drawable.woman).into(athletics_sex_image);
        }
        if (UserInfo.nickname != null){
            if (UserInfo.nickname.length() > 6) {
                athletics_user_name.setText(UserInfo.nickname.substring(0, 6) + "");
            } else {
                athletics_user_name.setText(UserInfo.nickname + "");
            }
        }

        if (UserInfo.systemUserId.length() > 0) {
            athletics_user_id.setText(UserInfo.systemUserId.substring(0, 6));
        }

        //退出登录
        back = (ImageView) findViewById(R.id.back);
        exit_login = (ImageView) findViewById(R.id.exit_login);
        back.setOnClickListener(this);
        exit_login.setOnClickListener(this);

        mExitHintDialog = new ExitHintDialog(SportAthleticsActivity.this);
    }

    private List<String> generateTips() {
        List<String> tips = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            tips.add("飞跑通知第" + i + "条");
        }
        return tips;
    }


    @Override
    public void initLoad() {
        super.initLoad();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //查询用户信息
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetWorkAddress.appPlusNetWork)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        NetWorkService service = retrofit.create(NetWorkService.class);
        Call<UserBean> call = service.postUserMessage(systemUserId);
        call.enqueue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //创建房间
            case R.id.create_room:
                createRoomDialog.show();
                View room_view = createRoomDialog.getCustomView();
                final RelativeLayout realy_password = (RelativeLayout) room_view.findViewById(R.id.realy_password);
                room_speed_id = (TextView) room_view.findViewById(R.id.room_speed_id);
                StrokeTextViewd create_room_bt = (StrokeTextViewd) room_view.findViewById(R.id.create_room_bt);
                Typeface typeFace_sport = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/FANGZHENG.TTF");
                room_speed_id.setTypeface(typeFace_sport);
                //创建人数初始化
                limit_number = (MyRadioGroup) room_view.findViewById(R.id.limit_number);
                radio_4 = (RadioButton) room_view.findViewById(R.id.radio_4);
                radio_8 = (RadioButton) room_view.findViewById(R.id.radio_8);
                radio_12 = (RadioButton) room_view.findViewById(R.id.radio_12);
                radio_20 = (RadioButton) room_view.findViewById(R.id.radio_20);
                radio_24 = (RadioButton) room_view.findViewById(R.id.radio_24);
                radio_28 = (RadioButton) room_view.findViewById(R.id.radio_28);
                radio_32 = (RadioButton) room_view.findViewById(R.id.radio_32);
                radio_40 = (RadioButton) room_view.findViewById(R.id.radio_40);
                //是否加密初始化
                limit_loack = (MyRadioGroup) room_view.findViewById(R.id.limit_loack);
                yse_password = (RadioButton) room_view.findViewById(R.id.yse_password);
                no_password = (RadioButton) room_view.findViewById(R.id.no_password);
                //选中的人数结果
                limit_number.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                        if (radio_4.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_4.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_8.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_8.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_12.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_12.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_20.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_20.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_24.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_24.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_28.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_28.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_32.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_32.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (radio_40.getId() == checkedId) {
                            Toast.makeText(getBaseContext(), radio_40.getText().toString(), Toast.LENGTH_SHORT).show();

                        }


                    }
                });
                //是否加密的结果
                limit_loack.setOnCheckedChangeListener(new MyRadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(MyRadioGroup group, int checkedId) {
                        if (yse_password.getId() == checkedId) {
                            realy_password.setVisibility(View.VISIBLE);
                            Toast.makeText(getBaseContext(), yse_password.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                        if (no_password.getId() == checkedId) {
                            realy_password.setVisibility(View.GONE);
                            Toast.makeText(getBaseContext(), no_password.getText().toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                createRoomDialog.setOnCallbackLister(new CreateRoomDialog.ClickListenerInterface() {
                    @Override
                    public void click(int id, View view) {
                        switch (id) {
                            case R.id.athletics_room_select:
                                createRoomPopupWindow.showAsDropDown(view, 0, -10);

                                break;
                            case R.id.creatRoom_close_dialoag:
                                createRoomDialog.dismiss();
                                break;
                        }
                    }
                });
                break;
            case R.id.quick_join:

                break;
            case R.id.find_room:
                roomDialog.show();
                View view = roomDialog.getCustomView();
                number_1 = (TextView) view.findViewById(R.id.number_1);
                number_2 = (TextView) view.findViewById(R.id.number_2);
                number_3 = (TextView) view.findViewById(R.id.number_3);
                number_4 = (TextView) view.findViewById(R.id.number_4);
                number_5 = (TextView) view.findViewById(R.id.number_5);
                number_6 = (TextView) view.findViewById(R.id.number_6);
                mTextViews = new ArrayList<>();
                mTextViews.add(number_1);
                mTextViews.add(number_2);
                mTextViews.add(number_3);
                mTextViews.add(number_4);
                mTextViews.add(number_5);
                mTextViews.add(number_6);
                roomDialog.setOnCallbackLister(new FindRoomDialog.ClickListenerInterface() {
                    @Override
                    public void click(int id) {
                        switch (id) {
                            case R.id.keyboard_0:
                                setRoomNumber(0);
                                break;
                            case R.id.keyboard_1:
                                setRoomNumber(1);
                                break;
                            case R.id.keyboard_2:
                                setRoomNumber(2);
                                break;
                            case R.id.keyboard_3:
                                setRoomNumber(3);
                                break;
                            case R.id.keyboard_4:
                                setRoomNumber(4);
                                break;
                            case R.id.keyboard_5:
                                setRoomNumber(5);
                                break;
                            case R.id.keyboard_6:
                                setRoomNumber(6);
                                break;
                            case R.id.keyboard_7:
                                setRoomNumber(7);
                                break;
                            case R.id.keyboard_8:
                                setRoomNumber(8);
                                break;
                            case R.id.keyboard_9:
                                setRoomNumber(9);
                                break;
                            case R.id.keyboard_clean:
                                for (int x = 0; x < mTextViews.size(); x++) {
                                    mTextViews.get(x).setText("");
                                }
                                break;
                            case R.id.keyboard_delete:
                                deleteRoomNumber();
                                break;
                            case R.id.close_dialoag:
                                roomDialog.dismiss();
                                break;

                        }
                    }
                });
                break;
            case R.id.naili_contest:
                naili_contest.setBackgroundResource(R.mipmap.select);
                kcl_contest.setBackgroundResource(select_no);
                speed_contest.setBackgroundResource(select_no);
                relay_contest.setBackgroundResource(select_no);
                naili_contest_text.setTextColor(getResources().getColor(R.color.black));
                kcl_contest_text.setTextColor(getResources().getColor(R.color.white));
                speed_contest_text.setTextColor(getResources().getColor(R.color.white));
                relay_contest_text.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.kcl_contest:
                naili_contest.setBackgroundResource(select_no);
                kcl_contest.setBackgroundResource(R.mipmap.select);
                speed_contest.setBackgroundResource(select_no);
                relay_contest.setBackgroundResource(select_no);
                naili_contest_text.setTextColor(getResources().getColor(R.color.white));
                kcl_contest_text.setTextColor(getResources().getColor(R.color.black));
                speed_contest_text.setTextColor(getResources().getColor(R.color.white));
                relay_contest_text.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.speed_contest:
                naili_contest.setBackgroundResource(R.mipmap.select_no);
                kcl_contest.setBackgroundResource(select_no);
                speed_contest.setBackgroundResource(select);
                relay_contest.setBackgroundResource(select_no);
                naili_contest_text.setTextColor(getResources().getColor(R.color.white));
                kcl_contest_text.setTextColor(getResources().getColor(R.color.white));
                speed_contest_text.setTextColor(getResources().getColor(R.color.black));
                relay_contest_text.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.relay_contest:
                naili_contest.setBackgroundResource(R.mipmap.select_no);
                kcl_contest.setBackgroundResource(select_no);
                speed_contest.setBackgroundResource(select_no);
                relay_contest.setBackgroundResource(select);
                naili_contest_text.setTextColor(getResources().getColor(R.color.white));
                kcl_contest_text.setTextColor(getResources().getColor(R.color.white));
                speed_contest_text.setTextColor(getResources().getColor(R.color.white));
                relay_contest_text.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.athletics_select:
                mPopupWindow.showAsDropDown(v, 22, 15);
                break;
            case R.id.athletis_item_one:
                speed_id.setText("耐力竞技");
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_two:
                speed_id.setText("卡路里竞技");
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_three:
                speed_id.setText("速度竞技");
                mPopupWindow.dismiss();
                break;
            case R.id.athletis_item_four:
                speed_id.setText("接力赛竞技");
                mPopupWindow.dismiss();
                break;
            case R.id.room_athletis_item_one:
                room_speed_id.setText("耐力竞技");
                createRoomPopupWindow.dismiss();
                break;
            case R.id.room_athletis_item_two:
                room_speed_id.setText("卡路里竞技");
                createRoomPopupWindow.dismiss();
                break;
            case R.id.room_athletis_item_three:
                room_speed_id.setText("速度竞技");
                createRoomPopupWindow.dismiss();
                break;
            case R.id.room_athletis_item_four:
                room_speed_id.setText("接力赛竞技");
                createRoomPopupWindow.dismiss();
                break;

            case R.id.back:
                finish();
                break;
            //退出登录系统
            case R.id.exit_login:
                mExitHintDialog.show();
                //提示佩戴手环
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitHintDialog.dismiss();
                        UserInfo userInfo = new UserInfo("", "", "", 0, "");
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(SportAthleticsActivity.this, MainActivity.class));
                    }
                }, 5000);
                break;
        }
    }

    /**
     * 列表点击事件
     */
    @Override
    public void onItemClick(int id, int position, Object o) {
        join_room.setBackgroundResource(R.mipmap.join_bg_select);
        join_room_text.setTextColor(getResources().getColor(R.color.black));
        Toast.makeText(getBaseContext(), "点击的房间号" + o.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(int id) {

    }

    private void setRoomNumber(int number) {
        if (mTextViews.get(0).getText().toString().length() == 0 && mTextViews.get(1).getText().toString().length() == 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(0).setText("" + number);
            return;
        }

        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() == 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(1).setText("" + number);
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(2).setText("" + number);
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(3).setText("" + number);
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(4).setText("" + number);
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() > 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(5).setText("" + number);
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() > 0 && mTextViews.get(5).getText().toString().length() > 0) {
            Toast.makeText(getBaseContext(), "输入完成，开始查找", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    private void deleteRoomNumber() {
        if (mTextViews.get(0).getText().toString().length() == 0 && mTextViews.get(1).getText().toString().length() == 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            return;
        }

        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() == 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(0).setText("");
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() == 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(1).setText("");
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() == 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(2).setText("");
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() == 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(3).setText("");
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() > 0 && mTextViews.get(5).getText().toString().length() == 0) {
            mTextViews.get(4).setText("");
            return;
        }
        if (mTextViews.get(0).getText().toString().length() > 0 && mTextViews.get(1).getText().toString().length() > 0 && mTextViews.get(2).getText().toString().length() > 0
                && mTextViews.get(3).getText().toString().length() > 0 && mTextViews.get(4).getText().toString().length() > 0 && mTextViews.get(5).getText().toString().length() > 0) {
            mTextViews.get(5).setText("");
            return;
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        Glide.get(this).clearMemory();
        System.gc();
        super.onDestroy();
    }

    @Override
    public void onResponse(Call<UserBean> call, Response<UserBean> response) {
        UserBean bean = response.body();
        if (bean == null) {
            return;
        }

        switch (bean.getCode()) {
            case 5121:
                UserInfo.age = bean.getContent().getAge();
                UserInfo.weight = bean.getContent().getWeight();
                UserInfo.height = bean.getContent().getHeight();
                UserInfo.sumTime = bean.getContent().getSumTime();
                UserInfo.sumLength = bean.getContent().getSumLength();
                UserInfo.sumCalorie = bean.getContent().getSumCalorie();
                athletics_distance_number.setText(bean.getContent().getSumLength() + "");
                athletics_time_number.setText(bean.getContent().getSumTime() + "");
                athletics_heat_name.setText(bean.getContent().getSumCalorie() + "");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SportAthletics Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        mClient.connect();
        AppIndex.AppIndexApi.start(mClient, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(mClient, getIndexApiAction());
        mClient.disconnect();
    }
}
