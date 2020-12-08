package com.test.storage;

import com.test.exception.ValidationException;

/**
 * Storage for package deliveries.
 */
public interface IStorage {

	/**
	 * Loads a record to the storage in the format <weight> <postal code>.
	 *
	 * @param inputLine Input in the format <weight> <postal code>.
	 * @throws ValidationException Exception for failed input validation with error message.
	 */
	void loadLine(final String inputLine) throws ValidationException;

	/**
	 * Preloads data to the storage from the specified file.
	 * Lines should be in the format <weight> <postal code>.
	 *
	 * @param file Filename to preload from.
	 */
	void preloadFromFile(final String file);

	/**
	 * Starts loading values from the user to the storage in the format <weight> <postal code>.
	 */
	void loadFromUser();

	/**
	 * Prints content of the storage to the system out.
	 */
	void printContent();

}
