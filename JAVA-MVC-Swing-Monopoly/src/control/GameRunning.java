package control;

import java.util.List;

import model.Port;
import model.PlayerModel;
import model.Tick;

import ui.JPanelGame;

/**
 * 
 * ??????????
 * 
 * @author MOVELIGHTS
 * 
 */
public class GameRunning {

	/**
	 * ??????
	 */
	private List<PlayerModel> players = null;

	/**
	 * ??????????
	 */
	private PlayerModel nowPlayer = null;

	/**
	 * ??????????
	 */
	private int point;

	/**
	 * ?????ÿ????
	 */
	public static int STATE_CARD = 1;
	/**
	 * ???????????
	 */
	public static int STATE_CARD_EFFECT = 2;
	/**
	 * ?????????
	 */
	public static int STATE_THROWDICE = 3;
	/**
	 * ????????
	 */
	public static int STATE_MOVE = 4;
	/**
	 * 
	 * ????????
	 * 
	 */
	public static int GAME_STOP = 5;
	/**
	 * 
	 * ???????
	 * 
	 */
	private int nowPlayerState;

	/**
	 * 
	 * ???????????
	 * 
	 */
	public static int day = 1;

	/**
	 * 
	 * ??????????
	 * 
	 */
	public static int MAP = 1;
	/**
	 * 
	 * ??????????? - 1???????
	 * 
	 */
	public static int GAME_DAY = -1;
	/**
	 * 
	 * ??????????????????????-1???????
	 * 
	 */
	public static int MONEY_MAX = -1;

	/**
	 * 
	 * ?????????????
	 * 
	 */
	public static int PLAYER_CASH = 1000;

	private Control control;

	public GameRunning(Control control, List<PlayerModel> players) {
		this.control = control;
		this.players = players;
	}

	/**
	 * 
	 * ??õ???????
	 * 
	 */
	public int getNowPlayerState() {
		return this.nowPlayerState;
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	public void nextState() {
		// ?????????ó????
		if (gameContinue()) {
			if (this.nowPlayerState == STATE_CARD) {
				// ??????????
				this.nowPlayerState = STATE_CARD_EFFECT;
				// ???BUFF
				this.control.cardsBuff();
			} else if (this.nowPlayerState == STATE_CARD_EFFECT) {
				// ????????????
				this.nowPlayerState = STATE_THROWDICE;
			} else if (this.nowPlayerState == STATE_THROWDICE) {
				// ?????
				this.nowPlayerState = STATE_MOVE;
			} else if (this.nowPlayerState == STATE_MOVE) {
				// ???????
				this.nowPlayerState = STATE_CARD;
				this.nextPlayer();
				// ???????????
				this.setPoint((int) (Math.random() * 6));
				// ??????????????????? - STATE_CARD
				this.control.useCards();
			}
		}
	}

	/**
	 * 
	 * ?????????
	 * 
	 */
	public PlayerModel getNowPlayer() {
		return this.nowPlayer;
	}

	public void setNowPlayerState(int nowPlayerState) {
		this.nowPlayerState = nowPlayerState;
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public PlayerModel getNotNowPlayer() {
		return this.nowPlayer.equals(this.players.get(0)) ? this.players.get(1)
				: this.players.get(0);
	}

	/**
	 * ???????
	 */
	private void nextPlayer() {
		// ???????
		if (this.nowPlayer.getInPrison() > 0) {
			this.nowPlayer.setInPrison(this.nowPlayer.getInPrison() - 1);
		}
		if (this.nowPlayer.getInHospital() > 0) {
			this.nowPlayer.setInHospital(this.nowPlayer.getInHospital() - 1);
		}
		// ????
		if (this.nowPlayer.equals(this.players.get(0))) {
			this.nowPlayer = this.players.get(1);
		} else {
			this.nowPlayer = this.players.get(0);
			// ?????????????????
			day++;
		}
	}

	/**
	 * 
	 * ????????????
	 * 
	 * 
	 */
	public boolean gameContinue() {
		PlayerModel p1 = this.nowPlayer;
		PlayerModel p2 = this.nowPlayer.getOtherPlayer();
		// ????
		if (GAME_DAY > 0 && day >= GAME_DAY) {
			this.control.gameOver();
			return false;
		}
		// ?????
		if (MONEY_MAX > 0 && p1.getCash() >= MONEY_MAX) {
			this.control.gameOver();
			return false;
		} else if (MONEY_MAX > 0 && p2.getCash() >= MONEY_MAX) {
			this.control.gameOver();
			return false;
		}
		// ???
		if (p1.getCash() < 0) {
			this.control.gameOver();
			return false;
		} else if (p2.getCash() < 0) {
			this.control.gameOver();
			return false;
		}
		return true;
	}

	public void setPlayers(List<PlayerModel> players) {
		this.players = players;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getDay() {
		return day;
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public void startGameInit() {
		// ???????????
		this.nowPlayer = this.players.get(0);
		// ???????????????ÿ????
		this.nowPlayerState = STATE_CARD;
		// ?????????
		this.setPoint((int) (Math.random() * 6));
		// ????????ÿ??
		this.control.useCards();
	}

}
