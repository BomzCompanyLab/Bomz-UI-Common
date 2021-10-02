package kr.co.bomz.cmn.ui.order;

/**
 * 	�����ϱ⸦ ���ϴ� ������ ����
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ItemOrder {

	/**		������ �̸� ����				*/
	public String getItemName();
	
	/**		������ ���̵� ����			*/
	public int getItemId();
	
	/**		������ ���� ���� ����		*/
	public int getItemOrder();
	
	/**		�������� ���� ���� ����		*/
	public void setItemOrder(int order);
}
