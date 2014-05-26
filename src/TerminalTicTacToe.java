import java.util.ArrayList;

public class TerminalTicTacToe{

    public static void main(String[] args){
        TicTacToeAI Alice = new TicTacToeAI();
        TicTacToeAI Bob = new TicTacToeAI();
        TicTacToeAI Charlie = new TicTacToeAI();
        TicTacToeAI David = new TicTacToeAI();
        String gameString = "         ";
        boolean gameOver = false;
        boolean done = false;  
        int k = 0;
        
        while (!done) {
            char humanPlayer = 'Q';
            while (humanPlayer == 'Q'){
                String input = System.console().readLine("X or O?");
                if (input.charAt(0) == 'X'){
                    humanPlayer = 'X';
                } else if (input.charAt(0) == 'O'){
                    humanPlayer = 'O';
                }
            }
        
            if (humanPlayer == 'O'){
                gameOver = false;
                gameString = "         ";
                while (!gameOver){
                    if (gameString.replaceAll("X", "").replaceAll("O", "").length() % 2 == 1 ){
                        gameString = updateGameString(gameString, Alice.makeMove(gameString), 'X');
                        System.out.println(toGameBoard(gameString));
                    } else {
                        try{
                            gameString = updateGameString(gameString, new Integer(System.console().readLine("Make a move (0 through 8):")), 'O');
                            System.out.println(toGameBoard(gameString));
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Invalid Move");
                        }
                    }
    
                    double temp = checkForWin(gameString);
                    if (temp != 0.0 ){
                        gameOver = true;
                        if (temp == 5){
                            System.out.println("You Lose!");
                            k = 0;
                        } else if (temp == 0.05) {
                            System.out.println("You Win!");
                            k = 100;
                        } else {
                            System.out.println("Tie");
                            k = 0;
                        }
                        Alice.learn(temp*temp);
                    }
                }
            } else {
                gameOver = false;
                gameString = "         ";
                while (!gameOver){
                    if (gameString.replaceAll("X", "").replaceAll("O", "").length() % 2 == 1 ){
                        try{
                            gameString = updateGameString(gameString, new Integer(System.console().readLine("Make a move (0 through 8):")), 'X');
                            System.out.println(toGameBoard(gameString));
                        } catch (IllegalArgumentException ex) {
                            System.out.println("Invalid Move");
                        }
                    } else {
                        gameString = updateGameString(gameString, Bob.makeMove(gameString), 'O');
                        System.out.println(toGameBoard(gameString));
                    }   

                    double temp = checkForWin(gameString);
                    if (temp != 0.0 ){
                        gameOver = true;
                        if (temp == 5){
                            System.out.println("You Win!");
                            k = 100;
                            Bob.learn(0.05);
                        } else if (temp == 0.05){
                            System.out.println("You Lose.");
                            k = 0;
                            Bob.learn(5);
                        } else {
                            System.out.println("Tie.");
                            k = 0;
                            Bob.learn(2);
                        }
                    }
                }
                

            
            }
            for (int i = 0; i < k; i++){        
                gameOver = false;
                gameString = "         ";
                while (!gameOver){
                    if (gameString.replaceAll("X", "").replaceAll("O", "").length() % 2 == 1 ){
                        gameString = updateGameString(gameString, Alice.makeMove(gameString), 'X');
                    } else {
                        gameString = updateGameString(gameString, Charlie.makeMove(gameString), 'O');
                    }

                    double temp = checkForWin(gameString);
                    if (temp != 0.0 ){
                        gameOver = true;
                        Alice.learn(temp);
                    }      
                }
                gameOver = false;
                gameString = "         ";
                while (!gameOver){
                    if (gameString.replaceAll("X", "").replaceAll("O", "").length() % 2 == 1 ){
                        gameString = updateGameString(gameString, Charlie.makeMove(gameString), 'X');
                    } else {
                        gameString = updateGameString(gameString, Bob.makeMove(gameString), 'O');
                    }

                    double temp = checkForWin(gameString);
                    if (temp != 0.0 ){
                        gameOver = true;
                        if (temp == 5){
                            Bob.learn(0.05);
                        } else if (temp == 0.05){
                            Bob.learn(5);
                        } else {
                            Bob.learn(2);
                        }
                    }      
                }
                gameOver = false;
                gameString = "         ";
                while (!gameOver){
                    if (gameString.replaceAll("X", "").replaceAll("O", "").length() % 2 == 1 ){
                        gameString = updateGameString(gameString, Alice.makeMove(gameString), 'X');
                    } else {
                        gameString = updateGameString(gameString, Bob.makeMove(gameString), 'O');
                    }

                    double temp = checkForWin(gameString);
                    if (temp != 0.0 ){
                        gameOver = true;
                        Alice.learn(temp);
                        if (temp == 5){
                            Bob.learn(0.05);
                        } else if (temp == 0.05){
                            Bob.learn(5);
                        } else {
                            Bob.learn(2);
                        }
                    }      
                }

            }
            String again = System.console().readLine("Play again? (y/n)");
            if (again.charAt(0) == 'n') { done = true; }
        }
    }
 

