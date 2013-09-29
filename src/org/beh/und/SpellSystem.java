package org.beh.und;

import java.util.*;

public class SpellSystem {
	public static final int ORDER_CODE_SPELL_FIRE=11;
	public static final int ORDER_CODE_SPELL_HEAL=12;
	public static final int ORDER_CODE_SPELL_SLEEP=13;
	public static final int ORDER_CODE_SPELL_SILENCE=14;
	public static final int ORDER_CODE_SPELL_DRAGONFIRE=21;
	
	public static final int BUFFID_FIXED_SLEEP=1651731568;//Util.id2int("[aslp]"); //睡眠
	public static final int BUFFID_FIXED_SLIENCE=1651731561;//Util.id2int("[asli]"); //沉默
	
	public static Map<String,Integer> SpellCodeMap;
	
	static{
		SpellCodeMap = new HashMap<String,Integer>();
		SpellCodeMap.put("fire", ORDER_CODE_SPELL_FIRE);
		SpellCodeMap.put("heal", ORDER_CODE_SPELL_HEAL);
		SpellCodeMap.put("sleep", ORDER_CODE_SPELL_SLEEP);
		SpellCodeMap.put("silence", ORDER_CODE_SPELL_SILENCE);
		SpellCodeMap.put("dragonfire", ORDER_CODE_SPELL_DRAGONFIRE);
	}
	
	private static void doHurt(Order order, double min, double max) {
		Result result=order.createResult(Result.RESULT_TYPE_DAMAGE);
//		result.type=Result.RESULT_TYPE_DAMAGE;
		int damage = (int)( Util.getRandomReal(min, max) );
		int delta = order.target.changeHp(-damage);
		result.value = -delta;
		order.addResult(result);
	}
	
	private static void doHeal(Order order, double min, double max) {
		Result result=order.createResult(Result.RESULT_TYPE_HEAL);
//		result.type=Result.RESULT_TYPE_HEAL;
		int health = (int)( Util.getRandomReal(min, max) );
		int delta = order.target.changeHp(health);
		result.value = delta;
		order.addResult(result);
	}
	
	private static void doBuff(Order order, int buffId, int min, int max) {
		Result result=order.createResult(Result.RESULT_TYPE_BUFF);
//		result.type=Result.RESULT_TYPE_BUFF;
		int time = Util.getRandomInt(min, max+1);
		order.target.setBuff(buffId, time);
		result.value=buffId;
		order.addResult(result);
	}
	
	private static boolean skillProcessFrame(
			Order order, 
			SkillInfo skill,
			boolean ignoreSilence, 
			boolean ignoreLowMana){
		Result result = order.createResult(Result.RESULT_TYPE_NONE);
		Unit srcUnit=order.src;
		//首先，检查有没有处于“沉默”状态
		if (!ignoreSilence){
			if (srcUnit.checkBuff( BUFFID_FIXED_SLIENCE )){
				//System.out.println(srcUnit.getName()+"处于沉默状态，无法使用咒文");
				result.message=srcUnit.getName()+"处于沉默状态，无法使用咒文";
				order.addResult(result);
				return false;
			}
		}
		//然后，看看法力值是否足够施放该技能
		if (!ignoreLowMana){
			if (!srcUnit.changeMp(-skill.getMpCost(), false)){
				result.message="但是法力值不足，无法使用该咒文";
				order.addResult(result);
				return false;
			}
		}
		//TODO 魔法抗性检测
		result=null;
		return true;
	}
	
	public static void execute(Order order) {
		//Result result = new Result();
		SkillInfo skill=(SkillInfo) order.action;
		int spellCode=skill.getParam();
		boolean success=false;
		if (spellCode==ORDER_CODE_SPELL_FIRE){
			order.setActionTextDesc(order.src.getName()+"施放"+skill.getName());
			success=skillProcessFrame(order, skill, false, false);
			if ( success ) 
				doHurt(order, skill.getData(1), skill.getData(2));
		}
		if (spellCode==ORDER_CODE_SPELL_HEAL){
			order.setActionTextDesc(order.src.getName()+"施放"+skill.getName());
			success=skillProcessFrame(order, skill, false, false);
			if ( success ) 
				doHeal(order, skill.getData(1), skill.getData(2));
		}
		if (spellCode==ORDER_CODE_SPELL_SLEEP){
			order.setActionTextDesc(order.src.getName()+"施放"+skill.getName());
			success=skillProcessFrame(order, skill, false, false);
			if ( success ) 
				doBuff(order, BUFFID_FIXED_SLEEP, skill.getData(1), skill.getData(2) );
		}
		if (spellCode==ORDER_CODE_SPELL_SILENCE){
			order.setActionTextDesc(order.src.getName()+"施放"+skill.getName());
			success=skillProcessFrame(order, skill, false, false);
			if ( success ) 
				doBuff(order, BUFFID_FIXED_SLIENCE, skill.getData(1), skill.getData(2) );
		}
		if (spellCode==ORDER_CODE_SPELL_DRAGONFIRE){
			order.setActionTextDesc(order.src.getName()+"吐出"+skill.getName());
			success=skillProcessFrame(order, skill, true, true);
			if ( success ) 
				doHurt(order, skill.getData(1), skill.getData(2) );
		}
		//order.addResult(result);
		//result=null;
	}
}
