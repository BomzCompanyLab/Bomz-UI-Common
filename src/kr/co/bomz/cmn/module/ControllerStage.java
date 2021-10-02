package kr.co.bomz.cmn.module;

import javafx.stage.Stage;

/**
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public interface ControllerStage {

	/**		��Ʈ�ѷ� �������� ���ҽ� ���̵�		*/
	public static final String RESOURCE_ID = "CS_KY";
	
	/**		ȭ���� ǥ���� �������� ����		*/
	public void setStage(Stage stage);
	
	/**		ȭ�� ǥ��		*/
	public void show();
	
	/**		
	 * 		ȭ�� ���� �� �� ����
	 * 
	 * @param value		���� ���� ��� null
	 */
	public void close(Object value);
	
}
