package com.company.functionnalities;

import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.entities.Position;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MowerProcess implements Runnable {
	private static Logger logger = Logger.getLogger("MowerProcess");

	private Mower mower ;
	private volatile Lawn lawn;
	private String instructions;

	public MowerProcess(Mower mower, Lawn lawn, String instructions) {
		this.mower = mower;
		this.lawn = lawn;
		this.instructions=instructions;
	}

	@Override
	public void run() {
		for(char instruction: instructions.toCharArray()){
			try {
				mower.computePositionFromInstruction(instruction, lawn);
			}catch (InterruptedException ine){
				logger.log(Level.SEVERE,ine.getMessage());
			}
		}
	}
}
