package org.beh.und.test;

import org.beh.und.*;
import org.beh.und.template.ActionInfo;
import org.beh.und.template.MonsterInfo;
import org.beh.und.template.SkillInfo;

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
