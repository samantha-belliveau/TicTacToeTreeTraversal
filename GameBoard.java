/**
 * Samantha Belliveau
 * ID: 110274063
 * Homework 5
 * CSE 214 Recitation Section 08
 * Recitation TA: Michael Rizzo
 * Grading TA: Tim Zhang
 * @author Samantha Belliveau
 *
 *GameBoard class creates an object representing the board
 *for TicTacToe as an array of 9 Box enums
 */
public class GameBoard implements Cloneable{
	private Box[] board;
	private final int boardSize = 9;
	
	/**
	 * default constructor for GameBoard object
	 * initializes the GameBoard to an array of size 
	 * boardSize (9) with all elements being the EMPTY
	 * Box enum
	 * 
	 * <dt><b>Postconditions:</b><dd>
	 * GameBoard object is initialized
	 */
	public GameBoard(){
		board = new Box[boardSize];
		for (int i = 0; i < boardSize; i++){
			board[i] = Box.EMPTY;
		}
	}
	
	/**
	 * checks if there are any "empty"
	 * (Box.EMPTY elements) in the array
	 * 
	 * @return true if full, false if not full
	 */
	public boolean isFull(){
		for (int i = 0; i < boardSize; i++){
			if (board[i] == Box.EMPTY){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * sets the element at position "position" in the 
	 * board array to the inputted move
	 * 
	 * <dt><b>Preconditions:</b><dd>
	 * board[position] should be = Box.EMPTY
	 * 
	 * <dt><b>Postconditions:</b><dd>
	 * board[position] = move
	 * 
	 * @param position desired position for move
	 * @param move type of Box to put in position
	 * @return true if the box was set, false if not
	 */
	public boolean setBox(int position, Box move){
		if (board[position] != Box.EMPTY){
			return false;
		}
		board[position] = move;
		return true;
	}
	
	/**
	 * gets the Box at the inputted position
	 * 
	 * @param position Box to retrieve
	 * @return Box enum in board[position]
	 */
	public Box getBox(int position){
		return board[position];
	}
	
	
	/**
	 * clones the GameBoard object
	 * 
	 * @return reference to the new object
	 */
	public Object clone(){
		try{
			GameBoard newBoard = (GameBoard)super.clone();
			newBoard.board = new Box[newBoard.boardSize];
			for (int i = 0; i < newBoard.boardSize; i++){
				newBoard.board[i] = this.board[i];
			}
			return newBoard;
		}catch(CloneNotSupportedException e){
			return null;
		}

	}
	
	/**
	 * @return String depicting the board 
	 * and its contents
	 */
	public String toString(){
		String str = "";
		for (int i = 0; i < boardSize; i++){
			if (board[i] == Box.O){
				str += "| O ";
			}
			if (board[i] == Box.X){
				str += "| X ";
			}
			if (board[i] == Box.EMPTY){
				str += "|___";
			}
			if (i == 2 || i == 5 || i == 8){
				str += "|\n";
			}
		}
		return str;
	}
	
	/**
	 * checks if a player has won the game, or if 
	 * there has been a draw
	 * 
	 * @return Box enum of the winning type, 
	 * or Box.EMPTY if there's a draw or no winner yet
	 */
	public Box checkWin(){
		Box a = board[0];
		Box b = board[1];
		Box c = board[2];
		if (a == b && b == c){
			return a;
		}
		
		a = board[3];
		b = board[4];
		c = board[5];
		if (a == b && b == c){
			return a;
		}
		
		a = board[6];
		b = board[7];
		c = board[8];
		if (a == b && b == c){
			return a;
		}
		
		a = board[0];
		b = board[3];
		c = board[6];
		if (a == b && b == c){
			return a;
		}
		
		a = board[1];
		b = board[4];
		c = board[7];
		if (a == b && b == c){
			return a;
		}
		
		a = board[2];
		b = board[5];
		c = board[8];
		if (a == b && b == c){
			return a;
		}
		
		a = board[0];
		b = board[4];
		c = board[8];
		if (a == b && b == c){
			return a;
		}
		
		a = board[6];
		b = board[4];
		c = board[2];
		if (a == b && b == c){
			return a;
		}
		
		return Box.EMPTY;
	}
	
}

/**
 * 
 * @author Samantha Belliveau
 *
 *GameBoardNode class wraps a GameBoard object 
 *and generates what its children (possible moves 
 * of the next player turn) would be in the
 *config array.
 */
class GameBoardNode{
	private GameBoard board;
	private boolean isEnd;
	private Box currentTurn;
	private Box winner;
	private GameBoardNode config[];
	private double winProb;
	private double loseProb;
	private double drawProb;
	private Box nextTurn;
	private int leavesO = 0;
	private int leavesTotal = 0;
	private int leavesX = 0;
	
	/**
	 * Constructor for the GameBoardNode, initializes 
	 * the board of the node, recursively sets all the 
	 * GameBoardNode elements of config and the config elements of 
	 * the elements in config until all possible moves have been layed 
	 * out
	 * 
	 * <dt><b>Preconditions:</b><dd>
	 * board must not be null
	 * currentTurn != Box.EMPTY
	 * 
	 * @param board GameBoard to set at the board
	 * @param currentTurn player making the next move
	 */
	public GameBoardNode(GameBoard board, Box currentTurn) {
		this.board = board;

		config = new GameBoardNode[9];
		if (board.isFull()){
			config = null;
			isEnd = true;
			winner = board.checkWin();
			return;
		}
		if (board.checkWin() != Box.EMPTY){
			config = null;
			isEnd = true;
			winner = board.checkWin();
			return;
		}
		for (int i = 0; i < config.length; i++){
			if (board.getBox(i) != Box.EMPTY){
				config[i] = null;
				continue;
			}
			else{
				if (currentTurn == Box.O){
					nextTurn = Box.X;
				}
				else{
					nextTurn = Box.O;
				}
				GameBoard temp = (GameBoard)board.clone();
				temp.setBox(i, currentTurn);
				config[i] = new GameBoardNode(temp, nextTurn);
			}
		}
	}
	
	
	/**
	 * 
	 * @return winner of the board, if there is one
	 */
	public Box getWinner(){
		return winner;
	}
	
	/**
	 * 
	 * @return probability of ending in a draw
	 */
	public double getDrawProb(){
		return drawProb;
	}
	
	/**
	 * 
	 * @return probability of the computer losing
	 */
	public double getLoseProb(){
		return loseProb;
	}
	
	/**
	 * 
	 * @return probability of the computer winning
	 */
	public double getWinProb(){
		return winProb;
	}
	
	/**
	 * 
	 * @return board of the GameBoardNode
	 */
	public GameBoard getBoard(){
		return board;
	}
	
	/**
	 * 
	 * @return nextTurn in play
	 */
	public Box getNextTurn(){
		return nextTurn;
	}
	
	/**
	 * 
	 * @return config array (array of next possible 
	 * GameBoardNode board configurations)
	 */
	public GameBoardNode[] getConfig(){
		return config;
	}
	
	/**
	 * 
	 * @return true if there are no more 
	 * moves to make (someone one, or there was 
	 * a draw), false otherwise
	 */
	public boolean isEnd(){
		return isEnd;
	}
	
	
	/**
	 * calls the recursive setProbabilities helper function 
	 * to set the probabilities for winning, losing, and drawing 
	 * for the configuration of this board
	 */
	public void setProbabilities(){
		boolean isLeaf = true;
		//this is a mess, write out how to traverse a tree to find all leaves
		for (int i = 0; i < 9; i++){
			if (config != null){
				if (config[i] != null){
					isLeaf = false;
					setProbabilities(config[i]);
				}
			}
		}
		if (isLeaf){
			if (winner == Box.X){
				winProb = 0;
				loseProb = 1;
				drawProb = 0;
			}
			if (winner == Box.O){
				winProb = 1;
				loseProb = 0;
				drawProb = 0;
			}
			else{
				winProb = 0;
				loseProb = 0;
				drawProb = 1;
			}
		}
		else{
			winProb = (double)leavesO/leavesTotal;
			loseProb = (double)leavesX/leavesTotal;
			drawProb = (double)(leavesTotal - leavesO - leavesX)/leavesTotal;
		}
	}
	
	/**
	 * recursive helper function that counts the amount 
	 * of winning/losing/drawing/total leaves in this tree
	 * @param node "root" of tree to calculates leaves for
	 */
	public void setProbabilities(GameBoardNode node){
		boolean isLeaf = true;
		if (node.config != null){
			for (int j = 0; j < 9; j++){
				if (node.getConfig()[j] != null){
					isLeaf = false;
					setProbabilities(node.getConfig()[j]);
				}
			}
		}
		if (isLeaf){
			if (node.board.checkWin() == Box.X){
				leavesX++;
			}
			if (node.board.checkWin() == Box.O){
				leavesO++;
			}
			leavesTotal++;
			return;
		}
	}
}

/**
 * 
 * @author samanthabelliveau
 *GameTree class creates a new GameBoardNode and sets it as 
 *the root of the tree, allows 
 *for traversals of the tree from root to leaf
 */
class GameTree{
	private GameBoardNode root;
	private GameBoardNode cursor;
	
	/**
	 * default constructor for the tree, sets the root 
	 * to a new GameBoardNode with an empty GameBoard and
	 * playerTurn being X (X goes first)
	 */
	public GameTree(){
		root = new GameBoardNode(new GameBoard(), Box.X);
		cursor = root;
	}
	
	/**
	 * <dt><b>Preconditions:</b><dd>
	 * pos must be < 9 and >= 0
	 * config array of the cursor must not be null
	 * 
	 * @param pos position of branch 
	 * to go down (0 would go to the first child, 
	 * 1 to the second, and so on)
	 */
	public void makeMove(int pos){
		if(pos < 0 || pos > 8){
			throw new IllegalArgumentException();
		}
		if (cursor.getConfig()[pos] == null){
			throw new IllegalArgumentException();
		}
		cursor = cursor.getConfig()[pos];
		//cursor = new GameBoardNode(cursor.getConfig()[pos].getBoard(), cursor.getNextTurn());
	}
	
	/**
	 * 
	 * @return cursor node of the tree
	 */
	public GameBoardNode getCursor(){
		return cursor;
	}
	
	/**
	 * 
	 * @return root node of the tree
	 */
	public GameBoardNode getRoot(){
		return root;
	}
	
	/**
	 * builds the tree of GameBoardNodes
	 * @param root root of tree to build
	 * @param turn players turn
	 * @return root node
	 */
	public static GameBoardNode buildTree(GameBoardNode root, Box turn){
		if (root == null){
			return root;
		}
		for (int i = 0; i < 9; i++){
			if (checkWin(root) == Box.EMPTY || !(root.getBoard().isFull())){
				if(root.getConfig() == null){
					return root;
				}
				if (root.getConfig()[i] == null){
					return root;
				}
				
				//System.out.println(root.getConfig()[i].getBoard());
				GameBoardNode next = new GameBoardNode(root.getConfig()[i].getBoard(), turn);
				buildTree(next, root.getNextTurn());
			}
		}
		return root;
	}
	
	/**
	 * checks to see if someone has won the board of 
	 * the given GameBoardNode 
	 * @param node GameBoardNode to check
	 * @return winning Box (O or X) or empty box if no winner
	 */
	public static Box checkWin(GameBoardNode node){
		Box a = node.getBoard().getBox(0);
		Box b = node.getBoard().getBox(1);
		Box c = node.getBoard().getBox(2);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(3);
		b = node.getBoard().getBox(4);
		c = node.getBoard().getBox(5);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(6);
		b = node.getBoard().getBox(7);
		c = node.getBoard().getBox(8);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(0);
		b = node.getBoard().getBox(3);
		c = node.getBoard().getBox(6);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(1);
		b = node.getBoard().getBox(4);
		c = node.getBoard().getBox(7);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(2);
		b = node.getBoard().getBox(5);
		c = node.getBoard().getBox(8);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(0);
		b = node.getBoard().getBox(4);
		c = node.getBoard().getBox(8);
		if (a == b && b == c){
			return a;
		}
		
		a = node.getBoard().getBox(6);
		b = node.getBoard().getBox(4);
		c = node.getBoard().getBox(2);
		if (a == b && b == c){
			return a;
		}
		
		return Box.EMPTY;
	}
	
	/**
	 * returns the probability of the computer 
	 * winning from this current game standings
	 * @return probability 
	 */
	public double cursorProbability(){
		return cursor.getWinProb();
	}
}

/**
 * Box enum has three type, 
 * X, O, and EMPTY to represent the 
 * three possible states of any box 
 * on the game board
 * @author samanthabelliveau
 *
 */
enum Box {
	X, O, EMPTY;
}

