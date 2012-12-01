package com.github.loop.stockapppro.data;

/**
 * The <code>DayData</code> class represents all known data of a specific stock
 * symbol for one day/week/month.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class StockRecord {

	private String date;
	private String open;
	private String high;
	private String low;
	private String close;
	private String volume;
	private String adjClose;

	/**
	 * Creates a <code>StockRecord</code> object with all of its fields set to
	 * passed-in arguments.
	 * 
	 * @param date
	 *            the date of the stock information
	 * @param open
	 *            the opening price of the stock
	 * @param high
	 *            the highest price of the stock
	 * @param low
	 *            the lowest price of the stock
	 * @param close
	 *            the price of stock at closing
	 * @param volume
	 *            the stock volume
	 * @param adjClose
	 *            the adjusted closing price of the stock
	 */
	public StockRecord(String date, String open, String high, String low,
			String close, String volume, String adjClose) {
		this.date = date;
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
		this.volume = volume;
		this.adjClose = adjClose;
	}

	/**
	 * @return the date of the stock information
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the stock price when the stock market was opened
	 */
	public String getOpen() {
		return open;
	}

	/**
	 * @return the highest stock price on the day
	 */
	public String getHigh() {
		return high;
	}

	/**
	 * @return the lowest stock price on the day
	 */
	public String getLow() {
		return low;
	}

	/**
	 * @return the price of the stock when the market was closed
	 */
	public String getClose() {
		return close;
	}

	/**
	 * @return the share volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @return the adjusted closing price
	 */
	public String getAdjClose() {
		return adjClose;
	}
}
