package com.company.entities;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LawnTest {

	private Lawn lawn;
	private Mower mower1;
	private Mower mower2;
	private Mower mower3;


	@BeforeEach
	void setUp() {
		mower3 = new Mower("Mower_3", new Position(0L, 1L, Orientation.WEST));
		mower2 = new Mower("Mower_2", new Position(0L, 1L, Orientation.EAST));
		mower1 = new Mower("Mower_1", new Position(1L, 1L, Orientation.EAST));
		ConcurrentHashMap<String, Position> mowersPositionReferential = new ConcurrentHashMap<>();
		mowersPositionReferential.put(mower1.getName(), mower1.getCurrentPosition());
		mowersPositionReferential.put(mower2.getName(), mower2.getCurrentPosition());
		mowersPositionReferential.put(mower3.getName(), mower3.getCurrentPosition());
		lawn = new Lawn(7L, 7L, mowersPositionReferential);
	}

	@ParameterizedTest
	@MethodSource("orientationGenerator")
	void updateMowerIntoLawnReferentialTest(Orientation orientationExpected) {

		lawn.updateOrientationIntoLawnReferential(mower1.getName(), orientationExpected);
		Assertions.assertFalse(lawn.getMowersPositionReferential().isEmpty());
		Assertions.assertEquals(lawn.getMowersPositionReferential()
		                            .get(mower1.getName())
		                            .getOrientation(), orientationExpected);
	}


	@Test
	void positionAlreadyExistsInLawnReferential() {
		lawn.computeAndUpdatePositionIntoLawnRefential("Mower_2");
		Assertions.assertEquals(lawn.getMowersPositionReferential()
		                            .get("Mower_2"), mower2.getCurrentPosition());
	}

	@Test
	void nextPositionOutOfLawnBoundaries() {
		lawn.computeAndUpdatePositionIntoLawnRefential("Mower_3");
		Assertions.assertEquals(lawn.getMowersPositionReferential()
		                            .get("Mower_3"), mower3.getCurrentPosition());
	}


	private static Stream<Arguments> orientationGenerator() {
		return Stream.of(Arguments.of(Orientation.EAST), Arguments.of(Orientation.NORTH),
				Arguments.of(Orientation.SOUTH), Arguments
				.of(Orientation.WEST));
	}

}