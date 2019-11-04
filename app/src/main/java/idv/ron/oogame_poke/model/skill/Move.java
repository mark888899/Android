package idv.ron.oogame_poke.model.skill;

import java.io.Serializable;

/**
 * 攻擊技能
 */
public class Move implements Serializable {
    private String name; // 技能名稱
    private int power; // 技能傷害
    private int sp;//增加的sp值
    private int spcost;

    public Move(String name, int power,int sp) {
        this.name = name;
        this.power = power;
        this.sp=sp;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPower() {
        return power;
    }

    public int getSp(){return sp;}

    public void setPower(int power) {
        this.power = power;
    }
}

