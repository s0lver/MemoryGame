package solver.app;

import java.awt.EventQueue;

import solver.app.gui.GUIWelcome;

public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(Main.class.getName());
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GUIWelcome();
			}
		});

	}
}
