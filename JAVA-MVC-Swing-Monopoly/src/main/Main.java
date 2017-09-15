package main;

import javax.swing.JFrame;
import javax.swing.UIManager;

import ui.JFrameGame;
import ui.WaitFrame;
import ui.config.FrameConfig;

public class Main {

	static {
		// ???????
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e1) {
			
		}
	}

	public static void main(String[] args) {
		// ???????????
		WaitFrame wFrame = new WaitFrame();
		// ?????????????
		JFrameGame frame = new JFrameGame();
		// ??????????ô???
		new FrameConfig(wFrame,frame);
	}
}
