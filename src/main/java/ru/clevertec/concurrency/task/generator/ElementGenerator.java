package ru.clevertec.concurrency.task.generator;

import java.util.LinkedList;
import java.util.List;

public class ElementGenerator {

	/**
	 * Генерурует последовательный список Integer от 1 до
	 * переданного в качестве параметра значения (включительно).
	 *
	 * @param sizeArray - размер генерируемого списка.
	 * @return List<Integer> - последовательный список Integer от 1 до
	 * переданного в качестве параметра значения
	 */
	public static List<Integer> generateSerialArrayOfInteger(Integer sizeArray){

		List<Integer>integers = new LinkedList<>();
		Integer count = 1;
		while (count <= sizeArray){
			integers.add(count);
			count++;
		}
		return integers;

	}

}
