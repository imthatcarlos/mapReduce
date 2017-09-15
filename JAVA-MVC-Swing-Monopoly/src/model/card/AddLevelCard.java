package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;
import model.buildings.Building;

/**
 * 
 * ????,????????????
 * OK
 * 
 */
public class AddLevelCard extends Card {

	public AddLevelCard(PlayerModel owner) {
		super(owner);
		this.name = "AddLevelCard";
		this.cName = "????";
		this.price = 30;

	}

	@Override
	public int useCard() {
		return GameState.CARD_ADDLEVEL;
	}

}
