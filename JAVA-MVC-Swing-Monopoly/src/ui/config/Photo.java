package ui.config;

import java.awt.Image;

import javax.swing.ImageIcon;
/**
 * 
 * ??????????
 * 
 * */

public class Photo {
	
	/**
	 *  ?????????
	 */
	public static ImageIcon PLAYER_01_SELECTED = new ImageIcon("images/config/playerChoose/selected_01.png");
	/**
	 *  ?????????
	 */
	public static ImageIcon PLAYER_02_SELECTED = new ImageIcon("images/config/playerChoose/selected_02.png");
	/**
	 *  ???
	 */
	public static ImageIcon[] BUTTON_LEFT = {
		new ImageIcon("images/config/???/normal.png"),
		new ImageIcon("images/config/???/disabled.png"),
		new ImageIcon("images/config/???/mouseOver.png"),
		new ImageIcon("images/config/???/pressed.png")
	};
	/**
	 *  ????
	 */
	public static ImageIcon[] BUTTON_RIGHT = {
		new ImageIcon("images/config/????/normal.png"),
		new ImageIcon("images/config/????/disabled.png"),
		new ImageIcon("images/config/????/mouseOver.png"),
		new ImageIcon("images/config/????/pressed.png")
	};
	/**
	 * ?????????
	 * */
	public static ImageIcon[] PLAYER_CHOOSE = {
		new ImageIcon("images/player/0/head_h5.png"),
		new ImageIcon("images/player/1/head_h5.png"),
		new ImageIcon("images/player/2/head_h5.png"),
		new ImageIcon("images/player/3/head_h5.png")
	};
}
