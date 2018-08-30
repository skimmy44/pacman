package de.amr.games.pacman.view.core;

import static java.lang.Math.round;

import de.amr.easy.game.entity.Transform;
import de.amr.games.pacman.model.Tile;

/**
 * Mixin for game entities which are placed on tiles.
 * 
 * @author Armin Reichert
 */
public interface TilePlacedEntity {

	int getTileSize();

	Transform tf();

	default int tileCenter(float coord) {
		return round(coord + getTileSize() / 2) / getTileSize();
	}

	default Tile getTile() {
		return new Tile(tileCenter(tf().getX()), tileCenter(tf().getY()));
	}

	default void placeAt(Tile tile, float xOffset, float yOffset) {
		tf().moveTo(tile.col * getTileSize() + xOffset, tile.row * getTileSize() + yOffset);
	}

	default void align() {
		placeAt(getTile(), 0, 0);
	}

	default boolean isAligned() {
		return getAlignmentX() == 0 && getAlignmentY() == 0;
	}

	default int getAlignmentX() {
		return round(tf().getX()) % getTileSize();
	}

	default int getAlignmentY() {
		return round(tf().getY()) % getTileSize();
	}
}