package model.buildings;

import java.awt.Image;

import javax.swing.ImageIcon;

import model.EventsModel;
import model.PlayerModel;
import context.GameState;
import control.Control;

/**
 * 
 * ??? ????????????????????????ô???????????
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class Park extends Building {
	/*
	 * 
	 * ?¼???
	 */
	private Image[] imgageEvents = { EVENT_PARK_1, EVENT_PARK_2, EVENT_PARK_3,
			EVENT_PARK_4 };
	/**
	 * ?¼???
	 */
	public static Image EVENT_PARK_1 = new ImageIcon("images/event/park01.jpg")
			.getImage();
	/**
	 * ?¼???
	 */
	public static Image EVENT_PARK_2 = new ImageIcon("images/event/park02.jpg")
			.getImage();
	/**
	 * ?¼???
	 */
	public static Image EVENT_PARK_3 = new ImageIcon("images/event/park03.jpg")
			.getImage();
	/**
	 * ?¼???
	 */
	public static Image EVENT_PARK_4 = new ImageIcon("images/event/park04.jpg")
			.getImage();


	public Park(int posX, int posY) {
		super(posX, posY);
		this.name = "???";
	}
	
	public Image[] getImgageEvents() {
		return imgageEvents;
	}

	@Override
	public int getEvent() {
		return GameState.PARK_EVENT;
	}

}
