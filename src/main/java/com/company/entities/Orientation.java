package com.company.entities;

import java.util.Optional;

public enum Orientation {
	NORTH,
	EAST,
	SOUTH,
	WEST;


	public static Orientation convertCharToOrientation(char c) throws Exception {
		Orientation orientation;
		switch (c) {
			case 'N':
				orientation = Orientation.NORTH;
				break;
			case 'W':
				orientation = Orientation.WEST;
				break;
			case 'E':
				orientation = Orientation.EAST;
				break;
			case 'S':
				orientation = Orientation.SOUTH;
				break;
			default:
				throw new Exception("Unknown Orientation " + c + " from the file");
		}
		return orientation;
	}

	public static char convertOrientationToChar(Orientation orientation) {
		char c = ' ';
		switch (orientation) {
			case NORTH:
				c = 'N';
				break;
			case WEST:
				c = 'W';
				break;
			case EAST:
				c = 'E';
				break;
			case SOUTH:
				c = 'S';
				break;
		}
		return c;
	}

	public static Optional<Orientation> toTheLeft(Orientation initialOrientation) {
		Optional<Orientation> finalOrientation;
		switch (initialOrientation) {
			case NORTH:
				finalOrientation = Optional.of(Orientation.WEST);
				break;
			case WEST:
				finalOrientation = Optional.of(Orientation.SOUTH);
				break;
			case SOUTH:
				finalOrientation = Optional.of(Orientation.EAST);
				break;
			case EAST:
				finalOrientation = Optional.of(Orientation.NORTH);
				break;
			default:
				finalOrientation = Optional.empty();
		}
		return finalOrientation;
	}

	public static Optional<Orientation> toTheRight(Orientation initialOrientation) {
		Optional<Orientation> finalOrientation;
		switch (initialOrientation) {
			case NORTH:
				finalOrientation = Optional.of(Orientation.EAST);
				break;
			case EAST:
				finalOrientation = Optional.of(Orientation.SOUTH);
				break;
			case SOUTH:
				finalOrientation = Optional.of(Orientation.WEST);
				break;
			case WEST:
				finalOrientation = Optional.of(Orientation.NORTH);
				break;
			default:
				finalOrientation = Optional.empty();
		}
		return finalOrientation;
	}

}
