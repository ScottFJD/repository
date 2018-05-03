package net.fitrun.fitrungame.sportSelect.speedy.bean;

/**
 * Created by 晁东洋 on 2017/4/1.
 */

public class UserInfo {
    public static String openid;
    public static String nickname ;
    public static int sex;
    public static String headimgurl = "";
    public static String systemUserId = "";
    public static double weight;
    public static int height;
    public static int age;
    public static String name = "";

    public static double sumLength;

    public static double getSumTime() {
        return sumTime;
    }

    public static void setSumTime(double sumTime) {
        UserInfo.sumTime = sumTime;
    }

    public static double getSumLength() {
        return sumLength;
    }

    public static void setSumLength(double sumLength) {
        UserInfo.sumLength = sumLength;
    }

    public static double getSumCalorie() {
        return sumCalorie;
    }

    public static void setSumCalorie(double sumCalorie) {
        UserInfo.sumCalorie = sumCalorie;
    }

    public static double sumTime;
    public static double sumCalorie;



    public static double getWeight() {
        return weight;
    }

    public static void setWeight(double weight) {
        UserInfo.weight = weight;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        UserInfo.height = height;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        UserInfo.age = age;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSystemUserId() {
        return systemUserId;
    }

    public void setSystemUserId(String systemUserId) {
        this.systemUserId = systemUserId;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public UserInfo(String openid, String systemUserId, String headimgurl, int sex, String nickname) {
        this.openid = openid;
        this.systemUserId = systemUserId;
        this.headimgurl = headimgurl;
        this.sex = sex;
        this.nickname = nickname;
    }
}
