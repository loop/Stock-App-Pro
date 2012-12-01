package com.github.loop.stockapppro.data;


import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * The <code>Data</code> class fetches and manages all known data about specific
 * stock symbol. This class also calculates maximal drawdown and average
 * adjusted closing price.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class TableModel extends AbstractTableModel {

	private static final long serialVersionUID = 667473423307666868L;

	private String symbol;
	private String startDay;
	private String startMonth;
	private String startYear;
	private String endDay;
	private String endMonth;
	private String endYear;
	private String interval;
	private String url;
	private String intervalText;
	private boolean order;
	private String orderText;
	private double avgAdjClose;
	private double drawdown;

	private int columns = 7;

	private ArrayList<StockRecord> data = new ArrayList<StockRecord>();

	/**
	 * Creates a <code>TableModel</code> object with all of its fields set to
	 * passed-in arguments.
	 * 
	 * @param symbol
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
	 * @param interval
	 *            the interval of the stock details
	 * @param order
	 *            the order which data should be shown
	 */
	public TableModel(String symbol, String startDay, String startMonth,
			String startYear, String endDay, String endMonth, String endYear,
			String interval, boolean order) {
		this.symbol = symbol;
		this.startDay = startDay;
		this.startMonth = startMonth;
		this.startYear = startYear;
		this.endDay = endDay;
		this.endMonth = endMonth;
		this.endYear = endYear;
		this.interval = interval;
		this.order = order;

		this.url = "http://ichart.yahoo.com/table.csv?s=" + symbol + "&a="
				+ startMonth + "&b=" + startDay + "&c=" + startYear + "&d="
				+ endMonth + "&e=" + endDay + "&f=" + endYear + "&g="
				+ interval;

	}

	/**
	 * @return the stock data for each day in an ArrayList
	 */
	public ArrayList<StockRecord> getData() {
		return data;
	}

	/**
	 * @return the average adjustment close price
	 */
	public double getAvgAdjClose() {
		return avgAdjClose;
	}

	/**
	 * @return the symbol for the stock
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @return the year of the start date inputed.
	 */
	public String getStartYear() {
		return startYear;
	}

	/**
	 * @return the month of the start date inputed
	 */
	public String getStartMonth() {
		return startMonth;
	}

	/**
	 * @return the day date of the start date inputed
	 */
	public String getStartDay() {
		return startDay;
	}

	/**
	 * @return the year of the end date inputed
	 */
	public String getEndYear() {
		return endYear;
	}

	/**
	 * @return the month of the end date inputed
	 */
	public String getEndMonth() {
		return endMonth;
	}

	/**
	 * @return the day date of the end date inputed
	 */
	public String getEndDay() {
		return endDay;
	}

	/**
	 * @return the interval of the stock data inputed
	 */
	public String getInterval() {
		return interval;
	}

	/**
	 * @return the string representation of the URL used to query
	 */
	public String getUrl() {
		if (order) {
			return url + "1";
		} else {
			return url;
		}
	}

	/**
	 * Queries Yahoo! finance to get the data.
	 * 
	 * This method queries Yahoo! using the <code>URLReader</code> class. It
	 * retrieves the data as a .csv file, it splits the file every time a new
	 * line is declared. It checks if the first line of the csv has been skipped
	 * (this first line tells us what each column contains which is not needed),
	 * if it false then it skips the first line, if it is false it doesn't do
	 * skip it. It then splits each line and adds it to the
	 * <code>ArrayList</code> data. This method also calculates the average
	 * adjustment close and the absolute drawdown.
	 */
	public void queryData() {
		String results = URLReader.readURL(this.url);
		String[] days = results.split("\n");
		boolean firstSkipped = false;
		for (String day : days) {
			if (!firstSkipped) {
				firstSkipped = true;
			} else {
				String[] values = day.split(",");
				data.add(new StockRecord(values[0], values[1], values[2],
						values[3], values[4], values[5], values[6]));
			}
		}

		double total = 0;

		for (int i = 0; i < data.size(); i++) {
			total += Double.parseDouble(data.get(i).getAdjClose());
		}

		double maximalDrawdown = 0.0;
		double peak = Double.MIN_VALUE;

		for (int i = data.size() - 1; i >= 0; i--) {
			double current = Double.parseDouble(data.get(i).getAdjClose());
			if (current > peak) {
				peak = current;
			} else {
				double currentDrawDown = peak - current;
				if (currentDrawDown > maximalDrawdown) {
					maximalDrawdown = currentDrawDown;
				}
			}
		}
		
		/*
		 * The above drawdown is coded from the algorithm taken from
		 * http://en.wikipedia.org/wiki/Drawdown_(economics)
		 * 
		 * MDD = 0 peak = -99999
		 * 	for i = 1 to N step 1
		 * 		if (NAV[i] > peak)
		 * 			peak = NAV[i]
		 * 		else 
		 * 			DD[i] = 100.0 * (peak - NAV[i]) / peak 
		 * 			if (DD[i] > MDD)
		 * 				MDD = DD[i] 
		 * 			endif 
		 * 		endif 
		 * 	endfor
		 */
		
		this.avgAdjClose = total / data.size();
		setIntervalText();
		setOrderText();
		this.drawdown = maximalDrawdown;
	}

	/**
	 * @return the drawdown calculation
	 */
	public double getDrawdown() {
		return drawdown;
	}

	/**
	 * It sets the <code>orderText</code> by checking if the
	 * <code>JCheckBox</code> order is checked or not. If it is checked, the
	 * orderText string is set to "Chronological order" else it is set to
	 * "Reverse chronological order".
	 */
	public void setOrderText() {
		if (order) {
			orderText = ("Chronological order");
		} else {
			orderText = ("Reverse chronological order");
		}
	}

	/**
	 * @return the <code>orderText</code> string which was set at
	 *         <code>setOrderText()</code>
	 */
	public String getOrderText() {
		return orderText;
	}

	/**
	 * @return true if 'Chronological?' is checked at input else returns false
	 */
	public boolean getOrder() {
		return order;
	}

	/**
	 * Sets the <code>IntervalText</code> by checking if this.interval is m, d
	 * or w.
	 */
	public void setIntervalText() {

		if (interval == "m") {
			intervalText = ("Monthly");
		}
		if (interval == "d") {
			intervalText = ("Daily");
		}
		if (interval == "w") {
			intervalText = ("Weekly");
		}
	}

	/**
	 * @return the <code>intervalText</code> string which was set in
	 *         <code>setIntervalText()</code>
	 */
	public String getIntervalText() {
		return intervalText;
	}

	/**
	 * @return the number of columns in the <code>TableModel</code>.
	 */
	@Override
	public int getColumnCount() {
		return columns;
	}

	/**
	 * @return the number rows which is the size of the data
	 *         <code>ArrayList</code>
	 */
	@Override
	public int getRowCount() {
		return data.size();
	}

	/**
	 * @param col
	 *            the column of the table header
	 * 
	 * @return the string of the table header names
	 */
	@Override
	public String getColumnName(int col) {
		switch (col) {
		case 0:
			return "Date";
		case 1:
			return "Open";
		case 2:
			return "High";
		case 3:
			return "Low";
		case 4:
			return "Close";
		case 5:
			return "Volume";
		case 6:
			return "Adj Close";
		default:
			return null;
		}
	}

	/**
	 * If this.order is set to true it returns the data from bottom to top
	 * (chronological order), else returns the data from top to bottom (reverse
	 * chronological order).
	 * 
	 * @param row
	 *            the row number of the table
	 * @param col
	 *            the column number of the table
	 * 
	 * @return the values at the given row and column.
	 */
	@Override
	public Object getValueAt(int row, int col) {
		StockRecord day;
		if (order) {
			day = data.get(data.size() - row - 1);
		} else {
			day = data.get(row);
		}
		switch (col) {
		case 0:
			return day.getDate();
		case 1:
			return day.getOpen();
		case 2:
			return day.getHigh();
		case 3:
			return day.getLow();
		case 4:
			return day.getClose();
		case 5:
			return day.getVolume();
		case 6:
			return day.getAdjClose();
		default:
			return null;
		}
	}

}
