package com.test.storage;

import com.test.exception.ValidationException;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

/**
 * Just a showcase of testing validations.
 * In real scenario there would be more tests including other public methods.
 */
public class StorageTest {

	private Storage cut;

	@Before
	public void setUp() throws Exception {
		cut = new Storage();
	}

	@Test
	public void loadLine() {
		assertThatNoException().isThrownBy(() -> cut.loadLine("1.23 12345"));
		assertThat(cut.getMap().get("12345")).isEqualByComparingTo(BigDecimal.valueOf(1.23d));
	}

	@Test
	public void loadLine_null() {
		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine(null))
				.withMessage("Null value supplied as input.");
		assertThat(cut.getMap()).isEmpty();
	}

	@Test
	public void loadLine_invalidInputFormats() {
		String errorMessage = "Invalid input in invalid format. Please use correct input format.";

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine(""))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine(" "))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1.23"))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("abc"))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("123 "))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("abc "))
				.withMessage(errorMessage);
		assertThat(cut.getMap()).isEmpty();
	}

	@Test
	public void loadLine_invalidWeights() {
		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1.123456 12345"))
				.withMessage("Weight value has more than 3 maximal decimal places: 1.123456");
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("-1 12345"))
				.withMessage("Weight value is not a positive number: -1");
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1,123 12345"))
				.withMessage("Invalid number format for weight value: 1,123");
		assertThat(cut.getMap()).isEmpty();
	}

	@Test
	public void loadLine_invalidPostalCodes() {
		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1.12 12"))
				.withMessage("Postal code does not have fixed 5 digits: 12");
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1.12 1234567"))
				.withMessage("Postal code does not have fixed 5 digits: 1234567");
		assertThat(cut.getMap()).isEmpty();

		assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> cut.loadLine("1.12 abcde"))
				.withMessage("Postal code does not contain digits only: abcde");
		assertThat(cut.getMap()).isEmpty();
	}

}