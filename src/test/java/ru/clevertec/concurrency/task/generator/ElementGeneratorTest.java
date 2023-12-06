package ru.clevertec.concurrency.task.generator;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class ElementGeneratorTest {

	@Test
	void generateSerialArrayOfInteger() {
		// given
		Integer sizeOfList = 10;
		List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// when
		List<Integer> actual = ElementGenerator.generateSerialArrayOfInteger(sizeOfList);

		// then
		assertArrayEquals(expected.toArray(), actual.toArray());
	}
}