package de.amr.games.pacman.controller.steering.common;

import java.util.function.Supplier;

import de.amr.games.pacman.model.world.api.MobileLifeform;
import de.amr.games.pacman.model.world.api.Tile;
import de.amr.games.pacman.model.world.graph.WorldGraph;

/**
 * Lets a lifeform follow the shortest path (using graph path finding) to the target tile.
 *
 * @author Armin Reichert
 */
public class TakingShortestPath<M extends MobileLifeform> extends FollowingPath<M> {

	private final WorldGraph graph;
	private final Supplier<Tile> fnTargetTile;

	public TakingShortestPath(M mover, Supplier<Tile> fnTargetTile) {
		super(mover);
		this.fnTargetTile = fnTargetTile;
		graph = new WorldGraph(mover.world());
	}

	@Override
	public void steer(M mover) {
		if (path.size() == 0 || isComplete()) {
			setPath(graph.shortestPath(mover.tileLocation(), fnTargetTile.get()));
		}
		super.steer(mover);
	}
}