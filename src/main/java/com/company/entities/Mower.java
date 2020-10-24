package com.company.entities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Mower {

	private static Logger logger = Logger.getLogger("Mower");

	private String name;
	private Position currentPosition;

	public Mower(String name, Position currentPosition) {
		this.name = name;
		this.currentPosition = currentPosition;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Position currentPosition) {
		this.currentPosition = currentPosition;
	}

	//Todo to be tested
	public Position computePositionFromInstruction(char instruction, Lawn lawn)
			throws InterruptedException {

		if(instruction =='L'){
			switch (currentPosition.getOrientation()) {
				case NORTH:
					currentPosition.setOrientation(Orientation.WEST);
					break;
				case WEST:
					currentPosition.setOrientation(Orientation.SOUTH);
					break;
				case SOUTH:
					currentPosition.setOrientation(Orientation.EAST);
					break;
				case EAST:
					currentPosition.setOrientation(Orientation.NORTH);
					break;
				default:

			}
		}
		if(instruction =='R'){
			switch (currentPosition.getOrientation()) {
				case NORTH:
					currentPosition.setOrientation(Orientation.EAST);
					break;
				case EAST:
					currentPosition.setOrientation(Orientation.SOUTH);
					break;
				case SOUTH:
					currentPosition.setOrientation(Orientation.WEST);
					break;
				case WEST:
					currentPosition.setOrientation(Orientation.NORTH);
					break;
			}
		}
		if(instruction =='F'){
			lawn.moveForwardOrNot(this.name);
		}

	return currentPosition;
	}



}
