package com.sivagtr.dockerops.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ListContainersCmd;
import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.LogContainerResultCallback;
import com.sivagtr.dockerops.cli.CommandExecutor;

@Service
public class CommandService {
	@Autowired
	private CommandExecutor commandExecutor;
	@Autowired
	private QueueService queueService;

	AtomicReference<LogContainerCmd> logContainerCmd = new AtomicReference<>();
	LogContainerResultCallback executor = null;

	DockerClient client = DockerClientBuilder.getInstance().build();

	public void startLogging() {

		ListContainersCmd listContainersCmd = client.listContainersCmd().withShowAll(true).withStatusFilter(Arrays.asList("running"));
		List<Container> exec = listContainersCmd.exec();
		AtomicReference<String> id = new AtomicReference<>("");
		exec.forEach(container -> {
			if(container.getImage().contains("be")) {
				id.set(container.getId());
			}
		});
		String s = id.get();
		if(s != null) {

			CompletableFuture.runAsync(() -> {
				while(null == s) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch(InterruptedException e) {
						return;
					}
				}

				logContainerCmd.set(client.logContainerCmd(s).withFollowStream(true));
				logContainerCmd.get().withStdOut(true).withStdErr(true).withFollowStream(true);
				executor = logContainerCmd.get().exec(new LogContainerResultCallback() {
					@Override
					public void onNext(Frame item) {
						System.out.println(new String(item.getPayload()));
					}
				});
			});

		}
	}

	public void close() {
		try {
			executor.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
