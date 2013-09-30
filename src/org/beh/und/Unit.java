package org.beh.und;

import java.util.*;

import org.beh.und.template.*;

public abstract class Unit {
	// 姿态常量
	public static final int POSTURE_NONE = 0;
	public static final int POSTURE_ATTACK = 1;
	public static final int POSTURE_DEFENCE = 2;

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

	public int getPosture() {
		return posture;
	}

	public int getJink() {
		return jink;
	}

	/**
	 * 获取防御力（玩家单位需要重写该方法）
	 * 
	 * @return 防御力
	 */
	public int getDef() {
		return def;
	}

	protected void setSkills(List<Integer> skills) {
		this.skills = skills;
	}

	public void initParams(String name, int hp, int mp, int atk, int def,
			int agi, int jink, int inte, int exp, int gold) {
		this.name = name;
		this.hp = this.max_hp = hp;
		this.mp = this.max_mp = mp;
		this.atk = atk;
		this.def = def;
		this.agi = agi;
		this.jink = jink;
		this.inte = inte;
		this.exp = exp;
		this.gold = gold;
	}

	public void initResists(int[] resists) {
		int i, size = resists.length;
		this.resists = new int[size];
		this.resistsMax = new int[size];
		resists[0] = 0;
		resistsMax[0] = 7;
		for (i = 1; i < size; i++) {
			this.resists[i] = resists[i];
			this.resistsMax[i] = 7;
		}
	}

	public void init(String name, int hp, int mp, int atk, int def, int agi,
			int jink, int inte, int exp, int gold, int[] resists,
			ArrayList<Integer> skills) {
		initParams(name, hp, mp, atk, def, agi, jink, inte, exp, gold);
		initResists(resists);
		setSkills(skills);
		buffMap = new HashMap<Integer, Integer>();
		posture = POSTURE_ATTACK;
		fleed = false;
	}

	/**
	 * 物理攻击
	 * 
	 * @param target
	 *            攻击目标
	 * @return 攻击的结果
	 */
	public Result attack(Unit target) {
		Result result = new Result(target, Result.RESULT_TYPE_DAMAGE);
		if (target == null) {
			result.success = false;
			result.message = "但攻击的目标并不存在";
			return result;
		}
		boolean jinkFlag = false;
		boolean critFlag = false;
		double jinkRand = Util.getRandomReal(0, 64);
		if (jinkRand <= target.getJink())
			jinkFlag = true;
		if (jinkFlag) { // 闪避的情况
			result.success = false;
			result.message = "但是" + target.getName() + "躲开了";
		} else { // 正常
			double critRand = Util.getRandomReal(0, 32);
			if (critRand <= 1)
				critFlag = true;
			// 某些怪物不可被暴击(暴击免疫技能)，critFlag设置为false
			// target.haveSkill("[nocr]");
			double damageMin = 0, damageMax = 0, damageR = 0;
			if (!critFlag) {
				// FC版 攻击伤害=(攻方攻击力-守方守备力*50%)*(25~50%)
				// SFC版 攻击伤害=(攻方攻击力-守方守备力*50%)*(45~55%) （采用）
				damageMin = (atk - target.getDef() * 0.5) * 0.45;
				damageMax = (atk - target.getDef() * 0.5) * 0.55;
			} else {
				// 会心一击 攻击伤害=(攻击力*0.5+1~攻击力)
				damageMin = atk * 0.5 + 1;
				damageMax = atk;
			}
			damageR = Util.getRandomReal(damageMin, damageMax);
			//如果计算结果为负，也可能造成1点伤害
			if (damageR<0) damageR=Util.getRandomReal(0, 2);
			int damage = (int) (damageR);
			int real_damage = target.changeHp(-damage);
			result.value = -real_damage;
			result.deathFlag = isCanNotBattle();
		}
		result.critFlag = critFlag;
		result.jinkFlag = jinkFlag;
		return result;
	}

	/**
	 * 修改单位的生命值
	 * 
	 * @param delta
	 *            生命值变化量
	 * @return 返回有效变化值
	 */
	public int changeHp(int delta) {
		int old_hp = hp;
		//boolean deadFlag = false;
		hp += delta;
		if (hp > max_hp) {
			hp = max_hp;
		}
		if (hp < 0) {
			hp = 0;
			//deadFlag = true;
		}
		int real_delta = hp - old_hp;
//		if (real_delta < 0) {
//			if (deadFlag) {
//			}
//		} else if (real_delta > 0) {
//		} else {
//		}
		return real_delta;
	}

	public abstract Order selectOrder();

