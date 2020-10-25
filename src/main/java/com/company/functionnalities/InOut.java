package com.company.functionnalities;

import static com.company.entities.Orientation.convertCharToOrientation;

import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.entities.Position;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InOut {

	private static final Logger logger = LoggerFactory.getLogger(InOut.class);

	public static List<String> readFile(String externalAbsolutePath) throws IOException {
		return Files.lines(Paths.get(externalAbsolutePath)).collect(Collectors.toList());
	}

	public static void writeIntoFile(String externalAbsolutePath, Lawn lawn) {
		List<Map.Entry<String, Position>> sortedMowersPosition = lawn.getMowersPositionReferential()
		                                                             .entrySet()
		                                                             .stream()
		                                                             .sorted(Map.Entry.comparingByKey())
		                                                             .collect(Collectors.toList());
		sortedMowersPosition.forEach(mower -> {
			try (FileWriter fw = new FileWriter(externalAbsolutePath +
					"_output", true); BufferedWriter bw = new BufferedWriter(fw); PrintWriter pw = new PrintWriter(bw)) {
				pw.println(mower.getValue().formatToFile());
			} catch (IOException e) {
				logger.warn("An error occur while writing to the file " + e.getMessage());
			}
		});
	}

	/**
	 * Extract from a string the dimension (Xmax,Ymax) of the lawn
	 *
	 * @param firstLine String formatted Xmax Ymax and they must be numeric
	 * @return Initialized lawn that with Xmax, Ymax setted from firstline and empty mower's referential
	 * @throws Exception Raise when the line is misformatted
	 */
	public static Lawn extractLawnDimension(String firstLine) throws Exception {
		String[] s = firstLine.split(" ");
		if (s.length == 2) {
			return new Lawn(Long.parseLong(s[0]), Long.parseLong(s[1]), new ConcurrentHashMap<>());
		}
		throw new Exception("File misformated");
	}

	/**
	 * Extract or Init a mower from a string
	 *
	 * @param currentLine the string must follow that pattern X Y O with X and Y as numeric and O between [NWES]
	 * @param place the position of the mower in the file
	 * @throws Exception Raise when the line is misformatted
	 */
	public static Mower extractMowers(String currentLine, long place) throws Exception {
		String[] s = currentLine.split(" ");
		if (s.length == 3) {
			try {
				Position position = new Position(Long.parseLong(s[0]), Long.parseLong(s[1]),
						convertCharToOrientation(s[2].charAt(0)));
				return new Mower("Mower_" + place, position);
			} catch (NumberFormatException nfe) {
				throw new Exception("Position axis (X, Y) must be numeric");
			}
		} else {
			throw new Exception(
					"Position must follow the pattern X Y O with X and Y numeric and O a " + "value between [NWES]");
		}
	}

}
