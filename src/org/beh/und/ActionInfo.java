package org.beh.und;

import java.util.*;

import org.beh.und.xml.PullHandler;

/**
 * <b>动作信息类</b>
 * <br/>
 * 用于描述动作相关的信息
 * @author Arathi
 *
 */
public class ActionInfo {
	public static final int TARGET_TYPE_NONE = 0;
	public static final int TARGET_TYPE_ENEMY = 1;
	public static final int TARGET_TYPE_ALLY = 2;

	public static final int TARGET_RANGE_ALL=0;
	public static final int TARGET_RANGE_SINGlE=1;
	public static final int TARGET_RANGE_TEAM=2;
	
	public static ActionInfo[] actionInfoList;
	public static Map<String, Integer> actionStringMap;
	public static Map<Integer, Integer> actionIdMap;
	
	private int actionId;
	private String actionString;
	private String name;
	private String description;
	
	private String codeStr;
	private int code;
	private int spellCode;
	
	private int targetType;
	private int targetRange;
	
	public int getActionId() {
		return actionId;
	}
	public void setActionId(int actionId) {
		this.actionId = actionId;
	}
	public String getActionString() {
		return actionString;
	}
	public void setActionString(String actionString) {
		this.actionString = actionString;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public static void init(){
		PullHandler.initActions();
		int i,amount=actionInfoList.length;
		actionStringMap=new HashMap<String, Integer>();
		actionIdMap=new HashMap<Integer,Integer>();
		for (i=1; i<amount; i++){
			ActionInfo info=actionInfoList[i];
			actionStringMap.put(info.getActionString(), i);
			actionIdMap.put(info.getActionId(), i);
		}
	}
	
	public static int getActionId(String actionString){
		int actionIndex=actionStringMap.get(actionString);
		int actionId=actionInfoList[actionIndex].actionId;
		return actionId;
	}
	
	public static ActionInfo getActionInfo(int actionId) {
		int actionIndex=actionIdMap.get(actionId);
		ActionInfo actionInfo=actionInfoList[actionIndex];
		return actionInfo;
	}
	
	public void setTargetUnitSide(int targetType) {
		this.targetType=targetType;
	}
	public void setTargetRange(int targetRange) {
		this.targetRange=targetRange;
	}
	
	public int getTargetUnitSide(){
		return targetType;
	}

	public int getTargetRange(){
		return targetRange;
	}
	
	public void setCode(String codeStr){
		this.codeStr=codeStr;
		//转换为对应的code
		//this.code = Order.OrderCodeMap;
		if (Order.OrderCodeMap.containsKey(codeStr)){
			this.code=Order.OrderCodeMap.get(codeStr);
		}
		else if (SpellSystem.SpellCodeMap.containsKey(codeStr)){
			this.code=Order.ORDER_CODE_SPELL;
			this.spellCode=SpellSystem.SpellCodeMap.get(codeStr);
		}
		else{
			this.code=Order.ORDER_CODE_ATTACK;
		}
	}
	
	public int getCode(){
		return code;
	}

	@Deprecated
	public String getCodeStr(){
		return codeStr;
	}
	
	public int getSpellCode() {
		return spellCode;
	}
	
	public String toString(){
		String info="No."+actionId+"("+actionString+")";
		return info;
	}
}
