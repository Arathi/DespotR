package org.beh.und;

import java.util.ArrayList;

public class Battle1V1 {
	private Unit unitA;
	private Unit unitB;
	
	private boolean end;
	
	private ArrayList<Order> orderList;
	
	
	public Battle1V1(Unit a, Unit b){
		addToForceA(a);
		addToForceB(b);
		setEnemyForAll();
		orderList=new ArrayList<Order>();
		end=false;
	}
	
	public void setEnemyForAll(){
		unitA.enemy=unitB;
		unitB.enemy=unitA;
		System.out.println("�ж�Ŀ���������");
	}
	
	public void addToForceA(Unit u){
		unitA=u;
		System.out.println(u.getName()+"����ս��");
	}
	public void addToForceB(Unit u){
		unitB=u;
		System.out.println(u.getName()+"����ս��");
	}
	
	public void sortOrderList(){
		
	}
	
	public void handle(){
		//�ռ�����
		orderList.clear();
		orderList.add(unitA.selectOrder());
		orderList.add(unitB.selectOrder());
		//unitB.selectOrder();
		//����
		sortOrderList();
		//ִ������
		int i, resultAmount, resultIndex;
		for (i=0; i<orderList.size(); i++){
			Order order = orderList.get(i);
			System.out.println(order);
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
	}

	public boolean isEnd() {
		// TODO Auto-generated method stub
		return end;
	}
	
}
