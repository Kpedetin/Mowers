package com.company.functionnalities;

import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.entities.Orientation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InOutTest {

	@Test
	void extractLawnDimensionNotNumericTest() {
		String dimension = "X 1";
		Assertions.assertThrows(Exception.class, () -> InOut.extractLawnDimension(dimension));
	}

	@Test
	void extractLawnDimensionInvalidTest() {
		String dimension = "1 1 3";
		Assertions.assertThrows(Exception.class, () -> InOut.extractLawnDimension(dimension));
	}

	@Test
	void extractLawnDimensionOkTest() {
		String dimension = "1 2";
		Assertions.assertDoesNotThrow(() -> {
			Lawn lawn = InOut.extractLawnDimension(dimension);
			Assertions.assertEquals(1, lawn.getMaxX());
			Assertions.assertEquals(2, lawn.getMaxY());
		});
	}

	@Test
	void extractMowersOKTest() {
		String position = "1 2 N";
		int positionInThePool = 1;
		Assertions.assertDoesNotThrow(() -> {
			Mower mower = InOut.extractMowers(position, positionInThePool);
			Assertions.assertEquals(1, mower.getCurrentPosition().getxPosition());
			Assertions.assertEquals(2, mower.getCurrentPosition().getyPosition());
			Assertions.assertEquals(Orientation.NORTH, mower.getCurrentPosition().getOrientation());
			Assertions.assertEquals("Mower_1", mower.getName());

		});
	}

	@Test
	void extractMowersPositionFromFileNotNUmeric() {
		String position = "1 X N";
		int positionInThePool = 1;
		//@formatter:off
		String message = Assertions.assertThrows(Exception.class, () -> InOut.extractMowers(position,
				positionInThePool)).getMessage();
		Assertions.assertEquals("Position axis (X, Y) must be numeric", message);
		//@formatter:on
	}

	@Test
	void extractMowersUnknownOrientation() {
		String position = "1 1 X";
		int positionInThePool = 1;
		//@formatter:off
		String message = Assertions.assertThrows(Exception.class, () -> InOut.extractMowers(position,
				positionInThePool)).getMessage();
		Assertions.assertEquals("Unknown Orientation X from the file", message);
		//@formatter:on
	}

}