    public static String toGameBoard(String inputstring){
        return " " + inputstring.charAt(0) + " | " + inputstring.charAt(1) + " | " + inputstring.charAt(2) + 
        "\n---+---+---\n" +
        " " + inputstring.charAt(3) + " | " + inputstring.charAt(4) + " | " + inputstring.charAt(5) +
        "\n---+---+---\n" +
        " " + inputstring.charAt(6) + " | " + inputstring.charAt(7) + " | " + inputstring.charAt(8) + "\n";

    }

   /**
    * Checks the given boardstate to determine if either player is in a winning position.
    *
    * @param oldGameState A 9 character string that represents the game state to check.
    * @return 10 if X wins, 0.05 if O wins, 2 for a tie and 0 if the game is not yet over.
    */
    public static double checkForWin(String oldGameState){ 
        if (oldGameState.length() != 9){
            throw new IllegalArgumentException("String oldGameState is of incorrect length");
        } else if (!oldGameState.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
            throw new IllegalArgumentException("String oldGameState contains invalid characters");
        }

        if (oldGameState.charAt(0) == oldGameState.charAt(1) && oldGameState.charAt(0) == oldGameState.charAt(2)) {
            if (oldGameState.charAt(0) == 'X'){
                return 5;
            } else if (oldGameState.charAt(0) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.charAt(3) == oldGameState.charAt(4) && oldGameState.charAt(3) == oldGameState.charAt(5)){
            if (oldGameState.charAt(3) == 'X'){
                return 5;
            } else if (oldGameState.charAt(3) == 'O') {
                return 0.05;
            }
        
        } else if (oldGameState.charAt(6) == oldGameState.charAt(7) && oldGameState.charAt(6) == oldGameState.charAt(8)){
            if (oldGameState.charAt(6) == 'X'){
                return 5;
            } else if (oldGameState.charAt(6) == 'O') {
                return 0.05;
            }
        
        } else if (oldGameState.charAt(0) == oldGameState.charAt(3) && oldGameState.charAt(0) == oldGameState.charAt(6)){
            if (oldGameState.charAt(0) == 'X'){
                return 5;
            } else if (oldGameState.charAt(0) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.charAt(1) == oldGameState.charAt(4) && oldGameState.charAt(1) == oldGameState.charAt(7)){
            if (oldGameState.charAt(1) == 'X'){
                return 5;
            } else if (oldGameState.charAt(1) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.charAt(2) == oldGameState.charAt(5) && oldGameState.charAt(2) == oldGameState.charAt(8)){
            if (oldGameState.charAt(2) == 'X'){
                return 5;
            } else if (oldGameState.charAt(2) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.charAt(0) == oldGameState.charAt(4) && oldGameState.charAt(0) == oldGameState.charAt(8)){
            if (oldGameState.charAt(0) == 'X'){
                return 5;
            } else if (oldGameState.charAt(0) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.charAt(2) == oldGameState.charAt(4) && oldGameState.charAt(2) == oldGameState.charAt(6)){
            if (oldGameState.charAt(2) == 'X'){
                return 5;
            } else if (oldGameState.charAt(2) == 'O') {
                return 0.05;
            }

        } else if (oldGameState.replaceAll(" ", "").length() == 9) {
            return 2;
        }
        return 0;
    }

    public static String updateGameString(String oldGameState, int move, char player){
        if (oldGameState.length() != 9){
            throw new IllegalArgumentException("String oldGameState is of incorrect length");
        } else if (!oldGameState.replaceAll("X", "").replaceAll("O", "").replaceAll(" ", "").equals("")){
            throw new IllegalArgumentException("String oldGameState contains invalid characters");
        }
        if ((move > 8 || move < 0) || (oldGameState.charAt(move) != ' ')){
            throw new IllegalArgumentException("Invalid move:" + move);
        } 

        if (!(player == 'X' || player == 'O')){
            throw new IllegalArgumentException("Invalid player");
        }
        String newGameState = "";
        for (int i = 0; i < oldGameState.length(); i++){
            if (i != move){
                newGameState += oldGameState.charAt(i);
            } else {
                newGameState += player;
            }
        }
        return newGameState;
    }
    
    public static void teach(TicTacToeAI player){
        player.memory.add(new MoveList("         ", 0));
        player.memory.add(new MoveList("X        ", 4));
        player.memory.add(new MoveList(" X       ", 4));
        player.memory.add(new MoveList("  X      ", 4));
        player.memory.add(new MoveList("   X     ", 4));
        player.memory.add(new MoveList("    X    ", 0));
        player.memory.add(new MoveList("     X   ", 4));
        player.memory.add(new MoveList("      X  ", 4));
        player.memory.add(new MoveList("       X ", 4));
        player.memory.add(new MoveList("        X", 4));
        player.memory.add(new MoveList("XO       ", 4));
        player.memory.add(new MoveList("X O      ", 6));
        player.memory.add(new MoveList("X  O     ", 4));
        player.memory.add(new MoveList("X   O    ", 8));
        player.memory.add(new MoveList("X    O   ", 4));
        player.memory.add(new MoveList("X     O  ", 2));
        player.memory.add(new MoveList("X      O ", 4));
        player.memory.add(new MoveList("X       O", 2));
        
    }

}
