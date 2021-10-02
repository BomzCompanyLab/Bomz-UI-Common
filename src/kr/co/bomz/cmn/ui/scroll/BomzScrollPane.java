package kr.co.bomz.cmn.ui.scroll;

import java.util.List;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import kr.co.bomz.cmn.ui.order.ItemOrder;

/**
 * 	스크롤바 구현을 위해 위/아래 버튼이 있는 화면 구성용
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzScrollPane extends BorderPane{

	/**		위/아래 이동 버튼 넓이		*/
	public static final int MOVE_BUTTON_WIDTH = 50;
	
	/**		버튼 형식의 컨텐츠 스타일 클래스		*/
	private final String CONTENT_ITEM_STYLE_CLASS = "contentItemBt";
	/**		버튼 형식의 컨텐츠 항목의 기본 CSS			*/
	private final String CONTENT_ITEM_DEFAULT_CSS = "-fx-border-radius:5;-fx-background-radius:5;-fx-background-color:#FFFFFF;";
	/**		버튼 형식의 컨텐츠 항목이 선택되었을 경우 CSS			*/
	private final String CONTENT_ITEM_SELECT_CSS = "-fx-border-radius:5;-fx-background-radius:5;-fx-background-color:#AD7D80";
	
	/**		컨텐츠 중 선택되어 있는 항목		*/
	private Node selectContent;
	
	/**		위로 버튼		*/
	private Button topBt = new Button("△");
	
	/**		아래로 버튼		*/
	private Button bottomBt = new Button("▽");
	
	/**		중앙 아이템 스크롤		*/
	private ScrollPane centerScroll = new ScrollPane();
	
	/**		중앙 화면 등록용		*/
	private Pane content;
	
	/**		중앙 화면 리스트		*/
	private ObservableList<Node> contentList;
	
	public BomzScrollPane(){
		this.init();
	}
	
	/**		화면 구성 초기화		*/
	private void init(){
		super.getStylesheets().add( BomzScrollPane.class.getResource("BomzScrollPane.css").toExternalForm() );
		
		this.initButton();
		this.initCenter();
		
	}
	
	/**		중앙 아이템 목록 화면 초기화		*/
	private void initCenter(){
		this.centerScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.centerScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		super.setCenter(this.centerScroll);
	}
	
	/**		버튼 초기화		*/
	private void initButton(){
		double height = super.getPrefHeight() / 2;
		this.topBt.setPrefHeight(height);
		this.bottomBt.setPrefHeight(height);
		
		String style = "upDownMoveBt";
		this.topBt.getStyleClass().add(style);
		this.bottomBt.getStyleClass().add(style);
		
		this.topBt.setOnAction(e->{				this.handleScrollMoveEvent(true);		});
		this.bottomBt.setOnAction(e->{		this.handleScrollMoveEvent(false);		});
		
		VBox box = new VBox();
		box.getChildren().addAll(this.topBt, this.bottomBt);
	
		super.setRight(box);
	}
	
	/**		위아래 이동 버튼 클릭 시 처리		*/
	private void handleScrollMoveEvent(boolean isUp){
		
		double viewHeight = this.centerScroll.getHeight();
		double totalHeight = this.content != null ? this.content.getHeight() : 0;
		
		int block = (int)(totalHeight / viewHeight) + 
								(totalHeight % viewHeight == 0 ? -1 : 0);
		
		double position = this.centerScroll.getVvalue();
		
		if( isUp ){
			position -= 1.0 / block;
			this.centerScroll.setVvalue(position < 0.0 ? 0.0 : position);
		}else{
			position += 1.0 / block;
			this.centerScroll.setVvalue(position > 1.0 ? 1.0 : position);
		}
		
	}
	
	/**		전체 높이 설정		*/
	public void setHeight(ReadOnlyDoubleProperty heightProperty){
		super.prefHeightProperty().bind(heightProperty);

		this.topBt.prefHeightProperty().bind(heightProperty.divide(2));
		this.bottomBt.prefHeightProperty().bind(heightProperty.divide(2));
	}
	
	/**		전체 넓이 설정		*/
	public void setWidth(ReadOnlyDoubleProperty widthProperty){
		super.prefWidthProperty().bind(widthProperty);
	}
	
	/**		아이템 삭제		*/
	public void clear(){
		if( this.contentList != null )		this.contentList.clear();
		this.centerScroll.setVvalue(0);		// 스크롤 위치 초기화
		this.selectContent = null;				// 선택되어 있는 항목 삭제
	}

	/**		아이템 한개 추가		*/
	public void add(Node node){
		if( this.contentList != null )		this.contentList.add(node);
	}
	
	/**		아이템 한개 추가		*/
	public void add(int index, Node node){
		if( this.contentList != null )		this.contentList.add(index, node);
	}
	
	/**		아이템 여러개 추가		*/
	public void addAll(Node ... node){
		if( this.contentList != null )		this.contentList.addAll(node);
	}
	
	/**		아이템 삭제. 삭제 성공 시 true 리턴		*/
	public boolean remove(Node node){
		if( this.contentList != null ){
			// 삭제하려는 항목이 선택된 항목과 동일할 경우 선택된 항목을 메모리에서 삭제
			if( this.selectContent != null && node == this.selectContent )		this.selectContent = null;
			
			return this.contentList.remove(node);		// 목록 정보에서 삭제
		}else{
			return false;
		}
	}
	
	/**		선택되어 있는 컨텐츠 항목 삭제 후 항목 리턴		*/
	public Node removeSelectContent(boolean keepSelectContent){
		if( this.selectContent == null )		return null;
		
		if( this.contentList.remove(this.selectContent) ){
			Node result = this.selectContent;
			
			// 선택된 컨텐츠 항목 유지가 아닐 경우
			if( !keepSelectContent )		this.selectContent = null;
			
			return result;
		}else{
			return null;
		}
		
	}
	
	public Pane getContent() {
		return content;
	}
	
	public void setContent(Pane content) {
		this.content = content;
		this.centerScroll.setContent(content);
		this.contentList = this.content == null ? null : this.content.getChildren();
		this.selectContent = null;
		
		this.content.prefWidthProperty().bind(super.widthProperty().subtract(50));
	}
	
	public ObservableList<Node> getContentList(){
		return this.contentList;
	}
	
	/**		
	 * 		컨텐츠 목록을 추가시킨다
	 * 
	 * @param	list	생성할 목록
	 * @param	rootPane	BomzScrollPane 이 포함된 상위 레이어
	 */
	public final void makeContentItem(List<?> list, Pane rootPane){
		this.makeContentItem(list, rootPane, null);
	}
	
	/**		
	 * 		컨텐츠 목록을 추가시킨다
	 * 
	 * @param	list	생성할 목록
	 * @param	rootPane	BomzScrollPane 이 포함된 상위 레이어
	 */
	public final void makeContentItem(List<?> list, Pane rootPane, EventHandler<ActionEvent> handler){
		int size = list.size();
		Button[] result = new Button[size];
		
		ReadOnlyDoubleProperty width = rootPane.widthProperty();
		
		Object obj;
		for(int i=0; i < size; i++){
			obj = list.get(i);
			if( !(obj instanceof ItemOrder) )		continue;
			
			result[i] = this.makeContentItem((ItemOrder)obj, width, handler);
		}
		
		this.addAll(result);
	}
	
	/**		
	 * 		컨텐츠 목록을 추가시킨다
	 * 
	 * @param	list	생성할 목록
	 * @param	rootPane	BomzScrollPane 이 포함된 상위 레이어
	 */
	public final void makeContentItem(ObservableList<Node> list, Pane rootPane){
		this.makeContentItem(list, rootPane, null);
	}
	
	/**		
	 * 		컨텐츠 목록을 추가시킨다
	 * 
	 * @param	list	생성할 목록
	 * @param	rootPane	BomzScrollPane 이 포함된 상위 레이어
	 */
	public final void makeContentItem(ObservableList<Node> list, Pane rootPane, EventHandler<ActionEvent> handler){
		
		int size = list.size();
		Button[] result = new Button[size];
		
		ReadOnlyDoubleProperty width = rootPane.widthProperty();
		
		for(int i=0; i < size; i++){
			result[i] = this.makeContentItem((ItemOrder) list.get(i).getUserData(), width, handler);
		}
		
		this.addAll(result);
	}
	
	/**		화면 클릭용 버튼 생성		*/
	private Button makeContentItem(ItemOrder itemOrder, ReadOnlyDoubleProperty width, EventHandler<ActionEvent> handler){
		Button bt = new Button( itemOrder.getItemName() );
		bt.prefWidthProperty().bind(width.subtract(BomzScrollPane.MOVE_BUTTON_WIDTH + 2));
		bt.setUserData(itemOrder);
		
		if( handler == null ){
			bt.setStyle(CONTENT_ITEM_DEFAULT_CSS);
			bt.setOnAction(e->{		
				this.makeContentItemEvent(bt);		// 선택된 아이템 색이 변함
			});
		}else{
			bt.getStyleClass().add(CONTENT_ITEM_STYLE_CLASS);		// 클릭 시 색 변함 CSS 설정
			bt.setOnAction(handler);
		}
		
		return bt;
	}
	
	/**		공통유틸로 생성한 항목이 클릭되었을 경우 클릭한 항목만 활성화		*/
	private void makeContentItemEvent(Button bt){
		if( this.selectContent != null )		// 선택되어 있던 항목이 있다면 기본 상태로 상태 변경
			this.selectContent.setStyle(CONTENT_ITEM_DEFAULT_CSS);
		
		// 신규 항목을 선택된 상태로 변경
		this.selectContent = bt;
		if( this.selectContent != null )
			this.selectContent.setStyle(CONTENT_ITEM_SELECT_CSS);
		
	}
	
	/**
	 * 		선택된 컨텐츠 항목의 위치
	 * @return		선택된 항목이 없을 경우 -1 리턴
	 */
	public int getSelectContentItemIndex(){
		if( this.selectContent == null )		return -1;
		
		return this.contentList.indexOf(this.selectContent);
	}
		
}
