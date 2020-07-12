package de.amr.games.pacman.view.api;

import java.awt.Color;
import java.awt.Font;

public interface ThemeParameters {

	int $int(String key);

	float $float(String key);

	Font $font(String key);

	Color $color(String key);

	<T> T $value(String key);
}
