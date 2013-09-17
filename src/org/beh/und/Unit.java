package org.beh.und;

import java.util.*;

public abstract class Unit {
	
	//��̬
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
	 * ������
	 * @param target ����Ŀ��
	 * @return �����Ľ��
	 */
	public Result attack(Unit target){
		//�����˺�=(����������-�ط��ر���*50%)*45~55%
		Result result=new Result();
		if (target==null){
			System.out.println("��������Ŀ�겢������");
			return null;
		}
		//TODO ���ܼ���(����ֵ/64)
		boolean jinkFlag=false;
		boolean critFlag=false;
		double jinkRand=Util.getRandomReal(0, 64);
		if (jinkRand<=target.getJink()) jinkFlag=true;
		if (jinkFlag){
			System.out.println("����"+target.getName()+"�㿪��");
		}
		else{
			//TODO ��������(1/32)
			double critRand=Util.getRandomReal(0, 32);
			if (critRand<=1) critFlag=true;
			double damageMin=0, damageMax=0, damageR=0 ;
			if (!critFlag){
				damageMin=(atk-target.getDef()*0.5)*0.25;
				damageMax=(atk-target.getDef()*0.5)*0.5;
			}
			else{
				System.out.println("���ĵ�һ����");
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
			System.out.println(name+"�ܵ�"+(-real_delta)+"���˺�");
			if (deadFlag){
				System.out.println(name+"�㵱�ˡ���");
			}
		}
		else if (real_delta>0) {
			System.out.println(name+"�õ�"+real_delta+"������");
		}
		else{
			System.out.println("����û��Ч��");
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
		if (hp<=0){
			return true;
		}
		if (fleed){ //�����ܵģ�Ҳ�㲻�ܼ���ս���ĵ�λ
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
	 * ��������
	 * @return �����Ƿ�ɹ�
	 */
	public boolean run(){
		//TODO ʹ��DQ1��������ԭ��
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
			System.out.println("��������ʧ��");
		}
		return fleed;
	}
	
}
