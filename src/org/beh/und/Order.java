package org.beh.und;

import java.util.*;

import org.beh.und.template.*;

/**
 * <b>������</b>
 * <br/>
 * ��װ��һ���������ϸ��Ϣ
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
	
	protected String actionTextDesc; //������������
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
	 * ������������
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
	 * ִ�и�����
	 * ������浽�����(results)��
	 */
	public void execute(){
		results=new ArrayList<Result>();
		if (src.isCanNotBattle()){
			return;
		}
		int orderCode=action.getCode();
		switch (orderCode){
		case ORDER_CODE_ATTACK:
			setActionTextDesc(src.getName()+"��������");
			addResult(src.attack(targets.get(0)));
			break;
		case ORDER_CODE_DEFENCE:
			setActionTextDesc(src.getName()+"���ڷ�����...");
			break;
		case ORDER_CODE_RUN:
			setActionTextDesc(src.getName()+"����");
			addResult(src.run());
			break;
		case ORDER_CODE_SPELL:
			//TODO ����ħ��ϵͳ
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
		// TODO ��Ӹ���Ԥ����Ŀǰֻ�з�����ҪԤ����
		int orderCode=action.getCode();
		switch (orderCode){
		case ORDER_CODE_DEFENCE:
			//System.out.println(src.getName()+"����Ϊ������̬");
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
