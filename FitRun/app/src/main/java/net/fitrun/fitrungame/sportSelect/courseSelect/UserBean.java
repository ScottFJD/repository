package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2016/10/17.
 * 用户信息
 */

public class UserBean {


    /**
     * code : 5121
     * message : 帐号信息获取成功
     * content : {"uid":"6eacac15466a43548b14de8d2fe6c13e","weight":70,"height":170,"location":"","avatar":"images/avatar/-test-76c21f381af949ddbbe8e909cb0b4a02.jpg","nickName":"传奇","age":25,"gender":1,"mobile":"18501662653","email":"","courseCount":1,"sumLength":0.62,"sumTime":0,"sumCalorie":45}
     */

    private int code;
    private String message;
    /**
     * uid : 6eacac15466a43548b14de8d2fe6c13e
     * weight : 70
     * height : 170
     * location :
     * avatar : images/avatar/-test-76c21f381af949ddbbe8e909cb0b4a02.jpg
     * nickName : 传奇
     * age : 25
     * gender : 1
     * mobile : 18501662653
     * email :
     * courseCount : 1
     * sumLength : 0.62
     * sumTime : 0
     * sumCalorie : 45
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
        private double weight;
        private int height;
        private String location;
        private String avatar;
        private String nickName;
        private int age;
        private int gender;
        private String mobile;
        private String email;
        private int courseCount;
        private double sumLength;
        private double sumTime;
        private double sumCalorie;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
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

        public double getSumLength() {
            return sumLength;
        }

        public void setSumLength(double sumLength) {
            this.sumLength = sumLength;
        }

        public double getSumTime() {
            return sumTime;
        }

        public void setSumTime(double sumTime) {
            this.sumTime = sumTime;
        }

        public double getSumCalorie() {
            return sumCalorie;
        }

        public void setSumCalorie(double sumCalorie) {
            this.sumCalorie = sumCalorie;
        }
    }
}
