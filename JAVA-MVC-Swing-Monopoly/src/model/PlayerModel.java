package model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.buildings.Building;
import model.card.Card;
import control.Control;
import control.GameRunning;

/**
 * ??????
 * 
 * @author MOVELIGHTS
 * 
 */
public class PlayerModel extends Tick implements Port {

	/**
	 * ????
	 */
	private String name;
	/**
	 * ??????????
	 */
	private int part = 0;
	/**
	 * ???
	 */
	private int cash;
	/**
	 * ???
	 */
	private int nx;

	/**
	 * ??????? x ???????½??X
	 */
	private int x;
	/**
	 * ??????? y ???????½??y
	 */
	private int y;

	/**
	 * 
	 * ?????????
	 * 
	 */
	private int inHospital;
	/**
	 * 
	 * ??????????
	 * 
	 */
	private int inPrison;

	/**
	 * 
	 * ?????,????????????
	 * 
	 */
	private int number = 0;

	/**
	 * 
	 * ?????????????
	 * 
	 */
	private List<Building> buildings = new ArrayList<Building>();

	/**
	 * 
	 * ?????
	 * 
	 */
	private List<Card> cards = new ArrayList<Card>();

	/**
	 * 
	 * ?????????
	 * 
	 */
	public static int MAX_CAN_HOLD_CARDS = 8;

	/**
	 * 
	 * ?????????EFFECT ???
	 * 
	 */
	private List<Card> effectCards = new ArrayList<Card>();

	private Image[] playerIMG = new Image[100];

	/**
	 * 
	 * ??????
	 * 
	 */
	private PlayerModel otherPlayer = null;
	/**
	 * 
	 * ?????????
	 * 
	 */
	private Control control = null;

	public PlayerModel(int number, Control control) {
		this.name = "";
		this.number = number;
		this.control = control;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public List<Card> getEffectCards() {
		return effectCards;
	}

	public List<Building> getBuildings() {
		return buildings;
	}

	public int getInPrison() {
		return inPrison;
	}

	public void setInPrison(int inPrison) {
		this.inPrison = inPrison;
	}

	/**
	 * 
	 * ???????????
	 * 
	 */
	private void initPlayerIMG() {
		// LOGO
		this.playerIMG[0] = new ImageIcon("images/player/" + this.getPart()
				+ "/logo.png").getImage();
		// mini_false
		this.playerIMG[1] = new ImageIcon("images/player/" + this.getPart()
				+ "/mini_01.png").getImage();
		// mini_true
		this.playerIMG[2] = new ImageIcon("images/player/" + this.getPart()
				+ "/mini_01_on.png").getImage();
		// head_h5
		this.playerIMG[3] = new ImageIcon("images/player/" + this.getPart()
				+ "/head_h5.png").getImage();
		// smile
		this.playerIMG[4] = new ImageIcon("images/player/" + this.getPart()
				+ "/smile.png").getImage();
		// sad
		this.playerIMG[5] = new ImageIcon("images/player/" + this.getPart()
				+ "/sad.png").getImage();
		// mini_02
		this.playerIMG[6] = new ImageIcon("images/player/" + this.getPart()
				+ "/mini_02.png").getImage();
	}

	/**
	 * 
	 * ?????????
	 * 
	 * @return <li>logo LOGO</li> <li>mini ????-???</li> <li>mini_on ????-???</li>
	 *         <i>h5 ??? </li> <li>other null</li>
	 */
	public Image getIMG(String str) {
		if (str.equals("logo"))
			return this.playerIMG[0];
		else if (str.equals("mini"))
			return this.playerIMG[1];
		else if (str.equals("mini_on"))
			return this.playerIMG[2];
		else if (str.equals("h5"))
			return this.playerIMG[3];
		else if (str.equals("smile"))
			return this.playerIMG[4];
		else if (str.equals("sad"))
			return this.playerIMG[5];
		else if (str.equals("mini_02"))
			return this.playerIMG[6];
		else
			return null;
	}

	public PlayerModel getOtherPlayer() {
		return otherPlayer;
	}

	public void setOtherPlayer(PlayerModel otherPlayer) {
		this.otherPlayer = otherPlayer;
	}

	public int getNumber() {
		return number;
	}

	public int getInHospital() {
		return inHospital;
	}

	public void setInHospital(int inHospital) {
		this.inHospital = inHospital;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPart() {
		return part;
	}

	public void setPart(int part) {
		this.part = part;
	}

	public int getCash() {
		return cash;
	}

	public void setCash(int cash) {
		this.cash = cash;
	}

	public int getNx() {
		return nx;
	}

	public void setNx(int nx) {
		this.nx = nx;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void debug() {
		System.out.println("???:" + name + ",????[" + x + "," + y + "].");
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public void startGameInit() {
		// ???????????
		this.initPlayerIMG();
		// ???õ??????60px??????????
		this.lastTime = Control.rate / 3;
		// ??????????
		this.cash = GameRunning.PLAYER_CASH;
	}

	@Override
	public void updata(long tick) {
		this.nowTick = tick;
		// ??????
		if (this.startTick < this.nowTick && this.nextTick >= this.nowTick) {
			this.control.movePlayer();
			// ·??????
			if (this.nextTick != this.nowTick) {
				this.control.prassBuilding();
			}
			// ????????????²???
			if (this.nextTick == this.nowTick) {
				this.control.playerStopJudge();
			}
		}
	}

}
