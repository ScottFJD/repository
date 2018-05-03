package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.*;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;

import net.fitrun.fitrungame.BaseActivity;
import net.fitrun.fitrungame.MainActivity;
import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.AppManager;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.greendao.gen.GreenDaoManager;
import net.fitrun.fitrungame.greendao.gen.UserSportInfoDao;
import net.fitrun.fitrungame.sportSelect.ExitHintDialog;
import net.fitrun.fitrungame.sportSelect.SportSelectActivity;
import net.fitrun.fitrungame.sportSelect.speedy.SpeedSportStatementActivity;
import net.fitrun.fitrungame.sportSelect.speedy.SpeedySportActivity;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserInfo;
import net.fitrun.fitrungame.sportSelect.speedy.bean.UserSportInfo;
import net.fitrun.fitrungame.view.DensityUtil;
import net.fitrun.fitrungame.zxing.encoding.EncodingUtils;

import org.greenrobot.greendao.query.QueryBuilder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

import static android.R.id.message;
import static net.fitrun.fitrungame.R.id.couesr_recycle;
import static net.fitrun.fitrungame.R.id.current_time;
import static net.fitrun.fitrungame.R.id.iv_zxing;

/**
 * Created by 晁东洋 on 2017/4/28.
 * 课程运动报告页面
 */

public class CourseSportStatementActivity extends BaseActivity implements View.OnClickListener{

    ImageView back,exit_login,iv_zxing;
    TextView course_grades,sport_time,sport_dis,sport_kcal
            ,sport_max_heart,sport_max_speed,sport_mean_speed,sport_mean_tempo_speed
            ,sport_max_tempo_speed,sport_min_tempo_speed,cureent_time,course_name,user_comment;
    RecyclerView tempo_recycle;
    TempoListAdapter mTempoListAdapter;


    //接受传递过来的数据
    private List<Integer> heartList;
    private List<Integer> speedList;
    private List<Integer> paceList;
    private String time;
    private String dis;
    private String kcal;
    private String courseName;
    private String averageSpeed;
    //最低心率
    private int minHeart;
    //最高心率
    private int maxHeart;
    //评分
    private String score;
    //评价
    private String comment;
    //分享连接
    private String webUrl;


    /**
     * 关于图表的控件的一些初始化内容
     *
     * */
     /*=========== 控件相关 ==========*/
    private LineChartView mLineChartView_kcal,lvc_speed;//线性图表控件

    /*=========== 数据相关 ==========*/
    private LineChartData mLineData;                    //图表数据
    private int numberOfLines = 1;                      //图上折线/曲线的显示条数
    private int maxNumberOfLines = 1;                   //图上折线/曲线的最多条数
    private int numberOfPoints = 0;                    //心率图上的节点数
    private int numberofSpeedPoints = 0;               //速度图上的节点数

    /*=========== 状态相关 ==========*/
    private boolean isHasAxes = true;                   //是否显示坐标轴
    private boolean isHasAxesNames = false;              //是否显示坐标轴名称
    private boolean isHasLines = true;                  //是否显示折线/曲线
    private boolean isHasPoints = false;                 //是否显示线上的节点
    private boolean isFilled = true;                   //是否填充线下方区域
    private boolean isHasPointsLabels = false;          //是否显示节点上的标签信息
    private boolean isCubic = true;                    //是否是立体的
    private boolean isPointsHasSelected = false;        //设置节点点击后效果(消失/显示标签)
    private boolean isPointsHaveDifferentColor;         //节点是否有不同的颜色

    /*=========== 其他相关 ==========*/
    private ValueShape pointsShape = ValueShape.CIRCLE; //点的形状(圆/方/菱形)
    //心率的数据
    float[][] randomNumbersTab ;
    //速度的数据
    float[][] speedRandomNumbersTab ;

    //遍历数据库中的值
    UserSportInfoDao mSportInfoDao;
    //分割线
    ImageView line_stop,line_start;

    //退出登录的按钮
    private AlertView mAlertView;

