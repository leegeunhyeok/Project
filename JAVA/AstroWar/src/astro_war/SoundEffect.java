package astro_war;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;

public class SoundEffect extends Thread{
	private Player player;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	
	public SoundEffect(String name){
		try{
			file = new File(Main.class.getResource("../sounds/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
			this.start();
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		try{
			player.play();
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public void close(){
		player.close();
		this.interrupt();
	}
}
