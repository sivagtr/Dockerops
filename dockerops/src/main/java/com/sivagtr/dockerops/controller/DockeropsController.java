package com.sivagtr.dockerops.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sivagtr.dockerops.service.CommandService;
import com.sivagtr.dockerops.service.QueueService;

@RestController
@RequestMapping("/api/v1")
public class DockeropsController {

	@Autowired
	private QueueService queueService;

	@Autowired
	private CommandService commandService;

	@GetMapping("/version")
	public String getVersion(){
		return "Version : 0.01";
	}

	@GetMapping("/docker-images")
	public void getDockerImages(){
		commandService.startLogging();
	}
	@DeleteMapping("/docker-images")
	public void deleteDockerImages(){
		commandService.close();
	}

}
