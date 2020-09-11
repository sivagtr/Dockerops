package com.sivagtr.dockerops.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sivagtr.dockerops.model.QueueModel;
import com.sivagtr.dockerops.service.QueueService;

@Component
public class CommandExecutor {
	@Autowired
	private QueueService queueService;

	public void execute(String name, String command){
		try {
			Process exec = Runtime.getRuntime().exec(command);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(exec.getInputStream()));
			String line;
			QueueModel<String> queueModel = new QueueModel<>();
			queueModel.setName(name);
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				queueModel.setData(line);
			}
			reader.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
