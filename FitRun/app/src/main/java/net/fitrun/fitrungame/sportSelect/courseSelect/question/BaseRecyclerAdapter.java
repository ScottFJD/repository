package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 晁东洋 on 2016/11/24.
 * 数据适配器的基类
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected final String TAG = getClass().getSimpleName();
    protected final Context mContext;
    protected final LayoutInflater mLayoutInflater;
    protected int mPostion =0;
    protected int isselect = 0;

    protected List<T> mDataList = new ArrayList<T>();


    public BaseRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public List<T> getDataList() {
        return mDataList;
    }

    public T getItemData(int position) {
        return position < mDataList.size() ? mDataList.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }


    public  int getCount(){
        return mDataList == null ? 0 : mDataList.size();
    }

    public int getpostion(){
        return mPostion;
    }

    public int getselect(){
        return isselect;
    }

    /**
     * 批量添加记录
     *
     * @param data     需要加入的数据结构
     * @param position 插入位置
     */
    public void addItems(List<T> data, int position) {
        if (position <= mDataList.size() && data != null && data.size() > 0) {
            mDataList.addAll(position, data);
            notifyItemRangeChanged(position, data.size());
        }
    }

    /**
     * 批量添加记录
     *
     * @param data 需要加入的数据结构
     */
    public void addItems(List<T> data,int s,int postion) {
        mPostion = postion;
        isselect = s;
        addItems(data, mDataList.size());
    }
}
