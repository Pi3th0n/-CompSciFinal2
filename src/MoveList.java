import java.util.Random;

public class MoveList {
	
	public final String gameState;
	public double[] moveList;

   /**
    * Creates a new MoveList with only one posible move.
    * This constructor is only useful for educating an AI in a scenario
    * where you already know the optimal move.
    *
    * @param _gamestate the gamestate the MoveList is based on.
    * @param optimalMove the optimal move for this particular gamestate.
    */
    public MoveList(String _gameState, int optimalMove) throws IllegalArgumentException{
		// Check to make sure that the given string is a valid game state
		if (_gameState.length() != 9){
			throw new IllegalArgumentException("String is of incorrect length");
		// The line below makes sure that only X's, O's, and spaces are in the string
		} else if (!_gameState.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
			throw new IllegalArgumentException("String contains invalid characters");
		}

        // Check to make sure that the given move is legal.  First, is it on the board?
        if (optimalMove > 8 || optimalMove < 0){
            throw new IllegalArgumentException("Invalid move (out of bounds): " + optimalMove);
        // Second, is the space acutally empty?
        } else if (_gameState.charAt(optimalMove) != ' ' ){
            throw new IllegalArgumentException("Invalid move (space occuppied): " + optimalMove);
        }
		
		// Initialize variables
		gameState = _gameState;
		moveList = new double[9];
		
		// This time, we want to fill moveList with 0's except for the location of the optimalMove
		for (int i = 0; i < 9; i++){
            if (i == optimalMove){
				// The optimal move should be represented with a 1
				moveList[i] = 1;
			} else {
				// All other moves should be represented with a 0
				moveList[i] = 0;
			}
		}
	}
	

   /**
    * Creates a new, equally distributed MoveList based on the given gamestate.
    *
    *
    * @param _gamestate the gamestate the MoveList is based on.
    * @throws IllegalArgumentException if _gamestate isn't a possible tic-tac-toe gamestate.
    */
	public MoveList(String _gameState) throws IllegalArgumentException{
		// Check to make sure that the given string is a valid game state
		if (_gameState.length() != 9){
			throw new IllegalArgumentException("String is of incorrect length");
		// The line below makes sure that only X's, O's, and spaces are in the string
		} else if (!_gameState.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
			throw new IllegalArgumentException("String contains invalid characters");
		}
		
		// Initialize variables
		gameState = _gameState;
		moveList = new double[9];
		
		// Fill moveList with values in every legal move space
		for (int i = 0; i < 9; i++){
			// If there's not an X or an O in this position already, the move is legal
			if (gameState.charAt(i) != 'X' && gameState.charAt(i) != 'O'){
				// Fills in all legal moves with a one
				moveList[i] = 1;
			} else {
				// Fills illegal moves with values of 0
				moveList[i] = 0;
			}
		}
	}

   /**
	* Returns the sum of every number in an array
	* 
	* @param array
	* @return the sum of the array
	*/
	public double arraySum(double[] array){
		double out = 0.0;
		for (int i = 0; i < array.length; i++){
			out += array[i];
		}
		return out;
	}
	
   /**
    * Resets the movelist so that all values are either 1 or 0.
    * 1's where moves are legal, 0 where moves are illegal.
    */
    public void reset(){
		for (int i = 0; i < 9; i++){
			if (gameState.charAt(i) != 'X' && gameState.charAt(i) != 'O'){
				moveList[i] = 1;
			} else {
				moveList[i] = 0;
			}
		}
    }

   /**
    * Changes the value of a number in the moveList, which changes the probability
    * of that move being selected by a future getMove().
    * Specifically, this method multiplies moveList[index] by change.
    *
    * Note that, in order to preserve the functionality of other methods in MoveList,
    * this method will do nothing if moveList[index] is close to Double.MAX_VALUE.
    *
    * @param index the index of the move to be changed
    * @param change the amount by wich the value should be multiplied
    */
    public void probabilityChange(int index, double change){
        if (moveList[index] < Double.MAX_VALUE/20){
        	moveList[index] *= change;
        }
    }
    
