package org.beh.und;

import java.util.Random;

import org.beh.und.xml.*;

/**
 * <b>���ù�����</b>
 * 
 * @author Arathi
 */
public class Util {
	public static Random random;// =new Random();
	protected static XMLHandler xmlHandler;

	static {
		// ��ʼ����������������������������Ϊ��ǰʱ��
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
	 * ��ȡ�����ڵ����ʵ��
	 * 
	 * @param min
	 *            ��Сȡֵ
	 * @param max
	 *            ���ȡֵ
	 * @return ����min��max֮���ʵ��
	 */
	public static double getRandomReal(double min, double max) {
		double delta = max - min;
		double randomReal = min + delta * random.nextDouble();
		// System.out.println("��ȡ���ʵ����"+randomReal);
		return randomReal;
	}

	/**
	 * ��ȡ�����ڵ��������
	 * 
	 * @param min
	 *            ��Сȡֵ
	 * @param max
	 *            ���ȡֵ
	 * @return ����min��max֮�������
	 */
	public static int getRandomInt(int min, int max) {
		int delta = max - min;
		int randomInt = min + random.nextInt(delta);
		// System.out.println("��ȡ���������"+randomInt);
		return randomInt;
	}

	/**
	 * ���ַ���ת��Ϊ����
	 * 
	 * @param value
	 *            �����ַ���
	 * @return ת���������������value�ǷǷ�ֵ�����Ϊ0
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
	 * ��һ��4λ�ַ�����IDת��Ϊ���ֱ��
	 * 
	 * @param value
	 *            ��ʽΪ\[[0-9A-Za-z]{4}\]
	 * @return ������ID��ʽ�Ϸ�ʱ�����ض�Ӧ���ֱ�ţ����򷵻�0
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
	 * ��һ���������ֱ��ת��Ϊ4λ�ַ�����ID
	 * 
	 * @param value ���������
	 * @return ת����������ID���������޷�ת��Ϊ�Ϸ�ID������null
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
				System.out.println("�Ƿ�������ID: "+value);
				id = "ERROR";
				break;
			}
		}
		if (id != null && id.length()==4) id="["+id+"]";
		return id;
	}
	
}
