package com.company.entities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lawn {
	private static Logger logger = Logger.getLogger("Lawn");

	private final long maxX;
	private final long maxY;
	private volatile ConcurrentHashMap<String, Position> mowersPositionReferential;

	public Lawn(long maxX, long maxY, ConcurrentHashMap<String, Position> MowersPositionReferential) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.mowersPositionReferential = MowersPositionReferential;
	}

	public Map<String, Position> getMowersPositionReferential() {
		return mowersPositionReferential;
	}

	public long getMaxX() {
		return maxX;
	}

	public long getMaxY() {
		return maxY;
	}


	@Override
	public String toString() {
		return "Lawn{" + "maxX=" + maxX + ", maxY=" + maxY + ", occupiedPosition=" +
				mowersPositionReferential +
				'}';
	}

	public void updateMowerPosition(String nameMower, Position mowerNewPosition ) {
		synchronized (this){
			mowersPositionReferential.compute(nameMower,(k,v)->mowerNewPosition);
		}
	}

	public boolean isThePositionAlreadyTaken(Position position){
			return mowersPositionReferential.values().stream()
			                                        .anyMatch(p -> p.getxPosition() == position.getxPosition() &&
					                                        p.getyPosition() == position.getyPosition());
		}


	public boolean isThePositionInsideLawn(Position position){
		return position.getxPosition()<=this.maxX && position.getxPosition() >=0
				&& position.getyPosition()<=this.maxY && position.getyPosition() >=0;
	}

	public void moveForwardOrNot(String name) throws InterruptedException {
		synchronized (this.getMowersPositionReferential()) {
			Position nextPosition;
			Position currentPosition = this.getMowersPositionReferential().get(name);
			switch (currentPosition.getOrientation()) {
				case NORTH:
					nextPosition = new Position(currentPosition.getxPosition(),
							currentPosition.getyPosition() + 1, currentPosition.getOrientation());
					break;
				case EAST:
					nextPosition = new Position(currentPosition.getxPosition() +
							1, currentPosition.getyPosition(), currentPosition.getOrientation());
					break;
				case WEST:
					nextPosition = new Position(currentPosition.getxPosition() -
							1, currentPosition.getyPosition(), currentPosition.getOrientation());
					break;
				case SOUTH:
					nextPosition = new Position(currentPosition.getxPosition(),
							currentPosition.getyPosition() - 1, currentPosition.getOrientation());
					break;
				default:
					nextPosition = currentPosition;
			}
			updatePositionIfNecessary(nextPosition, name);
		}
	}

	public void updatePositionIfNecessary(Position nextPosition,String name) throws InterruptedException {

			//logger.log(Level.INFO,"Mower named "+ name +" is processing from "+this.getMowersPositionReferential().get(name)+" towards position :"+nextPosition+ "with the lock :"+ getMowersPositionReferential());
		System.out.println("Mower named "+ name +" is processing from "+this.getMowersPositionReferential().get(name)+" towards position :"+nextPosition+ "with the lock :"+ getMowersPositionReferential());
			if (nextPosition != null && isThePositionInsideLawn(nextPosition) &&
					!isThePositionAlreadyTaken(nextPosition)) {
				this.getMowersPositionReferential().put(name,nextPosition);
				updateMowerPosition(name, nextPosition);
				//logger.log(Level.INFO,"Mower named: " + name + " has moved and his new position is : " + this.getMowersPositionReferential().get(name));
				System.out.println("Mower named: " + name + " has moved and his new position is : " + this.getMowersPositionReferential().get(name));

		}
	}
}
