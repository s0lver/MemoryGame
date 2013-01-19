package solver.app.gui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import solver.app.FlagPair;
import solver.app.GameValues;

@SuppressWarnings("serial")
public class GUIGame extends JFrame implements ActionListener {
	private final static String[] COUNTRIES = { "Afghanistan", "Brazil",
			"Chile", "China", "Denmark", "France", "Germany", "Italy",
			"Nigeria", "Romania", "Russia", "San-Marino", "Saudi-Arabia",
			"South-Africa", "Switzerland", "Turkey", "United-Kingdom",
			"Uruguay" };
	// private int boardsize = -1;
	private String username = null;
	private int[] orderOfFlags = null;
	private ArrayList<ImageIcon> listOfImages = null;
	private ArrayList<FlagPair> listOfFlagPairs = null;
	private ArrayList<JButton> buttons = null;

	private int firstFlagPressed = -1, secondFlagPressed = -1;
	private boolean toBeDisabled = false, userStarted = true;
	private JButton firstButtonPressed = null, secondButtonPressed = null;
	private JLabel lblTime = null;
	private int numberOfPairs = 0;
	private int numberOfGuessedPairs = 0;
	private int numberOfTries = 0;
	private int runningSeconds = -1;
	private Date dateStarted, dateFinished;
	private Properties preferences = null;

	Timer timer = new Timer();

	TimerTask timerTask = new TimerTask() {
		public void run() {
			// here have your program do whatever you wnt
			runningSeconds++;
			lblTime.setText("Running Time: " + runningSeconds + " sec");
			// "Running time: --:--:--"
		}
	};

	public GUIGame(Properties preferences) {
		super("Memory game");
		this.preferences = preferences;
		String str_boardsize = preferences
				.getProperty(GameValues.STRING_BOARDSIZE);
		switch (str_boardsize) {
		case GameValues.STRING_BOARDSIZE_SMALL:
			numberOfPairs = 8; // A board of 4x4
			// boardsize = 1;
			break;
		case GameValues.STRING_BOARDSIZE_MEDIUM:
			numberOfPairs = 10; // A board of 5x4
			// boardsize = 2;
			break;
		case GameValues.STRING_BOARDSIZE_BIG:
			numberOfPairs = 18; // A board of 6x6;
			// boardsize = 3;
		}
		username = preferences.getProperty(GameValues.STRING_USERNAME);
		prepareGUI();
	}

