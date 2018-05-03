package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.zhy.autolayout.utils.AutoUtils;

import net.fitrun.fitrungame.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 晁东洋 on 2016/11/24.
 */

public abstract class MultiSettingSelectAdapter<T> extends BaseMultiSelectAdapter<T> {
    private static Context mContext;
    private static int count =0;
    private static int pos =0;
    private static int isselect = 0;
    private static boolean isActionModeShow = false;


    public void setIsActionModeShow(boolean isActionModeShow) {
        this.isActionModeShow = isActionModeShow;
        if (!isActionModeShow) {
            clearAllSelect();
        }
    }

    public MultiSettingSelectAdapter(Context context) {
        super(context);
        mContext = context;
    }

    public abstract QuestionBean.ContentBean.QuestionResultsBean getItemTitle(int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MultiSettingSelectViewHolder(mLayoutInflater.inflate(R.layout.layout_question_content_item, parent, false), this);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MultiSettingSelectViewHolder) {
            count = getCount();
            pos = getpostion();
            isselect = getselect();
            ((MultiSettingSelectViewHolder) holder).bindViewData(getItemTitle(position), position);
        }
    }

    static class MultiSettingSelectViewHolder extends RecyclerView.ViewHolder {
        TextView  question_content;
        LinearLayout fl_check;
        View question_line;
        MultiSettingSelectAdapter mAdapter;

        MultiSettingSelectViewHolder(View view, MultiSettingSelectAdapter adapter) {
            super(view);
            mAdapter = adapter;
            question_line = (View)itemView.findViewById(R.id.question_line);
            question_content = (TextView)itemView.findViewById(R.id.question_content);
            fl_check = (LinearLayout)itemView.findViewById(R.id.fl_check);
        }

        public void bindViewData(QuestionBean.ContentBean.QuestionResultsBean name, int position) {
            if (position !=0 && position+1 != count) {
                question_line.setVisibility(View.VISIBLE);
                if (mAdapter.isSelected(position)) {
                    isActionModeShow = true;
                    EventBus.getDefault().post(new QuestionEvent(name.getQrid(),position+count,1));
                    question_content.setTextColor(mContext.getResources().getColor(R.color.black));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.text_bg));
                } else {
                    if (isActionModeShow ==true){
                        EventBus.getDefault().post(new QuestionEvent("",position+count,1));
                    }
                    question_content.setTextColor(mContext.getResources().getColor(R.color.white));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));

                }
            }
            if (position ==0){
                question_line.setVisibility(View.VISIBLE);
                if (mAdapter.isSelected(position)){
                    isActionModeShow = true;
                    EventBus.getDefault().post(new QuestionEvent(name.getQrid(),position+count,1));
                    question_content.setTextColor(mContext.getResources().getColor(R.color.black));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.text_bg));
                }else {
                    if (isActionModeShow ==true){
                        EventBus.getDefault().post(new QuestionEvent("",position+count,1));
                    }
                    question_content.setTextColor(mContext.getResources().getColor(R.color.white));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));

                }

            }
            if (position+1 ==count){
                question_line.setVisibility(View.GONE);
                if (mAdapter.isSelected(position)){
                    isActionModeShow = true;

                    EventBus.getDefault().post(new QuestionEvent(name.getQrid(),position+count,1));
                    question_content.setTextColor(mContext.getResources().getColor(R.color.black));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.text_bg));
                }else {
                    if (isActionModeShow ==true){
                        EventBus.getDefault().post(new QuestionEvent("",position+count,1));
                    }
                    question_content.setTextColor(mContext.getResources().getColor(R.color.white));
                    question_content.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));

                }

            }
            question_content.setText(name.getContent());
            //question_content.setAlpha(0.7f);
            fl_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mAdapter.isSelectedEnable) {
                        if (mAdapter.isSelected(getPosition())) {//已选中
                            mAdapter.removeSelectPosition(getPosition());
                        } else {//未选中
                            mAdapter.addSelectPosition(getPosition());
                        }
                        mAdapter.notifyItemChanged(getPosition());
                    }
                }
            });
        }

    }

}