    //退出的提示
    ExitHintDialog mExitHintDialog;

    //最大配速和最低配速
    int maxPac , minPac;

    @Override
    protected int getContentViewId() {
        return R.layout.layout_course_sport_statement;
    }

    @Override
    public void initView() {
        super.initView();
        back = (ImageView)findViewById(R.id.back);
        iv_zxing = (ImageView)findViewById(R.id.iv_zxing);
        back.setOnClickListener(this);
        exit_login = (ImageView)findViewById(R.id.exit_login);
        exit_login.setOnClickListener(this);
        course_name = (TextView)findViewById(R.id.course_name);
        course_grades = (TextView)findViewById(R.id.course_grades);
        sport_time = (TextView)findViewById(R.id.sport_time);
        sport_dis = (TextView)findViewById(R.id.sport_dis);
        sport_kcal = (TextView)findViewById(R.id.sport_kcal);
        sport_max_heart = (TextView)findViewById(R.id.sport_max_heart);
        sport_max_speed = (TextView)findViewById(R.id.sport_max_speed);
        sport_mean_speed = (TextView)findViewById(R.id.sport_mean_speed);
        sport_mean_tempo_speed = (TextView)findViewById(R.id.sport_mean_tempo_speed);
        sport_max_tempo_speed = (TextView)findViewById(R.id.sport_max_tempo_speed);
        sport_min_tempo_speed = (TextView)findViewById(R.id.sport_min_tempo_speed);
        user_comment = (TextView)findViewById(R.id.user_comment);
        cureent_time = (TextView)findViewById(R.id.cureent_time);
        SimpleDateFormat sDateFormat  = new  SimpleDateFormat("yyyy/MM/dd");
        String date  = sDateFormat.format(new java.util.Date());
        cureent_time.setText(date);
        //设置字体
        Typeface typeFace =Typeface.createFromAsset(getBaseContext().getAssets(),"fonts/AGENCYB.TTF");
        course_grades.setTypeface(typeFace);
        sport_time.setTypeface(typeFace);
        sport_dis.setTypeface(typeFace);
        sport_kcal.setTypeface(typeFace);
        sport_max_heart.setTypeface(typeFace);
        sport_max_speed.setTypeface(typeFace);
        sport_mean_speed.setTypeface(typeFace);
        sport_mean_tempo_speed.setTypeface(typeFace);
        sport_max_tempo_speed.setTypeface(typeFace);
        sport_min_tempo_speed.setTypeface(typeFace);

        tempo_recycle = (RecyclerView)findViewById(R.id.tempo_recycle);
        //设置布局管理,垂直布局
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        tempo_recycle.setLayoutManager(manager);

        mLineChartView_kcal = (LineChartView) findViewById(R.id.lvc_kcal);
        mLineChartView_kcal.setZoomEnabled(false);
        lvc_speed = (LineChartView)findViewById(R.id.lvc_speed);
        lvc_speed.setZoomEnabled(false);

        /**
         * 禁用视图重新计算 主要用于图表在变化时动态更改，不是重新计算
         * 类似于ListView中数据变化时，只需notifyDataSetChanged()，而不用重新setAdapter()
         */
        mLineChartView_kcal.setViewportCalculationEnabled(false);
        lvc_speed.setViewportCalculationEnabled(false);

        //分割线
        line_stop = (ImageView)findViewById(R.id.line_stop);
        line_start = (ImageView)findViewById(R.id.line_start);

        mExitHintDialog = new ExitHintDialog(CourseSportStatementActivity.this);
    }



