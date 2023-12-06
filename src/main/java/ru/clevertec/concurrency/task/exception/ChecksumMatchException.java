package ru.clevertec.concurrency.task.exception;

public class ChecksumMatchException extends ApplicationException {

	/**
	 * Контрольная сумма не совпадает с размером передаваемого массива.
	 * Данные переданы на сервер не корректно.
	 *
	 */
	public ChecksumMatchException() {
		super("Checksum does not match. Data was not transferred to the server correctly.");
	}

}