	/**
	 * 检测一个单位是否还能继续战斗
	 * 
	 * @return
	 */
	public boolean isCanNotBattle() {
		// TODO 完善“战斗不能”的判断条件
		if (hp <= 0) { // 单位已经死亡
			return true;
		}
		if (fleed) { // 已逃跑的，也算不能继续战斗的单位
			return true;
		}
		return false;
	}

	/**
	 * 重置状态
	 */
	public void resetPosture() {
		// TODO 添加睡眠Buff的影响
		if (checkBuff(SpellSystem.BUFFID_FIXED_SLEEP)){
			posture = POSTURE_NONE;
		}
		posture = POSTURE_ATTACK;
	}

	// public boolean is

	public void defence() {
		posture = POSTURE_DEFENCE;
	}

	/**
	 * 处理逃跑
	 * 
	 * @return 逃跑是否成功
	 */
	public Result run() {
		Result result = new Result(this, Result.RESULT_TYPE_NONE);
		double successRate = 0, fleeRand = 1;
		// 如果敌方不在攻击状态，则逃跑必定成功
		if (enemy.getPosture() == POSTURE_NONE
				|| enemy.getPosture() == POSTURE_DEFENCE) {
			fleed = true;
		} else {
			// 逃跑成功率：总敏捷比例
			successRate = 1.0 * agi / (1.0 * agi + 1.0 * enemy.getAgi());
			fleeRand = Util.getRandomReal(0, 1);
			if (fleeRand <= successRate) {
				fleed = true;
			}
		}

		if (!fleed) {
			// System.out.println("但是逃跑失败");
			result.success = false;
			result.message = "但是逃跑失败";
		}
		else{
			result.success = true;
			result.message = "逃跑成功";
		}
		return result;
	}

	public int findSkillIdByOrder(int orderId) {
		int i, skillId;
		SkillInfo skillInfo;
		for (i = 0; i < skills.size(); i++) {
			skillId = skills.get(i);
			skillInfo = SkillInfo.getSkillInfo(skillId);
			if (skillInfo != null && skillInfo.getId() == skillId) {
				return skillId;
			}
		}
		return 0;
	}

	public SkillInfo findSkillInfoByOrder(int orderId) {
		int i, skillId;
		SkillInfo skillInfo;
		for (i = 0; i < skills.size(); i++) {
			skillId = skills.get(i);
			skillInfo = SkillInfo.getSkillInfo(skillId);
			if (skillInfo != null && skillInfo.getOrderCode() == orderId) {
				return skillInfo;
			}
		}
		return null;
	}

	@Deprecated
	private boolean changeMp(int delta, boolean testOnly) {
		// if () //不可思议的帽子类物品特效，减少法力消耗
		int tmp = mp + delta;
		if (tmp > max_mp) {
			tmp = max_mp;
		} else if (tmp <= 0) {
			// 法力值不足
			return false;
		}
		if (!testOnly)
			mp = tmp;
		return true;
	}
	
	public boolean changeMp(int delta) {
		return changeMp(delta, false);
	}
	public boolean checkMp(int delta){
		return changeMp(delta, true);
	}
	public boolean costMp(int cost){
		return changeMp(-cost, false);
	}

	public boolean checkBuff(int buffId) {
		return buffMap.containsKey(buffId);
	}

	public void setBuff(int buffId, int time) {
		// TODO 对于不同的Buff，做不同的处理
		buffMap.put(buffId, time);
	}

	public int getBuff(int buffId) {
		return buffMap.get(buffId);
	}

	public boolean stackBuff(int buffId, int time) {
		boolean haveBuff = buffMap.containsKey(buffId);
		int stack = 0;
		if (haveBuff) {
			// 该Buff已经存在，则叠加
			stack = buffMap.get(buffId);
			stack += time;
			// TODO 处理达到最大叠加值
			// 清除Buff
			if (stack > 0) {
				buffMap.put(buffId, stack);
			} else {
				// 处理叠加下限（清除Buff）
				buffMap.remove(buffId);
				System.out.println(name+"从"+"异常"+"中恢复");
			}
		} else {
			// 该Buff不存在，则只设置
			buffMap.put(buffId, time);
		}
		return haveBuff;
	}

	/**
	 * Buff自动减弱
	 */
	public void buffsDecrement() {
		for (Map.Entry<Integer, Integer> entry : buffMap.entrySet()) {
			int buffId = entry.getKey();
			// int stack = entry.getValue();
			// System.out.println("key=" + key + " value=" + value);
			switch (buffId) {
			case SpellSystem.BUFFID_FIXED_SLEEP:
			case SpellSystem.BUFFID_FIXED_SLIENCE:
				stackBuff(buffId, -1);
				break;
			}
		}
	}

	/**
	 * Hero请重写这个方法
	 */
	public int getAtk() {
		return atk;
	}

}
