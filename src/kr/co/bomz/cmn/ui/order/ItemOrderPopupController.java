package kr.co.bomz.cmn.ui.order;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import kr.co.bomz.cmn.Commons;
import kr.co.bomz.cmn.db.ItemVO;
import kr.co.bomz.cmn.db.OrderDatabaseService;
import kr.co.bomz.cmn.module.CommonsController;
import kr.co.bomz.cmn.ui.scroll.BomzScrollPane;

/**
 * 	������ ���� ���� ���� �˾��� ���� ��Ʈ�ѷ�
 * 	
 * 	���ҽ��� ITEM_CONTROLLER_ID Ű ������ kr.co.bomz.cmn.ui.order.ItemOrderController �� �߰��Ѵ�
 * 	���ҽ��� ITEM_LIST_ID Ű ������ ȭ�鿡 ǥ�� ���� ������ ����� �߰��Ѵ�
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ItemOrderPopupController implements Initializable{

	public static final URL fxml = ItemOrderPopupController.class.getResource("ItemOrderPopup.fxml");
	
	/**		ȭ��� ǥ�� ���� ������ ��� ���̵�			*/
	public static final String ITEM_LIST_ID = "ID_ITEM_LIST";
	
	/**
	 * 		������ ���� ����� ������ ��� ���̺� ����
	 * 		new String[]{���̺��, ���̵� �÷�, ���� �� �÷�}
	 */
	public static final String ITEM_DB_ID = "ID_ITEM_DB";
	
	/**
	 * 		OrderDatabaseService �� ������ ��� ó�� ���� ����
	 */
	public static final String DB_SERVICE_ID = "ID_DB_SERVICE";
	
	@FXML
	private AnchorPane itemOrderPn;
	
	@FXML
	private BomzScrollPane itemOrderScrollPn;
	
	@FXML
	private Button submitBt;
	
	@FXML
	private Button cancelBt;
	
	/**		�˾� ǥ�ÿ� ��������		*/
	private Stage popupStage;
	
	/**		�˾� ��ü		*/
	private Popup popup;
	
	/**
	 * 		Resources �� ���� ITEM_DB_ID Ű ������ �Ѿ�� ���
	 * 		[0]	:	���̺��
	 * 		[1]	:	���̵� �÷���
	 * 		[2]	:	���� �� �÷��� 
	 */
	private String[] dbTableInfo;
	
	/**		Resources �� ���� DB_SERVICE_ID Ű ������ �Ѿ�� ���		*/
	private OrderDatabaseService orderDatabaseService;
	
	/**		������ ���		*/
	private ObservableList<Node> viewNodeList;
	
	public ItemOrderPopupController(){}
	
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Stage stage = new Stage();
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setAlwaysOnTop(true);
		
		this.popupStage = (Stage)resources.getObject(CommonsController.POPUP_STAGE_ID);
		this.popup = (Popup)resources.getObject(CommonsController.POPUP_ID);
	
		this.dbTableInfo = (String[])resources.getObject(ITEM_DB_ID);
		this.orderDatabaseService = (OrderDatabaseService)resources.getObject(DB_SERVICE_ID);
		this.viewNodeList = (ObservableList<Node>)resources.getObject(ITEM_LIST_ID);
		
		
		this.itemOrderScrollPn.setWidth(this.itemOrderPn.widthProperty());
		this.itemOrderScrollPn.setHeight(this.itemOrderPn.heightProperty());
		
		// ȭ�鿡 ǥ�õǾ��ִ� ������ ����ȭ
		this.itemOrderScrollPn.makeContentItem(this.viewNodeList, this.itemOrderScrollPn);
	}
		
	/**		���� ���� ���� ���� �ø���		*/
	@FXML
	public void handleOrderUpAction(ActionEvent event){
		this.updateItemOrder(true);
	}

	/**		���� ���� ���� �Ʒ��� ������		*/
	@FXML
	public void handleOrderDownAction(ActionEvent event){
		this.updateItemOrder(false);
	}
	
	private void updateItemOrder(boolean isUp){
		int index = this.itemOrderScrollPn.getSelectContentItemIndex();
		
		// ��Ͽ� ������ ���õǾ� ���� ���� ���
		if( index == -1 )		return;
		
		// ù��° �������� ���� �ø������Ѵٸ� ������ ó�� ����
		if( isUp && index == 0 )		return;
		
		int size = this.viewNodeList.size();
		
		// ������ �������� �Ʒ��� ���������Ѵٸ� ������ ó�� ����
		if( !isUp && (index+1) == size )		return;
		
		Node scrollNode = this.itemOrderScrollPn.removeSelectContent(true);
		if( scrollNode == null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("������ ���� ó�� �� ���õ� ������ �׸� ���� �� null ����");
			return;
		}
		
		Node node = this.viewNodeList.remove(index);
		
		
		if( isUp )		index--;		// ���� �ø� ���
		else				index++;		// �Ʒ��� ���� ���
				
		this.viewNodeList.add(index, node);
		this.itemOrderScrollPn.add(index, scrollNode);
		
	}
	
	/**	�߰� ��ư Ŭ�� �̺�Ʈ		*/
	@FXML
	public void handleSubmitAction(ActionEvent event){
		
		SqlSession session = null;
		
		try {
		
			session = this.orderDatabaseService.openSession();
			
			ItemVO item = new ItemVO();
			item.setTableName(this.dbTableInfo[0]);				// ���̺��
			item.setItemIdColumn(this.dbTableInfo[1]);		// ������ ���̵� �÷���
			item.setItemOrderColumn(this.dbTableInfo[2]);	// ������ ���̵� �÷���
			
			int order = 1;
			ItemOrder vo;
			for(Node node : this.itemOrderScrollPn.getContentList()){
				vo = (ItemOrder)node.getUserData();
				vo.setItemOrder(order++);
				item.setItem(vo);

				// ���� ������� ��� �� ����
				this.orderDatabaseService.updateItemOrder(session, item);	
			}
			
			
			// �˾� �ݱ�
			this.closePopup();
			
			session.commit();
			
		}catch(Exception e) {
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("������ ���� ���� ������ ���� ������ ���� ���� �� ����", e);
			
			if( session != null )		session.rollback();
		}finally{
			if( session != null )		session.close();
		}
	
	}
			
	/**	ȭ�鿡 �������� �ִ� �˾� �ݱ�		*/
	private void closePopup(){
		if( this.popup != null )				this.popup.hide();
		if( this.popupStage != null )		this.popupStage.close();
		
		
		this.popup = null;
		this.popupStage = null;
	}
	
}
