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
 * 	��/�ƴϿ� ��ư�� �����ϴ� �˾� ��Ʈ�ѷ�
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ConfirmPopupController implements Initializable{

	public static final URL fxml = ConfirmPopupController.class.getResource("ConfirmPopup.fxml");
	
	public static final String POPUP_ID = "id_confirm_pop";
	
	/**		�˾� ���� ���ҽ� ���̵�			*/
	public static final String POPUP_CONTENT_ID = "id_confirm_pop_con";
	
	@FXML
	private Label contentLb;
	
	private Stage stage;

	private Popup popup;
	
	/**		'��' ��ư Ŭ�� �� �߻��� �̺�Ʈ �ڵ鷯		*/
	private EventHandler<ActionEvent> okEventHandler;
	
	/**		'�ƴϿ�' ��ư Ŭ�� �� �߻��� �̺�Ʈ �ڵ鷯		*/
	private EventHandler<ActionEvent> cancelEventHandler;
	
	public ConfirmPopupController(){}
	
	@FXML
	public void handleOkAction(ActionEvent event){
		this.closePopup();
		
		if( this.okEventHandler != null )		// �ڵ鷯�� �����Ǿ� ���� ��츸 ����
			this.okEventHandler.handle(event);
	}

	@FXML
	public void handleCancelAction(ActionEvent event){
		this.closePopup();
		
		if( this.cancelEventHandler != null )		// �ڵ鷯�� �����Ǿ� ���� ��츸 ����
			this.cancelEventHandler.handle(event);
	}
	
	/**		ȭ�� ����		*/
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
