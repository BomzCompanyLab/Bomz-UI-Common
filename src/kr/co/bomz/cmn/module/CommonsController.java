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
 * 	��Ʈ�ѷ����� ���������� ����ϴ� ��� �� ������ ������ �⺻ ��Ʈ�ѷ� �߻� Ŭ����
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public abstract class CommonsController {

	/**		��ü ���̵�		*/
	public static final String ID = "ID_PROD_F_CONT";
	
	/**		�˾� ǥ�ø� ���� �˾� �������� ���̵�		*/
	public static final String POPUP_STAGE_ID = "ID_PROD_POP_ST";
	
	/**		�˾� ǥ�ø� ���� �˾� ���̵�				*/
	public static final String POPUP_ID = "ID_PROD_POP";
	
	/**		�˾� ǥ�ø� ���� ���� �Է� ���� 			*/
	public static final String POPUP_BEFORE_VALUE = "ID_PROD_B_VALUE";
	
	protected Stage stage;
	
	public CommonsController(){}
	
	/**		�� �޴��� ���� �˾� ����		*/
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
