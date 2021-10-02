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
 * 	아이템 정렬 순서 변경 팝업에 대한 컨트롤러
 * 	
 * 	리소스에 ITEM_CONTROLLER_ID 키 값으로 kr.co.bomz.cmn.ui.order.ItemOrderController 를 추가한다
 * 	리소스에 ITEM_LIST_ID 키 값으로 화면에 표시 중인 아이템 목록을 추가한다
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class ItemOrderPopupController implements Initializable{

	public static final URL fxml = ItemOrderPopupController.class.getResource("ItemOrderPopup.fxml");
	
	/**		화면상에 표시 중인 아이템 목록 아이디			*/
	public static final String ITEM_LIST_ID = "ID_ITEM_LIST";
	
	/**
	 * 		아이템 정렬 결과를 저장할 디비 테이블 정보
	 * 		new String[]{테이블명, 아이디 컬럼, 정렬 값 컬럼}
	 */
	public static final String ITEM_DB_ID = "ID_ITEM_DB";
	
	/**
	 * 		OrderDatabaseService 를 구현한 디비 처리 서비스 정보
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
	
	/**		팝업 표시용 스테이지		*/
	private Stage popupStage;
	
	/**		팝업 객체		*/
	private Popup popup;
	
	/**
	 * 		Resources 를 통해 ITEM_DB_ID 키 값으로 넘어온 결과
	 * 		[0]	:	테이블명
	 * 		[1]	:	아이디 컬럼명
	 * 		[2]	:	정렬 값 컬럼명 
	 */
	private String[] dbTableInfo;
	
	/**		Resources 를 통해 DB_SERVICE_ID 키 값으로 넘어온 결과		*/
	private OrderDatabaseService orderDatabaseService;
	
	/**		아이템 목록		*/
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
		
		// 화면에 표시되어있는 구역과 동기화
		this.itemOrderScrollPn.makeContentItem(this.viewNodeList, this.itemOrderScrollPn);
	}
		
	/**		구역 정렬 순서 위로 올리기		*/
	@FXML
	public void handleOrderUpAction(ActionEvent event){
		this.updateItemOrder(true);
	}

	/**		구역 정렬 순서 아래로 내리기		*/
	@FXML
	public void handleOrderDownAction(ActionEvent event){
		this.updateItemOrder(false);
	}
	
	private void updateItemOrder(boolean isUp){
		int index = this.itemOrderScrollPn.getSelectContentItemIndex();
		
		// 목록에 구역이 선택되어 있지 않을 경우
		if( index == -1 )		return;
		
		// 첫번째 아이템을 위로 올리려고한다면 별도의 처리 없음
		if( isUp && index == 0 )		return;
		
		int size = this.viewNodeList.size();
		
		// 마지막 아이템을 아래로 내리려고한다면 별도의 처리 없음
		if( !isUp && (index+1) == size )		return;
		
		Node scrollNode = this.itemOrderScrollPn.removeSelectContent(true);
		if( scrollNode == null ){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("아이테 정렬 처리 중 선택된 컨텐츠 항목 삭제 후 null 리턴");
			return;
		}
		
		Node node = this.viewNodeList.remove(index);
		
		
		if( isUp )		index--;		// 위로 올릴 경우
		else				index++;		// 아래로 내릴 경우
				
		this.viewNodeList.add(index, node);
		this.itemOrderScrollPn.add(index, scrollNode);
		
	}
	
	/**	추가 버튼 클릭 이벤트		*/
	@FXML
	public void handleSubmitAction(ActionEvent event){
		
		SqlSession session = null;
		
		try {
		
			session = this.orderDatabaseService.openSession();
			
			ItemVO item = new ItemVO();
			item.setTableName(this.dbTableInfo[0]);				// 테이블명
			item.setItemIdColumn(this.dbTableInfo[1]);		// 아이템 아이디 컬럼명
			item.setItemOrderColumn(this.dbTableInfo[2]);	// 아이템 아이디 컬럼명
			
			int order = 1;
			ItemOrder vo;
			for(Node node : this.itemOrderScrollPn.getContentList()){
				vo = (ItemOrder)node.getUserData();
				vo.setItemOrder(order++);
				item.setItem(vo);

				// 정렬 순서대로 디비 값 변경
				this.orderDatabaseService.updateItemOrder(session, item);	
			}
			
			
			// 팝업 닫기
			this.closePopup();
			
			session.commit();
			
		}catch(Exception e) {
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("아이템 정렬 순서 변경을 위한 아이템 정보 변경 중 오류", e);
			
			if( session != null )		session.rollback();
		}finally{
			if( session != null )		session.close();
		}
	
	}
			
	/**	화면에 보여지고 있는 팝업 닫기		*/
	private void closePopup(){
		if( this.popup != null )				this.popup.hide();
		if( this.popupStage != null )		this.popupStage.close();
		
		
		this.popup = null;
		this.popupStage = null;
	}
	
}
