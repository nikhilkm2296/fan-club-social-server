package com.pyxsasys.fc.social.custom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FCSocialProcessingUtil {

	private final static Logger log = LoggerFactory.getLogger(FCSocialProcessingUtil.class);

	public static String getUTCDate(String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utcTime = sdf.format(new Date());
		return utcTime;
	}

	public static String getDaysAddedDate(String dateFormat, String utcDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date curDate = sdf.parse(utcDate);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, 5);
		Date addedDateRes = cal.getTime();
		return sdf.format(addedDateRes);
	}
	
	public static String getDaysMinusDate(String dateFormat, String utcDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date curDate = sdf.parse(utcDate);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, -15);
		Date addedDateRes = cal.getTime();
		return sdf.format(addedDateRes);
	}

	public static String getJsonStringResponse(String api) throws Exception {
		HttpURLConnection connection = getConnection(api);
		return readTheURLResult(connection);
	}

	private static HttpURLConnection getConnection(String api) throws Exception {
		URL url = new URL(api);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.connect();
		return conn;
	}

	/**
	 * Gets the results/feeds XML from the connection established
	 * @param connection the {@link HttpURLConnection} instance
	 * @return string of the XML feed
	 */
	private static String readTheURLResult(HttpURLConnection connection) {
		InputStream is = null;
		StringBuilder resJSON = new StringBuilder();

		try {
			is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String line = null;
			while ((line = reader.readLine()) != null) {
				resJSON.append(line);
			}
		} catch (IOException e) {
			log.error("Error reading response from stats URL..", e);
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
			}
		}
		return resJSON.toString();
	}
}
