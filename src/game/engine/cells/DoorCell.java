package game.engine.cells;

import java.util.ArrayList;

import game.engine.Board;
import game.engine.Role;
import game.engine.interfaces.CanisterModifier;
import game.engine.monsters.Monster;

public class DoorCell extends Cell implements CanisterModifier {
	private Role role;
	private int energy;
	private boolean activated;
	
	public DoorCell(String name, Role role, int energy) {
		super(name);
		this.role = role;
		this.energy = energy;
		this.activated = false;
	}
	
	public Role getRole() {
		return role;
	}
	
	public int getEnergy() {
		return energy;
	}
	
	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean isActivated) {
		this.activated = isActivated;
	}
	
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		if(!this.isActivated()){ //IF #1
			ArrayList<Monster> monsters =  Board.getStationedMonsters();
			if(this.getRole()==landingMonster.getRole()){ // IF #2
					for(int i=0;i<monsters.size();i++){
						if(monsters.get(i).getRole()==landingMonster.getRole()) // IF #3
							this.modifyCanisterEnergy(monsters.get(i), this.getEnergy());
					}
					this.modifyCanisterEnergy(landingMonster, this.getEnergy());
					this.setActivated(true);
			}else{
				if(landingMonster.isShielded()){ // IF #4
					landingMonster.setShielded(false);
				}else{
					for(int i=0;i<monsters.size();i++){
						if(monsters.get(i).getRole()==landingMonster.getRole()) // IF #3 (YES IT'S THE SAME IF WITH THE SAME FUNCTIONALITY)
							this.modifyCanisterEnergy(monsters.get(i), -this.getEnergy());
					}
					this.modifyCanisterEnergy(landingMonster, -this.getEnergy());
					this.setActivated(true);
				}
			}
		}
		/* EXPLANATION!!!
		 *IF #1 makes sure this doorCell is not used up already because if it is then the monster will simply just land.
		 *IF #2 checks if the team will win or lose energy based on matching the role of the doorCell
		 *IF #3 makes sure we alter the energy of the landingMonster's team only.
		 *IF #4 checks the shield status of the landing monster so that we can determine if its team will get penalized or not. Also the doors don't activate if it's a deduction and the landingMonster has a shield.
		 *I have assumed .alterEnergy doesn't allow the energy to fall below 0.
		 *I have assumed stationedMonsters don't include the landingMonster.
		 *Please see the pic I sent on the group which is a flowchart about the logic of this method. */
		
	}
	
	
	@Override
    public void modifyCanisterEnergy(Monster monster, int canisterValue) {
        monster.alterEnergy(canisterValue);
    }

}