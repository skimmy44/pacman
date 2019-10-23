package de.amr.games.pacman.actor;

import static de.amr.games.pacman.model.Maze.NESW;
import static de.amr.games.pacman.model.PacManGame.TS;
import static java.lang.Math.round;

import java.util.OptionalInt;

import de.amr.easy.game.entity.SpriteEntity;
import de.amr.easy.game.math.Vector2f;
import de.amr.games.pacman.model.PacManGame;
import de.amr.games.pacman.model.Tile;
import de.amr.graph.grid.impl.Top4;

/**
 * Abstract base class for Pac-Man and the ghosts.
 * 
 * <p>
 * Implements movement inside the maze. Movement is controlled by supplying the intended move
 * direction before moving.
 * 
 * @author Armin Reichert
 */
public abstract class MazeMover extends SpriteEntity {

	/**
	 * Converts pixel coordinate (x/y) to tile index (column/row).
	 * 
	 * @param coord
	 *                pixel coordinate
	 * @return tile index
	 */
	private static int tileIndex(float coord) {
		return round(coord) / TS;
	}

	/* The game model. */
	public final PacManGame game;

	/* Current move direction (Top4.N, Top4.E, Top4.S, Top4.W). */
	private int moveDir;

	/* The intended move direction, actor turns to this direction as soon as possible. */
	private int nextDir;

	/* Tells if the last move entered a new tile position */
	private boolean enteredNewTile;

	protected MazeMover(PacManGame game) {
		this.game = game;
		moveDir = nextDir = Top4.E;
		enteredNewTile = false;
		// collision box size of maze movers is one tile, sprite size is larger!
		tf.setWidth(TS);
		tf.setHeight(TS);
	}

	public int getMoveDir() {
		return moveDir;
	}

	protected void setMoveDir(int moveDir) {
		this.moveDir = moveDir;
	}

	public int getNextDir() {
		return nextDir;
	}

	protected void setNextDir(int nextDir) {
		this.nextDir = nextDir;
	}

	public boolean enteredNewTile() {
		return enteredNewTile;
	}

	/**
	 * @return The tile where this maze mover is located, which is the tile containing the center
	 *         point of this maze mover.
	 */
	public Tile getTile() {
		Vector2f center = tf.getCenter();
		return new Tile(tileIndex(center.x), tileIndex(center.y));
	}

	/**
	 * @param numTiles
	 *                   number of tiles
	 * @return the tile located <code>numTiles</code> tiles ahead of the actor towards his current
	 *         move direction.
	 */
	public Tile tilesAhead(int numTiles) {
		return getTile().tileTowards(moveDir, numTiles);
	}

	/**
	 * Places this maze mover at the given tile, optionally with some pixel offset.
	 * 
	 * @param tile
	 *                  the tile where this maze mover is placed
	 * @param xOffset
	 *                  pixel offset in x-direction
	 * @param yOffset
	 *                  pixel offset in y-direction
	 */
	public void placeAtTile(Tile tile, float xOffset, float yOffset) {
		enteredNewTile = !tile.equals(getTile());
		tf.setPosition(tile.col * TS + xOffset, tile.row * TS + yOffset);
	}

	/**
	 * Places this maze mover exactly over its current tile.
	 */
	public void align() {
		Tile tile = getTile();
		tf.setPosition(tile.col * TS, tile.row * TS);
	}

	/**
	 * @return <code>true</code> if this maze mover is exactly positioned over its current tile.
	 */
	public boolean isAligned() {
		return getAlignmentX() == 0 && getAlignmentY() == 0;
	}

	/**
	 * @return the pixel offset in x-direction wrt the current tile
	 */
	public int getAlignmentX() {
		return round(tf.getX()) % TS;
	}

	/**
	 * @return the pixel offset in y-direction wrt the current tile
	 */
	public int getAlignmentY() {
		return round(tf.getY()) % TS;
	}

	/**
	 * @return a function returning the intended move direction
	 */
	public abstract OptionalInt supplyIntendedDir();

	/**
	 * @return the current speed (in pixels)
	 */
	public abstract float getSpeed();

	/**
	 * @param tile
	 *               some tile
	 * @return <code>true</code> if this maze mover can currently enter the tile
	 */
	public abstract boolean canEnterTile(Tile tile);

	/**
	 * @return <code>true</code> if the maze mover cannot move further towards its current direction
	 */
	public boolean isStuck() {
		return tf.getVelocity().length() == 0;
	}

	/**
	 * Moves this actor through the maze. Handles changing the direction according to the intended
	 * move direction, moving around corners without losing alignment, movement through the "teleport"
	 * tile and getting stuck.
	 */
	protected void move() {
		Tile oldTile = getTile();
		supplyIntendedDir().ifPresent(this::setNextDir);
		float speed = computeMaxSpeed(nextDir);
		if (speed > 0) {
			if (isTurning90Degrees()) {
				align();
			}
			setMoveDir(nextDir);
		}
		else {
			speed = computeMaxSpeed(moveDir);
		}
		tf.setVelocity(velocity(speed));
		if (speed > 0) {
			tf.move();
			// check for exit from teleport space
			if (tf.getX() + tf.getWidth() < 0) {
				tf.setX(game.maze.numCols() * TS);
			}
			else if (tf.getX() > (game.maze.numCols()) * TS) {
				tf.setX(-tf.getWidth());
			}
		}
		enteredNewTile = !oldTile.equals(getTile());
	}

	private boolean isTurning90Degrees() {
		return nextDir == NESW.left(moveDir) || nextDir == NESW.right(moveDir);
	}

	private Vector2f velocity(float speed) {
		return Vector2f.smul(speed, Vector2f.of(NESW.dx(moveDir), NESW.dy(moveDir)));
	}

	/*
	 * Computes how many pixels this entity can move towards the given direction in one frame.
	 */
	private float computeMaxSpeed(int dir) {
		final Tile currentTile = getTile();
		final Tile neighborTile = currentTile.tileTowards(dir);
		final float speed = getSpeed();
		if (game.maze.inTeleportSpace(currentTile)) {
			// in teleporting area only horizontal movement is possible
			return dir == Top4.N || dir == Top4.S ? 0 : speed;
		}
		if (canEnterTile(neighborTile)) {
			return speed;
		}
		float cappedSpeed = 0;
		switch (dir) {
		case Top4.E:
			cappedSpeed = neighborTile.col * TS - (tf.getX() + tf.getWidth());
			break;
		case Top4.W:
			cappedSpeed = tf.getX() - currentTile.col * TS;
			break;
		case Top4.N:
			cappedSpeed = tf.getY() - currentTile.row * TS;
			break;
		case Top4.S:
			cappedSpeed = neighborTile.row * TS - (tf.getY() + tf.getHeight());
			break;
		default:
			throw new IllegalArgumentException("Illegal move direction: " + dir);
		}
		return Math.min(speed, cappedSpeed);
	}
}