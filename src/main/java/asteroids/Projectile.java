/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

/**
 *
 * @author romkov
 */
import javafx.scene.shape.Polygon;
import java.util.Timer;
import java.util.TimerTask;

public class Projectile extends Character {

	private Timer timer;
	
	public Projectile(int x, int y) {
		super(new Polygon(2, -2, 2, 2, -2, 2, -2, -2), x, y);
		this.timer = new Timer();
		this.timer.schedule(new TimerTask() {
			@Override
			public void run() {
				setAlive(false);
			}
		}, 1500);
	}
}
