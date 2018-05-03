package net.fitrun.fitrungame.sportSelect.courseSelect.userinfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wx.wheelview.adapter.BaseWheelAdapter;

import net.fitrun.fitrungame.R;

/**
 * Created by 晁东洋 on 2017/4/12.
 */

public class MyWheelAdapter extends BaseWheelAdapter<String> {

    private Context mContext;

    public MyWheelAdapter(Context context) {
        mContext = context;
    }

    @Override
    protected View bindView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.wheel_item_list, null);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.item_name);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mList.get(position));
        viewHolder.textView.setTextColor(mContext.getResources().getColor(R.color.white));
        return convertView;
    }

    static class ViewHolder {
        TextView textView;
    }
}
