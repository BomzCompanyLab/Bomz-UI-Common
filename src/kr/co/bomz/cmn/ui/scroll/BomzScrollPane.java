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
 * 	��ũ�ѹ� ������ ���� ��/�Ʒ� ��ư�� �ִ� ȭ�� ������
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzScrollPane extends BorderPane{

	/**		��/�Ʒ� �̵� ��ư ����		*/
	public static final int MOVE_BUTTON_WIDTH = 50;
	
	/**		��ư ������ ������ ��Ÿ�� Ŭ����		*/
	private final String CONTENT_ITEM_STYLE_CLASS = "contentItemBt";
	/**		��ư ������ ������ �׸��� �⺻ CSS			*/
	private final String CONTENT_ITEM_DEFAULT_CSS = "-fx-border-radius:5;-fx-background-radius:5;-fx-background-color:#FFFFFF;";
	/**		��ư ������ ������ �׸��� ���õǾ��� ��� CSS			*/
	private final String CONTENT_ITEM_SELECT_CSS = "-fx-border-radius:5;-fx-background-radius:5;-fx-background-color:#AD7D80";
	
	/**		������ �� ���õǾ� �ִ� �׸�		*/
	private Node selectContent;
	
	/**		���� ��ư		*/
	private Button topBt = new Button("��");
	
	/**		�Ʒ��� ��ư		*/
	private Button bottomBt = new Button("��");
	
	/**		�߾� ������ ��ũ��		*/
	private ScrollPane centerScroll = new ScrollPane();
	
	/**		�߾� ȭ�� ��Ͽ�		*/
	private Pane content;
	
	/**		�߾� ȭ�� ����Ʈ		*/
	private ObservableList<Node> contentList;
	
	public BomzScrollPane(){
		this.init();
	}
	
	/**		ȭ�� ���� �ʱ�ȭ		*/
	private void init(){
		super.getStylesheets().add( BomzScrollPane.class.getResource("BomzScrollPane.css").toExternalForm() );
		
		this.initButton();
		this.initCenter();
		
	}
	
	/**		�߾� ������ ��� ȭ�� �ʱ�ȭ		*/
	private void initCenter(){
		this.centerScroll.setVbarPolicy(ScrollBarPolicy.NEVER);
		this.centerScroll.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		super.setCenter(this.centerScroll);
	}
	
	/**		��ư �ʱ�ȭ		*/
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
	
	/**		���Ʒ� �̵� ��ư Ŭ�� �� ó��		*/
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
	
	/**		��ü ���� ����		*/
	public void setHeight(ReadOnlyDoubleProperty heightProperty){
		super.prefHeightProperty().bind(heightProperty);

		this.topBt.prefHeightProperty().bind(heightProperty.divide(2));
		this.bottomBt.prefHeightProperty().bind(heightProperty.divide(2));
	}
	
	/**		��ü ���� ����		*/
	public void setWidth(ReadOnlyDoubleProperty widthProperty){
		super.prefWidthProperty().bind(widthProperty);
	}
	
	/**		������ ����		*/
	public void clear(){
		if( this.contentList != null )		this.contentList.clear();
		this.centerScroll.setVvalue(0);		// ��ũ�� ��ġ �ʱ�ȭ
		this.selectContent = null;				// ���õǾ� �ִ� �׸� ����
	}

	/**		������ �Ѱ� �߰�		*/
	public void add(Node node){
		if( this.contentList != null )		this.contentList.add(node);
	}
	
	/**		������ �Ѱ� �߰�		*/
	public void add(int index, Node node){
		if( this.contentList != null )		this.contentList.add(index, node);
	}
	
	/**		������ ������ �߰�		*/
	public void addAll(Node ... node){
		if( this.contentList != null )		this.contentList.addAll(node);
	}
	
	/**		������ ����. ���� ���� �� true ����		*/
	public boolean remove(Node node){
		if( this.contentList != null ){
			// �����Ϸ��� �׸��� ���õ� �׸�� ������ ��� ���õ� �׸��� �޸𸮿��� ����
			if( this.selectContent != null && node == this.selectContent )		this.selectContent = null;
			
			return this.contentList.remove(node);		// ��� �������� ����
		}else{
			return false;
		}
	}
	
	/**		���õǾ� �ִ� ������ �׸� ���� �� �׸� ����		*/
	public Node removeSelectContent(boolean keepSelectContent){
		if( this.selectContent == null )		return null;
		
		if( this.contentList.remove(this.selectContent) ){
			Node result = this.selectContent;
			
			// ���õ� ������ �׸� ������ �ƴ� ���
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
	 * 		������ ����� �߰���Ų��
	 * 
	 * @param	list	������ ���
	 * @param	rootPane	BomzScrollPane �� ���Ե� ���� ���̾�
	 */
	public final void makeContentItem(List<?> list, Pane rootPane){
		this.makeContentItem(list, rootPane, null);
	}
	
	/**		
	 * 		������ ����� �߰���Ų��
	 * 
	 * @param	list	������ ���
	 * @param	rootPane	BomzScrollPane �� ���Ե� ���� ���̾�
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
	 * 		������ ����� �߰���Ų��
	 * 
	 * @param	list	������ ���
	 * @param	rootPane	BomzScrollPane �� ���Ե� ���� ���̾�
	 */
	public final void makeContentItem(ObservableList<Node> list, Pane rootPane){
		this.makeContentItem(list, rootPane, null);
	}
	
	/**		
	 * 		������ ����� �߰���Ų��
	 * 
	 * @param	list	������ ���
	 * @param	rootPane	BomzScrollPane �� ���Ե� ���� ���̾�
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
	
	/**		ȭ�� Ŭ���� ��ư ����		*/
	private Button makeContentItem(ItemOrder itemOrder, ReadOnlyDoubleProperty width, EventHandler<ActionEvent> handler){
		Button bt = new Button( itemOrder.getItemName() );
		bt.prefWidthProperty().bind(width.subtract(BomzScrollPane.MOVE_BUTTON_WIDTH + 2));
		bt.setUserData(itemOrder);
		
		if( handler == null ){
			bt.setStyle(CONTENT_ITEM_DEFAULT_CSS);
			bt.setOnAction(e->{		
				this.makeContentItemEvent(bt);		// ���õ� ������ ���� ����
			});
		}else{
			bt.getStyleClass().add(CONTENT_ITEM_STYLE_CLASS);		// Ŭ�� �� �� ���� CSS ����
			bt.setOnAction(handler);
		}
		
		return bt;
	}
	
	/**		������ƿ�� ������ �׸��� Ŭ���Ǿ��� ��� Ŭ���� �׸� Ȱ��ȭ		*/
	private void makeContentItemEvent(Button bt){
		if( this.selectContent != null )		// ���õǾ� �ִ� �׸��� �ִٸ� �⺻ ���·� ���� ����
			this.selectContent.setStyle(CONTENT_ITEM_DEFAULT_CSS);
		
		// �ű� �׸��� ���õ� ���·� ����
		this.selectContent = bt;
		if( this.selectContent != null )
			this.selectContent.setStyle(CONTENT_ITEM_SELECT_CSS);
		
	}
	
	/**
	 * 		���õ� ������ �׸��� ��ġ
	 * @return		���õ� �׸��� ���� ��� -1 ����
	 */
	public int getSelectContentItemIndex(){
		if( this.selectContent == null )		return -1;
		
		return this.contentList.indexOf(this.selectContent);
	}
		
}
