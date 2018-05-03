package net.fitrun.fitrungame.sportSelect.courseSelect.question;

/**
 * Created by 晁东洋 on 2016/11/24.
 * 调查问卷内容
 */

public class QuestionEvent {

    public final String message;
    public final int postion;
    public final int is ;

    public QuestionEvent(String message, int postion, int is) {
        this.message = message;
        this.postion = postion;
        this.is = is;
    }
}
