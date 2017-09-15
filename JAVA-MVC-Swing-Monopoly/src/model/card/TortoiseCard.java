package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;

/**
 * 
 * ???,????????????Þ???????????????????B?m????????????
 * 
 * ??????????????????????????????????????w??
 * 
 */
public class TortoiseCard extends Card {

	private int life = 3;

	public TortoiseCard(PlayerModel owner) {
		super(owner);
		this.name = "TortoiseCard";
		this.cName = "???";
		this.price = 50;
	}

	@Override
	public int useCard() {
		return GameState.CARD_TORTOISE;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	@Override
	public int cardBuff() {
		/*
		// ??????????
		this.owner.showTextTip(this.owner.getName() + " ??\"???\" ???ã??????????.. ", 2);
		this.owner.getRunning().setPoint(1);
		if (life < 0) {
			this.owner.getEffectCards().remove(this);
		}
		*/
		return GameState.CARD_BUFF_TORTOISE;
	}
}
