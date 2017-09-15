package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.LandModel;
import model.PlayerModel;

/**
 * 
 * ?????,???????????x?????????????????????????z2??
 * 
 */
public class TrapCard extends Card {

	public TrapCard(PlayerModel owner) {
		super(owner);
		this.name = "TrapCard";
		this.cName = "?????";
		this.price = 120;
	}

	@Override
	public int useCard() {
		return GameState.CARD_TRAP;
	}

}
