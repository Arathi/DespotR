package org.beh.und;

import java.util.Random;

import org.beh.und.xml.*;

/**
 * <b>常用工具类</b>
 * 
 * @author Arathi
 */
public class Util {
	public static Random random;// =new Random();
	protected static XMLHandler xmlHandler;

	static {
		// 初始化随机数生成器，设置随机数种子为当前时间
		random = new Random();
		random.setSeed(System.currentTimeMillis());
	}
	
	public static XMLHandler getXmlHandler(){
		if (xmlHandler==null){
			xmlHandler=new PullHandler();
		}
		return xmlHandler;
	}

	/**
	 * 获取区间内的随机实数
	 * 
	 * @param min
	 *            最小取值
	 * @param max
	 *            最大取值
	 * @return 介于min和max之间的实数
	 */
	public static double getRandomReal(double min, double max) {
		double delta = max - min;
		double randomReal = min + delta * random.nextDouble();
		// System.out.println("获取随机实数："+randomReal);
		return randomReal;
	}

	/**
	 * 获取区间内的随机整数
	 * 
	 * @param min
	 *            最小取值
	 * @param max
	 *            最大取值
	 * @return 介于min和max之间的整数
	 */
	public static int getRandomInt(int min, int max) {
		int delta = max - min;
		int randomInt = min + random.nextInt(delta);
		// System.out.println("获取随机整数："+randomInt);
		return randomInt;
	}

	/**
	 * 将字符串转换为整数
	 * 
	 * @param value
	 *            输入字符串
	 * @return 转换出的整数，如果value是非法值，结果为0
	 */
	public static int toInteger(String value) {
		int digit = 0;
		try {
			digit = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			digit = 0;
		}
		return digit;
	}

	/**
	 * 将一个4位字符物体ID转换为数字编号
	 * 
	 * @param value
	 *            格式为\[[0-9A-Za-z]{4}\]
	 * @return 当物体ID格式合法时，返回对应数字编号，否则返回0
	 */
	public static int id2int(String value) {
		int i, idCode = 0, ch;
		if (value.length() == 6 && value.charAt(0) == '['
				&& value.charAt(5) == ']') {
			for (i = 1; i < 5; i++) {
				idCode <<= 8;
				ch = value.charAt(i);
				idCode += ch;
			}
		}
		return idCode;
	}

	/**
	 * 将一个物体数字编号转换为4位字符物体ID
	 * 
	 * @param value 输入的整数
	 * @return 转换出的物体ID，如果编号无法转换为合法ID，返回null
	 */
	public static String int2id(int value) {
		String id = "";
		int i;
		char ch;
		for (i = 0; i < 4; i++) {
			ch = (char) (value % 256);
			if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z')
					|| (ch >= 'a' && ch <= 'z')) {
				id = ch + id;
				value >>= 8;
			} else {
				System.out.println("非法的数字ID: "+value);
				id = "ERROR";
				break;
			}
		}
		if (id != null && id.length()==4) id="["+id+"]";
		return id;
	}
	
}
