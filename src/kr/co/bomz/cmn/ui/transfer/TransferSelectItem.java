package kr.co.bomz.cmn.ui.transfer;

/**
 * 	TransferSelect UI �� ����ϴ� �������� �ش� �������̽��� �����ؾ� �Ѵ�
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.1
 *
 */
public interface TransferSelectItem<T> {

	/**	���� ���̵�		*/
	public T getItemId();
	
	/**	ȭ�鿡 ǥ�õ� �̸�		*/
	public String getItemName();
}
