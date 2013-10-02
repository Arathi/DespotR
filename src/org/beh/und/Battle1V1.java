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
		sortOrderList(); //����
	}
	
	public void handle(){
		allUnitBuffsDecrement(); //����Buff
		allUnitResetPosture(); //������̬
		allUnitSelectOrders(); ////�ռ�����
		
		int i, resultAmount, resultIndex;
		//����Ԥ����
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			order.pretreatment();
		}
		//ִ������
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			//������
			order.execute();
			
			//��ʾ���
			System.out.println( order.getActionTextDesc() );
			ArrayList<Result> results=order.getResults();
			resultAmount = results.size();
			for (resultIndex=0; resultIndex<resultAmount; resultIndex++){
				System.out.println(results.get(resultIndex));
			}
			
			if (unitA.isCanNotBattle() || unitB.isCanNotBattle()){
				end=true;
				System.out.println("ս������");
				break;
			}
		}
		
		System.out.println(unitA);
		System.out.println(unitB);
		turnCounter++;
	}
	
}
