# Wordle in JavaFX
Spring 2024 Final Project

# A few basic pieces of information:
DEMO: https://youtu.be/pM6Dn8rXv2Y

## Stat Log:
The player can press the restart button in the top left denotated by the ↺ symbol to restart the game at any time. The restart button only clears the board and selects a new word to for the player to guess. It essentially reinitiallizes the board. This does not affect statisctics whatsoever.

For the statistics, you may press the button located in the top right denotated by the 📶 symbol. This shows you your current statistics at any point in time. The stats also atomatically propogate after completion of the game (whether you win or lose). Stats are stored within the "statLog.txt" file.

The format of the stat log is as follows - {# of games played, # of wins, # of losses, the current streak (wins in a row), and then the max streak}

Stats update automatically after completion of each game, and carry over forever (you cannot clear your stats completely unless you manually do so by erasing data in the files).

## Move Log:
Every time you type using the physical or virtual keyboard the letter appears on the "current" location on the board. The user's guess is only stored in the "shadow" data structure if it is a valid guess (a word in the "WordList" text file. A log of every move you make for every game you play is located in the "moveLog.txt" file.

The format of the move log is as follows - {The letters you guess are separated by spaces and each row is separated by a newline. If you guess the correct word before completely filling out the board, leftover spaces are denoted by a "-"}. Moves are not overwritten, they are appended so every game you ever play is recorded in the file.
Example log of word that is 'NICER':=<br>
| A | D | I | E | U |
| --- | --- | --- | --- | --- |
| H | O | W | D | Y |
| N | I | C | E | R |
| \- | \- | \- | \- | \- |
| \- | \- | \- | \- | \- |
| \- | \- | \- | \- | \- |

## Used Words:
A record of used words are stored in the "UsedWords.txt" file and it makes it so that you can never play and get the same word twice. If the file ever filles up completely with all 2310 words, you have to manually enter the game files and delete all of your statistics for a fresh start. If I had a bit more time I could have polished this up; however, I did not see it as a priority as I highly doubt many will play more than 10 times.

## Shadow Data Structure:
The Collection used in this project was a TreeMap with Integer keys and ArrayLists of Labels as values. This mimics the structure of a two dimensional array with the added benefits of being able to efficiently navigate through the structure. I used a TreeMap rather than a HashMap or any data structure primarily for the convenience of keeping the keys in ascending sorted order. Despite manually initializing the TreeMap in the proper order each time, having the order maintained no matter what is a nice failsafe.

# Creator's Note
This project was made in my sophomore year of college; it was my first time creating a visual interactive program utilizing JavaFX. The use of SceneBuilder made the entire process much easier and more fun to work on, I only had to worry about what the design would look like and easily implemented it using SceneBuilder.

I had been acquainted with Java for a few years by now so creating the logic for the game was quite straightforward, the bulk of my time was spent adding various features and designing a, somewhat, visually appealing experience.

The project required me to:
- Create a working application of Wordle.
- Have a random word selected through Java File IO.
- Use a complex (more complex than a 2D array) data structure for storing letter data.
 - Also store this data for future game use using Java File IO.
-  Ensure the application was visually appealing and user friendly.
- Record and calculate player statistics that could be seen anytime:
 - Wins/losses
 - Number of games played
 - Current streak/max streak

*Copyright (c) 2024 Taylor Smith*
