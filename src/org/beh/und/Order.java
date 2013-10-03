package org.beh.und;

import java.util.*;

import org.beh.und.template.*;

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
	protected List<Unit> targets;
	
	//protected int type;
	//protected int actionId;
	protected int param;
	//protected int spellCode;
	
	protected int agi;
	
	protected ActionInfo action;
	
	protected String actionTextDesc; //动作文字描述
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
	
	public Order(Unit src, List<Unit> targets, ActionInfo action){
		this.src=src;
		this.targets=targets;
		this.action=action;
		agi=src.getAgi();
	}
	
	public int getAgi(){
		return agi;
	}
	
	public ArrayList<Result> getResults(){
		return results;
	}
	
	/**
	 * 用于命令排序
	 */
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
		results=new ArrayList<Result>();
		if (src.isCanNotBattle()){
			return;
		}
		int orderCode=action.getCode();
		switch (orderCode){
		case ORDER_CODE_ATTACK:
			setActionTextDesc(src.getName()+"发动攻击");
			addResult(src.attack(targets.get(0)));
			break;
		case ORDER_CODE_DEFENCE:
			setActionTextDesc(src.getName()+"正在防御中...");
			break;
		case ORDER_CODE_RUN:
			setActionTextDesc(src.getName()+"逃跑");
			addResult(src.run());
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
			//System.out.println(src.getName()+"调整为防御姿态");
			src.defence();
			break;
		}
	}
	
	public void addResult(Result result){
		results.add(result);
	}
	
	public void setActionTextDesc(String desc){
		actionTextDesc=desc;
	}
	public String getActionTextDesc(){
		return actionTextDesc;
	}
	
//	@Deprecated
//	public Result createResult(int type){
//		Result result = new Result(targets.get(0), type);
//		return result;
//	}
	
	public Result createResult(Unit target, int type){
		Result result = new Result(target, type);
		return result;
	}
	
	
}
