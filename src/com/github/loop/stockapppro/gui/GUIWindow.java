package com.github.loop.stockapppro.gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.github.loop.stockapppro.data.DataMaintenance;
import com.github.loop.stockapppro.data.TableModel;
import com.github.loop.stockapppro.data.WindowEventListener;

/**
 * The <code>GUIWindow</code> initialises all GUI elements of the app and takes
 * care of <code>ActionListener</code>.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class GUIWindow extends JFrame {

	private static final long serialVersionUID = 7900012838099271206L;

	private TableModel data;

	/**
	 * Stores the <code>TableWindow</code> object and the URL so if user asks
	 * the same stock symbol and same data the window is just brought to the
	 * front.
	 */
	private HashMap<String, TableWindow> windows = new HashMap<String, TableWindow>();

	/**
	 * Creates a GUIWindow object.
	 */
	public GUIWindow() {
		super("Stock Market GUI Pro");
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		initWidgets();
	}

	/**
	 * Creates all widgets and adds them to the layout of GUIWindow.
	 */
	private void initWidgets() {
		final JTextField jtfSymbolInput = new JTextField(5);
		JLabel jlStockSymbol = new JLabel("Stock Symbol: ");
		JButton jbLookup = new JButton("Lookup");
		JLabel jlBegin = new JLabel("Begin: ");
		String numberDate[] = new String[31];
		for (int i = 0; i < numberDate.length; i++) {
			numberDate[i] = i + 1 + "";
		}
		String month[] = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		final String numberMonth[] = { "0", "1", "2", "3", "4", "5", "6", "7",
				"8", "9", "10", "11" };
		String numberYear[] = new String[(Calendar.getInstance().get(
				Calendar.YEAR) - 1970) + 1];
		String currentYear = Calendar.getInstance().get(Calendar.YEAR) + "";
		int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
		String currentDay = Calendar.getInstance().get(Calendar.DATE) + "";

		for (int i = 0; i < numberYear.length; i++) {
			numberYear[i] = 1970 + i + "";
		}
		final JComboBox jcbnumberDateB = new JComboBox(numberDate);
		final JComboBox jcbnumberMonthB = new JComboBox(month);
		final JComboBox jcbnumberYearB = new JComboBox(numberYear);
		jcbnumberYearB
				.setSelectedIndex(jcbnumberYearB.getModel().getSize() - 3);
		JLabel jlEnd = new JLabel("End: ");
		final JComboBox jcbnumberDateE = new JComboBox(numberDate);
		final JComboBox jcbnumberMonthE = new JComboBox(month);
		final JComboBox jcbnumberYearE = new JComboBox(numberYear);
		jcbnumberYearE.setSelectedItem(currentYear);
		jcbnumberMonthE.setSelectedIndex(currentMonth);
		jcbnumberDateE.setSelectedItem(currentDay);

		jcbnumberYearB.setSelectedItem("2000");
		jcbnumberMonthB.setSelectedItem("January");
		jcbnumberDateB.setSelectedItem("1");

		JLabel jlInterval = new JLabel("Interval: ");
		String interval[] = { "Monthly", "Weekly", "Daily" };
		final String intervalAbbr[] = { "m", "w", "d" };
		final JComboBox jcbInterval = new JComboBox(interval);
		JLabel jlOrder = new JLabel("Chronlogical Order? ");
		final JCheckBox jcborder = new JCheckBox();

		setLayout(new GridLayout(5, 1));

		JPanel jpTopPanel = new JPanel(new FlowLayout());
		jpTopPanel.add(jlStockSymbol);
		jpTopPanel.add(jtfSymbolInput);

		JPanel jpDateBeginPanel = new JPanel(new FlowLayout());
		jpDateBeginPanel.add(jlBegin);
		jpDateBeginPanel.add(jcbnumberDateB);
		jpDateBeginPanel.add(jcbnumberMonthB);
		jpDateBeginPanel.add(jcbnumberYearB);

		JPanel jpDateEndPanel = new JPanel(new FlowLayout());
		jpDateEndPanel.add(jlEnd);
		jpDateEndPanel.add(jcbnumberDateE);
		jpDateEndPanel.add(jcbnumberMonthE);
		jpDateEndPanel.add(jcbnumberYearE);

		JPanel jpDetailsPanel = new JPanel(new FlowLayout());
		jpDetailsPanel.add(jlInterval);
		jpDetailsPanel.add(jcbInterval);
		jpDetailsPanel.add(jlOrder);
		jpDetailsPanel.add(jcborder);

		JPanel jpEndPanel = new JPanel(new FlowLayout());
		jpEndPanel.add(jbLookup);

		add(jpTopPanel);
		add(jpDateBeginPanel);
		add(jpDateEndPanel);
		add(jpDetailsPanel);
		add(jpEndPanel);
		pack();

		jbLookup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				DataMaintenance checkData = new DataMaintenance(jtfSymbolInput
						.getText(),
						jcbnumberDateB.getSelectedItem().toString(),
						numberMonth[jcbnumberMonthB.getSelectedIndex()],
						jcbnumberYearB.getSelectedItem().toString(),
						jcbnumberDateE.getSelectedItem().toString(),
						numberMonth[jcbnumberMonthE.getSelectedIndex()],
						jcbnumberYearE.getSelectedItem().toString());
				if (checkData.checkPattern() == true) {
					data = new TableModel(jtfSymbolInput.getText(),
							jcbnumberDateB.getSelectedItem().toString(),
							numberMonth[jcbnumberMonthB.getSelectedIndex()],
							jcbnumberYearB.getSelectedItem().toString(),
							jcbnumberDateE.getSelectedItem().toString(),
							numberMonth[jcbnumberMonthE.getSelectedIndex()],
							jcbnumberYearE.getSelectedItem().toString(),
							intervalAbbr[jcbInterval.getSelectedIndex()],
							jcborder.isSelected());
					String url = data.getUrl();
					if (windows.containsKey(url)) {
						windows.get(url).setVisible(true);
						windows.get(url).toFront();
					} else {
						if (checkData.checkDates() != 1) {
							try {
								data.queryData();
								TableWindow window = new TableWindow(data);
								window.setVisible(true);
								window.toFront();
								windows.put(data.getUrl(), window);
								window.addWindowListener(new WindowEventListener(
										url, windows));

							} catch (Exception e) {
								// System.out.println("ERROR");
							}
						} else {
							JOptionPane
									.showMessageDialog(
											null,
											"The end date cannot be before the begin date.",
											"Date Error",
											JOptionPane.ERROR_MESSAGE);
						}
					}
				} else {
					if (jtfSymbolInput.getText().length() == 0) {
						JOptionPane.showMessageDialog(null,
								"Input cannot be blank.", "Input Error",
								JOptionPane.ERROR_MESSAGE);
						jtfSymbolInput.setText("");
					} else {
						JOptionPane.showMessageDialog(
								null,
								"'"
										+ jtfSymbolInput.getText()
										+ "'"
										+ " must be maximum of 8 characters in length (uppercase, digits and period)!",
								"Input Error", JOptionPane.ERROR_MESSAGE);
					}

				}
			}
		});

	}
}
