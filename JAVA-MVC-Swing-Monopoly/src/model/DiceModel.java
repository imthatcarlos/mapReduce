package model;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import control.Control;
import control.GameRunning;


import ui.JPanelGame;
import util.FileUtil;

/**
 * 
 * ????
 * 
 * 
 * @author MOVELIGHTS
 * 
 */
public class DiceModel extends Tick implements Port {

	/**
	 * 
	 * ???????
	 * 
	 */
	private int point;
	private Image[] img = new Image[] {
			new ImageIcon("images/dice/dice_play_01.png").getImage(),
			new ImageIcon("images/dice/dice_play_02.png").getImage(),
			new ImageIcon("images/dice/dice_play_03.png").getImage(),
			new ImageIcon("images/dice/dice_play_04.png").getImage(),
			new ImageIcon("images/dice/dice_play_05.png").getImage() };
 	/**
	 * 
	 * ??????????
	 * 
	 */
	private Image[] dicePoints = new Image[] {
			new ImageIcon("images/dice/point/1.png").getImage(),
			new ImageIcon("images/dice/point/2.png").getImage(),
			new ImageIcon("images/dice/point/3.png").getImage(),
			new ImageIcon("images/dice/point/4.png").getImage(),
			new ImageIcon("images/dice/point/5.png").getImage(),
			new ImageIcon("images/dice/point/6.png").getImage() };

	/**
	 * ????????
	 */
	public  ImageIcon[] diceIMG = new ImageIcon[] {
			new ImageIcon("images/string/dice.png"),
			new ImageIcon("images/string/diceEnter.png"),
			new ImageIcon("images/string/dicePress.png"),
			new ImageIcon("images/string/diceBan.png")
	};
	/**
	 * 
	 * ???????
	 * 
	 */
	private GameRunning running = null;

	/**
	 * 
	 * ???????????????????????????????
	 * 
	 */
	private int imgCount;
	/**
	 * 
	 * ?????????????
	 * 
	 */
	public static int DICE_RUNNING = 1;
	/**
	 * ?????????????
	 */
	public static int DICE_POINT = 2;
	/**
	 * ????????
	 */
	private int diceState;
	/**
	 * ??????????
	 */
	boolean showButton;

	public DiceModel(GameRunning running) {
		this.running = running;
	}




	public void addImgCount(int add) {
		this.imgCount+=add;
	}




	public ImageIcon[] getDiceIMG() {
		return diceIMG;
	}




	public Image[] getDicePoints() {
		return dicePoints;
	}




	public int getImgCount() {
		return imgCount;
	}
	
	/**
	 * 
	 * ???????????
	 * 
	 */
	public Image getNowImg(){
		this.imgCount = this.imgCount % this.img.length;
		return this.img[this.imgCount];
	}


	public void setDiceState(int diceState) {
		this.diceState = diceState;
	}

	public int getDiceState() {
		return diceState;
	}




	public void setShowButton(boolean showButton) {
		this.showButton = showButton;
	}




	@Override
	public void updata(long tick) {
		this.nowTick = tick;
		// ???????
		this.checkButton();
	}

	/**
	 * 
	 * ???????
	 * 
	 */
	private void checkButton() {
		if (this.running.getNowPlayerState() == GameRunning.STATE_THROWDICE) {// "??????"
			this.showButton = true;
		} else {
			this.showButton = false;
		}
	}
	
	public GameRunning getRunning() {
		return running;
	}



	public boolean isShowDiceLabel() {
		return showButton;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public void startGameInit() {
		
		// ??????????????????????????
		this.diceState = DiceModel.DICE_POINT;
		// ??????????????
		this.showButton = true;
		// ????????????????
		this.lastTime = Control.rate * 1;
	}

}
