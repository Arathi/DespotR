package org.beh.und;

import java.util.*;

import org.beh.und.template.*;

public abstract class Unit {
	// ��̬����
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
	 * ��ȡ����������ҵ�λ��Ҫ��д�÷�����
	 * 
	 * @return ������
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
	 * ������
	 * 
	 * @param target
	 *            ����Ŀ��
	 * @return �����Ľ��
	 */
	public Result attack(Unit target) {
		Result result = new Result(target, Result.RESULT_TYPE_DAMAGE);
		if (target == null) {
			result.success = false;
			result.message = "��������Ŀ�겢������";
			return result;
		}
		boolean jinkFlag = false;
		boolean critFlag = false;
		double jinkRand = Util.getRandomReal(0, 64);
		if (jinkRand <= target.getJink())
			jinkFlag = true;
		if (jinkFlag) { // ���ܵ����
			result.success = false;
			result.message = "����" + target.getName() + "�㿪��";
		} else { // ����
			double critRand = Util.getRandomReal(0, 32);
			if (critRand <= 1)
				critFlag = true;
			// ĳЩ���ﲻ�ɱ�����(�������߼���)��critFlag����Ϊfalse
			// target.haveSkill("[nocr]");
			double damageMin = 0, damageMax = 0, damageR = 0;
			if (!critFlag) {
				// FC�� �����˺�=(����������-�ط��ر���*50%)*(25~50%)
				// SFC�� �����˺�=(����������-�ط��ر���*50%)*(45~55%) �����ã�
				damageMin = (atk - target.getDef() * 0.5) * 0.45;
				damageMax = (atk - target.getDef() * 0.5) * 0.55;
			} else {
				// ����һ�� �����˺�=(������*0.5+1~������)
				damageMin = atk * 0.5 + 1;
				damageMax = atk;
			}
			damageR = Util.getRandomReal(damageMin, damageMax);
			//���������Ϊ����Ҳ�������1���˺�
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
	 * �޸ĵ�λ������ֵ
	 * 
	 * @param delta
	 *            ����ֵ�仯��
	 * @return ������Ч�仯ֵ
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
	 * ���һ����λ�Ƿ��ܼ���ս��
	 * 
	 * @return
	 */
	public boolean isCanNotBattle() {
		// TODO ���ơ�ս�����ܡ����ж�����
		if (hp <= 0) { // ��λ�Ѿ�����
			return true;
		}
		if (fleed) { // �����ܵģ�Ҳ�㲻�ܼ���ս���ĵ�λ
			return true;
		}
		return false;
	}

	/**
	 * ����״̬
	 */
	public void resetPosture() {
		// TODO ���˯��Buff��Ӱ��
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
	 * ��������
	 * 
	 * @return �����Ƿ�ɹ�
	 */
	public Result run() {
		Result result = new Result(this, Result.RESULT_TYPE_NONE);
		double successRate = 0, fleeRand = 1;
		// ����з����ڹ���״̬�������ܱض��ɹ�
		if (enemy.getPosture() == POSTURE_NONE
				|| enemy.getPosture() == POSTURE_DEFENCE) {
			fleed = true;
		} else {
			// ���ܳɹ��ʣ������ݱ���
			successRate = 1.0 * agi / (1.0 * agi + 1.0 * enemy.getAgi());
			fleeRand = Util.getRandomReal(0, 1);
			if (fleeRand <= successRate) {
				fleed = true;
			}
		}

		if (!fleed) {
			// System.out.println("��������ʧ��");
			result.success = false;
			result.message = "��������ʧ��";
		}
		else{
			result.success = true;
			result.message = "���ܳɹ�";
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
		// if () //����˼���ñ������Ʒ��Ч�����ٷ�������
		int tmp = mp + delta;
		if (tmp > max_mp) {
			tmp = max_mp;
		} else if (tmp <= 0) {
			// ����ֵ����
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
		// TODO ���ڲ�ͬ��Buff������ͬ�Ĵ���
		buffMap.put(buffId, time);
	}

	public int getBuff(int buffId) {
		return buffMap.get(buffId);
	}

	public boolean stackBuff(int buffId, int time) {
		boolean haveBuff = buffMap.containsKey(buffId);
		int stack = 0;
		if (haveBuff) {
			// ��Buff�Ѿ����ڣ������
			stack = buffMap.get(buffId);
			stack += time;
			// TODO ����ﵽ������ֵ
			// ���Buff
			if (stack > 0) {
				buffMap.put(buffId, stack);
			} else {
				// ����������ޣ����Buff��
				buffMap.remove(buffId);
				System.out.println(name+"��"+"�쳣"+"�лָ�");
			}
		} else {
			// ��Buff�����ڣ���ֻ����
			buffMap.put(buffId, time);
		}
		return haveBuff;
	}

	/**
	 * Buff�Զ�����
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
	 * Hero����д�������
	 */
	public int getAtk() {
		return atk;
	}

}
