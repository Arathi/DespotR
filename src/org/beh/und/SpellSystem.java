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
		// TODO ���Ĵ���
		Result result = new Result();
		SkillInfo info=(SkillInfo) order.action;
		int spellCode=info.getParam();
		boolean success=true;
		int damage=0;
		//System.out.println("���Ĵ���: "+spellCode);
		if (spellCode==ORDER_CODE_SPELL_FIRE){
			System.out.println("�������ϵ����");
			//
		}
		if (spellCode==ORDER_CODE_SPELL_HEAL){
			//System.out.println("���������ϵ����");
			System.out.println(order.src.getName()+"ʩ��"+info.getName());
			//����
			success=order.src.changeMp(-info.mpCost);
			//����
			if (success){
				int healAmount=Util.getRandomInt(info.getData(1), info.getData(2));
				order.target.changeHp(healAmount);
			}
			else{
				System.out.println("���Ƿ���ֵ����");
			}
		}
		if (spellCode==ORDER_CODE_SPELL_SLEEP){
			System.out.println("���������ϵ����");
			
		}
		if (spellCode==ORDER_CODE_SPELL_SILENCE){
			System.out.println("������ɶ�ϵ����");
			
		}
		if (spellCode==ORDER_CODE_SPELL_DRAGONFIRE){
			//System.out.println("��������ϵ����");
			System.out.println(order.src.getName()+"�³�"+info.getName());
			damage=Util.getRandomInt(info.getData(1), info.getData(2));
			order.target.changeHp(-damage);
		}
		return result;
	}
}
