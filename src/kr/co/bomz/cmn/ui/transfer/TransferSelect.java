package kr.co.bomz.cmn.ui.transfer;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import kr.co.bomz.cmn.ui.BomzUI;
import kr.co.bomz.cmn.ui.BomzUIUtil;

/**
 * 	���� / ���� VBox ������ UI
 * 	������ Ŭ�� �� �ݴ��� VBox �� �̵��ȴ�
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.1
 *
 */
public class TransferSelect<T> extends HBox implements BomzUI{

	/**		��� �̵� ��ư �ڽ� ���� ����		*/
	private final double CENTER_BUTTON_BOX_WIDTH = 80;
	
	private ScrollPane useScrollPane = new ScrollPane();
	private ScrollPane notUseScrollPane = new ScrollPane();
	
	private VBox useBox = new VBox();
	private VBox notUseBox = new VBox();
	
	private Label useBoxTitleLb = new Label("���");
	private Label notUseBoxTitleLb = new Label("�̻��");
	
	private Button moveLeftBt = new Button("��");
	private Button moveRightBt = new Button("��");
	
	/**		���ʿ� ����� ������ ����� ǥ���� ��� true		*/
	private final boolean positionUseItemLeft;
	
	/**		
	 * 		������ �̵� �� ����Ŭ�� ��� ����
	 * 	false �� ��� �ѹ� Ŭ������ �̵�
	 * 	true �� ��� ����Ŭ���� ��� �̵�
	 **/
	private boolean itemMoveDoubleClick = false;
	
	/**		���� ������ ������ �����Ǿ��ٸ� true		*/
	private boolean removeTitle = false;
	
	public TransferSelect(){
		this(true);
	}
	
	public TransferSelect(boolean positionUseItemLeft){
		this.positionUseItemLeft = positionUseItemLeft;
		this.init();
	}
	
	/**		ȭ�� �ʱ�ȭ		*/
	private void init(){
		super.getStyleClass().add("transferSelectBox");
		super.getStylesheets().add( TransferSelect.class.getResource("TransferSelect.css").toExternalForm() );		// css ����
		
		this.initLayout();
		this.initEvent();
	}
	
	/**		�̺�Ʈ ����		*/
	private void initEvent(){
		this.moveLeftBt.setOnAction( (e) -> this.itemMoveEvent( this.positionUseItemLeft ? false : true) );
		this.moveRightBt.setOnAction( (e) -> this.itemMoveEvent( this.positionUseItemLeft ? true : false) );
	}
	
	/**
	 * 	������ ��ü �̵� �̺�Ʈ ó��
	 * @param inToOut		true �� ��� ����Ͽ��� �̻�������� �̵�
	 */
	private void itemMoveEvent(boolean useToNotUse){
		ObservableList<Node> useList = this.useBox.getChildren();
		ObservableList<Node> notUseList = this.notUseBox.getChildren();
		
		if( useToNotUse ){
			// ��뿡�� ���������� �̵��� ���
			notUseList.addAll(useList);
			useList.clear();
		}else{
			// �����Կ��� ������� �̵��� ���
			useList.addAll(notUseList);
			notUseList.clear();
		}
		
	}
	
	/**		ȭ�� ���� ����		*/
	private void initLayout(){
		this.initLayoutScrollPane(this.useScrollPane, this.useBox);
		this.initLayoutScrollPane(this.notUseScrollPane, this.notUseBox);
		
		VBox leftBox = new VBox();
		leftBox.getStyleClass().add("itemBoxPane");
		leftBox.getChildren().addAll(
				this.makeItemTitlePane( this.positionUseItemLeft ? this.useBoxTitleLb : this.notUseBoxTitleLb ), 
				this.positionUseItemLeft ? this.useScrollPane : this.notUseScrollPane
			);
		
		VBox rightBox = new VBox();
		rightBox.getStyleClass().add("itemBoxPane");
		rightBox.getChildren().addAll(
				this.makeItemTitlePane( this.positionUseItemLeft ? this.notUseBoxTitleLb : this.useBoxTitleLb ),
				this.positionUseItemLeft ? this.notUseScrollPane : this.useScrollPane
			);
		
		VBox centerBox = new VBox();
		this.initLayoutMoveButtonPane(centerBox);
		
		super.getChildren().addAll(leftBox, centerBox, rightBox);
	}
	
	/**		������ ���� �ڽ� �߰�		*/
	private FlowPane makeItemTitlePane(Label lb){
		FlowPane pn = new FlowPane();
		pn.setAlignment(Pos.CENTER);
		pn.getChildren().add(lb);
		
		return pn;
	}
	
	/**	ȭ�� �߰� ������ �̵� ��ư �ڽ� �ʱ�ȭ		*/
	private void initLayoutMoveButtonPane(VBox centerBox){
		centerBox.getStyleClass().add("centerMoveButtonBox");
		
		centerBox.setMaxWidth(CENTER_BUTTON_BOX_WIDTH);
		centerBox.setMinWidth(CENTER_BUTTON_BOX_WIDTH);
		centerBox.prefHeightProperty().bind(super.heightProperty());
		centerBox.getChildren().addAll(this.moveLeftBt, this.moveRightBt);
	}
	
