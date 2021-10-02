package kr.co.bomz.cmn;

/**
 * 	���������� ���Ǵ� ��ƿ
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzUtils {

	/**		
	 * 		���� ǥ�ø� ���� 3�ڸ����� ��ǥ�� �߰��Ѵ�
	 * 		��)		1234567 ��û �� 1,234,567 �� ����		
	 **/
	public static final String toPriceValue(int value){
		return toPriceValue(value +"");
	}
	
	/**		
	 * 		���� ǥ�ø� ���� 3�ڸ����� ��ǥ�� �߰��Ѵ�
	 * 		��)		1234567 ��û �� 1,234,567 �� ����		
	 **/
	public static final String toPriceValue(String value){
		if( value == null )		return "";
		
		value = value.trim();
		if( value.isEmpty() )	return "";
		
		StringBuilder buffer = new StringBuilder(value.trim());
		int length = buffer.length();
		while( true ){
			length-=3;
			if( length <= 0 )		break;
			buffer.insert(length, ",");
		}
		
		return buffer.toString();
	}
	

	/**
	 * 	ȭ�鿡 ǥ���ϱ� ���� �� ���ڿ��� ���� ũ�⸸ŭ�� �����Ѵ�
	 * @param value				���� ���ڿ�
	 * @param maxLength		�ִ� ���� ��
	 * @return						������ �ִ� ���� �� ��ŭ�� ���ڿ�
	 */
	public static String printSubString(String value, int maxLength){
		if( value == null )		return "";
		
		int length = value.length();
		if( length > maxLength )		return value.substring(0, maxLength);
		else										return value;
	}
	
	/**		��� ������ �ڸ� �ݿø� ó��		*/
	public static int round(int value){
		if( value < 10 )		return value;
		
		String data = String.valueOf(value);
		int lastValue = Integer.parseInt(data.substring(data.length() - 1));
		
		if( lastValue >= 5 ){
			// �ø� ó��
			return value + (10 - lastValue);
			
		}else{
			// ���� ó��
			return value - lastValue;
		}
		
	}
	
//	/**
//	 * 	���ڷ� ������ ���ݿ� �б� ���� ��ǥ(,)�� �߰��Ѵ�
//	 * @param value		����
//	 * @return				��ǥ�� �߰��� ����
//	 */
//	public static String printPrice(int value){
//		StringBuilder buffer = new StringBuilder(String.valueOf(value));
//		
//		int length = buffer.length();
//		int loop =  (length / 3) + (length %3 == 0 ? -1 : 0);
//		
//		for(int i=0; i < loop; i++){
//			length -= 3;
//			buffer.insert(length, ",");
//		}
//		
//		buffer.append(" ��");
//		return buffer.toString();
//	}
	
}
