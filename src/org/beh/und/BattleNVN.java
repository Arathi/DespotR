package org.beh.und;

public class BattleNVN extends Battle {
//	protected Force forceA;
//	protected Force forceB;
	
	@Override
	public void addToForceA(Unit u){
		if (forceA==null) forceA=new Force();
		((Force) forceA).addUnit(u);
	}
	
	@Override
	public void addToForceB(Unit u){
		if (forceB==null) forceB=new Force();
		((Force) forceB).addUnit(u);
	}
	
//	@Override
//	public void allUnitSelectOrders() {
//		forceA.selectOrders(orderList);
//		forceB.selectOrders(orderList);
//	}
	
//	@Override
//	public void allUnitResetPosture() {
//		forceA.resetPosture();
//		forceB.resetPosture();
//	}
//	
//	@Override
//	public void allUnitBuffsDecrement() {
//		forceA.buffsDecrement();
//		forceB.buffsDecrement();
//	}


}
