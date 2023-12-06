package ru.clevertec.concurrency.task.client.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.task.client.Client;
import ru.clevertec.concurrency.task.client.service.ClientService;
import ru.clevertec.concurrency.task.datatransfer.request.Request;
import ru.clevertec.concurrency.task.datatransfer.response.Response;
import ru.clevertec.concurrency.task.exception.ChecksumMatchException;
import ru.clevertec.concurrency.task.generator.ElementGenerator;
import ru.clevertec.concurrency.task.util.ConstantsForTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClientServiceImplTest {

	private Client client;
	private ClientService clientService;

	@BeforeEach
	void setup() {
		client = new Client(ElementGenerator.generateSerialArrayOfInteger(ConstantsForTest.SIZE_TRANSMIT_LIST));
		clientService = new ClientServiceImpl(client);
	}

	@Test
	void shouldGetCorrectSizeTransmitList() {
		// given
		Integer expected = 100;

		// when
		Integer actual = clientService.getSizeTransmitList();

		// then
		assertEquals(expected, actual);
	}

	@Test
	void shouldGetNotCorrectSizeTransmitList() {
		// given
		Integer expected = 99;

		// when
		Integer actual = clientService.getSizeTransmitList();

		// then
		assertNotEquals(expected, actual);
	}

	@Test
	void shouldAddElementToTransmitList() {
		// given
		Integer element = 101;
		Integer expectedSize = 101;

		// when
		clientService.add(element);
		Integer actualSize = clientService.getSizeTransmitList();

		// then
		assertEquals(expectedSize, actualSize);
	}

	@Test
	void shouldRemoveIndex() {
		// given
		Integer index = 99;
		Request expected = new Request(99, 100);

		// when
		Request actual = clientService.remove(index);

		// then
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnNull_whenIndexIsNull() {
		// given
		Integer index = null;

		// when
		Request actual = clientService.remove(index);

		// then
		assertNull(actual);
	}

	@Test
	void shouldReturnNull_whenIndexIsMoreThenSizeTransmitList() {
		// given
		Integer index = 101;

		// when
		Request actual = clientService.remove(index);

		// then
		assertNull(actual);
	}

	@Test
	void shouldClearAllValues() {
		// given
		Integer expected = 0;

		// when
		clientService.clearValues();
		Integer actual = clientService.getSizeTransmitList();

		// then
		assertEquals(expected, actual);
	}

	@Test
	void shouldReturnTrue_whenClientGetAnswerFromServerWithReceivedListSize100AndCheckSum5050() {
		// given
		Response response = new Response(100);
		Integer checkSum = 5050;

		// when
		boolean actual = clientService.getGeneralAnswerFromServer(response, checkSum);

		// then
		assertTrue(actual);
	}

	@Test
	void shouldThrowsChecksumMatchException_whenClientGetAnswerFromServerWithDoNotCorrectCheckSum() {
		// given
		Response response = new Response(100);
		Integer checkSum = 5049;
		String errorMessage = "Checksum does not match. Data was not transferred to the server correctly.";

		// when
		ChecksumMatchException thrown = assertThrows(ChecksumMatchException.class, () -> {
			clientService.getGeneralAnswerFromServer(response, checkSum);
		});

		// then
		assertEquals(errorMessage, thrown.getMessage());
	}

	@Test
	void shouldThrowsChecksumMatchException_whenClientGetAnswerFromServerWithDoNotCorrectListSize() {
		// given
		Response response = new Response(99);
		Integer checkSum = 5050;
		String errorMessage = "Checksum does not match. Data was not transferred to the server correctly.";

		// when
		ChecksumMatchException thrown = assertThrows(ChecksumMatchException.class, () -> {
			clientService.getGeneralAnswerFromServer(response, checkSum);
		});

		// then
		assertEquals(errorMessage, thrown.getMessage());
	}

}