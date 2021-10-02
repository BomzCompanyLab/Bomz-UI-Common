package kr.co.bomz.cmn.db;

/**
 * 	정렬 순서가 있는 테이블의 아이템 정렬 처리
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ItemOrderMapper {

	/**		아이템 정렬 순서 변경		*/
	public void updateItemOrder(ItemVO vo);
}
