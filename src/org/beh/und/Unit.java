package org.beh.und;

import java.util.*;

public abstract class Unit {
	
	protected String name;
	
	protected int hp;
	protected int max_hp;
	protected int mp;
	protected int max_mp;
	
	protected int atk;
	protected int def;
	protected int agi;
	protected int jink;
	protected int inte;
	
	protected int exp;
	protected int gold;
	
	protected int[] resists;
	protected int[] resistsMax;
	
	protected Map<Integer, Integer> buffMap;
	protected Unit enemy;
	
	public void initParams(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold
			){
		this.name=name;
		this.hp=this.max_hp=hp;
		this.mp=this.max_mp=mp;
		this.atk=atk;
		this.def=def;
		this.agi=agi;
		this.jink=jink;
		this.inte=inte;
		this.exp=exp;
		this.gold=gold;
	}
	
	public void initResists(int[] resists){
		int i,size=resists.length;
		this.resists=new int[size];
		this.resistsMax=new int[size];
		resists[0]=0;
		resistsMax[0]=7;
		for (i=1; i<size; i++){
			this.resists[i]=resists[i];
			this.resistsMax[i]=7;
		}
	}
	
	public void init(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold, 
			int[] resists
			){
		initParams(name, hp, mp, atk, def, agi, jink, inte, exp, gold);
		initResists(resists);
		buffMap=new HashMap<Integer, Integer>();
	}
	
	public Result attack(Unit target){
		//攻击伤害=(攻方攻击力-守方守备力*50%)*45~55%
		if (target==null){
			System.out.println("攻击目标不存在");
			return null;
		}
		//System.out.println(name+"对"+target.getName()+"发动攻击！");
		double damageAVR=1.0*atk-def*0.5;
		double rate=Util.getRandomReal(0.45, 0.55);
		int damage= (int) (damageAVR*rate);
		target.changeHp(-damage);
		return null;
	}
	
	/**
	 * 修改单位的生命值
	 * @param delta 生命值变化量
	 * @return 返回有效变化值
	 */
	public int changeHp(int delta){
		int old_hp=hp;
		hp+=delta;
		if (hp>max_hp){
			hp=max_hp;
		}
		if (hp<0){
			hp=0;
		}
		int real_delta=hp-old_hp;
		if (real_delta<0){
			System.out.println(name+"受到"+(-delta)+"点伤害");
		}
		else if (real_delta>0) {
			System.out.println(name+"得到"+delta+"点治疗");
		}
		else{
			System.out.println("但是没有效果");
		}
		return real_delta;

	}

	public abstract Order selectOrder();

	/**
	 * 检测一个单位是否还能继续战斗
	 * @return
	 */
	public boolean isCanNotBattle() {
		// TODO 完善“战斗不能”的判断条件
		if (hp<=0){
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public int getAgi() {
		// TODO Auto-generated method stub
		return agi;
	}
	
}
