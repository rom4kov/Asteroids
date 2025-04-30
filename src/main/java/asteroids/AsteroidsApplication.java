package asteroids;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.input.KeyCode;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;

import java.util.Random;

public class AsteroidsApplication extends Application {

	public static int WIDTH = 600;
	public static int HEIGHT = 400;

	@Override
	public void start(Stage window) throws Exception {

		Pane pane = new Pane();
		pane.setPrefSize(WIDTH, HEIGHT);
		Points points = new Points();

		Ship ship = new Ship(WIDTH / 2, HEIGHT / 2);

		pane.getChildren().add(ship.getCharacter());
		pane.getChildren().add(points);

		List<Asteroid> asteroids = this.createAsteroids();
		List<Projectile> projectiles = new ArrayList<>();

		asteroids.forEach(asteroid -> pane.getChildren().add(asteroid.getCharacter()));

		Map<KeyCode, Boolean> pressedKeys = new HashMap<>();

		Scene scene = new Scene(pane);
		scene.setOnKeyPressed(event -> {
			pressedKeys.put(event.getCode(), Boolean.TRUE);
		});

		scene.setOnKeyReleased(event -> {
			pressedKeys.put(event.getCode(), Boolean.FALSE);
		});

		new AnimationTimer() {
			private long lastUpdate = 0;
			private static final double TARGET_FPS = 45.0;
			private static final double TARGET_FRAME_TIME = 1_000_000_000 / TARGET_FPS; // in nanoseconds

			@Override
			public void handle(long now) {
				if (now - lastUpdate >= TARGET_FRAME_TIME) {
					if (pressedKeys.getOrDefault(KeyCode.LEFT, false)) {
						ship.turnLeft();
					}
					
					if (pressedKeys.getOrDefault(KeyCode.RIGHT, false)) {
						ship.turnRight();
					}
					
					if (pressedKeys.getOrDefault(KeyCode.UP, false)) {
						ship.accelerate();
					}

					if (pressedKeys.getOrDefault(KeyCode.SPACE, false) && projectiles.size() < 10) {
						Projectile p = ship.shoot();
						projectiles.add(p);
						pane.getChildren().add(p.getCharacter());
					}
					

					ship.move();
					projectiles.forEach(projectile -> projectile.move());

					for (Asteroid asteroid : asteroids) {
						asteroid.move();
						if (ship.collide(asteroid)) {
							stop();
						}
					}

					projectiles.forEach(projectile -> {
						asteroids.forEach(asteroid -> {
							if (projectile.collide(asteroid)) {
								points.increasePoints();
								projectile.setAlive(false);
								asteroid.setAlive(false);
							}
						});
					});

					projectiles.stream()
						.filter(projectile -> !projectile.isAlive())
						.forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
					projectiles.removeAll(projectiles.stream()
											.filter(projectile -> !projectile.isAlive())
											.collect(Collectors.toList()));

					asteroids.stream()
						.filter(asteroid -> !asteroid.isAlive())
						.forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));

					asteroids.stream()
						.filter(asteroid ->!asteroid.isAlive())
						.forEach(asteroid ->pane.getChildren().remove(asteroid.getCharacter()));
					asteroids.removeAll(asteroids.stream()
						.filter(asteroid -> !asteroid.isAlive())
						.collect(Collectors.toList()));

					if (Math.random() < 0.005) {
						addAsteroid(ship, asteroids, pane);
					}

					lastUpdate = now;
				}
			}
		}.start();
		
		window.setTitle("Asteroids!");
		window.setScene(scene);
		window.show();
	}

	public List<Asteroid> createAsteroids() {
		List<Asteroid> asteroids = new ArrayList<>();
		Random rand = new Random();

		for (int i = 0; i < 5; i++) {
			int x = rand.nextInt(WIDTH - 200) + 50;
			int y = rand.nextInt(HEIGHT - 300) + 50;
			Asteroid asteroid = new Asteroid(x, y);
			asteroids.add(asteroid);
		}

		return asteroids;
	}

	public static void addAsteroid(Ship ship, List<Asteroid> asteroids, Pane pane) {
		Asteroid asteroid = new Asteroid(WIDTH, HEIGHT);
		if (!asteroid.collide(ship)) {
			asteroids.add(asteroid);
			pane.getChildren().add(asteroid.getCharacter());
		}
	}

    public static void main(String[] args) {
		launch(args);
    }

    public static int partsCompleted() {
        // State how many parts you have completed using the return value of this method
        return 4;
    }
}
