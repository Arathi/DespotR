package org.beh.und.xml;

import org.beh.und.*;

public abstract class XMLHandler {
	public abstract int initMonsters();
	public abstract int initActions();
	public abstract int initSkills();
	
	public boolean setMonsterParam(MonsterInfo monsterInfo, String paramName, String paramValue){
		String name=paramName, strValue=paramValue,  resistType;
		int intValue, resistIndex=0;
		if (name.equals("id")){
			strValue = paramValue.substring(3);
			intValue = Util.toInteger(strValue);
			monsterInfo.setId(intValue);
		}
		if (name.endsWith("name")){
			//strValue = parser.nextText();
			monsterInfo.setName(strValue);
		}
		if (name.equals("hp")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setHp(intValue);
		}
		if (name.equals("mp")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setMp(intValue);
		}
		if (name.equals("atk")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setAtk(intValue);
		}
		if (name.equals("def")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setDef(intValue);
		}
		if (name.equals("agi")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setAgi(intValue);
		}
		if (name.equals("jink")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setJink(intValue);
		}
		if (name.equals("inte")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setInte(intValue);
		}
		if (name.equals("exp")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setExp(intValue);
		}
		if (name.equals("gold")){
			intValue = Util.toInteger(strValue);
			monsterInfo.setGold(intValue);
		}
		if (name.startsWith("resist")){
			resistType=name.substring(6);
			String resistPair=strValue;
			//intValue=toInteger();
			//TODO 类型可配置化改造
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
		if (name.startsWith("action")){
			int actionOrder=Util.toInteger(name.substring(6));
			if (actionOrder>0 || actionOrder<=8){
				//strValue = parser.nextText();
				//monsterInfo.setActionString(actionOrder-1, strValue);
				//intValue = ActionInfo.getActionId(strValue);
				//intValue = strValue.hashCode();
				monsterInfo.setAction(actionOrder-1, strValue);
			}
		}
		if (name.equals("actmode")){
			//strValue=parser.nextText();
			if (strValue.equals("fixed")){
				monsterInfo.setActmode(MonsterInfo.ACTION_MODE_FIXED);
			}
			else{
				monsterInfo.setActmode(MonsterInfo.ACTION_MODE_RANDOM);
			}
		}
		if (name.equals("skill")){
			//strValue=parser.nextText(); //获取技能ID
			intValue=Util.id2int(strValue);
			if (intValue!=0){
				monsterInfo.getSkills().add(intValue);
			}
			else{
				System.out.println("读取到错误的技能ID: "+strValue);
				return false;
			}
		}
		return true;
	}
	public boolean setActionParam(ActionInfo actionInfo, String paramName, String paramValue){
		String name=paramName;
		int id=0;
		boolean findParam=true;
		if (name.equals("id")){
			//id=Util.toInteger(paramValue);
			id = Util.id2int(paramValue);
			actionInfo.setId(id);
		}
		else if (name.equals("string")){
			actionInfo.setOrderString(paramValue);
		}
		else if (name.endsWith("name")){
			actionInfo.setName(paramValue);
		}
		else if (name.equals("desc")){
			actionInfo.setDescription(paramValue);
		}
		else if (name.equals("code")){
			actionInfo.setCode(paramValue);
		}
		else if (name.equals("type")){
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
}
