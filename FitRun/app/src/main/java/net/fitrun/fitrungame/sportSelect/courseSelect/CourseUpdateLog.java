package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2017/5/9.
 * 减肥课程运动之后的数据更新
 */

public class CourseUpdateLog {

    /**
     * code : 4867
     * message : 课程信息采集成功
     * content : {"score":80,"comment":"abcd","webUrl":"abcd"}
     */

    private int code;
    private String message;
    /**
     * score : 80
     * comment : abcd
     * webUrl : abcd
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
        private int score;
        private String comment;
        private String webUrl;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getWebUrl() {
            return webUrl;
        }

        public void setWebUrl(String webUrl) {
            this.webUrl = webUrl;
        }
    }
}
