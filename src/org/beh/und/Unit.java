package org.beh.und;

import java.util.*;

public abstract class Unit {
	//��̬����
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
	 * ��ȡ����������ҵ�λ��Ҫ��д�÷�����
	 * @return ������
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
		setSkills(skills);
		buffMap=new HashMap<Integer, Integer>();
		posture=POSTURE_ATTACK;
		fleed=false;
	}
	
	/**
	 * ������
	 * @param target ����Ŀ��
	 * @return �����Ľ��
	 */
	public Result attack(Unit target){
		Result result=new Result(target, Result.RESULT_TYPE_DAMAGE);
//		result.type=;
		if (target==null){
			result.success=false;
			result.message="��������Ŀ�겢������";
			//System.out.println();
			return result;
		}
		boolean jinkFlag=false;
		boolean critFlag=false;
		double jinkRand=Util.getRandomReal(0, 64);
		if (jinkRand<=target.getJink()) jinkFlag=true;
		if (jinkFlag){ //���ܵ����
			//System.out.println("����"+target.getName()+"�㿪��");
			result.success=false;
			result.message="����"+target.getName()+"�㿪��";
		}
		else{ //����
			double critRand=Util.getRandomReal(0, 32);
			if (critRand<=1) critFlag=true;
			//ĳЩ���ﲻ�ɱ�����(�������߼���)��critFlag����Ϊfalse
			//target.haveSkill("[nocr]");
			double damageMin=0, damageMax=0, damageR=0 ;
			if (!critFlag){
				// FC�� �����˺�=(����������-�ط��ر���*50%)*(25~50%)
				//SFC�� �����˺�=(����������-�ط��ر���*50%)*(45~55%) �����ã�
				damageMin=(atk-target.getDef()*0.5)*0.45;
				damageMax=(atk-target.getDef()*0.5)*0.55;
			}
			else{
				// ����һ��  �����˺�=(������*0.5+1~������)
				//System.out.println("���ĵ�һ����");
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
	 * �޸ĵ�λ������ֵ
	 * @param delta ����ֵ�仯��
	 * @return ������Ч�仯ֵ
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
			//System.out.println(name+"�ܵ�"+(-real_delta)+"���˺�");
			if (deadFlag){
				//System.out.println(name+"��㵱��");
			}
		}
		else if (real_delta>0) {
			//System.out.println(name+"�õ�"+real_delta+"������");
		}
		else{
			//System.out.println("����û��Ч��");
		}
		return real_delta;
	}

	public abstract Order selectOrder();

	/**
	 * ���һ����λ�Ƿ��ܼ���ս��
	 * @return
	 */
	public boolean isCanNotBattle() {
		// TODO ���ơ�ս�����ܡ����ж�����
		if (hp<=0){ //��λ�Ѿ�����
			return true;
		}
		if (fleed){ //�����ܵģ�Ҳ�㲻�ܼ���ս���ĵ�λ
			return true;
		}
		return false;
	}
	
	/**
	 * ����״̬
	 */
	public void resetPosture(){
		//TODO ���˯��Buff��Ӱ��
		posture=POSTURE_ATTACK;
	}
	
	//public boolean is

	public void defence() {
		posture=POSTURE_DEFENCE;
	}
	
	/**
	 * ��������
	 * @return �����Ƿ�ɹ�
	 */
	public boolean run(){
		double successRate=0, fleeRand=1;
		//����з����ڹ���״̬�������ܱض��ɹ�
		if (enemy.getPosture()==POSTURE_NONE || enemy.getPosture()==POSTURE_DEFENCE){
			fleed=true;
		}
		else{
			//���ܳɹ��ʣ������ݱ���
			successRate=1.0*agi/(1.0*agi+1.0*enemy.getAgi());
			fleeRand=Util.getRandomReal(0, 1);
			if (fleeRand<=successRate){
				fleed=true;
			}
		}
		
		if (!fleed){
			//System.out.println("��������ʧ��");
		}
		return fleed;
	}
	
	public int findSkillIdByOrder(int orderId){
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
	public SkillInfo findSkillInfoByOrder(int orderId){
		int i, skillId;
		SkillInfo skillInfo;
		for (i=0; i<skills.size(); i++){
			skillId=skills.get(i);
			skillInfo = SkillInfo.getSkillInfo(skillId);
			if (skillInfo!=null && skillInfo.getId()==skillId){
				return skillInfo;
			}
		}
		return null;
	}
	
	public boolean changeMp(int delta, boolean testOnly) {
		// TODO Auto-generated method stub
		//if () //����˼���ñ������Ʒ��Ч�����ٷ�������
		int tmp=mp+delta;
		if (tmp>max_mp){
			tmp=max_mp;
		}
		else if (tmp<=0){
			//����ֵ����
			return false;
		}
		if (!testOnly)
			mp=tmp;
		return true;
	}
	
	public boolean checkBuff(int buffId){
		return buffMap.containsKey(buffId);
	}
	
	public void setBuff(int buffId, int time) {
		// TODO ���ڲ�ͬ��Buff������ͬ�Ĵ���
		buffMap.put(buffId, time);
	}
	
	public int setBuff(int buffId) {
		return buffMap.get(buffId);
	}
	
}
