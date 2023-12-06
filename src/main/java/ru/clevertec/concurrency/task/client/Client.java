package ru.clevertec.concurrency.task.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Client {

	private List<Integer> elements;

	public Client() {
		elements = new CopyOnWriteArrayList<>();
	}

	public Client(List<Integer> elements) {
		this.elements = new CopyOnWriteArrayList<>(elements);
	}

	public List<Integer> getElements() {
		return elements;
	}

	public void setElements(List<Integer> elements) {
		this.elements = new CopyOnWriteArrayList<>(elements);
	}

}
