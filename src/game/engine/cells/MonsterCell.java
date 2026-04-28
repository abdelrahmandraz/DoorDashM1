package game.engine.cells;

import game.engine.monsters.*;

public class MonsterCell extends Cell {
	private Monster cellMonster;

	public MonsterCell(String name, Monster cellMonster) {
		super(name);
		this.cellMonster = cellMonster;
	}

	public Monster getCellMonster() {
		return cellMonster;
	}
	public void onLand(Monster landingMonster, Monster opponentMonster){
		super.onLand(landingMonster, opponentMonster);
		if(this.getCellMonster().getRole()==landingMonster.getRole()){
			landingMonster.executePowerupEffect(opponentMonster);
		}else{
			if(landingMonster.getEnergy()>this.getCellMonster().getEnergy()){
				if(landingMonster.isShielded()){
					this.getCellMonster().setEnergy(landingMonster.getEnergy());
					landingMonster.setShielded(false);
				}else{
					int cellMonstersEnergy = this.getCellMonster().getEnergy();
					this.getCellMonster().setEnergy(landingMonster.getEnergy());
					landingMonster.setEnergy(cellMonstersEnergy);
				}
			}
		}
	}
}
