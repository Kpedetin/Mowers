package com.company.functionnalities;

import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.entities.Orientation;
import com.company.entities.Position;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InOut {

	public static List<String> readFile(String externalAbsolutePath) throws IOException {
		return Files.lines(Paths.get(externalAbsolutePath)).collect(Collectors.toList());
	}

	public static Lawn extractLawnDimension(String firstLine)
			throws Exception {
		String[] s = firstLine.split(" ");
		if(s.length==2 ) {
			try {
				return  new Lawn(Long.parseLong(s[0]), Long.parseLong(s[1]), new ConcurrentHashMap<>());
			} catch (NumberFormatException nfe) {
				throw nfe;
			}
		}else {
			throw new Exception("File misformated");
		}

	}

	public static Mower extractMowers(String currentLine, long indice)
			throws Exception {
		String[] s = currentLine.split(" ");
		if(s.length==3){
			try{
				Position position = new Position(Long.parseLong(s[0]),Long.parseLong(s[1]),convertCharToOrientation(s[2].charAt(0)));
				return new Mower("Mower " +indice, position);
				//lawn.updateMowerPosition("Mower " +indice,position);
			}catch (NumberFormatException nfe){
				throw new Exception ("File misformated");
			}
		}else {
			throw new Exception ("File misformated");
		}
	}

	public static Orientation convertCharToOrientation(char s){
		Orientation orientation=Orientation.NORTH;
		switch (s){
			case 'N': orientation= Orientation.NORTH; break;
			case 'W': orientation = Orientation.WEST; break;
			case 'E': orientation = Orientation.EAST;break;
			case 'S': orientation = Orientation.SOUTH;break;
		}
		return orientation;
	}
}
