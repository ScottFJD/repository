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

import java.util.List;


/**
 * Created by 晁东洋 on 2017/4/14.
 * 定制课程的动态
 */

public class CustomCourseStateAdapter extends RecyclerView.Adapter<CustomCourseStateAdapter.MyViewHolder>{

    private LayoutInflater mInflater;
    private Context mContext;
    private List<DataMode> mList ;
    private OnItemClickListener mListener;
    Typeface typeFace_sport;
    public CustomCourseStateAdapter(Context context, List<DataMode> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
        typeFace_sport =Typeface.createFromAsset(context.getAssets(),"fonts/FANGZHENG.TTF");
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }


    @Override
    public CustomCourseStateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_custom_sourse_state_item,parent,false);
        CustomCourseStateAdapter.MyViewHolder viewHolder = new CustomCourseStateAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomCourseStateAdapter.MyViewHolder holder, final int position) {
        holder.user_name.setText(mList.get(position).name);
        holder.user_state.setText(mList.get(position).content);
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
        TextView user_name;
        TextView user_state;
        public MyViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.user_name);
            user_state = (TextView)itemView.findViewById(R.id.user_state);

        }
    }
}
