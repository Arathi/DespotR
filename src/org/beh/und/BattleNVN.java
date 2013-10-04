package org.beh.und;

public class BattleNVN extends Battle {
	
	public BattleNVN(Force a, Force b){
		//addToForceA(a);
		//addToForceB(b);
		forceA=a;
		forceB=b;

		init();
	}
	
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
	
}
