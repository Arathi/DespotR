package org.beh.und;

import java.util.*;

public class Force {
	protected List<Unit> units;
	protected Force enemyForce;
	
	public Force(){
		units=new ArrayList<Unit>();
	}
	
	public void addUnit(Unit u){
		units.add(u);
	}
	
	public void setEnemyForce(Force f){
		enemyForce=f;
	}

	public void selectOrders() {
		// TODO Auto-generated method stub
		
	}

	public void resetPosture() {
		// TODO Auto-generated method stub
		
	}

	public void buffsDecrement() {
		// TODO Auto-generated method stub
		
	}

	public boolean isCanNotBattle() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
