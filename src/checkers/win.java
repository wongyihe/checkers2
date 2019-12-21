package checkers;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class win extends Frame {
	board b;
	Button btn;
	TextField itxt = new TextField(10);
	TextField jtxt = new TextField(10);
	TextArea sockettext = new TextArea();

	public win() throws HeadlessException {
		b = new board();
		b.setBounds(0, 0, 1000, 1000);
		btn = new Button();
		btn.setSize(100, 50);
		btn.setLabel("go");
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = Integer.parseInt(itxt.getText().toString());
				int j = Integer.parseInt(jtxt.getText().toString());
				System.out.println(i + "," + j);
				b.set(i, j, 5);
				b.repaint();
			}
		});

		setLayout(new FlowLayout());
		add(btn);
		add(itxt);
		add(jtxt);
		add(sockettext);
		add(b);
	}
}