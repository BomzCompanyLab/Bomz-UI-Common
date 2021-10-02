package kr.co.bomz.cmn.db;

import kr.co.bomz.cmn.ui.order.ItemOrder;

/**
 * 		������ ���� ��� ó���� ���� ��ü
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ItemVO {

	/**		���̺� ��		*/
	private String tableName;
	
	/**		������ ���̵� �÷���			*/
	private String itemIdColumn;
	
	/**		������ ���� ���� �÷���		*/
	private String itemOrderColumn;
	
	/**		������ ���� ���		*/
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
