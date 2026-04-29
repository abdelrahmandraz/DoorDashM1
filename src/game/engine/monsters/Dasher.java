package game.engine.monsters;

import game.engine.Role;

public class Dasher extends Monster {
	private int momentumTurns;

	public Dasher(String name, String description, Role role, int energy) {
		super(name, description, role, energy);
		this.momentumTurns = 0;
	}
	
	public int getMomentumTurns() {
		return momentumTurns;
	}
	
	public void setMomentumTurns(int momentumTurns) {
		this.momentumTurns = momentumTurns;
	}

	@Override
	public void executePowerupEffect(Monster opponentMonster) {
		this.setMomentumTurns(this.getMomentumTurns()+3);
	}
	
	@Override 
	public void move(int distance) {
	    int multiplier = 2; // Passive: Moves at 2x the dice roll
	    
	    // Check if Momentum Rush powerup is active
	    if (this.getMomentumTurns() > 0) {
	        multiplier = 3; 
	        // Decrement using the setter to keep state consistent
	        this.setMomentumTurns(this.getMomentumTurns() - 1);
	    }
	        
	    int travelDistance = multiplier * distance;
	    int newPosition = (this.getPosition() + travelDistance) % 100; 
	    
	    this.setPosition(newPosition);
	}
	
	
	
	
}
