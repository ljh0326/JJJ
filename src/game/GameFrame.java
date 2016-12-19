package game;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import game.scenes.AddWordScene;
import game.scenes.GameScene;
import game.scenes.OverScene;
import game.scenes.TitleScene;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements KeyListener, ActionListener {
	TitleScene title_scene;
	GameScene game_scene;
	OverScene over_scene;
	AddWordScene add_word_scene;
	Vector<String> vc;
	Container c;
	JTextField add_field;
	CardLayout card;
	//메뉴들
	public static final String[] item = {"main", "rank", "play", "pause", "exit", "add word", "kor", "eng" };
	public Scanner rank_in;
	public FileOutputStream rank_out;
	public Scanner word_in;
	public BufferedWriter word_out;

	public static final String TITLE_CARD_INDEX = "title"; // 시작화면 카드레이아웃 인덱스
	public static final String GAME_CARD_INDEX = "game"; // 게임화면 카드레이아웃 인덱스
	public static final String OVER_CARD_INDEX = "over"; // 게임오버화면 카드레이아웃 인덱스
	public static final String ADD_WORD_CARD_INDEX = "add";
	public static final String KOR = "res/kor.txt";
	public static final String ENG = "res/eng.txt";
	boolean kor_bool = true;
	// JPanel rank_panel;

	GameFrame(int width, int height, String title) {
		setTitle(title);
		setResizable(false); // 프레임 크기조절 불가
		c = getContentPane();
		card = new CardLayout();
		setLayout(card);
		setSize(width, height);
		vc = new Vector<>(); // 벡터 객체 생성

		// 한글모드 디폴트로 읽어들임
		try {
			//떨어질 단어
			word_in = new Scanner(new BufferedReader(new FileReader(KOR)));
			while (word_in.hasNext()) {
				//벡터에 저장
				vc.add(word_in.next());
			}
			word_in.close();
		} catch (IOException e) {
			return;
		}
		
		title_scene = new TitleScene(width, height, c, card);
		//컨테이너에 부착
		c.add(title_scene, TITLE_CARD_INDEX);
		game_scene = new GameScene(width, height, c, card, vc);
		c.add(game_scene, GAME_CARD_INDEX);
		over_scene = new OverScene(width, height, c, card);
		c.add(over_scene, OVER_CARD_INDEX);
		add_word_scene = new AddWordScene(width, height, c, card, word_out);
		c.add(add_word_scene, ADD_WORD_CARD_INDEX);

		// Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		// super.setLocation((screen_size.width - width) / 2, (screen_size.height - height) / 2); //화면 위치 윈도우화면에서 가운데로 고정

		JMenuBar menu_bar = new JMenuBar(); // 메뉴바 생성
		JMenu menu_menu = new JMenu("메뉴"); // '메뉴' 메뉴 생성
		JMenu option_menu = new JMenu("옵션"); // '옵션' 메뉴 생성
		JMenuItem[] menu_item = new JMenuItem[item.length]; // 메뉴아이템 생성
		menu_bar.add(menu_menu);
		menu_bar.add(option_menu);
		for (int i = 0; i < item.length; i++) {
			menu_item[i] = new JMenuItem(item[i]);
			if (i < 5)
				menu_menu.add(menu_item[i]);
			else
				option_menu.add(menu_item[i]);
			menu_item[i].addActionListener(this);
		}

		setJMenuBar(menu_bar);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String menu = e.getActionCommand();

		//public static final String[] item = {"main", "rank", "play", "pause", "exit", "add word", "kor", "eng" };
		if (menu.equals(item[0])) {
			card.show(c, TITLE_CARD_INDEX);
		} else if (menu.equals(item[1])) {
			try {
				rank_in = new Scanner(new BufferedReader(new FileReader("res/rank.txt")));
			} catch (IOException e1) {
				return;
			}
		} else if (menu.equals(item[2])) {
			GameScene.game_pause = false;
		} else if (menu.equals(item[3])) {
			GameScene.game_pause = true;
		} else if (menu.equals(item[4])) {
			System.exit(0);
		} else if (menu.equals(item[5])) {
			try {
				if (kor_bool == true) {
					word_out = new BufferedWriter(new FileWriter(KOR, true));
					card.show(c, ADD_WORD_CARD_INDEX);
				} else if (kor_bool == false) {
					word_out = new BufferedWriter(new FileWriter(ENG, true));
					card.show(c, ADD_WORD_CARD_INDEX);
				}
			} catch (IOException e1) {
				return;
			}
		} else if (menu.equals(item[6])) {
			kor_bool = true;
			vc.clear();
			try {
				word_in = new Scanner(new BufferedReader(new FileReader(KOR)));
				while (word_in.hasNext()) {
					vc.add(word_in.next());
				}
				word_in.close();
			} catch (IOException e1) {
				return;
			}
		} else if (menu.equals(item[7])) {
			kor_bool = false;
			vc.clear();
			try {
				word_in = new Scanner(new BufferedReader(new FileReader(ENG)));
				while (word_in.hasNext()) {
					vc.add(word_in.next());
				}
				word_in.close();
			} catch (IOException e1) {
				return;
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
