package de.amr.games.pacman.controller.creatures;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import de.amr.easy.game.entity.Entity;
import de.amr.easy.game.entity.Transform;
import de.amr.games.pacman.controller.event.PacManGameEvent;
import de.amr.games.pacman.controller.steering.api.Steering;
import de.amr.games.pacman.controller.steering.common.Movement;
import de.amr.games.pacman.controller.steering.common.MovementType;
import de.amr.games.pacman.model.game.Game;
import de.amr.games.pacman.model.world.api.Direction;
import de.amr.games.pacman.model.world.api.MobileLifeform;
import de.amr.games.pacman.model.world.api.Tile;
import de.amr.games.pacman.model.world.api.World;
import de.amr.games.pacman.view.api.Theme;
import de.amr.games.pacman.view.api.Themeable;
import de.amr.statemachine.core.StateMachine;

/**
 * A creature is a named entity that can move through the world and has "intelligence", i.e. it can
 * make decisions changing its behavior.
 * <p>
 * The physical size is one tile, however the visual appearance may be larger.
 * 
 * @param <M> subtype of mobile lifeform
 * @param <S> state (identifier) type
 * 
 * @author Armin Reichert
 */
public abstract class Creature<M extends MobileLifeform, S> extends StateMachine<S, PacManGameEvent>
		implements MobileLifeform, Themeable {

	public final Entity entity;
	public final String name;
	protected final World world;
	protected Game game;
	protected boolean enabled;
	protected Map<S, Steering<M>> steeringsByState;
	protected Movement movement;
	protected Direction moveDir;
	protected Direction wishDir;
	protected Theme theme;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Creature(Class<S> stateClass, String name, World world) {
		super(stateClass);
		this.name = name;
		this.world = world;
		enabled = true;
		entity = new Entity();
		entity.tf.width = entity.tf.height = Tile.SIZE;
		steeringsByState = stateClass.isEnum() ? new EnumMap(stateClass) : new HashMap<>();
		movement = new Movement(this, name);
	}

	@Override
	public World world() {
		return world;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @return this creatures' current speed (pixels per tick)
	 */
	@Override
	public float getSpeed() {
		return 0;
	}

	/**
	 * @param game the game this creature takes part in
	 */
	public void setGame(Game game) {
		this.game = game;
		init();
	}

	public Stream<StateMachine<?, ?>> machines() {
		return Stream.of(this, movement);
	}

	@Override
	public boolean isVisible() {
		return entity.visible;
	}

	@Override
	public void setVisible(boolean visible) {
		entity.visible = visible;
	}

	@Override
	public Theme getTheme() {
		return theme;
	}

	@Override
	public void setTheme(Theme theme) {
		this.theme = theme;
	}

	public Steering<M> steering() {
		return steering(this);
	}

	/**
	 * @return the current steering for this actor.
	 */
	public Steering<M> steering(MobileLifeform mover) {
		return steeringsByState.getOrDefault(getState(), m -> {
		});
	}

	/**
	 * @return the optional target tile of this creature
	 */
	public Optional<Tile> targetTile() {
		return steering().targetTile();
	}

	/**
	 * Returns the steering for the given state.
	 * 
	 * @param state state
	 * @return steering defined for this state
	 */
	public Steering<M> steering(S state) {
		if (steeringsByState.containsKey(state)) {
			return steeringsByState.get(state);
		}
		throw new IllegalArgumentException(String.format("%s: No steering found for state %s", this, state));
	}

	/**
	 * Defines the steering for the given state.
	 * 
	 * @param state    state
	 * @param steering steering defined for this state
	 */
	public void behavior(S state, Steering<M> steering) {
		steeringsByState.put(state, steering);
	}

	@Override
	public boolean requiresAlignment() {
		return steering().requiresGridAlignment();
	}

	@Override
	public void init() {
		movement.init();
		super.init();
	}

	@Override
	public Transform tf() {
		return entity.tf;
	}

	@Override
	public void placeAt(Tile tile, float xOffset, float yOffset) {
		movement.placeAt(tile, xOffset, yOffset);
	}

	public boolean isTeleporting() {
		return movement.is(MovementType.TELEPORTING);
	}

	@Override
	public boolean enteredNewTile() {
		return movement.enteredNewTile;
	}

	@Override
	public Direction moveDir() {
		return moveDir;
	}

	@Override
	public void setMoveDir(Direction dir) {
		moveDir = Objects.requireNonNull(dir);
	}

	@Override
	public Direction wishDir() {
		return wishDir;
	}

	@Override
	public void setWishDir(Direction dir) {
		wishDir = dir;
	}

	@Override
	public boolean canCrossBorderTo(Direction dir) {
		Tile currentTile = tileLocation(), neighbor = world().neighbor(currentTile, dir);
		return canMoveBetween(currentTile, neighbor);
	}

	@Override
	public boolean canMoveBetween(Tile tile, Tile neighbor) {
		return world().isAccessible(neighbor);
	}

	@Override
	public void forceMoving(Direction dir) {
		setWishDir(dir);
		movement.update();
	}
}