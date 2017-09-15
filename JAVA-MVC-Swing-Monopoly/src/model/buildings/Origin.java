package model.buildings;

import model.PlayerModel;
import context.GameState;
import control.Control;

/**
 * 
 * ??? ??????????????
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Origin extends Building {
	/**
	 * ????????????
	 */
	private int passReward;
	/**
	 * ???????????
	 */
	private int reward;

	private PlayerModel player;

	public Origin(int posX, int posY) {
		super(posX, posY);
		this.name = "???";
		this.reward = 500;
		this.passReward = 200;
	}
	@Override
	public int getEvent() {
		return GameState.ORIGIN_EVENT;
	}
	
	public int getPassReward() {
		return passReward;
	}
	public int getReward() {
		return reward;
	}
	@Override
	public int passEvent() {
		return GameState.ORIGIN_PASS_EVENT;
	}
}
