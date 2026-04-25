package game.engine.monsters;

import game.engine.Constants;
import game.engine.Role;

public abstract class Monster implements Comparable<Monster> {
	private String name;
	private String description;
	private Role role;
	private Role originalRole; // For confusion card
	private int energy;
	private int position;
	private boolean frozen;
	private boolean shielded;
	private int confusionTurns;
	
	public Monster(String name, String description, Role originalRole, int energy) {
		super();
		this.name = name;
		this.description = description;
		this.role = originalRole;
		this.originalRole = originalRole; 
		this.energy = energy;
		this.position = 0;
		this.frozen = false;
		this.shielded = false;
		this.confusionTurns = 0;
	}
	

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}

	public Role getOriginalRole() {
		return originalRole;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = Math.max(Constants.MIN_ENERGY, energy);
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position % Constants.BOARD_SIZE;
	}
	
	public boolean isFrozen() {
		return frozen;
	}
	
	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	
	public boolean isShielded() {
		return shielded;
	}
	
	public void setShielded(boolean shielded) {
		this.shielded = shielded;
	}
	
	public int getConfusionTurns() {
		return confusionTurns;
	}
	
	public void setConfusionTurns(int confusionTurns) {
		this.confusionTurns = confusionTurns;
	}

	@Override
	public int compareTo(Monster other) {
		return this.position - other.position;
	}
	
	public abstract void executePowerupEffect(Monster opponentMonster); //2.1
	
	public boolean isConfused(){ // confused or not 2.2
		if(this.getConfusionTurns() == 0) return false ;
		return true;
	}
	
	public void move(int distance){ // 2.3
		int newPosition = this.getPosition()+ distance ;
		
		if(newPosition>99)	newPosition %= 100 ;
		
		
		this.setPosition(newPosition);
	}
	
	public final void alterEnergy(int incomingChange) {
        if (incomingChange < 0 && this.isShielded()) {
        	this.setShielded(false); // shield consumes setEnergy is NEVER called
        } else {
            // respects passive logic because setEnergy is overridden in subclasses
            setEnergy(this.energy + incomingChange); 
        }
    }
	
	public void decrementConfusion() {
	    int confusion = this.getConfusionTurns();
	    
	    if (confusion > 0) {
	        confusion--; 
	        this.setConfusionTurns(confusion);
	     
	        if (confusion == 0) {
	            this.setRole(this.getOriginalRole());
	        }
	    }
	}


}