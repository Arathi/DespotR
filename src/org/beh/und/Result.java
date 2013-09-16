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
	
	Unit src;
	Unit target;
	int type;
	int value; //治疗量、伤害量、效果回合数等
	boolean critFlag; //致命一击
	boolean jinkFlag; //闪避
	boolean success; //动作执行结果为成功
	
	public Result(){
		
	}
	
	public String toString(){
		String result="";
		if (type==RESULT_TYPE_DAMAGE){
			result+=src.getName()+"受到"+value+"点伤害";
		}
		if (type==RESULT_TYPE_HEAL){
			result+=src.getName()+"得到"+value+"点治疗";
		}
		//return super.toString();
		return result;
	}
}
