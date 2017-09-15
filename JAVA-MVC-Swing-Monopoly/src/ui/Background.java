package ui;

import java.awt.Graphics;
import java.awt.Image;

import util.FileUtil;

import model.BackgroundModel;


/**
 * 
 * ???????²?
 * 
 * @author MOVELIGHTS
 * 
 */
public class Background extends Layer {

	/**
	 * ??????
	 */
	private Image bg = null;
	/**
	 * 
	 * ???????
	 * 
	 */
	private BackgroundModel background = null;
	private JPanelGame panel;

	protected Background(int x, int y, int w, int h,
			BackgroundModel background,JPanelGame panel) {
		super(x, y, w, h);
		this.background = background;
		this.panel = panel;
	}

	public void paint(Graphics g) {
		// ???????
		this.paintBg(g);
	}
	/**
	 * 
	 * ??????????
	 * 
	 */
	public void moveToBack() {
		this.panel.getLayeredPane().moveToBack(this);
	}

	/**
	 * 
	 * ??????????
	 * 
	 */
	public void moveToFront() {
		this.panel.getLayeredPane().moveToFront(this);
	}
	
	/**
	 * 
	 * ???????????
	 * 
	 */
	public void paintBg(Graphics g){
		g.drawImage(this.bg, 0, 0, this.bg.getWidth(null),
				this.bg.getHeight(null), 0, 0, this.bg.getWidth(null),
				this.bg.getHeight(null), null);
	}
	

	@Override
	public void startPanel() {
		this.bg = background.getBg();
	}

}
