import java.util.ArrayList;

public class TicTacToeAI{

    public ArrayList<MoveList> memory;	// The ArrayList that holds the MoveLists for every game the AI has ever seen
    public ArrayList<Integer> recentMoveInts;    // Remembers which move was made each turn
    public ArrayList<MoveList> recentMoveLists;  // Remembers which moveLists were used this game
    public ArrayList<Integer> recentMoveRotations;  // 

    
    public TicTacToeAI(){
        memory = new ArrayList<MoveList>();
        recentMoveInts = new ArrayList<Integer>();
        recentMoveLists = new ArrayList<MoveList>();
        recentMoveRotations = new ArrayList<Integer>();
    }

   /**
    * Returns and int (0 through 8) representing the location the AI has decided to move
    * 
    * @param _gameState A 9-character string representing the tic-tac-toe board
    * @return an int between 0 and 8, inclusive
    */
    public int makeMove(String _gameState) {
        // Search memory for the same gameState
        // dolan will remember where the applicable moveList is
    	int dolan;
        
        if (memory.size() < 1) {
        	// -1 means that the applicable moveList does not exist, which it can't because memory is empty
            dolan = -1;
            
        } else {
            dolan = -1;
            // We'll assume that the moveList isn't in memory, but if we find it, we'll tell dolan
            for (int i = 0; i < memory.size(); i++) {
                int temp = memory.get(i).compareGameState(_gameState);
                if (temp != 0) {
                	// Oh, we found it!
                    dolan = i;
                    recentMoveRotations.add(temp);
                }
            }
        }
        
        // There is a similar moveList somewhere in memory
        if (dolan >= 0) {
            int sam = memory.get(dolan).getMove();
            recentMoveInts.add(sam);
            recentMoveLists.add(memory.get(dolan)); 
            return rotate(sam, recentMoveRotations.get(recentMoveRotations.size()-1));
        
        } else {  // There was not a similar moveList somewhere in memory
            // Add the move to Memory
        	memory.add(new MoveList(_gameState));
            recentMoveLists.add(new MoveList(_gameState));
            recentMoveInts.add(memory.get(memory.size()-1).getMove());
            // Then return the move most recently added to memory
            return recentMoveInts.get(recentMoveInts.size()-1);
        }
    }

   /**
    * Returns the given move after rotating the board
    *
    * @param move the original move
    * @param rotation what rotation/reflection to apply
    * @return an int between 0 and 8 representing the move on a rotated board
    */
    public static int rotate(int move, int rotation){
        if (rotation == 1){
            return move;
        } else if (rotation == MoveList.QUARTER_TURN){
            return Integer.valueOf("630741852".substring(move, move+1));
        } else if (rotation == MoveList.HALF_TURN){
            return Integer.valueOf("876543210".substring(move, move+1));
        } else if (rotation == MoveList.THREE_QUARTER_TURN){
            return Integer.valueOf("258147036".substring(move, move+1));
        } else if (rotation == MoveList.HORIZONTAL_REFLECTION){
            return Integer.valueOf("210543876".substring(move, move+1));
        } else if (rotation == MoveList.VERTICAL_REFLECTION){
            return Integer.valueOf("678345012".substring(move, move+1));
        } else if (rotation == MoveList.UP_DIAGONAL_REFLECTION){
            return Integer.valueOf("852741630".substring(move, move+1));
        } else if (rotation == MoveList.DOWN_DIAGONAL_REFLECTION){
            return Integer.valueOf("036147258".substring(move, move+1));
        }
        return -1;
    }
    

    // This method makes sure the AI learns from previous games.  A gameResult > 1 increases the probability
    // that the AI will make similar moves in the future, while a gameResult < 1 decreases the probability that
    // the AI will make similar moves in the future.
    public void learn(double gameResult){
    	
        for (int i = 0; i < recentMoveLists.size(); i++)
        {	// Iterate through our list of moveLists used this game
            for (int k = 0; k < memory.size(); k++)
            {   // Search memory to find it
                if (memory.get(k).gameState.equals(recentMoveLists.get(i).gameState))
                {
                    if (i == recentMoveLists.size()-1)
                    {   // If this was the last move of the game, it probably directly affected whether you won or lost 
                        memory.get(k).probabilityChange(recentMoveInts.get(i), gameResult*gameResult);
                    } 
                    else if (i == 0) 
                    {   // The first move of the game probably had less effect on whether you won or lost
                        memory.get(k).probabilityChange(recentMoveInts.get(i), Math.sqrt(gameResult));
                    } 
                    else 
                    {   // Otherwise, it slightly affected whether you won or lost
                        memory.get(k).probabilityChange(recentMoveInts.get(i), gameResult);
                    }
                }
            }
        }
        recentMoveLists.clear();
        recentMoveInts.clear();
    }
}
