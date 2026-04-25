package game.engine.monsters;

import game.engine.Role;

import game.engine.Constants;

public class MultiTasker extends Monster {
	private int normalSpeedTurns;
	
	public MultiTasker(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.normalSpeedTurns = 0;
	}

	public int getNormalSpeedTurns() {
		return normalSpeedTurns;
	}

	public void setNormalSpeedTurns(int normalSpeedTurns) {
		this.normalSpeedTurns = normalSpeedTurns;
	}
	
	@Override
    public void executePowerupEffect(Monster opponentMonster) {
        try {
            // Focus Mode lasts for 2 turns according to the table.
            this.setNormalSpeedTurns(this.getNormalSpeedTurns()+2);
            
        } catch (NullPointerException e) {
           
            System.err.println("Error: MultiTasker instance is null.");
        }

	}
	
	@Override 
	public void move(int distance) {
	    double multiplier = 0.5; // Passive: Moves at 0.5 the dice roll
	    
	    if (this.getNormalSpeedTurns() > 0) {
	        multiplier = 1.0; // Focus Mode: Moves at normal speed
	        // Decrement turns (using the setter/getter pattern or field access)
	        this.setNormalSpeedTurns(this.getNormalSpeedTurns() - 1);
	    }
	        
	    int travelDistance = (int) (multiplier * distance);
	    int newPosition = (this.getPosition() + travelDistance) % 100; 
	    
	    this.setPosition(newPosition);
	}
	
	@Override
	public void setEnergy(int newEnergy) {
	    int currentEnergy = this.getEnergy();
	    int requestedChange = newEnergy - currentEnergy;

	    if (requestedChange != 0) { 
	        // Passive: Gains +200 on all incoming energy changes
	        int finalEnergy = currentEnergy + requestedChange + Constants.MULTITASKER_BONUS;
	        
	        // Use super to avoid the infinite loop and handle clamping
	        super.setEnergy(finalEnergy); 
	    }
	}
}