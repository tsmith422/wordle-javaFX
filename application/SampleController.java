package application;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.fxml.FXML;

import java.io.*;
import java.util.*;

public class SampleController {
	@FXML
	private Label r1c1, r1c2, r1c3, r1c4, r1c5, r2c1, r2c2, r2c3, r2c4, r2c5, r3c1, r3c2, r3c3, r3c4, r3c5, r4c1, r4c2,
			r4c3, r4c4, r4c5, r5c1, r5c2, r5c3, r5c4, r5c5, r6c1, r6c2, r6c3, r6c4, r6c5, current, warning, played,
			winPercent, streak, maxStreak;

	@FXML
	private VBox leftStats, rightStats;

	@FXML
	private Button a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z;

	private TreeMap<Integer, ArrayList<Label>> board = new TreeMap<>();
	private int row;
	private String word;
	private ArrayList<Button> keyboard;
	private PauseTransition pause;

	// main flag that tells any user input whether it should function or not
	private boolean gameOver;

	public void initialize() throws IOException {
//		Initializing Shadow Data Structure
		gameOver = false;

		warning.setVisible(false);
		{
			board.put(1, new ArrayList<Label>(Arrays.asList(r1c1, r1c2, r1c3, r1c4, r1c5)));
			board.put(2, new ArrayList<Label>(Arrays.asList(r2c1, r2c2, r2c3, r2c4, r2c5)));
			board.put(3, new ArrayList<Label>(Arrays.asList(r3c1, r3c2, r3c3, r3c4, r3c5)));
			board.put(4, new ArrayList<Label>(Arrays.asList(r4c1, r4c2, r4c3, r4c4, r4c5)));
			board.put(5, new ArrayList<Label>(Arrays.asList(r5c1, r5c2, r5c3, r5c4, r5c5)));
			board.put(6, new ArrayList<Label>(Arrays.asList(r6c1, r6c2, r6c3, r6c4, r6c5)));
		}

		// initialize grid labels
		for (Integer key : board.keySet()) {
			for (Label l : board.get(key)) {
				initializeLabel(l);
			}
		}

		// initialize keyboard
		keyboard = new ArrayList<Button>(
				Arrays.asList(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z));

		for (Button b : keyboard) {
			initializeKeyboard(b);
		}

		// set the current label we're writing to, to the first row, first column
		current = board.get(1).get(0);
		row = 1;

		// get the word
		wordInitialize();
		// set the stats by reading from the txt files
		statsInitialize();

		// pause is used so warnings disappear after 3 seconds
		pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(
				event -> warning.setVisible(warning.getText().contains("win") || warning.getText().contains("lose")));

//		System.out.println(word);
	}

	// function for when statistics button is pressed
	public void statsPressed(ActionEvent e) {
		if (leftStats.isVisible()) {
			leftStats.setVisible(false);
			rightStats.setVisible(false);
		} else {
			leftStats.setVisible(true);
			rightStats.setVisible(true);
		}
	}

	// reads the keyboard input and sets the "current" label text to the input (if
	// valid)
	public void keyPressed(KeyEvent e) {
		if (gameOver)
			return;

		switch (e.getCode()) {
		case BACK_SPACE:
			if (currentIndex() == 4 && current.getText() != "") {
				current.setText("");
				break;
			}
			// decrements to previous value in map
			current = getPrevious();
			current.setText("");
			break;
		case A:
		case B:
		case C:
		case D:
		case E:
		case F:
		case G:
		case H:
		case I:
		case J:
		case K:
		case L:
		case M:
		case N:
		case O:
		case P:
		case Q:
		case R:
		case S:
		case T:
		case U:
		case V:
		case W:
		case X:
		case Y:
		case Z:
			// checks to see if keyboard input is an option on the virtual keyboard
			// (buttons)
			boolean good = true;
			for (Button b : keyboard) {
				// makes sure if the key you're typing is disabled or not on the virtual
				// keyboard
				if (b.getText().equals(e.getText().toUpperCase()) && b.isDisabled()) {
					good = false;
					break;
				}
			}
			// sets the "current" label text to the input
			if (current.getText() == "" && good) {
				current.setText(e.getCode().toString());
				// increments to next value in the map
				current = getNext();
			}
			break;
		default:
			break;
		}
	}

	// depending on the button pressed on the virtual keyboard, it sets the
	// "current" label text corresponding to the button
	public void buttonPressed(ActionEvent e) throws IOException {
		if (gameOver)
			return;

		Button b = (Button) e.getSource();
		switch (b.getText()) {
		case "ENTER":
//			System.out.println("ENTER");
			// checks if the row has been filled with 5 letters
			if (current.getText() == "") {
				warning.setText("Not enough letters");
				if (!warning.isVisible())
					pause.play();
				warning.setVisible(true);
				break;
			}
			// calls checkRow() which returns whether or not you guessed the word correctly
			boolean won = checkRow();
			// if you guessed correctly, the game tells you and no more actions are allowed
			// the stats are also updated
			if (won) {
//				System.out.println("u win!");
				warning.setText("You win!\nPress restart to play again!");
				warning.setVisible(true);
				gameOver = true;
				updateStats(won);
			}
			break;
		case "←":
			if (currentIndex() == 4 && current.getText() != "") {
				current.setText("");
				break;
			}
			current = getPrevious();
			current.setText("");
			break;
		case "A":
		case "B":
		case "C":
		case "D":
		case "E":
		case "F":
		case "G":
		case "H":
		case "I":
		case "J":
		case "K":
		case "L":
		case "M":
		case "N":
		case "O":
		case "P":
		case "Q":
		case "R":
		case "S":
		case "T":
		case "U":
		case "V":
		case "W":
		case "X":
		case "Y":
		case "Z":
			if (current.getText() == "") {
				current.setText(b.getText().toString());
				current = getNext();
			}
			break;
		default:
			break;
		}
	}

