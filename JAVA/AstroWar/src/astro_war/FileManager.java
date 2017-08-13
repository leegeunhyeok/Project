package astro_war;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private BufferedReader in;
	private BufferedWriter out;
	private int coin;
	
	public void loadData() {
		try {
			in = new BufferedReader(new FileReader("player.asw"));
			String s = in.readLine();
			if(s != null) this.coin = Integer.parseInt(s);
			//System.out.println(this.coin);
		} catch (IOException e) {
			try {
				FileWriter newfile = new FileWriter(new File("player.asw"));
				newfile.write("0");
				newfile.close();
			} catch (IOException e1) {
				e.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void saveData(int _coin) {
		try {
			this.coin = _coin;
			out = new BufferedWriter(new FileWriter("player.asw"));
			String s = "" + _coin;
			out.write(s);
			out.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int getData() {
		return coin;
	}
}
