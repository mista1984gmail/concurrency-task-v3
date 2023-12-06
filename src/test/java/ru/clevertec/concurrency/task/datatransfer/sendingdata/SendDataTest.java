package ru.clevertec.concurrency.task.datatransfer.sendingdata;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.clevertec.concurrency.task.client.Client;
import ru.clevertec.concurrency.task.client.service.ClientService;
import ru.clevertec.concurrency.task.client.service.impl.ClientServiceImpl;
import ru.clevertec.concurrency.task.datatransfer.DataIntegrity;
import ru.clevertec.concurrency.task.datatransfer.response.Response;
import ru.clevertec.concurrency.task.generator.ElementGenerator;
import ru.clevertec.concurrency.task.server.Server;
import ru.clevertec.concurrency.task.util.ConstantsForTest;
import ru.clevertec.concurrency.task.util.constant.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SendDataTest {

	private Client client;
	private ClientService clientService;
	private DataIntegrity dataIntegrity;
	private Server server;
	private List<SendData> sendDataTasks;
	private ExecutorService service;

	@BeforeEach
	void setup() {
		client = new Client(ElementGenerator.generateSerialArrayOfInteger(ConstantsForTest.SIZE_TRANSMIT_LIST));
		clientService = new ClientServiceImpl(client);
		dataIntegrity = new DataIntegrity(client);
		server = new Server();
		sendDataTasks = new ArrayList<>();
		service = Executors.newFixedThreadPool(ConstantsForTest.NUMBER_THREADS);
	}

	@Test
	void checkEqualsReceivedServerListSizeAndTransmitClientListSize() throws InterruptedException {
		// given
		Integer expectedSize = clientService.getSizeTransmitList();

		// when
		for (int i = 0; i < Constants.NUMBER_THREADS; i++) {
			SendData sendData = new SendData(clientService, server, dataIntegrity);
			sendDataTasks.add(sendData);
		}
		List<Future<Response>> resultsOfTasks = service.invokeAll(sendDataTasks);
		service.shutdown();
		Integer actualSize = server.getValues().size();

		// then
		assertEquals(expectedSize, actualSize);
	}

	@Test
	void checkTransmitClientListSizeAfterTransmissionIsZero() throws InterruptedException {
		// given
		Integer expectedSize = 0;

		// when
		for (int i = 0; i < Constants.NUMBER_THREADS; i++) {
			SendData sendData = new SendData(clientService, server, dataIntegrity);
			sendDataTasks.add(sendData);
		}
		List<Future<Response>> resultsOfTasks = service.invokeAll(sendDataTasks);
		service.shutdown();
		Integer actualSize = clientService.getSizeTransmitList();

		// then
		assertEquals(expectedSize, actualSize);
	}

	@Test
	void shouldBeAccumulator5050_withTransmitClientListSize100() throws InterruptedException {
		// given
		Integer expected = 5050;

		// when
		for (int i = 0; i < Constants.NUMBER_THREADS; i++) {
			SendData sendData = new SendData(clientService, server, dataIntegrity);
			sendDataTasks.add(sendData);
		}
		List<Future<Response>> resultsOfTasks = service.invokeAll(sendDataTasks);
		service.shutdown();
		Integer actual = dataIntegrity.ACCUMULATOR.intValue();

		// then
		assertEquals(expected, actual);
	}

}