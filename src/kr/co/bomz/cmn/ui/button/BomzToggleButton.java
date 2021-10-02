package kr.co.bomz.cmn.ui.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import kr.co.bomz.cmn.ui.BomzUI;
import kr.co.bomz.cmn.ui.BomzUIUtil;

/**
 * 	ON / OFF 와 같은 둘 중 하나를 선택하는 ToggleButton
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzToggleButton extends FlowPane implements BomzUI{
	
	/**		OFF 토글 버튼		*/
	private final ToggleButton offBt;
	
	/**		ON 토글 버튼		*/
	private final ToggleButton onBt;
	
	/**		토글 버튼 선택 변경 시 호출되는 이벤트 핸들러		*/
	private EventHandler<ActionEvent> handler;
	
	public BomzToggleButton(){
		this(null, null, true);
	}
	
	public BomzToggleButton(boolean isOff){
		this(null, null, isOff);
	}

	public BomzToggleButton(String offName, String onName){
		this(offName, onName, true);
	}

	public BomzToggleButton(String offName, String onName, boolean isOff){
		this.offBt = new ToggleButton(offName == null ? "OFF" : offName);
		this.onBt = new ToggleButton(onName == null ? "ON" : onName);
		
		this.offBt.getStyleClass().add("offToggleBt");
		this.onBt.getStyleClass().add("onToggleBt");
		
		this.initLayout(isOff);
		
		this.onBt.setOnAction( e -> this.clickEvent(true) );
		this.offBt.setOnAction( e -> this.clickEvent(false) );
	}

	/**		버튼 클릭 이벤트 처리		*/
	private void clickEvent(boolean isOn){
		// 해당 처리를 하지 않을 경우 이미 선택된 버튼 클릭 시 선택이 해제된다
		if( isOn )		this.setOn();
		else				this.setOff();
	}
	
	/**		레이아웃 초기화		*/
	private void initLayout(boolean isOff){
		super.getStylesheets().add( BomzToggleButton.class.getResource("BomzToggleButton.css").toExternalForm() );
		
		super.getChildren().addAll(this.offBt, this.onBt);
		
		this.initToggleGroup();						// 토글버튼 그룹 설정
		
		if( isOff )		this.setOff();
		else				this.setOn();
	}
	
	
	/**		ON/OFF 토글 그룹 설정		*/
	private void initToggleGroup(){
		ToggleGroup group = new ToggleGroup();
		this.offBt.setToggleGroup(group);
		this.onBt.setToggleGroup(group);
	}

	/**		OFF 토글버튼 이름 설정		*/
	public void setOffButtonText(String name){
		if( name == null )		return;
		this.offBt.setText(name);
	}
	
	/**		ON 토글버튼 이름 설정		*/
	public void setOnButtonText(String name){
		if( name == null )		return;
		this.onBt.setText(name);
	}
	
	/**		ON 선택 여부		*/
	public boolean isOn(){
		return this.onBt.isSelected();
	}
	
	/**		OFF 선택 여부	*/
	public boolean isOff(){
		return this.offBt.isSelected();
	}
	
	/**		ON 선택		*/
	public void setOn(){
		this.onBt.setSelected(true);
		if( this.handler != null )		this.handler.handle(new ActionEvent(this.onBt, this));
	}
	
	/**		OFF 선택		*/
	public void setOff(){
		this.offBt.setSelected(true);
		if( this.handler != null )		this.handler.handle(new ActionEvent(this.offBt, this));
	}
	
	/**		버튼 넓이 지정		*/
	public void setButtonWidth(double width){
		if( width <= 0 )		return;		// 0보다 작으면 사용하지 않는다
		
		this.onBt.setMinWidth(width);
		this.onBt.setMaxWidth(width);
		
		this.offBt.setMinWidth(width);
		this.offBt.setMaxWidth(width);
	}
	
	/**		버튼 높이 지정		*/
	public void setButtonHeight(double height){
		if( height <= 0 )		return;		// 0보다 작으면 사용하지 않는다
		
		this.onBt.setMinHeight(height);
		this.onBt.setMaxHeight(height);
		
		this.offBt.setMinHeight(height);
		this.offBt.setMaxHeight(height);
	}
	
	/**		버튼 클릭 시 호출되는 이벤트 핸들러 등록		*/
	public void addEventHandler(EventHandler<ActionEvent> handler){
		this.handler = handler;
	}
	
	/**		버튼 클릭 시 호출되는 이벤트 핸들러 삭제		*/
	public void removeEventHandler(){
		this.handler = null;
	}
	
	@Override
	public void setStyleClass(String value){
		new BomzUIUtil().setStyleClass(super.getStyleClass(), value);
	}
}