	// sets the style back to original for each label on the board
	private void initializeLabel(Label l) {
		l.setStyle("-fx-background-color: #1f2224");
		l.setStyle("-fx-border-width: 2.5");
		l.setStyle("-fx-border-color: #909496");
		l.setText("");
	}

	// enables keyboard buttons and resets their colors
	// also I make the cursor a hand to provide a more intuitive feel
	// for the buttons, making them feel a bit more corresponsive
	private void initializeKeyboard(Button b) {
		b.setStyle("-fx-background-color: #909496; -fx-cursor: hand");
		b.setDisable(false);
	}

	// randomly select a word that has not been used before
	private void wordInitialize() throws IOException {
		Scanner usedFile = new Scanner(new FileReader("UsedWords.txt"));
		Scanner statFile = new Scanner(new FileReader("statLog.txt"));
		// numUsed stores the number of times the user has played
		int numUsed;
		try {
			numUsed = statFile.nextInt();
		} catch (Exception e) {
			numUsed = 0;
		}

		Scanner infile = new Scanner(new FileReader("WordList.txt"));
		// get a random number
		Random random = new Random();
		int wordBuffer = random.nextInt(2310);

		if (numUsed < 2309) {
			while (true) {
				// go forward in the txt file the buffer amount
				// and select that word, essentially randomly accessing a word
				for (int i = 0; i < wordBuffer; ++i)
					infile.next();

				boolean good = true;
				word = infile.next();
				// checks if the random word has been used already by checking it against the
				// UsedWords file
				while (usedFile.hasNext()) {
					if (word.equals(usedFile.next().toLowerCase())) {
						good = false;
						break;
					}
				}

				// if the word has been used, both files are closed and the process is repeated
				// until a new word has been selected
				if (!good) {
					infile.close();
					usedFile.close();

					usedFile = new Scanner(new FileReader("UsedWords.txt"));
					numUsed = usedFile.nextInt();

					infile = new Scanner(new FileReader("WordList.txt"));
					random = new Random();
					wordBuffer = random.nextInt(2310);
				} else {
					break;
				}
			}
		}
		infile.close();
		usedFile.close();
		statFile.close();

		// make the word upper case so comparing is consistent
		word = word.toUpperCase();
	}

	// set the stats to what the previous game stats updated to
	private void statsInitialize() {
		leftStats.setVisible(false);
		rightStats.setVisible(false);
		try {
			Scanner statInfile = new Scanner(new File("statLog.txt"));
			int playedIn, winPercentIn, streakIn, maxStreakIn;
			playedIn = statInfile.nextInt();
			winPercentIn = (int) (statInfile.nextInt() * 1.0 / playedIn * 100);
			statInfile.next();
			streakIn = statInfile.nextInt();
			maxStreakIn = statInfile.nextInt();

			played.setText("Played " + playedIn);
			winPercent.setText("Win % " + winPercentIn);
			streak.setText("Current Streak: " + streakIn);
			maxStreak.setText("Max Streak: " + maxStreakIn);
			statInfile.close();
			// if the player has never played before (does not have stats)
			// the stats are automatically set below
		} catch (Exception e) {
			played.setText("Played 0");
			winPercent.setText("Win % 100");
			streak.setText("Current Streak: 0");
			maxStreak.setText("Max Streak: 0");
		}
	}

