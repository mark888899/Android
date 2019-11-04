package idv.ron.oogame_poke.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import idv.ron.oogame_poke.R;
import idv.ron.oogame_poke.model.action.Fight;
import idv.ron.oogame_poke.model.skill.Move;

/**
 * 寶可夢精靈，參看寶可夢Go全圖鑑（http://www.otaku-hk.com/pkmgo/en/pokedex）
 */
public class Pokemon implements Fight, Serializable {
    private static List<Pokemon> myPokemons = new ArrayList<>();

    // 圖片
    private int image;
    // 名稱
    private String name;
    // 血
    private int hp;
    private int Fullhp;
    private int sp;
    // 攻擊力
    private int attack;
    // 防禦力
    private int defense;
    // 捕捉率
    private int catchChance;
    //出現率
    private int appear;
    //體型
    private int bodytype;//(1為大,2為中,3為小)
    //屬性
    private int attribute;//1為電 2為木 3為水 4為火 5岩石 6無形
    // 基本技能
    private Move fastMove;
    // 基本技能2
    private Move secondMove;
    // 特別技能
    private Move chargeMove;


    public Pokemon(int image, String name, int Fullhp,  int attack, int defense,int attribute,int bodytype, int catchChance,int appear, Move fastMove,Move secondMove,Move chargeMove) {
        this.image = image;
        this.name = name;
        this.Fullhp = Fullhp;
        //hp預設為滿
        this.hp = getFullhp();
        this.attack = attack;
        this.defense = defense;
        this.attribute=attribute;
        this.bodytype=bodytype;
        this.catchChance = catchChance;
        this.appear=appear;
        this.fastMove = fastMove;
        this.secondMove = secondMove;
        this.chargeMove = chargeMove;
    }

    @Override
    public double attack(Pokemon enemy, Move move) {//enemy是攻擊對象，move是技能名稱
        // 傷害公式：(1+(自己.攻擊 – 敵人.防禦)*0.1 )*(技能傷害*屬性相剋*體型相剋)
        // 基本傷害：(1+(自己.攻擊 – 敵人.防禦)*0.1 )
        double Damage1=1+(getAttack()-enemy.getDefense())*0.1;
        // 後方公式：(技能傷害*屬性相剋*體型相剋)
        double Damage2=move.getPower()*bodytypeAttack(enemy)*attributeAttack(enemy);

        // 合併傷害
        double resultDamage =(double)(Damage1*Damage2);
        double random=(70+Math.random()*50)*0.01;//70%攻擊固定，30%浮動攻擊
        resultDamage=resultDamage*random;
        resultDamage=(int)(Math.round(resultDamage*100.0))/100.0;
        resultDamage = resultDamage >= 0 ? resultDamage : 0;
        // 敵人依照結果傷害計算損失的HP，HP為負值則改為0
        int hp = (int)(enemy.getHp() - resultDamage);

        enemy.setHp(hp > 0 ? hp : 0);
        return resultDamage;
    }
    public double bodytypeAttack(Pokemon enemy){
        if((getBodytype()-enemy.getBodytype())==1){//我方體型大於敵方體型一號（大>中、中>小）
            return 1.25;
        }else if((enemy.getBodytype()-getBodytype())==1){//敵方體型比我方大一號
            return 0.75;
        }else if((enemy.getBodytype()==3)&&(getBodytype()==1)){//敵方大 我方小
            return 1.25;
        }else if((enemy.getBodytype()==1)&&(getBodytype()==3)){//敵方小 我方大
            return 0.75;
        }else{
            return 1.00;
        }
    }
    public double attributeAttack(Pokemon enemy){
        if(getAttribute()==6){
            return 1.25;
        }else if((getAttribute()-enemy.getAttribute()==1)){//我方剋敵方
            return 1.25;
        }else if((getAttribute()-enemy.getAttribute()==-1)){//敵方剋我方
            return 0.75;
        }else if(getAttribute()==5&&enemy.getAttribute()==1){//土剋金
            return 1.25;
        }else if(getAttribute()==1&&enemy.getAttribute()==5){
            return 0.75;
        }else{
            return 1.00;
        }
    }
//double totalDamage = getAttack() + move.getPower();

    @Override
    public String attackResult(Pokemon enemy, Move move) {
        double resultDamage = this.attack(enemy, move);
        String text = String.format(
                "[%s][%s]攻擊[%s]造成[%s]傷害, [%3$s]HP剩下[%s]",
                this.getName(), move.getName(), enemy.getName(), resultDamage, enemy.getHp());
        return text;
    }

