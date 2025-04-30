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

import java.util.List;

import javafx.scene.shape.Polygon;

public class Ship extends Character {

	public Ship(int x, int y) {
		super(new Polygon(-5, -5, 10, 0, -5, 5), x, y);
	}

	public Projectile shoot() {
		Projectile p = new Projectile((int) this.getCharacter().getTranslateX(), (int) this.getCharacter().getTranslateY());
		p.getCharacter().setRotate(this.getCharacter().getRotate());
		p.accelerate();
		p.setMovement(p.getMovement().normalize().multiply(3));
		return p;
	}
}
