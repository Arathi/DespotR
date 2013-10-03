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
		
		allUnitAddToBattle(); //���е�λ����ս��
	}
	
	/**
	 * ���������
	 */
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
		allUnitSelectOrders(); //�ռ�����
		
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
		sortOrderList(); //����
	}
	
	public void allUnitAddToBattle() {
		forceA.setBattleEnvForAll(this);
		forceB.setBattleEnvForAll(this);
		
		forceA.setEnemys(forceB);
		forceB.setEnemys(forceA);
	}
}
