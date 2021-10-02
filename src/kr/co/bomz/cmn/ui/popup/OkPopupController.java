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
 * 	OK ��ư�� �����ϴ� �˾� ��Ʈ�ѷ�
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.0
 *
 */
public class OkPopupController implements Initializable{

	public static final URL fxml = OkPopupController.class.getResource("OkPopup.fxml");
	
	public static final String POPUP_ID = "id_ok_pop";
	
	/**		�˾� ���� ���ҽ� ���̵�			*/
	public static final String POPUP_CONTENT_ID = "id_ok_pop_con";
	
	@FXML
	private Label contentLb;
	
	@FXML
	private Button okBt;
	
	private Stage stage;

	private Popup popup;
	
	/**		'Ȯ��' ��ư Ŭ�� �� �߻��� �̺�Ʈ �ڵ鷯		*/
	private EventHandler<ActionEvent> okEventHandler;
	
	public OkPopupController(){}
	
	@FXML
	public void handleOkAction(ActionEvent event){
		
		try{		if( this.popup != null )		this.popup.hide();		}catch(Throwable e){}		
		try{		if( this.stage != null )		this.stage.close();		}catch(Throwable e){}

		this.popup = null;
		this.stage = null;
		
		if( this.okEventHandler != null )		// �ڵ鷯�� �����Ǿ� ���� ��츸 ����
			this.okEventHandler.handle(event);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL url, ResourceBundle resource) {
		this.stage = (Stage)resource.getObject(ControllerStage.RESOURCE_ID);
		this.popup = (Popup)resource.getObject(POPUP_ID);
		this.contentLb.setText( (String)resource.getObject(POPUP_CONTENT_ID) );
		
		// ������ Ȯ�ι�ư ��Ÿ���� ���� ��� ó��
		try{
			this.okBt.setStyle( (String)resource.getObject(PopupProperties.POPUP_BT_STY_ID) );
		}catch(java.util.MissingResourceException e){}
		
		
		// Ȯ�ι�ư Ŭ�� �� ȣ���� �̺�Ʈ�� ���� ��� ó��
		try{
			this.okEventHandler = (EventHandler<ActionEvent>) resource.getObject(PopupProperties.POPUP_OK_EVENT_HANDLER_ID);
		}catch(java.util.MissingResourceException e){}
	}
	
}