	/***
	 * Prepares the GUI of game according to the user settings
	 */
	private void prepareGUI() {
		setTitle(getTitle() + ", Good Luck " + username);
		loadImages();
		setUpFlagPairs();
		createButtons();

		setLayout(new BorderLayout());
		add(new JLabel("Do the matches!!"), BorderLayout.NORTH);
		lblTime = new JLabel("Running time: --:--:--");
		add(lblTime, BorderLayout.SOUTH);
		JPanel centralPanel = new JPanel();
		switch (preferences.getProperty(GameValues.STRING_BOARDSIZE)) {
		case GameValues.STRING_BOARDSIZE_SMALL:
			centralPanel.setLayout(new GridLayout(4, 4));
			break;
		case GameValues.STRING_BOARDSIZE_MEDIUM:
			centralPanel.setLayout(new GridLayout(4, 5));
			break;
		case GameValues.STRING_BOARDSIZE_BIG:
			centralPanel.setLayout(new GridLayout(6, 6));
		}

		for (JButton button : buttons) {
			centralPanel.add(button);
		}
		add(centralPanel, BorderLayout.CENTER);
		setSize(800, 600);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/***
	 * Prepares and shuffles the flags
	 */
	private void setUpFlagPairs() {
		orderOfFlags = new int[numberOfPairs * 2];
		for (int i = 0; i < orderOfFlags.length; i++) {
			orderOfFlags[i] = i;
		}

		shuffleArray(orderOfFlags);
		listOfFlagPairs = new ArrayList<FlagPair>();

		// Taking pairs of shuffled array
		FlagPair tmpFlagPair = null;
		for (int i = 0; i < numberOfPairs; i++) {
			tmpFlagPair = new FlagPair(COUNTRIES[i], orderOfFlags[i * 2],
					orderOfFlags[(i * 2) + 1], listOfImages.get(i));
			listOfFlagPairs.add(tmpFlagPair);
		}
	}

	/*
	 * Loads the images of the flags
	 */
	private void loadImages() {
		listOfImages = new ArrayList<ImageIcon>();
		for (int i = 0; i < numberOfPairs; i++) {
			listOfImages.add(new ImageIcon(COUNTRIES[i] + "-128.png",
					COUNTRIES[i]));
		}
	}

	/***
	 * Creates the buttons
	 */
	private void createButtons() {
		JButton btnTmp = null;
		buttons = new ArrayList<JButton>(orderOfFlags.length);
		// Add N empty elements to buttons
		for (int i = 0; i < orderOfFlags.length; i++) {
			buttons.add(null);
		}

		for (int i = 0; i < listOfFlagPairs.size(); i++) {
			FlagPair tmpfp = listOfFlagPairs.get(i);

			btnTmp = new JButton();
			btnTmp.setDisabledIcon(tmpfp.getImage());
			btnTmp.setIcon(new ImageIcon("aim.png", ""));
			btnTmp.setEnabled(true);
			btnTmp.setActionCommand(String.valueOf(tmpfp.getPairA()));
			btnTmp.addActionListener(this);
			buttons.set(tmpfp.getPairA(), btnTmp);

			btnTmp = new JButton();
			btnTmp.setDisabledIcon(tmpfp.getImage());
			btnTmp.setIcon(new ImageIcon("aim.png", ""));
			btnTmp.setActionCommand(String.valueOf(tmpfp.getPairB()));
			btnTmp.addActionListener(this);
			buttons.set(tmpfp.getPairB(), btnTmp);
		}
	}

	/***
	 * Shuffles an array of int
	 * 
	 * @param array
	 *            of int
	 */
	private void shuffleArray(int[] array) {
		Integer[] tmp = new Integer[array.length];
		for (int i = 0; i < array.length; i++) {
			tmp[i] = array[i];
		}
		List<Integer> listOfNumbers = new ArrayList<Integer>(Arrays.asList(tmp));
		Collections.shuffle(listOfNumbers);
		for (int i = 0; i < listOfNumbers.size(); i++) {
			array[i] = listOfNumbers.get(i);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton tmpbtn = (JButton) e.getSource();
		int index = Integer.valueOf(tmpbtn.getActionCommand());
		if (userStarted) {
			dateStarted = new Date();
			timer.schedule(timerTask, 0, 1000);
			userStarted = !true;
		}
		if (toBeDisabled) {
			firstButtonPressed.setEnabled(true);
			secondButtonPressed.setEnabled(true);
		}
		// Beggining a new pair selection?
		if (firstFlagPressed == -1) {
			firstFlagPressed = index;
			firstButtonPressed = tmpbtn;
			firstButtonPressed.setEnabled(false);
			toBeDisabled = false;
		}
		// Finishing a pair selection?
		else {
			secondFlagPressed = index;
			secondButtonPressed = tmpbtn;
			secondButtonPressed.setEnabled(false);
			numberOfTries++;

			// Do the calculations to see if they are the same...
			String firstFlagName = getFlagName(firstFlagPressed);
			String secondFlagName = getFlagName(secondFlagPressed);
			System.out.println(firstFlagName + " AND " + secondFlagName + " ?");

			// A pair Match?
			if (firstFlagName.equals(secondFlagName)) {
				// JOptionPane.showMessageDialog(this, "OK good!", "Advice",
				// JOptionPane.INFORMATION_MESSAGE);
				toBeDisabled = false;
				firstButtonPressed = null;
				secondButtonPressed = null;
				numberOfGuessedPairs++;
				if (numberOfGuessedPairs == numberOfPairs) {
					dateFinished = new Date();
					timer.cancel();
					gameSucceded();
				}
			} else {
				// If not match
				toBeDisabled = true;
			}
			firstFlagPressed = secondFlagPressed = -1;
		}

	}

	/***
	 * Ends the game showing a small message
	 */
	private void gameSucceded() {
		Long diff = dateFinished.getTime() - dateStarted.getTime();
		diff = diff / 1000;
		JOptionPane.showMessageDialog(this, "You have Won! " + numberOfTries
				+ " tries in " + diff + " seconds...", "Advice",
				JOptionPane.INFORMATION_MESSAGE);
		this.dispose();
		new GUIConfigureGame();
	}

	private String getFlagName(int index) {
		for (FlagPair flagpair : listOfFlagPairs) {
			if (flagpair.getPairA() == index || flagpair.getPairB() == index)
				return flagpair.getName();
		}
		return null;
	}
}
