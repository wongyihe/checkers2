package checkers;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class board extends Canvas {

	int[][] A = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1, 1 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0 },
			{ 0, 0, 0, 0, 3, 1, 1, 1, 1, 1, 1, 1, 4, 0, 0, 0, 0 },
			{ 0, 0, 0, 3, 3, 1, 1, 1, 1, 1, 1, 4, 4, 0, 0, 0, 0 },
			{ 0, 0, 3, 3, 3, 1, 1, 1, 1, 1, 4, 4, 4, 0, 0, 0, 0 },
			{ 0, 3, 3, 3, 3, 1, 1, 1, 1, 4, 4, 4, 4, 0, 0, 0, 0 },
			{ 3, 3, 3, 3, 3, 1, 1, 1, 4, 4, 4, 4, 4, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
			{ 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
	int[][][] goal = { {}, {}, {} };
	double l = 40;
	double dx = l * Math.cos(60 * Math.PI / 180);
	double dy = l * Math.sin(60 * Math.PI / 180);
	int r = 5;
	Point p0 = new Point();
	int mark = 0;
	int i_old, j_old;
	board b;

	int[][] move_jump = { { -2, 0 }, { 2, 0 }, { 0, -2 }, { 0, 2 }, { -2, 2 }, { 2, -2 } };
	int[][] move_step = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }, { -1, 1 }, { 1, -1 } };

	SocketClient sc = new SocketClient();

	void clearmark() {
		int i, j;
		for (i = 0; i < 17; i++)
			for (j = 0; j < 17; j++) {
				if (A[i][j] > 4)
					A[i][j] = 1;
			}
		repaint();
	}

	void possiblemove(int i, int j) {
		int k, l;
		for (int m = 0; m < 6; m++) {
			k = i + move_jump[m][0];
			l = j + move_jump[m][1];
			if (k >= 0 && l >= 0 && k < 17 && l < 17)
				if (ismovable(i, j, k, l)) {
					A[k][l] = 5;
					possiblemove(k, l);
				}
			k = i + move_step[m][0];
			l = j + move_step[m][1];
			if (k >= 0 && l >= 0 && k < 17 && l < 17)
				if (ismovable(i, j, k, l) && A[i][j] != 5) {
					A[k][l] = 5;
				}
		}
		repaint();
	}

	boolean ismovable(int i, int j, int k, int l) {

		if (A[i][j] > 1 && A[k][l] == 1) {
			if ((k == i - 1 && l == j) || (k == i + 1 && l == j) || (k == i && l == j - 1) || (k == i && l == j + 1)
					|| (k == i - 1 && l == j + 1) || (k == i + 1 && l == j - 1))
				return true;
		}
		if (A[i][j] > 1 && A[k][l] == 1) {
			if ((k == i - 1 && l == j) || (k == i + 1 && l == j) || (k == i && l == j - 1) || (k == i && l == j + 1)
					|| (k == i - 1 && l == j + 1) || (k == i + 1 && l == j - 1))
				return true;
			if (((k == i - 2 && l == j) && A[i - 1][l] > 1) || ((k == i + 2 && l == j) && A[i + 1][j] > 1)
					|| ((k == i && l == j - 2) && A[i][j - 1] > 1) || ((k == i && l == j + 2) && A[i][j + 1] > 1)
					|| ((k == i - 2 && l == j + 2) && A[i - 1][j + 1] > 1)
					|| ((k == i + 2 && l == j - 2) && A[i + 1][j - 1] > 1))
				return true;
		}

		return false;
	}

	public board() {
		p0.x = 150;
		p0.y = 40;
		b = this;
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int i = (int) Math.round((x - p0.x - (y - p0.y) * dx / dy) / 2 / dx);
				int j = (int) Math.round((y - p0.y) / dy);
				move(i_old,j_old,i, j);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				int i = (int) Math.round((x - p0.x - (y - p0.y) * dx / dy) / 2 / dx);
				int j = (int) Math.round((y - p0.y) / dy);
				mark = A[i][j];
				i_old = i;
				j_old = j;
				possiblemove(i, j);
				b.repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		final int N = 17;
		int i, j;
		cell c = new cell();
		int x, y;
		for (i = 0; i < N; i++)
			for (j = 0; j < N; j++) {
				if (A[i][j] > 0) {
					x = (int) ((2 * i + j) * dx);
					y = (int) (j * dy);
					c.set(p0.x + x, p0.y + y, A[i][j] - 1);
					c.draw(g);
					g.drawString("(" + i + "," + j + ")", p0.x + x, p0.y + y + r + r + r);
				}
			}
	}

//---------------------------
	public void move(int i_old, int j_old, int i, int j) {

		if (ismovable(i_old, j_old, i, j)) {

			A[i][j] = mark;
			A[i_old][j_old] = 1;
			b.repaint();
		}
		if (A[i][j] == 5) {
			// possible movement
			sc.A.x = i_old;
			sc.A.y = j_old;
			sc.B.x = i;
			sc.B.y = j;
			try {
				sc.printsomething(A[i_old][j_old]);
			} catch (IOException e1) {
				// e1.printStackTrace();
				System.out.println(e1);
			}
			A[i][j] = mark;
			A[i_old][j_old] = 1;
			b.repaint();
		}

		clearmark();
	}

	boolean set(int i, int j, int player) {
		if (A[i][j] < 1) {
			System.out.println("wrong position");
			return false;
		} else
			A[i][j] = player;
		return true;
	}

	boolean jump(int i, int j, int m, int n) {
		if (ismovable(i, j, m, n)) {
			A[m][n] = A[i][j];
			A[i][j] = 1;
		}
		return true;
	}
}

class cell {
	Point p = new Point();
	int r = 10;
	int player = 0;
	final Color[] c = { Color.WHITE, Color.YELLOW, Color.RED, Color.GREEN, Color.GRAY };

	void set(int x, int y, int player) {
		p.x = x;
		p.y = y;
		this.player = player;
	}

	void draw(Graphics g) {
		g.setColor(c[player]);
		g.fillOval(p.x - r, p.y - r, r + r, r + r);
		g.setColor(Color.BLACK);
		g.drawOval(p.x - r, p.y - r, r + r, r + r);
	}

}