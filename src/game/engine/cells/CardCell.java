package game.engine.cells;

import game.engine.monsters.Monster;
import game.engine.Board;
import game.engine.cards.*;

public class CardCell extends Cell {
	
	public CardCell(String name) {
        super(name);
    }
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		Card pulledCard=Board.drawCard();
		pulledCard.performAction(landingMonster, opponentMonster);
		
	}
   
}
