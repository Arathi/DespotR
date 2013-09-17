package org.beh.und;

import java.util.ArrayList;
import java.util.Collections;

public class Battle1V1 {
	protected Unit unitA;
	protected Unit unitB;
	
	protected int turnCounter;
	protected boolean end;
	
	protected ArrayList<Order> orderList;
	
	
	public Battle1V1(Unit a, Unit b){
		addToForceA(a);
		addToForceB(b);
		setEnemyForAll();
		orderList=new ArrayList<Order>();
		end=false;
		turnCounter=1;
	}
	
	public void setEnemyForAll(){
		unitA.enemy=unitB;
		unitB.enemy=unitA;
		//System.out.println("�ж�Ŀ���������");
	}
	
	public void addToForceA(Unit u){
		unitA=u;
		//System.out.println(u.getName()+"����ս��");
	}
	public void addToForceB(Unit u){
		unitB=u;
		//System.out.println(u.getName()+"����ս��");
	}
	
	public void sortOrderList(){
		//orderList;
		Collections.sort(orderList);
	}
	
	public void handle(){
		//������̬
		unitA.resetPosture();
		unitB.resetPosture();
		//
		
		//�ռ�����
		orderList.clear();
		orderList.add(unitA.selectOrder());
		orderList.add(unitB.selectOrder());
		//unitB.selectOrder();
		//����
		sortOrderList();
		
		int i, resultAmount, resultIndex;
		//����Ԥ����
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			//if (order.action.getCode())
			order.pretreatment();
		}
		//ִ������
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			//System.out.println(order);
			order.execute();
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

	public boolean isEnd() {
		// TODO Auto-generated method stub
		return end;
	}
	
}
