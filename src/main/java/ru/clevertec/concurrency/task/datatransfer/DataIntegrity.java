package ru.clevertec.concurrency.task.datatransfer;

import lombok.extern.slf4j.Slf4j;
import ru.clevertec.concurrency.task.client.Client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DataIntegrity {

	private final Client client;
	private final ConcurrentLinkedQueue<Integer> randomIndexes;
	public AtomicInteger COUNT = new AtomicInteger(0);
	public AtomicInteger ACCUMULATOR = new AtomicInteger(0);
	public AtomicBoolean checkSendAnswerToClient = new AtomicBoolean(true);


	public DataIntegrity(Client client) {
		this.client = client;
		this.randomIndexes = new ConcurrentLinkedQueue<>();
		getRandomListOfIndexes();
	}

	/**
	 * Создает случайную последовательность индексов в виде очереди,
	 * на основе передаваемого на Сервер списка.
	 * Устанавливает данную очередь в виде значения в поле
	 * randomIndexes объекту класса DataIntegrity
	 * при его создании.
	 *
	 */
	public void getRandomListOfIndexes() {

		List<Integer> indexes = new ArrayList<>();
		for (int i = 0; i < client.getElements().size(); i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		randomIndexes.addAll(indexes);

	}

	/**
	 * Возвращает случайный индекс из очереди
	 * индексов значений, передаваемых на Сервер.
	 * После его отправки - удаляет его из очереди.
	 * Если индексов больше нет в очереди - возвращает null.
	 *
	 * @return случайный индекс, если индексов больше нет - null.
	 */
	public Integer getRandomIndex() {

		if (!randomIndexes.isEmpty()) {
			return randomIndexes.poll();
		}
		log.info("No more value");
		return null;

	}

	/**
	 * Возвращает очередь индексов, сформированную в случайном порядке
	 *
	 * @return очередь индексов, сформированную в случайном порядке.
	 */
	public ConcurrentLinkedQueue<Integer> getRandomIndexes() {

		return randomIndexes;

	}

}