	// logic for changing the stats depending on a win or loss
	private void updateStats(boolean won) throws IOException {
		// FileWriter is used so all previous moves/used words are kept track of
		// (not overwritten)
		FileWriter movesOutfile = new FileWriter("moveLog.txt", true);
		FileWriter wordsOutfile = new FileWriter("UsedWords.txt", true);

		// essentially, the stats to be written to the statLog are updated from the
		// Labels corresponding to played, winPercent, streak, and maxStreak

		// String manipulation is done to parse the number value of each Stat Label so
		// we can use arithmetic on it
		int numPlayed, wins, losses, streakOut, maxStreakOut;
		numPlayed = Integer.parseInt(played.getText().split(" ")[1]) + 1;
		// if the player has played at least one time, wins is updated through
		// incrementing the wins value in the statLog
		if (numPlayed > 1) {
			Scanner statInfile = new Scanner(new File("statLog.txt"));
			statInfile.next();
			wins = statInfile.nextInt();
			if (won)
				wins++;
			statInfile.close();
			// otherwise if its the first time, their wins is set to 1 or 0 depending on if
			// they won or not
		} else {
			wins = won ? 1 : 0;
		}
		losses = numPlayed - wins;
		// streak either increments or resets to 0 depending on the outcome of the game
		streakOut = won ? Integer.parseInt(streak.getText().split(" ")[2]) + 1 : 0;
		// max streak only updates if the current streak is higher than the current max
		// streak
		maxStreakOut = streakOut > Integer.parseInt(maxStreak.getText().split(" ")[2])
				? Integer.parseInt(maxStreak.getText().split(" ")[2]) + 1
				: Integer.parseInt(maxStreak.getText().split(" ")[2]);

		// PrintWriter is used because we want to overwrite the current stat values (as
		// the new ones have been recalculated)
		PrintWriter statOutfile = new PrintWriter("statLog.txt");
		// the statLog is formatted as follows:
		// # of games played, # of wins, # of losses, the current streak (wins in a
		// row), and then the max streak
		statOutfile.print(numPlayed + " " + wins + " " + losses + " " + streakOut + " " + maxStreakOut);

		// iterates through the users guesses and writes them to the "moveLog" file
		for (Integer key : board.keySet()) {
			for (Label l : board.get(key)) {
				// if the player guessed correctly before filling in all the game board, "-" is
				// set in place of empty spaces
				if (l.getText() == "")
					movesOutfile.write("-" + " ");
				else
					movesOutfile.write(l.getText() + " ");
			}
			movesOutfile.write("\n");
		}
		movesOutfile.write("\n");

		// writes the current word to the "UsedWords" file so that it cannot be used
		// again
		wordsOutfile.write(word + "\n");

		statOutfile.close();
		movesOutfile.close();
		wordsOutfile.close();

		// updates the labels so the new stats show when the game ends
		statsInitialize();
		leftStats.setVisible(true);
		rightStats.setVisible(true);
	}

	// gets the index in the ArrayList of the current label
	private int currentIndex() {
		return board.get(row).indexOf(current);
	}

	// essentially like decrementing an iterator in the Map
	private Label getPrevious() {
		if (currentIndex() - 1 >= 0)
			return board.get(row).get(currentIndex() - 1);
		return current;
	}

	// essentially like incrementing an iterator in the Map
	private Label getNext() {
		if (currentIndex() + 1 < board.get(row).size())
			return board.get(row).get(currentIndex() + 1);
		return current;
	}

	// checks the users guess to see whether they got it correct or not
	private boolean checkRow() throws IOException {
		boolean wins = true;

		// puts the users guess in a variable for convenience
		String checkWord = "";
		for (int i = 0; i < 5; ++i) {
			checkWord += board.get(row).get(i).getText();
		}

		Scanner wordList = new Scanner(new FileReader("WordList.txt"));
		boolean good = false;
		// checks whether the user's guess is in the word list
		while (wordList.hasNext()) {
			if (checkWord.equals(wordList.next().toUpperCase())) {
				good = true;
				break;
			}
		}
		wordList.close();

		// if the guess is not in the word list, the user is given a warning and they
		// have to guess again
		if (!good) {
			warning.setText("Not in word list");
			warning.setVisible(true);
			pause.play();
			return false;
		}

		// if the guess is in the word list, each character is checked
		for (int i = 0; i < 5; ++i) {
			// if the letter is in the correct place, it turns green as well as the button
			// corresponding to that letter
			if (checkWord.charAt(i) == word.charAt(i)) {
				board.get(row).get(i).setStyle("-fx-background-color: #34a851; -fx-cursor: hand");

				for (Button b : keyboard) {
					if (b.getText().equals(board.get(row).get(i).getText()))
						b.setStyle("-fx-background-color: #34a851; -fx-cursor: hand");
				}
				// if the letter is in the word, but not the right place, it turns yellow as
				// well as the button
			} else if (word.indexOf(checkWord.charAt(i)) != -1) {
				board.get(row).get(i).setStyle("-fx-background-color: #f7d734; -fx-cursor: hand");
				wins = false;

				// side note: the button does not turn yellow if it is already green
				for (Button b : keyboard) {
					if (b.getText().equals(checkWord.charAt(i) + "") && !b.getStyle().contains("34a851"))
						b.setStyle("-fx-background-color: #f7d734; -fx-cursor: hand");
				}
				// if the letter is not in the word, it turns grey and the corresponding button
				// is disabled
			} else {
				board.get(row).get(i).setStyle("-fx-background-color: #4c4d4c; -fx-cursor: hand");
				wins = false;

				for (Button b : keyboard) {
					if (b.getText().equals(checkWord.charAt(i) + ""))
						b.setDisable(true);
				}
			}

		}

		// if the user still has guesses left, the "current" label is updated to the
		// first column of the next row
		if (row < 6) {
			row += 1;
			current = board.get(row).get(0);
			// otherwise, if the guess was incorrect then the user loses and the stats are
			// updates, the user then can only reset to play again
		} else if (!wins) {
//			System.out.println("u lose");
			warning.setText("You lose, the word was:\n" + word);
			warning.setVisible(true);
			gameOver = true;
			updateStats(wins);
		}
		return wins;
	}

}