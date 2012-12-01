package com.github.loop.stockapppro;

import com.github.loop.stockapppro.gui.GUIWindow;

/**
 * The MainApp is the main class that should be called to run the application.
 * 
 * @author Yogesh Nagarur
 * @since 2012-03-12
 */
public class MainApp {

	/**
	 * Main method which called when the application is started. It creates the
	 * application window and makes it visible.
	 * 
	 * @param String
	 *            [] args (arguments passed from command line which are not
	 *            used)
	 */
	public static void main(String[] args) {
		new GUIWindow().setVisible(true);
	}

}
