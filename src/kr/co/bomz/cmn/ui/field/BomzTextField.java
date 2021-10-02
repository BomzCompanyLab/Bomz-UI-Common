package kr.co.bomz.cmn.ui.field;


import javafx.beans.NamedArg;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import kr.co.bomz.cmn.ui.BomzUI;
import kr.co.bomz.cmn.ui.BomzUIUtil;

/**
 * 	Ư�� ���� �Է� ������ �Է��ʵ�
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzTextField extends TextField implements BomzUI{

	private final TextFieldEvent event;
	
	public BomzTextField(@NamedArg("keyInputType") KeyInputType keyInputType){
		this(-1, keyInputType);
	}
	
	/**
	 * �Է��� �� �ִ� �ؽ�Ʈ �ִ� �� ����
	 * @param maxTextLength
	 */
	public BomzTextField(@NamedArg("maxTextLength") int maxTextLength, 
			@NamedArg("keyInputType") KeyInputType keyInputType){
		// �̺�Ʈ ���
		this(maxTextLength, true, keyInputType);
	}
	
	public BomzTextField(@NamedArg("maxTextLength") int maxTextLength, 
			@NamedArg("firstCharUseInteger") boolean firstCharUseInteger, 
			@NamedArg("keyInputType") KeyInputType keyInputType){
		
		// �̺�Ʈ ���
		this.event = new TextFieldEvent(maxTextLength, firstCharUseInteger, keyInputType, this);
		super.focusedProperty().addListener(this.event);
		
		super.addEventFilter(KeyEvent.KEY_TYPED, (e)->{
			String value = e.getCharacter();
			
			// Ű �Է°��� ����� �Ѿ�Դ��� �˻�
			if( value == null || value.isEmpty() ){
				e.consume();
				return;
			}
			
			// �ִ� �ڸ����� ������ ���� ��� �ڸ����� �Ѿ����� �˻�
			if( this.event.isMaxText() ){
				e.consume();
				return;
			}
			
			// Ű �Է°� ��ȿ�� �˻�
			if( this.event.validation(value.charAt(0)) ){
				e.consume();
				return;
			}
			
		});
		
	}
	
	@Override
	public void setStyleClass(String value){
		new BomzUIUtil().setStyleClass(super.getStyleClass(), value);
	}
}
