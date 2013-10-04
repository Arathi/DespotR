package org.beh.und;

import java.util.*;

public class Force implements IBattleFlow {
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
	
	@Override
	public void selectOrders(List<Order> orderList) {
		// TODO Auto-generated method stub
		int i;
		for (i=0; i<units.size(); i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false)
				u.selectOrders(orderList);
		}
	}
	
	@Override
	public void resetPosture() {
		// TODO Auto-generated method stub
		int i;
		for (i=0; i<units.size(); i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false)
				u.resetPosture();
		}
	}
	
	@Override
	public void buffsDecrement() {
		// TODO Auto-generated method stub
		int i;
		for (i=0; i<units.size(); i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false)
				u.buffsDecrement();
		}		
	}
	
	@Override
	public boolean isCanNotBattle() {
		//该组所有单位都战斗不能时，返回true
		boolean ret=true;
		int i;
		for (i=0; i<units.size(); i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false)
				return false;
		}
		return ret;
	}

	@Override
	public void setBattleEnvForAll(Battle battle) {
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null){
				u.setBattleEnv(battle);
			}
		}
	}

	@Override
	public boolean isCanNotStopRunning() {
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotStopRunning()==false){
				return false;
			}
		}
		return true;
	}

	@Override
	public int getAgiSum() {
		int agisum = 0;
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotStopRunning()){
				agisum+=u.getAgi();
			}
		}
		return agisum;
	}
	
	@Override
	public int getAtkSum(){
		int atksum = 0;
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotStopRunning()){
				atksum+=u.getAtk();
			}
		}
		return atksum;
	}
	
	@Override
	public void setEnemys(IBattleFlow f) {
		enemyForce=(Force) f;
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null){
				u.setEnemys(f);
			}
		}
	}
	
	@Override
	public Unit getSingleUnit(int rule){
		//返回一个活着的单位
		List<Unit> alives=new ArrayList<Unit>();
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false){
				alives.add(u);
			}
		}
		int randId;
		randId=Util.getRandomInt(0,alives.size()); //获取随机单位
		return alives.get(randId);
	}

	@Override
	public List<Unit> getAllUnits(int rule){
		List<Unit> alives=new ArrayList<Unit>();
		int i, size=units.size();
		for (i=0; i<size; i++){
			Unit u = units.get(i);
			if (u!=null && u.isCanNotBattle()==false){
				alives.add(u);
			}
		}
		return alives;
	}
	
}
