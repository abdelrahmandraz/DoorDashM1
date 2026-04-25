package game.engine.cards;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class EnergyStealCard extends Card implements CanisterModifier {
	private int energy;

	public EnergyStealCard(String name, String description, int rarity, int energy) {
		super(name, description, rarity, true);
		this.energy = energy;
	}
	
	public int getEnergy() {
		return energy;
	}

	// my part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! unfinished !!!!!!!!!!!!!!!!!!!!!!
	
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		
		
	}
	
}
