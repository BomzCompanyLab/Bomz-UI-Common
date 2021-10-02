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
 * 	간략 내용과 예/아니요 버튼이 있는 팝업창
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ConfirmPopup {
	
	private final Window ownerWindow;
	
	private final String message;
	
	public ConfirmPopup(String message){
		this(null, message);
	}
	
	public ConfirmPopup(Window ownerWindow, String message){
		this.ownerWindow = ownerWindow;
		this.message = message;
	}
		
	/**		예/아니요 버튼이 있는 팝업 창 생성		*/
	public void show(){
		this.show(null, null);
	}
	
	/**
	 * 		예/아니요 버튼이 있는 팝업 창 생성
	 * 
	 * @param okEventHandler					'예' 버튼 클릭 시 호출될 이벤트
	 * @param cancelEventHandler			'아니요' 버튼 클릭 시 호출될 이벤트
	 */
	public void show(EventHandler<ActionEvent> okEventHandler, EventHandler<ActionEvent> cancelEventHandler){
		
		Stage stage = new Stage(StageStyle.TRANSPARENT);
		stage.setAlwaysOnTop(true);
		stage.centerOnScreen();
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setResizable(false);
		stage.initOwner(this.ownerWindow);
		stage.setTitle("알림");
		
		Popup popup = new Popup();
		
		BomzResource resource = new BomzResource();
		resource.addResource(ControllerStage.RESOURCE_ID, stage);
		resource.addResource(ConfirmPopupController.POPUP_ID, popup);
		resource.addResource(ConfirmPopupController.POPUP_CONTENT_ID, this.message);
		
		if( okEventHandler != null )		
			resource.addResource(PopupProperties.POPUP_OK_EVENT_HANDLER_ID, okEventHandler);
		
		if( cancelEventHandler != null )		
			resource.addResource(PopupProperties.POPUP_CANCEL_EVENT_HANDLER_ID, cancelEventHandler);
		
		
		try {
			Parent parent = FXMLLoader.load(ConfirmPopupController.fxml, resource);
			
			popup.getContent().add(parent);
			
			stage.show();
			popup.show(stage);
			
		} catch (IOException e) {
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("ConfirmPopup 생성 중 오류", e);
		}
		
		
	}
	
}
