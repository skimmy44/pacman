package de.amr.games.pacman;

import de.amr.easy.game.Application;
import de.amr.easy.game.config.AppSettings;
import de.amr.games.pacman.PacManApp.PacManAppSettings;
import de.amr.games.pacman.controller.GameController;
import de.amr.games.pacman.model.Game;
import de.amr.games.pacman.model.Tile;
import de.amr.games.pacman.theme.ArcadeTheme;
import de.amr.games.pacman.theme.Theme;

/**
 * The Pac-Man game application.
 * 
 * @see <a href="https://github.com/armin-reichert/pacman">GitHub</a>
 * 
 * @author Armin Reichert
 */
public class PacManApp extends Application<PacManAppSettings> {

	public class PacManAppSettings extends AppSettings {

	}

	public static void main(String[] args) {
		launch(new PacManApp(), args);
	}

	@Override
	public PacManAppSettings createAppSettings() {
		PacManAppSettings settings = new PacManAppSettings();
		settings.width = 28 * Tile.SIZE;
		settings.height = 36 * Tile.SIZE;
		settings.scale = 2;
		settings.title = "Armin's Pac-Man";
		settings.set("PacManApp.skipIntro", false);
		settings.set("Ghost.fleeRandomly", true);
		settings.set("PacMan.overflowBug", true);
		settings.set("PacMan.immortable", false);
		return settings;
	}

	@Override
	public void init() {
		Theme theme = new ArcadeTheme();
//		theme = new MSXTheme();
		GameController gameController = new GameController(theme);
		exitHandler = app -> gameController.game().ifPresent(Game::saveHiscore);
		setController(gameController);
		setIcon(theme.spr_ghostFrightened().frame(0));
		gameController.init();
	}
}