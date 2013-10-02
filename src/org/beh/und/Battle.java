package org.beh.und;

import java.util.ArrayList;
import java.util.Collections;

public class Battle {
	public Force forceA, forceB;
	
	protected int turnCounter;
	protected boolean end;
	protected ArrayList<Order> orderList;
	
	public void addToForceA(Unit u){
		if (forceA==null) forceA=new Force();
		forceA.addUnit(u);
	}
	public void addToForceB(Unit u){
		if (forceB==null) forceB=new Force();
		forceB.addUnit(u);
	}
	
	public void setEnemys(){
		forceA.setEnemyForce(forceB);
		forceB.setEnemyForce(forceA);
	}
	
	public void init(){
		orderList=new ArrayList<Order>();
		end=false;
		turnCounter=1;
	}
	
	/**
	 * 命令表排序
	 */
	//TODO 生成命令队列
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
		allUnitSelectOrders(); ////收集命令
		
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
	
	public void allUnitSelectOrders() {
		// TODO Auto-generated method stub
		forceA.selectOrders();
		forceB.selectOrders();
	}
	public void allUnitResetPosture() {
		// TODO Auto-generated method stub
		forceA.resetPosture();
		forceB.resetPosture();
	}
	public void allUnitBuffsDecrement() {
		// TODO Auto-generated method stub
		forceA.buffsDecrement();
		forceB.buffsDecrement();
	}
	
	
}
