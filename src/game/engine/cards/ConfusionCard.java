package game.engine.cards;

import game.engine.monsters.Monster;
import game.engine.Role;

public class ConfusionCard extends Card {
	private int duration;
	
	public ConfusionCard(String name, String description, int rarity, int duration) {
		super(name, description, rarity, false);
		this.duration = duration;
	}
	
	public int getDuration() {
		return duration;
	}

	//my code
	
	@Override
	public void performAction(Monster player, Monster opponent) {  //  sets the monster confusion for a set confusion 
		player.setConfusionTurns(duration);                         // 
		opponent.setConfusionTurns(duration);
		
		Role temp=player.getRole();
		player.setRole(opponent.getRole());
		opponent.setRole(temp);
		
	}


}
