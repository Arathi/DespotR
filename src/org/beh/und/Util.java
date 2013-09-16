package org.beh.und;

import java.awt.peer.SystemTrayPeer;
import java.util.Random;

/**
 * <b>���ù�����</b>
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
	 * ��ȡ�����ڵ����ʵ��
	 * @param min ��Сȡֵ
	 * @param max ���ȡֵ
	 * @return ����min��max֮���ʵ��
	 */
	public static double getRandomReal(double min, double max){
		double delta=max-min;
		double randomReal = min+delta*random.nextDouble();
		//System.out.println("��ȡ���ʵ����"+randomReal);
		return randomReal;
	}
	
	/**
	 * ��ȡ�����ڵ��������
	 * @param min ��Сȡֵ
	 * @param max ���ȡֵ
	 * @return ����min��max֮�������
	 */
	public static int getRandomInt(int min, int max){
		int delta=max-min;
		int randomInt=min+random.nextInt(delta);
		//System.out.println("��ȡ���������"+randomInt);
		return randomInt;
	}
	
	/**
	 * ���ַ���ת��Ϊ����
	 * @param value �����ַ���
	 * @return ת����������
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
