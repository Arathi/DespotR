package org.beh.und;

import java.util.*;

/**
 * <b>命令类</b>
 * <br/>
 * 封装了一个命令的详细信息
 * @author Arathi
 */
public class Order implements Comparable<Order>{
	public static final int ORDER_CODE_NONE=0;
	public static final int ORDER_CODE_ATTACK=1;
	public static final int ORDER_CODE_DEFENCE=2;
	public static final int ORDER_CODE_RUN=3;
	public static final int ORDER_CODE_ITEM=4;
	public static final int ORDER_CODE_SPELL=5;
	
	protected Unit src;
	protected Unit target;
	
	//protected int type;
	//protected int actionId;
	protected int param;
	//protected int spellCode;
	
	protected int agi;
	
	protected ActionInfo action;
	
	protected ArrayList<Result> results;
	
	public static Map<String,Integer> OrderCodeMap;
	
	static{
		OrderCodeMap=new HashMap<String,Integer>();
		OrderCodeMap.put("none", ORDER_CODE_NONE);
		OrderCodeMap.put("attack", ORDER_CODE_ATTACK);
		OrderCodeMap.put("defence", ORDER_CODE_DEFENCE);
		OrderCodeMap.put("run", ORDER_CODE_RUN);
		OrderCodeMap.put("spell", ORDER_CODE_SPELL);
	}
	
	public Order(Unit src, Unit target, ActionInfo action){
		this.src=src;
		this.target=target;
		//this.actionId=actionId;
		this.action=action;
		agi=src.getAgi();
	}
	
	public int getAgi(){
		return agi;
	}
	
	public ArrayList<Result> getResults(){
		return results;
	}
	
	@Override
	public int compareTo(Order other) {
		if (agi>other.getAgi()){
			return -1;
		}
		else if (agi<other.getAgi()) {
			return +1;
		}
		return 0;
	}
	
	/**
	 * 执行该命令
	 * 结果保存到结果表(results)中
	 */
	public void execute(){
		//TODO 实现 命令的执行(实现魔法)
		results=new ArrayList<Result>();
		if (src.isCanNotBattle()){
			return;
		}
		int orderCode=action.getCode();
		switch (orderCode){
		case ORDER_CODE_ATTACK:
			System.out.println(src.getName()+"发动攻击");
			src.attack(target);
			break;
		case ORDER_CODE_DEFENCE:
			System.out.println("正在防御中...");
			break;
		case ORDER_CODE_RUN:
			src.run();
			System.out.println(src.getName()+"逃跑");
			break;
		case ORDER_CODE_SPELL:
			//TODO 完善魔法系统
			SpellSystem.execute(this);
			break;
		}
	}
	
	@Override
	public String toString(){
		String info=src+": ["+agi+"] "+action;
		return info;
	}

	public void pretreatment() {
		// TODO 添加更多预处理（目前只有防御需要预处理）
		int orderCode=action.getCode();
		switch (orderCode){
		case ORDER_CODE_DEFENCE:
			System.out.println(src.getName()+"调整为防御姿态");
			src.defence();
			break;
		}
	}
	
}
