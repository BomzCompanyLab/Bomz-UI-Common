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
 * 	선택 / 비선택 VBox 형태의 UI
 * 	아이템 클릭 시 반대편 VBox 로 이동된다
 * 
 * @author Bomz
 * @version 1.1
 * @since 1.1
 *
 */
public class TransferSelect<T> extends HBox implements BomzUI{

	/**		가운데 이동 버튼 박스 고정 넓이		*/
	private final double CENTER_BUTTON_BOX_WIDTH = 80;
	
	private ScrollPane useScrollPane = new ScrollPane();
	private ScrollPane notUseScrollPane = new ScrollPane();
	
	private VBox useBox = new VBox();
	private VBox notUseBox = new VBox();
	
	private Label useBoxTitleLb = new Label("사용");
	private Label notUseBoxTitleLb = new Label("미사용");
	
	private Button moveLeftBt = new Button("◁");
	private Button moveRightBt = new Button("▷");
	
	/**		왼쪽에 사용할 아이템 목록을 표시할 경우 true		*/
	private final boolean positionUseItemLeft;
	
	/**		
	 * 		아이템 이동 시 더블클릭 사용 여부
	 * 	false 일 경우 한번 클릭으로 이동
	 * 	true 일 경우 더블클릭일 경우 이동
	 **/
	private boolean itemMoveDoubleClick = false;
	
	/**		현재 아이템 제목이 삭제되었다면 true		*/
	private boolean removeTitle = false;
	
	public TransferSelect(){
		this(true);
	}
	
	public TransferSelect(boolean positionUseItemLeft){
		this.positionUseItemLeft = positionUseItemLeft;
		this.init();
	}
	
	/**		화면 초기화		*/
	private void init(){
		super.getStyleClass().add("transferSelectBox");
		super.getStylesheets().add( TransferSelect.class.getResource("TransferSelect.css").toExternalForm() );		// css 설정
		
		this.initLayout();
		this.initEvent();
	}
	
	/**		이벤트 설정		*/
	private void initEvent(){
		this.moveLeftBt.setOnAction( (e) -> this.itemMoveEvent( this.positionUseItemLeft ? false : true) );
		this.moveRightBt.setOnAction( (e) -> this.itemMoveEvent( this.positionUseItemLeft ? true : false) );
	}
	
	/**
	 * 	아이템 전체 이동 이벤트 처리
	 * @param inToOut		true 일 경우 사용목록에서 미사용목록으로 이동
	 */
	private void itemMoveEvent(boolean useToNotUse){
		ObservableList<Node> useList = this.useBox.getChildren();
		ObservableList<Node> notUseList = this.notUseBox.getChildren();
		
		if( useToNotUse ){
			// 사용에서 사용안함으로 이동할 경우
			notUseList.addAll(useList);
			useList.clear();
		}else{
			// 사용안함에서 사용으로 이동할 경우
			useList.addAll(notUseList);
			notUseList.clear();
		}
		
	}
	
	/**		화면 구성 설정		*/
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
	
	/**		아이템 제목 박스 추가		*/
	private FlowPane makeItemTitlePane(Label lb){
		FlowPane pn = new FlowPane();
		pn.setAlignment(Pos.CENTER);
		pn.getChildren().add(lb);
		
		return pn;
	}
	
	/**	화면 중간 아이템 이동 버튼 박스 초기화		*/
	private void initLayoutMoveButtonPane(VBox centerBox){
		centerBox.getStyleClass().add("centerMoveButtonBox");
		
		centerBox.setMaxWidth(CENTER_BUTTON_BOX_WIDTH);
		centerBox.setMinWidth(CENTER_BUTTON_BOX_WIDTH);
		centerBox.prefHeightProperty().bind(super.heightProperty());
		centerBox.getChildren().addAll(this.moveLeftBt, this.moveRightBt);
	}
	
