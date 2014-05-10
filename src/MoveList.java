import java.util.Random;

public class MoveList {
	
	public final String gamestate;
	public int numOfLegalMoves;
	public double[] moveList;
	
	public MoveList(String _gamestate) throws IllegalArgumentException{
		// Check to make sure that the given string is a valid game state
		if (_gamestate.length() != 9){
			throw new IllegalArgumentException("String is of incorrect length");
		// The line below makes sure that only X's, O's, and spaces are in the string
		} else if (!_gamestate.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
			throw new IllegalArgumentException("String contains invalid characters");
		}
		
		// Initialize variables
		gamestate = _gamestate;
		numOfLegalMoves = gamestate.replaceAll("X","").replaceAll("O", "").length();
		moveList = new double[9];
		
		// Fill moveList with values in every legal move space
		for (int i = 0; i < 9; i++){
			// If there's not an X or an O, the move is legal
			if (gamestate.charAt(i) != 'X' && gamestate.charAt(i) != 'O'){
				// Fills legal moves with values equal to the number of legal moves
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
		// This method chooses
		double matrixSum = arraySum(moveList);
		double choice = new Random().nextDouble()*matrixSum;
		for (int i = 0; i < moveList.length; i++){
			choice -= moveList[i];
			if (choice < 0){
				return i;
			}
		}
		return -1;
	}
	
	public String toString(){
		return gamestate + ":" + moveList[0] + "," + moveList[1] + "," + moveList[2] + "," + moveList[3] 
                + "," + moveList[4] + "," + moveList[5] + "," + moveList[6] + "," + moveList[7] + "," + moveList[8];
	}
	
}
