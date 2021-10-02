package kr.co.bomz.cmn.ui.popup;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import kr.co.bomz.cmn.module.ControllerStage;

/**
 * 	OK 버튼만 존재하는 팝업 컨트롤러
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.0
 *
 */
public class OkPopupController implements Initializable{

	public static final URL fxml = OkPopupController.class.getResource("OkPopup.fxml");
	
	public static final String POPUP_ID = "id_ok_pop";
	
	/**		팝업 내용 리소스 아이디			*/
	public static final String POPUP_CONTENT_ID = "id_ok_pop_con";
	
	@FXML
	private Label contentLb;
	
	@FXML
	private Button okBt;
	
	private Stage stage;

	private Popup popup;
	
	/**		'확인' 버튼 클릭 시 발생할 이벤트 핸들러		*/
	private EventHandler<ActionEvent> okEventHandler;
	
	public OkPopupController(){}
	
	@FXML
	public void handleOkAction(ActionEvent event){
		
		try{		if( this.popup != null )		this.popup.hide();		}catch(Throwable e){}		
		try{		if( this.stage != null )		this.stage.close();		}catch(Throwable e){}

		this.popup = null;
		this.stage = null;
		
		if( this.okEventHandler != null )		// 핸들러가 설정되어 있을 경우만 수행
			this.okEventHandler.handle(event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		this.stage = (Stage)resource.getObject(ControllerStage.RESOURCE_ID);
		this.popup = (Popup)resource.getObject(POPUP_ID);
		this.contentLb.setText( (String)resource.getObject(POPUP_CONTENT_ID) );
		
		// 별도의 확인버튼 스타일이 있을 경우 처리
		try{
			this.okBt.setStyle( (String)resource.getObject(PopupProperties.POPUP_BT_STY_ID) );
		}catch(java.util.MissingResourceException e){}
		
		
		// 확인버튼 클릭 시 호출할 이벤트가 있을 경우 처리
		try{
			this.okEventHandler = (EventHandler<ActionEvent>) resource.getObject(PopupProperties.POPUP_OK_EVENT_HANDLER_ID);
		}catch(java.util.MissingResourceException e){}
	}
	
}
