package ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

import util.FrameUtil;

public class WaitFrame extends JFrame {

	public WaitFrame() {
		// ��������
		this.setTitle("�������-Java��");
		// ����Ĭ�Ϲر����ԣ����������
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ô��ڴ�С
		this.setSize(380, 370);
		// �������û��ı䴰�ڴ�С
		this.setResizable(false);
		// ����
		FrameUtil.setFrameCenter(this);
		add(new JLabel("�����У����Ժ�...",JLabel.CENTER));
		setVisible(true);
	}

}
