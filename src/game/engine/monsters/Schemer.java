package game.engine.monsters;

import java.util.ArrayList;

import game.engine.Constants;

import game.engine.Role;
import game.engine.Board;

public class Schemer extends Monster {
	
	public Schemer(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

    @Override
    public void executePowerupEffect(Monster opponentMonster) {
        // 1. Start with your current energy (e.g., 20)
        int totalEnergy = this.getEnergy();
        ArrayList<Monster> monsters = Board.getStationedMonsters();

        // 2. Steal from the list
        if (monsters != null) {
            for (Monster m : monsters) {
                totalEnergy += this.stealEnergyFrom(m);
               // System.out.println("Current total energy: " + totalEnergy); // Debug
            }
        }

        // 3. Steal from the opponent
        if (opponentMonster != null) {
            totalEnergy += this.stealEnergyFrom(opponentMonster);
          //  System.out.println("Energy after opponent steal: " + totalEnergy);
        }

        // 4. Update the Schemer
        this.setEnergy(totalEnergy);
    }

    @Override
    public void setEnergy(int newEnergy) {
        int currentEnergy = this.getEnergy();
        int requestedChange = newEnergy - currentEnergy;

       // if (requestedChange != 0) {
            // 2. newEnergy already represents (currentEnergy + requestedChange)
            int finalEnergy = newEnergy + Constants.SCHEMER_STEAL;

           // System.out.println("Applying passive: " + newEnergy + " + 10 = " + finalEnergy);

            super.setEnergy(finalEnergy);
        //}

    }


    private int stealEnergyFrom(Monster target) {
        if (target == null || target == this) return 0;

        int energyBefore = target.getEnergy();

        // Check if the monster is shielded BEFORE you try to steal
        if (target.isShielded()) {
            target.setShielded(false); // Shield breaks
            return 0; // You stole nothing
        }

        // Determine how much you CAN steal (cannot take more than they have)
        int amountToSteal = Math.min(Constants.SCHEMER_STEAL, energyBefore);

        // This triggers the victim's passive (like MultiTasker's +200)
        target.setEnergy(energyBefore - amountToSteal);

        // You return the amount you actually took,
        // NOT the difference in their final energy.
        return amountToSteal;
    }
	
	
}
