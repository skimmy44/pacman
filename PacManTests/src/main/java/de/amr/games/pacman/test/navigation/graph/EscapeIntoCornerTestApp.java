package de.amr.games.pacman.test.navigation.graph;

import static de.amr.games.pacman.controller.actor.GhostState.FRIGHTENED;
import static de.amr.games.pacman.controller.actor.PacManState.EATING;

import java.util.Optional;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;
import de.amr.easy.game.view.View;
import de.amr.easy.game.view.VisualController;
import de.amr.games.pacman.controller.actor.MovingActor;
import de.amr.games.pacman.model.Game;
import de.amr.games.pacman.model.Tile;
import de.amr.games.pacman.view.play.PlayView;
import de.amr.games.pacman.view.theme.ArcadeTheme;
import de.amr.games.pacman.view.theme.Theme;

public class EscapeIntoCornerTestApp extends Application {

	public static void main(String[] args) {
		launch(EscapeIntoCornerTestApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		settings.width = 28 * Tile.SIZE;
		settings.height = 36 * Tile.SIZE;
		settings.scale = 2;
		settings.title = "Escape Into Corner";
	}

	@Override
	public void init() {
		Game game = new Game();
		Theme theme = new ArcadeTheme();
		setController(new EscapeIntoCornerTestUI(game, theme));
	}
}

class EscapeIntoCornerTestUI extends PlayView implements VisualController {

	public EscapeIntoCornerTestUI(Game game, Theme theme) {
		super(game, theme);
		showRoutes = true;
		showStates = true;
		showScores = false;
	}

	@Override
	public void init() {
		super.init();
		game.maze.removeFood();
		game.stage.add(game.pacMan);
		game.pacMan.setState(EATING);
		game.stage.add(game.blinky);
		game.blinky.behavior(FRIGHTENED, game.blinky.isFleeingToSafeCorner(game.pacMan, game.maze.cornerNW(),
				game.maze.cornerNE(), game.maze.cornerSW(), game.maze.cornerSE()));
		game.blinky.setState(FRIGHTENED);
	}

	@Override
	public void update() {
		super.update();
		game.movingActorsOnStage().forEach(MovingActor::update);
	}

	@Override
	public Optional<View> currentView() {
		return Optional.of(this);
	}
}