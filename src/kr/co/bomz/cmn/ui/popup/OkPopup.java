package kr.co.bomz.cmn.ui.popup;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.cmn.module.BomzResource;
import kr.co.bomz.cmn.module.ControllerStage;


/**
 * 	간략 내용과 OK 버튼이 있는 팝업창
 * @author Bomz
 * @version 1.1
 * @since 1.0
 *
 */
public class OkPopup {
	
	private final Window ownerWindow;
	
	/**		팝업 제목		*/
	private String title = "알림";
	
	/**		팝업 내용		*/
	private final String message;
	
	/**		팝업 확인 버튼 넓이		*/
	private String okButtonCssStyle;
	
	public OkPopup(String message){
		this(null, message);
	}
	
	public OkPopup(Window ownerWindow, String message){
		this.ownerWindow = ownerWindow;
		this.message = message;
	}

	public void show(){
		this.show(null);
	}
	
	public void show(EventHandler<ActionEvent> okEventHandler){
		
		Stage stage = new Stage(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.centerOnScreen();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(this.ownerWindow);
		stage.setTitle(this.title);
		
		Popup popup = new Popup();
		
		BomzResource resource = new BomzResource();
		resource.addResource(ControllerStage.RESOURCE_ID, stage);
		resource.addResource(OkPopupController.POPUP_ID, popup);
		resource.addResource(OkPopupController.POPUP_CONTENT_ID, this.message);
		
		if( this.okButtonCssStyle != null )		resource.addResource(PopupProperties.POPUP_BT_STY_ID, this.okButtonCssStyle);
		
		if( okEventHandler != null )		
			resource.addResource(PopupProperties.POPUP_OK_EVENT_HANDLER_ID, okEventHandler);
		
		try {
			Parent parent = FXMLLoader.load(OkPopupController.fxml, resource);
			
			popup.getContent().add(parent);
			
			stage.show();
			popup.show(stage);
			
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("OkPopup 생성 중 오류", e);
		}
		
	}
	
	/**		팝업 제목 설정		*/
	public void setTitle(String title){
		if( title == null )		return;
		this.title = title;
	}
	
	/**		팝업 확인 버튼 CSS 스타일 지정		*/
	public void setOkButtonStyle(String cssStyle){
		this.okButtonCssStyle = cssStyle;
	}
}
