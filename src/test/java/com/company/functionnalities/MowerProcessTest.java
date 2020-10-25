package com.company.functionnalities;

import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.entities.Orientation;
import com.company.entities.Position;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MowerProcessTest {

	private Mower mower;
	private MowerProcess mowerProcessForward;
	private MowerProcess mowerProcessLeft;
	private MowerProcess mowerProcessRight;
	private MowerProcess mowerProcessUnknown;

	Lawn lawn;
	String instructionsForward;
	String instructionOrientationRight;
	String instructionOrientationLeft;
	String instructionUnknown;

	@BeforeEach
	void setUp() {
		Position position = new Position(1L, 1L, Orientation.EAST);
		mower = new Mower("Mower_1", position);
		ConcurrentHashMap<String, Position> concurrentHashMap = new ConcurrentHashMap<>();
		concurrentHashMap.put(mower.getName(), mower.getCurrentPosition());
		lawn = new Lawn(7L, 7L, concurrentHashMap);
		instructionsForward = "F";
		instructionOrientationLeft = "L";
		instructionOrientationRight = "R";
		instructionUnknown = "U";
		mowerProcessForward = new MowerProcess(mower.getName(), lawn, instructionsForward);
		mowerProcessLeft = new MowerProcess(mower.getName(), lawn, instructionOrientationLeft);
		mowerProcessRight = new MowerProcess(mower.getName(), lawn, instructionOrientationRight);
		mowerProcessUnknown = new MowerProcess(mower.getName(), lawn, instructionUnknown);
	}

	@ParameterizedTest
	@ValueSource(strings = {"L", "R"})
	void computeOrientationInstructionTest(String instructionOrientation) {
		MowerProcess mowerProcessAllOrientation = new MowerProcess(mower.getName(), lawn,
				instructionOrientation);
		Position nextPosition = new Position(1L, 1L, Orientation.NORTH);
		mowerProcessAllOrientation.run();
		//@formatter:off
		Assertions.assertAll("The Orientation should change from EAST but not the axis",
				() -> Assertions
				.assertEquals(lawn.getMowersPositionReferential()
				                  .get(mower.getName())
				                  .getxPosition(), nextPosition.getxPosition()),
				() -> Assertions.assertEquals(lawn
				.getMowersPositionReferential()
				.get(mower.getName())
				.getyPosition(), nextPosition.getyPosition()),
				() -> Assertions.assertNotEquals(lawn.getMowersPositionReferential().get(mower.getName()),mower.getCurrentPosition().getOrientation()));
	  //@formatter:on
	}

	@Test
	void computeFormwardInstructionTest() {

		mowerProcessForward.run();

		//@formatter:off
		Assertions.assertEquals(mower.getCurrentPosition()
		                             .getOrientation(), lawn.getMowersPositionReferential()
		                                                    .get(mower.getName())
		                                                    .getOrientation());
		//@formatter:on
	}

	@Test
	void computeUnkownInstructionTest() {

		mowerProcessUnknown.run();
		//@formatter:off
		Assertions.assertAll("The position should not change",
				() -> Assertions.assertEquals(lawn.getMowersPositionReferential().get(mower.getName()).getxPosition(), mower.getCurrentPosition().getxPosition()),
				() -> Assertions.assertEquals(lawn.getMowersPositionReferential().get(mower.getName()).getyPosition(), mower.getCurrentPosition().getyPosition()),
				() -> Assertions.assertEquals(lawn.getMowersPositionReferential().get(mower.getName()).getOrientation(), mower.getCurrentPosition().getOrientation()));
		//@formatter:on
	}

}