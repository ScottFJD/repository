package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.OnItemClickListener;
import net.fitrun.fitrungame.app.Utils;
import net.fitrun.fitrungame.view.FilterImageView;

import java.util.List;

import static net.fitrun.fitrungame.R.id.course_click;
import static net.fitrun.fitrungame.R.id.course_sport_image;
import static net.fitrun.fitrungame.R.id.stamina_text;
import static net.fitrun.fitrungame.R.id.stamina_text_e;

/**
 * Created by 晁东洋 on 2017/4/13.
 * 课程选择的数据适配器
 */

public class TempoListAdapter extends RecyclerView.Adapter<TempoListAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<Integer> mList ;
    private OnItemClickListener mListener;
    Typeface typeFace_sport;
    public TempoListAdapter(Context context, List<Integer> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
        typeFace_sport =Typeface.createFromAsset(context.getAssets(),"fonts/FANGZHENG.TTF");
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_tempo_list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tempo_postion.setText(position+1+"");
        if (position ==0){
            holder.tempo_time.setText(Utils.secToTime(mList.get(position)).substring(0, 2)+"′"+Utils.secToTime(mList.get(position)).substring(3, 5)+"″");
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)holder.tempo_progressbar.getLayoutParams();
            //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20  
            linearParams.width = (int) (mList.get(position) * 0.1277);// 控件的宽强制设成30   
            holder.tempo_progressbar.setLayoutParams(linearParams);
        }else {
            Log.e("集合中的当前的数值是多少？",mList.get(position)+"");
            Log.e("集合中的前一个数值是多少？",mList.get(position-1)+"");
            int time = mList.get(position) - mList.get(position-1);
            holder.tempo_time.setText(Utils.secToTime(time).substring(0, 2)+"′"+Utils.secToTime(time).substring(3, 5)+"″");
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)holder.tempo_progressbar.getLayoutParams();
            //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20  
            linearParams.width = (int) (time * 0.1277);// 控件的宽强制设成30   
            holder.tempo_progressbar.setLayoutParams(linearParams);
        }

        //使设置好的布局参数应用到控件
    }

    @Override
    public int getItemViewType(int position) {
        return position %2;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tempo_postion;
        ProgressBar tempo_progressbar;
        TextView tempo_time;
        public MyViewHolder(View itemView) {
            super(itemView);
            tempo_postion = (TextView) itemView.findViewById(R.id.tempo_postion);
            tempo_progressbar = (ProgressBar) itemView.findViewById(R.id.tempo_progressbar);
            tempo_time = (TextView)itemView.findViewById(R.id.tempo_time);

        }
    }
}
