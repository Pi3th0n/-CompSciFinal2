import java.util.Random;

public class MoveList {
	
	public final String gamestate;
	public int numOfLegalMoves;
	public int[] moveList;
	
	public MoveList(String _gamestate) throws IllegalArgumentException{
		// Check to make sure that the given string is a valid game state
		if (_gamestate.length() != 9){
			throw new IllegalArgumentException("String is of incorrect length");
		} else if (!_gamestate.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
			throw new IllegalArgumentException("String contains invalid characters");
		}
		
		gamestate = _gamestate;
		numOfLegalMoves = gamestate.replaceAll("X","").replaceAll("O", "").length();
		moveList = new int[9];
		
		for (int i = 0; i < 9; i++){
			if (gamestate.charAt(i) != 'X' && gamestate.charAt(i) != 'O'){
				moveList[i] = gamestate.replaceAll("X", "").replaceAll("O", "").length();
			} else {
				moveList[i] = 0;
			}
		}
	}
	
	public int arraySum(int[] array){
		int out = 0;
		for (int i = 0; i < array.length; i++){
			out += array[i];
		}
		return out;
	}
	
	public int getMove(){
		int matrixSum = arraySum(moveList);
		int choice = new Random().nextInt(matrixSum);
		for (int i = 0; i < moveList.length; i++){
			choice -= moveList[i];
			if (choice < 0){
				return i;
			}
		}
		return -1;
	}
	
	public String toString(){
		return gamestate + ":" + moveList[0] + moveList[1] + moveList[2] + moveList[3] + moveList[4] + moveList[5]
				 + moveList[6] + moveList[7] + moveList[8];
	}
	
	public static void main(String[] args){
		MoveList test = new MoveList("XX O     ");
		System.out.println(test);
		System.out.println(test.getMove());
	}
}
