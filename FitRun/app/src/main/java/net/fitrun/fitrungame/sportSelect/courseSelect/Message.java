package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2017/4/17.
 */

public class Message {
    /**
     * code : 4609
     * message : 验证码发送成功
     * content : null
     */

    private int code;
    private String message;
    private Object content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
