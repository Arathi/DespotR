package org.beh.und;

import java.util.*;

public abstract class Battle {
	protected IBattleFlow forceA, forceB;
	
	protected int turnCounter;
	protected boolean end;
	protected ArrayList<Order> orderList;
	
	public abstract void addToForceA(Unit u);
	public abstract void addToForceB(Unit u);
	
//	public void setEnemys(){
//		forceA.setEnemys(forceB);
//		forceB.setEnemys(forceA);
//	}
	
	public void init(){
		orderList=new ArrayList<Order>();
		end=false;
		turnCounter=1;
		
		allUnitAddToBattle(); //所有单位进入战斗
	}
	
	/**
	 * 命令表排序
	 */
	public void sortOrderList(){
		Collections.sort(orderList);
	}

	public boolean isEnd() {
		return end;
	}
	
	/**
	 * 战斗流程处理
	 */
	public void handle(){
		allUnitBuffsDecrement(); //计算Buff
		allUnitResetPosture(); //重置姿态
		allUnitSelectOrders(); //收集命令
		
		int i, resultAmount, resultIndex;
		//命令预处理
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			order.pretreatment();
		}
		//执行命令
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			//处理技能
			order.execute();
			
			//显示结果
			System.out.println( order.getActionTextDesc() );
			ArrayList<Result> results=order.getResults();
			resultAmount = results.size();
			for (resultIndex=0; resultIndex<resultAmount; resultIndex++){
				System.out.println(results.get(resultIndex));
			}
			
			if (forceA.isCanNotBattle() || forceB.isCanNotBattle()){
				end=true;
				System.out.println("战斗结束");
				break;
			}
		}
		
		System.out.println(forceA);
		System.out.println(forceB);
		turnCounter++;
	}
	
//	public abstract void allUnitSelectOrders();
//	public abstract void allUnitResetPosture();
//	public abstract void allUnitBuffsDecrement();
//	public abstract void allUnitAddToBattle();
	
	public void allUnitBuffsDecrement(){
		forceA.buffsDecrement();
		forceB.buffsDecrement();
	}
	
	public void allUnitResetPosture(){
		forceA.resetPosture();
		forceB.resetPosture();
	}
	
	public void allUnitSelectOrders(){
		orderList.clear();
		forceA.selectOrders(orderList);
		forceB.selectOrders(orderList);
		sortOrderList(); //排序
	}
	
	public void allUnitAddToBattle() {
		forceA.setBattleEnvForAll(this);
		forceB.setBattleEnvForAll(this);
		
		forceA.setEnemys(forceB);
		forceB.setEnemys(forceA);
	}
}
