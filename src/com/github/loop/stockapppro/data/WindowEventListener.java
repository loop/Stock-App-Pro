package com.github.loop.stockapppro.data;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

import com.github.loop.stockapppro.gui.TableWindow;

/**
 * The <code>WindowEventListener</code> class makes sure if the window is closed
 * the item is removed from the <code>Map</code>.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class WindowEventListener extends WindowAdapter {
	String url;
	HashMap<String, TableWindow> windows;

	/**
	 * 
	 * @param url
	 *            the url of the stock query which is key
	 * @param windows
	 *            the stock table object which is stored as the value
	 * @see TableModel
	 */
	public WindowEventListener(String url, HashMap<String, TableWindow> windows) {
		this.url = url;
		this.windows = windows;
	}

	public void windowClosing(WindowEvent evt) {
		windows.remove(url);
	}
}