	/**	���� �������� ǥ�õǴ� ScrollPane �ʱ�ȭ		*/
	private void initLayoutScrollPane(ScrollPane scrollPn, VBox content){
		content.getStyleClass().add("itemBox");
		content.prefWidthProperty().bind(scrollPn.widthProperty().subtract(24));
		
		scrollPn.setContent(content);
		scrollPn.setVbarPolicy(ScrollBarPolicy.ALWAYS);		// ���Ʒ� ��ũ�ѹ� �׻� ���̱�
		scrollPn.setHbarPolicy(ScrollBarPolicy.NEVER);		// �翷 ��ũ�ѹ� �׻� �����
		scrollPn.prefHeightProperty().bind(super.heightProperty());
		scrollPn.prefWidthProperty().bind(super.widthProperty().divide(2).subtract(CENTER_BUTTON_BOX_WIDTH/2));
	}
	
	/**		����ϴ� �׸��� ���� ��� ����		*/
	public List<TransferSelectItem<T>> getUseItemList(){
		return this.getItemList(this.useBox);
	}
	
	/**		������� �ʴ� �׸��� ���� ��� ����		*/
	public List<TransferSelectItem<T>> getNotUseItemList(){
		return this.getItemList(this.notUseBox);
	}
	
	@SuppressWarnings("unchecked")
	private List<TransferSelectItem<T>> getItemList(VBox box){
		List<TransferSelectItem<T>> list = new ArrayList<TransferSelectItem<T>>();
		
		for(Node node : box.getChildren()){
			list.add( (TransferSelectItem<T>)node.getUserData() );
		}
		
		return list;
	}
	
	/**		����ϴ� �׸� ��Ͽ� ǥ�õ� ���� ����		*/
	public void setUseItemList(List<TransferSelectItem<T>> list){
		this.setItemList(this.useBox, list);
	}
	
	/**		������� �ʴ� �׸� ��Ͽ� ǥ�õ� ���� ����		*/
	public void setNotUseItemList(List<TransferSelectItem<T>> list){
		this.setItemList(this.notUseBox, list);
	}
	
	/**		��� ȭ�� ����		*/
	private void setItemList(VBox box, List<TransferSelectItem<T>> list){
		ObservableList<Node> boxList = box.getChildren();
		
		if( list == null || list.isEmpty() ){
			// ���� �׸��� ����� ���
			boxList.clear();		// ���� ���� ��� ����
		}else{
			// ���� �׸��� ���� ���
			List<Label> lbList = new ArrayList<Label>(list.size());
			for(TransferSelectItem<T> item : list){
				lbList.add( this.makeItemLabel(item, box) );
			}
			
			boxList.clear();				// ���� ���� ��� ����
			boxList.addAll(lbList);	// �ű� ���� �߰�
		}
	}
	
	/**		������ Label ����		*/
	private Label makeItemLabel(TransferSelectItem<T> item, VBox box){
		Label label = new Label(item.getItemName());
		label.getStyleClass().add("itemLb");
		label.setUserData(item);
		label.prefWidthProperty().bind(box.widthProperty());
		
		// Ŭ�� �̺�Ʈ ����
		label.setOnMouseClicked( (e) -> handleItemClickEvent(label, e) );
		return label;
	}
	
	/**		������ Label Ŭ�� �̺�Ʈ ����		*/
	private void handleItemClickEvent(Label lb, MouseEvent e){
		if( this.itemMoveDoubleClick ){
			// ����Ŭ�� �̵� ������ ��� ����Ŭ������ Ȯ���Ѵ�
			if( e.getClickCount() != 2 )		return;		// ����Ŭ���� �ƴ� ��� ó������ �ʴ´�
		}
		
		ObservableList<Node> useList = this.useBox.getChildren();
		if( useList.remove(lb) ){
			// ���� ���� �� ������ �������� ��Ͽ� �߰��Ǿ� �ִ� �������̹Ƿ� ������ ������� �̵�
			this.notUseBox.getChildren().add(0, lb);
			
		}else{
			// ���� ���� �� ������ ������ ��Ͽ� �߰��Ǿ� �ִ� ��������
			ObservableList<Node> notUseList = this.notUseBox.getChildren();
			if( !notUseList.remove(lb) )		return;		// �����Կ��� �߰��Ǿ� ���� ���� ��� ������
			
			useList.add(0, lb);
		}
	}
	
	/**
	 * 	ȭ�鿡 ǥ�õ� ��� ���� ����
	 * 
	 * @param useList			����� ������ ���
	 * @param notUseList		������� �ʴ� ������ ���
	 */
	public void setItemList(List<TransferSelectItem<T>> useList, List<TransferSelectItem<T>> notUseList){
		this.setUseItemList(useList);
		this.setNotUseItemList(notUseList);
	}
	
