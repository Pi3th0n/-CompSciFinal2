import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.util.ArrayList;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TicTacToeGui implements Runnable{

	JMenuBar firstHill;  // Our JMenuBar
	ArrayList<JButton> buttList;  // This ArrayList holds the 9 buttons that the game is played on
	int turn; // What turn is it?
	JFrame chase; // Our JFrame
	int gameType; // Controls wether the game is pvp or pvAI
	TicTacToeAI Xander;  // Xander is our X bot
	TicTacToeAI Olivia;  // Olivia is our O bot
	TicTacToeAI practiceBot; // This guy is the dummy they practice against

	public TicTacToeGui()
	{  // Initializing Variables
		firstHill = new JMenuBar();
		buttList = new ArrayList<JButton>();
		chase = new JFrame();
		turn = 0;
		gameType = 0;
		Xander = new TicTacToeAI();
		Olivia = new TicTacToeAI();
		practiceBot = new TicTacToeAI();
		for( int i = 0; i < 9; i++)
		{ // Making all 9 buttons, initially disabled
			JButton butt = new JButton(" ");
			butt.setEnabled(false);
			butt.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Object o = e.getSource();
					if(o instanceof JButton)
					{
						JButton sourceButton = (JButton)e.getSource();
						buttonClicked(sourceButton);
					}
				}
			});
			buttList.add(butt);
		}
	}

	public static void main(String[] args)
	{
			TicTacToeGui stephen = new TicTacToeGui();
			javax.swing.SwingUtilities.invokeLater(stephen);
	}

	public void makeFileMenu()
	{
        JMenu menu = new JMenu("Menu");
        
        JMenuItem quitMI = new JMenuItem("Quit");
        quitMI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        
        JMenuItem trainAI = new JMenuItem("Speed-train the AI");
        trainAI.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
               trainAI();
            }
        });       
        
        JMenuItem newPlayerMI = new JMenuItem("New Game vs. Player");
        newPlayerMI.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
            newGame(1);
          }
        });
        
        JMenuItem newAIXMI = new JMenuItem("New Game vs. AI 'Olivia'");        
        newAIXMI.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
        	  newGame(2);
           }
        });
        
        JMenuItem newAIOMI = new JMenuItem("New Game vs. AI 'Xander'");        
        newAIOMI.addActionListener(new ActionListener(){
          public void actionPerformed(ActionEvent e){
        	  newGame(3);
           }
        });
        menu.add(newPlayerMI);
        menu.add(newAIXMI);
        menu.add(newAIOMI);
        menu.add(trainAI);
        menu.add(quitMI);
        firstHill.add(menu);
	}

	public void run(){
		chase.setSize(640,480);
		chase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chase.getContentPane().setLayout(new GridLayout(3, 3));
		makeFileMenu();
		chase.setJMenuBar(firstHill);
		for(int i = 0; i < buttList.size(); i++)
		{
			chase.getContentPane().add(buttList.get(i));
		}

		chase.setVisible(true);

	}

	public void buttonClicked(JButton jbutt)
	{
		// We don't want buttons to do anything while the AI is training, or when a game hasn't started
		if (gameType == 4 || gameType == 0)
		{
			return;
		}
		
		// On even turns, X goes
		if(turn%2 == 0)
		{
			jbutt.setText("X");
			jbutt.setEnabled(false);
		}
		
		// On odd turns, O goes
		else if(turn%2 == 1)
		{
			jbutt.setText("O");
			jbutt.setEnabled(false);
		}
		
		// Check for win (Since the player just moved, we know that if someone won, it must have been the player)
		int winner = checkForWin();
		
		// Initialize response, and make sure it's not YES_OPTION or NO_OPTION
		int response = 69;
		
		// O wins
		if(winner == 1)
		{
			response = JOptionPane.showConfirmDialog(chase, "O has Won! Would you like to play again?", 
					"O has won!", JOptionPane.YES_NO_OPTION);
			// If this is a bot game, we want the bots to learn from this loss
			if (gameType == 2 || gameType == 3) { Xander.learn(0.5); }
		}
		
		// X wins
		if(winner == -1)
		{
			response = JOptionPane.showConfirmDialog(chase, "X has Won! Would you like to play again?", 
					"X has won!", JOptionPane.YES_NO_OPTION);
			// If this is a bot game, we want the bots to learn from this loss
			if (gameType ==2 || gameType == 3) { Olivia.learn(0.5); }
		}
		
		// Tie
		if(winner == 2)
		{
			response = JOptionPane.showConfirmDialog(chase, "It is a tie! Would you like to play again?", 
					"You have tied!", JOptionPane.YES_NO_OPTION);
			// Draws are better than losing, so we'll have the appropriate bot learn, if this was a bot game.
			if (gameType == 2) { Olivia.learn(1.5); }
			else if (gameType == 3) { Xander.learn(1.5); }
		}
		
		// Play again?
		if(response == JOptionPane.YES_OPTION)
		{
			newGame(gameType);
			return;
		}
		
		if(response == JOptionPane.NO_OPTION)
		{
			System.exit(1); 
		}
		turn++;
		
		// If this is a pvp game, do nothing else
		// If this was a bot game, it is now the bot's turn
		if(gameType == 2 || gameType == 3)
		{
			
			if (turn%2 == 0)
			{
				// Xander is our X bot, so he'll make moves for X
				jbutt = buttList.get(Xander.makeMove(arrayToString()));
				jbutt.setText("X");
				jbutt.setEnabled(false);
			}
			else if (turn%2 == 1)
			{
				// Olivia is our O bot, so she'll make moves for O
				jbutt = buttList.get(Olivia.makeMove(arrayToString()));
				jbutt.setText("O");
				jbutt.setEnabled(false);
			}
			turn++;
			// Now we'll check for win again, but now we know that if someone won, it was a bot
			winner = checkForWin();
			response = 69;
			
			// O Wins!
			if(winner == 1)
			{
				response = JOptionPane.showConfirmDialog(chase, "O has Won! Would you like to play again?", 
						"O has won!", JOptionPane.YES_NO_OPTION);
				// Winning is good!
				Olivia.learn(10);
			}
			
			// X Wins!
			if(winner == -1)
			{
				response = JOptionPane.showConfirmDialog(chase, "X has Won! Would you like to play again?", 
						"X has won!", JOptionPane.YES_NO_OPTION);
				// Winning is good!
				Xander.learn(10);
			}

			// Tie
			if(winner == 2)
			{
				response = JOptionPane.showConfirmDialog(chase, "It is a tie! Would you like to play again?", 
						"You have tied!", JOptionPane.YES_NO_OPTION);
				Xander.learn(1.0);
				Olivia.learn(1.0);
			}
			
			// Play again?
			if(response == JOptionPane.YES_OPTION)
			{
				newGame(gameType);
				return;
			}
			
			if(response == JOptionPane.NO_OPTION)
			{
				System.exit(1); 
			}
		}
	}
 
	// This method basically just brute force checks every win combination to see if someone won
	// Returns -1 if X wins, 1 if O wins, 2 for a tie and 0 if the game is not yet over.
	public int checkForWin()
	{
		String isItWinning = arrayToString();
		if (isItWinning.length() != 9){
			throw new IllegalArgumentException("String isItWinning is of incorrect length");
		} else if (!isItWinning.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
            throw new IllegalArgumentException("String isItWinning contains invalid characters");
        }

        if (isItWinning.charAt(0) == isItWinning.charAt(1) && isItWinning.charAt(0) == isItWinning.charAt(2)) {
            if (isItWinning.charAt(0) == 'X'){
                return -1;
            } else if (isItWinning.charAt(0) == 'O') {
                return 1;
            }

        } else if (isItWinning.charAt(3) == isItWinning.charAt(4) && isItWinning.charAt(3) == isItWinning.charAt(5)){
            if (isItWinning.charAt(3) == 'X'){
                return -1;
            } else if (isItWinning.charAt(3) == 'O') {
                return 1;
            }
        
        } else if (isItWinning.charAt(6) == isItWinning.charAt(7) && isItWinning.charAt(6) == isItWinning.charAt(8)){
            if (isItWinning.charAt(6) == 'X'){
                return -1;
            } else if (isItWinning.charAt(6) == 'O') {
                return 1;
            }
        
        } else if (isItWinning.charAt(0) == isItWinning.charAt(3) && isItWinning.charAt(0) == isItWinning.charAt(6)){
            if (isItWinning.charAt(0) == 'X'){
                return -1;
            } else if (isItWinning.charAt(0) == 'O') {
                return 1;
            }

        } else if (isItWinning.charAt(1) == isItWinning.charAt(4) && isItWinning.charAt(1) == isItWinning.charAt(7)){
            if (isItWinning.charAt(1) == 'X'){
                return -1;
            } else if (isItWinning.charAt(1) == 'O') {
                return 1;
            }

        } else if (isItWinning.charAt(2) == isItWinning.charAt(5) && isItWinning.charAt(2) == isItWinning.charAt(8)){
            if (isItWinning.charAt(2) == 'X'){
                return -1;
            } else if (isItWinning.charAt(2) == 'O') {
                return 1;
            }

        } else if (isItWinning.charAt(0) == isItWinning.charAt(4) && isItWinning.charAt(0) == isItWinning.charAt(8)){
            if (isItWinning.charAt(0) == 'X'){
                return -1;
            } else if (isItWinning.charAt(0) == 'O') {
                return 1;
            }

        } else if (isItWinning.charAt(2) == isItWinning.charAt(4) && isItWinning.charAt(2) == isItWinning.charAt(6)){
            if (isItWinning.charAt(2) == 'X'){
                return -1;
            } else if (isItWinning.charAt(2) == 'O') {
                return 1;
            }

        } else if (isItWinning.replaceAll(" ", "").length() == 9) {
            return 2;
        }
        return 0;
    }
 
	public void newGame(int n)
	{
		// Clear the board and enable all the buttons
		for(int i = 0; i < buttList.size(); i++)
        {
			turn = 0;
			buttList.get(i).setText(" ");
			buttList.get(i).setEnabled(true);
        }
		
		// Set the gameType to whatever it's supposed to be
		gameType = n;
		
		// GameType three is player vs AI but the player goes second.  We'll go ahead and make the AI's first move here
		if (n == 3)
		{
			JButton jbutt = buttList.get(Xander.makeMove(arrayToString()));
			jbutt.setText("X");
			jbutt.setEnabled(false);
			turn++;
		}
	}
 
	// This method turns the board into a string that the AI can use
	public String arrayToString()
	{
		
		String winning = new String("");
		for(int i = 0; i < buttList.size(); i++)
		{
			winning += (buttList.get(i).getText());
		}
		return winning;
	}
 
	// This method runs 150 to help the AI learn to play TicTacToe
	public void trainAI()
	{
		newGame(4);
		Xander.recentMoveInts.clear();
		Xander.recentMoveLists.clear();
		Xander.recentMoveRotations.clear();
		Olivia.recentMoveInts.clear();
		Olivia.recentMoveLists.clear();
		Olivia.recentMoveRotations.clear();
		
		for (int i = 0; i < 50; i++)
		{
			// For these 50 games, We'll train Xander
			boolean gameOver = false;
			while (!gameOver)
			{
								
				// Xander's Turn
				JButton jbutt = buttList.get(Xander.makeMove(arrayToString()));
				jbutt.setText("X");
				jbutt.setEnabled(false);
				turn++;
			
				int winner = checkForWin();
				
				// X Wins!
				if(winner == -1)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(10.0);
				}
				
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(1.5);
				}
			
				// Practice Bot's turn
				jbutt = buttList.get(practiceBot.makeMove(arrayToString()));
				jbutt.setText("O");
				jbutt.setEnabled(false);
				turn++;
			
				winner = checkForWin();
				// O wins
				if(winner == 1)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(0.05);
				}
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(1.5);
				}
			}
		}
		for (int i = 0; i < 50; i++)
		{
			// For these 50 games, we'll train Olivia
			
			boolean gameOver = false;
			while (!gameOver)
			{
				
				// Practice Bot's turn				
				JButton	jbutt = buttList.get(practiceBot.makeMove(arrayToString()));
				jbutt.setText("X");
				jbutt.setEnabled(false);
				turn++;
			
				int winner = checkForWin();
				
				// X wins!
				if(winner == -1)
				{
					gameOver = true;
					newGame(4);
					Olivia.learn(0.05);
				}
				
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(4);
					Olivia.learn(1.5);
				}
				
				// Olivia's Turn
				jbutt = buttList.get(Olivia.makeMove(arrayToString()));
				jbutt.setText("O");
				jbutt.setEnabled(false);
				turn++;
			
				winner = checkForWin();
				
				// O wins!
				if(winner == 1)
				{
					gameOver = true;
					newGame(3);
					Olivia.learn(10.0);
				}
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(3);
					Olivia.learn(1.5);
				}
			
			}
		}
		for (int i = 0; i < 50; i++)
		{
			// For these 50 games, we'll have Xander and Olivia play against each other
			
			boolean gameOver = false;
			while (!gameOver)
			{
				
				// Xander's turn				
				JButton	jbutt = buttList.get(Xander.makeMove(arrayToString()));
				jbutt.setText("X");
				jbutt.setEnabled(false);
				turn++;
			
				int winner = checkForWin();
				
				// X wins!
				if(winner == -1)
				{
					gameOver = true;
					newGame(4);
					Xander.learn(10.0);
					Olivia.learn(0.05);
				}
				
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(4);
					Xander.learn(1.5);
					Olivia.learn(1.5);
				}
				
				// Olivia's Turn
				jbutt = buttList.get(Olivia.makeMove(arrayToString()));
				jbutt.setText("O");
				jbutt.setEnabled(false);
				turn++;
			
				winner = checkForWin();
				
				// O wins!
				if(winner == 1)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(0.05);
					Olivia.learn(10.0);
				}
				// Tie
				if(winner == 2)
				{
					gameOver = true;
					newGame(3);
					Xander.learn(1.5);
					Olivia.learn(1.5);
				}
			
			}
		}
		newGame(0);
		JOptionPane.showMessageDialog(chase,"Training Complete");
	}
}
