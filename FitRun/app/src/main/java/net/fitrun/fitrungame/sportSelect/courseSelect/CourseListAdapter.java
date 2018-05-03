package net.fitrun.fitrungame.sportSelect.courseSelect;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.OnItemClickListener;
import net.fitrun.fitrungame.view.FilterImageView;

import java.util.List;

/**
 * Created by 晁东洋 on 2017/4/13.
 * 课程选择的数据适配器
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<CourseBean.ContentBean> mList ;
    private OnItemClickListener mListener;
    Typeface typeFace_sport;
    public CourseListAdapter(Context context, List<CourseBean.ContentBean> list) {
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
        View view = mInflater.inflate(R.layout.layout_course_selece_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        if (position ==0){
            Glide.with(mContext).load(R.drawable.course_endurance).into(holder.course_sport_image);
            holder.stamina_text.setText(mList.get(position).getCourseDescription());
            holder.stamina_text_e.setText("LOSE WEIGHT");
        }
        if (position ==1){
            Glide.with(mContext).load(R.drawable.course_lose_weight).into(holder.course_sport_image);
            holder.stamina_text.setText(mList.get(position).getCourseDescription());
            holder.stamina_text_e.setText("ENDURANCE");
        }
        if (position ==2){
            Glide.with(mContext).load(R.drawable.height_blode).into(holder.course_sport_image);
            holder.stamina_text.setText(mList.get(position).getCourseDescription());
            holder.stamina_text_e.setText("PRESSURE");
        }
        if (position ==3){
            Glide.with(mContext).load(R.drawable.diabetes).into(holder.course_sport_image);
            holder.stamina_text.setText(mList.get(position).getCourseDescription());
            holder.stamina_text_e.setText("DIABETES");
        }
        holder.stamina_text.setTypeface(typeFace_sport);

        holder.course_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN)
                {
                    mListener.onItemClick(v.getId(),position,1);
                }
                return false;
            }
        });
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
        FilterImageView course_sport_image;
        TextView stamina_text;
        TextView stamina_text_e;
        RelativeLayout course_click;
        public MyViewHolder(View itemView) {
            super(itemView);
            course_click = (RelativeLayout)itemView.findViewById(R.id.course_click);
            course_sport_image = (FilterImageView) itemView.findViewById(R.id.course_sport_image);
            stamina_text = (TextView) itemView.findViewById(R.id.stamina_text);
            stamina_text_e = (TextView)itemView.findViewById(R.id.stamina_text_e);

        }
    }
}
