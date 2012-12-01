package com.github.loop.stockapppro.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.github.loop.stockapppro.data.TableModel;

/**
 * The <code>CustomRenderer</code> class changes the colour of the adjustment
 * column by checking the values receives from the Data object.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */

class CustomRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 8317268474223636389L;

	TableModel data;

	/**
	 * Creates a <code>CustomRenderer</code> object with all of its fields set
	 * to the passed in arguments.
	 * 
	 * @param the
	 *            data object
	 */
	public CustomRenderer(TableModel data) {
		this.data = data;
	}

	/**
	 * Returns component foreground colour depending on the value, if the
	 * adjustment price is higher than the previous it returns the component of
	 * colour green, if it is lower then returns component of colour red. The
	 * latest price will stay black as it has nothing to compare to.
	 * 
	 * @return the component with the foreground colour changed
	 * @see java.awt.Component
	 */
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component c = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);

		if (column == 6) {
			double adjClose = Double.parseDouble((String) table.getModel()
					.getValueAt(row, column));
			double adjClosePrevious;
			if (!data.getOrder()) {
				adjClosePrevious = Double
						.parseDouble((String) table.getModel().getValueAt(
								table.getModel().getRowCount() - 1, column));
				if (row < data.getRowCount() - 1)
					adjClosePrevious = Double.parseDouble((String) table
							.getModel().getValueAt(row + 1, column));
			} else {
				adjClosePrevious = Double.parseDouble((String) table.getModel()
						.getValueAt(0, column));
				if (row > 1)
					adjClosePrevious = Double.parseDouble((String) table
							.getModel().getValueAt(row - 1, column));
			}

			if (adjClose > adjClosePrevious) {
				c.setForeground(new Color(0, 128, 0));
			} else if (adjClose < adjClosePrevious) {
				c.setForeground(Color.red);
			} else {
				c.setForeground(Color.black);
			}
		} else {
			c.setForeground(Color.black);
		}

		return c;
	}

}