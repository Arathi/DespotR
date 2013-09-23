package org.beh.und;

import java.util.*;

public class SkillInfo extends ActionInfo {
	public static SkillInfo[] skillInfoList;
	public static Map<Integer, Integer> skillIdMap;
	
//	<action>
//		<id>[aatk]</id>
//		<string>attack</string>
//		<name>攻撃</name>
//		<type>enemy,single</type>
//		<code>attack</code>
//		<desc>攻击伤害=(攻方攻击力-守方守备力*50%)*45~55%</desc>
//	</action>
//	<skill>
//		<id>[hoim]</id> r
//		<name>ホイミ</name> r
//		<cnname>荷伊米</cnname> 
//		<desc>我方HP+25~30</desc> r
//		<mpcost>3</mpcost> new
//		<learnlevel>3</learnlevel> new
//		<string>hoimi</string> r
//		<code>heal</code> r
//		<type>ally,single</type> r
//		<data1>25</data1> new
//		<data2>30</data2> 
//	</skill>
	
	protected int mpCost;
	protected int learnLevel;
	protected int skillCode;
	protected String skillStr;
	protected ArrayList<Integer> datas;
	
	public int getMpCost() {
		return mpCost;
	}
	public void setMpCost(int mpCost) {
		this.mpCost = mpCost;
	}
	public int getLearnLevel() {
		return learnLevel;
	}
	public void setLearnLevel(int learnLevel) {
		this.learnLevel = learnLevel;
	}
	@Deprecated
	public ArrayList<Integer> getDatas() {
		return datas;
	}
	public void setDatas(ArrayList<Integer> datas) {
		this.datas = datas;
	}
	public int getData(int index){
		return datas.get(index);
	}
	@Deprecated
	public void setData(int index, int value){
		datas.set(index, value);
	}
	
	public int getParam(){
		return skillCode;
	}
	public void setParam(int skillCode){
		this.skillCode=skillCode;
	}
	
	public static void init(){
		System.out.println("Skill系统正在初始化");
		Util.getXmlHandler().initSkills();
		skillIdMap = new HashMap<Integer, Integer>();
		int i;
		for (i=1; i<skillInfoList.length; i++){
			SkillInfo info = skillInfoList[i];
			skillIdMap.put(info.getId(), i);
		}
	}
	
	public static SkillInfo getSkillInfo(int skillId){
		SkillInfo info = null;
		int index;
		if (skillIdMap.containsKey(skillId)){
			index = skillIdMap.get(skillId);
			info = skillInfoList[index];
		}
		return info;
	}
	
}
/*

int id;
String name;
String desc;
int mpCost;
int learnLevel;
String orderString;
String codeStr;
int[] datas;

public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public int getMpCost() {
	return mpCost;
}
public void setMpCost(int mpCost) {
	this.mpCost = mpCost;
}
public int getLearnLevel() {
	return learnLevel;
}
public void setLearnLevel(int learnLevel) {
	this.learnLevel = learnLevel;
}
public String getOrderString() {
	return orderString;
}
public void setOrderString(String orderString) {
	this.orderString = orderString;
}
public String getCodeStr() {
	return codeStr;
}
public void setCodeStr(String codeStr) {
	this.codeStr = codeStr;
}
public int[] getDatas() {
	return datas;
}
public int getData(int index){
	return datas[index];
}
public void setDatas(int[] datas) {
	this.datas = datas;
}
public void setData(int index, int data) {
	this.datas[index] = data;
}


*/