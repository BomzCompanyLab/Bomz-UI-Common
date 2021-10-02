package kr.co.bomz.cmn.ui.field;

/**
 * 	입력키 허용여부 타입
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public enum KeyInputType {

	/**		숫자만 입력 가능		*/
	ONLY_NUMBER,				
	/**		숫자,영어,한국어 등 입력 가능		*/
	ALL,
	/**		숫자와 영어만 입력 가능		*/
	ONLY_NUMBER_AND_ENGLISH,
	/**		숫자,영어,한국어 등과 쉼표 입력 가능		*/
	ALL_AND_COMMA,
	/**		숫자,영어,한국어 등과 쉼표,괄호 입력 가능		*/
	ALL_AND_COMMA_AND_ANGLEBRAKET,
	/**		숫자,영어대문자,한국어 등과 쉼표 입력 가능		*/
	ALL_AND_COMMA_AND_UPPER,
	/**		숫자와 점만 입력 가능		*/
	ONLY_NUMBER_DOT,
	/**		숫자와 장선('-')만 입력 가능		*/
	ONLY_NUMBER_HYPHEN,
	/**		영어만 입력 가능		*/
	ONLY_ENGLISH
	
}
