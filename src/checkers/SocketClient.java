package checkers;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.awt.Point;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class SocketClient {
	//private String address = "192.168.1.197";
	 private String address = "127.0.0.1";// server name
	private int port = 8765;// port number
	public Point A = new Point();
	public Point B = new Point();
	public int times = 0;
	

	public SocketClient() {

		Socket client = new Socket();
		InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
		try {
			client.connect(isa, 10000);
			BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
			// for(int i=0;i<10;i++) {
			// out.write(("t"+i+"Send From Client yayaya\n").getBytes());
			out.write("Send From Client \n".getBytes());
			out.flush();
			// }
			// out.write("Send From Client ".getBytes());
			// out.flush();
			out.close();
			out = null;
			client.close();
			client = null;

		} catch (java.io.IOException e) {
			System.out.println("Socket連線有問題 !");
			System.out.println("IOException :" + e.toString());
		}
	}

	public void printsomething(int color) throws IOException {
		Socket client = new Socket();
		InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
		client.connect(isa, 10000);
		BufferedOutputStream out = new BufferedOutputStream(client.getOutputStream());
		if (!A.equals(B)) {
			times++;
//			c,n,i1,j1,i2,j2,i3,j3...
//			c:己方顏色(2: red, 3: green, 4:yellow)
//			n: 移動步數
//			(i,j) 移動位置
			out.write((color+"," +times + "," + A.x + "," + A.y + "," + B.x + "," + B.y).getBytes());
		}
		out.flush();
		out.close();
		out = null;
		client.close();
		client = null;

	}

//	public static void main(String args[]) {
//		new SocketClient();
//	}
}