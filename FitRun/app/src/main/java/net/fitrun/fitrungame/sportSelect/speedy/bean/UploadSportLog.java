package net.fitrun.fitrungame.sportSelect.speedy.bean;

/**
 * Created by 晁东洋 on 2017/3/31.
 */

public class UploadSportLog {


    /**
     * code : 8193
     * message : 减肥课程快捷运动保存成功
     * content : {"webUrl":"http://app.idxwrd.com/app/sport-report/sport-report.html?uid=1cfd361052b945e7b09b55eae1f52fee&date=1494412570264&endDate=1494412570264"}
     */

    private int code;
    private String message;
    /**
     * webUrl : http://app.idxwrd.com/app/sport-report/sport-report.html?uid=1cfd361052b945e7b09b55eae1f52fee&date=1494412570264&endDate=1494412570264
     */

    private ContentBean content;

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

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        private String webUrl;

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }
    }
}
