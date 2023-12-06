package ru.clevertec.concurrency.task.util.constant;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Constants {

	public final static Integer SIZE_TRANSMIT_LIST = 100;
	public final static Integer NUMBER_THREADS = 4;
	public final static Lock LOCK = new ReentrantLock();

}
