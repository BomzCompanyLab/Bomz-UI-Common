package kr.co.bomz.cmn;

/**
 * 	공통적으로 사용되는 유틸
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class BomzUtils {

	/**		
	 * 		가격 표시를 위해 3자리마다 쉼표를 추가한다
	 * 		예)		1234567 요청 시 1,234,567 로 리턴		
	 **/
	public static final String toPriceValue(int value){
		return toPriceValue(value +"");
	}
	
	/**		
	 * 		가격 표시를 위해 3자리마다 쉼표를 추가한다
	 * 		예)		1234567 요청 시 1,234,567 로 리턴		
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
	 * 	화면에 표시하기 위해 긴 문자열을 지정 크기만큼만 리턴한다
	 * @param value				원본 문자열
	 * @param maxLength		최대 글자 수
	 * @return						지정한 최대 글자 수 만큼의 문자열
	 */
	public static String printSubString(String value, int maxLength){
		if( value == null )		return "";
		
		int length = value.length();
		if( length > maxLength )		return value.substring(0, maxLength);
		else										return value;
	}
	
	/**		양수 마지막 자리 반올림 처리		*/
	public static int round(int value){
		if( value < 10 )		return value;
		
		String data = String.valueOf(value);
		int lastValue = Integer.parseInt(data.substring(data.length() - 1));
		
		if( lastValue >= 5 ){
			// 올림 처리
			return value + (10 - lastValue);
			
		}else{
			// 내림 처리
			return value - lastValue;
		}
		
	}
	
//	/**
//	 * 	숫자로 구성된 가격에 읽기 쉽게 쉼표(,)를 추가한다
//	 * @param value		가격
//	 * @return				쉼표가 추가된 가격
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
//		buffer.append(" 원");
//		return buffer.toString();
//	}
	
}
