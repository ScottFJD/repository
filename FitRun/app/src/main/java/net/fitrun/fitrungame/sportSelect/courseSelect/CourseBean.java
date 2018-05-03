package net.fitrun.fitrungame.sportSelect.courseSelect;

import java.util.List;

/**
 * Created by 晁东洋 on 2017/4/24.
 * 课程bean
 */

public class CourseBean {

    /**
     * code : 5129
     * message : 帐号课程状态获取成功
     * content : [{"courseCode":"LW20170417","courseDescription":"减肥课程","createdCourse":false},{"courseCode":"NL17021001","courseDescription":"耐力课程","createdCourse":false},{"courseCode":"HR17042401","courseDescription":"高血压康复","createdCourse":false},{"courseCode":"DR17042401","courseDescription":"糖尿病康复","createdCourse":false}]
     */

    private int code;
    private String message;
    /**
     * courseCode : LW20170417
     * courseDescription : 减肥课程
     * createdCourse : false
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
        private String courseCode;
        private String courseDescription;
        private boolean createdCourse;

        public String getCourseCode() {
            return courseCode;
        }

        public void setCourseCode(String courseCode) {
            this.courseCode = courseCode;
        }

        public String getCourseDescription() {
            return courseDescription;
        }

        public void setCourseDescription(String courseDescription) {
            this.courseDescription = courseDescription;
        }

        public boolean isCreatedCourse() {
            return createdCourse;
        }

        public void setCreatedCourse(boolean createdCourse) {
            this.createdCourse = createdCourse;
        }
    }
}
