package astro_war;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Meteorite {
	private boolean meteo_living = true;
	public int meteo_X; 
	public int meteo_Y = -130;
	private int meteo_HP;
	
	public Meteorite(int x){
		meteo_X = x;
	}
	
	public Meteorite(int x,int HP){
		meteo_X = x;
		meteo_HP = HP;
	}
	
	public void MoveDown(){
		meteo_Y += Main.METEO_SPEED;
	}
	
	public boolean crashCheck(int x, int y){
		if(x >= meteo_X-20 && x <= meteo_X+75 && y >= meteo_Y-50 && y <= meteo_Y+120){
			return true;
		}
		else return false;
	}
	
	public void kill(){
		meteo_living = false;
	}
	
	public void attacked(int damage){
		meteo_HP -= damage;
		if(meteo_HP <= 0) meteo_living = false;
	}
}
