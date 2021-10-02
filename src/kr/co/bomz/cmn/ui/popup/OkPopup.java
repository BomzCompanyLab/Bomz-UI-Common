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
 * 	���� ����� OK ��ư�� �ִ� �˾�â
 * @author Bomz
 * @version 1.1
 * @since 1.0
 *
 */
public class OkPopup {
	
	private final Window ownerWindow;
	
	/**		�˾� ����		*/
	private String title = "�˸�";
	
	/**		�˾� ����		*/
	private final String message;
	
	/**		�˾� Ȯ�� ��ư ����		*/
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
			logger.error("OkPopup ���� �� ����", e);
		}
		
	}
	
	/**		�˾� ���� ����		*/
	public void setTitle(String title){
		if( title == null )		return;
		this.title = title;
	}
	
	/**		�˾� Ȯ�� ��ư CSS ��Ÿ�� ����		*/
	public void setOkButtonStyle(String cssStyle){
		this.okButtonCssStyle = cssStyle;
	}
}
