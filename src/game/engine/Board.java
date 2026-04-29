package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.dataloader.DataLoader;
import game.engine.exceptions.*;
import game.engine.monsters.Monster;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	//private static Board instance; // added this to be able to use the conveyer cell
	
	/*public static Board getInstance() {
		return instance;
	}*/

	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		this.setCardsByRarity();
		
		cards = new ArrayList<Card>();
		reloadCards();
	}
	
	public Cell[][] getBoardCells() {
		return boardCells;
	}
	
	public static ArrayList<Monster> getStationedMonsters() {
		return stationedMonsters;
	}
	
	public static void setStationedMonsters(ArrayList<Monster> stationedMonsters) {
		Board.stationedMonsters = stationedMonsters;
	}

	public static ArrayList<Card> getOriginalCards() {
		return originalCards;
	}
	
	public static ArrayList<Card> getCards() {
		return cards;
	}
	
	public static void setCards(ArrayList<Card> cards) {
		Board.cards = cards;
	}
	
	// deraz 
	
	private int[] indexToRowCol(int index) {
		if(index>99 || index<0) {
			throw new IllegalArgumentException("Invalid cell index: " + index);
		}
		
		int row= index/10;
		int colums =(row%2==0)? index%10 : 9-index%10;
		int[] ans= {row,colums};
		return ans;
	}	
	
	
	private Cell getCell(int index) {
		int[] cell_Location=indexToRowCol(index);
		return boardCells[cell_Location[0]][cell_Location[1]];
		
	}
	
	private void setCell(int index, Cell cell) {
		if(index>99 || index<0) {
			System.err.print("invalid index");
			return;
		}
		int[] cell_Location=indexToRowCol(index);
		boardCells[cell_Location[0]][cell_Location[1]]=cell;
	}
	
	public void initializeBoard(ArrayList<Cell> specialCells) throws IOException {
		
		int[] Coordinate;
		
		for(int i=1,j=0;i<100;i+=2,j++) { //populates the door cells
			Coordinate=indexToRowCol(i);
			boardCells[Coordinate[0]][Coordinate[1]]=specialCells.get(j);
		}
		
		for(int i=0,j=50;i<Constants.CONVEYOR_CELL_INDICES.length;i++,j++){ //populates the conveyer 
			Coordinate=indexToRowCol(Constants.CONVEYOR_CELL_INDICES[i]);
			while(!(specialCells.get(j)instanceof ConveyorBelt)) j++;
			boardCells[Coordinate[0]][Coordinate[1]]=specialCells.get(j);
			
		}
		
		for(int i=0,j=50;i<Constants.SOCK_CELL_INDICES.length;i++,j++){ //populates the Contamination sock 
			Coordinate=indexToRowCol(Constants.SOCK_CELL_INDICES[i]);
			while(!(specialCells.get(j)instanceof ContaminationSock)) j++;
			boardCells[Coordinate[0]][Coordinate[1]]=specialCells.get(j);
			
		}
		
		for(int i=0;i<Constants.MONSTER_CELL_INDICES.length;i++){ //populates the stationed Monsters 
			Coordinate=indexToRowCol(Constants.MONSTER_CELL_INDICES[i]);
			boardCells[Coordinate[0]][Coordinate[1]]=new MonsterCell(stationedMonsters.get(i).getName(), stationedMonsters.get(i));
			stationedMonsters.get(i).setPosition(Constants.MONSTER_CELL_INDICES[i]);
			
		}
		
		for(int i=0;i<Constants.CARD_CELL_INDICES.length;i++){
			Coordinate=indexToRowCol(Constants.CARD_CELL_INDICES[i]);
			boardCells[Coordinate[0]][Coordinate[1]]=new CardCell(""+i);
		}
		
		for(int i=0;i<100;i++) {
			if(getCell(i)==null) setCell(i,new Cell(i+""));
		}
		
		
		
	}
	
	private void setCardsByRarity() {
		ArrayList<Card> ans=new ArrayList<Card>();
		for(int i=0; i<originalCards.size();i++) {

			for(int j=0;j<originalCards.get(i).getRarity();j++) {
				ans.add(originalCards.get(i).GetCopy());
			}
		}
		originalCards=ans;
		
	}
	
	public static void reloadCards() {
		Collections.shuffle(originalCards);
		for (int i=0;i<getOriginalCards().size();i++) {
			cards.add(originalCards.get(i));
		}
	}
	
	public static Card drawCard() {
		if(cards.isEmpty())
			reloadCards();
		
		return cards.remove(0);	
	}
	
	
	public  void moveMonster(Monster currentMonster, int roll, Monster opponentMonster) throws InvalidMoveException{
		/*if(currentMonster.isFrozen()) {
			currentMonster.setFrozen(false);
			return;								//still working on it !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		}*/
		
		int original_position=currentMonster.getPosition();
		int startEnergy = currentMonster.getEnergy();
		boolean currentWasConfused = currentMonster.isConfused();
	    boolean opponentWasConfused = opponentMonster.isConfused();
		
		
		currentMonster.move(roll);
		Cell currentCell=getCell(currentMonster.getPosition());
		currentCell.onLand(currentMonster, opponentMonster);
		
		if(currentMonster.getPosition()==opponentMonster.getPosition()) {
			currentMonster.setPosition(original_position);
			currentMonster.setEnergy(startEnergy);
			throw new InvalidMoveException("cell already occupied");
		}
		
		if (currentWasConfused) currentMonster.decrementConfusion();
	    //if (opponentWasConfused) opponentMonster.decrementConfusion();
		
	    updateMonsterPositions(currentMonster,opponentMonster);
	}
	
	private void updateMonsterPositions(Monster player, Monster opponent) {
		for(int i=0;i<boardCells.length;i++) {
			for (int j=0;j<boardCells.length;j++) {
				boardCells[i][j].setMonster(null);
				
			}
		}
		
		
		for (Monster stationed : stationedMonsters) {
	        getCell(stationed.getPosition()).setMonster(stationed);
	    }
		
		getCell(player.getPosition()).setMonster(player);
		getCell(opponent.getPosition()).setMonster(opponent);
		
	}
	
	
	
	
	
	
		

	
	
	
	
}
