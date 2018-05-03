package net.fitrun.fitrungame.sportSelect.speedy.bean;

/**
 * Created by 晁东洋 on 2017/3/31.
 * 运动的参数
 *
 */

public class SportLog {
    public int speed;
    public int grade;
    public int heartRate;
    public int date;
    public int length;

    public SportLog(int speed, int grade, int heartRate, int date, int length) {
        this.speed = speed;
        this.grade = grade;
        this.heartRate = heartRate;
        this.date = date;
        this.length = length;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
