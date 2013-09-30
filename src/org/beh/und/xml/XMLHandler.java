package org.beh.und.xml;

import org.beh.und.*;
import org.beh.und.template.*;

public abstract class XMLHandler {
	public abstract int initMonsters();
	public abstract int initActions();
	public abstract int initSkills();
	public abstract int initBuffs();
	
	public boolean setMonsterParam(MonsterInfo monsterInfo, String paramName, String paramValue){
		String resistType;
		int intValue, resistIndex=0;
		if (paramName.equals("id")){
			paramValue = paramValue.substring(3);
			intValue = Util.toInteger(paramValue);
			monsterInfo.setId(intValue);
		}
		if (paramName.endsWith("name")){
			monsterInfo.setName(paramValue);
		}
		if (paramName.equals("hp")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setHp(intValue);
		}
		if (paramName.equals("mp")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setMp(intValue);
		}
		if (paramName.equals("atk")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setAtk(intValue);
		}
		if (paramName.equals("def")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setDef(intValue);
		}
		if (paramName.equals("agi")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setAgi(intValue);
		}
		if (paramName.equals("jink")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setJink(intValue);
		}
		if (paramName.equals("inte")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setInte(intValue);
		}
		if (paramName.equals("exp")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setExp(intValue);
		}
		if (paramName.equals("gold")){
			intValue = Util.toInteger(paramValue);
			monsterInfo.setGold(intValue);
		}
		if (paramName.startsWith("resist")){
			resistType=paramName.substring(6);
			String resistPair=paramValue;
			//intValue=toInteger();
			//TODO 抗性类型可配置化改造
			if (resistType.equals("gira")){
				resistIndex=1;
			}
			if (resistType.equals("rariho")){
				resistIndex=2;
			}
			if (resistType.equals("mahoton")){
				resistIndex=3;
			}
			if (resistIndex>0){
				int delimter_index= resistPair.indexOf('/');
				if (delimter_index>=0 && delimter_index<resistPair.length()){
					String resistStr=resistPair.substring(0, delimter_index);
					String resistMaxStr=resistPair.substring(delimter_index+1);
					monsterInfo.setResist(resistIndex, Util.toInteger(resistStr), Util.toInteger(resistMaxStr));
				}
			}
		}
		if (paramName.startsWith("action")){
			int actionOrder=Util.toInteger(paramName.substring(6));
			if (actionOrder>0 || actionOrder<=8){
				monsterInfo.setAction(actionOrder-1, paramValue);
			}
		}
		if (paramName.equals("actmode")){
			if (paramValue.equals("fixed")){
				monsterInfo.setActmode(MonsterInfo.ACTION_MODE_FIXED);
			}
			else{
				monsterInfo.setActmode(MonsterInfo.ACTION_MODE_RANDOM);
			}
		}
		if (paramName.equals("skill")){
			intValue=Util.id2int(paramValue);
			if (intValue!=0){
				monsterInfo.getSkills().add(intValue);
			}
			else{
				System.out.println("读取到错误的技能ID: "+paramValue);
				return false;
			}
		}
		return true;
	}
	
	public boolean setActionParam(ActionInfo actionInfo, String paramName, String paramValue){
		int id=0;
		boolean findParam=true;
		if (paramName.equals("id")){
			id = Util.id2int(paramValue);
			actionInfo.setId(id);
		}
		else if (paramName.equals("string")){
			actionInfo.setOrderString(paramValue);
		}
		else if (paramName.endsWith("name")){
			actionInfo.setName(paramValue);
		}
		else if (paramName.equals("desc")){
			actionInfo.setDescription(paramValue);
		}
		else if (paramName.equals("code")){
			actionInfo.setCode(paramValue);
		}
		else if (paramName.equals("type")){
			String[] targetTypes=paramValue.split(",");
			int i, size=targetTypes.length;
			for (i=0; i<size; i++){
				if (targetTypes[i].equals("enemy")){
					actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_ENEMY);
				}
				if (targetTypes[i].equals("ally")){
					actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_ALLY);
				}
				if (targetTypes[i].equals("none")){
					actionInfo.setTargetUnitSide(ActionInfo.TARGET_TYPE_NONE);
				}
				if (targetTypes[i].equals("single")){
					actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_SINGlE);
				}
				if (targetTypes[i].equals("team")){
					actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_TEAM);
				}
				if (targetTypes[i].equals("all")){
					actionInfo.setTargetRange(ActionInfo.TARGET_RANGE_ALL);
				}
			}
		}
		else{
			findParam=false;
		}
		return findParam;
	}	
	
	public boolean setSkillParam(SkillInfo skillInfo, String paramName, String paramValue){
		String name=paramName;
		int intValue;
		if (name.equals("mpcost")){
			intValue=Util.toInteger(paramValue);
			skillInfo.setMpCost(intValue);
		}
		if (name.equals("learnlevel")){
			intValue=Util.toInteger(paramValue);
			skillInfo.setLearnLevel(intValue);
		}
		if (name.startsWith("data")){
			//System.out.println("暂不处理DATA");
			//TODO 处理DATA
			intValue=Util.toInteger(name.substring(4));
			skillInfo.setData(intValue, Util.toInteger(paramValue) );
		}
		return true;
	}
	public boolean setBuffParam(BuffInfo buffInfo, String paramName, String paramValue){
		return true;
	}
}
