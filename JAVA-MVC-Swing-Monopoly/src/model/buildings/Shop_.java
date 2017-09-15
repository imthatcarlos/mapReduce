package model.buildings;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import context.GameState;
import control.GameRunning;


import model.buildings.Building;
import model.card.*;

/**
 * 
 * 
 * ??????
 * 
 * @author MOVELIGHTS
 * 
 */
public class Shop_ extends Building {

	/**
	 * 
	 * ??????????
	 * 
	 */
	public static int MAXITEMSIZE = 9;
	/**
	 * 
	 * ???????
	 * <p>
	 * ??????? 9
	 * 
	 */
	private List<Card> cards = new ArrayList<Card>(MAXITEMSIZE);

	private GameRunning running = null;

	private Shop_ shopUI;

	public Shop_(int posX, int posY) {
		super(posX, posY);
		this.name = "???";
	}

	@Override
	public int getEvent() {
		return GameState.SHOP_EVENT;
	}

	/**
	 * 
	 * ??????????????????
	 * 
	 */
	public void createCards() {
		// ??????
		this.cards = new ArrayList<Card>(MAXITEMSIZE);
		// ????µ?card
		for (int i = 0; i < MAXITEMSIZE; i++) {
			int random = (int) (Math.random() * 12);
			switch (random) {
			case 0:
				cards.add(new AddLevelCard(null));
				break;
			case 1:
				cards.add(new AveragerPoorCard(null));
				break;
			case 2:
				cards.add(new ChangeCard(null));
				break;
			case 3:
				cards.add(new ControlDiceCard(null));
				break;
			case 4:
				cards.add(new CrossingCard(null));
				break;
			case 5:
				cards.add(new HaveCard(null));
				break;
			case 6:
				cards.add(new ReduceLevelCard(null));
				break;
			case 7:
				cards.add(new RobCard(null));
				break;
			case 8:
				cards.add(new StopCard(null));
				break;
			case 9:
				cards.add(new TallageCard(null));
				break;
			case 10:
				cards.add(new TortoiseCard(null));
				break;
			case 11:
				cards.add(new TrapCard(null));
				break;
			}
		}
	}
	public List<Card> getCards() {
		return cards;
	}
}
