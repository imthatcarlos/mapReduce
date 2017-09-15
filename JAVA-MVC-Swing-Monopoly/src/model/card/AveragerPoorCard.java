package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;

/**
 * 
 * ?????,???????????þ???????????c????????F??
 * OK
 * 
 */
public class AveragerPoorCard extends Card {

	public AveragerPoorCard(PlayerModel owner) {
		super(owner);
		this.name = "AveragerPoorCard";
		this.cName = "?????";
		this.price = 200;
	}

	@Override
	public int useCard() {
		return GameState.CARD_AVERAGERPOOR;
	}

}
