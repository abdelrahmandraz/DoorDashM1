package game.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import game.engine.exceptions.*;
import game.engine.monsters.*;
import game.engine.Board;

import game.engine.dataloader.DataLoader;
import game.engine.monsters.*;


public class Game {
	private Board board;
	private ArrayList<Monster> allMonsters; 
	private Monster player;
	private Monster opponent;
	private Monster current;
	private static Game instance; // added this to be able to implement the schemer
	
	private final Random rand = new Random();
	
	
	public Game(Role playerRole) throws IOException {
		
		this.board = new Board(DataLoader.readCards());
		this.allMonsters = DataLoader.readMonsters();
		this.player = selectRandomMonsterByRole(playerRole);
		this.opponent = selectRandomMonsterByRole(playerRole == Role.SCARER ? Role.LAUGHER : Role.SCARER);
		this.current = player;
		
		instance = this;// added this to be able to implement the schemer
		
		Board.setStationedMonsters(allMonsters);
		Board.getStationedMonsters().remove(player);
		Board.getStationedMonsters().remove(opponent);
		
	}
	
	public static Game getInstance() {// added this to be able to implement the schemer
        return instance;
    }
	
	public Board getBoard() {
		return board;
	}
	
	public ArrayList<Monster> getAllMonsters() {
		return allMonsters; 
	}
	
	public Monster getPlayer() {
		return player;
	}
	
	public Monster getOpponent() {
		return opponent;
	}
	
	public Monster getCurrent() {
		return current;
	}
	
	public void setCurrent(Monster current) {
		this.current = current;
	}
	
	private Monster selectRandomMonsterByRole(Role role) {
		Collections.shuffle(allMonsters);
	    return allMonsters.stream()
	    		.filter(m -> m.getRole() == role)
	    		.findFirst()
	    		.orElse(null);
	}
	
	// andrew
	
	private Monster getCurrentOpponent() {
        if(current== player) return opponent;
        return player;
    }
    
    private int rollDice() {
        return rand.nextInt(6)+1;
    }
    
    void usePowerup() throws OutOfEnergyException {
        int energy = getCurrent().getEnergy();
        int cost = Constants.POWERUP_COST;
        if (energy >= cost){
			getCurrent().executePowerupEffect(opponent);
			getCurrent().setEnergy(energy-cost);
		}
        else throw new OutOfEnergyException("insufficient monster energy");
        
    }
    
    public void playTurn() throws InvalidMoveException {
        Monster current = getCurrent();

        if (!current.isFrozen()) {
            
            int roll = rollDice();
            
            board.moveMonster(current, roll, getOpponent());
            
            if (getWinner()==current) {  
            	return; // Exit early if the game is over
            }
            
        } else 
            // Test Requirement: Unfreeze the monster if it was frozen
            current.setFrozen(false);
        
        switchTurn();
    }
    
    private boolean checkWinCondition(Monster monster) {
        // Return the result of the check so other methods can use it
        return monster.getPosition() >= Constants.WINNING_POSITION &&
                monster.getEnergy() >= Constants.WINNING_ENERGY;
    }
    
    public Monster getWinner() {
        if (checkWinCondition(getCurrent())){

            System.out.println("The winner (of team " + getCurrent().getRole() + ") : \" " + getCurrent().getName() + "\" takes it all " +
                    "! \n" + getOpponent().getName() + " has to fall (u suck!!)");
            return getCurrent();
        } else if (checkWinCondition(getOpponent())) {
            System.out.println("The winner (of team " + getOpponent().getRole() + ") : \" " + getOpponent().getName() + "\" takes it all " +
                    "! \n" + getCurrent().getName() + " has to fall (u suck!!)");
            return getOpponent();
        }
        return null;
    }
    
    private void switchTurn() {
        setCurrent((getCurrent()==getOpponent())? getPlayer() : getOpponent());
    }
    
    
	
}