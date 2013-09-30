package org.beh.und;

/**
 * <b>结果类</b>
 * <br/>
 * 封装一个命令的执行结果
 * @author Arathi
 */
public class Result {
	public static final int RESULT_TYPE_NONE=0;
	public static final int RESULT_TYPE_DAMAGE=1;
	public static final int RESULT_TYPE_HEAL=2;
	public static final int RESULT_TYPE_BUFF=3;
	
	//public static final int 
	
	Unit src;
	Unit target;
	int type;
	int value; //治疗量、伤害量、效果回合数等
	boolean critFlag; //致命一击
	boolean jinkFlag; //闪避
	boolean deathFlag; //致死
	boolean success; //动作执行结果为成功
	//boolean sleepFlag; //睡眠标志
	//boolean scienceFlag; //沉默标志
	
	String message; // 错误时的消息
	
	protected Result(Unit target, int type){
		this.target=target;
		this.type=type;
	}
	
	public String toString(){
		String result="";
		String targetName=target.getName();
		if (type==RESULT_TYPE_DAMAGE){
			if (jinkFlag){
				result+="但是"+targetName+"躲开了";
			}
			else{
				if (critFlag) result+="会心的一击！\n";
				if (value>0){
					result+=targetName+"受到"+value+"点伤害";
				}
				else{
					result+="但是"+targetName+"毫发无损";
				}
			}
		}
		if (type==RESULT_TYPE_HEAL){
			if (value>0){
				result+=targetName+"得到"+value+"点治疗";
			}
			else{
				result+="但是，好像没什么效果";
			}
		}
		if (type==RESULT_TYPE_BUFF){
			result+=targetName;
			if (value==SpellSystem.BUFFID_FIXED_SLEEP){
				result+="睡着了...";
			}
			else if (value==SpellSystem.BUFFID_FIXED_SLIENCE){
				result+="沉默了!";
			}
		}
		if (type==RESULT_TYPE_NONE){
			result+=message;
		}
		return result;
	}
}
