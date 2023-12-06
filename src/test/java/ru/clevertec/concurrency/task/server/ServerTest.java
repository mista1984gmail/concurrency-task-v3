package ru.clevertec.concurrency.task.server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.task.datatransfer.request.Request;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ServerTest {

	private Server server;

	@BeforeEach
	void setup() {
		server = new Server();
	}


	@Test
	void shouldGetValue() {
		// given
		Integer expectedIndex = 0;
		Integer expectedValue = 1;
		Request request = new Request(expectedIndex, expectedValue);

		// when
		server.getValue(request);
		Integer actualValue = server.getValues().get(expectedIndex);

		// then
		assertEquals(expectedValue, actualValue);
	}

	@Test
	void shouldGetValues() {
		// given
		List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		for (int i = 0; i < 10; i++) {
			Request request = new Request(i, i + 1);
			server.getValue(request);
		}

		// when
		List<Integer> actual = server.getValues();

		// then
		assertArrayEquals(expected.toArray(), actual.toArray());
	}

}