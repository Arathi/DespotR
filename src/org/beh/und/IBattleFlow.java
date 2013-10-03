package org.beh.und;

import java.util.*;

/**
 * �μ�ս�����̵Ľӿڣ�������һ����λ(Unit)��
 * Ҳ������һ������(Force)
 * @author Arathi
 *
 */
public interface IBattleFlow {
	/**
	 * ѡȡ����
	 */
	public void selectOrders(List<Order> orderList);
	
	/**
	 * ������̬
	 */
	public void resetPosture();
	
	/**
	 * Buff��ʱ��
	 */
	public void buffsDecrement();
	
	/**
	 * ���ս������
	 * @return true: ս������  false: ���Լ���ս��
	 */
	public boolean isCanNotBattle();
	
	/**
	 * ��ӵ�λ
	 * @param u
	 */
	//public void addUnit(Unit u);
	
	/**
	 * ���õ�λ�������ĵж�
	 * @param units
	 */
	//public void setEnemys(IBattleFlow units);

	public void setBattleEnvForAll(Battle battle);
	
	public boolean isCanNotStopRunning();

	public int getAgiSum();

	public int getAtkSum();
	
	public void setEnemys(IBattleFlow enemys);
	
	public Unit getSingleUnit(int rule);
	public List<Unit> getAllUnits(int rule);
}
