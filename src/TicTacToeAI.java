import java.util.ArrayList;

public class TicTacToeAI{

    public ArrayList<MoveList> memory;
    public ArrayList<Integer> recentMoveInts;
    public ArrayList<MoveList> recentMoveLists;

    public TicTacToeAI(){
        memory = new ArrayList<MoveList>();
        recentMoveInts = new ArrayList<Integer>();
        recentMoveLists = new ArrayList<MoveList>();
    }

    public int makeMove(String _gamestate) {
        int dolan;
        if (memory.size() < 1) {
            dolan = -1;
        } else {
            dolan = -1;
            for (int i = 0; i < memory.size(); i++) {
                if (memory.get(i).gamestate.equals(_gamestate)) {
                    dolan = i;
                }
            }
        }

        if (dolan >= 0) {
            recentMoveInts.add(memory.get(dolan).getMove());
            recentMoveLists.add(memory.get(dolan)); 
        } else {
            memory.add(new MoveList(_gamestate));
            recentMoveInts.add(memory.get(memory.size()-1).getMove());
        }
        return recentMoveInts.get(recentMoveInts.size()-1);
    }

    public void learn(double gameResult){
        System.out.println("Learning...");
        for (int i = 0; i < recentMoveLists.size(); i++){
            for (int k = 0; k < memory.size(); k++){
                if (memory.get(k).gamestate.equals(recentMoveLists.get(i).gamestate)){
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
