package kr.co.bomz.cmn.ui.field;


import javafx.beans.NamedArg;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import kr.co.bomz.cmn.ui.BomzUI;
import kr.co.bomz.cmn.ui.BomzUIUtil;

/**
 * 	특정 값만 입력 가능한 입력필드
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
	 * 입력할 수 있는 텍스트 최대 수 지정
	 * @param maxTextLength
	 */
	public BomzTextField(@NamedArg("maxTextLength") int maxTextLength, 
			@NamedArg("keyInputType") KeyInputType keyInputType){
		// 이벤트 등록
		this(maxTextLength, true, keyInputType);
	}
	
	public BomzTextField(@NamedArg("maxTextLength") int maxTextLength, 
			@NamedArg("firstCharUseInteger") boolean firstCharUseInteger, 
			@NamedArg("keyInputType") KeyInputType keyInputType){
		
		// 이벤트 등록
		this.event = new TextFieldEvent(maxTextLength, firstCharUseInteger, keyInputType, this);
		super.focusedProperty().addListener(this.event);
		
		super.addEventFilter(KeyEvent.KEY_TYPED, (e)->{
			String value = e.getCharacter();
			
			// 키 입력값이 제대로 넘어왔는지 검사
			if( value == null || value.isEmpty() ){
				e.consume();
				return;
			}
			
			// 최대 자릿수가 정해져 있을 경우 자릿수를 넘었는지 검사
			if( this.event.isMaxText() ){
				e.consume();
				return;
			}
			
			// 키 입력값 유효성 검사
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
