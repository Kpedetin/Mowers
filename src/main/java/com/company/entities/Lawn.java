package com.company.entities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lawn {

	private static final Logger logger = LoggerFactory.getLogger(Lawn.class);


	private final long maxX;
	private final long maxY;
	private final ConcurrentHashMap<String, Position> mowersPositionReferential;

	public Lawn(long maxX, long maxY, ConcurrentHashMap<String, Position> MowersPositionReferential) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.mowersPositionReferential = MowersPositionReferential;
	}

	public long getMaxX() {
		return maxX;
	}

	public long getMaxY() {
		return maxY;
	}

	public Map<String, Position> getMowersPositionReferential() {
		return mowersPositionReferential;
	}

	@Override
	public String toString() {
		return "Lawn{" + "maxX=" + maxX + ", maxY=" + maxY + ", mowersPositionReferential=" + mowersPositionReferential +
				'}';
	}

	/**
	 * Update into the referential the orientation of a mower
	 *
	 * @param nameMower mower name
	 * @param orientation new orientation
	 */
	public void updateOrientationIntoLawnReferential(String nameMower, Orientation orientation) {
		synchronized (this) {
			logger.debug("You have the lock " + this + "on  Mower named " + nameMower + " to update his Orientation");
			Position position = getMowersPositionReferential().get(nameMower);
			if (position.getOrientation() != orientation) {
				position.setOrientation(orientation);
			}
			logger.debug("You release the lock" + this + "on  Mower named " + nameMower + "after updating his Orientation");
		}
	}

	/**
	 * Add or update mower position into the referential
	 *
	 * @param nameMower mower name
	 * @param mowerNewPosition mower new position
	 */
	public void addMowerPositionToRefential(String nameMower, Position mowerNewPosition) {
		mowersPositionReferential.compute(nameMower, (key, value) -> mowerNewPosition);
	}

	private boolean isThePositionAlreadyTaken(Position position) {
		synchronized (this) {
			return mowersPositionReferential.values()
			                                .stream()
			                                .anyMatch(p -> p.getxPosition() == position.getxPosition() &&
					                                p.getyPosition() == position.getyPosition());
		}
	}


	private boolean isThePositionInsideLawn(Position position) {
		return position.getxPosition() <= this.maxX && position.getxPosition() >= 0 &&
				position.getyPosition() <= this.maxY && position.getyPosition() >= 0;
	}

	/**
	 * Compute the next position on a mower and try to update his position into the referential
	 *
	 * @param nameMower mower name
	 */
	public void computeAndUpdatePositionIntoLawnRefential(String nameMower) {
		synchronized (this) {
			logger.debug("You have the lock " + this + "on  Mower named " + nameMower + " to update his " + "Position");
			Position currentPosition = this.getMowersPositionReferential().get(nameMower);
			Position nextPosition = computeNextPositionFromCurrentPosition(currentPosition);
			updatePositionIfNecessary(nextPosition, nameMower);
			logger.debug("You release the lock" + this + "on  Mower named " + nameMower + "after updating his Position");

		}
	}

	private Position computeNextPositionFromCurrentPosition(Position position) {
		Position nextPosition;
		switch (position.getOrientation()) {
			case NORTH:
				nextPosition = new Position(position.getxPosition(), position.getyPosition() + 1, position.getOrientation());
				break;
			case EAST:
				nextPosition = new Position(position.getxPosition() + 1, position.getyPosition(), position.getOrientation());
				break;
			case WEST:
				nextPosition = new Position(position.getxPosition() - 1, position.getyPosition(), position.getOrientation());
				break;
			case SOUTH:
				nextPosition = new Position(position.getxPosition(), position.getyPosition() - 1, position.getOrientation());
				break;
			default:
				nextPosition = position;
		}
		return nextPosition;
	}

	private void updatePositionIfNecessary(Position nextPosition, String name) {
		logger.debug("Mower named " + name + " is processing from " + this.getMowersPositionReferential().get(name) +
				" towards position :" + nextPosition);
		if (nextPosition != null && isThePositionInsideLawn(nextPosition) && !isThePositionAlreadyTaken(nextPosition)) {
			addMowerPositionToRefential(name, nextPosition);
			logger.debug("Mower named: " + name + " has moved and his new position is : " +
					this.getMowersPositionReferential().get(name));
		} else {
			logger.debug("Mower named: " + name +
					" find another mower on his next position or is out of the lawn boundarie. His current " + "position is " +
					this.getMowersPositionReferential().get(name));
		}
	}

}
