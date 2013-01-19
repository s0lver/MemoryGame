package solver.app.gui;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/***
 * Initial frame of our beloved game!
 * 
 * @author Rafael
 * 
 */
@SuppressWarnings("serial")
public class GUIWelcome extends JFrame {
	private final static String TITLE_GUI_WELCOME = "Welcome to the game";
	private final static String TWITTER_PROFILE_URL = "http://twitter.com/s0lver";

	/***
	 * Button to advance to the next screen
	 */
	private JButton btnEnter = null;
	/***
	 * Button to see s0lver's twitter profile
	 */
	private JButton btnTwitterProfile = null;

	/***
	 * URL to see author profile in twitter!
	 */
	private URI twitterUri = null;

	/***
	 * Parametrised Constructor
	 * 
	 * @param theTitle
	 */
	public GUIWelcome() {
		super(TITLE_GUI_WELCOME);

		prepararGUI();

	}

	/***
	 * Prepares the GUI of the welcome JFrame
	 */
	private void prepararGUI() {
		setLayout(new GridLayout(0, 1));

		JPanel jp = new JPanel();
		jp.setLayout(new BorderLayout());
		JLabel tmpLabel = new JLabel("Welcome, press button to start");
		tmpLabel.setHorizontalAlignment(SwingConstants.CENTER);
		jp.add(tmpLabel, BorderLayout.CENTER);
		add(jp);

		jp = new JPanel();
		ImageIcon img = new ImageIcon("aim.png", null);
		btnEnter = new JButton("Enter!", img);
		btnEnter.setVerticalTextPosition(SwingConstants.BOTTOM);
		btnEnter.setHorizontalTextPosition(SwingConstants.CENTER);
		jp.add(btnEnter);
		add(jp);

		jp = new JPanel();
		// jp.setLayout(new BorderLayout());
		btnTwitterProfile = new JButton(
				"<HTML><FONT color=\"#000099\"><U>@s0lver</U></FONT></HTML>");
		btnTwitterProfile.setBorderPainted(false);
		btnTwitterProfile.setOpaque(false);
		btnTwitterProfile.setToolTipText(TWITTER_PROFILE_URL);

		try {
			twitterUri = new URI(TWITTER_PROFILE_URL);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		btnTwitterProfile.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (Desktop.isDesktopSupported()) {
					try {
						Desktop.getDesktop().browse(twitterUri);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Feature not supported :(");
				}
			}
		});
		jp.add(btnTwitterProfile);
		add(jp);

		setSize(500, 600);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		btnEnter.addActionListener(new ListenerBtn(this));

		setLocationRelativeTo(null);

	}

	/***
	 * Little helper class to dispose this JFrame instance and launch the
	 * ConfigureGame JFrame
	 * 
	 * @author Rafael
	 * 
	 */
	class ListenerBtn implements ActionListener {
		GUIWelcome g = null;

		public ListenerBtn(GUIWelcome g) {
			this.g = g;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			g.dispose();
			new GUIConfigureGame();
		}
	}
}
