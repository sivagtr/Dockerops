package com.sivagtr.dockerops.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@Setter
@Getter
public class DockerImageModel {
	private String name;
	private String tag;
	private String id;
}
