package ru.clevertec.concurrency.task.client.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.concurrency.task.client.Client;
import ru.clevertec.concurrency.task.client.service.ClientService;
import ru.clevertec.concurrency.task.datatransfer.request.Request;
import ru.clevertec.concurrency.task.datatransfer.response.Response;
import ru.clevertec.concurrency.task.exception.ChecksumMatchException;

@Slf4j
public class ClientServiceImpl implements ClientService {

	private final Client client;

	public ClientServiceImpl(Client client) {
		this.client = client;
	}

	/**
	 * Возвращает размер передаваемого на Сервер списка с числами.
	 *
	 * @return размер передаваемого на Сервер списка с числами.
	 */
	public Integer getSizeTransmitList() {

		return client.getElements().size();

	}

	/**
	 * Добавляет значение в список для передачи на Сервер.
	 *
	 * @param element - значение, которое нужно добавить в список.
	 * @return true - если значение добавилось в список, иначе - false.
	 */
	public boolean add(Integer element) {

		return client.getElements().add(element);

	}

	/**
	 * Возвращает новый объект Request, созданный на основе переданного индекса,
	 * по которому в объект Request передается индекс и соответствующее значение
	 * по этому индексу.
	 *
	 *
	 * @param index - индекс соответствующего значения, которое нужно передать
	 * в новый объект Request  .
	 * @return new Request - если переданный индекс не null и данный индекс
	 * не превышает размер передаваемого списка, иначе - null.
	 */
	public Request remove(Integer index) {

		if (index != null && index < client.getElements().size()) {
			return new Request(index, client.getElements().get(index));
		} else {
			log.info("No more value");
			return null;
		}

	}

	/**
	 * Удаляет все значения с передаваемого на Сервер списка,
	 * если данный список не пустой.
	 *
	 */
	public void clearValues() {

		if (!client.getElements().isEmpty()) {
			client.getElements().clear();
		}

	}

	/**
	 * Получает ответ от Сервера в виде объекта Response.
	 * Логирует факт получения ответа.
	 * Выводит размер переданного на Сервер списка со значениями
	 * на момент формирования ответа.
	 *
	 * @param response - объект Response - ответ от Сервера
	 * с размером переданного на Сервер списка со значениями
	 * на момент формирования ответа.
	 */
	@Override
	public void getAnswerFromServer(Response response) {

		log.info("Get answer from server. Size received list: {}", response.getSizeReceivedList());

	}

	/**
	 * Проверяет ответ от Сервера, на основании размера переданного списка на Сервер
	 * и контрольной суммы. Если проверка прошла успешно выводится соответствующая
	 * информация об успешной операции, в противном случае - выбрасывается
	 * исключение ChecksumMatchException().
	 *
	 * @param response - объект Response - ответ от Сервера
	 * с размером переданного на Сервер списка со значениями.
	 * @param checkSum - сумма всех значений с переданного на Сервер списка.
	 *
	 * @throws ChecksumMatchException() - если проверка на корректность переданных данных
	 * от Клиента прошла не успешно.
	 */
	public boolean getGeneralAnswerFromServer(Response response, Integer checkSum) {

		int n = response.getSizeReceivedList();
		if (checkSum == ((1.0 + n) * (n / 2.0))) {
			log.info("The checksum matches the size of the transferred array. The data was transferred to the server correctly.");
			return true;
		} else {
			throw new ChecksumMatchException();
		}

	}

}
