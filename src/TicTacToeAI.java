import java.util.ArrayList;

public class TicTacToeAI{

    public ArrayList<MoveList> memory;
    public ArrayList<Integer> recentMoveInts;
    public ArrayList<MoveList> recentMoveLists;
    public ArrayList<Integer> recentMoveRotations;

    public TicTacToeAI(){
        memory = new ArrayList<MoveList>();
        recentMoveInts = new ArrayList<Integer>();
        recentMoveLists = new ArrayList<MoveList>();
        recentMoveRotations = new ArrayList<Integer>();
    }

    public int makeMove(String _gameState) {
        // Search memory for the same gameState
        int dolan;
        if (memory.size() < 1) {
            dolan = -1;
        } else {
            dolan = -1;
            for (int i = 0; i < memory.size(); i++) {
                int temp = memory.get(i).compareGameState(_gameState);
                if (temp != 0) {
                    dolan = i;
                    recentMoveRotations.add(temp);
                    if (temp == 1){
//                        System.out.println("I've seen that board before");
                    } else {
//                        System.out.println("I've seen a board similar to that one before");
                    }
                }
            }
        }
        // Add the move to memory
        if (dolan >= 0) {
            int sam = memory.get(dolan).getMove();
            recentMoveInts.add(sam);
            recentMoveLists.add(memory.get(dolan)); 
            return rotate(sam, recentMoveRotations.get(recentMoveRotations.size()-1));
        } else {
//            System.out.println("I've never seen that board before");
            memory.add(new MoveList(_gameState));
            recentMoveLists.add(new MoveList(_gameState));
            recentMoveInts.add(memory.get(memory.size()-1).getMove());
        }
        // Return the move most recently added to memory
        return recentMoveInts.get(recentMoveInts.size()-1);
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
    

    public void learn(double gameResult){
        for (int i = 0; i < recentMoveLists.size(); i++){
            for (int k = 0; k < memory.size(); k++){
                if (memory.get(k).gameState.equals(recentMoveLists.get(i).gameState)){
                    if (i == recentMoveLists.size()-1){
                        memory.get(k).probabilityChange(recentMoveInts.get(i), gameResult*gameResult);
                    } else if (i == 0) {
                        memory.get(k).probabilityChange(recentMoveInts.get(i), Math.sqrt(gameResult));
                        
                    } else {
                        memory.get(k).probabilityChange(recentMoveInts.get(i), gameResult);
                    
                    }
                }
            }
        }
        recentMoveLists.clear();
        recentMoveInts.clear();
    }
}
