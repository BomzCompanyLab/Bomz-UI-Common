package kr.co.bomz.cmn.module;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import kr.co.bomz.cmn.Commons;

/**
 * 	달력 표준화를 위한 모듈
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class DatePickerModule {

	/**	DatePicker 의 날짜 표시형을 표준 규칙으로 변환		*/
	public static final void initDatePicker(DatePicker datePicker){
		final String pattern = "yyyy-MM-dd";
		StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
			@Override
			public String toString(LocalDate date) {
				if (date != null)		return dateFormatter.format(date);
				else 	                    	return "";
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) 	return LocalDate.parse(string, dateFormatter);
				else 		return null;
				}
			}; 
			
			datePicker.setConverter(converter);
			datePicker.setPromptText(pattern.toLowerCase());
	}
	

	/**		yyyy-MM-dd 형식의 날짜 데이터를 DatePicker 에 설정할 수 있도록 변환		*/
	public static final LocalDate parseDate(String date){
		if( date == null )		return null;
		
		String[] values = date.split("-");
		if( values.length != 3 )		return null;
		
		try{
			return LocalDate.of(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		}catch(Exception e){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("날짜 형식 오류 [{}]", date, e);
			return null;
		}
	}
	
	/**		시작일과 종료일 유효성 검사		*/
	public static final boolean validationStartAndEndDate(DatePicker startDateDp, DatePicker endDateDp){
		String startDate = startDateDp.getEditor().getText();
		String endDate = endDateDp.getEditor().getText();
		
		if( startDate == null || endDate == null || startDate.equals("") || endDate.equals("") )	return true;
		
		// 시작일과 종료일 모두 값이 입력되어 있을 경우
		
		String[] start = startDate.split("-");
		if( start.length != 3 )		return false;
		
		String[] end = endDate.split("-"); 
		if( end.length != 3 )			return false;
		
		try{
			// 년도 비교
			int st = Integer.parseInt(start[0]);
			int ed = Integer.parseInt(end[0]);
			if( st > ed )			return false;
			else if( st < ed )	return true;
			
			// 년도가 같을 경우 월 비교
			st = Integer.parseInt(start[1]);
			ed = Integer.parseInt(end[1]);
			if( st > ed )				return false;
			else if( st < ed )		return true;
			
			// 년도와 월이 같을 경우 일단위 비교
			st = Integer.parseInt(start[2]);
			ed = Integer.parseInt(end[2]);
			if( st > ed )					return false;
						
			return true;
			
		}catch(NumberFormatException e){
			// 오지는 않겠지만 형변환 중 오류가 날 경우를 대비
			return false;
		}
		
	}
	
}
