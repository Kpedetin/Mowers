package com.company.entities;

import java.util.Objects;

public class Position {

	private long xPosition;
	private long yPosition;
	private Orientation orientation;

	public Position(long xPosition, long yPosition, Orientation orientation) {
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.orientation = orientation;
	}

	public long getxPosition() {
		return xPosition;
	}

	public void setxPosition(long xPosition) {
		this.xPosition = xPosition;
	}

	public long getyPosition() {
		return yPosition;
	}

	public void setyPosition(long yPosition) {
		this.yPosition = yPosition;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "Position{" + "xPosition=" + xPosition + ", yPosition=" + yPosition + ", orientation=" +
				orientation + '}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Position position = (Position) o;
		return xPosition == position.xPosition && yPosition == position.yPosition &&
				orientation == position.orientation;
	}

	@Override
	public int hashCode() {
		return Objects.hash(xPosition, yPosition, orientation);
	}

	public String formatToFile() {
		return xPosition + " " + yPosition + " " + Orientation.convertOrientationToChar(orientation);
	}
}
