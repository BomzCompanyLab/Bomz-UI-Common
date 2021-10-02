package kr.co.bomz.cmn.ui.transfer;

/**
 * 	TransferSelect UI 에 사용하는 아이템은 해당 인터페이스를 구현해야 한다
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.1
 *
 */
public interface TransferSelectItem<T> {

	/**	고유 아이디		*/
	public T getItemId();
	
	/**	화면에 표시될 이름		*/
	public String getItemName();
}
