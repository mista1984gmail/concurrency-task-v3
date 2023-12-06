package ru.clevertec.concurrency.task.datatransfer.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Request {

	private Integer index;
	private Integer value;

}
