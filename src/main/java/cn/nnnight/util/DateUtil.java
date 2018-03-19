package cn.nnnight.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {

	public static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	public static String DateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	public static Date StringToDate(String dateStr, String format) throws ParseException{
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(dateStr);
	}
}
