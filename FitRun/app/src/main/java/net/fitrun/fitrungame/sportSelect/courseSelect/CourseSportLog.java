package net.fitrun.fitrungame.sportSelect.courseSelect;

/**
 * Created by 晁东洋 on 2017/5/9.
 * 运动的详细记录
 *
 */

public class CourseSportLog {

    public int speed;
    public int grade;
    public int heartRate;
    public int date;
    public double length;

    public CourseSportLog(int speed, int grade, int heartRate, int date, double length) {
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

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }
}