	/**	양쪽 아이템이 표시되는 ScrollPane 초기화		*/
	private void initLayoutScrollPane(ScrollPane scrollPn, VBox content){
		content.getStyleClass().add("itemBox");
		content.prefWidthProperty().bind(scrollPn.widthProperty().subtract(24));
		
		scrollPn.setContent(content);
		scrollPn.setVbarPolicy(ScrollBarPolicy.ALWAYS);		// 위아래 스크롤바 항상 보이기
		scrollPn.setHbarPolicy(ScrollBarPolicy.NEVER);		// 양옆 스크롤바 항상 숨기기
		scrollPn.prefHeightProperty().bind(super.heightProperty());
		scrollPn.prefWidthProperty().bind(super.widthProperty().divide(2).subtract(CENTER_BUTTON_BOX_WIDTH/2));
	}
	
	/**		사용하는 항목의 내용 목록 리턴		*/
	public List<TransferSelectItem<T>> getUseItemList(){
		return this.getItemList(this.useBox);
	}
	
	/**		사용하지 않는 항목의 내용 목록 리턴		*/
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
	
	/**		사용하는 항목 목록에 표시될 내용 설정		*/
	public void setUseItemList(List<TransferSelectItem<T>> list){
		this.setItemList(this.useBox, list);
	}
	
	/**		사용하지 않는 항목 목록에 표시될 내용 설정		*/
	public void setNotUseItemList(List<TransferSelectItem<T>> list){
		this.setItemList(this.notUseBox, list);
	}
	
	/**		목록 화면 설정		*/
	private void setItemList(VBox box, List<TransferSelectItem<T>> list){
		ObservableList<Node> boxList = box.getChildren();
		
		if( list == null || list.isEmpty() ){
			// 설정 항목이 비었을 경우
			boxList.clear();		// 기존 내용 모두 삭제
		}else{
			// 설정 항목이 있을 경우
			List<Label> lbList = new ArrayList<Label>(list.size());
			for(TransferSelectItem<T> item : list){
				lbList.add( this.makeItemLabel(item, box) );
			}
			
			boxList.clear();				// 기존 내용 모두 삭제
			boxList.addAll(lbList);	// 신규 내용 추가
		}
	}
	
	/**		아이템 Label 생성		*/
	private Label makeItemLabel(TransferSelectItem<T> item, VBox box){
		Label label = new Label(item.getItemName());
		label.getStyleClass().add("itemLb");
		label.setUserData(item);
		label.prefWidthProperty().bind(box.widthProperty());
		
		// 클릭 이벤트 수행
		label.setOnMouseClicked( (e) -> handleItemClickEvent(label, e) );
		return label;
	}
	
	/**		아이템 Label 클릭 이벤트 수행		*/
	private void handleItemClickEvent(Label lb, MouseEvent e){
		if( this.itemMoveDoubleClick ){
			// 더블클릭 이동 설정일 경우 더블클릭인지 확인한다
			if( e.getClickCount() != 2 )		return;		// 더블클릭이 아닐 경우 처리하지 않는다
		}
		
		ObservableList<Node> useList = this.useBox.getChildren();
		if( useList.remove(lb) ){
			// 삭제 성공 시 기존에 사용아이템 목록에 추가되어 있던 아이템이므로 사용안함 목록으로 이동
			this.notUseBox.getChildren().add(0, lb);
			
		}else{
			// 삭제 실패 시 기존에 사용안함 목록에 추가되어 있던 아이템임
			ObservableList<Node> notUseList = this.notUseBox.getChildren();
			if( !notUseList.remove(lb) )		return;		// 사용안함에도 추가되어 있지 않을 경우 오류임
			
			useList.add(0, lb);
		}
	}
	
	/**
	 * 	화면에 표시될 목록 정보 설정
	 * 
	 * @param useList			사용할 아이템 목록
	 * @param notUseList		사용하지 않는 아이템 목록
	 */
	public void setItemList(List<TransferSelectItem<T>> useList, List<TransferSelectItem<T>> notUseList){
		this.setUseItemList(useList);
		this.setNotUseItemList(notUseList);
	}
	
