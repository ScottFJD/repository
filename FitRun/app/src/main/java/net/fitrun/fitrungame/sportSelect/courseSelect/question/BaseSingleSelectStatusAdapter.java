package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Context;

/**
 * Created by 晁东洋 on 2016/11/24.
 * 单选的基类
 */

public abstract class BaseSingleSelectStatusAdapter<T> extends BaseRecyclerAdapter<T> {
    protected int mCurrentSelect = -1;
    protected boolean isEnableSelect = true;


    public BaseSingleSelectStatusAdapter(Context context) {
        super(context);
    }

    public boolean isSelectDate() {
        return mCurrentSelect >= 0;
    }

    public void setEnableSelect(boolean isEnableSelect) {
        this.isEnableSelect = isEnableSelect;
    }

    public int getCurrentSelect() {
        return mCurrentSelect;
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    public void setCurrentSelect(int currentSelect) {
        notifyItemChanged(mCurrentSelect);
        this.mCurrentSelect = currentSelect;
        notifyItemChanged(mCurrentSelect);
    }
}
