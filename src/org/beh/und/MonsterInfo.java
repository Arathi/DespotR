package org.beh.und;

import org.beh.und.xml.PullHandler;

public class MonsterInfo {
	public static final int ACTION_MODE_RANDOM = 0;
	public static final int ACTION_MODE_FIXED  = 1;
	public static final int ACTION_AMOUNT=8;
	
	public static final int RESIST_TYPE=3;
	
	public static MonsterInfo[] monsterInfoList;
	
	private String icon;
	private int id;
	private String name;
	private int hp;
	private int mp;
	private int atk;
	private int def;
	private int agi;
	private int jink;
	private int inte;
	private int exp;
	private int gold;
	private int[] resists;
	private int[] resistsMax;
	private int[] actions;
	private String[] actionStrings;
	private int actmode;
	
	public MonsterInfo() {
		resists=new int[RESIST_TYPE+1];
		resistsMax=new int[RESIST_TYPE+1];
		actions=new int[ACTION_AMOUNT];
		actionStrings=new String[ACTION_AMOUNT];
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
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
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMp() {
		return mp;
	}
	public void setMp(int mp) {
		this.mp = mp;
	}
	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getAgi() {
		return agi;
	}
	public void setAgi(int agi) {
		this.agi = agi;
	}
	public int getJink() {
		return jink;
	}
	public void setJink(int jink) {
		this.jink = jink;
	}
	public int getInte() {
		return inte;
	}
	public void setInte(int inte) {
		this.inte = inte;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getGold() {
		return gold;
	}
	public void setGold(int gold) {
		this.gold = gold;
	}
	public int[] getResists() {
		return resists;
	}
	public void setResists(int[] resists) {
		this.resists = resists;
	}
	public void setResist(int resistIndex, int resistValue, int resistMaxValue){
		this.resists[resistIndex] = resistValue;
		this.resistsMax[resistIndex] = resistMaxValue;
	}
	public int[] getActions() {
		return actions;
	}
	public int getAction(int index) {
		return actions[index];
	}
	public void setActions(int[] actions) {
		this.actions = actions;
	}
	public void setAction(int index, int actionId) {
		this.actions[index] = actionId;
	}
	public String[] getActionStrings() {
		return actionStrings;
	}
	public String getActionString(int actionOrder) {
		return actionStrings[actionOrder];
	}
	public void setActionStrings(String[] actionStrings) {
		this.actionStrings = actionStrings;
	}
	public void setActionString(int actionOrder, String actionString) {
		this.actionStrings[actionOrder] = actionString;
	}
	public int getActmode() {
		return actmode;
	}
	public void setActmode(int actmode) {
		this.actmode = actmode;
	}
	
	@Override
	public String toString(){
		String ret="";
		ret+="No."+id+" "+name+" ( "+exp+"E, "+gold+"G"+" )";
		
		if (ret.equals("")) return super.toString();
		return ret;
	}
	
	public Monster create(){
		Monster monster=new Monster(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists, actions, actmode);
//		monster.init(name, hp, mp, atk, def, agi, jink, inte, exp, gold, resists, actions, actmode);
		return monster;
	}
	
	public static void init(){
		PullHandler.initMonsters(); //MonsterInfoList
	}
	
	public static Monster create(int id){
		if (monsterInfoList==null) return null;
		return monsterInfoList[id].create();
	}
}