	/**		���Ʒ� ��ũ�ѹ� ǥ�� ����		*/
	public void setScrollVBarPolicy(ScrollBarPolicy policy){
		this.useScrollPane.setVbarPolicy(policy);
		this.notUseScrollPane.setVbarPolicy(policy);
	}
	
	/**		�翷 ��ũ�ѹ� ǥ�� ����		*/
	public void setScrollHBarPolicy(ScrollBarPolicy policy){
		this.useScrollPane.setHbarPolicy(policy);
		this.notUseScrollPane.setHbarPolicy(policy);
	}
	
	/**		�������� ����Ŭ���Ͽ� �̵�ó���� ��� true ����. �⺻�� false		*/
	public void setItemMoveDoubleClick(boolean click){
		this.itemMoveDoubleClick = click;
	}
	
	/**		 ������ �̵� �� ����Ŭ�� ��� ����		*/
	public boolean isItemMoveDoubleClick(){
		return this.itemMoveDoubleClick;
	}
	
	/**		ȭ�� ǥ�� Ÿ��Ʋ�� ����		*/
	public void setTitleName(String useTitleName, String notUseTitleName){
		this.useBoxTitleLb.setText(useTitleName);
		this.notUseBoxTitleLb.setText(notUseTitleName);
	}
	
	/**		ȭ�鿡 ǥ�õǴ� Ÿ��Ʋ���� �����Ѵ�. ���� �� ������ �� ����		*/
	public synchronized void removeTitle(){
		if( this.removeTitle )			return;		// �̹� ����ó���� ���
		
		this.removeTitle = true;
		
		try{
			((VBox)super.getChildren().get(0)).getChildren().remove(0);
			((VBox)super.getChildren().get(2)).getChildren().remove(0);
		}catch(Exception e){}
	}
	
	/**		ȭ�鿡 ǥ�õǴ� Ÿ��Ʋ ��Ÿ�� ����		*/
	public void setTitleStyle(String style){
		this.useBoxTitleLb.setStyle(style);
		this.notUseBoxTitleLb.setStyle(style);
	}
	
	/**		��ü ���̾� pref-width ����		*/
	public void setTransferSelectPrefWidth(double width){
		super.setPrefWidth(width);
	}
	
	/**		��ü ���̾� min-width ����		*/
	public void setTransferSelectMinWidth(double width){
		super.setMinWidth(width);
	}
	
	/**		��ü ���̾� max-width ����		*/
	public void setTransferSelectMaxWidth(double width){
		super.setMaxWidth(width);
	}
	
	/**		����׸� ������ �߰�		*/
	public void addUseItem(TransferSelectItem<T> item){
		this.addItem(this.useBox, item);
	}
	
	/**		�������׸� ������ �߰�		*/
	public void addNotUseItem(TransferSelectItem<T> item){
		this.addItem(this.notUseBox, item);
	}
	
	
	/**		������ �߰�		*/
	private void addItem(VBox box, TransferSelectItem<T> item){
		Label lb = this.makeItemLabel(item, box);
		box.getChildren().add(lb);
	}
	
	/**		����׸� ������ ����		*/
	public TransferSelectItem<T> removeUseItem(TransferSelectItem<T> item){
		return this.removeItem(this.useBox, item);
	}
	
	/**		�������׸� ������ ����		*/
	public TransferSelectItem<T> removeNotUseItem(TransferSelectItem<T> item){
		return this.removeItem(this.notUseBox, item);
	}
	
	/**		������ ����		*/
	@SuppressWarnings("unchecked")
	private TransferSelectItem<T> removeItem(VBox box, TransferSelectItem<T> item){
		ObservableList<Node> list = box.getChildren();
		for(Node node : list){
			if( node.getUserData() != item )		continue;
				
			return list.remove(node) ? (TransferSelectItem<T>)node.getUserData() : null;
		}
		
		return null;
	}
	
	/**		����׸� ������ ����		*/
	public TransferSelectItem<T> removeUseItem(T itemId){
		return this.removeItem(this.useBox, itemId);
	}
	
	/**		����׸� ������ ����		*/
	public TransferSelectItem<T> removeNotUseItem(T itemId){
		return this.removeItem(this.notUseBox, itemId);
	}
	
	/**		������ ����		*/
	@SuppressWarnings("unchecked")
	private TransferSelectItem<T> removeItem(VBox box, T itemId){
		ObservableList<Node> list = box.getChildren();
		
		TransferSelectItem<T> item;
		T beforeItemId;
		for(Node node : list){
			item = (TransferSelectItem<T>)node.getUserData();
			beforeItemId = item.getItemId();

			if( beforeItemId == itemId || beforeItemId.equals(itemId) )		return list.remove(node) ? item : null;
		}
		
		return null;
	}

	@Override
	public void setStyleClass(String value){
		new BomzUIUtil().setStyleClass(super.getStyleClass(), value);
	}
}