    @Override
    public void initLoad() {
        super.initLoad();
        //接收传递过来的数据
        //接收传递过来的数据
        Intent intent = getIntent();
        heartList = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("heartList");
        speedList = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("speedList");
        paceList = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("paceList");
        Log.e("接收到的心率数据",heartList.size()+"");
        Log.e("接收到的速度数据",speedList.size()+"");
        Log.e("转换后运动的配速的数据",paceList.size()+"");
        numberOfPoints = heartList.size();
        numberofSpeedPoints = speedList.size();
        time = getIntent().getStringExtra("time");
        dis = getIntent().getStringExtra("dis");
        kcal = getIntent().getStringExtra("kcal");
        averageSpeed = getIntent().getStringExtra("averageSpeed");
        courseName = getIntent().getStringExtra("courseName");
        minHeart = Integer.parseInt(intent.getStringExtra("minHeart"));
        maxHeart = Integer.parseInt(intent.getStringExtra("maxHeart"));

        //用户的评分，评语，分享连接
        score = getIntent().getStringExtra("score");
        comment = getIntent().getStringExtra("comment");
        webUrl = getIntent().getStringExtra("webUrl");



        Log.e("接收到的最小的心率",paceList.size()+"");
        randomNumbersTab = new float[maxNumberOfLines][numberOfPoints]; //将线上的点放在一个数组中
        speedRandomNumbersTab = new float[maxNumberOfLines][numberofSpeedPoints]; //将线上的点放在一个数组中

        //设置值
        course_name.setText(courseName+"运动报告");
        sport_time.setText(Utils.secToTime(Integer.parseInt(time)).substring(0, 2)+"′"+Utils.secToTime(Integer.parseInt(time)).substring(3, 5)+"″");
        sport_dis.setText(dis+"km");
        sport_kcal.setText(kcal+"kcal");
        sport_mean_speed.setText(averageSpeed+"");

        //查询数据库中符合记录的心率数量
/*
        QueryBuilder<UserSportInfo> db = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().queryBuilder();
        db.where(UserSportInfoDao.Properties.Heart.ge(minHeart),UserSportInfoDao.Properties.Heart.le(maxHeart));
        List<UserSportInfo> heartList = db.list();
        int count = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().loadAll().size();
        Log.e("接收到的符合范围的数据",heartList.size()+"");
        Log.e("接收到的记录的总的数据",count+"");
        if (heartList.size() >0){
            double zong = (double)count;
            double scope = heartList.size();
            Log.e("接收到的计算结果",(scope/zong)+"");
            Log.e("接收到的计算结果",(scope/zong)*40+"");
            Log.e("接收到的计算结果",(scope/zong)*40 +60+"");
            int grades = (int) ((scope/zong)*40 +60);
            course_grades.setText(grades+"");

        }else {
            Log.e("接收到的并未参加计算计算结果",(heartList.size()/count)*40 +60+"");
            course_grades.setText(60+"");
        }*/
        course_grades.setText(score+"");
        user_comment.setText(comment+"");



        //查询数据库记录的最大心率最大速度等值
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursor = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().getDatabase().rawQuery("select max(SPEED) from USER_SPORT_INFO",null);
                while (cursor.moveToNext()) {
                    Log.e("最大的速度",cursor.getDouble(0)+"");
                    Message message = new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("juli",cursor.getDouble(0)+"");
                    message.setData(bundle);//bundle传值，耗时，效率低  
                    message.what=1;//标志是哪个线程传数据  
                    updateHandler.sendMessage(message);//发送message信息  

                }
            }
        }).start();

        //查询最大心率
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursorheart = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().getDatabase().rawQuery("select max(HEART) from USER_SPORT_INFO",null);
                while (cursorheart.moveToNext()) {
                    Log.e("最大的心率",cursorheart.getInt(0)+"");
                    Message message = new Message();
                    Bundle bundle=new Bundle();
                    bundle.putString("xinlv",cursorheart.getInt(0)+"");
                    message.setData(bundle);//bundle传值，耗时，效率低  
                    message.what=2;//标志是哪个线程传数据  
                    updateHandler.sendMessage(message);//发送message信息  

                }
            }
        }).start();


        //最大配速的查询
        new Thread(new Runnable() {
            @Override
            public void run() {
                Cursor cursorMaxPace = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().getDatabase().rawQuery("select min(PACE) from USER_SPORT_INFO",null);
                while (cursorMaxPace.moveToNext()) {
                    Log.e("最大的配速",cursorMaxPace.getInt(0)+"");
                    if (cursorMaxPace.getInt(0) >= 3600){
                        maxPac = 3600;
                        Message message = new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("maxPac",maxPac+"");
                        message.setData(bundle);//bundle传值，耗时，效率低  
                        message.what=4;//标志是哪个线程传数据 
                        message.arg1 = maxPac;
                        updateHandler.sendMessage(message);//发送message信息  
                    }else {
                        maxPac = cursorMaxPace.getInt(0);
                        Message message = new Message();
                        Bundle bundle=new Bundle();
                        bundle.putString("maxPac",maxPac+"");
                        message.setData(bundle);//bundle传值，耗时，效率低  
                        message.what=5;//标志是哪个线程传数据  
                        message.arg1 = maxPac;
                        updateHandler.sendMessage(message);//发送message信息  
                    }
                }
            }
        }).start();

        setPointsValues();          //设置心率每条线的节点值
        setLinesDatas();            //设置每条线的一些属性
        setPointsSpeedValues();
        setLinesSpeedDatas();       //设置速度节点的数据
        resetViewport();            //计算并绘图
        resetSpeedViewport();    //绘制速度的图标
        create();



        //配速的图标
       /* List<Integer> list = new ArrayList<>();
        Random random = new Random();
        int max=1200;
        int min=240;
        for (int x =0;x<6;x++){
            list.add( random.nextInt(max)%(max-min+1) + min);
        }*/

        double juli = Double.parseDouble(dis);
        int shijian = Integer.parseInt(time);
        if (juli - paceList.size() >0){
           int meanPac = (int) ((shijian - paceList.get(paceList.size() -1))/(juli - paceList.size()));
            Log.e("估算的配速当前距离的配速",meanPac+"秒");
            paceList.add(meanPac + paceList.get(paceList.size() -1));
        }
        mTempoListAdapter = new TempoListAdapter(this,paceList);
        tempo_recycle.setAdapter(mTempoListAdapter);

        //心率图标的分割线
        setLayout(line_start,85,220-minHeart+55);
        setLayout(line_stop,85,220-maxHeart+55);
    }


    /**
     * 异步查询数据库之后更新界面
     * */
   final Handler updateHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    sport_max_speed.setText(msg.getData().getString("juli")+"km/h");
                    break;
                case 2:
                    sport_max_heart.setText(msg.getData().getString("xinlv")+"min");
                    break;
                case 3:

                    break;
                case 4:

                    sport_max_tempo_speed.setText(60+"′"+00+"″");
                    //最慢配速的查询
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Cursor cursorMinPace = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().getDatabase().rawQuery("select max(PACE) from USER_SPORT_INFO",null);
                            while (cursorMinPace.moveToNext()) {
                                Log.e("最慢的配速",cursorMinPace.getInt(0)+"");
                                if (cursorMinPace.getInt(0) >= 3600){
                                    minPac = 3600;
                                    Message message = new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("minPac",minPac+"");
                                    message.setData(bundle);//bundle传值，耗时，效率低  
                                    message.what=6;//标志是哪个线程传数据  
                                    message.arg1 = minPac;
                                    updateHandler.sendMessage(message);//发送message信息 

                                }else {
                                    minPac = cursorMinPace.getInt(0);
                                    Message message = new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("minPac",minPac+"");
                                    message.setData(bundle);//bundle传值，耗时，效率低  
                                    message.what=7;//标志是哪个线程传数据  
                                    message.arg1 = minPac;
                                    updateHandler.sendMessage(message);//发送message信息 
                                }
                            }
                        }
                    }).start();
                    break;
                case 5:
                    sport_max_tempo_speed.setText(Utils.secToTime(msg.arg1).substring(0, 2)+"′"+Utils.secToTime(msg.arg1).substring(3, 5)+"″");
                    //最慢配速的查询
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Cursor cursorMinPace = GreenDaoManager.getInstance().getSession().getUserSportInfoDao().getDatabase().rawQuery("select max(PACE) from USER_SPORT_INFO",null);
                            while (cursorMinPace.moveToNext()) {
                                Log.e("最慢的配速",cursorMinPace.getInt(0)+"");
                                if (cursorMinPace.getInt(0) >= 3600){
                                    minPac = 3600;
                                    Message message = new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("minPac",minPac+"");
                                    message.setData(bundle);//bundle传值，耗时，效率低  
                                    message.what=6;//标志是哪个线程传数据  
                                    message.arg1 = minPac;
                                    updateHandler.sendMessage(message);//发送message信息 

                                }else {
                                    minPac = cursorMinPace.getInt(0);
                                    Message message = new Message();
                                    Bundle bundle=new Bundle();
                                    bundle.putString("minPac",minPac+"");
                                    message.setData(bundle);//bundle传值，耗时，效率低  
                                    message.what=7;//标志是哪个线程传数据  
                                    message.arg1 = minPac;
                                    updateHandler.sendMessage(message);//发送message信息 
                                }
                            }
                        }
                    }).start();
                    break;
                case 6:
                    sport_min_tempo_speed.setText(60+"′"+00+"″");
                    //平均配速
                    int meanPac = (int) (maxPac+minPac)/2;
                    sport_mean_tempo_speed.setText(Utils.secToTime(meanPac).substring(0, 2)+"′"+Utils.secToTime(meanPac).substring(3, 5)+"″");
                    break;
                case 7:
                    //平均配速
                    int meanPac_x = (int) (maxPac+minPac)/2;
                    sport_mean_tempo_speed.setText(Utils.secToTime(meanPac_x).substring(0, 2)+"′"+Utils.secToTime(meanPac_x).substring(3, 5)+"″");
                    sport_min_tempo_speed.setText(Utils.secToTime(msg.arg1).substring(0, 2)+"′"+Utils.secToTime(msg.arg1).substring(3, 5)+"″");
                    break;
            }
        }
    };


    /* 
     * 设置控件所在的位置YY，并且不改变宽高， 
     * XY为绝对位置 
     */
    public static void setLayout(View view,int x,int y){
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(view.getLayoutParams());
        margin.setMargins(x,y, x+margin.width, y+ margin.height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(margin);
        view.setLayoutParams(layoutParams);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                mSportInfoDao.deleteAll();
                finish();
                break;
            case R.id.exit_login:
                mExitHintDialog.show();
                mSportInfoDao.deleteAll();
                //提示佩戴手环
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mExitHintDialog.dismiss();
                        UserInfo userInfo = new UserInfo("","","",0,"");
                        AppManager.getAppManager().finishAllActivity();
                        startActivity(new Intent(CourseSportStatementActivity.this, MainActivity.class));
                    }
                },5000);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mSportInfoDao.deleteAll();
        AppManager.getAppManager().finishActivity(SpeedSportStatementActivity.class);
    }

    /**
     * 创建二维码并将图片保存在本地
     */
    private void create() {
        int width = DensityUtil.dip2px(this, 200);
        Bitmap bitmap = EncodingUtils.createQRCode(webUrl,
                width, width, BitmapFactory.decodeResource(getResources(),
                        R.mipmap.ic_launcher));
        iv_zxing.setImageBitmap(bitmap);
    }

    /**
     * 利用随机数设置每条线对应节点的值
     */
    private void setPointsValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            Log.e("心率的节点",heartList.size()+"");
            for (int j = 0; j < numberOfPoints; ++j) {
                //  Log.e("心率记录的值",heartList.get(j)+"");
                randomNumbersTab[0][j] = heartList.get(j);
            }
        }
    }

    /**
     * 利用随机数设置每条线对应节点的速度值
     */
    private void setPointsSpeedValues() {
        for (int i = 0; i < maxNumberOfLines; ++i) {
            Log.e("速度的节点",speedList.size()+"");
            for (int j = 0; j < numberofSpeedPoints; ++j) {
                speedRandomNumbersTab[0][j] = speedList.get(j);
            }
        }
    }

    /**
     * 设置心率线的相关数据
     */
    private void setLinesDatas() {
        List<Line> lines = new ArrayList<>();
        //循环将每条线都设置成对应的属性
        for (int i = 0; i < numberOfLines; ++i) {
            //节点的值
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberOfPoints; ++j) {
                values.add(new PointValue(j/10.0f, randomNumbersTab[i][j]));
            }

            /*========== 设置线的一些属性 ==========*/
            Line line = new Line(values);               //根据值来创建一条线
            line.setColor(getResources().getColor(R.color.red));        //设置线的颜色
            line.setShape(pointsShape);                 //设置点的形状
            line.setHasLines(isHasLines);               //设置是否显示线
            line.setHasPoints(isHasPoints);             //设置是否显示节点
            line.setCubic(isCubic);                     //设置线是否立体或其他效果
            line.setFilled(isFilled);                   //设置是否填充线下方区域
            line.setHasLabels(isHasPointsLabels);       //设置是否显示节点标签
            //设置节点点击的效果
            line.setHasLabelsOnlyForSelected(isPointsHasSelected);
            //如果节点与线有不同颜色 则设置不同颜色
            if (isPointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        mLineData = new LineChartData(lines);                      //将所有的线加入线数据类中
        mLineData.setBaseValue(Float.NEGATIVE_INFINITY);
        /* 其他的一些属性方法 可自行查看效果
         * mLineData.setValueLabelBackgroundAuto(true);            //设置数据背景是否跟随节点颜色
         * mLineData.setValueLabelBackgroundColor(Color.BLUE);     //设置数据背景颜色
         * mLineData.setValueLabelBackgroundEnabled(true);         //设置是否有数据背景
         * mLineData.setValueLabelsTextColor(Color.RED);           //设置数据文字颜色
         * mLineData.setValueLabelTextSize(15);                    //设置数据文字大小
         * mLineData.setValueLabelTypeface(Typeface.MONOSPACE);    //设置数据文字样式
        */

        //如果显示坐标轴
        if (isHasAxes) {
            Axis axisX = new Axis().setHasLines(true);                    //X轴
            Axis axisY = new Axis().setHasLines(true);  //Y轴
            axisX.setTextColor(Color.WHITE);             //X轴灰色
            axisY.setTextColor(Color.WHITE);             //Y轴灰色
            axisX.setTextSize(19);
            axisY.setTextSize(19);
            axisX.setLineColor(R.color.white);
            axisY.setLineColor(R.color.white); //此方法是设置图表的网格线颜色 并不是轴本身颜色
            //如果显示名称
            if (isHasAxesNames) {
                axisX.setName("Axis X");                //设置名称
                axisY.setName("Axis Y");
            }
            mLineData.setAxisXBottom(axisX);            //设置X轴位置 下方
            mLineData.setAxisYLeft(axisY);              //设置Y轴位置 左边
        } else {
            mLineData.setAxisXBottom(null);
            mLineData.setAxisYLeft(null);
        }

        mLineChartView_kcal.setLineChartData(mLineData);    //设置图表控件
    }

    /**
     * 设置速度线的相关数据
     */
    private void setLinesSpeedDatas() {
        List<Line> lines = new ArrayList<>();
        //循环将每条线都设置成对应的属性
        for (int i = 0; i < numberOfLines; ++i) {
            //节点的值
            List<PointValue> values = new ArrayList<>();
            for (int j = 0; j < numberofSpeedPoints; ++j) {
                values.add(new PointValue(j/10.0f, speedRandomNumbersTab[i][j]));
            }

            /*========== 设置线的一些属性 ==========*/
            Line line = new Line(values);               //根据值来创建一条线
            line.setColor(getResources().getColor(R.color.blue));        //设置线的颜色
            line.setShape(pointsShape);                 //设置点的形状
            line.setHasLines(isHasLines);               //设置是否显示线
            line.setHasPoints(isHasPoints);             //设置是否显示节点
            line.setCubic(isCubic);                     //设置线是否立体或其他效果
            line.setFilled(isFilled);                   //设置是否填充线下方区域
            line.setHasLabels(isHasPointsLabels);       //设置是否显示节点标签
            //设置节点点击的效果
            line.setHasLabelsOnlyForSelected(isPointsHasSelected);
            //如果节点与线有不同颜色 则设置不同颜色
            if (isPointsHaveDifferentColor) {
                line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
            }
            lines.add(line);
        }

        mLineData = new LineChartData(lines);                      //将所有的线加入线数据类中
        mLineData.setBaseValue(Float.NEGATIVE_INFINITY);
        /* 其他的一些属性方法 可自行查看效果
         * mLineData.setValueLabelBackgroundAuto(true);            //设置数据背景是否跟随节点颜色
         * mLineData.setValueLabelBackgroundColor(Color.BLUE);     //设置数据背景颜色
         * mLineData.setValueLabelBackgroundEnabled(true);         //设置是否有数据背景
         * mLineData.setValueLabelsTextColor(Color.RED);           //设置数据文字颜色
         * mLineData.setValueLabelTextSize(15);                    //设置数据文字大小
         * mLineData.setValueLabelTypeface(Typeface.MONOSPACE);    //设置数据文字样式
        */

        //如果显示坐标轴
        if (isHasAxes) {
            Axis axisX = new Axis().setHasLines(true);                    //X轴
            Axis axisY = new Axis().setHasLines(true);  //Y轴
            axisX.setTextColor(Color.WHITE);             //X轴灰色
            axisY.setTextColor(Color.WHITE);             //Y轴灰色
            axisX.setTextSize(19);
            axisY.setTextSize(19);
            axisX.setLineColor(R.color.white);
            axisY.setLineColor(R.color.white); //此方法是设置图表的网格线颜色 并不是轴本身颜色
            //如果显示名称
            if (isHasAxesNames) {
                axisX.setName("Axis X");                //设置名称
                axisY.setName("Axis Y");
            }
            mLineData.setAxisXBottom(axisX);            //设置X轴位置 下方
            mLineData.setAxisYLeft(axisY);              //设置Y轴位置 左边
        } else {
            mLineData.setAxisXBottom(null);
            mLineData.setAxisYLeft(null);
        }

        lvc_speed.setLineChartData(mLineData);    //设置图表控件
    }

    /**
     * 重点方法，计算绘制图表
     */
    private void resetViewport() {
        //创建一个图标视图 大小为控件的最大大小
        final Viewport v = new Viewport(mLineChartView_kcal.getMaximumViewport());
        v.left = 0;                             //坐标原点在左下
        v.bottom = 60;
        v.top = 220;                            //最高点为200
        v.right = (numberOfPoints -1)/10.0f;           //右边为点 坐标从0开始 点号从1 需要 -1
        mLineChartView_kcal.setMaximumViewport(v);   //给最大的视图设置 相当于原图
        mLineChartView_kcal.setCurrentViewport(v);   //给当前的视图设置 相当于当前展示的图
    }

    /**
     * 重点方法，计算绘制图表
     */
    private void resetSpeedViewport() {
        //创建一个图标视图 大小为控件的最大大小
        final Viewport v = new Viewport(mLineChartView_kcal.getMaximumViewport());
        v.left = 0;                             //坐标原点在左下
        v.bottom = 0;
        v.top = 20;                            //最高点为100
        v.right = (numberofSpeedPoints -1)/10.0f;           //右边为点 坐标从0开始 点号从1 需要 -1
        lvc_speed.setMaximumViewport(v);   //给最大的视图设置 相当于原图
        lvc_speed.setCurrentViewport(v);   //给当前的视图设置 相当于当前展示的图
    }


}
