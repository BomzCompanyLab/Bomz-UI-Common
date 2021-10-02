package kr.co.bomz.cmn.db;

import kr.co.bomz.cmn.ui.order.ItemOrder;

/**
 * 		아이템 정렬 디비 처리를 위한 객체
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ItemVO {

	/**		테이블 명		*/
	private String tableName;
	
	/**		아이템 아이디 컬럼명			*/
	private String itemIdColumn;
	
	/**		아이템 정렬 순서 컬럼명		*/
	private String itemOrderColumn;
	
	/**		아이템 정렬 결과		*/
	private ItemOrder item;
	
	public ItemVO(){}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getItemIdColumn() {
		return itemIdColumn;
	}

	public void setItemIdColumn(String itemIdColumn) {
		this.itemIdColumn = itemIdColumn;
	}

	public String getItemOrderColumn() {
		return itemOrderColumn;
	}

	public void setItemOrderColumn(String itemOrderColumn) {
		this.itemOrderColumn = itemOrderColumn;
	}

	public ItemOrder getItem() {
		return item;
	}

	public void setItem(ItemOrder item) {
		this.item = item;
	}
	
}
