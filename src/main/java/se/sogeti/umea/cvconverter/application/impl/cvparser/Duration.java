package se.sogeti.umea.cvconverter.application.impl.cvparser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Duration {

	DateFormat df = new SimpleDateFormat("yyyy-MM");

	private final Calendar start;
	private final Calendar stop;

	/**
	 * Constructs a duration from a string with the format yyyy-MM (- yyyy-MM).
	 * 
	 * @param date
	 *            the duration as a string.
	 * 
	 * @throws ParseException
	 *             if the string can not be parsed to a date.
	 */
	public Duration(String date) throws ParseException {
		String[] dateStartAndStop = date.split(" - ");

		start = new GregorianCalendar();
		start.setTime(df.parse(dateStartAndStop[0]));

		if (dateStartAndStop.length > 1) {
			stop = new GregorianCalendar();
			stop.setTime(df.parse(dateStartAndStop[1]));
		} else {
			stop = null;
		}
	}

	/**
	 * Gets the duration in months expressed as an integer.
	 * 
	 * @return the duration in months or 0 if there is no stop date to this
	 *         duration.
	 */
	public int getDurationMonths() {
		if (stop != null) {
			int m1 = start.get(Calendar.YEAR) * 12 + start.get(Calendar.MONTH);
			int m2 = stop.get(Calendar.YEAR) * 12 + stop.get(Calendar.MONTH);
			return m2 - m1;
		} else {
			return 0;
		}
	}

	/**
	 * Gets the duration as a string. If the startYear and stopYear is the same,
	 * or if there is no stopYear, the stopYear will be omitted from the
	 * string.
	 * 
	 * @return the duration as a string with format: yyyy (-yyyy)
	 */
	public String getDurationYearString() {
		int startYear = start.get(Calendar.YEAR);
		if (stop != null) {
			int stopYear = stop.get(Calendar.YEAR);
			if (startYear != stopYear) {
				return startYear + " - " + stopYear;
			}
		}
		return startYear + "";
	}
	

	@Override
	public String toString() {
		return "Duration [df=" + df + ", start=" + start + ", stop=" + stop
				+ "]";
	}

}
