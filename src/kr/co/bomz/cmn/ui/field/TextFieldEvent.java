package kr.co.bomz.cmn.ui.field;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.TextField;

/**
 * 	�Է��ʵ� �� �˻�
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class TextFieldEvent implements InvalidationListener{
	
	/**		��ü ���� �ִ� ����		*/
	private final int maxTextLength;
	
	/**		ù��° ���ڸ� ���� ��� ����. true �� ��� ���		*/
	private final boolean firstCharUseInteger;
	
	/**		�Է� �� �˻� ����			*/
	private final KeyInputType keyInputType;
			
	/**		�Է� �ʵ�		*/
	private final TextField textTf;
	
	public TextFieldEvent(int maxTextLength, boolean firstCharUseInteger, KeyInputType keyInputType, TextField textTf){
		this.maxTextLength = maxTextLength <= 0 ? -1 : maxTextLength;
		this.firstCharUseInteger = firstCharUseInteger;
		this.keyInputType = keyInputType;
		this.textTf = textTf;
	}
	
	/**		�Է� �� �˻�		*/
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
				// ù��° ���ڴ� ���ڰ� ���� �� ���� ���		
				if( tmpChar >= 48 && tmpChar <= 57 )			continue;
			}
			
			if( this.validation(tmpChar) )		continue;
			
			this.textTf.appendText( String.valueOf(tmpChar) );
			if( this.maxTextLength != -1 && ++appendCount >= this.maxTextLength )		break;
			
		}
		
	}
	
	/**		�Է¹��� ���� �� ��� ����, Ư�����ڸ� �����Ѵ�		*/
	private String replaceSpecialLetters(){
		
		String value = this.textTf.getText();
		if( value == null )		return "";
		
		value = value.trim();
		
		// ������ �Է� ���ڰ� ���� ���
		if( value.length() <= 0 )		return value;
		
		switch( this.keyInputType ){
		case ALL_AND_COMMA :				return this.replaceSpecialLetter2(value);		// Ư����ȣ �� ���� ������ ��� ����
		case ALL_AND_COMMA_AND_ANGLEBRAKET :		return this.replaceSpecialLetter5(value);		// Ư����ȣ �� ���� ������ ��ǥ�� ��ȣ,����,���� ��� ����
		case ALL_AND_COMMA_AND_UPPER :		return this.replaceSpecialLetter2(value.toUpperCase());			// ��ü�� ��å, �μ�
		case ONLY_NUMBER_DOT :				return this.replaceSpecialLetter3(value);		// ������
		case ONLY_NUMBER_HYPHEN :		return this.replaceSpecialLetter4(value);		// ��ȭ��ȣ
		default : return value;
		}
	}
	
	
	/**		����ٿ� ��ǥ(',')�� ������ ��� Ư������ ��� �Ұ�		*/
	private String replaceSpecialLetter2(String value){
		return value.replaceAll("[~!@#$%&*()\\-=\\[\\]\\{\\};':\"./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		����ٿ� ��('.')�� ������ ��� Ư������ ��� �Ұ�		*/
	private String replaceSpecialLetter3(String value){
		return value.replaceAll("[~!@#$%&*()\\-=\\[\\]\\{\\};':\",/<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		����ٿ� �弱('-')�� ������ ��� Ư������ ��� �Ұ�		*/
	private String replaceSpecialLetter4(String value){
		return value.replaceAll("[~!@#$%&*()\\=\\[\\]\\{\\};':\",./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}
	
	/**		����ٿ� ��ǥ(','), ��ȣ�� ������ ��� Ư������ ��� �Ұ�		*/
	private String replaceSpecialLetter5(String value){
		return value.replaceAll("[~!@#$%&*\\-=\\[\\]\\{\\};':\"./<>?\\\\\\|]", "").
				replaceAll("\\^", "").replaceAll("`", "").replaceAll("\\+", "").
				replaceAll(" ", "");
	}

	/**		
	 * 	�ִ� �ڸ����� ������ ���� ��� ���� �ִ� �ڸ�����ŭ ���� �ԷµǾ� �ִ��� �˻�
	 * 	@return ���̻� �Է��� �� ���� ��� true ����
	 **/
	public boolean isMaxText(){
		if( this.maxTextLength == -1 )		return false;
		
		String value = this.textTf.getText();
		if( value == null )		return false;
		
		return value.length() >= this.maxTextLength;
	}
	
}
