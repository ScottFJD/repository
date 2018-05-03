package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import android.content.Context;

/**
 * Created by 晁东洋 on 2017/4/11.
 */

public class DefaultMultipleAdapter extends MultiSettingSelectAdapter<QuestionBean.ContentBean.QuestionResultsBean> {
    public DefaultMultipleAdapter(Context context) {
        super(context);
    }

    @Override
    public QuestionBean.ContentBean.QuestionResultsBean getItemTitle(int position) {
        return getItemData(position);
    }
}
