package org.beh.und;

public class Monster extends Unit {
	int actionIndex;
	int actionListSize;
	int[] actions;
	int actionMode;
	
	//public Monster(){}
	
	public Monster(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold, 
			int[] resists, int[] actions, int actmode){
		init(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists, actions, actmode);
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
		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
			actionIndex=0;
		}
		else {
			actionIndex=Util.getRandomInt(0, 8);
		}
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
	 */
	public void init(
			String name, int hp, int mp, 
			int atk, int def, int agi, 
			int jink, int inte, int exp, int gold, 
			int[] resists, int[] actions, int actmode){
		super.init(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists);
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
	 */
	@Override
	public Order selectOrder() {
		Order order=null;
		//��ȡ�����Ӧ�Ķ��� 
		int actionId=actions[actionIndex];
		ActionInfo action=ActionInfo.getActionInfo(actionId);
		//ѡ��Ŀ�굥λ
		Unit target=selectTarget(action);
		//��������
		order=new Order(this, target, action);
		//System.out.println(order);
		//����AI����һ������
		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
			actionIndex++;
			if (actionIndex>=MonsterInfo.ACTION_AMOUNT){
				actionIndex=0;
			}
		}
		else{
			actionIndex=Util.getRandomInt(0, 7);
		}
		//����
		return order;
	}
	
	/**
	 * ѡ��Ŀ�굥λ
	 * @param actionId ����ID
	 * @return ѡȡ��Ŀ�굥λ
	 */
	public Unit selectTarget(ActionInfo actionInfo){
		//TODO �൥λ��
		Unit target=null;
//		ActionInfo actionInfo = ActionInfo.getActionInfo(actionId);
		int targetSide = actionInfo.getTargetUnitSide();
		if (targetSide==ActionInfo.TARGET_TYPE_ENEMY){
			//System.out.println("ѡ�����ΪĿ��");
			target = enemy;
		}
		else if (targetSide==ActionInfo.TARGET_TYPE_ALLY){
			//System.out.println("ѡ���Լ�ΪĿ��");
			target = this;
		}
		else {
			//System.out.println("û��ѡ��Ŀ��");
			target = null;
		}
		return target;
	}
	
}
