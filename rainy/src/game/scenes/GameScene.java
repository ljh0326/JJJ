package game.scenes;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import game.GameFrame;
import game.Run;

@SuppressWarnings("serial")
public class GameScene extends JPanel implements KeyListener, Runnable {
	CardLayout card;
	Container c;
	Vector<String> vc;
	public static int heart_point; // hp 변수
	public static int score; // 점수 변수
	public static boolean reset = false;
	public static boolean game_pause = false; // 게임 일시정지 변수
	public static boolean game_start = false; // 게임 시작 변수
	ImageIcon heart_img = new ImageIcon("res/heart.png");
	JTextField text_field;
	JLabel word_label;
	JLabel word_label2;
	JLabel word_label3;
	JLabel word_label4;
	JLabel word_label5;
	
	//점수표
	static JLabel score_label;
	//hp바
	static JLabel heart_label;
	Random rand = new Random();
	int x,x2,x3,x4,x5, y, timer;
	//단어내용변수
	String word,word2,word3,word4,word5;

	public GameScene(int width, int height, Container c, CardLayout card, Vector<String> vc) {
		this.card = card;
		this.c = c;
		this.vc = vc;
		heart_point = 5;
		score = 0;
		this.setLayout(null);
		this.setBackground(Color.BLACK);
		setBounds(0, 0, width, height);

		score_label = new JLabel("Score : " + score);
		score_label.setForeground(Color.WHITE);
		score_label.setBounds(width - 100, 20, 150, 20);
		
		add(score_label);

		heart_label = new JLabel(heart_img);
		heart_label.setBounds(width, height, 200, 200);
		add(heart_label);

		text_field = new JTextField(30);
		text_field.setBounds((width - 150) / 2, height - 100, 150, 30);
		add(text_field);
		text_field.addKeyListener(this);
		
		word = vc.get(rand.nextInt(vc.size()));
		word2= vc.get(rand.nextInt(vc.size()));
		word3= vc.get(rand.nextInt(vc.size()));
		word4= vc.get(rand.nextInt(vc.size()));
		word5= vc.get(rand.nextInt(vc.size()));
		word_label = new JLabel(word);
		word_label2=new JLabel(word2);
		word_label3=new JLabel(word3);
		word_label4=new JLabel(word4);
		word_label5=new JLabel(word5);
	
		word_label.setForeground(Color.WHITE);
		word_label.setSize(50, 20);
		
		word_label2.setForeground(Color.WHITE);
		word_label2.setSize(50,20);
		
		word_label3.setForeground(Color.WHITE);
		word_label3.setSize(50,20);
		
		word_label4.setForeground(Color.WHITE);
		word_label4.setSize(50,20);
		
		word_label5.setForeground(Color.WHITE);
		word_label5.setSize(50,20);
		
		add(word_label);
		add(word_label2);
		add(word_label3);
		add(word_label4);
		add(word_label5);
		Thread th = new Thread(this);
		th.start();
	}

	public static void init() {
		heart_point = 5;
		score = 0;
		score_label.setText("Score : " + score);
	}
	
	public synchronized void run() {
		while (true) {
			x = rand.nextInt(Run.width - 40) + 10; //단어 x좌표 랜덤으로 받음
			x2 = rand.nextInt(Run.width - 40) + 10;
			x3 = rand.nextInt(Run.width - 40) + 10;
			x4 = rand.nextInt(Run.width - 40) + 10;
			x5 = rand.nextInt(Run.width - 40) + 10;
			timer = rand.nextInt(50) + 10; //밀리초 랜덤으로 받음
			try {
				for (y = 0; y <= Run.height; y += 5) { //단어 위치 움직이는 제어문
					
					word_label.setLocation(x, y);
					word_label2.setLocation(x2,y*2);
					word_label3.setLocation(x3,y*3);
					word_label4.setLocation(x4,y*4);
					word_label5.setLocation(x5,y*5);
					Thread.sleep(timer); //떨어지는 속도는 timer밀리초
					if(GameScene.reset == true) { //단어 위치 리셋 조건문
						GameScene.reset = false;
						break;
					}
					
					//시작하면 스레드 작동
					if(game_start == true) 
						notify();
					//시작하기 전이면 스레드 정지
					else if(game_start == false) 
						wait();
					
					if(game_pause == true) wait(); //일시정지 true
					else if(game_pause == false) notify(); //일시정지 false
				}
				if (y >= Run.height + 5) {
					
					GameScene.heart_point--; //떨어지는 단어가 y좌표 최대 높이를 넘어가면 하트포인트 1개 깎임
					
					if (GameScene.heart_point <= 0) { // 하트포인트 0보다 작게 되면 게임오버
						card.show(c, GameFrame.OVER_CARD_INDEX); // 게임오버시 게임오버 화면으로 전환
						//game_start = false; // 단어 떨어지는 스레드 멈춤
					}
				}
				else {
				word = vc.get(rand.nextInt(vc.size()));
				word_label.setText(word);
				word_label2.setText(word);
				word_label3.setText(word);
				word_label4.setText(word);
				word_label5.setText(word);
				
				}
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) { // 엔터 입력 시 단어 확인
			if (text_field.getText().equals(word)) { // 텍스트필드의 단어와 떨어지는 단어가 같으면
				reset = true; // 단어 위치 리셋 -> 떨어지는 단어 없앰
				score += 50;
				score_label.setText("Score : " + score);
				text_field.setText(""); // 텍스트필드 초기화
			} else {
				heart_point--; // 입력한 단어가 떨어지는 단어와 다르면 하트포인트 하나 깎임
				text_field.setText(""); // 텍스트필드 초기화
				if (heart_point <= 0) { // 하트포인트 0보다 작게 되면 게임오버
					card.show(c, GameFrame.OVER_CARD_INDEX); // 게임오버시 게임오버 화면으로
																// 전환
					// WordDrop.game_start = false; // 단어 떨어지는 스레드 멈춤
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
