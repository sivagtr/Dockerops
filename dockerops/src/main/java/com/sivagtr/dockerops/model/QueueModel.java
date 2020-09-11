package com.sivagtr.dockerops.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class QueueModel <T> {
	private String name;
	private T data;
}
