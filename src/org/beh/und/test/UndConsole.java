package org.beh.und.test;

import org.beh.und.*;

public class UndConsole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ActionInfo.init();
		SkillInfo.init();
		MonsterInfo.init();
		
		Monster monsterA=MonsterInfo.create(37);
		Monster monsterB=MonsterInfo.create(38);
		
		int MaxTurn=30, turnId=0;
		Battle1V1 battle = new Battle1V1(monsterA, monsterB);
		while (!battle.isEnd() && turnId<MaxTurn){
			battle.handle();
			turnId++;
		}
		
//		System.out.println(monsterA);
//		System.out.println(monsterB);
//		
//		monsterA.attack(monsterB);
//		monsterB.attack(monsterA);
//		monsterA.attack(monsterB);
//		monsterB.attack(monsterA);
//		monsterA.attack(monsterB);
//		monsterB.attack(monsterA);
//		
//		System.out.println(monsterA);
//		System.out.println(monsterB);
	}

}
