package game.scenes;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import game.GameFrame;

@SuppressWarnings("serial")
public class OverScene extends JPanel implements ActionListener {
	CardLayout card;
	Container c;
	ImageIcon over_icon = new ImageIcon("res/over.jpg"); //게임오버화면 이미지 파일
	Image over_img = over_icon.getImage();
	public Scanner rank_in;
	public BufferedWriter buffer;
	public FileOutputStream rank_out;
	String rank_name="res/rank.txt";
	public OverScene(int width, int height, Container c, CardLayout card) {
		this.c = c;
		this.card = card;
		setLayout(null);
		setBounds(0, 0, width, height);
		
		JButton restart_btn = new JButton("다시하기");
		JButton record_btn = new JButton("기록하기");
		restart_btn.setBounds((width-100)/2, height/2, 100, 40);
		record_btn.setBounds((width-100)/2, (height+100)/2, 100, 40);
		restart_btn.addActionListener(this);
		record_btn.addActionListener(this);
		add(restart_btn);
		add(record_btn);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(over_img, 0, 0, getWidth(), getHeight(), this);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton)e.getSource();
		if(btn.getText().equals("다시하기")) {
			GameScene.init();
			card.show(c, GameFrame.GAME_CARD_INDEX);
		}
		else if(btn.getText().equals("기록하기")) {
			try{
				rank_in=new Scanner(new BufferedWriter(new FileWriter()))
			}
			catch(IOException e1){
				return;
			}
		}
	}
}
