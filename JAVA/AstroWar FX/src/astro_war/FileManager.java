package astro_war;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
	private BufferedReader in;
	private BufferedWriter out;
	private int[] unlock = new int[5];
	private int coin;
	private int skin_number;

	/* 데이터 불러오기 */
	public void loadData() {
		try {
			System.out.print("File loading.. ");
			in = new BufferedReader(new FileReader("player.asw"));
			String s = in.readLine();
			if(s != null) this.coin = Integer.parseInt(s);
			s = in.readLine();
			if(s != null) this.skin_number = Integer.parseInt(s);
			for(int i =0; i<5; i++) {
				s = in.readLine();
				unlock[i] = Integer.parseInt(s);
			}
			System.out.println("Done!");
		} catch (FileNotFoundException e) {
			// 파일이 없으면 새로 생성
			try {
				System.out.print("File Not Found!\nNew file Creating.. ");
				FileWriter newfile = new FileWriter(new File("player.asw"));
				String s = "0\r\n0\r\n0\r\n0\r\n0\r\n0\r\n0";
				newfile.write(s);
				newfile.close();
				coin = 0;
				skin_number = 0;
				unlock[0] = 1;
				for(int i = 1; i<5; i++)
					unlock[i] = 0;
				System.out.println("Done!");
			} catch (IOException e1) {
				System.out.println("Fail!");
				e.printStackTrace();
			}
		} catch(IOException e){
			System.out.println("Read Error!");
			e.printStackTrace();
		} finally {
			try {
				if(in!=null) in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/* 데이터 저장하기 */
	public void saveData(int _coin, int skin_number, int[] unlock) {
		try {
			System.out.print("File saving.. ");
			this.coin = _coin;
			out = new BufferedWriter(new FileWriter("player.asw"));
			out.write("" + _coin);
			out.newLine();
			out.write("" + skin_number);
			out.newLine();
			for(int i =0; i<5; i++) {
				out.write("" + unlock[i]);
				out.newLine();
			}
			System.out.println("Done!");
		} catch (IOException e) {
			System.out.println("IO Error!");
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public int[] getUnlockState() {
		return unlock;
	}

	public int getCoin() {
		return coin;
	}

	public int getSkinNum() {
		return skin_number;
	}
}
