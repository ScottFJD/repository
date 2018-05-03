package net.fitrun.fitrungame.sportSelect.courseSelect.question;

import java.util.List;

/**
 * Created by 晁东洋 on 2017/4/11.
 * 问卷
 */

public class QuestionBean {

    /**
     * code : 5377
     * message : 问卷列表获取成功
     * content : [{"selectType":0,"content":"您有多久没有运动过了？","questionResults":[{"qrid":"39a8f584a663499382157b28793e8b9a","content":"一直没有运动。"},{"qrid":"53802a3605f042658409931c0ed942a7","content":"以前曾经运动，已经超过三个月没有运动了！"},{"qrid":"5744460ab8df4c79acf6e7f71ff2b682","content":"每周少于1-2次运动，不规律的运动习惯"},{"qrid":"b6afd3bd18f84763a99cd4534f23aeb1","content":"每周规律运动，至少3次，包括心肺训练"}]},{"selectType":1,"content":"您喜欢的运动项目有哪些？","questionResults":[{"qrid":"bacadffbaa1f4cdaae0c654cb6516ea0","content":"跑步"},{"qrid":"4d0948e6d24c463aa97b9108bec39e91","content":"单车"},{"qrid":"d0f7c4b96d6c45f091d8ea7768f328c4","content":"跳操"},{"qrid":"5830fa1fa502478baf8d2dfb9858b10c","content":"游泳"},{"qrid":"6ab57e98fd03400f84bca54df2a560fb","content":"健身房锻炼"},{"qrid":"b170400afcd04717bf016e0ed255b36d","content":"其他"}]},{"selectType":1,"content":"以下哪些情况是您有的？","questionResults":[{"qrid":"7de4664a5a3d454c9d45ff31bfb1c574","content":"我的直系亲属患有心血管疾病，且发病时间在40岁之前。（心血管疾病包含以下几种：高血压，高血脂，冠心病，心绞痛，心梗等）"},{"qrid":"51fe05324db740699276daeca8d8dab1","content":"我曾经有过骨头受伤，是下肢部分。（如：下肢骨折或手术等。）"},{"qrid":"1f5ddfc5d92d4258b68ff1c70cca4a3e","content":"我曾经有过与心脏血管相关的疾病。（如冠心病，心绞痛，心梗等）"},{"qrid":"63c41aef36f444eda7b96eff27d8d7d4","content":"我很健康！"}]}]
     */

    private int code;
    private String message;
    /**
     * selectType : 0
     * content : 您有多久没有运动过了？
     * questionResults : [{"qrid":"39a8f584a663499382157b28793e8b9a","content":"一直没有运动。"},{"qrid":"53802a3605f042658409931c0ed942a7","content":"以前曾经运动，已经超过三个月没有运动了！"},{"qrid":"5744460ab8df4c79acf6e7f71ff2b682","content":"每周少于1-2次运动，不规律的运动习惯"},{"qrid":"b6afd3bd18f84763a99cd4534f23aeb1","content":"每周规律运动，至少3次，包括心肺训练"}]
     */

    private List<ContentBean> content;

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

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        private int selectType;
        private String content;
        /**
         * qrid : 39a8f584a663499382157b28793e8b9a
         * content : 一直没有运动。
         */

        private List<QuestionResultsBean> questionResults;

        public int getSelectType() {
            return selectType;
        }

        public void setSelectType(int selectType) {
            this.selectType = selectType;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<QuestionResultsBean> getQuestionResults() {
            return questionResults;
        }

        public void setQuestionResults(List<QuestionResultsBean> questionResults) {
            this.questionResults = questionResults;
        }

        public static class QuestionResultsBean {
            private String qrid;
            private String content;

            public String getQrid() {
                return qrid;
            }

            public void setQrid(String qrid) {
                this.qrid = qrid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }
    }
}
