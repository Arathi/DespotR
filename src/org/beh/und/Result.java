package org.beh.und;

/**
 * <b>�����</b>
 * <br/>
 * ��װһ�������ִ�н��
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
	int value; //���������˺�����Ч���غ�����
	boolean critFlag; //����һ��
	boolean jinkFlag; //����
	boolean success; //����ִ�н��Ϊ�ɹ�
	
	public Result(){
		
	}
	
	public String toString(){
		String result="";
		if (type==RESULT_TYPE_DAMAGE){
			result+=src.getName()+"�ܵ�"+value+"���˺�";
		}
		if (type==RESULT_TYPE_HEAL){
			result+=src.getName()+"�õ�"+value+"������";
		}
		//return super.toString();
		return result;
	}
}
