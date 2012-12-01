package com.github.loop.stockapppro.data;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The <code>DataMaintenance</code> class checks that the user inputs are
 * correct for improved UX and also makes sure data is presented in a sensible
 * manner such as large numbers rounded off.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class DataMaintenance {

	private String stockInput;
	private int startDay;
	private int startMonth;
	private int startYear;
	private int endDay;
	private int endMonth;
	private int endYear;
	private Calendar calendar1;
	private Calendar calendar2;

	/**
	 * Creates a <code>DataMaintenance</code> object with no arguments.
	 * 
	 * Default constructor created in case there is no explicit parameters.
	 */
	public DataMaintenance() {

	}

	/**
	 * Creates a
	 * <code>DataMaintenance<code> object with all of its fields set to passed-in
	 * arguments.
	 * 
	 * @param stockInput
	 *            the input symbol of the stock
	 * @param startDay
	 *            the start date of the stock details
	 * @param startMonth
	 *            the start month of the stock details
	 * @param startYear
	 *            the start year of the stock details
	 * @param endDay
	 *            the end date of the stock details
	 * @param endMonth
	 *            the end month of the stock details
	 * @param endYear
	 *            the end year of the stock details
	 */
	public DataMaintenance(String stockInput, String startDay,
			String startMonth, String startYear, String endDay,
			String endMonth, String endYear) {
		this.stockInput = stockInput;
		this.startDay = Integer.parseInt(startDay);
		this.startMonth = Integer.parseInt(startMonth);
		this.startYear = Integer.parseInt(startYear);
		this.endDay = Integer.parseInt(endDay);
		this.endMonth = Integer.parseInt(endMonth);
		this.endYear = Integer.parseInt(endYear);
	}

	/**
	 * Checks if the <code>stockInput</code> contains only numbers, capital
	 * letters, periods, length is only 8 characters or less and is not 0.
	 * 
	 * @return true if the regex is matched, length of string is less than 8 or
	 *         less and is not empty.
	 */
	public boolean checkPattern() {
		Pattern r = Pattern.compile("^[\\p{Lu}\\p{Nd}.]{0,8}$");
		Matcher m = r.matcher(stockInput);
		if (m.matches() && stockInput.length() <= 8 && stockInput.length() != 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Rounds numbers to nearest 2 decimal places for sensible UX.
	 * 
	 * @param numberToBeRounded
	 *            the number which is to be rounded to 2 decimal places
	 * @return the number which has been rounded to nearest 2 decimal places.
	 */
	public double roundTwoDecimals(double numberToBeRounded) {
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		Double roundedNumber = Double.valueOf(twoDForm
				.format(numberToBeRounded));
		return roundedNumber;
	}

	/**
	 * Checks to see if the end date is before the start date.
	 * 
	 * @return 0 if the dates are the same, 1 if calendar2 is before calendar1
	 *         and -1 if calendar2 is after calendar1
	 */
	public int checkDates() {

		calendar1 = new GregorianCalendar(startYear, startMonth, startDay);
		calendar2 = new GregorianCalendar(endYear, endMonth, endDay);

		int result = calendar1.compareTo(calendar2);

		return result;

	}
}
