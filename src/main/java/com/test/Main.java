package com.test;

import com.test.storage.IStorage;
import com.test.storage.Storage;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		IStorage storage = new Storage();

		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		executorService.scheduleAtFixedRate(storage::printContent, 1L, 1L, TimeUnit.MINUTES);

		if (args.length > 0) {
			storage.preloadFromFile(args[0]);
		}

		System.out.println("Please input values in the format \"<weight> <postal code>\". Type \"quit\" to end the program.");

		storage.loadFromUser();

		executorService.shutdownNow();
	}

}
