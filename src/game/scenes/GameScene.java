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
	public static int heart_point; // hp ����
	public static int score; // ���� ����
	public static boolean reset = false;
	public static boolean game_pause = false; // ���� �Ͻ����� ����
	public static boolean game_start = false; // ���� ���� ����
	ImageIcon heart_img = new ImageIcon("res/heart.png");
	JTextField text_field;
	JLabel word_label;
	JLabel word_label2;
	JLabel word_label3;
	JLabel word_label4;
	JLabel word_label5;
	
	//����ǥ
	static JLabel score_label;
	//hp��
	static JLabel heart_label;
	Random rand = new Random();
	int x,x2,x3,x4,x5, y, timer;
	//�ܾ�뺯��
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
			x = rand.nextInt(Run.width - 40) + 10; //�ܾ� x��ǥ �������� ����
			x2 = rand.nextInt(Run.width - 40) + 10;
			x3 = rand.nextInt(Run.width - 40) + 10;
			x4 = rand.nextInt(Run.width - 40) + 10;
			x5 = rand.nextInt(Run.width - 40) + 10;
			timer = rand.nextInt(50) + 10; //�и��� �������� ����
			try {
				for (y = 0; y <= Run.height; y += 5) { //�ܾ� ��ġ �����̴� ���
					
					word_label.setLocation(x, y);
					word_label2.setLocation(x2,y*2);
					word_label3.setLocation(x3,y*3);
					word_label4.setLocation(x4,y*4);
					word_label5.setLocation(x5,y*5);
					Thread.sleep(timer); //�������� �ӵ��� timer�и���
					if(GameScene.reset == true) { //�ܾ� ��ġ ���� ���ǹ�
						GameScene.reset = false;
						break;
					}
					
					//�����ϸ� ������ �۵�
					if(game_start == true) 
						notify();
					//�����ϱ� ���̸� ������ ����
					else if(game_start == false) 
						wait();
					
					if(game_pause == true) wait(); //�Ͻ����� true
					else if(game_pause == false) notify(); //�Ͻ����� false
				}
				if (y >= Run.height + 5) {
					
					GameScene.heart_point--; //�������� �ܾ y��ǥ �ִ� ���̸� �Ѿ�� ��Ʈ����Ʈ 1�� ����
					
					if (GameScene.heart_point <= 0) { // ��Ʈ����Ʈ 0���� �۰� �Ǹ� ���ӿ���
						card.show(c, GameFrame.OVER_CARD_INDEX); // ���ӿ����� ���ӿ��� ȭ������ ��ȯ
						//game_start = false; // �ܾ� �������� ������ ����
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
		if (keyCode == KeyEvent.VK_ENTER) { // ���� �Է� �� �ܾ� Ȯ��
			if (text_field.getText().equals(word)) { // �ؽ�Ʈ�ʵ��� �ܾ�� �������� �ܾ ������
				reset = true; // �ܾ� ��ġ ���� -> �������� �ܾ� ����
				score += 50;
				score_label.setText("Score : " + score);
				text_field.setText(""); // �ؽ�Ʈ�ʵ� �ʱ�ȭ
			} else {
				heart_point--; // �Է��� �ܾ �������� �ܾ�� �ٸ��� ��Ʈ����Ʈ �ϳ� ����
				text_field.setText(""); // �ؽ�Ʈ�ʵ� �ʱ�ȭ
				if (heart_point <= 0) { // ��Ʈ����Ʈ 0���� �۰� �Ǹ� ���ӿ���
					card.show(c, GameFrame.OVER_CARD_INDEX); // ���ӿ����� ���ӿ��� ȭ������
																// ��ȯ
					// WordDrop.game_start = false; // �ܾ� �������� ������ ����
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
