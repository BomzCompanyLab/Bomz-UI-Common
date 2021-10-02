package kr.co.bomz.cmn.ui.event;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import kr.co.bomz.cmn.BomzUtils;
import kr.co.bomz.util.text.BomzDateFormat;

/**
 * 	���� ���� ǥ�ÿ� �ڽ��� ���̺�� / ���� / �ο��� / ���� �ð� ������ ǥ�õȴ�
 * 
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TablePaneEvent extends PaneEvent{

	/**		���̺��� �̿����� ��� ��Ÿ��		*/
	private static final String TABLE_STYLE_USE = "tableBoxUse";
	
	/**		���̺��� �̿����� �ƴ� ��� ��Ÿ��		*/
	private static final String TABLE_STYLE_EMPTY = "tableBoxEmpty";
	
	/**		���̺� �մ� ���� �� �ֹ� ������� ��� ��Ÿ��		*/
	private static final String TABLE_STYLE_WAIT = "tableBoxWait";
	
	/**		���̺� ���� �ο��� ���� ��� ��		*/
	public static final int EMPTY_VISITOR_COUNT = -1;
	
	/**		��ü ���� ���̾�		*/
	private BorderPane tablePane;
	
	/**		���̺� �̸�		*/
	private Label tableNameLb;
	
	/**		�ο���		*/
	private Label visitorCountLb;
	
	/**		��ü ����			*/
	private Label totalPriceLb;
		
	/**		���� �ð�		*/
	private Label timeLb;
	
	/**		���� ���̺� ����		*/
	private TableType tableType;
	
	public TablePaneEvent(double screenWidth, double screenHeight, String tableName) throws NullPointerException, ScreenSizeException {
		this(screenWidth, screenHeight, tableName, true);
	}
	
	public TablePaneEvent(double screenWidth, double screenHeight, String tableName, boolean clickEvent) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, clickEvent);
		this.tableNameLb.setText(tableName);
	}
	
	/**		ȭ�鿡 ǥ�õǴ� �̸� ����		*/
	public void setVisitorCount(int visitorCount){
		if( visitorCount == EMPTY_VISITOR_COUNT )
			this.visitorCountLb.setText("");
		else
			this.visitorCountLb.setText(visitorCount + "��");
	}
	
	/**		���� �ð� ����		*/
	public void setTime(java.util.Date time){
		if( time == null ){
			this.timeLb.setText("");
		}else{
			BomzDateFormat format = BomzDateFormat.getInstance("[HH:mm]");
			this.timeLb.setText(format.format(time));
		}
	}
	
	/**		�հ� �ݾ� ����		*/
	public void setTotalPrice(int totalPrice){
		this.totalPriceLb.setText(BomzUtils.toPriceValue(totalPrice));
	}
	
	/**		�հ� �ݾ� ����		*/
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
	
	/**		���� ���̺��� ��������� ����		*/
	public boolean isUseTable(){
		return this.tableType != null && this.tableType == TableType.USE_TABLE;
	}
	
	/**
	 * 	���̺��� ��������� �ƴ����� ���� ��Ÿ���� �����Ѵ�
	 * @param type		���̺� ���� ��
	 */
	public void updateTableStyle(TableType type){
		
		// ����� ������ ���¶�� ���ٸ� ó�� ����
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
		
		// ���� ���·� ����
		this.tableType = type;
		
	}
	
	/**		��Ÿ�� ����		*/
	public void addStyleClass(String styleClass){
		if( styleClass == null || styleClass.isEmpty() )		return;
		this.tablePane.getStyleClass().add(styleClass);
	}
	
	/**		��Ÿ�� ����		*/
	public void removeStyleClass(String styleClass){
		if( styleClass == null || styleClass.isEmpty() )		return;
		this.tablePane.getStyleClass().remove(styleClass);
	}
}
