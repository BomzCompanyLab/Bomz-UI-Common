package kr.co.bomz.cmn.module;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import kr.co.bomz.cmn.module.BomzResource;

/**
 * 	컨트롤러에서 공통적으로 사용하는 기능 및 변수를 포함한 기본 컨트롤러 추상 클래스
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class CommonsController {

	/**		객체 아이디		*/
	public static final String ID = "ID_PROD_F_CONT";
	
	/**		팝업 표시를 위한 팝업 스테이지 아이디		*/
	public static final String POPUP_STAGE_ID = "ID_PROD_POP_ST";
	
	/**		팝업 표시를 위한 팝업 아이디				*/
	public static final String POPUP_ID = "ID_PROD_POP";
	
	/**		팝업 표시를 위한 기존 입력 정보 			*/
	public static final String POPUP_BEFORE_VALUE = "ID_PROD_B_VALUE";
	
	protected Stage stage;
	
	public CommonsController(){}
	
	/**		각 메뉴에 대한 팝업 생성		*/
	protected void createPopup(URL fxmlUrl, BomzResource resource) throws IOException{
		Stage popupStage = new Stage(StageStyle.TRANSPARENT);
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.setAlwaysOnTop(true);
		popupStage.initOwner(this.stage);
		
		Popup popup = new Popup();
		popup.centerOnScreen();
		
		if( resource == null )		resource = new BomzResource();
		resource.addResource(ID, this);
		resource.addResource(POPUP_STAGE_ID, popupStage);
		resource.addResource(POPUP_ID, popup);
		
		
		Parent parent = FXMLLoader.load(fxmlUrl, resource);
		
		popupStage.show();
		popup.getContent().add(parent);
		popup.show(popupStage);
	}
	
}
