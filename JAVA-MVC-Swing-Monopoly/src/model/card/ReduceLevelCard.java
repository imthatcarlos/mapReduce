package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;
import model.buildings.Building;

/**
 * 
 * ??????,????????????????????
 * OK
 *
 */
public class ReduceLevelCard extends Card{

	public ReduceLevelCard(PlayerModel owner) {
		super(owner);
		this.name = "ReduceLevelCard";
		this.cName = "??????";
		this.price = 30;
	}

	@Override
	public int useCard() {
		return GameState.CARD_REDUCELEVEL;
		}

}
