package kr.co.bomz.cmn.ui.field;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TextField;

/**
 * 	입력필드 값 검사
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TextFieldEvent implements InvalidationListener{
	
	/**		전체 문자 최대 길이		*/
	private final int maxTextLength;
	
	/**		첫번째 문자를 숫자 허용 여부. true 일 경우 허용		*/
	private final boolean firstCharUseInteger;
	
	/**		입력 값 검사 조건			*/
	private final KeyInputType keyInputType;
			
	/**		입력 필드		*/
	private final TextField textTf;
	
	public TextFieldEvent(int maxTextLength, boolean firstCharUseInteger, KeyInputType keyInputType, TextField textTf){
		this.maxTextLength = maxTextLength <= 0 ? -1 : maxTextLength;
		this.firstCharUseInteger = firstCharUseInteger;
		this.keyInputType = keyInputType;
		this.textTf = textTf;
	}
	
	/**		입력 값 검사		*/
	public boolean validation(char value){
		switch( this.keyInputType ){
		case ONLY_NUMBER :
			return !(value >= 48 && value <= 57);

		case ONLY_ENGLISH :
			return !((value >= 65 && value <= 90) || (value >= 97 && value <= 122));

		case ONLY_NUMBER_AND_ENGLISH :
			return !((value >= 48 && value <= 57) || (value >= 65 && value <= 90) || (value >= 97 && value <= 122));
			
		case ONLY_NUMBER_DOT :
			return !((value >= 48 && value <= 57) || value == '.');

		case ONLY_NUMBER_HYPHEN :
			return !((value >= 48 && value <= 57) || value == '-');
			
		default :		return false;
		}
	}
	
	@Override
	public void invalidated(Observable event) {
		
		String text = this.replaceSpecialLetters();
		
		this.textTf.setText("");
		
		final int length = text.length();
		int appendCount = 0;
		
		char tmpChar;
		for(int i=0; i < length; i++){
			tmpChar = text.charAt(i);
			// 0 : 48, 1 : 49, 9 : 57 , A : 65 , Z : 90 , a : 97 , z : 122
			
			if(!this.firstCharUseInteger && appendCount == 0){
				// 첫번째 문자는 숫자가 들어올 수 없을 경우		
				if( tmpChar >= 48 && tmpChar <= 57 )			continue;
			}
			
			if( this.validation(tmpChar) )		continue;
			
			this.textTf.appendText( String.valueOf(tmpChar) );
			if( this.maxTextLength != -1 && ++appendCount >= this.maxTextLength )		break;
			
		}
		
	}
	
	/**		입력받은 문자 중 모든 공백, 특수문자를 삭제한다		*/
	private String replaceSpecialLetters(){
		
		String value = this.textTf.getText();
		if( value == null )		return "";
		
		value = value.trim();
		
		// 별도의 입력 문자가 없을 경우
		if( value.length() <= 0 )		return value;
		
		switch( this.keyInputType ){
		case ALL_AND_COMMA :				return this.replaceSpecialLetter2(value);		// 특수기호 및 예약어를 제외한 모든 문자
		case ALL_AND_COMMA_AND_ANGLEBRAKET :		return this.replaceSpecialLetter5(value);		// 특수기호 및 예약어를 제외한 쉼표와 괄호,영어,숫자 사용 가능
		case ALL_AND_COMMA_AND_UPPER :		return this.replaceSpecialLetter2(value.toUpperCase());			// 업체의 직책, 부서
		case ONLY_NUMBER_DOT :				return this.replaceSpecialLetter3(value);		// 아이피
		case ONLY_NUMBER_HYPHEN :		return this.replaceSpecialLetter4(value);		// 전화번호
		default : return value;
		}
	}
	
	
	/**		언더바와 쉼표(',')를 제외한 모든 특수문자 사용 불가		*/
	private String replaceSpecialLetter2(String value){
		return value.replaceAll("[~!@#$%&*()\\-=\\[\\]\\{\\};':\"./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		언더바와 점('.')을 제외한 모든 특수문자 사용 불가		*/
	private String replaceSpecialLetter3(String value){
		return value.replaceAll("[~!@#$%&*()\\-=\\[\\]\\{\\};':\",/<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		언더바와 장선('-')을 제외한 모든 특수문자 사용 불가		*/
	private String replaceSpecialLetter4(String value){
		return value.replaceAll("[~!@#$%&*()\\=\\[\\]\\{\\};':\",./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		언더바와 쉼표(','), 괄호를 제외한 모든 특수문자 사용 불가		*/
	private String replaceSpecialLetter5(String value){
		return value.replaceAll("[~!@#$%&*\\-=\\[\\]\\{\\};':\"./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}

	/**		
	 * 	최대 자릿수가 정해져 있을 경우 현재 최대 자릿수만큼 글이 입력되어 있는지 검사
	 * 	@return 더이상 입력할 수 없을 경우 true 리턴
	 **/
	public boolean isMaxText(){
		if( this.maxTextLength == -1 )		return false;
		
		String value = this.textTf.getText();
		if( value == null )		return false;
		
		return value.length() >= this.maxTextLength;
	}
	
}
