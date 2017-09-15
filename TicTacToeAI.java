import java.util.InputMismatchException;
import java.util.Scanner;
/**
 * Samantha Belliveau
 * ID: 110274063
 * Homework 5
 * CSE 214 Recitation Section 08
 * Recitation TA: Michael Rizzo
 * Grading TA: Tim Zhang
 * @author Samantha Belliveau
 *
 *AI class runs the game and controls the moves 
 *of the computer AI
 */
public class TicTacToeAI {

	/**
	 * runs the game and controls the moves 
	 * of the computer AI
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean win = false;
		boolean playerTurn = true;
		int pos;
		GameTree newGame = new GameTree();
		GameBoard start = new GameBoard();
		GameBoardNode node = new GameBoardNode(start, Box.X);
		GameTree.buildTree(node, Box.X);
		
		
		System.out.println("Welcome to TicTacToe\nThis is the board:");
		for (int i = 0; i < 9; i++){
			System.out.print("|_" + (i+1) + "_");
			if (i == 2 || i == 5 || i == 8){
				System.out.print("|\n");
			}
		}
		
		
		while(win == false){
			if (newGame.getCursor().isEnd()){
				win = true;
				continue;
			}
			if (playerTurn){
				
				System.out.println("\nEnter position for move: ");
				
				try{
					
					pos = input.nextInt();
					newGame.makeMove(pos-1);
					System.out.println("Your Move:");
					System.out.println(newGame.getCursor().getBoard());
					playerTurn = false;
					if (newGame.getCursor().isEnd()){
						win = true;
					}
					continue;
					
				}catch (IllegalArgumentException e){
					System.out.println("Invalid position, try again");
				}catch(InputMismatchException e){
					System.out.println("Invalid input, try again.");
					input.next();
				}
			}
			
			else if (!playerTurn){
				
				int maxProb = 0;
				double maxProbValue = 0;
				int drawProb = 0;
				double drawProbValue = 0;
				GameBoardNode nodePtr;
				int prevMax = 0;
				double prevMaxProbValue = 0;
				boolean badMove = false;
				
				for (int i = 0; i < 9; i++){
					
					if (newGame.getCursor().getConfig() == null){
						break;
					}
					
					nodePtr = newGame.getCursor().getConfig()[i];
					if (nodePtr == null){
						continue;
					}
					
					nodePtr.setProbabilities();
					
					if (nodePtr.getWinner() == Box.O){
						newGame.makeMove(i);
						break;
					}
					
					badMove = false;
					for (int j = 0; j < 9; j++){
						
						if (nodePtr.getConfig() == null){
							continue;
						}
						
						if (nodePtr.getConfig()[j] == null){
							continue;
						}
						
						if (GameTree.checkWin(nodePtr.getConfig()[j]) == Box.X){
							badMove = true;
							newGame.makeMove(j);
							break;
						}
					}
					if (badMove){
						break;
					}
					if (nodePtr.getWinProb() > maxProbValue){
						prevMax = maxProb;
						prevMaxProbValue = maxProbValue;
						maxProb = i;
						maxProbValue = nodePtr.getWinProb();	
					}
					
					if (nodePtr.getDrawProb() > drawProbValue){
						drawProb = i;
						drawProbValue = nodePtr.getDrawProb();
					}
				}
				
				if (badMove){
					
					newGame.getCursor().setProbabilities();
					System.out.println("Computer's Move:");
					System.out.println(newGame.getCursor().getBoard());
					System.out.printf("The probability of a win is: %.2f\n", newGame.getCursor().getLoseProb());
					System.out.printf("The probability of a draw is: %.2f\n", newGame.getCursor().getDrawProb());
					System.out.printf("The probability of a lose is: %.2f\n", newGame.getCursor().getWinProb());
					playerTurn = true;
					continue;
					
				}
				
				if (newGame.getCursor().getConfig() != null){
				if (newGame.getCursor().getConfig()[maxProb] == null){
					if (newGame.getCursor().getConfig()[drawProb] == null){
						int a = 0;
						while(newGame.getCursor().getConfig()[a] == null){
							a++;
						}
						
						newGame.makeMove(a);
						newGame.getCursor().setProbabilities();
						System.out.println("Computer's Move:");
						System.out.println(newGame.getCursor().getBoard());
						System.out.printf("The probability of a win is: %.2f\n", newGame.getCursor().getLoseProb());
						System.out.printf("The probability of a draw is: %.2f\n", newGame.getCursor().getDrawProb());
						System.out.printf("The probability of a lose is: %.2f\n", newGame.getCursor().getWinProb());
						playerTurn = true;
						continue;
						
					}
					
					newGame.makeMove(drawProb);
					System.out.println(newGame.getCursor().getBoard());
					playerTurn = true;
					
					if (newGame.getCursor().isEnd()){
						win = true;
						continue;	
					}
				}
				
				else if (newGame.getCursor().getConfig()[maxProb].getWinProb() == 0){
					newGame.makeMove(drawProb);
				}
				
				else{
					newGame.makeMove(maxProb);
				}}
				
				System.out.println("Computer's Move:");
				System.out.println(newGame.getCursor().getBoard());
				System.out.printf("The probability of a win is: %.2f\n", newGame.getCursor().getLoseProb());
				System.out.printf("The probability of a draw is: %.2f\n", newGame.getCursor().getDrawProb());
				System.out.printf("The probability of a lose is: %.2f\n", newGame.getCursor().getWinProb());
				playerTurn = true;

			}
			
			if (newGame.getCursor().isEnd()){
				win = true;
			}

		}
		
		if (newGame.getCursor().getWinner() == Box.O){
			System.out.println("The winner is: O");
		}
		
		if (newGame.getCursor().getWinner() == Box.X){
			System.out.println("The winner is: X");
		}
		
		if (newGame.getCursor().getWinner() == Box.EMPTY){
			System.out.println("It's a draw.");
		}
	}

}
