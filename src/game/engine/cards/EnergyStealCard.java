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

	// my part !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! unfinished !!!!!!!!!!!!!!!!!!!!!! not sure ok the fuck is canister energy ????
	
	@Override
	public void modifyCanisterEnergy(Monster monster, int canisterValue) {
		monster.alterEnergy(canisterValue);	
	}

	@Override
	public void performAction(Monster player, Monster opponent) {
		if(opponent.isShielded()) {
			opponent.setShielded(false);
			return;
		}
		int amount_stolen=Math.min(opponent.getEnergy(),this.getEnergy());
		this.modifyCanisterEnergy(opponent,-amount_stolen);
		this.modifyCanisterEnergy(player, amount_stolen);
		
	}

	
}
