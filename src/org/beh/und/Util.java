package org.beh.und;

import java.awt.peer.SystemTrayPeer;
import java.util.Random;

/**
 * <b>常用工具类</b>
 * @author Arathi
 *
 */
public class Util {
	public static Random random;//=new Random();
	
	static{
		random = new Random();
		random.setSeed(System.currentTimeMillis());
	}
	
	/**
	 * 获取区间内的随机实数
	 * @param min 最小取值
	 * @param max 最大取值
	 * @return 介于min和max之间的实数
	 */
	public static double getRandomReal(double min, double max){
		double delta=max-min;
		double randomReal = min+delta*random.nextDouble();
		//System.out.println("获取随机实数："+randomReal);
		return randomReal;
	}
	
	/**
	 * 获取区间内的随机整数
	 * @param min 最小取值
	 * @param max 最大取值
	 * @return 介于min和max之间的整数
	 */
	public static int getRandomInt(int min, int max){
		int delta=max-min;
		int randomInt=min+random.nextInt(delta);
		//System.out.println("获取随机整数："+randomInt);
		return randomInt;
	}
	
	/**
	 * 将字符串转换为整数
	 * @param value 输入字符串
	 * @return 转换出的整数
	 */
	public static int toInteger(String value){
		int digit=0;
		try {
			digit=Integer.parseInt(value);
		}
		catch (NumberFormatException e){
			digit=0;
		}
		return digit;
	}
}
