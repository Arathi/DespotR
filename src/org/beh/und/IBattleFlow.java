package org.beh.und;

import java.util.*;

/**
 * 参加战斗流程的接口，可以是一个单位(Unit)，
 * 也可以是一个势力(Force)
 * @author Arathi
 *
 */
public interface IBattleFlow {
	/**
	 * 选取命令
	 */
	public void selectOrders(List<Order> orderList);
	
	/**
	 * 重置姿态
	 */
	public void resetPosture();
	
	/**
	 * Buff计时器
	 */
	public void buffsDecrement();
	
	/**
	 * 检测战斗不能
	 * @return true: 战斗不能  false: 可以继续战斗
	 */
	public boolean isCanNotBattle();
	
	/**
	 * 添加单位
	 * @param u
	 */
	//public void addUnit(Unit u);
	
	/**
	 * 设置单位或势力的敌对
	 * @param units
	 */
	//public void setEnemys(IBattleFlow units);

	public void setBattleEnvForAll(Battle battle);
	
	public boolean isCanNotStopRunning();

	public int getAgiSum();

	public int getAtkSum();
	
	public void setEnemys(IBattleFlow enemys);
	
	public Unit getSingleUnit(int rule);
	public List<Unit> getAllUnits(int rule);
}
