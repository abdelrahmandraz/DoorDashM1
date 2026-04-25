package game.engine;

import java.util.ArrayList;

import game.engine.cards.Card;
import game.engine.cells.*;
import game.engine.monsters.Monster;

public class Board {
	private Cell[][] boardCells;
	private static ArrayList<Monster> stationedMonsters; 
	private static ArrayList<Card> originalCards;
	public static ArrayList<Card> cards;
	
	public Board(ArrayList<Card> readCards) {
		this.boardCells = new Cell[Constants.BOARD_ROWS][Constants.BOARD_COLS];
		stationedMonsters = new ArrayList<Monster>();
		originalCards = readCards;
		cards = new ArrayList<Card>();
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
	
	// my code 
	
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
	
	private void setCardsByRarity() {
		ArrayList<Card> ans=new ArrayList<Card>();
		for(int i=0; i<originalCards.size();i++) {

			for(int j=0;j<originalCards.get(i).getRarity();j++) {
				ans.add(originalCards.get(i).GetCopy());
			}
		}
		originalCards=ans;
		
	}
	
		

	
	
	
	
}
