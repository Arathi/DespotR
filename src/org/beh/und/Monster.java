package org.beh.und;

import java.util.*;

import org.beh.und.template.*;

public class Monster extends Unit {
	int actionIndex;
	int actionListSize;
	int[] actions;
	int actionMode;
	
	public Monster(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold, 
			int[] resists, int[] actions, int actmode,
			ArrayList<Integer> skills){
		init(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists, actions, actmode, skills);
		System.out.println("����Monster��"+this.toString());
	}
	
	/**
	 * ��ʼ�������AI
	 * @param actionMode �ж�ģʽ
	 * @param actions ������
	 */
	public void initAI(int actionMode, int[] actions){
		actionListSize=actions.length;
		this.actionMode=actionMode;
		this.actions=new int[actionListSize];
		int i;
		for (i=0; i<actionListSize; i++){
			this.actions[i]=actions[i];
		}
		//������һ��actionIndex
		actionIndex=-1; //��ʼ������Ϊ[0,7]�ڵ���
		setNextActionIndex();
//		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
//			actionIndex=0;
//		}
//		else {
//			actionIndex=Util.getRandomInt(0, 8);
//		}
	}
	
	/**
	 * ��ʼ��������Ϣ
	 * @param name    ����
	 * @param hp      ����ֵ
	 * @param mp      ����ֵ
	 * @param atk     ������
	 * @param def     ������
	 * @param agi     ����
	 * @param jink    ����
	 * @param inte    �ǻ�
	 * @param exp     ����
	 * @param gold    ��Ǯ
	 * @param resists ħ������
	 * @param actions ������
	 * @param actmode ����ģʽ
	 * @param skills  ���ܱ�
	 */
	public void init(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold, 
			int[] resists, int[] actions, int actmode,
			ArrayList<Integer> skills){
		super.init(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists, skills);
		initAI(actmode, actions);
	}
	
	@Override
	public String toString(){
		String info="";
		info=name+"("+hp+"/"+max_hp+", "+mp+"/"+max_mp+", A"+atk+", D"+def+")";
		return info;
	}
	
	/**
	 * ѡȡ����
	 * @return ѡȡ������
	 */
	public Order selectOrder() {
		Order order=null;
		int orderId = 0;

		//ս��������ʱ������
		if (getAtk()*2<enemys.getAtkSum()){
			orderId = "run".hashCode();
			order = new Order( this, null, ActionInfo.getActionInfo(orderId) );
			setNextActionIndex();
			return order;
		}
		
		//��ȡ�����Ӧ�Ķ���
		orderId=actions[actionIndex];
		ActionInfo action=ActionInfo.getActionInfo(orderId);
		if (action==null){
			action = findSkillInfoByOrder(orderId);
			//TODO ����޷�ʩ�����(���˳�Ĭ�ͷ���ֵ����)��ת��Ϊattack
			if (action != null){
				SkillInfo skill = (SkillInfo)action;
				if (checkBuff(SpellSystem.BUFFID_FIXED_SLIENCE)){
					action = null; //����Ĭ���޷�ʩ��
				}
				else if (checkMp(-skill.getMpCost())==false){
					action = null; //����ֵ���㣬�޷�ʩ��
				}
			}
			
		}
		if (action==null){
			//System.out.println("δʶ���orderId: "+orderId);
			action=ActionInfo.getActionInfo("attack".hashCode());
		}
		//ѡ��Ŀ�굥λ
		List<Unit> targets=selectTarget(action);
		//��������
		order=new Order(this, targets, action);
		//����AI����һ������
		setNextActionIndex();
		//����
		return order;
	}
	
	@Override
	public void selectOrders(List<Order> orderList){
		Order order = selectOrder();
		orderList.add(order);
	}
	
	/**
	 * ������һ��Action����
	 */
	private void setNextActionIndex(){
		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
			actionIndex++;
			if (actionIndex>=MonsterInfo.ACTION_AMOUNT){
				actionIndex=0;
			}
		}
		else{
			actionIndex=Util.getRandomInt(0, MonsterInfo.ACTION_AMOUNT);
		}
	}
	
	/**
	 * ѡ��Ŀ�굥λ
	 * @param actionId ����ID
	 * @return ѡȡ��Ŀ�굥λ
	 */
	public List<Unit> selectTarget(ActionInfo actionInfo){
		//TODO �൥λ��
		List<Unit> targets=null;
		Unit target=null;
//		ActionInfo actionInfo = ActionInfo.getActionInfo(actionId);
		int targetSide = actionInfo.getTargetUnitSide();
		int targetAmount = actionInfo.getTargetRange();
		if (targetSide==ActionInfo.TARGET_TYPE_ENEMY){
			//System.out.println("ѡ�����ΪĿ��");
			if (targetAmount == ActionInfo.TARGET_RANGE_SINGlE){
				target = enemys.getSingleUnit(0);
			}
			else{
				targets = enemys.getAllUnits(0);
			}
		}
		else if (targetSide==ActionInfo.TARGET_TYPE_ALLY){
			//System.out.println("ѡ���Լ�ΪĿ��");
			if (targetAmount == ActionInfo.TARGET_RANGE_SINGlE){
				//target = enemys.getSingleUnit(0);
				target = this;
			}
			else{
				//TODO ѡ�����Ѿ���λ
				target = this;
			}
		}
		else {
			//System.out.println("û��ѡ��Ŀ��");
			target = null;
		}
		if (targets==null && target==null){
			return null;
		}
		if (targets==null && target!=null){
			targets = new ArrayList<Unit>();
			targets.add(target);
		}
		return targets;
	}
	
}
