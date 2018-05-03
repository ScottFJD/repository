package net.fitrun.fitrungame.sportSelect.courseSelect;

import java.util.List;

/**
 * Created by 晁东洋 on 2017/4/17.
 */

public class CourseTable {
    /**
     * code : 4869
     * message : 当前分类课程信息获取成功
     * content : {"courseName":"减肥课程","sumTime":27,"validTime":5,"length":61.5,"information":"您在该课程已经消耗186.0相当于0.7个汉堡的热量","dayExerciseMsgs":[{"dayNo":19,"valid":false,"currentDay":false},{"dayNo":20,"valid":true,"currentDay":false},{"dayNo":21,"valid":false,"currentDay":false},{"dayNo":22,"valid":true,"currentDay":true},{"dayNo":23,"valid":true,"currentDay":false},{"dayNo":24,"valid":false,"currentDay":false},{"dayNo":25,"valid":false,"currentDay":false}]}
     */

    private int code;
    private String message;
    /**
     * courseName : 减肥课程
     * sumTime : 27
     * validTime : 5
     * length : 61.5
     * information : 您在该课程已经消耗186.0相当于0.7个汉堡的热量
     * dayExerciseMsgs : [{"dayNo":19,"valid":false,"currentDay":false},{"dayNo":20,"valid":true,"currentDay":false},{"dayNo":21,"valid":false,"currentDay":false},{"dayNo":22,"valid":true,"currentDay":true},{"dayNo":23,"valid":true,"currentDay":false},{"dayNo":24,"valid":false,"currentDay":false},{"dayNo":25,"valid":false,"currentDay":false}]
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
        private String courseName;
        private int sumTime;
        private int validTime;
        private double length;
        private String information;
        /**
         * dayNo : 19
         * valid : false
         * currentDay : false
         */

        private List<DayExerciseMsgsBean> dayExerciseMsgs;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getSumTime() {
            return sumTime;
        }

        public void setSumTime(int sumTime) {
            this.sumTime = sumTime;
        }

        public int getValidTime() {
            return validTime;
        }

        public void setValidTime(int validTime) {
            this.validTime = validTime;
        }

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public List<DayExerciseMsgsBean> getDayExerciseMsgs() {
            return dayExerciseMsgs;
        }

        public void setDayExerciseMsgs(List<DayExerciseMsgsBean> dayExerciseMsgs) {
            this.dayExerciseMsgs = dayExerciseMsgs;
        }

        public static class DayExerciseMsgsBean {
            private int dayNo;
            private boolean valid;
            private boolean currentDay;

            public int getDayNo() {
                return dayNo;
            }

            public void setDayNo(int dayNo) {
                this.dayNo = dayNo;
            }

            public boolean isValid() {
                return valid;
            }

            public void setValid(boolean valid) {
                this.valid = valid;
            }

            public boolean isCurrentDay() {
                return currentDay;
            }

            public void setCurrentDay(boolean currentDay) {
                this.currentDay = currentDay;
            }
        }
    }
}
