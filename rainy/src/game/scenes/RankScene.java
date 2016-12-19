package game.scenes;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class RankScene extends JPanel {
	Container c;
	CardLayout card;
	Scanner rank_in;
	JTextField add_field;
	HashMap<String, Integer> map;
	JLabel[] rank_label;
	
	int first_i, second_i, third_i;
	public RankScene(int width, int height, Container c, CardLayout card, Scanner rank_in) {
		this.c = c;
		this.card = card;
		this.rank_in = rank_in;
		map = new HashMap<>();
		setLayout(null);
		setBounds(0, 0, width, height);
		setBackground(Color.BLACK);
		
		while(rank_in.hasNext()) {
			map.put(rank_in.next(), rank_in.nextInt());
		}
		//map.sort();
		Set<String> keys = map.keySet();
		Iterator<String> it = keys.iterator();
		
	}
}
