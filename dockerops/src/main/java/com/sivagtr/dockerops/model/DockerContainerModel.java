package com.sivagtr.dockerops.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class DockerContainerModel {
	private String id;
	private String name;
	private String port;
	private String command;
	private String imageName;
}
