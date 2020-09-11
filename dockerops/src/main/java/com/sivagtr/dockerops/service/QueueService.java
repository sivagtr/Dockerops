package com.sivagtr.dockerops.service;

import java.util.LinkedList;
import java.util.Queue;

import org.springframework.stereotype.Service;

import com.sivagtr.dockerops.model.QueueModel;

@Service
public class QueueService {
	private Queue<QueueModel> queue = new LinkedList<>();

	public void offer(QueueModel model){
		queue.offer(model);
	}

	public QueueModel poll(){
		return queue.poll();
	}

	public boolean isEmpty(){
		return queue.isEmpty();
	}
}
