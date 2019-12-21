package checkers;

import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.ServerSocket;
import java.net.Socket;

public class Checkers extends Thread {
	private boolean OutServer = false;
	private ServerSocket server;
	private final int ServerPort = 8765;
	win w;
	String strbuf = new String();

	@Override
	public void run() {
		Socket socket;
		java.io.BufferedInputStream in;
		strbuf = "Server started!hahahaha";
		w.sockettext.setText(strbuf);
		System.out.println("Server started!");
		while (!OutServer) {
			socket = null;
			try {
				synchronized (server) {
					socket = server.accept();
				}
				System.out.println("connected : InetAddress = " + socket.getInetAddress());
				strbuf += "connected : InetAddress = " + socket.getInetAddress();
				w.sockettext.setText(strbuf);
				// TimeOut時間
				socket.setSoTimeout(100000);

				in = new java.io.BufferedInputStream(socket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = in.read(b)) > 0) {
					data += new String(b, 0, length);
					strbuf += data + "\n";
					// strbuf=data;
					w.sockettext.setText(strbuf);
				}
				getData(data);
				System.out.println(data);
				in.close();
				in = null;
				socket.close();

			} catch (java.io.IOException e) {
				System.out.println("Socketconnect error !");
				System.out.println("IOException :" + e.toString());
			}

		}
	}

	public void getData(String data) {

//		c,n,i1,j1,i2,j2,i3,j3...
//		c:己方顏色(2: red, 3: green, 4:yellow)
//		n: 移動步數
//		(i,j) 移動位置
		String[] parts = data.split(",");
		 for (int i = 0; i < parts.length; i++)
		// System.out.println("parts "+i+":"+Integer.parseInt(parts[i]));
		 System.out.println("parts " + i + ":" + parts[i]);
		// parts[0]=c;
		// parts[1]=n;
		// parts[2]=i1;
		// parts[3]=j1;
		// parts[4]=i2;
		// parts[5]=j2;
		//System.out.println("parts " + 1 + ":" + Integer.parseInt(parts[1]));
		// char data_c[] = data.toCharArray();
//		System.out.println("debug");
//		int i_old = Integer.parseInt(parts[2]);
//		int j_old = Integer.parseInt(parts[3]);
//		int i = Integer.parseInt(parts[4]);
//		int j = Integer.parseInt(parts[5]);
//		System.out.println(j);
		// w.b.move(i_old, j_old, i, j);
		// System.out.println("repainted");

	}

	public Checkers() {
		try {
			server = new ServerSocket(ServerPort);

		} catch (java.io.IOException e) {
			System.out.println("Socket startup error!");
			System.out.println("IOException :" + e.toString());
		}
	}

	public static void main(String[] args) {
		Checkers c = new Checkers();
		c.w = new win();
		c.w.setSize(1000, 1000);
		c.w.setLayout(new FlowLayout());
		c.w.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
				super.windowClosing(e);
			}
		});
		c.w.setVisible(true);

		// server started!
		c.start();

	}

}