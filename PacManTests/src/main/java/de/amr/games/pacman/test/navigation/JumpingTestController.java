package de.amr.games.pacman.test.navigation;

import de.amr.easy.game.view.View;
import de.amr.easy.game.view.ViewController;
import de.amr.games.pacman.actor.Ghost;
import de.amr.games.pacman.model.PacManGame;
import de.amr.games.pacman.view.play.PlayViewXtended;

public class JumpingTestController implements ViewController {

	private PacManGame game;
	private PlayViewXtended view;

	@Override
	public void init() {
		game = new PacManGame();
		game.init();
		game.maze.removeFood();
		game.setActive(game.pacMan, false);
		game.activeGhosts().forEach(ghost -> {
			ghost.init();
			ghost.fnIsUnlocked = g -> false;
		});
		view = new PlayViewXtended(game);
		view.setShowGrid(false);
		view.setShowRoutes(false);
		view.setShowStates(true);
		view.setScoresVisible(false);
	}

	@Override
	public void update() {
		game.activeGhosts().forEach(Ghost::update);
		view.update();
	}

	@Override
	public View currentView() {
		return view;
	}
}