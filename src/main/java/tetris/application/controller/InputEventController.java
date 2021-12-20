package application.controller;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

/**
 * @author Friederike Buchner Hier werden die Tasteneingaben des Users
 *         verarbeitet. Möglich sind Eingaben durch die Pfeiltasten zur Bewegung
 *         des Spielsteins nach rechts, links und unten, sowie zum
 *         rechtsseitigen Rotieren (hoch). Ebenso können die Tasten W und S zur
 *         rechtsseitigen und linksseitigen Rotation verwendet werden. Die
 *         Eingaben werden an den MovementController zur Ausführung der
 *         Bewegungen weitergereicht.
 */
public class InputEventController {
	/**
	 * Hier wird nach den Tasteneingaben gehorcht.
	 */
	private Scene scene;
	/**
	 * Hier werden die Richtungsinformationen weitergereicht.
	 */
	private MovementController movement;

	/**
	 * Konstruktor der Klasse InputEventController
	 * Liefert die Eingabeinformation an den MovementController.
	 * @param pscene    Listener zum Auffangen der Tasteneingaben
	 * @param pmovement MovementController zur Annahme der Eingabeinfos
	 */
	public InputEventController(
			final Scene pscene, final MovementController pmovement
			) {
		this.scene = pscene;
		this.movement = pmovement;

		scene.setOnKeyPressed(event -> {

			KeyCode keycode = event.getCode();
			if (keycode == KeyCode.LEFT) {
				movement.moveLeft();
			}
			if (keycode == KeyCode.RIGHT) {
				movement.moveRight();
			}
			if (keycode == KeyCode.DOWN) {
				movement.moveDown();
			}
			if (keycode == KeyCode.W || keycode == KeyCode.UP) {
				movement.rotateRight();
			}
			if (keycode == KeyCode.S) {
				movement.rotateLeft();
			}
		});
	}
}
