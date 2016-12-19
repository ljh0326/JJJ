package game.scenes;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.GameFrame;

@SuppressWarnings("serial")
public class TitleScene extends JPanel implements ActionListener {
	ImageIcon start_icon = new ImageIcon("res/start.jpg"); //시작화면 이미지 파일
	Image start_img = start_icon.getImage();
	CardLayout card;
	Container c;
	
	public TitleScene(int width, int height, Container c, CardLayout card) {
		this.card = card;
		this.c = c;
		this.setLayout(null);
		setBounds(0, 0, width, height);
		
		JButton start_btn = new JButton("start");
		start_btn.setBounds((width-100)/2, height/2, 100, 40);
		add(start_btn);
		start_btn.addActionListener(this);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(start_img, 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText().equals("start")) {
			//WordDrop.game_start = true;
			GameScene.game_start=true;
			card.show(c, GameFrame.GAME_CARD_INDEX);
			
		}
	}
}
