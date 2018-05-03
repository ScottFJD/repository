package net.fitrun.fitrungame.sportSelect.speedy.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by 晁东洋 on 2017/4/2.
 */
@Entity
public class UserSportInfo {
    @Id
    private Long id;
    private double speed;
    private int heart;
    private int pace;
    public int getPace() {
        return this.pace;
    }
    public void setPace(int pace) {
        this.pace = pace;
    }
    public int getHeart() {
        return this.heart;
    }
    public void setHeart(int heart) {
        this.heart = heart;
    }
    public double getSpeed() {
        return this.speed;
    }
    public void setSpeed(double speed) {
        this.speed = speed;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1391814305)
    public UserSportInfo(Long id, double speed, int heart, int pace) {
        this.id = id;
        this.speed = speed;
        this.heart = heart;
        this.pace = pace;
    }
    @Generated(hash = 1715743470)
    public UserSportInfo() {
    }


}
