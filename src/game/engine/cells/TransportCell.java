package game.engine.cells;

import game.engine.monsters.Monster;

public abstract class TransportCell extends Cell {
	private int effect;

	public TransportCell(String name, int effect) {
		super(name);
		this.effect = effect;
	}

	public int getEffect() {
		return effect;
	}
	
	public void transport(Monster monster) {
		int original_positionOf_monster=monster.getPosition();
		int Desired_position=original_positionOf_monster+this.getEffect();   
          monster.setPosition(Desired_position);
	}
	
	
	
	
}
