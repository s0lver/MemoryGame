package solver.app.gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import solver.app.GameValues;

/**
 * JFrame to configure the behaviour of our beloved game
 * 
 * @author Rafael
 * 
 */
@SuppressWarnings("serial")
public class GUIConfigureGame extends JFrame {
	private JRadioButton jrbSmall = null, jrbMedium = null, jrbBig = null;
	private ButtonGroup bgrpSizeOptions = null;
	private JTextField txtUsername = null;

	private JButton btnStart = null;

	private static Properties preferences = null;

	public GUIConfigureGame() {
		super(GameValues.TITLE_GUI_CONFIGURE_GAME);
		prepareGUI();
	}

	private void prepareGUI() {
		setLayout(new FlowLayout());
		add(new JLabel("Specify boardsize: "));

		preferences = loadPreferences();

		jrbSmall = new JRadioButton(GameValues.STRING_BOARDSIZE_SMALL);
		jrbMedium = new JRadioButton(GameValues.STRING_BOARDSIZE_MEDIUM);
		jrbBig = new JRadioButton(GameValues.STRING_BOARDSIZE_BIG);
		bgrpSizeOptions = new ButtonGroup();
		bgrpSizeOptions.add(jrbSmall);
		bgrpSizeOptions.add(jrbMedium);
		bgrpSizeOptions.add(jrbBig);

		add(jrbSmall);
		add(jrbMedium);
		add(jrbBig);

		add(new JLabel("Input your name: "));
		txtUsername = new JTextField(
				preferences.getProperty(GameValues.STRING_USERNAME), 10);
		add(txtUsername);

		btnStart = new JButton("Start Game!");
		btnStart.addActionListener(new ListenerBtn(this));
		add(btnStart);

		// Load value of properties in screen
		switch (preferences.getProperty(GameValues.STRING_BOARDSIZE)) {
		case "small":
			jrbSmall.setSelected(true);
			break;
		case "medium":
			jrbMedium.setSelected(true);
			break;
		case "big":
			jrbBig.setSelected(true);
			break;
		default:
			break;
		}

		setSize(350, 100);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

	}

	/***
	 * Reads the properties file
	 * 
	 * @return the properties stored or new ones if not found
	 */
	private Properties loadPreferences() {
		preferences = new Properties();
		File propertiesFile = new File(GameValues.PROPERTIES_FILE_NAME);
		if (propertiesFile.exists()) {
			System.out.println("Preferences exist!");
			// Load the preferences
			try {
				preferences.load(new FileInputStream(
						GameValues.PROPERTIES_FILE_NAME));
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("username = "
					+ preferences.getProperty("username") + ", boardsize = "
					+ preferences.getProperty("boardsize"));
		} else {
			// Create and store a new properties file with default preferences
			try {
				System.out.println("Creating preferences with default values!");
				// set the properties value
				preferences.setProperty("username", "nobody");
				preferences.setProperty("boardsize", "small");
				// save properties to project root folder
				writeProperties(preferences);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return preferences;
	}

	/***
	 * Wrapper to write the properties
	 * 
	 * @param prop
	 *            the properties object
	 * @throws IOException
	 *             IDK xD
	 */
	private void writeProperties(Properties prop) throws IOException {
		System.out.println("Saving preferences...");
		prop.store(new FileOutputStream(GameValues.PROPERTIES_FILE_NAME),
				"Properties of the game behaviour");
	}

	private void writeProperties() {
		try {
			preferences.setProperty("username", txtUsername.getText());
			String tmp = "";
			if (jrbSmall.isSelected())
				tmp = "small";
			else if (jrbMedium.isSelected())
				tmp = "medium";
			else
				tmp = "big";
			preferences.setProperty("boardsize", tmp);
			writeProperties(preferences);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Little helper class to dispose this JFrame instance and launch the
	 * GUIGame JFrame
	 * 
	 * @author Rafael
	 * 
	 */
	class ListenerBtn implements ActionListener {
		GUIConfigureGame g = null;

		public ListenerBtn(GUIConfigureGame g) {
			this.g = g;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			g.writeProperties();
			g.dispose();
			new GUIGame(preferences);
		}
	}
}
