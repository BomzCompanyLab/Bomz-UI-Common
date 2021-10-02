package kr.co.bomz.cmn.ui.field;

/**
 * 	�Է�Ű ��뿩�� Ÿ��
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public enum KeyInputType {

	/**		���ڸ� �Է� ����		*/
	ONLY_NUMBER,				
	/**		����,����,�ѱ��� �� �Է� ����		*/
	ALL,
	/**		���ڿ� ��� �Է� ����		*/
	ONLY_NUMBER_AND_ENGLISH,
	/**		����,����,�ѱ��� ��� ��ǥ �Է� ����		*/
	ALL_AND_COMMA,
	/**		����,����,�ѱ��� ��� ��ǥ,��ȣ �Է� ����		*/
	ALL_AND_COMMA_AND_ANGLEBRAKET,
	/**		����,����빮��,�ѱ��� ��� ��ǥ �Է� ����		*/
	ALL_AND_COMMA_AND_UPPER,
	/**		���ڿ� ���� �Է� ����		*/
	ONLY_NUMBER_DOT,
	/**		���ڿ� �弱('-')�� �Է� ����		*/
	ONLY_NUMBER_HYPHEN,
	/**		��� �Է� ����		*/
	ONLY_ENGLISH
	
}
