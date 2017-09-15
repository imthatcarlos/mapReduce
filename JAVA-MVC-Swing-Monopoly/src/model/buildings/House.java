package model.buildings;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.PlayerModel;

import context.GameState;
import control.Control;

import util.FileUtil;
import util.MyThread;

/**
 * 
 * ???? 1-5?? ??? ???? ??? ?????¥ ????¥
 * 
 * @author MOVELIGHTS
 * 
 */
public class House extends Building {

	private int upPrice;
	private String[] nameString = { "???", "???", "????", "???", "?????¥", "????¥" };

	private PlayerModel player;

	public House(int posX, int posY) {
		super(posX, posY);
		this.maxLevel = 5;
	}

	public int getUpLevelPrice() {
		if (this.level == 0) {
			this.upPrice = 500;
		} else {
			this.upPrice = 1000 * this.level;
		}
		return upPrice;
	}
	
	/**
	 * 
	 * ???????????
	 * 
	 * @return
	 */
	public int getAllPrice() {
		int price = 0;
		for (int i = 0; i <= level; i++) {
			if (this.level == 0) {
				price +=500;
			} else {
				price += 1000 * i;
			}
		}
		return price;
	}

	/**
	 * ???
	 * 
	 * @return
	 */
	public int getRevenue() {
		/**
		 * ????????
		 */
		this.revenue = this.level * (int) (Math.random() * 1000)
				+ (this.level * 300);
		return revenue;
	}

	public String getName() {
		return this.nameString[this.level];
	}

	/**
	 * ????????????
	 * 
	 * @return
	 */
	public String getUpName() {
		if (this.level >= this.nameString.length - 1) {
			return "null";
		}
		return this.nameString[this.level + 1];
	}

	@Override
	public int getEvent() {
		return GameState.HUOSE_EVENT;
	}
}
