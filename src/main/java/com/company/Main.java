package com.company;


import com.company.entities.Lawn;
import com.company.entities.Mower;
import com.company.functionnalities.InOut;
import com.company.functionnalities.MowerProcess;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws Exception {
		if (args == null || args.length == 0) {
			throw new Exception("No Input file has been specified, please provide one");
		}
		try {
			logger.info("Program has started");
			int place = 1;
			Lawn lawn = new Lawn(0L, 0L, new ConcurrentHashMap<>());
			ArrayList<Mower> mowers = new ArrayList<>();
			ArrayList<String> instructions = new ArrayList<>();
			String externalAbsolutePath = args[0];

			List<String> lines = InOut.readFile(externalAbsolutePath);
			for (int i = 0; i < lines.size(); i++) {

				if (i == 0) {
					lawn = InOut.extractLawnDimension(lines.get(i));
				}
				if (i % 2 != 0) {
					Mower mower = InOut.extractMowers(lines.get(i), place);
					lawn.addMowerPositionToRefential(mower.getName(), mower.getCurrentPosition());
					place++;
					mowers.add(mower);
				}
				if (i != 0 && i % 2 == 0) {
					instructions.add(lines.get(i));
				}
			}
			ExecutorService executor = Executors.newFixedThreadPool(mowers.size());

			for (int i = 0; i < mowers.size(); i++) {
				executor.execute(new MowerProcess(mowers.get(i).getName(), lawn, instructions.get(i)));
			}

			executor.shutdown();
			executor.awaitTermination(1, TimeUnit.DAYS);
			InOut.writeIntoFile(externalAbsolutePath, lawn);
			logger.info("Program has finished his execution. You may check the file named " + args[0] + "_output");
			logger.debug(lawn.toString());
		} catch (Exception e) {
			logger.warn("An error occured in the main method" + e.getMessage());
			e.printStackTrace();
		}
	}
}
