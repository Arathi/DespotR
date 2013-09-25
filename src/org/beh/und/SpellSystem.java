package org.beh.und;

import java.util.*;

public class SpellSystem {
	public static final int ORDER_CODE_SPELL_FIRE=11;
	public static final int ORDER_CODE_SPELL_HEAL=12;
	public static final int ORDER_CODE_SPELL_SLEEP=13;
	public static final int ORDER_CODE_SPELL_SILENCE=14;
	public static final int ORDER_CODE_SPELL_DRAGONFIRE=21;
	
	public static Map<String,Integer> SpellCodeMap;
	
	static{
		SpellCodeMap = new HashMap<String,Integer>();
		SpellCodeMap.put("fire", ORDER_CODE_SPELL_FIRE);
		SpellCodeMap.put("heal", ORDER_CODE_SPELL_HEAL);
		SpellCodeMap.put("sleep", ORDER_CODE_SPELL_SLEEP);
		SpellCodeMap.put("silence", ORDER_CODE_SPELL_SILENCE);
		SpellCodeMap.put("dragonfire", ORDER_CODE_SPELL_DRAGONFIRE);
	}

	public static Result execute(Order order) {
		// TODO 咒文处理
		Result result = new Result();
		SkillInfo info=(SkillInfo) order.action;
		int spellCode=info.getParam();
		boolean success=true;
		int damage=0;
		//System.out.println("咒文处理: "+spellCode);
		if (spellCode==ORDER_CODE_SPELL_FIRE){
			System.out.println("处理基拉系咒文");
			//
		}
		if (spellCode==ORDER_CODE_SPELL_HEAL){
			//System.out.println("处理荷伊米系咒文");
			System.out.println(order.src.getName()+"施放"+info.getName());
			//扣蓝
			success=order.src.changeMp(-info.mpCost);
			//治疗
			if (success){
				int healAmount=Util.getRandomInt(info.getData(1), info.getData(2));
				order.target.changeHp(healAmount);
			}
			else{
				System.out.println("但是法力值不足");
			}
		}
		if (spellCode==ORDER_CODE_SPELL_SLEEP){
			System.out.println("处理拉里荷系咒文");
			
		}
		if (spellCode==ORDER_CODE_SPELL_SILENCE){
			System.out.println("处理马荷东系咒文");
			
		}
		if (spellCode==ORDER_CODE_SPELL_DRAGONFIRE){
			//System.out.println("处理龙火系技能");
			System.out.println(order.src.getName()+"吐出"+info.getName());
			damage=Util.getRandomInt(info.getData(1), info.getData(2));
			order.target.changeHp(-damage);
		}
		return result;
	}
}
