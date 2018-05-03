package net.fitrun.fitrungame.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import net.fitrun.fitrungame.R;


/**
 * Created by limengjie on 2017/3/1.
 * 仪表盘
 */

public class LmjDashBoard extends View {

    private Paint paint_text = new Paint();

    private Paint paint_KeDu = new Paint();
    private Paint paint_shader = new Paint();
    private Paint paint_KeDuBig = new Paint();
    private Paint paint_KeDuSmall = new Paint();
    private Paint paint_bg = new Paint();
    private Paint paint_zhizhen = new Paint();
    private int innerPadding = 0;


    private int[] datas_kudu = {50, 70, 90, 110, 130, 150, 170, 190, 200};

    private int totalDrgee = 180;

    private int radius = 0;
    private Context context;
    private int bwidth = 0;
    private int bheight = 0;

    private int arcWidth = 0;//圆弧的宽度
    private int outLineWidth = 0;//最外面的线条的宽度
    private int smallKeduLenth = 0;//短刻度线的长度
    private int bigKeduLength = 0;//长刻度线的长度
    private int bigKeduWidth = 0;//长刻度线宽度
    private int textSize = 20;
    private String Tag = "LmjDashBoard";

    private Path path_arc = new Path();
    private RectF acr1;
    private RectF acr2;
    private SweepGradient mSweepGradient;
    private LinearGradient linearGradient;
    private int currentValue = 0;//现在的值是多少
    private float currentDrgee = 0;//根据current，计算出指针的角度

    private int zhizhen_circleRadus = 0;
    private int zhizhen_width = 0;

    private int[] fengeData = {100, 150};//分割线的值
    private Rect textrect;


    public LmjDashBoard(Context context) {
        this(context, null);
    }

