package game.engine.monsters;

import game.engine.Role;

public class Dynamo extends Monster {
	
	public Dynamo(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		
		try{
			opponentMonster.setFrozen(true);
		}
		catch(NullPointerException e){
			System.err.println("Error: Dynamo instance is null.");
		}
	}
	
	@Override
	public void setEnergy(int newEnergy) {
	    //  Calculate what the change was supposed to be
	    int incomingChange = newEnergy - this.getEnergy();

	    if (incomingChange != 0) {
	        //  Dynamo passive: Double the change
	        int doubledChange = incomingChange * 2;

	        // USE SUPER to save the value and avoid the infinite loop
	        super.setEnergy(this.getEnergy() + doubledChange);
	    }
	}
	
}
