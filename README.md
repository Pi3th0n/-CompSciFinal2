-CompSciFinal2
==============

Our Final Project for our second trimester of AP Comp Sci.  Tic-Tac-Toe AI that learns from every game!

Proposed Algorithm:

AI identifies current board state
    Stores the game state in a string of 9 characters, where 'X' represents an X, 'O' represents an O, and ' ' is an empty space.
    
     0 | 1 | 2
    ---+---+---
     3 | 4 | 5
    ---+---+---
     6 | 7 | 8

    Thus, the following board:

     X | O | 
    ---+---+---
       | O |  
    ---+---+---
       |   | X 
    
    would be represented by the string "XO  O   X"


AI looks up / creates a decision matrix based on this board state
	Something like:
	| .4 .0 .2 |
	| .2 .0 .1 |
	| .0 .1 .0 |
	where each cell is the probability of moving there
	The AI should already have one of these for board states it's seen before
	for new board states, the AI will create one with an equal probability of moving in any open space

AI keeps a list of Decision matrices it used and where it decided to move

At the end of the game:
	If AI won game:
		Increase the probability of making the same moves
	If AI lost game:
		Decrease the probability of making the same moves
	else:
		slightly increase probability?
		
		
