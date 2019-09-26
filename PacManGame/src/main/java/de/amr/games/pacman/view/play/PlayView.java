package de.amr.games.pacman.view.play;

import static de.amr.easy.game.Application.app;
import static de.amr.games.pacman.model.PacManGame.TS;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.Optional;
import java.util.stream.Stream;

import de.amr.easy.game.view.Controller;
import de.amr.easy.game.view.View;
import de.amr.games.pacman.actor.Bonus;
import de.amr.games.pacman.actor.Ghost;
import de.amr.games.pacman.actor.GhostState;
import de.amr.games.pacman.actor.PacManWorld;
import de.amr.games.pacman.model.BonusSymbol;
import de.amr.games.pacman.model.PacManGame;
import de.amr.graph.grid.impl.Top4;

/**
 * Simple play view without bells and whistles.
 * 
 * @author Armin Reichert
 */
public class PlayView implements View, Controller, PacManWorld {

	protected final int width, height;
	protected final PacManGame game;
	protected final MazeUI mazeView;
	protected final Image lifeImage;
	protected String infoText;
	protected Color infoTextColor;
	protected boolean scoresVisible;

	public PlayView(PacManGame game, Color mazeBgColor) {
		this.width = app().settings.width;
		this.height = app().settings.height;
		this.game = game;
		game.pacMan.setWorld(this);
		lifeImage = game.theme.spr_pacManWalking(Top4.W).frame(1);
		mazeView = new MazeUI(game, mazeBgColor);
		mazeView.tf.setPosition(0, 3 * TS);
	}

	@Override
	public void init() {
		mazeView.init();
	}

	@Override
	public void update() {
		mazeView.update();
	}

	public void enableAnimation(boolean enable) {
		mazeView.enableSprites(enable);
		game.pacMan.sprites.enableAnimation(enable);
		game.activeGhosts().forEach(ghost -> ghost.sprites.enableAnimation(enable));
	}

	@Override
	public Stream<Ghost> getGhosts() {
		return game.activeGhosts();
	}

	@Override
	public Optional<Bonus> getBonus() {
		return mazeView.getBonus();
	}

	public void setBonusTimer(int ticks) {
		mazeView.setBonusTimer(ticks);
	}

	public void setBonus(BonusSymbol symbol, int value) {
		mazeView.setBonus(new Bonus(symbol, value, game.theme));
	}

	public void removeBonus() {
		mazeView.setBonus(null);
	}

	public void setMazeFlashing(boolean flashing) {
		mazeView.setFlashing(flashing);
	}

	public void showInfoText(String text, Color color) {
		infoText = text;
		infoTextColor = color;
	}

	public void hideInfoText() {
		this.infoText = null;
	}

	public boolean isScoresVisible() {
		return scoresVisible;
	}

	public void setScoresVisible(boolean scoresVisible) {
		this.scoresVisible = scoresVisible;
	}

	@Override
	public void draw(Graphics2D g) {
		mazeView.draw(g);
		drawActors(g);
		drawInfoText(g);
		drawScores(g);
	}

	protected void drawActors(Graphics2D g) {
		if (game.isActive(game.pacMan)) {
			game.pacMan.draw(g);
		}
		game.activeGhosts().filter(ghost -> ghost.getState() != GhostState.DYING)
				.forEach(ghost -> ghost.draw(g));
		game.activeGhosts().filter(ghost -> ghost.getState() == GhostState.DYING)
				.forEach(ghost -> ghost.draw(g));
	}

	protected void drawScores(Graphics2D g) {
		if (scoresVisible) {
			// Points score
			int score = game.score.getPoints();
			g.setFont(game.theme.fnt_text());
			g.setColor(Color.YELLOW);
			g.drawString("SCORE", TS, TS);
			g.setColor(Color.WHITE);
			g.drawString(String.format("%07d", score), TS, 2 * TS);
			g.setColor(Color.YELLOW);
			g.drawString(String.format("LEVEL %2d", game.getLevel()), 22 * TS, TS);

			// High score
			g.setColor(Color.YELLOW);
			g.drawString("HIGH", 10 * TS, TS);
			g.drawString("SCORE", 14 * TS, TS);
			g.setColor(Color.WHITE);
			g.drawString(String.format("%07d", game.score.getHiscorePoints()), 10 * TS, 2 * TS);
			g.drawString(String.format("L%d", game.score.getHiscoreLevel()), 16 * TS, 2 * TS);

			// Food remaining score
			g.setColor(Color.PINK);
			g.fillRect(22 * TS + 2, TS + 2, 4, 4);
			g.setColor(Color.WHITE);
			g.drawString(String.format("%d", game.getFoodRemaining()), 23 * TS, 2 * TS);

			drawLives(g);
			drawLevelCounter(g);
		}
	}

	protected void drawLives(Graphics2D g) {
		g.translate(0, height - 2 * TS);
		for (int i = 0; i < game.getLives(); ++i) {
			g.translate((2 - i) * lifeImage.getWidth(null), 0);
			g.drawImage(lifeImage, 0, 0, null);
			g.translate((i - 2) * lifeImage.getWidth(null), 0);
		}
		g.translate(0, -height + 2 * TS);
	}

	protected void drawLevelCounter(Graphics2D g) {
		int mazeWidth = mazeView.sprites.current().get().getWidth();
		g.translate(0, height - 2 * TS);
		for (int i = 0, n = game.getLevelCounter().size(); i < n; ++i) {
			g.translate(mazeWidth - (n - i) * 2 * TS, 0);
			Image bonusImage = game.theme.spr_bonusSymbol(game.getLevelCounter().get(i)).frame(0);
			g.drawImage(bonusImage, 0, 0, 2 * TS, 2 * TS, null);
			g.translate(-mazeWidth + (n - i) * 2 * TS, 0);
		}
		g.translate(0, -height + 2 * TS);
	}

	protected void drawInfoText(Graphics2D g) {
		if (infoText == null) {
			return;
		}
		int mazeWidth = mazeView.sprites.current().get().getWidth();
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setFont(game.theme.fnt_text(14));
		g2.setColor(infoTextColor);
		Rectangle box = g2.getFontMetrics().getStringBounds(infoText, g2).getBounds();
		g2.translate((mazeWidth - box.width) / 2, (game.maze.getBonusTile().row + 1) * TS);
		g2.drawString(infoText, 0, 0);
		g2.dispose();
	}
}