package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Constants;

import game.engine.Role;
import game.engine.Game;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

    @Override
    public void executePowerupEffect(Monster opponentMonster) {
        int totalStolen = 0;
        ArrayList<Monster> monsters = Game.getInstance().getAllMonsters();

        for (Monster m : monsters) {
            // Steal from others, respecting their shields
            totalStolen += this.stealEnergyFrom(m);
        }

        // Using alterEnergy triggers the +10 passive automatically
        this.alterEnergy(totalStolen);
    }

    @Override
    public void setEnergy(int newEnergy) {
        int currentEnergy = this.getEnergy();
        int requestedChange = newEnergy - currentEnergy;

        if (requestedChange != 0) {
            // Apply passive: Change + SCHEMER_STEAL 
            int finalEnergy = currentEnergy + requestedChange + Constants.SCHEMER_STEAL;
            
            // Call super to handle the Math.max(Constants.MIN_ENERGY, energy) logic
            super.setEnergy(finalEnergy);
        }
    }

    private int stealEnergyFrom(Monster target) {
        if (target != this) {
            int energyBefore = target.getEnergy();
            
            // ALWAYS use alterEnergy for the victim to respect their shield
            target.alterEnergy(-Constants.SCHEMER_STEAL);
            
            // Calculate actual energy lost (will be 0 if victim was shielded)
            return energyBefore - target.getEnergy();
        }
        return 0;
    }
	
	
}
