package kr.co.bomz.cmn.ui.order;

/**
 * 	정렬하기를 원하는 아이템 정보
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ItemOrder {

	/**		아이템 이름 리턴				*/
	public String getItemName();
	
	/**		아이템 아이디 리턴			*/
	public int getItemId();
	
	/**		아이템 정렬 순서 리턴		*/
	public int getItemOrder();
	
	/**		아이템의 정렬 순서 설정		*/
	public void setItemOrder(int order);
}
