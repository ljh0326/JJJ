package game.scenes;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddWordScene extends JPanel implements KeyListener {
	Container c;
	CardLayout card;
	BufferedWriter word_out;
	JTextField add_field;
	public AddWordScene(int width, int height, Container c, CardLayout card, BufferedWriter word_out) {
		this.c = c;
		this.card = card;
		this.word_out = word_out;
		setLayout(null);
		setBounds(0, 0, width, height);
		setBackground(Color.BLACK);
		
		JLabel add_ques = new JLabel("추가하고 싶은 단어를 입력하세요 (한영모드에 유의해 주세요)");
		add_ques.setBounds((width-360)/2, (height-150)/2, 360, 30);
		add_ques.setBackground(Color.WHITE);
		add(add_ques);
		
		add_field = new JTextField(30);
		add_field.setBounds((width - 150) / 2, height / 2, 150, 30);
		add_field.addKeyListener(this);
		add(add_field);
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_ENTER) {
			//try {
			//	word_out.write(add_field.getText());
			//	word_out.write("\n");
			//	word_out.close();
			//	add_field.setText("");
			//} catch (IOException e1) {
			//	return;
			//}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