   /**
	* Returns a move chosen randomly from the available moves.
    * More likely to pick higher numbers 
	* 
	* @return an int between 0 and 8 (inclusive) representing a move
	*/
	public int getMove(){
        // First, let's pick a number between 0 and the sum of the array
		double matrixSum = arraySum(moveList);
		double choice = new Random().nextDouble()*matrixSum;
        /* Then, we'll iterate through the list, subtracting every double in the 
         * array from the number chosen above. */
		for (int i = 0; i < moveList.length; i++){
			choice -= moveList[i];			
            // Once choice < 0, we'll go with that index
            if (choice <= 0){
				return i;
			}
		}
        // If (somehow) choice never is <= 0, return -1
		return -1;

       /* I'm pretty sure this method is very similar to rolling a d20
        * in DnD and comparing the roll to a table of results */
	}
	
   /**
    * Determines if the input gamestate is a rotation or reflection of the
    * this MoveList's gamestate.
    *
    * @param otherGameState
    * @return an int representing how the given gameState compares to this.gameState
    */
    public int compareGameState(String otherGameState){
		// Check to make sure that the given string is a valid game state
		if (otherGameState.length() != 9){
			throw new IllegalArgumentException("String is of incorrect length");
		// The line below makes sure that only X's, O's, and spaces are in the string
		} else if (!otherGameState.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
			throw new IllegalArgumentException("String contains invalid characters");
		}
        
        if (otherGameState.equals(gameState)){ // Same game state
            return 1;        
        } else if (quarterTurn(otherGameState).equals(gameState)){ // Quarter Turn
            return 2;
        } else if (quarterTurn(quarterTurn(otherGameState)).equals(gameState)){  // Half Turn
            return 3;
        } else if (quarterTurn(quarterTurn(quarterTurn(otherGameState))).equals(gameState)){  // Three Quarter Turn
            return 4;
        } else if (horizReflection(otherGameState).equals(gameState)){  // Horizontal Relfection
            return 5;
        } else if (vertReflection(otherGameState).equals(gameState)){  // Vertical Relfection
            return 6;
        } else if (vertReflection(quarterTurn(otherGameState)).equals(gameState)){ // Diagonal Reflection (across y = x)
            return 7;
        } else if (horizReflection(quarterTurn(otherGameState)).equals(gameState)){ // Diagonal Reflection (across y = -x)
            return 8;
        }
        return 0;

    }

   /**
    * Returns a new string representing the given game state that has been rotated
    * 90 degrees clockwise. 
    *
    * @param _gameState a 9-character string representing the game state to be rotated
    * @return a string representing a 90 degree clockwise turn of the game state
    */
    private static String quarterTurn(String _gameState){
        String out = "";
        out += _gameState.charAt(6);
        out += _gameState.charAt(3);
        out += _gameState.charAt(0);
        out += _gameState.charAt(7);
        out += _gameState.charAt(4);
        out += _gameState.charAt(1);
        out += _gameState.charAt(8);
        out += _gameState.charAt(5);
        out += _gameState.charAt(2);
        return out;
    }

   /** 
    * Returns a new string representing the given game state that has been mirrored
    * across the middle row.
    *
    * @param _gameState a 9-character string representing the game state to be mirrored
    * @return a string representing a vertical reflection of the given game state
    */
    private static String vertReflection(String _gameState){
        String out = "";
        out += _gameState.substring(6, 9);
        out += _gameState.substring(3, 6);
        out += _gameState.substring(0, 3);
        return out;
    }


   /**
    * Returns a new string representing the given game state that has been mirrored
    * across the middle column.
    *
    * @param _gameState a 9-character string representing the game state to be mirrored
    * @return a string representing a horizontal reflection of the given game state
    */
    private static String horizReflection(String _gameState){
        String out = "";
        out += _gameState.charAt(2);
        out += _gameState.charAt(1); 
        out += _gameState.charAt(0);
        out += _gameState.charAt(5);
        out += _gameState.charAt(4);
        out += _gameState.charAt(3);
        out += _gameState.charAt(8);
        out += _gameState.charAt(7);
        out += _gameState.charAt(6);     
        return out;
    }
    
	public String toString(){
		return gameState + ":" + moveList[0] + "," + moveList[1] + "," + moveList[2] + "," + moveList[3] 
                + "," + moveList[4] + "," + moveList[5] + "," + moveList[6] + "," + moveList[7] + "," + moveList[8];
	}

    public static int QUARTER_TURN = 2;
    public static int HALF_TURN = 3;
    public static int THREE_QUARTER_TURN = 4;
    public static int HORIZONTAL_REFLECTION = 5;
    public static int VERTICAL_REFLECTION = 6;
    public static int UP_DIAGONAL_REFLECTION = 7;
    public static int DOWN_DIAGONAL_REFLECTION = 8;
	
}