    /**
     * 取得野生寶可夢
     * @return 回傳野生寶可夢
     */
    public static List<Pokemon> getFieldPokemons() {
        List<Pokemon> pokemons = new ArrayList<>();
        Pokemon eevee = new Pokemon(R.drawable.eevee,
                "伊布",
                100,
                7,
                4,2,2,
                30,40,
                new Move("電光石火",30,10),//技能1
                new Move("撞擊", 20,20),
                new Move("速度之星",60, -35)
        );
        Pokemon corsola = new Pokemon(R.drawable.corsola,
                "太陽珊瑚",
                150,
                5,
                8,3,3,50,55,
                new Move("泡沫",25,10),//技能1
                new Move("撞擊", 20,20),
                new Move("泡沫光線",45, -25)
        );
        Pokemon pulse = new Pokemon(R.drawable.pulse,
                "正電拍拍",
                80,
                6,
                3,1,1,50,70,
                new Move("閃光",15,15),//技能1
                new Move("電光石火", 30,10),
                new Move("十萬伏特",65, -40)
        );
        Pokemon vulpix = new Pokemon(R.drawable.vulpix,
                "六尾",
                100,
                7,
                4,2,2,20,50,
                new Move("火花",20,20),//技能1
                new Move("電光石火", 30,10),
                new Move("火焰衝擊",70, -45)
        );
        Pokemon minun = new Pokemon(R.drawable.minun,
                "負電拍拍",
                80,
                6,
                3,1,1,50,70,
                new Move("閃電",15,15),//技能1
                new Move("電光石火", 30,10),
                new Move("放電",55, -35)
        );
        Pokemon mew = new Pokemon(R.drawable.mew,
                "夢幻",
                250,
                10,
                6,6,1,5,5,
                new Move("陽光烈焰",40,20),//技能1
                new Move("心靈幻象", 50,10),
                new Move("龍之波動",100, -50)
        );
        Pokemon dratini = new Pokemon(R.drawable.dartini,
                "迷你龍",
                150,
                8,
                4,3,1,20,35,
                new Move("噬咬",25,15),//技能1
                new Move("龍之吐息", 30,10),
                new Move("龍捲風",50, -40)
        );
        Pokemon snorlax = new Pokemon(R.drawable.snorlax,
                "卡比獸",
                200,
                6,
                10,5,3,30,20,
                new Move("意念頭槌",35,10),//技能1
                new Move("舔", 25,25),
                new Move("破壞死光",70, -50)
        );
        Pokemon wailmer = new Pokemon(R.drawable.wailmer,
                "吼吼鯨",
                180,
                5,
                8,3,3,30,30,
                new Move("噴水",30,10),//技能1
                new Move("壓制", 15,20),
                new Move("水之波動",60, -45)
        );
        Pokemon slowbro = new Pokemon(R.drawable.slowbro,
                "呆河馬",
                180,
                8,
                6,3,3,25,25,
                new Move("念力",35,5),//技能1
                new Move("噴水", 30,10),
                new Move("冷凍光束",70, -35)
        );

        pokemons.add(eevee);
        pokemons.add(corsola);
        pokemons.add(pulse);
        pokemons.add(vulpix);
        pokemons.add(minun);
        pokemons.add(mew);
        pokemons.add(dratini);
        pokemons.add(snorlax);
        pokemons.add(wailmer);
        pokemons.add(slowbro);

        return pokemons;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public int getFullhp() { return Fullhp; }

    public void setFullhp(int fullhp) { Fullhp = fullhp; }

    public int getSp() { return sp; }

    public void setSp(int sp) { this.sp = sp; }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getCatchChance() {
        return catchChance;
    }

    //捕捉率、技能、體型、屬性暫時不提供更改
    //public void setCatchChance(int catchChance) {this.catchChance = catchChance;}
    public String getFastMoveName(){return fastMove.getName();};
    public String getSecondMoveName(){return secondMove.getName();};

    public Move getFastMove() {
        return fastMove;
    }

    public Move getSecondMove() {
        return secondMove;
    }

    //public void setFastMove(Move fastMove) {this.fastMove = fastMove;}

    public Move getChargeMove() {
        return chargeMove;
    }

    //public void setChargeMove(Move chargeMove) {this.chargeMove = chargeMove;}

    public int getBodytype() { return bodytype; }


    public int getAttribute() { return attribute; }




    public static List<Pokemon> getMyPokemons() {
        return myPokemons;
    }


    public static void addPokemon(Pokemon pokemon) {
        myPokemons.add(pokemon);
    }
    //體型(1為大,2為中,3為小)
    //屬性1為電 2為木 3為水 4為火 5岩石 6無形
    public String getBodytupeText(){
        if(this.getBodytype()==1){
            return "大";
        }else if (this.getBodytype()==2){
            return "中";
        }else{
            return "小";
        }
    }

    public String getAttributeText(){
        if(this.getAttribute()==1){
            return "電系";
        }else if(this.getAttribute()==2){
            return "草系";
        }else if(this.getAttribute()==3){
            return "水系";
        }else if(this.getAttribute()==4){
            return "火系";
        }else if(this.getAttribute()==5){
            return  "岩石系";
        }else{
            return "無形";
        }
    }
}
