package ru.clevertec.concurrency.task.server;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.concurrency.task.datatransfer.request.Request;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Server {

	private final Map<Integer, Integer> receivedValues;

	public Server() {
		this.receivedValues = new ConcurrentHashMap<>();
	}

	/**
	 * Получает от Клиента значение в виде объекта Request,
	 * содержащего индекс элемента и его значение.
	 *
	 * @param request - объект Request.
	 */
	public void getValue(Request request) {

		receivedValues.put(request.getIndex(), request.getValue());
		log.info("Server get value: {}", request.getValue());

	}

	/**
	 * Возвращает список переданных от Клиента на Сервер значений в виде
	 * List<Integer>.
	 *
	 * @return List<Integer> - список переданных от Клиента на Сервер значений.
	 */
	public List<Integer> getValues() {

		return receivedValues.values().stream().toList();

	}

}
