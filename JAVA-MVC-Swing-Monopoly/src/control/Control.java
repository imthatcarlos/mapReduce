package control;

import java.applet.AudioClip;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JApplet;
import javax.swing.JOptionPane;

import model.BackgroundModel;
import model.BuildingsModel;
import model.DiceModel;
import model.EffectModel;
import model.EventsModel;
import model.LandModel;
import model.PlayerModel;
import model.Port;
import model.TextTipModel;
import model.buildings.Building;
import model.buildings.Hospital;
import model.buildings.News;
import model.buildings.Origin;
import model.buildings.Park;
import model.buildings.Point;
import model.buildings.Prison;
import model.buildings.Shop_;
import model.card.Card;
import model.card.TortoiseCard;
import music.Music;
import ui.JPanelGame;
import util.FileUtil;
import util.MyThread;
import context.GameState;

/**
 * 
 * ??????????
 * 
 * 
 * @author Administrator
 * 
 */
public class Control {
	/**
	 * 
	 * ???tick?
	 * 
	 */
	public static long tick;
	/**
	 * 
	 * ÿ??????????
	 * 
	 */
	public static int rate = 30;
	/**
	 * 
	 * ????????
	 * 
	 */
	private JPanelGame panel;
	/**
	 * 
	 * ???????
	 * 
	 */
	private GameRunning run = null;

	private List<Port> models = new ArrayList<Port>();
	private List<PlayerModel> players = null;
	private BuildingsModel building = null;
	private BackgroundModel background = null;
	private LandModel land = null;
	private TextTipModel textTip = null;
	private DiceModel dice = null;
	private EventsModel events = null;
	private EffectModel effect = null;

	private Music music = null;
	
	/**
	 * 
	 * ????????
	 * 
	 */
	private Timer gameTimer = null;

	public Control() {
		// ????????????
		this.run = new GameRunning(this, players);
		// ????????????
		this.initClass();
		// ??????????????????
		this.run.setPlayers(players);
	}

	public void setPanel(JPanelGame panel) {
		this.panel = panel;
	}

