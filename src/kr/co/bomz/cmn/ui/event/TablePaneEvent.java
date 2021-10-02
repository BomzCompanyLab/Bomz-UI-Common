package kr.co.bomz.cmn.ui.event;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import kr.co.bomz.cmn.BomzUtils;
import kr.co.bomz.util.text.BomzDateFormat;

/**
 * 	매장 상태 표시용 박스로 테이블명 / 가격 / 인원수 / 착석 시간 정보가 표시된다
 * 
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TablePaneEvent extends PaneEvent{

	/**		테이블을 이용중일 경우 스타일		*/
	private static final String TABLE_STYLE_USE = "tableBoxUse";
	
	/**		테이블을 이용중이 아닐 경우 스타일		*/
	private static final String TABLE_STYLE_EMPTY = "tableBoxEmpty";
	
	/**		테이블에 손님 착석 후 주문 대기중일 경우 스타일		*/
	private static final String TABLE_STYLE_WAIT = "tableBoxWait";
	
	/**		테이블에 착석 인원이 없을 경우 값		*/
	public static final int EMPTY_VISITOR_COUNT = -1;
	
	/**		전체 구성 레이어		*/
	private BorderPane tablePane;
	
	/**		테이블 이름		*/
	private Label tableNameLb;
	
	/**		인원수		*/
	private Label visitorCountLb;
	
	/**		전체 가격			*/
	private Label totalPriceLb;
		
	/**		착석 시간		*/
	private Label timeLb;
	
	/**		현재 테이블 상태		*/
	private TableType tableType;
	
	public TablePaneEvent(double screenWidth, double screenHeight, String tableName) throws NullPointerException, ScreenSizeException {
		this(screenWidth, screenHeight, tableName, true);
	}
	
	public TablePaneEvent(double screenWidth, double screenHeight, String tableName, boolean clickEvent) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, clickEvent);
		this.tableNameLb.setText(tableName);
	}
	
	/**		화면에 표시되는 이름 수정		*/
	public void setVisitorCount(int visitorCount){
		if( visitorCount == EMPTY_VISITOR_COUNT )
			this.visitorCountLb.setText("");
		else
			this.visitorCountLb.setText(visitorCount + "명");
	}
	
	/**		착석 시간 설정		*/
	public void setTime(java.util.Date time){
		if( time == null ){
			this.timeLb.setText("");
		}else{
			BomzDateFormat format = BomzDateFormat.getInstance("[HH:mm]");
			this.timeLb.setText(format.format(time));
		}
	}
	
	/**		합계 금액 설정		*/
	public void setTotalPrice(int totalPrice){
		this.totalPriceLb.setText(BomzUtils.toPriceValue(totalPrice));
	}
	
	/**		합계 금액 설정		*/
	public void setZeroTotalPrice(){
		this.totalPriceLb.setText("");
	}
	
	@Override
	protected void initSubPane() {
		this.tableNameLb = new Label();
		this.tableNameLb.getStyleClass().addAll("cmnLb");
		
		this.visitorCountLb = new Label();
		this.visitorCountLb.getStyleClass().add("cmnLb");

		this.totalPriceLb = new Label();
		this.totalPriceLb.getStyleClass().add("priceLb");
		
		this.timeLb = new Label();
		this.timeLb.getStyleClass().add("cmnLb");
		
		this.tablePane = new BorderPane();
		this.tablePane.getStyleClass().add("tableBox");
		
		AnchorPane topPn = new AnchorPane();
		AnchorPane.setLeftAnchor(this.tableNameLb, 5.0);
		topPn.getChildren().add(this.tableNameLb);
		
		this.tablePane.setTop(topPn);
		this.tablePane.setCenter(this.totalPriceLb);

		AnchorPane bottomPn = new AnchorPane();
		AnchorPane.setLeftAnchor(this.visitorCountLb, 5.0);
		AnchorPane.setRightAnchor(this.timeLb, 5.0);
		bottomPn.getChildren().addAll(this.visitorCountLb, this.timeLb);
		
		this.tablePane.setBottom(bottomPn);
		
		super.rootPane.getChildren().add(this.tablePane);
	}
	
	/**		현재 테이블이 사용중인지 여부		*/
	public boolean isUseTable(){
		return this.tableType != null && this.tableType == TableType.USE_TABLE;
	}
	
	/**
	 * 	테이블이 사용중인지 아닌지에 따라 스타일을 변경한다
	 * @param type		테이블 상태 값
	 */
	public void updateTableStyle(TableType type){
		
		// 현재와 동일한 상태라면 별다른 처리 없음
		if( this.tableType != null && this.tableType == type )		return;
		
		ObservableList<String> cssList = this.tablePane.getStyleClass();
		
		switch( type ){
		case EMPTY_TABLE :
			cssList.remove(TABLE_STYLE_USE);
			cssList.remove(TABLE_STYLE_WAIT);
			cssList.add(TABLE_STYLE_EMPTY);
			break;
			
		case USE_TABLE :
			cssList.remove(TABLE_STYLE_EMPTY);
			cssList.remove(TABLE_STYLE_WAIT);
			cssList.add(TABLE_STYLE_USE);
			break;
			
		case WAIT_TABLE :
			cssList.remove(TABLE_STYLE_EMPTY);
			cssList.remove(TABLE_STYLE_USE);
			cssList.add(TABLE_STYLE_WAIT);
			break;
		}
		
		// 현재 상태로 설정
		this.tableType = type;
		
	}
	
	/**		스타일 지정		*/
	public void addStyleClass(String styleClass){
		if( styleClass == null || styleClass.isEmpty() )		return;
		this.tablePane.getStyleClass().add(styleClass);
	}
	
	/**		스타일 삭제		*/
	public void removeStyleClass(String styleClass){
		if( styleClass == null || styleClass.isEmpty() )		return;
		this.tablePane.getStyleClass().remove(styleClass);
	}
}
