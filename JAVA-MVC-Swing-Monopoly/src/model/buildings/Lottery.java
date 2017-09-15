package model.buildings;

import model.PlayerModel;
import model.TextTipModel;
import context.GameState;
import control.Control;

/**
 * 
 * ???
 * ????????????????????????????????????????.
 * 
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Lottery extends Building {

	private PlayerModel player;
	
	public Lottery(int posX, int posY) {
		super(posX, posY);
		this.name = "???";
	}
	
	@Override
	public int getEvent() {
		return GameState.LOTTERY_EVENT;
	}
}
