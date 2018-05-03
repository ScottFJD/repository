package net.fitrun.fitrungame.sportSelect.speedy.bean;

/**
 * Created by 晁东洋 on 2017/5/9.
 * 当前用户的运动记录
 */

public class UserSportLog {

    /**
     * code : 5121
     * message : 帐号信息获取成功
     * content : {"uid":"df1f6654af9a4986a6bfbc62d0b9991a","weight":70,"height":170,"location":" ","avatar":"http://wx.qlogo.cn/mmopen/XzhF92tBceyNesiaVwhxfeShM6t5hZOJYO6HItMvtxWibUKQvpiaXejedO3yEKOTD3l3fP9rbXXTPX7BsOVrtiaG7A/0","nickName":"晁东洋","age":25,"gender":1,"mobile":"","email":"","courseCount":1,"sumLength":0,"sumTime":0,"sumCalorie":0}
     */

    private int code;
    private String message;
    /**
     * uid : df1f6654af9a4986a6bfbc62d0b9991a
     * weight : 70
     * height : 170
     * location :
     * avatar : http://wx.qlogo.cn/mmopen/XzhF92tBceyNesiaVwhxfeShM6t5hZOJYO6HItMvtxWibUKQvpiaXejedO3yEKOTD3l3fP9rbXXTPX7BsOVrtiaG7A/0
     * nickName : 晁东洋
     * age : 25
     * gender : 1
     * mobile :
     * email :
     * courseCount : 1
     * sumLength : 0
     * sumTime : 0
     * sumCalorie : 0
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
        private String uid;
        private int weight;
        private int height;
        private String location;
        private String avatar;
        private String nickName;
        private int age;
        private int gender;
        private String mobile;
        private String email;
        private int courseCount;
        private int sumLength;
        private int sumTime;
        private int sumCalorie;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getCourseCount() {
            return courseCount;
        }

        public void setCourseCount(int courseCount) {
            this.courseCount = courseCount;
        }

        public int getSumLength() {
            return sumLength;
        }

        public void setSumLength(int sumLength) {
            this.sumLength = sumLength;
        }

        public int getSumTime() {
            return sumTime;
        }

        public void setSumTime(int sumTime) {
            this.sumTime = sumTime;
        }

        public int getSumCalorie() {
            return sumCalorie;
        }

        public void setSumCalorie(int sumCalorie) {
            this.sumCalorie = sumCalorie;
        }
    }
}
