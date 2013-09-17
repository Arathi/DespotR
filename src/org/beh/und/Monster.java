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
		System.out.println("创建Monster："+this.toString());
	}
	
	/**
	 * 初始化怪物的AI
	 * @param actionMode 行动模式
	 * @param actions 动作表
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
	 * 初始化怪物信息
	 * @param name    名称
	 * @param hp      生命值
	 * @param mp      法力值
	 * @param atk     攻击力
	 * @param def     防御力
	 * @param agi     敏捷
	 * @param jink    闪避
	 * @param inte    智慧
	 * @param exp     经验
	 * @param gold    金钱
	 * @param resists 魔法抗性
	 * @param actions 动作表
	 * @param actmode 动作模式
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
	 * 选取命令
	 */
	@Override
	public Order selectOrder() {
		Order order=null;
		//获取命令对应的动作 
		int actionId=actions[actionIndex];
		ActionInfo action=ActionInfo.getActionInfo(actionId);
		//选择目标单位
		Unit target=selectTarget(action);
		//创建命令
		order=new Order(this, target, action);
		//System.out.println(order);
		//计算AI的下一个命令
		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
			actionIndex++;
			if (actionIndex>=MonsterInfo.ACTION_AMOUNT){
				actionIndex=0;
			}
		}
		else{
			actionIndex=Util.getRandomInt(0, 7);
		}
		//结束
		return order;
	}
	
	/**
	 * 选择目标单位
	 * @param actionId 动作ID
	 * @return 选取的目标单位
	 */
	public Unit selectTarget(ActionInfo actionInfo){
		//TODO 多单位化
		Unit target=null;
//		ActionInfo actionInfo = ActionInfo.getActionInfo(actionId);
		int targetSide = actionInfo.getTargetUnitSide();
		if (targetSide==ActionInfo.TARGET_TYPE_ENEMY){
			//System.out.println("选择敌人为目标");
			target = enemy;
		}
		else if (targetSide==ActionInfo.TARGET_TYPE_ALLY){
			//System.out.println("选择自己为目标");
			target = this;
		}
		else {
			//System.out.println("没有选择目标");
			target = null;
		}
		return target;
	}
	
}
