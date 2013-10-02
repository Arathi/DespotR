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
	 * ���������
	 */
	//TODO �����������
	public void sortOrderList(){
		Collections.sort(orderList);
	}

	public boolean isEnd() {
		return end;
	}
	
	/**
	 * ս�����̴���
	 */
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
			
			if (forceA.isCanNotBattle() || forceB.isCanNotBattle()){
				end=true;
				System.out.println("ս������");
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
