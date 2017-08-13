package astro_war;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.player.Player;

public class SoundPlayer extends Thread{
	private AudioInputStream ais;	
	private Player player;
	private File file;
	private FileInputStream fis;
	private BufferedInputStream bis;
	private boolean loop;
	
	public SoundPlayer(String name, boolean loop){
		try{
			this.loop = loop;
			file = new File(Main.class.getResource("../sounds/" + name).toURI());
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			player = new Player(bis);
		}
		catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
	
	public void close(){
		loop = false;
		player.close();
		this.stop();
	}
	
	@Override
	public void run(){
		try {
			while(loop){
				player.play();
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}
}
