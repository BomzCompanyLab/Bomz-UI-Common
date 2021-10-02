package kr.co.bomz.cmn.ui.popup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import kr.co.bomz.cmn.module.ControllerStage;

/**
 * 	예/아니요 버튼이 존재하는 팝업 컨트롤러
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ConfirmPopupController implements Initializable{

	public static final URL fxml = ConfirmPopupController.class.getResource("ConfirmPopup.fxml");
	
	public static final String POPUP_ID = "id_confirm_pop";
	
	/**		팝업 내용 리소스 아이디			*/
	public static final String POPUP_CONTENT_ID = "id_confirm_pop_con";
	
	@FXML
	private Label contentLb;
	
	private Stage stage;

	private Popup popup;
	
	/**		'예' 버튼 클릭 시 발생할 이벤트 핸들러		*/
	private EventHandler<ActionEvent> okEventHandler;
	
	/**		'아니요' 버튼 클릭 시 발생할 이벤트 핸들러		*/
	private EventHandler<ActionEvent> cancelEventHandler;
	
	public ConfirmPopupController(){}
	
	@FXML
	public void handleOkAction(ActionEvent event){
		this.closePopup();
		
		if( this.okEventHandler != null )		// 핸들러가 설정되어 있을 경우만 수행
			this.okEventHandler.handle(event);
	}

	@FXML
	public void handleCancelAction(ActionEvent event){
		this.closePopup();
		
		if( this.cancelEventHandler != null )		// 핸들러가 설정되어 있을 경우만 수행
			this.cancelEventHandler.handle(event);
	}
	
	/**		화면 종료		*/
	private void closePopup(){
		try{		if( this.popup != null )		this.popup.hide();		}catch(Throwable e){}
		try{		if( this.stage != null )		this.stage.close();		}catch(Throwable e){}

		this.popup = null;
		this.stage = null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		this.stage = (Stage)resource.getObject(ControllerStage.RESOURCE_ID);
		this.popup = (Popup)resource.getObject(POPUP_ID);
		this.contentLb.setText( (String)resource.getObject(POPUP_CONTENT_ID) );
		
		try{
			this.okEventHandler = (EventHandler<ActionEvent>) resource.getObject(PopupProperties.POPUP_OK_EVENT_HANDLER_ID);
		}catch(java.util.MissingResourceException e){}
		
		try{
			this.cancelEventHandler = (EventHandler<ActionEvent>) resource.getObject(PopupProperties.POPUP_CANCEL_EVENT_HANDLER_ID);
		}catch(java.util.MissingResourceException e){}
	}
	
}
