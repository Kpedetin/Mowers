package com.company.entities;


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
}
