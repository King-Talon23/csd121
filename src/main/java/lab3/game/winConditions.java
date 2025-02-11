package lab3.game;

/**
 *Represents a winning condition on thr board
 * A win is achieved when three coordinates are in line
 *
 * @param coord1 The first coordinate in a win
 * @param coord2  The second coordinate in a win
 * @param coord3 The third coordinate in a win
 */

public record winConditions(Coordinates coord1, Coordinates coord2, Coordinates coord3) {}

