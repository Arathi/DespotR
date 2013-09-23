package org.beh.und;

import java.util.*;

public abstract class Unit {
	//姿态常量
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
	
	protected List<Integer> skills;

	public String getName() {
		return name;
	}
	public int getAgi() {
		return agi;
	}
	public int getPosture(){
		return posture;
	}
	public int getJink() {
		return jink;
	}
	/**
	 * 获取防御力（玩家单位需要重写该方法）
	 * @return 防御力
	 */
	public int getDef() {
		return def;
	}
	protected void setSkills(List<Integer> skills){
		this.skills=skills;
	}

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
			int[] resists, ArrayList<Integer> skills
			){
		initParams(name, hp, mp, atk, def, agi, jink, inte, exp, gold);
		initResists(resists);
//		initSkills();
		setSkills(skills);
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
		Result result=new Result();
		if (target==null){
			System.out.println("但攻击的目标并不存在");
			return null;
		}
		boolean jinkFlag=false;
		boolean critFlag=false;
		double jinkRand=Util.getRandomReal(0, 64);
		if (jinkRand<=target.getJink()) jinkFlag=true;
		if (jinkFlag){ //闪避的情况
			System.out.println("但是"+target.getName()+"躲开了");
		}
		else{ //正常
			double critRand=Util.getRandomReal(0, 32);
			if (critRand<=1) critFlag=true;
			//某些怪物不可被暴击(暴击免疫技能)，critFlag设置为false
			//target.haveSkill("[nocr]");
			double damageMin=0, damageMax=0, damageR=0 ;
			if (!critFlag){
				// FC版 攻击伤害=(攻方攻击力-守方守备力*50%)*(25~50%)
				//SFC版 攻击伤害=(攻方攻击力-守方守备力*50%)*(45~55%) （采用）
				damageMin=(atk-target.getDef()*0.5)*0.45;
				damageMax=(atk-target.getDef()*0.5)*0.55;
			}
			else{
				// 会心一击  攻击伤害=(攻击力*0.5+1~攻击力)
				System.out.println("会心的一击！");
				damageMin=atk*0.5+1;
				damageMax=atk;
			}
			damageR=Util.getRandomReal(damageMin, damageMax);
			int damage = (int) (damageR);
			int real_damage=target.changeHp(-damage);
			result.value=real_damage;
		}
		result.critFlag=critFlag;
		result.jinkFlag=jinkFlag;
		return result;
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
				System.out.println(name+"领便当了");
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
		if (hp<=0){ //单位已经死亡
			return true;
		}
		if (fleed){ //已逃跑的，也算不能继续战斗的单位
			return true;
		}
		return false;
	}
	
	/**
	 * 重置状态
	 */
	public void resetPosture(){
		//TODO 添加睡眠Buff的影响
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
		double successRate=0, fleeRand=1;
		//如果敌方不在攻击状态，则逃跑必定成功
		if (enemy.getPosture()==POSTURE_NONE || enemy.getPosture()==POSTURE_DEFENCE){
			fleed=true;
		}
		else{
			//逃跑成功率：总敏捷比例
			successRate=1.0*agi/(1.0*agi+1.0*enemy.getAgi());
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
	
	public int findSkillByOrder(int orderId){
		int i, skillId;
		SkillInfo skillInfo;
		for (i=0; i<skills.size(); i++){
			skillId=skills.get(i);
			skillInfo = SkillInfo.getSkillInfo(skillId);
			if (skillInfo!=null && skillInfo.getId()==skillId){
				return skillId;
			}
		}
		return 0;
	}
	
}
