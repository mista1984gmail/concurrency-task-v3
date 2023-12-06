package ru.clevertec.concurrency.task.datatransfer.sendingdata;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.concurrency.task.client.service.ClientService;
import ru.clevertec.concurrency.task.datatransfer.DataIntegrity;
import ru.clevertec.concurrency.task.datatransfer.request.Request;
import ru.clevertec.concurrency.task.datatransfer.response.Response;
import ru.clevertec.concurrency.task.exception.ChecksumMatchException;
import ru.clevertec.concurrency.task.server.Server;
import ru.clevertec.concurrency.task.util.constant.Constants;

import java.util.Random;
import java.util.concurrent.Callable;

@Slf4j
public class SendData implements Callable<Response> {

	private final ClientService clientService;
	private final Server server;
	private final DataIntegrity integralityData;

	public SendData(ClientService clientService, Server server, DataIntegrity integralityData) {
		this.clientService = clientService;
		this.server = server;
		this.integralityData = integralityData;
	}

	/**
	 * Эмулирует работу Клиента и Сервера.
	 * От Клиента к Серверу передается список значений в случайном порядке.
	 * Сервер его получает и отправляет ответ Клиенту.
	 * После отправки всех данных Клиент проверяет целостность отправленных данных на основе ответа от Сервера.
	 * Если проверка не прошла успешно выбрасывается исключение ChecksumMatchException().
	 *
	 * @return объект Response с размером полученного списка значений от Клиента на Сервер.
	 */
	@Override
	public Response call() throws InterruptedException {

		while (!integralityData.getRandomIndexes().isEmpty()) {
			Thread.sleep(getRandomDelayInRange(100, 500));
			Request request = clientService.remove(integralityData.getRandomIndex());
			if (request == null) {
				break;
			}
			log.info("Request {} accepted {}", Thread.currentThread().getName(), request.getValue());
			Thread.sleep(getRandomDelayInRange(100, 1000));
			server.getValue(request);
			clientService.getAnswerFromServer(new Response(server.getValues().size()));
		}
		while (clientService.getSizeTransmitList() != server.getValues().size()) {
			Thread.sleep(100);
			if (clientService.getSizeTransmitList() == 0) {
				break;
			}
		}
		clientService.clearValues();
		accumulatedValues();
		Constants.LOCK.lock();
		try {
			if (integralityData.checkSendAnswerToClient.get()) {
				try{
				clientService.getGeneralAnswerFromServer(new Response(server.getValues().size()), integralityData.ACCUMULATOR.get());}
				catch (ChecksumMatchException e){
					log.error(e.getMessage());
				}
				integralityData.checkSendAnswerToClient.set(false);
			}
		} finally {
			Constants.LOCK.unlock();
		}
		return new Response(server.getValues().size());

	}

	/**
	 * Суммирует все значения со списка, переданного на Сервер от Клиента.
	 *
	 */
	private void accumulatedValues() {

		while (server.getValues().size() > integralityData.COUNT.get()){
			integralityData.ACCUMULATOR.accumulateAndGet(server.getValues().get(integralityData.COUNT.getAndIncrement()), Integer::sum);
		}

	}

	/**
	 * Возвращает случайное значение Integer в диапазоне от
	 * передаваемых параметров min и max (от min до max).
	 *
	 * @param min - от данного значения включительно генерируется случайное значение.
	 * @param max - до данного значения не включая его генерируется случайное значение.
	 * @return случайное значение Integer в диапазоне от
	 * передаваемых параметров min и max.
	 */
	private Integer getRandomDelayInRange(int min, int max){

		return new Random().nextInt((max - min) + 1);

	}

}
