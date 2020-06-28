package de.amr.games.pacman.test.navigation;

import static de.amr.games.pacman.controller.actor.GhostState.FRIGHTENED;

import java.awt.Color;
import java.awt.event.KeyEvent;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;
import de.amr.easy.game.input.Keyboard;
import de.amr.games.pacman.controller.actor.Ghost;
import de.amr.games.pacman.model.Game;
import de.amr.games.pacman.model.world.Tile;
import de.amr.games.pacman.view.play.PlayView;
import de.amr.games.pacman.view.theme.ArcadeTheme;

public class MovingRandomlyTestApp extends Application {

	public static void main(String[] args) {
		launch(MovingRandomlyTestApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.width = 28 * Tile.SIZE;
		settings.height = 36 * Tile.SIZE;
		settings.scale = 2;
		settings.title = "Moving Randomly";
	}

	@Override
	public void init() {
		setController(new MovingRandomlyTestUI());
	}
}

class MovingRandomlyTestUI extends PlayView {

	boolean started;

	public MovingRandomlyTestUI() {
		super(Game.defaultGame(), new ArcadeTheme());
		showRoutes = true;
		showStates = true;
		showScores = false;
		showGrid = true;
	}

	@Override
	public void init() {
		super.init();
		game.world.eatFood();
		game.ghosts().forEach(ghost -> {
			game.takePart(ghost);
			ghost.tf.setPosition(game.world.pacManSeat().position);
			ghost.behavior(FRIGHTENED, ghost.isMovingRandomlyWithoutTurningBack());
			ghost.state(FRIGHTENED).removeTimer();
			ghost.setState(FRIGHTENED);
		});
		showMessage("Press SPACE", Color.WHITE);
	}

	@Override
	public void update() {
		super.update();
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			started = true;
			clearMessage();
		}
		if (started) {
			game.ghostsOnStage().forEach(Ghost::update);
		}
	}
}