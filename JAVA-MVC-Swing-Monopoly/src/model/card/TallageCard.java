package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;

/**
 * 
 * ?????,??????????ö?????????????F???10%????
 * 
 *
 */
public class TallageCard extends Card{

	public TallageCard(PlayerModel owner) {
		super(owner);
		this.name = "TallageCard";
		this.cName = "?????";
		this.price = 100;
	}

	@Override
	public int useCard() {
		return GameState.CARD_TALLAGE;
	}

}