    public LmjDashBoard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LmjDashBoard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initParam();
        initPaint();
    }

    /**
     * 初始化参数，半径大小，表盘的宽度
     */
    public void initParam() {
        arcWidth = DensityUtil.dip2px(context, 75);
        outLineWidth = DensityUtil.dip2px(context, 4);
        bigKeduWidth = DensityUtil.dip2px(context, 6);
        smallKeduLenth = DensityUtil.dip2px(context, 12);
        bigKeduLength = DensityUtil.dip2px(context, 18);
        radius = DensityUtil.dip2px(context, 80);//默认80dp
        textSize = DensityUtil.dip2px(context, 16);
        innerPadding = DensityUtil.dip2px(context, 4);

        zhizhen_circleRadus = DensityUtil.dip2px(context, 12);
        zhizhen_width = DensityUtil.dip2px(context, 6);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //由于是一个半圆，所以宽度高度要一致，这里如果宽度和高度都是wrapcontent，就使用默认值

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {//给定了大小
            bwidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            bwidth = DensityUtil.dip2px(context, 80);//默认80dp,图片给的134px，我这是80dp。可以自己修改
        }
        bheight = bwidth / 2;
        radius = bheight - innerPadding;
        setMeasuredDimension(bwidth, bheight+DensityUtil.dip2px(context,10));
        if (null == acr1) {
            initPath();
        }

        Log.i(Tag, "LmjDashBoard");


    }

    public void initPath() {
        Log.i(Tag, "initPath");
        int pianyi = innerPadding;
        acr1 = new RectF(0 + pianyi, 0 + pianyi, bwidth - pianyi, bwidth - pianyi);
        acr2 = new RectF(0 + arcWidth + pianyi, 0 + arcWidth + pianyi, bwidth - arcWidth - pianyi, bwidth - arcWidth - pianyi);


        path_arc.arcTo(acr1, 180, 180);
        path_arc.lineTo(bwidth - arcWidth - pianyi, bheight);

        path_arc.arcTo(acr2, 0, -180);
        path_arc.close();
    }

    public void initPaint() {
        paint_KeDu.setColor(Color.WHITE);
        paint_KeDu.setStrokeWidth(1);
        paint_KeDu.setStyle(Paint.Style.STROKE);
        paint_KeDu.setAntiAlias(true);

        paint_KeDuBig.setColor(Color.WHITE);
        paint_KeDuBig.setStrokeWidth(bigKeduWidth);
        paint_KeDuBig.setStyle(Paint.Style.STROKE);
        paint_KeDuBig.setAntiAlias(true);

        paint_KeDuSmall.setColor(Color.WHITE);
        paint_KeDuSmall.setStrokeWidth(2);
        paint_KeDuSmall.setStyle(Paint.Style.STROKE);
        paint_zhizhen.setColor(Color.RED);
        paint_zhizhen.setAntiAlias(true);

        paint_text.setColor(Color.WHITE);
        paint_text.setTextSize(textSize);
        paint_text.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKeDu(canvas);
        drawZhiZhen(canvas);

    }

    //绘制刻度
    public void drawKeDu(Canvas canvas) {
        if (null == mSweepGradient) {

            linearGradient = new LinearGradient(0, 0, bwidth, 0, new int[]{

                    getResources().getColor(R.color.shadercolor1),
                    getResources().getColor(R.color.shadercolor2),
                    getResources().getColor(R.color.shadercolor3)}, null, Shader.TileMode.CLAMP);

            paint_shader.setShader(linearGradient);
        }
        canvas.drawPath(path_arc, paint_shader);
        //绘制圆弧边框
        canvas.drawPath(path_arc, paint_KeDu);

        canvas.save();
        //绘制刻度
        int keduNum = 10 * (datas_kudu.length - 1) + 1;//一共有多少个刻度
        float preDrgee = (float) (totalDrgee * 1.0 / (keduNum-1));
        float rtotal = 0;
        for (int i = 0; i < keduNum; i++) {
            if (i % 10 == 0) {
               canvas.drawLine(0 + innerPadding, bheight , bigKeduLength, bheight , paint_KeDuBig);
            } else {
                canvas.drawLine(0 + innerPadding, bheight, smallKeduLenth, bheight, paint_KeDuSmall);
            }
            rtotal += preDrgee;
            if (i != keduNum - 1) {
                canvas.rotate(preDrgee, getWidth() / 2, getWidth()/2);
            } else {
                canvas.rotate(180 - totalDrgee, getWidth() / 2, getWidth()/2);

            }

        }
        canvas.restore();
        canvas.save();
        canvas.translate(getWidth() / 2, getWidth() / 2);

        for (int i = 0; i < datas_kudu.length; i++) {
            //绘制文字
            String text = datas_kudu[i] + "";
            double drgee = (totalDrgee*1.0f / (datas_kudu.length - 1)) * i * 1.0d;
            WandH wh = getTextRotateXY(drgee, text,i);
//            if(wh.width<0){
//                wh.height=wh.height-10;
//            }

            canvas.drawText(text, wh.width, wh.height, paint_text);
        }
        canvas.restore();
        canvas.save();
        //绘制分割线，计算每个分割线的角度，进行旋转

        for (int i = 0; i < fengeData.length; i++) {
            canvas.save();
            float fengeDrgee = getValueForDrgee(fengeData[i]);
            canvas.rotate(fengeDrgee, getWidth() / 2, getWidth()/2);
            canvas.drawLine(innerPadding,bheight-innerPadding,innerPadding+arcWidth,bheight-innerPadding,paint_KeDuSmall);
            canvas.restore();
        }
        canvas.save();

    }

    /**
     * 绘制指针
     *
     * @param canvas
     */
    public void drawZhiZhen(Canvas canvas) {
        //先在中心位置画一个圆形
        //先绘制中心的红色圆形
        canvas.restore();
        canvas.save();

        paint_zhizhen.setColor(Color.RED);
        int rx = getWidth() / 2;
        int ry = getWidth() / 2 ;
//        currentDrgee = (float) (currentDrgee - Math.atan(zhizhen_circleRadus * 1.0d / (getWidth() / 2)) * 180 / Math.PI);

        canvas.rotate(currentDrgee, rx, ry);
        canvas.drawCircle(rx, ry, zhizhen_circleRadus, paint_zhizhen);
        Path path_zhizhen = new Path();
        path_zhizhen.moveTo(getWidth() / 2, ry+zhizhen_circleRadus/2);
        path_zhizhen.lineTo(getWidth() / 2, ry-zhizhen_circleRadus/2);
        path_zhizhen.lineTo(innerPadding * 2 + 20, ry);
        path_zhizhen.close();
        canvas.drawPath(path_zhizhen, paint_zhizhen);
        paint_zhizhen.setColor(Color.WHITE);
        canvas.drawCircle(rx, ry, zhizhen_circleRadus / 2, paint_zhizhen);

        canvas.save();


    }

    public WandH getTextRotateXY(double drgee, String str, int index) {
        drgee = (float) ((Math.PI / 180.0f) * drgee);
        int innerRadius = getWidth()/2-innerPadding*3-bigKeduLength;
        int wp = 0;
        int width = -(int) (Math.cos(drgee) * innerRadius);
        if(width>0){
            width-=wp;
        }else if(width<0){
            width+=wp;
        }
        int height = -(int) (Math.sin(drgee) * innerRadius);
        if(null==textrect){
            textrect = new Rect();
            paint_text.getTextBounds("12", 0, "12".length(), textrect);
        }
        int tw = textrect.width();
        int th = textrect.height();
        if(index==0||index==datas_kudu.length-1){
            height = height-DensityUtil.dip2px(context,2);
        }

        if(datas_kudu.length%2!=0&&index==datas_kudu.length/2){//基数个，并且是中间那个
            width = width-tw/2;

        }else{
            if (width > 0) {
                width = width - tw;
            }
        }
        if(str.length()==2){
            width = width - textrect.width()/2;
        }

        Log.i(Tag, "width:" + width + ",height:" + height + ",drgee:" + drgee + "innerRadius:" + innerRadius);
//        Log.i(Tag,"drgee:"+(Math.sin(drgee)*radius)+",height:"+height+",w:"+width+",radius:"+radius);
        return new WandH(width, height);
    }

    /**
     * 设置值
     *
     * @param value
     */
    public void setValue(int value) {


        currentValue = value;
        currentDrgee = getValueForDrgee(value);
        Log.e("value", "drgee:" + currentDrgee + ",dr:" + value);
        invalidate();
    }

    private float getValueForDrgee(int value) {
        float drgee = 0;
        float predrgee = 180.0f / (datas_kudu.length - 1);
        //找出value的所在范围，在计算角度
        for (int i = 0; i < datas_kudu.length - 1; i++) {
            if (datas_kudu[i] <= value && value <= datas_kudu[i + 1]) {

                drgee =  (i * predrgee + predrgee * ((value - datas_kudu[i]) * 1.0f / (datas_kudu[i + 1] - datas_kudu[i])));

                break;
            }
        }
        return drgee;
    }

    public void setFengeValue(int[] fegedata) {
        this.fengeData = fegedata;
        invalidate();
    }


    class WandH {
        int width;
        int height;

        public WandH(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

}
