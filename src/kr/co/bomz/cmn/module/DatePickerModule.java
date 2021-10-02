package kr.co.bomz.cmn.module;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.control.DatePicker;
import javafx.util.StringConverter;
import kr.co.bomz.cmn.Commons;

/**
 * 	�޷� ǥ��ȭ�� ���� ���
 * 
 * @author Bomz
 * @version 1.0
 * @since 1.0
 *
 */
public class DatePickerModule {

	/**	DatePicker �� ��¥ ǥ������ ǥ�� ��Ģ���� ��ȯ		*/
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
	

	/**		yyyy-MM-dd ������ ��¥ �����͸� DatePicker �� ������ �� �ֵ��� ��ȯ		*/
	public static final LocalDate parseDate(String date){
		if( date == null )		return null;
		
		String[] values = date.split("-");
		if( values.length != 3 )		return null;
		
		try{
			return LocalDate.of(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		}catch(Exception e){
			Logger logger = LoggerFactory.getLogger(Commons.CMN_LOGGER_ID);
			logger.error("��¥ ���� ���� [{}]", date, e);
			return null;
		}
	}
	
	/**		�����ϰ� ������ ��ȿ�� �˻�		*/
	public static final boolean validationStartAndEndDate(DatePicker startDateDp, DatePicker endDateDp){
		String startDate = startDateDp.getEditor().getText();
		String endDate = endDateDp.getEditor().getText();
		
		if( startDate == null || endDate == null || startDate.equals("") || endDate.equals("") )	return true;
		
		// �����ϰ� ������ ��� ���� �ԷµǾ� ���� ���
		
		String[] start = startDate.split("-");
		if( start.length != 3 )		return false;
		
		String[] end = endDate.split("-"); 
		if( end.length != 3 )			return false;
		
		try{
			// �⵵ ��
			int st = Integer.parseInt(start[0]);
			int ed = Integer.parseInt(end[0]);
			if( st > ed )			return false;
			else if( st < ed )	return true;
			
			// �⵵�� ���� ��� �� ��
			st = Integer.parseInt(start[1]);
			ed = Integer.parseInt(end[1]);
			if( st > ed )				return false;
			else if( st < ed )		return true;
			
			// �⵵�� ���� ���� ��� �ϴ��� ��
			st = Integer.parseInt(start[2]);
			ed = Integer.parseInt(end[2]);
			if( st > ed )					return false;
						
			return true;
			
		}catch(NumberFormatException e){
			// ������ �ʰ����� ����ȯ �� ������ �� ��츦 ���
			return false;
		}
		
	}
	
}
