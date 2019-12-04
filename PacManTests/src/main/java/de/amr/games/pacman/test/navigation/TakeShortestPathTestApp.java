package de.amr.games.pacman.test.navigation;

import de.amr.easy.game.Application;
import de.amr.games.pacman.actor.Ensemble;
import de.amr.games.pacman.model.PacManGame;

public class TakeShortestPathTestApp extends Application {

	public static void main(String[] args) {
		launch(new TakeShortestPathTestApp(), args);
	}

	public TakeShortestPathTestApp() {
		settings.title = "Follow Shortest Path";
		settings.width = 28 * PacManGame.TS;
		settings.height = 36 * PacManGame.TS;
		settings.scale = 2;
	}

	@Override
	public void init() {
		PacManGame game = new PacManGame();
		Ensemble ensemble = new Ensemble(game, game.maze);
		setController(new TakeShortestPathTestUI(game, ensemble));
	}
}