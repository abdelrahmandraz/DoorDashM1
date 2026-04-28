package game.engine.cells;

import game.engine.monsters.Monster;


public class ConveyorBelt extends TransportCell {

	public ConveyorBelt(String name, int effect) {
		super(name, effect);
	}
	
	@Override
	public void transport(Monster monster) {
		int original_positionOf_monster=monster.getPosition();
		int Desired_position=original_positionOf_monster+this.getEffect();
          
          monster.setPosition(Desired_position);
	}
	
	
	@Override
	public void onLand(Monster landingMonster, Monster opponentMonster) {
		super.onLand(landingMonster, opponentMonster);
		this.transport(landingMonster);
	}
	
	
}
