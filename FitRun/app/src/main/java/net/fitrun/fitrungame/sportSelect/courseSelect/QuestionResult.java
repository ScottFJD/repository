package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2016/11/25.
 * 问卷结果信息
 */

public class QuestionResult {

    /**
     * code : 5378
     * message : 问卷结果获取成功
     * content : {"score":38,"des":"请在医生指导下进行运动","bmi":22.49,"bimType":"","advice":",您已度过怀孕高危期，配合适量的中低强度运动，将对生产时有很大帮助。,规律运动需要维持每周3次以上；每周1次的运动习惯较易产生运动伤害；建议运动形式： 快走2周后再进入慢跑形式,,"}
     */

    private int code;
    private String message;
    /**
     * score : 38
     * des : 请在医生指导下进行运动
     * bmi : 22.49
     * bimType :
     * advice : ,您已度过怀孕高危期，配合适量的中低强度运动，将对生产时有很大帮助。,规律运动需要维持每周3次以上；每周1次的运动习惯较易产生运动伤害；建议运动形式： 快走2周后再进入慢跑形式,,
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
        private String des;
        private double bmi;
        private String bimType;
        private String advice;

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public String getBimType() {
            return bimType;
        }

        public void setBimType(String bimType) {
            this.bimType = bimType;
        }

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }
    }
}
