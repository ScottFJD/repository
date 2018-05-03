package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2017/4/24.
 * 查询本次课程的初始运动数据
 */

public class StartSportBean {
    /**
     * code : 4868
     * message : 课程设置信息获取成功
     * content : {"cid":"0fbb397be5374802a7b50f9d75717207","maxHeartRate":117,"minHeartRate":97,"topSpeed":6,"startSpeed":2,"length":40}
     */

    private int code;
    private String message;
    /**
     * cid : 0fbb397be5374802a7b50f9d75717207
     * maxHeartRate : 117
     * minHeartRate : 97
     * topSpeed : 6
     * startSpeed : 2
     * length : 40
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
        private String cid;
        private int maxHeartRate;
        private int minHeartRate;
        private int topSpeed;
        private int startSpeed;
        private int length;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public int getMaxHeartRate() {
            return maxHeartRate;
        }

        public void setMaxHeartRate(int maxHeartRate) {
            this.maxHeartRate = maxHeartRate;
        }

        public int getMinHeartRate() {
            return minHeartRate;
        }

        public void setMinHeartRate(int minHeartRate) {
            this.minHeartRate = minHeartRate;
        }

        public int getTopSpeed() {
            return topSpeed;
        }

        public void setTopSpeed(int topSpeed) {
            this.topSpeed = topSpeed;
        }

        public int getStartSpeed() {
            return startSpeed;
        }

        public void setStartSpeed(int startSpeed) {
            this.startSpeed = startSpeed;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }
    }

}
