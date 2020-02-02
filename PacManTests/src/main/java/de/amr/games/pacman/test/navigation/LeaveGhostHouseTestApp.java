package de.amr.games.pacman.test.navigation;

import static de.amr.games.pacman.actor.GhostState.SCATTERING;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.Optional;

import de.amr.easy.game.config.AppSettings;
import de.amr.easy.game.input.Keyboard;
import de.amr.easy.game.view.View;
import de.amr.easy.game.view.VisualController;
import de.amr.games.pacman.PacManApp;
import de.amr.games.pacman.actor.Cast;
import de.amr.games.pacman.controller.event.GhostUnlockedEvent;
import de.amr.games.pacman.model.Game;
import de.amr.games.pacman.theme.ArcadeTheme;
import de.amr.games.pacman.theme.Theme;
import de.amr.games.pacman.view.play.PlayView;

public class LeaveGhostHouseTestApp extends PacManApp {

	public static void main(String[] args) {
		launch(LeaveGhostHouseTestApp.class, args);
	}

	@Override
	protected void configure(AppSettings settings) {
		super.configure(settings);
		settings.title = "Leave Ghost House";
	}

	@Override
	public void init() {
		clock().setFrequency(10);
		Game game = new Game();
		Cast cast = new Cast(game);
		Theme theme = new ArcadeTheme();
		setController(new LeaveGhostHouseTestUI(cast, theme));
	}
}

class LeaveGhostHouseTestUI extends PlayView implements VisualController {

	public LeaveGhostHouseTestUI(Cast cast, Theme theme) {
		super(cast, theme);
		showRoutes = () -> true;
		showStates = () -> true;
		showScores = () -> false;
		showGrid = () -> true;
	}

	@Override
	public void init() {
		super.init();
		maze().removeFood();
		cast.putActorOnStage(cast.inky);
		cast.inky.setFollowState(SCATTERING);
		messageColor = Color.YELLOW;
		messageText = "Press SPACE to unlock";
	}

	@Override
	public void update() {
		if (Keyboard.keyPressedOnce(KeyEvent.VK_SPACE)) {
			cast.inky.process(new GhostUnlockedEvent());
			messageText = null;
		}
		cast.inky.update();
		super.update();
	}

	@Override
	public Optional<View> currentView() {
		return Optional.of(this);
	}

}