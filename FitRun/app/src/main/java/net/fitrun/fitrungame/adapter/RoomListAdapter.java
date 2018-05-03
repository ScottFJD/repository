package net.fitrun.fitrungame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import static net.fitrun.fitrungame.R.color.purple_s;
import static net.fitrun.fitrungame.R.id.room_number;

/**
 * Created by 晁东洋 on 2017/3/16.
 * 房间数据适配
 */

public class RoomListAdapter extends RecyclerView.Adapter<RoomListAdapter.MyViewHolder>{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList ;
    private OnItemClickListener mListener;
    private int selectPosition = -1;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public RoomListAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_room_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.room_number.setText(mList.get(position)+"");
        holder.room_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    selectPosition = position;
                    notifyDataSetChanged();
                    mListener.onItemClick(v.getId(), position, mList.get(position));
                }
            }
        });

        if (position == selectPosition) {
            holder.room_item.setBackgroundColor(mContext.getResources().getColor(R.color.purple_s));
        } else {
            holder.room_item.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView room_number;
        TextView room_master_name;
        TextView room_in_number;
        TextView room_sum_number;
        TextView room_status;
        ImageView is_look;
        LinearLayout room_item;
        public MyViewHolder(View view)
        {
            super(view);
            room_number = (TextView) view.findViewById(R.id.room_number);
            room_master_name = (TextView) view.findViewById(R.id.room_master_name);
            room_in_number = (TextView)view.findViewById(R.id.room_in_number);
            room_sum_number = (TextView)view.findViewById(R.id.room_sum_number);
            room_status = (TextView)view.findViewById(R.id.room_status);
            is_look = (ImageView)view.findViewById(R.id.is_look);
            room_item = (LinearLayout)view.findViewById(R.id.room_item);

        }
    }


}
