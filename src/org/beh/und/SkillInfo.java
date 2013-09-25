package org.beh.und;

import java.util.*;

public class SkillInfo extends ActionInfo {
	public static SkillInfo[] skillInfoList;
	public static Map<Integer, Integer> skillIdMap;
	
	public static final int SkillDataAmount = 8;

//	<action>
//		<id>[aatk]</id>
//		<string>attack</string>
//		<name>攻撃</name>
//		<type>enemy,single</type>
//		<code>attack</code>
//		<desc>攻击伤害=(攻方攻击力-守方守备力*50%)*45~55%</desc>
//	</action>
//	<skill>
//		<id>[hoim]</id>
//		<name>ホイミ</name>
//		<cnname>荷伊米</cnname> 
//		<desc>我方HP+25~30</desc>
//		<mpcost>3</mpcost>
//		<learnlevel>3</learnlevel>
//		<string>hoimi</string>
//		<code>heal</code>
//		<type>ally,single</type>
//		<data1>25</data1>
//		<data2>30</data2>
//	</skill>

	protected int mpCost;
	protected int learnLevel;
	protected int skillCode;
	protected String skillStr;
	protected ArrayList<Integer> datas;
	
	public SkillInfo(){
		datas = new ArrayList<Integer>();
		for (int i=0; i<SkillDataAmount; i++){
			datas.add(0);
		}
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
	@Deprecated
	public ArrayList<Integer> getDatas() {
		return datas;
	}
	@Deprecated
	public void setDatas(ArrayList<Integer> datas) {
		this.datas = datas;
	}
	public int getData(int index){
		return datas.get(index);
	}
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
			//info.datas.trimToSize(); //省空间
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
