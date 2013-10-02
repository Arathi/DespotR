package org.beh.und;

import java.util.ArrayList;
import java.util.Collections;

public class Battle1V1 extends Battle {
	protected Unit unitA;
	protected Unit unitB;
	

	public Battle1V1(Unit a, Unit b){
		init();
		addToForceA(a);
		addToForceB(b);
		
		setEnemys();
	}
	
	@Override
	public void addToForceA(Unit u){
		unitA=u;
	}
	
	@Override
	public void addToForceB(Unit u){
		unitB=u;
	}
	
	@Override
	public void setEnemys(){
		unitA.enemy=unitB;
		unitB.enemy=unitA;
	}
	
	@Override
	public void allUnitBuffsDecrement(){
		unitA.buffsDecrement();
		unitB.buffsDecrement();
	}
	
	@Override
	public void allUnitResetPosture(){
		unitA.resetPosture();
		unitB.resetPosture();
	}
	
	@Override
	public void allUnitSelectOrders(){
		orderList.clear();
		orderList.add(unitA.selectOrder());
		orderList.add(unitB.selectOrder());
		sortOrderList(); //排序
	}
	
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
			
			if (unitA.isCanNotBattle() || unitB.isCanNotBattle()){
				end=true;
				System.out.println("战斗结束");
				break;
			}
		}
		
		System.out.println(unitA);
		System.out.println(unitB);
		turnCounter++;
	}
	
}
