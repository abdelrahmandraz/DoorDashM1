package game.engine.cards;
import game.engine.monsters.Monster;

public class SwapperCard extends Card {

	public SwapperCard(String name, String description, int rarity) {
		super(name, description, rarity, true);
	}
	
	// my part
	
	
	@Override
	public void performAction(Monster player, Monster opponent) { // swaps the position of the players if the position of 
		if(player.getPosition()<opponent.getPosition()) {		  //the player is less then the position of the opponent
			int temp=player.getPosition();
			player.setPosition(opponent.getPosition());
			opponent.setPosition(temp);
		}
		
	}

	
	
	//added

	@Override
	public Card GetCopy() {
		return new SwapperCard(this.getName(),this.getDescription(),this.getRarity());
	}
	
}
