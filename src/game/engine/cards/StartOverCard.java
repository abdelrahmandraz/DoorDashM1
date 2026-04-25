package game.engine.cards;

import game.engine.monsters.Monster;

public class StartOverCard extends Card {

	public StartOverCard(String name, String description, int rarity, boolean lucky) {
		super(name, description, rarity, lucky);
	}
	
	// my code

	@Override
	public void performAction(Monster player, Monster opponent) { //if the card is lucky the opponent position is reset to zero otherwise 
		if(this.isLucky())  //										the player position is set to zero  
			opponent.setPosition(0);
		
		player.setPosition(0);
		
	}
	
	@Override
	public Card GetCopy() {
		return new StartOverCard(this.getName(),this.getDescription(),this.getRarity(),this.isLucky());
	}

}
