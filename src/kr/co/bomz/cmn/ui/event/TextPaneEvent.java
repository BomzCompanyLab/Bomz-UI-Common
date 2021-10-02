package kr.co.bomz.cmn.ui.event;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

/**
 * 	ȭ��� �簢�� �������� ǥ�õǾ� ��ǰ ���� ������ ǥ���Ѵ�.
 * 	������ ������ �ؽ�Ʈ ǥ�ÿ�
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TextPaneEvent extends PaneEvent{

	/**		ȭ�� ǥ�ÿ�		*/
	private Label textLb;
	
	/**		ȭ�� ǥ�� �ؽ�Ʈ		*/
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
	
	/**		ȭ�鿡 ǥ�õǴ� �̸� ����		*/
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
