package com.company.functionnalities;

import com.company.entities.Lawn;
import com.company.entities.Orientation;
import java.util.Optional;

public class MowerProcess implements Runnable {

	private final String name;
	private final Lawn lawn;
	private final String instructions;

	public MowerProcess(String name, Lawn lawn, String instructions) {
		this.name = name;
		this.lawn = lawn;
		this.instructions = instructions;
	}


	@Override
	public void run() {
		for (char instruction : instructions.toCharArray()) {
			process(instruction);
		}
	}

	private void process(char instruction) {
		if (instruction == 'L' || instruction == 'R') {
			//@formatter:Off
				computeNewOrientation(instruction,lawn)
				.ifPresent(orientation -> lawn.updateOrientationIntoLawnReferential(this.name, orientation));
				//@formatter::On
	    } else if (instruction == 'F') {
		lawn.computeAndUpdatePositionIntoLawnRefential(this.name);
	 }

	}

	private Optional<Orientation> computeNewOrientation(char instruction,Lawn lawn) {
		Optional<Orientation> orientation = Optional.empty();
		if (instruction == 'R') {
			orientation = Orientation.toTheRight(lawn.getMowersPositionReferential().get(name).getOrientation());
		} else if (instruction == 'L') {
			orientation = Orientation.toTheLeft(lawn.getMowersPositionReferential().get(name).getOrientation());
		}
		return orientation;
	}
}
