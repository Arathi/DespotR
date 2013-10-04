package org.beh.und;

public class Battle1V1 extends Battle {

	public Battle1V1(Unit a, Unit b){
		addToForceA(a);
		addToForceB(b);

		init();
	}
	
	@Override
	public void addToForceA(Unit u){
		//unitA=u;
		forceA=u;
	}
	
	@Override
	public void addToForceB(Unit u){
		//unitB=u;
		forceB=u;
	}
	
}
