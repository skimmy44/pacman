package de.amr.games.pacman.model;

public class Maps {

	public static GameMap PACMAN_MAP = new GameMap(new byte[][] {
			//@formatter:off
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			{ 1, 2, 2, 2, 2, 2,18, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2,18, 2, 2, 2, 2, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1, 6, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 6, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1,18, 2, 2, 2, 2,18, 2, 2,18, 2, 2,18, 2, 2,18, 2, 2,18, 2, 2,18, 2, 2, 2, 2,18, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1, 2, 2, 2, 2, 2,18, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1,18, 2, 2, 2, 2, 2, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 0, 0,48, 0, 0,48, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{64,64,64,64,64,64,18, 0, 0,16, 1, 0, 0, 0, 0, 0, 0, 1,16, 0, 0,18,64,64,64,64,64,64 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1,16, 0, 0, 0, 0, 0, 0, 0, 0,16, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1 },
			{ 1, 2, 2, 2, 2, 2,18, 2, 2,18, 2, 2, 2, 1, 1, 2, 2, 2,18, 2, 2,18, 2, 2, 2, 2, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1 },
			{ 1, 6, 2, 2, 1, 1,18, 2, 2,18, 2, 2,50, 0, 0,50, 2, 2,18, 2, 2,18, 1, 1, 2, 2, 6, 1 },
			{ 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1 },
			{ 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1 },
			{ 1, 2, 2,18, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2,18, 2, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{ 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1 },
			{ 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,18, 2, 2,18, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1 },
			{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
			//@formatter:on
	});
}