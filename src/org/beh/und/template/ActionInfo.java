package org.beh.und.template;

import java.util.*;

import org.beh.und.Order;
import org.beh.und.SpellSystem;
import org.beh.und.Util;

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
	public static Map<String, Integer> actionOrderStringMap;
	public static Map<Integer, Integer> actionOrderMap;
	public static Map<Integer, Integer> actionIdMap;
	
	protected int objectId;
	protected String orderString;
	protected int orderCode;
	protected String name;
	protected String description;
	
	protected String codeStr;
	protected int code;
	
	protected int targetType;
	protected int targetRange;

	public int getId() {
		return objectId;
	}
	public void setId(int id) {
		this.objectId = id;
	}
	@Deprecated
	public final String getOrderString() {
		return orderString;
	}
	public final int getOrderCode() {
		return orderCode;
	}
	public final void setOrderString(String ostring) {
		this.orderString = ostring;
		this.orderCode = ostring.hashCode();
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name;
	}
	public final String getDescription() {
		return description;
	}
	public final void setDescription(String description) {
		this.description = description;
	}
	public final void setTargetUnitSide(int targetType) {
		this.targetType=targetType;
	}
	public final void setTargetRange(int targetRange) {
		this.targetRange=targetRange;
	}
	public final int getTargetUnitSide(){
		return targetType;
	}
	public final int getTargetRange(){
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
			setParam(SpellSystem.SpellCodeMap.get(codeStr));
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
	/**
	 * 获取动作参数，SkillInfo要重写这个方法(不要对ActionInfo调用该方法)
	 * @return 如果调用了，返回一个错误的参数值
	 */
	@Deprecated
	public int getParam() {
		return -1;
	}
	/**
	 * 设置动作参数，SkillInfo要重写这个方法<br/>
	 * (不要对ActionInfo调用该方法，即使调用了也没有实际操作)
	 * @param param
	 */
	@Deprecated
	public void setParam(int param) {}
	
	/**
	 * 初始化Action系统
	 */
	public static void init(){
		System.out.println("Action系统正在初始化");
		Util.getXmlHandler().initActions();
		int i,amount=actionInfoList.length;
		actionOrderStringMap=new HashMap<String, Integer>();
		actionOrderMap = new HashMap<Integer, Integer>();
		actionIdMap=new HashMap<Integer,Integer>();
		for (i=1; i<amount; i++){
			ActionInfo info=actionInfoList[i];
			actionOrderStringMap.put(info.getOrderString(), i);
			actionOrderMap.put(info.getOrderCode(), i);
			actionIdMap.put(info.getId(), i);
		}
	}
	
	/**
	 * 通过动作字符串获取对应动作ID
	 * @param actionString
	 * @return
	 */
	public static int getActionId(String actionString){
		if (!actionOrderStringMap.containsKey(actionString)){
			return 0;
		}
		int actionIndex=actionOrderStringMap.get(actionString);
		int actionId=actionInfoList[actionIndex].getId();
		return actionId;
	}
	
	/**
	 * 通过orderId获取对应动作信息
	 * @param orderId
	 * @return
	 */
	public static ActionInfo getActionInfo(int orderId) {
		int actionIndex=0;
		//if (actionIdMap.containsKey(actionId)){
			//actionIndex=actionIdMap.get(actionId);
		//}
		if (actionOrderMap.containsKey(orderId)){
			actionIndex=actionOrderMap.get(orderId);
		}
		ActionInfo actionInfo=actionInfoList[actionIndex];
		return actionInfo;
	}
	/**
	 * 通过动作ID([xxxx]形式)获取对应动作信息
	 * @param actionId
	 * @return
	 */
	public static ActionInfo getActionInfo(String id) {
		return getActionInfo(Util.id2int(id));
	}
	
	public String toString(){
		String info="["+Util.int2id(objectId)+"] ("+orderString+")";
		return info;
	}
	
}
