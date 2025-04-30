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

import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import java.util.concurrent.atomic.AtomicInteger;

public class Points extends Pane {
	private Text text;
	private AtomicInteger points;

	public Points() {
		this.text = new Text(10, 20, "Points: 0");

		this.getChildren().add(this.text);

		this.points = new AtomicInteger();
	}

	public void increasePoints() {
		this.text.setText("Points: " + points.incrementAndGet());
	}
}
