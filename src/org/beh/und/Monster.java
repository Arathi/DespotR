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
		//决定第一个actionIndex
		actionIndex=-1; //初始化后会变为[0,7]内的数
		setNextActionIndex();
//		if (actionMode==MonsterInfo.ACTION_MODE_FIXED){
//			actionIndex=0;
//		}
//		else {
//			actionIndex=Util.getRandomInt(0, 8);
//		}
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
	 * @param skills  技能表
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
	 * 选取命令
	 * @return 选取的命令
	 */
	public Order selectOrder() {
		Order order=null;
		int orderId = 0;

		//战斗力不足时，逃跑
		if (getAtk()*2<enemys.getAtkSum()){
			orderId = "run".hashCode();
			order = new Order( this, null, ActionInfo.getActionInfo(orderId) );
			setNextActionIndex();
			return order;
		}
		
		//获取命令对应的动作
		orderId=actions[actionIndex];
		ActionInfo action=ActionInfo.getActionInfo(orderId);
		if (action==null){
			action = findSkillInfoByOrder(orderId);
			//TODO 检测无法施法情况(除了沉默和法力值不足)，转换为attack
			if (action != null){
				SkillInfo skill = (SkillInfo)action;
				if (checkBuff(SpellSystem.BUFFID_FIXED_SLIENCE)){
					action = null; //被沉默，无法施法
				}
				else if (checkMp(-skill.getMpCost())==false){
					action = null; //法力值不足，无法施法
				}
			}
			
		}
		if (action==null){
			//System.out.println("未识别的orderId: "+orderId);
			action=ActionInfo.getActionInfo("attack".hashCode());
		}
		//选择目标单位
		List<Unit> targets=selectTarget(action);
		//创建命令
		order=new Order(this, targets, action);
		//计算AI的下一个命令
		setNextActionIndex();
		//结束
		return order;
	}
	
	@Override
	public void selectOrders(List<Order> orderList){
		Order order = selectOrder();
		orderList.add(order);
	}
	
	/**
	 * 设置下一个Action索引
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
	 * 选择目标单位
	 * @param actionId 动作ID
	 * @return 选取的目标单位
	 */
	public List<Unit> selectTarget(ActionInfo actionInfo){
		//TODO 多单位化
		List<Unit> targets=null;
		Unit target=null;
//		ActionInfo actionInfo = ActionInfo.getActionInfo(actionId);
		int targetSide = actionInfo.getTargetUnitSide();
		int targetAmount = actionInfo.getTargetRange();
		if (targetSide==ActionInfo.TARGET_TYPE_ENEMY){
			//System.out.println("选择敌人为目标");
			if (targetAmount == ActionInfo.TARGET_RANGE_SINGlE){
				target = enemys.getSingleUnit(0);
			}
			else{
				targets = enemys.getAllUnits(0);
			}
		}
		else if (targetSide==ActionInfo.TARGET_TYPE_ALLY){
			//System.out.println("选择自己为目标");
			if (targetAmount == ActionInfo.TARGET_RANGE_SINGlE){
				//target = enemys.getSingleUnit(0);
				target = this;
			}
			else{
				//TODO 选择多个友军单位
				target = this;
			}
		}
		else {
			//System.out.println("没有选择目标");
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
