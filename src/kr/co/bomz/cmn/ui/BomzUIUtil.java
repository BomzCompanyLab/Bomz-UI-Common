package kr.co.bomz.cmn.ui;

import javafx.collections.ObservableList;

/**
 * 	UI ȭ��� ���� ��ƿ
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzUIUtil {

	/**
	 * 	��Ÿ�� Ŭ���� ����
	 * @param styleClassList
	 * @param value
	 */
	public void setStyleClass(ObservableList<String> styleClassList, String value){
		if( styleClassList == null )		return;
		if( value == null )					return;
		
		for(String v : value.split("\\,")){
			if( v == null )		continue;
			v = v.trim();
			
			if( v.isEmpty() )	continue;
			styleClassList.add(v);
		}
	}
}
