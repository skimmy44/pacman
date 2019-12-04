package de.amr.games.pacman.test.navigation;

import de.amr.easy.game.Application;
import de.amr.games.pacman.actor.Ensemble;
import de.amr.games.pacman.model.PacManGame;

public class InkyChaseTestApp extends Application {

	public static void main(String[] args) {
		launch(new InkyChaseTestApp(), args);
	}

	public InkyChaseTestApp() {
		settings.title = "Inky Chasing";
		settings.width = 28 * PacManGame.TS;
		settings.height = 36 * PacManGame.TS;
		settings.scale = 2;
	}

	@Override
	public void init() {
		PacManGame game = new PacManGame();
		Ensemble ensemble = new Ensemble(game, game.maze);
		setController(new InkyChaseTestUI(game, ensemble));
	}
}