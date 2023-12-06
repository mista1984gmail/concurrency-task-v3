package ru.clevertec.concurrency.task.client.service;

import ru.clevertec.concurrency.task.datatransfer.request.Request;
import ru.clevertec.concurrency.task.datatransfer.response.Response;

public interface ClientService {

	Integer getSizeTransmitList();

	boolean add(Integer element);

	Request remove(Integer index);

	void clearValues();

	void getAnswerFromServer(Response response);

	boolean getGeneralAnswerFromServer(Response response, Integer checkSum);

}
