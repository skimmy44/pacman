package de.amr.games.pacman.controller.actor.steering.common;

import java.util.Objects;

import de.amr.games.pacman.controller.actor.WorldMover;
import de.amr.games.pacman.controller.actor.steering.Steering;
import de.amr.games.pacman.model.Direction;

/**
 * Lets a creature move randomly but never reverse its direction.
 * 
 * @author Armin Reichert
 */
public class RandomMovement implements Steering {

	private WorldMover creature;
	private boolean forced;

	public RandomMovement(WorldMover creature) {
		this.creature = Objects.requireNonNull(creature);
	}

	@Override
	public void force() {
		forced = true;
	}

	@Override
	public void steer() {
		creature.setTargetTile(null);
		if (forced || creature.enteredNewTile() || !creature.canCrossBorderTo(creature.moveDir())) {
			/*@formatter:off*/
			Direction.dirsShuffled()
				.filter(dir -> dir != creature.moveDir().opposite())
				.filter(creature::canCrossBorderTo)
				.findFirst()
				.ifPresent(creature::setWishDir);
			/*@formatter:on*/
			forced = false;
		}
	}

	@Override
	public boolean requiresGridAlignment() {
		return true;
	}
}