package asteroids;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author romkov
 */

import javafx.scene.shape.Polygon;
import javafx.geometry.Point2D;
import javafx.scene.shape.Shape;
import java.util.Random;

public abstract class Character {

	private Polygon character; 
	private Point2D movement; 
	private boolean isAlive;

	public Character(Polygon polygon, int x, int y) {
		this.character = polygon;
		character.setTranslateX(x);
		character.setTranslateY(y);

		this.movement = new Point2D(0, 0);
		this.isAlive = true;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean status) {
		this.isAlive = status;
	}

	public Polygon getCharacter() {
		return character;
	}
	
	public void turnLeft() {
		if (this.character.getRotate() < 7.5) {
			this.character.setRotate(360 - (7.5 - this.character.getRotate()));
		} else {
			this.character.setRotate(this.character.getRotate() - 7.5);
		}
	}

	public void turnRight() {
		if (this.character.getRotate() > 352.5) {
			this.character.setRotate((this.character.getRotate() + 7.5) - 360);
		} else {
			this.character.setRotate(this.character.getRotate() + 7.5);
		}
	}

	public void move() {
		this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
		this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

		if (this.character.getTranslateX() < 0) {
			this.character.setTranslateX(AsteroidsApplication.WIDTH);
		}

		if (this.character.getTranslateY() < 0) {
			this.character.setTranslateY(AsteroidsApplication.HEIGHT);
		}

		if (this.character.getTranslateX() > AsteroidsApplication.WIDTH) {
			this.character.setTranslateX(0);
		}

		if (this.character.getTranslateY() > AsteroidsApplication.HEIGHT) {
			this.character.setTranslateY(0);
		}

	}

	public Point2D getMovement() {
		return this.movement;
	}

	public void setMovement(Point2D changedMovement) {
		double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
		double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

		changeX *= 10;
		changeY *= 10;
		this.movement = this.movement.add(changeX, changeY);
	}

	public void accelerate() {
		double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
		double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

		changeX *= 0.4;
		changeY *= 0.4;

		this.movement = this.movement.add(changeX, changeY);
	}

	public boolean collide(Character other) {
		Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
		return collisionArea.getBoundsInLocal().getWidth() != -1;
	}
}
