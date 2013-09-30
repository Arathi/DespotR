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
	
	//public static final int 
	
	Unit src;
	Unit target;
	int type;
	int value; //���������˺�����Ч���غ�����
	boolean critFlag; //����һ��
	boolean jinkFlag; //����
	boolean deathFlag; //����
	boolean success; //����ִ�н��Ϊ�ɹ�
	//boolean sleepFlag; //˯�߱�־
	//boolean scienceFlag; //��Ĭ��־
	
	String message; // ����ʱ����Ϣ
	
	protected Result(Unit target, int type){
		this.target=target;
		this.type=type;
	}
	
	public String toString(){
		String result="";
		String targetName=target.getName();
		if (type==RESULT_TYPE_DAMAGE){
			if (jinkFlag){
				result+="����"+targetName+"�㿪��";
			}
			else{
				if (critFlag) result+="���ĵ�һ����\n";
				if (value>0){
					result+=targetName+"�ܵ�"+value+"���˺�";
				}
				else{
					result+="����"+targetName+"��������";
				}
			}
		}
		if (type==RESULT_TYPE_HEAL){
			if (value>0){
				result+=targetName+"�õ�"+value+"������";
			}
			else{
				result+="���ǣ�����ûʲôЧ��";
			}
		}
		if (type==RESULT_TYPE_BUFF){
			result+=targetName;
			if (value==SpellSystem.BUFFID_FIXED_SLEEP){
				result+="˯����...";
			}
			else if (value==SpellSystem.BUFFID_FIXED_SLIENCE){
				result+="��Ĭ��!";
			}
		}
		if (type==RESULT_TYPE_NONE){
			result+=message;
		}
		return result;
	}
}
