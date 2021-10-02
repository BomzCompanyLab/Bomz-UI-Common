package example;

import kr.co.bomz.cmn.ui.transfer.TransferSelectItem;

public class Test1Item implements TransferSelectItem<Integer>{

	private int itemId;
	
	private String itemName;
	
	public Test1Item(){}
	
	public Test1Item(int id, String name){
		this.itemId = id;
		this.itemName = name;
	}
	
	@Override
	public Integer getItemId() {
		return itemId;
	}

	@Override
	public String getItemName() {
		return itemName;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
