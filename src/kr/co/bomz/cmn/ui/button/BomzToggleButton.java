package kr.co.bomz.cmn.ui.button;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import kr.co.bomz.cmn.ui.BomzUI;
import kr.co.bomz.cmn.ui.BomzUIUtil;

/**
 * 	ON / OFF �� ���� �� �� �ϳ��� �����ϴ� ToggleButton
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzToggleButton extends FlowPane implements BomzUI{
	
	/**		OFF ��� ��ư		*/
	private final ToggleButton offBt;
	
	/**		ON ��� ��ư		*/
	private final ToggleButton onBt;
	
	/**		��� ��ư ���� ���� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯		*/
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

	/**		��ư Ŭ�� �̺�Ʈ ó��		*/
	private void clickEvent(boolean isOn){
		// �ش� ó���� ���� ���� ��� �̹� ���õ� ��ư Ŭ�� �� ������ �����ȴ�
		if( isOn )		this.setOn();
		else				this.setOff();
	}
	
	/**		���̾ƿ� �ʱ�ȭ		*/
	private void initLayout(boolean isOff){
		super.getStylesheets().add( BomzToggleButton.class.getResource("BomzToggleButton.css").toExternalForm() );
		
		super.getChildren().addAll(this.offBt, this.onBt);
		
		this.initToggleGroup();						// ��۹�ư �׷� ����
		
		if( isOff )		this.setOff();
		else				this.setOn();
	}
	
	
	/**		ON/OFF ��� �׷� ����		*/
	private void initToggleGroup(){
		ToggleGroup group = new ToggleGroup();
		this.offBt.setToggleGroup(group);
		this.onBt.setToggleGroup(group);
	}

	/**		OFF ��۹�ư �̸� ����		*/
	public void setOffButtonText(String name){
		if( name == null )		return;
		this.offBt.setText(name);
	}
	
	/**		ON ��۹�ư �̸� ����		*/
	public void setOnButtonText(String name){
		if( name == null )		return;
		this.onBt.setText(name);
	}
	
	/**		ON ���� ����		*/
	public boolean isOn(){
		return this.onBt.isSelected();
	}
	
	/**		OFF ���� ����	*/
	public boolean isOff(){
		return this.offBt.isSelected();
	}
	
	/**		ON ����		*/
	public void setOn(){
		this.onBt.setSelected(true);
		if( this.handler != null )		this.handler.handle(new ActionEvent(this.onBt, this));
	}
	
	/**		OFF ����		*/
	public void setOff(){
		this.offBt.setSelected(true);
		if( this.handler != null )		this.handler.handle(new ActionEvent(this.offBt, this));
	}
	
	/**		��ư ���� ����		*/
	public void setButtonWidth(double width){
		if( width <= 0 )		return;		// 0���� ������ ������� �ʴ´�
		
		this.onBt.setMinWidth(width);
		this.onBt.setMaxWidth(width);
		
		this.offBt.setMinWidth(width);
		this.offBt.setMaxWidth(width);
	}
	
	/**		��ư ���� ����		*/
	public void setButtonHeight(double height){
		if( height <= 0 )		return;		// 0���� ������ ������� �ʴ´�
		
		this.onBt.setMinHeight(height);
		this.onBt.setMaxHeight(height);
		
		this.offBt.setMinHeight(height);
		this.offBt.setMaxHeight(height);
	}
	
	/**		��ư Ŭ�� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯 ���		*/
	public void addEventHandler(EventHandler<ActionEvent> handler){
		this.handler = handler;
	}
	
	/**		��ư Ŭ�� �� ȣ��Ǵ� �̺�Ʈ �ڵ鷯 ����		*/
	public void removeEventHandler(){
		this.handler = null;
	}
	
	@Override
	public void setStyleClass(String value){
		new BomzUIUtil().setStyleClass(super.getStyleClass(), value);
	}
}
