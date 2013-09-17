package org.beh.und;

import java.util.*;

public abstract class Unit {
	
	//姿态
	public static final int POSTURE_NONE=0;
	public static final int POSTURE_ATTACK=1;
	public static final int POSTURE_DEFENCE=2;
	
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
	
	protected int posture;
	protected boolean fleed;
	
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
		posture=POSTURE_ATTACK;
		fleed=false;
	}
	
	/**
	 * 物理攻击
	 * @param target 攻击目标
	 * @return 攻击的结果
	 */
	public Result attack(Unit target){
		//攻击伤害=(攻方攻击力-守方守备力*50%)*45~55%
		Result result=new Result();
		if (target==null){
			System.out.println("但攻击的目标并不存在");
			return null;
		}
		//TODO 闪避计算(闪避值/64)
		boolean jinkFlag=false;
		boolean critFlag=false;
		double jinkRand=Util.getRandomReal(0, 64);
		if (jinkRand<=target.getJink()) jinkFlag=true;
		if (jinkFlag){
			System.out.println("但是"+target.getName()+"躲开了");
		}
		else{
			//TODO 暴击计算(1/32)
			double critRand=Util.getRandomReal(0, 32);
			if (critRand<=1) critFlag=true;
			double damageMin=0, damageMax=0, damageR=0 ;
			if (!critFlag){
				damageMin=(atk-target.getDef()*0.5)*0.25;
				damageMax=(atk-target.getDef()*0.5)*0.5;
			}
			else{
				System.out.println("会心的一击！");
				damageMin=atk*0.5+1;
				damageMax=atk;
			}
			damageR=Util.getRandomReal(damageMin, damageMax);
			int damage = (int) damageR;
			int real_damage=target.changeHp(-damage);
			result.value=real_damage;
		}
		result.critFlag=critFlag;
		result.jinkFlag=jinkFlag;
		return result;
	}
	
	private int getJink() {
		return jink;
	}

	private int getDef() {
		// TODO Auto-generated method stub
		return def;
	}

	/**
	 * 修改单位的生命值
	 * @param delta 生命值变化量
	 * @return 返回有效变化值
	 */
	public int changeHp(int delta){
		int old_hp=hp;
		boolean deadFlag=false;
		hp+=delta;
		if (hp>max_hp){
			hp=max_hp;
		}
		if (hp<0){
			hp=0;
			deadFlag=true;
		}
		int real_delta=hp-old_hp;
		if (real_delta<0){
			System.out.println(name+"受到"+(-real_delta)+"点伤害");
			if (deadFlag){
				System.out.println(name+"便当了……");
			}
		}
		else if (real_delta>0) {
			System.out.println(name+"得到"+real_delta+"点治疗");
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
		if (fleed){ //已逃跑的，也算不能继续战斗的单位
			return true;
		}
		return false;
	}

	public String getName() {
		return name;
	}

	public int getAgi() {
		return agi;
	}
	
	public int getPosture(){
		return posture;
	}
	public void resetPosture(){
		posture=POSTURE_ATTACK;
	}
	
	//public boolean is

	public void defence() {
		posture=POSTURE_DEFENCE;
	}
	
	/**
	 * 处理逃跑
	 * @return 逃跑是否成功
	 */
	public boolean run(){
		//TODO 使用DQ1风格的逃跑原理
//		double _self=0,_enemy=0;
		double successRate=0, fleeRand=1;
		
		if (enemy.getPosture()==POSTURE_NONE || enemy.getPosture()==POSTURE_DEFENCE){
			fleed=true;
		}
		else{
			successRate=1.0*agi/(1.0*agi+1.0*enemy.getAgi());
//			_self=Util.getRandomReal(0, agi);
//			_enemy=Util.getRandomReal(0, enemy.getAgi());
//			if (_self>_enemy){
//				fleed=true;
//			}
			fleeRand=Util.getRandomReal(0, 1);
			if (fleeRand<=successRate){
				fleed=true;
			}
		}
		
		if (!fleed){
			System.out.println("但是逃跑失败");
		}
		return fleed;
	}
	
}
