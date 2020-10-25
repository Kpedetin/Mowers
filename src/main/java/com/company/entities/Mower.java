package com.company.entities;

public class Mower {

	private final String name;
	private final Position currentPosition;

	public Mower(String name, Position currentPosition) {
		this.name = name;
		this.currentPosition = currentPosition;
	}

	public String getName() {
		return name;
	}

	public Position getCurrentPosition() {
		return currentPosition;
	}

}

