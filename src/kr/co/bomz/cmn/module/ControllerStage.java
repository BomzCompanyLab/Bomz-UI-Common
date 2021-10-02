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

	/**		컨트롤러 스테이지 리소스 아이디		*/
	public static final String RESOURCE_ID = "CS_KY";
	
	/**		화면을 표시할 스테이지 설정		*/
	public void setStage(Stage stage);
	
	/**		화면 표시		*/
	public void show();
	
	/**		
	 * 		화면 종료 및 값 설정
	 * 
	 * @param value		값이 없을 경우 null
	 */
	public void close(Object value);
	
}
