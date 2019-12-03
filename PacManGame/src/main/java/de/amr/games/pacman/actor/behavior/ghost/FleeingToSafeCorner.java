package de.amr.games.pacman.actor.behavior.ghost;

import static de.amr.datastruct.StreamUtils.permute;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import de.amr.games.pacman.actor.MazeMover;
import de.amr.games.pacman.actor.behavior.TakingPrecomputedPath;
import de.amr.games.pacman.model.Maze;
import de.amr.games.pacman.model.Tile;

/**
 * Lets a ghost escape to the "safest" maze corner depending on Pac-Man's current position. The
 * "safest" corner is defined by the maximum distance of Pac-Man to any tile on the path from the
 * actor's current position to the corner. When the target corner is reached the next corner is
 * computed.
 * 
 * @author Armin Reichert
 */
class FleeingToSafeCorner<T extends MazeMover> extends TakingPrecomputedPath<T> {

	public FleeingToSafeCorner(Supplier<Tile> chaserTileSupplier) {
		super(chaserTileSupplier);
	}

	@Override
	protected List<Tile> computePath(T refugee) {
		Tile target = refugee.tile();
		while (target.equals(refugee.tile())) {
			target = safeCorner(refugee);
		}
		return refugee.maze.findPath(refugee.tile(), target);
	}

	private Tile safeCorner(T refugee) {
		Maze maze = refugee.maze;
		Tile refugeeTile = refugee.tile();
		Tile chaserTile = fnTargetTile.get();
		//@formatter:off
		return permute(Stream.of(maze.topLeft, maze.topRight, maze.bottomRight, maze.bottomLeft))
			.filter(corner -> !corner.equals(refugeeTile))
			.sorted(byDist(maze,refugeeTile, chaserTile).reversed())
			.findFirst().get();
		//@formatter:on
	}

	private Comparator<Tile> byDist(Maze maze, Tile refugeeTile, Tile chaserTile) {
		return (corner1, corner2) -> {
			double dist1 = minDistFromPath(maze, maze.findPath(refugeeTile, corner1), chaserTile);
			double dist2 = minDistFromPath(maze, maze.findPath(refugeeTile, corner2), chaserTile);
			return Double.compare(dist1, dist2);
		};
	}

	private int manhattanDist(Tile t1, Tile t2) {
		// Note: tiles may be outside of board so we cannot use graph.manhattan()!
		int dx = t2.col - t1.col, dy = t2.row - t1.row;
		return Math.abs(dx) + Math.abs(dy);
	}

	private int minDistFromPath(Maze maze, List<Tile> path, Tile tile) {
		int min = Integer.MAX_VALUE;
		for (Tile t : path) {
			int dist = manhattanDist(t, tile);
			if (dist < min) {
				min = dist;
			}
		}
		return min;
	}
}