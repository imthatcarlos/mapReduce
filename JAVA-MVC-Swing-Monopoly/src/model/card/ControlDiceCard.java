package model.card;

import javax.swing.JOptionPane;

import context.GameState;

import model.PlayerModel;

/**
 * 
 * 
 * ???????,????b????????????????????????????c????
 * OK
 *
 */
public class ControlDiceCard extends Card{

	int diceNumber;
	
	public ControlDiceCard(PlayerModel owner) {
		super(owner);
		this.name = "ControlDiceCard";
		this.cName = "????????";
		this.price = 30;
	}

	@Override
	public int useCard() {
		/*
		Object[] options = { "1??", "2??","3??","4??","5??","6??","???????" };
		int response = JOptionPane.showOptionDialog(null,
				"??????\"????????\"???????????", "?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == -1 || response == 6){
			// ???????
			this.owner.useCards();
		} else {
			// ???
			this.owner.getRunning().setPoint(response + 1);
			// ??????????
			this.owner.showTextTip(this.owner.getName() + " ????? \"????????\".", 2);
			//????????
			this.owner.getCards().remove(this);
		}
		this.owner.useCards();
		*/
		return GameState.CARD_CONTROLDICE;
	}
}
