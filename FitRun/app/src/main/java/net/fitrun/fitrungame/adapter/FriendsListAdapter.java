package net.fitrun.fitrungame.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.fitrun.fitrungame.R;
import net.fitrun.fitrungame.app.OnItemClickListener;

import java.util.List;

import static net.fitrun.fitrungame.R.id.is_look;
import static net.fitrun.fitrungame.R.id.room_in_number;
import static net.fitrun.fitrungame.R.id.room_item;
import static net.fitrun.fitrungame.R.id.room_master_name;
import static net.fitrun.fitrungame.R.id.room_number;
import static net.fitrun.fitrungame.R.id.room_status;
import static net.fitrun.fitrungame.R.id.room_sum_number;

/**
 * Created by 晁东洋 on 2017/3/16.
 * 房间数据适配
 */

public class FriendsListAdapter extends RecyclerView.Adapter<FriendsListAdapter.MyViewHolder>{
    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mList ;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public FriendsListAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.layout_firends_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //绑定数据

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        ImageView findes_user_image;

        public MyViewHolder(View view)
        {
            super(view);
            findes_user_image = (ImageView)view.findViewById(R.id.findes_user_image);

        }
    }


}
