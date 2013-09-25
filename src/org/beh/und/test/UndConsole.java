package org.beh.und.test;

import org.beh.und.*;

public class UndConsole {

	public static void main(String[] args) {
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
		
	}

}
