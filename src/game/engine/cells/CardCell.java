package game.engine.cells;

import game.engine.Board;
import game.engine.cards.Card;
import game.engine.monsters.Monster;

public class CardCell extends Cell {
	
	public CardCell(String name) {
        super(name);
    }
    
    @Override
    public void onLand(Monster landingMonster, Monster opponentMonster) {
    	super.onLand(landingMonster, opponentMonster);
    	
        Card card = Board.drawCard();
        
        // Notify the Board which card was just drawn so GUI can read it
        Board.setLastDrawnCard(card);
        
        card.performAction(landingMonster, opponentMonster);
    }
   
}