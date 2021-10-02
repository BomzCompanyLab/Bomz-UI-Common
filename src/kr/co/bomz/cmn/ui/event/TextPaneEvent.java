package kr.co.bomz.cmn.ui.event;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * 	화면상에 사각형 아이템이 표시되어 상품 등의 정보를 표시한다.
 * 	한줄의 간단한 텍스트 표시용
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TextPaneEvent extends PaneEvent{

	/**		화면 표시용		*/
	private Label textLb;
	
	/**		화면 표시 텍스트		*/
	private String text;
	
	public TextPaneEvent(double screenWidth, double screenHeight) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, true);
	}
	
	public TextPaneEvent(double screenWidth, double screenHeight, boolean clickEvent) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, clickEvent);
	}

	public TextPaneEvent(double screenWidth, double screenHeight, String text) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, true);
		this.text = text;
	}
	
	public TextPaneEvent(double screenWidth, double screenHeight, String text, boolean clickEvent) throws NullPointerException, ScreenSizeException {
		super(screenWidth, screenHeight, clickEvent);
		this.text = text;
	}
	
	/**		화면에 표시되는 이름 수정		*/
	public void setText(String text){
		this.text = text == null ? "" : text.trim();
		
		this.textLb.setText(this.text);
	}

	@Override
	protected void initSubPane() {
		this.textLb = new Label();
		
		FlowPane pn = new FlowPane();
		pn.setAlignment(Pos.CENTER);
		pn.getChildren().add(this.textLb);
		
		super.rootPane.getChildren().add(pn);
	}
	
}