	/**
	 * 
	 * ????????????
	 * 
	 */
	private void initClass() {
		// ????????µ??¼????
		this.events = new EventsModel();
		this.models.add(events);
		// ????????µ??????????
		this.effect = new EffectModel();
		this.models.add(effect);
		// ?????µ???????
		this.background = new BackgroundModel();
		this.models.add(background);
		// ?????µ????????
		this.land = new LandModel();
		this.models.add(land);
		// ?????µ??????????
		this.textTip = new TextTipModel();
		this.models.add(textTip);
		// ????????µ???????
		this.building = new BuildingsModel(land);
		this.models.add(building);
		// ????????µ????????
		this.players = new ArrayList<PlayerModel>();
		this.players.add(new PlayerModel(1, this));
		this.players.add(new PlayerModel(2, this));
		this.models.add(players.get(0));
		this.models.add(players.get(1));
		// ????????µ????????
		this.dice = new DiceModel(run);
		this.models.add(dice);
		
		// ?????????????
		this.music = new Music();
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void createGameTimer() {
		this.gameTimer = new Timer();
		this.gameTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				tick++;
				// ???¸?????
				for (Port temp : models) {
					temp.updata(tick);
				}
				// UI????
				panel.repaint();
			}
		}, 0, (1000 / rate));
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public void start() {
		// ????????????
		this.createGameTimer();
		// ??¶?????????
		for (Port temp : this.models) {
			temp.startGameInit();
		}
		// ??????????
		this.run.startGameInit();
		// panel ?????
		this.panel.startGamePanelInit();
		// ???????????
		this.startMusic();
		// ????????????????
		this.effect.showImg("start");
	}

	
	/**
	 * 
	 * ???????????
	 * 
	 */
	private void startMusic() {
		music.start();
	}

	public List<PlayerModel> getPlayers() {
		return players;
	}

	public BuildingsModel getBuilding() {
		return building;
	}

	public BackgroundModel getBackground() {
		return background;
	}

	public LandModel getLand() {
		return land;
	}

	public EffectModel getEffect() {
		return effect;
	}

	public TextTipModel getTextTip() {
		return textTip;
	}

	public GameRunning getRunning() {
		return run;
	}

	public DiceModel getDice() {
		return dice;
	}

	public EventsModel getEvents() {
		return events;
	}

	public JPanelGame getPanel() {
		return panel;
	}

	/**
	 * 
	 * 
	 * ????????
	 * 
	 * 
	 */
	public void pressButton() {
		PlayerModel player = this.run.getNowPlayer();
		if (player.getInHospital() > 0 || player.getInPrison() > 0) {
			this.run.nextState();
			if (player.getInHospital() > 0) {
				this.textTip.showTextTip(player, player.getName() + "????.", 3);
			} else if (player.getInPrison() > 0) {
				this.textTip.showTextTip(player, player.getName() + "?????.", 3);
			}
			this.run.nextState();
		} else {
			// ??????????????????
			this.dice.setStartTick(Control.tick);
			// ????????????????????
			this.dice.setNextTick(this.dice.getStartTick()
					+ this.dice.getLastTime());
			// ???????????????????????
			this.dice.setPoint(this.run.getPoint());
			// ????????????????
			this.run.nextState();
			// ?????????????????
			this.run.getNowPlayer().setStartTick(this.dice.getNextTick() + 10);
			this.run.getNowPlayer().setNextTick(
					this.run.getNowPlayer().getStartTick()
							+ this.run.getNowPlayer().getLastTime()
							* (this.run.getPoint() + 1));
		}
	}

	/**
	 * 
	 * 
	 * ??????
	 * 
	 * 
	 */
	public void movePlayer() {
		// ???????
		for (int i = 0; i < (60 / this.run.getNowPlayer().getLastTime()); i++) {
			// ??????
			if (GameRunning.MAP == 1){
				this.move01();
			} else if (GameRunning.MAP == 2){
				this.move02();
			} else if (GameRunning.MAP == 3) {
				this.move03();
			}
		}
	}

	/**
	 * 
	 * ??????·??????
	 * 
	 */
	public void prassBuilding() {
		// ??????
		PlayerModel player = this.run.getNowPlayer();
		// ?õ????
		Building building = this.building.getBuilding(player.getY() / 60,
				player.getX() / 60);
		if (building != null && player.getX() % 60 == 0
				&& player.getY() % 60 == 0) {
			// ????????????¼?
			int event = building.passEvent();
			// ???????????¼?????
			disposePassEvent(building, event, player);
		}
	}

	/**
	 * 
	 * ?????????¼?????
	 * 
	 */
	private void disposePassEvent(Building b, int event, PlayerModel player) {
		switch (event) {
		case GameState.ORIGIN_PASS_EVENT:
			// ??????????
			passOrigin(b, player);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	private void passOrigin(Building b, PlayerModel player) {
		this.textTip.showTextTip(player, player.getName() + " ·????????? "
				+ ((Origin) b).getPassReward() + "???.", 3);
		player.setCash(player.getCash() + ((Origin) b).getPassReward());
	}

	/**
	 * 
	 * 
	 * ???????????
	 * 
	 * 
	 */
	private void move02() {
		int dice = this.run.getPoint() + 1;
		PlayerModel p = this.run.getNowPlayer();
		// ??????????
		int movePixel = 1;
		if (p.getX() < 12 * 60 && p.getY() == 0) {
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 *60 && p.getY() < 2 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() == 12 * 60 && p.getY() == 2 * 60){
			if ((int)(Math.random() * 2 ) == 0){
				p.setX(p.getX() - movePixel);
			} else {
				p.setY(p.getY() + movePixel);
			}
		} else if (p.getX() == 12 * 60 && p.getY() > 2 * 60 && p.getY() < 4 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 8 * 60 && p.getX() <= 12 * 60 && p.getY() == 4 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 8 * 60 && p.getY() == 4 * 60){
			if ((int)(Math.random() * 2 ) == 0){
				p.setX(p.getX() - movePixel);
			} else {
				p.setY(p.getY() + movePixel);
			}
		} else if (p.getX() > 4 * 60 && p.getX() < 8 * 60 && p.getY() == 4 * 60) {
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 8 * 60 && p.getY() > 4 * 60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() >  4 * 60 && p.getX() <= 8 * 60 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() > 4 * 60 && p.getX() < 12 * 60 && p.getY() == 2 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 4 * 60 && p.getY() >= 2 * 60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getX() <= 4 * 60 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0){
			p.setY(p.getY() - movePixel);
		}
	}
	
	/**
	 * 
	 * 
	 * ???????????
	 * 
	 * 
	 */
	private void move01() {
		int dice = this.run.getPoint() + 1;
		PlayerModel p = this.run.getNowPlayer();
		// ??????????
		int movePixel = 1;
		Boolean turn = dice % 2 != 0;
		if (p.getX() < 9 * 60 && p.getY() == 0) {
			// ????
			if (p.getX() == 4 * 60 && turn) {
				// ???????
				p.setY(p.getY() + movePixel);
			} else {
				p.setX(p.getX() + movePixel);
			}
		} else if (p.getX() == 9 * 60 && p.getY() >= 0 && p.getY() < 60) {
			// [0,9]
			// ??
			p.setY(p.getY() + movePixel);
		} else if (p.getX() >= 8 * 60 && p.getX() < 12 * 60
				&& p.getY() >= 1 * 60 && p.getY() <= 60 * 1.5) {
			// ??
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 * 60 && p.getY() >= 1 * 60
				&& p.getY() < 7 * 60) {
			// ??
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getY() == 7 * 60) {
			// ??
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0) {
			// ??
			p.setY(p.getY() - movePixel);
		} else if (p.getX() == 4 * 60 && p.getY() > 0 && p.getY() < 7 * 60) {
			// ??
			p.setY(p.getY() + movePixel);
		}
	}
	/**
	 * 
	 * 
	 * ???????????
	 * 
	 * 
	 */
	private void move03() {
		PlayerModel p = this.run.getNowPlayer();
		// ??????????
		int movePixel = 1;
		if (p.getX() < 12 * 60 && p.getY() == 0) {
			p.setX(p.getX() + movePixel);
		} else if (p.getX() == 12 *60 && p.getY() < 7 * 60){
			p.setY(p.getY() + movePixel);
		} else if (p.getX() > 0 && p.getY() == 7 * 60){
			p.setX(p.getX() - movePixel);
		} else if (p.getX() == 0 && p.getY() > 0){
			p.setY(p.getY() - movePixel);
		}
	}
	/**
	 * 
	 * ????????????????
	 * 
	 */
	public void playerStopJudge() {
		// ??????
		PlayerModel player = this.run.getNowPlayer();
		if (player.getInHospital() > 0) {
			this.textTip.showTextTip(player, player.getName() + "???????,???????.",
					2);
			// ?????????
			this.run.nextState();
		} else if (player.getInPrison() > 0) {
			this.textTip.showTextTip(player, player.getName() + "????????,???????.",
					2);
			// ?????????
			this.run.nextState();
		} else {
			// ?????????????? ?¼????
			this.playerStop();
		}
	}

	/**
	 * 
	 * ????????????²???
	 * 
	 */
	public void playerStop() {
		// ??????
		PlayerModel player = this.run.getNowPlayer();
		// ?õ????
		Building building = this.building.getBuilding(player.getY() / 60,
				player.getX() / 60);
		if (building != null) {// ???????
			int event = building.getEvent();
			// ???????????
			disposeStopEvent(building, event, player);

		}
	}

	/**
	 * 
	 * ????????¼?????
	 * 
	 * 
	 */
	private void disposeStopEvent(Building b, int event, PlayerModel player) {
		switch (event) {
		case GameState.HOSPITAL_EVENT:
			// ???????
			stopInHospital(b, player);
			break;
		case GameState.HUOSE_EVENT:
			// ?????????????
			stopInHouse(b, player);
			break;
		case GameState.LOTTERY_EVENT:
			// ????????????
			stopInLottery(b, player);
			break;
		case GameState.NEWS_EVENT:
			// ????????????
			stopInNews(b, player);
			break;
		case GameState.ORIGIN_EVENT:
			// ????????
			stopInOrigin(b, player);
			break;
		case GameState.PARK_EVENT:
			// ???????
			stopInPack(b, player);
			break;
		case GameState.POINT_EVENT:
			// ????????
			stopInPoint(b, player);
			break;
		case GameState.PRISON_EVENT:
			// ????????
			stopInPrison(b, player);
			break;
		case GameState.SHOP_EVENT:
			// ????????
			stopInShop(b, player);
			break;
		}

	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void stopInShop(Building b, PlayerModel player) {
		if (player.getNx() > 0){
		// ??????????????????
		((Shop_) b).createCards();
		// ???????????µ??????
		this.panel.getShop().addCards((Shop_) b);
		// ???????????????
		this.panel.getShop().moveToFront();
		} else {
			this.run.nextState();
		}
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void stopInPrison(Building b, PlayerModel player) {
		int days = (int) (Math.random() * 3) + 2;
		player.setInPrison(days);
		int random = (int) (Math.random() * ((Prison) b).getEvents().length);
		String text = ((Prison) b).getEvents()[random];
		this.textTip.showTextTip(player, player.getName() + text + "???"
				+ (days - 1) + "??.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void stopInPoint(Building b, PlayerModel player) {
		player.setNx(((Point) b).getPoint() + player.getNx());
		this.textTip.showTextTip(player, player.getName() + " ??? "
				+ ((Point) b).getPoint() + "???.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ???????
	 * 
	 */
	private void stopInPack(Building b, PlayerModel player) {
		int random = (int) (Math.random() * ((Park) b).getImgageEvents().length);

		switch (random) {
		case 0:
		case 1:
			// ??????
			player.setCash(player.getCash() - 1);
			break;
		case 2:
			// ??200???
			player.setCash(player.getCash() - 200);
			break;
		case 3:
			// ??200???
			player.setCash(player.getCash() + 200);
			break;
		}
		// ???¼???????¼?
		this.events.showImg(((Park) b).getImgageEvents()[random], 3, new Point(
				320, 160, 0));
		new Thread(new MyThread(run, 3)).start();
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void stopInOrigin(Building b, PlayerModel player) {
		this.textTip.showTextTip(player, player.getName() + " ?????????????? "
				+ ((Origin) b).getReward() + "???.", 3);
		player.setCash(player.getCash() + ((Origin) b).getReward());
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ????????????
	 * 
	 */
	private void stopInNews(Building b, PlayerModel player) {
		int random = (int) (Math.random() * ((News) b).getImgageEvents().length);
		switch (random) {
		case 0:
		case 1:
			// ????????
			player.setInHospital(player.getInHospital() + 4);
			// ????????????????
			if (LandModel.hospital != null) {
				player.setX(LandModel.hospital.x);
				player.setY(LandModel.hospital.y);
			}
			break;
		case 2:
		case 3:
			player.setCash(player.getCash() - 1000);
			break;
		case 4:
			player.setCash(player.getCash() - 1500);
			break;
		case 5:
			player.setCash(player.getCash() - 2000);
			break;
		case 6:
		case 7:
			player.setCash(player.getCash() - 300);
			break;
		case 8:
			player.setCash(player.getCash() - 400);
			break;
		case 9:
			// ?????????????¼?
			if (player.getNx() < 40) {
				stopInNews(b, player);
				return;
			}
			player.setNx(player.getNx() - 40);
			break;
		case 10:
			player.setCash(player.getCash() - 500);
			break;
		case 11:
			player.setCash(player.getCash() + 1000);
			break;
		case 12:
		case 13:
			player.setCash(player.getCash() + 2000);
			break;
		case 14:
			player.setCash(player.getCash() + 3999);
			player.setNx(player.getNx() + 100);
			break;
		case 15:
			player.setNx(player.getNx() + 300);
			break;
		case 16:
			for (int i = 0; i  < player.getCards().size();i++){
//				System.out.println(player.getCards().get(i).getcName());
				// ?????
				if (player.getCards().get(i).getName().equals("CrossingCard")){
					player.getCards().remove(i);
					// ?????????.
					player.getOtherPlayer().setCash(player.getOtherPlayer().getCash() - 3000);
					this.textTip.showTextTip(player, player.getName() + "?????\"3000?\"????? "+ player.getOtherPlayer().getName()+"????????????????.", 6);
					this.events.showImg(((News) b).get3000(), 3, new Point(
							420, 160, 0));
					new Thread(new MyThread(run, 3)).start();
					return;
				}
			}
			player.setCash(player.getCash() - 3000);
			break;
		}
		// ???¼???????¼?
		this.events.showImg(((News) b).getImgageEvents()[random], 3, new Point(
				420, 160, 0));
		new Thread(new MyThread(run, 3)).start();
	}

	/**
	 * 
	 * ????????????
	 * 
	 */
	private void stopInLottery(Building b, PlayerModel player) {
		// ?????
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * 
	 * ?????????????
	 * 
	 * 
	 */
	private void stopInHouse(Building b, PlayerModel player) {
		if (b.isPurchasability()) {// ??????
			if (b.getOwner() == null) { // ???????
				// ????????
				this.buyHouse(b, player);
			} else {// ???????
				if (b.getOwner().equals(player)) {// ???????
					// ??????????????
					this.upHouseLevel(b, player);
				} else {// ???????
					// ?????????
					this.giveTax(b, player);
				}
			}
		}
	}

	/**
	 * 
	 * ?????????
	 * 
	 * 
	 */
	private void giveTax(Building b, PlayerModel player) {
		if (b.getOwner().getInHospital() > 0) {
			// ??????????
			this.textTip.showTextTip(player, b.getOwner().getName()
					+ "??????,????·??.", 3);
		} else if (b.getOwner().getInPrison() > 0) {
			// ??????????
			this.textTip.showTextTip(player, b.getOwner().getName()
					+ "???????,????·??.", 3);
		} else {
			int revenue = b.getRevenue();
			// ??????????
			player.setCash(player.getCash() - revenue);
			// ????õ????
			b.getOwner().setCash(b.getOwner().getCash() + revenue);
			// ??????????
			this.textTip.showTextTip(player, player.getName() + "????"
					+ b.getOwner().getName() + "????????·??:" + revenue + "???.", 3);

		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ??????????????
	 * 
	 */
	private void upHouseLevel(Building b, PlayerModel player) {
		if (b.canUpLevel()) {
			// ????????
			int price = b.getUpLevelPrice();
			String name = b.getName();
			String upName = b.getUpName();
			int choose = JOptionPane.showConfirmDialog(null,
					"?????:" + player.getName() + "\r\n" + "????????????\r\n" + name
							+ "??" + upName + "\r\n" + "???" + price + " ???.");
			if (choose == JOptionPane.OK_OPTION) {
				if (player.getCash() >= price) {
					b.setLevel(b.getLevel() + 1);
					// ???????????
					player.setCash(player.getCash() - price);
					// ??????????
					this.textTip.showTextTip(player, player.getName() + " ?? "
							+ name + " ?????? " + upName + ".?????? " + price
							+ "???. ", 3);
				} else {
					// ??????????
					this.textTip.showTextTip(player, player.getName()
							+ " ??????,???????. ", 3);
				}
			}
		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ????????
	 * 
	 * 
	 */
	private void buyHouse(Building b, PlayerModel player) {
		int price = b.getUpLevelPrice();
		int choose = JOptionPane.showConfirmDialog(
				null,
				"?????:" + player.getName() + "\r\n" + "????????????\r\n"
						+ b.getName() + "??" + b.getUpName() + "\r\n" + "???"
						+ price + " ???.");

		if (choose == JOptionPane.OK_OPTION) {
			// ????
			if (player.getCash() >= price) {
				b.setOwner(player);
				b.setLevel(1);
				// ???÷???????????????????
				player.getBuildings().add(b);
				// ???????????
				player.setCash(player.getCash() - price);
				this.textTip.showTextTip(player, player.getName()
						+ " ???????????.??????: " + price + "???. ", 3);
			} else {
				this.textTip.showTextTip(player, player.getName()
						+ " ??????,???????. ", 3);
			}
		}
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ???????
	 * 
	 */
	private void stopInHospital(Building b, PlayerModel player) {
		int days = (int) (Math.random() * 4) + 2;
		player.setInHospital(days);
		int random = (int) (Math.random() * ((Hospital) b).getEvents().length);
		String text = ((Hospital) b).getEvents()[random];
		this.textTip.showTextTip(player, player.getName() + text + "???"
				+ (days - 1) + "??.", 3);
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public void cardsBuff() {
		List<Card>delete = new ArrayList<Card>();
		for (Card a : this.run.getNowPlayer().getEffectCards()) {
			int buff = a.cardBuff();
			cardBuff(a, buff,delete);
		}
		this.run.getNowPlayer().getEffectCards().removeAll(delete);
		this.run.nextState();
	}

	/**
	 * 
	 * ??????????
	 * 
	 * 
	 */
	private void cardBuff(Card card, int buff,List<Card>delete) {
		switch (buff) {
		case GameState.CARD_BUFF_TORTOISE:
			// ???BUff
			buffTortoiseCard((TortoiseCard) card,delete);
			break;
		case GameState.CARD_BUFF_STOP:
			// ?????Buff
			buffStopCard(card,delete);
			break;
		}
	}

	/**
	 * 
	 * ?????Buff
	 * 
	 * 
	 */
	private void buffStopCard(Card card,List<Card>delete) {
		// ??????????
		this.textTip.showTextTip(card.geteOwner(), card.geteOwner().getName()
				+ " ??\"?????\" ???ã????????.. ", 2);
		// ??????
		delete.add(card);
		this.run.nextState();
		new Thread(new MyThread(run, 1)).start();
	}
	

	/**
	 * 
	 * ???BUff
	 * 
	 */

	private void buffTortoiseCard(TortoiseCard card,List<Card>delete) {
		if (card.getLife() <= 0) {
			delete.add(card);
			return;
		} else {
			card.setLife(card.getLife() - 1);
		}
		this.textTip.showTextTip(card.geteOwner(), card.geteOwner().getName()
				+ " ??\"???\" ???ã??????????.. ", 2);
		this.run.setPoint(0);
	}

	/**
	 * 
	 * ??ÿ??
	 * 
	 */
	public void useCards() {
		PlayerModel p = this.run.getNowPlayer();
		while (true) {
			if (p.getCards().size() == 0) {
				// ?????????????
				this.run.nextState();
				break;
			} else {
				Object[] options = new Object[p.getCards().size() + 1];
				int i;
				for (i = 0; i < p.getCards().size(); i++) {
					options[i] = p.getCards().get(i).getcName() + "\r\n";
				}
				options[i] = "????,?????";
				int response = JOptionPane.showOptionDialog(null,
						" " + p.getName() + "??????????õ???", "?????ý??.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
				if (response != i && response != -1) {
					// ??ÿ??
					int th = p.getCards().get(response).useCard();
					// ??ÿ??
					useCard(p.getCards().get(response), th);
				} else {
					// ????ã????????.
					this.run.nextState();
					break;
				}
			}
		}
	}

	/**
	 * 
	 * ??ÿ??
	 * 
	 */
	private void useCard(Card card, int th) {
		switch (th) {
		case GameState.CARD_ADDLEVEL:
			// ??ü???
			useAddLevelCard(card);
			break;
		case GameState.CARD_AVERAGERPOOR:
			// ??þ????
			useAveragerPoorCard(card);
			break;
		case GameState.CARD_CHANGE:
			// ??û????
			useChangeCard(card);
			break;
		case GameState.CARD_CONTROLDICE:
			// ???????????
			useControlDiceCard(card);
			break;
		case GameState.CARD_HAVE:
			// ??ù????
			useHaveCard(card);
			break;
		case GameState.CARD_REDUCELEVEL:
			// ??ý?????
			useReduceLevelCard(card);
			break;
		case GameState.CARD_ROB:
			// ???????
			useRobCard(card);
			break;
		case GameState.CARD_STOP:
			// ????????
			useStopCard(card);
			break;
		case GameState.CARD_TALLAGE:
			// ??ò????
			useTallageCard(card);
			break;
		case GameState.CARD_TORTOISE:
			// ??????
			useTortoiseCard(card);
			break;
		case GameState.CARD_TRAP:
			// ????????
			useTrapCard(card);
			break;
		case GameState.CARD_CROSSING:
			// ??ü????
			useCrossingCard(card);
			break;
		}
	}

	/**
	 * 
	 * ??ü????
	 * 
	 */
	private void useCrossingCard(Card card) {
		Object[] options1 = { "???????" };
		JOptionPane.showOptionDialog(null, " ?????????¼??????????????.",
				"?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options1,
				options1[0]);
	}

	/**
	 * 
	 * ????????
	 * 
	 */
	private void useTrapCard(Card card) {
		Object[] options = { "??????", "???????" };
		int response = JOptionPane.showOptionDialog(null, "??????\"?????\"?? \""
				+ card.getOwner().getOtherPlayer().getName() + "\"????2???",
				"?????ý??.", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		if (response == 0) {
			// ???
			PlayerModel cPlayer = card.getOwner().getOtherPlayer();
			// ????????
			cPlayer.setInPrison(cPlayer.getInPrison() + 2);
			// ????????????????
			if (LandModel.prison != null) {
				cPlayer.setX(LandModel.prison.x);
				cPlayer.setY(LandModel.prison.y);
			}
			// ??????????
			this.textTip
					.showTextTip(card.getOwner(), card.getOwner().getName()
							+ " ????? \"?????\"???? \""
							+ card.getOwner().getOtherPlayer().getName()
							+ "\"????2??.", 2);
			// ????????
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * ??????
	 * 
	 * 
	 */
	private void useTortoiseCard(Card card) {
		Object[] options = { card.getOwner().getName(),
				card.getOwner().getOtherPlayer().getName(), "???????" };
		int response = JOptionPane.showOptionDialog(null,
				" ??????????????????\"???\".", "?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			card.getOwner().getEffectCards().add(card);
			card.seteOwner(card.getOwner());
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ??????????\"???\". ", 2);
			card.getOwner().getCards().remove(card);
		} else if (response == 1) {
			card.getOwner().getOtherPlayer().getEffectCards().add(card);
			card.seteOwner(card.getOwner().getOtherPlayer());
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ??\"" + card.getOwner().getOtherPlayer().getName()
					+ "\"?????\"???\". ", 2);
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * ??ò????
	 * 
	 * 
	 */
	private void useTallageCard(Card card) {
		Object[] options = { "??????", "???????" };
		int response = JOptionPane.showOptionDialog(null, "??????\"?????\"?? \""
				+ card.getOwner().getOtherPlayer().getName() + "\"?????? 10%????",
				"?????ý??.", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, options, options[0]);
		if (response == 0) {
			// ???
			int money = (int) (card.getOwner().getOtherPlayer().getCash() / 10);
			card.getOwner().setCash(card.getOwner().getCash() + money);
			card.getOwner()
					.getOtherPlayer()
					.setCash(card.getOwner().getOtherPlayer().getCash() - money);
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ????? \"?????\"???? \""
					+ card.getOwner().getOtherPlayer().getName()
					+ "\"?????? 10%???", 2);
			// ????????
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 
	 * ????????
	 * 
	 */
	private void useStopCard(Card card) {
		Object[] options = { card.getOwner().getName(),
				card.getOwner().getOtherPlayer().getName(), "???????" };
		int response = JOptionPane.showOptionDialog(null,
				" ??????????????????\"?????\".", "?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			card.getOwner().getEffectCards().add(card);
			card.seteOwner(card.getOwner());
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ??????????\"?????\". ", 2);
			card.getOwner().getCards().remove(card);
		} else if (response == 1) {
			card.getOwner().getOtherPlayer().getEffectCards().add(card);
			card.seteOwner(card.getOwner().getOtherPlayer());
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ??\"" + card.getOwner().getOtherPlayer().getName()
					+ "\"?????\"?????\". ", 2);
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * 
	 * ???????
	 * 
	 * 
	 */
	private void useRobCard(Card card) {
		if (card.getOwner().getCards().size() >= PlayerModel.MAX_CAN_HOLD_CARDS) {
			// ??????
			Object[] options = { "???????" };
			JOptionPane.showOptionDialog(null, " ??????????????????????????\"????\"",
					"?????ý??.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		} else if (card.getOwner().getOtherPlayer().getCards().size() == 0) {
			// ??????
			Object[] options = { "???????" };
			JOptionPane.showOptionDialog(null, " \""
					+ card.getOwner().getOtherPlayer().getName()
					+ "\"û????????????\"????\"", "?????ý??.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		} else {
			PlayerModel srcPlayer = card.getOwner().getOtherPlayer();
			// ????????
//			System.out.println(srcPlayer.getCards().size() + "zhang");
			Card getCard = srcPlayer.getCards().get((int) (srcPlayer.getCards().size() * Math.random()));
			// ?????????
			srcPlayer.getCards().remove(getCard);
			// ??????????
			card.getOwner().getCards().add(getCard);
			// ?????ÿ???????
			getCard.setOwner(card.getOwner());
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ????? \"????\"???????? \"" + srcPlayer.getName() + "\"?????\""
					+ getCard.getcName() + ".\". ", 2);
			// ????????
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * ??ý?????
	 * 
	 */
	private void useReduceLevelCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// ?????????
			if (building.getLevel() > 0) { // ???????
				// ????
				building.setLevel(building.getLevel() - 1);
				// ??????????
				this.textTip.showTextTip(card.getOwner(), card.getOwner()
						.getName()
						+ " ????? \"??????\"????\""
						+ card.getOwner().getOtherPlayer().getName()
						+ "\"??????????????. ", 2);
				// ????????
				card.getOwner().getCards().remove(card);
			} else {
				// ??????,???????
				Object[] options = { "???????" };
				JOptionPane.showOptionDialog(null, " ?????????????", "?????ý??.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
			}
		} else {
			// ??????.
			Object[] options = { "???????" };
			JOptionPane.showOptionDialog(null, " ????????????øÿ??.", "?????ý??.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * ??ù????
	 * 
	 */
	private void useHaveCard(Card card) {
		// ?õ????
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// ?????????
			Object[] options = { "??????", "???????" };
			int response = JOptionPane.showOptionDialog(null,
					"??????\"?????\"??????????????????" + building.getAllPrice() + " ???.",
					"?????ý??.", JOptionPane.YES_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
			if (response == 0) {
				if (card.getOwner().getCash() >= building.getAllPrice()) {
					// ??????
					building.getOwner().setCash(
							building.getOwner().getCash()
									+ building.getAllPrice());
					card.getOwner().setCash(
							card.getOwner().getCash() - building.getAllPrice());
					building.setOwner(card.getOwner());
					// ??????????
					this.textTip.showTextTip(card.getOwner(), card.getOwner()
							.getName() + " ????? \"?????\"???????????????. ", 2);
					// ????????
					card.getOwner().getCards().remove(card);
				} else {
					Object[] options1 = { "???????" };
					JOptionPane.showOptionDialog(null, " ???????????????!",
							"?????ý??.", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options1,
							options1[0]);
				}
			}
		} else {
			Object[] options1 = { "???????" };
			JOptionPane.showOptionDialog(null, "??????????øÿ??.", "?????ý??.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options1, options1[0]);
		}
	}

	/**
	 * 
	 * 
	 * ???????????
	 * 
	 * 
	 */
	private void useControlDiceCard(Card card) {
		Object[] options = { "1??", "2??", "3??", "4??", "5??", "6??", "???????" };
		int response = JOptionPane.showOptionDialog(null,
				"??????\"????????\"???????????", "?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == -1 || response == 6) {
			return;
		} else {
			// ???
			this.run.setPoint(response);
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ????? \"????????\".", 2);
			// ????????
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * ??û????
	 * 
	 */
	private void useChangeCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner().getOtherPlayer())) {// ????????
			Object[] options = { "??????", "???????" };
			int response = JOptionPane.showOptionDialog(null,
					"??????\"?????\"??????????????????????????", "?????ý??.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
			if (response == 0) {
				// ???????????
				int thisBuildingLevel = building.getLevel();
				Building changeBuilding = null;
				for (Building a : card.getOwner().getBuildings()) {
					if (a.getLevel() == thisBuildingLevel) {
						changeBuilding = a;
						break;
					}
				}
				// ???????????
				if (changeBuilding != null) {
					changeBuilding.setOwner(card.getOwner().getOtherPlayer());
					building.setOwner(card.getOwner());
					// ??????????
					this.textTip.showTextTip(card.getOwner(), card.getOwner()
							.getName()
							+ " ????? \"?????\"?????????????"
							+ card.getOwner().getOtherPlayer().getName()
							+ "?õ???????????.. ", 2);
					// ????????
					card.getOwner().getCards().remove(card);
				} else {
					Object[] options1 = { "???????" };
					JOptionPane.showOptionDialog(null, " ?????????????\"?????\"",
							"?????ý??.", JOptionPane.YES_OPTION,
							JOptionPane.PLAIN_MESSAGE, null, options1,
							options1[0]);
				}
			}
		} else {
			Object[] options = { "???????" };
			JOptionPane.showOptionDialog(null, " ?????????????\"?????\"", "?????ý??.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * ??þ????
	 * 
	 */
	private void useAveragerPoorCard(Card card) {
		Object[] options = { "??????", "???????" };
		int response = JOptionPane.showOptionDialog(null,
				"??????\"?????\"????????????", "?????ý??.", JOptionPane.YES_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
		if (response == 0) {
			// ???
			int money = (int) (card.getOwner().getCash() + card.getOwner()
					.getOtherPlayer().getCash()) / 2;
			card.getOwner().setCash(money);
			card.getOwner().getOtherPlayer().setCash(money);
			// ??????????
			this.textTip.showTextTip(card.getOwner(), card.getOwner().getName()
					+ " ????? \"?????\"???????????????,?????????????:" + money + " ???. ", 2);

			// ????????
			card.getOwner().getCards().remove(card);
		}
	}

	/**
	 * 
	 * ??ü???
	 * 
	 */

	private void useAddLevelCard(Card card) {
		Building building = this.building.getBuilding(
				card.getOwner().getY() / 60, card.getOwner().getX() / 60);
		if (building.getOwner() != null
				&& building.getOwner().equals(card.getOwner())) {// ??????????
			if (building.canUpLevel()) { // ??????
				// ????
				building.setLevel(building.getLevel() + 1);
				// ??????????
				this.textTip.showTextTip(card.getOwner(), card.getOwner()
						.getName() + " ????? \"????\"?????????????????. ", 2);
				// ????????
				card.getOwner().getCards().remove(card);
			} else {
				// ??????,????????
				Object[] options = { "???????" };
				JOptionPane.showOptionDialog(null, " ??????????????.", "?????ý??.",
						JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE,
						null, options, options[0]);
			}
		} else {
			// ??????.
			Object[] options = { "???????" };
			JOptionPane.showOptionDialog(null, " ????????????øÿ??.", "?????ý??.",
					JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE, null,
					options, options[0]);
		}
	}

	/**
	 * 
	 * ??????
	 * 
	 */
	public void exitShop() {
		new Thread(new MyThread(run, 1)).start();
	}

	/**
	 * 
	 * ????????????
	 * 
	 * 
	 */
	public void buyCard(Shop_ shop) {
		int chooseCard = this.panel.getShop().getChooseCard();
		if (chooseCard >= 0
				&& this.panel.getShop().getCard().get(chooseCard) != null) {
			// ????? ?????????
			if (this.buyCard(shop, chooseCard)) {
				// ?????????
				this.panel.getShop().getCard().get(chooseCard).setEnabled(false);
				// ???????????
				this.panel.getShop().setChooseCard(-1);
			}
		}
	}

	/**
	 * 
	 * ?????
	 * 
	 * 
	 */
	public boolean buyCard(Shop_ shop, int p) {
		if (this.panel.getShop().getCard().get(p) != null) {
			if (this.run.getNowPlayer().getCards().size() >= PlayerModel.MAX_CAN_HOLD_CARDS) {
				JOptionPane.showMessageDialog(null, "?????????:"
						+ PlayerModel.MAX_CAN_HOLD_CARDS + "????,????????????????!");
				return false;
			}
			if (this.run.getNowPlayer().getNx() < shop.getCards().get(p)
					.getPrice()) {
				JOptionPane.showMessageDialog(null, "?????????:"
						+ shop.getCards().get(p).getPrice() + "???,????????.");
				return false;
			}
			// ???ÿ???????
			shop.getCards().get(p).setOwner(this.run.getNowPlayer());
			// ????????????????
			this.run.getNowPlayer().getCards().add(shop.getCards().get(p));
			// ?????????
			this.run.getNowPlayer().setNx(
					this.run.getNowPlayer().getNx()
							- shop.getCards().get(p).getPrice());
		}
		return true;
	}

	/**
	 * 
	 * ???????~
	 * 
	 * 
	 * @param
	 */
	public void gameOver () {
		this.run.setNowPlayerState(GameRunning.GAME_STOP);
		this.panel.getBackgroundUI().moveToFront();
		this.panel.getRunning().moveToFront();
		this.panel.getPlayerInfo().moveToFront();
		this.panel.getEffect().moveToFront();
		this.music.gameOver();
		this.effect.showImg("timeover2");
		
	}
}
