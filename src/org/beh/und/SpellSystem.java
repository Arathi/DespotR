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

	public static void execute(Order order) {
		// TODO ���Ĵ���
		SkillInfo info=(SkillInfo) order.action;
		int spellCode=info.getParam();
		System.out.println("���Ĵ���: "+spellCode);
		if (spellCode==ORDER_CODE_SPELL_FIRE){
			
		}
		if (spellCode==ORDER_CODE_SPELL_HEAL){
			
		}
		if (spellCode==ORDER_CODE_SPELL_SLEEP){
			
		}
		if (spellCode==ORDER_CODE_SPELL_SILENCE){
			
		}
		if (spellCode==ORDER_CODE_SPELL_DRAGONFIRE){
			
		}
	}
}