	/**		위아래 스크롤바 표시 여부		*/
	public void setScrollVBarPolicy(ScrollBarPolicy policy){
		this.useScrollPane.setVbarPolicy(policy);
		this.notUseScrollPane.setVbarPolicy(policy);
	}
	
	/**		양옆 스크롤바 표시 여부		*/
	public void setScrollHBarPolicy(ScrollBarPolicy policy){
		this.useScrollPane.setHbarPolicy(policy);
		this.notUseScrollPane.setHbarPolicy(policy);
	}
	
	/**		아이템을 더블클릭하여 이동처리할 경우 true 설정. 기본값 false		*/
	public void setItemMoveDoubleClick(boolean click){
		this.itemMoveDoubleClick = click;
	}
	
	/**		 아이템 이동 시 더블클릭 사용 여부		*/
	public boolean isItemMoveDoubleClick(){
		return this.itemMoveDoubleClick;
	}
	
	/**		화면 표시 타이틀명 설정		*/
	public void setTitleName(String useTitleName, String notUseTitleName){
		this.useBoxTitleLb.setText(useTitleName);
		this.notUseBoxTitleLb.setText(notUseTitleName);
	}
	
	/**		화면에 표시되는 타이틀명을 삭제한다. 삭제 후 복구할 수 없다		*/
	public synchronized void removeTitle(){
		if( this.removeTitle )			return;		// 이미 삭제처리된 경우
		
		this.removeTitle = true;
		
		try{
			((VBox)super.getChildren().get(0)).getChildren().remove(0);
			((VBox)super.getChildren().get(2)).getChildren().remove(0);
		}catch(Exception e){}
	}
	
	/**		화면에 표시되는 타이틀 스타일 지정		*/
	public void setTitleStyle(String style){
		this.useBoxTitleLb.setStyle(style);
		this.notUseBoxTitleLb.setStyle(style);
	}
	
	/**		전체 레이어 pref-width 지정		*/
	public void setTransferSelectPrefWidth(double width){
		super.setPrefWidth(width);
	}
	
	/**		전체 레이어 min-width 지정		*/
	public void setTransferSelectMinWidth(double width){
		super.setMinWidth(width);
	}
	
	/**		전체 레이어 max-width 지정		*/
	public void setTransferSelectMaxWidth(double width){
		super.setMaxWidth(width);
	}
	
	/**		사용항목 아이템 추가		*/
	public void addUseItem(TransferSelectItem<T> item){
		this.addItem(this.useBox, item);
	}
	
	/**		사용안함항목 아이템 추가		*/
	public void addNotUseItem(TransferSelectItem<T> item){
		this.addItem(this.notUseBox, item);
	}
	
	
	/**		아이템 추가		*/
	private void addItem(VBox box, TransferSelectItem<T> item){
		Label lb = this.makeItemLabel(item, box);
		box.getChildren().add(lb);
	}
	
	/**		사용항목 아이템 삭제		*/
	public TransferSelectItem<T> removeUseItem(TransferSelectItem<T> item){
		return this.removeItem(this.useBox, item);
	}
	
	/**		사용안함항목 아이템 삭제		*/
	public TransferSelectItem<T> removeNotUseItem(TransferSelectItem<T> item){
		return this.removeItem(this.notUseBox, item);
	}
	
	/**		아이템 삭제		*/
	@SuppressWarnings("unchecked")
	private TransferSelectItem<T> removeItem(VBox box, TransferSelectItem<T> item){
		ObservableList<Node> list = box.getChildren();
		for(Node node : list){
			if( node.getUserData() != item )		continue;
				
			return list.remove(node) ? (TransferSelectItem<T>)node.getUserData() : null;
		}
		
		return null;
	}
	
	/**		사용항목 아이템 삭제		*/
	public TransferSelectItem<T> removeUseItem(T itemId){
		return this.removeItem(this.useBox, itemId);
	}
	
	/**		사용항목 아이템 삭제		*/
	public TransferSelectItem<T> removeNotUseItem(T itemId){
		return this.removeItem(this.notUseBox, itemId);
	}
	
	/**		아이템 삭제		*/
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
