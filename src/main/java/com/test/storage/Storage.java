package com.test.storage;

import com.test.exception.ValidationException;
import lombok.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Scanner;

@Data
public class Storage implements IStorage {

	private HashMap<String, BigDecimal> map = new HashMap<>();

	@Override
	public void loadLine(final String inputLine) throws ValidationException {
		if (inputLine == null) {
			throw new ValidationException("Null value supplied as input.");
		}

		final String[] splitInput = inputLine.split(" ");
		if (splitInput.length != 2) {
			throw new ValidationException("Invalid input in invalid format. Please use correct input format.");
		}

		final BigDecimal weight = parseWeight(splitInput[0]);
		final String postalCode = splitInput[1];

		checkWeightValid(weight);
		checkPostalCodeValid(postalCode);

		map.merge(splitInput[1], weight, BigDecimal::add);
	}

	@Override
	public void preloadFromFile(final String file) {
		System.out.println("Preloading data from input file: " + file);
		File dataPreload = new File(file);
		try (Scanner scanner = new Scanner(dataPreload)) {
			while (scanner.hasNextLine()) {
				loadLine(scanner.nextLine());
			}
		} catch (FileNotFoundException e) {
			System.err.println("File \"" + file + "\" was not found.");
		} catch (ValidationException e) {
			System.err.println(e.getMessage());
		}
	}

	@Override
	public void loadFromUser() {
		try (Scanner scanner = new Scanner(System.in)) {
			String input = scanner.nextLine();

			while (!input.equals("quit")) {
				try {
					loadLine(input);
				} catch (ValidationException e) {
					System.err.println(e.getMessage());
				}
				System.out.println("Values added successfully.");
				input = scanner.nextLine();
			}
		}
	}

	@Override
	public void printContent() {
		map.forEach((k, v) -> System.out.println(k + " " + v.toPlainString()));
	}

	private BigDecimal parseWeight(final String weightInput) throws ValidationException {
		try {
			return new BigDecimal(weightInput);
		} catch (NumberFormatException e) {
			throw new ValidationException("Invalid number format for weight value: " + weightInput);
		}
	}

	private void checkPostalCodeValid(final String postalCode) throws ValidationException {
		if (postalCode == null) {
			throw new ValidationException("Postal code is null.");
		}
		if (postalCode.length() != 5) {
			throw new ValidationException("Postal code does not have fixed 5 digits: " + postalCode);
		}
		if (!postalCode.matches("[0-9]+")) {
			throw new ValidationException("Postal code does not contain digits only: " + postalCode);
		}
	}

	private void checkWeightValid(final BigDecimal weight) throws ValidationException {
		if (weight == null) {
			throw new ValidationException("Weight is null.");
		}
		if (weight.compareTo(BigDecimal.ZERO) <= 0) {
			throw new ValidationException("Weight value is not a positive number: " + weight.toPlainString());
		}
		if (weight.scale() > 3) {
			throw new ValidationException("Weight value has more than 3 maximal decimal places: " + weight.toPlainString());
		}
	}

}
