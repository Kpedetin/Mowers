package com.company.entities;

import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class OrientationTest {

	@ParameterizedTest
	@MethodSource("orientationGenerator")
	void convertOrientationToCharTest(Orientation orientation) {
		char c = Orientation.convertOrientationToChar(orientation);
		Assertions.assertEquals(orientation.toString().charAt(0), c);
	}

	@Test
	void convertCharToOrientation() {
		char[] chars = "NWES ".toCharArray();

		//@formatter:off
		Assertions.assertAll("Conversion should equal",
				() -> Assertions.assertEquals(Orientation.convertCharToOrientation(chars[0]),Orientation.NORTH),
				() -> Assertions.assertEquals(Orientation.convertCharToOrientation(chars[1]),Orientation.WEST),
				() -> Assertions.assertEquals(Orientation.convertCharToOrientation(chars[2]),Orientation.EAST),
				() -> Assertions.assertEquals(Orientation.convertCharToOrientation(chars[3]),Orientation.SOUTH),
				() -> Assertions.assertThrows(Exception.class,()->Orientation.convertCharToOrientation(chars[4]))
				);
		//@formatter:on
	}

	@Test
	void toLeftTest() {
		//@formatter:off
		Assertions.assertAll("to Left orientation computation",
				() -> Assertions.assertEquals(Orientation.toTheLeft(Orientation.NORTH),Optional.of(Orientation.WEST)),
				() -> Assertions.assertEquals(Orientation.toTheLeft(Orientation.WEST),Optional.of(Orientation.SOUTH)),
				() -> Assertions.assertEquals(Orientation.toTheLeft(Orientation.SOUTH),Optional.of(Orientation.EAST)),
				() -> Assertions.assertEquals(Orientation.toTheLeft(Orientation.EAST),Optional.of(Orientation.NORTH))
				);
		//@formatter:on

	}

	@Test
	void toRightTest() {
		//@formatter:off
		Assertions.assertAll("to right orientation computation",
				() -> Assertions.assertEquals(Orientation.toTheRight(Orientation.NORTH),Optional.of(Orientation.EAST)),
				() -> Assertions.assertEquals(Orientation.toTheRight(Orientation.EAST),Optional.of(Orientation.SOUTH)),
				() -> Assertions.assertEquals(Orientation.toTheRight(Orientation.SOUTH),Optional.of(Orientation.WEST)),
				() -> Assertions.assertEquals(Orientation.toTheRight(Orientation.WEST),Optional.of(Orientation.NORTH))
				);
		//@formatter:on

	}

	private static Stream<Arguments> orientationGenerator() {
		return Stream.of(Arguments.of(Orientation.EAST), Arguments.of(Orientation.NORTH),
				Arguments.of(Orientation.SOUTH), Arguments
				.of(Orientation.WEST));
	}
}