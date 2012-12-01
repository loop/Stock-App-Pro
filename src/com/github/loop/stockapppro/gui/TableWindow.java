package com.github.loop.stockapppro.gui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.github.loop.stockapppro.data.DataMaintenance;
import com.github.loop.stockapppro.data.TableModel;

/**
 * The <code>TableWindow</code> class creates a new window for each stock symbol
 * displaying the stock data.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class TableWindow extends JFrame {

	private static final long serialVersionUID = -8371730413435262982L;

	private TableModel data;

	/**
	 * Creates a TableWindow object with data object passed-in as the argument.
	 * 
	 * @param data
	 *            information of the stock
	 */
	public TableWindow(TableModel data) {
		super(data.getSymbol() + ": " + data.getStartYear() + "-"
				+ (Integer.parseInt(data.getStartMonth()) + 1) + "-"
				+ data.getStartDay() + " to " + data.getEndYear() + "-"
				+ (Integer.parseInt(data.getEndMonth()) + 1) + "-"
				+ data.getEndDay() + " (" + data.getIntervalText() + ", "
				+ data.getOrderText() + ")");
		this.data = data;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(640, 500);
		initWidgets();
	}

	/**
	 * Creates all widgets and adds them to the layout of TableWindow.
	 */
	private void initWidgets() {
		final JTable jtSpread = new JTable(data);
		jtSpread.setDefaultRenderer(Object.class, new CustomRenderer(data));
		jtSpread.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		jtSpread.setCellSelectionEnabled(true);
		jtSpread.setGridColor(Color.BLACK);
		jtSpread.getTableHeader().setReorderingAllowed(false);
		JScrollPane jsp = new JScrollPane(jtSpread);

		DataMaintenance maintainData = new DataMaintenance();
		JLabel jl = new JLabel();
		jl.setText("Average Adjusted Closing Price: "
				+ maintainData.roundTwoDecimals(data.getAvgAdjClose())
				+ " | Maximal Drawdown: "
				+ maintainData.roundTwoDecimals(data.getDrawdown()));

		setLayout(new BorderLayout());
		add(jsp, BorderLayout.CENTER);
		add(jl, BorderLayout.SOUTH);

	}
}
