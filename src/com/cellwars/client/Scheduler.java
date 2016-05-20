package com.cellwars.client;

import javafx.concurrent.Task;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Tam�s on 2015-05-22.
 */
public class Scheduler {

    private ClientConnection clientConnection;
    private BlockingQueue<String []> requestQueue;
    private BlockingQueue<String> answerQueue;

    public Scheduler(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
        this.requestQueue = new LinkedBlockingQueue<>();
        this.answerQueue = new LinkedBlockingQueue<>();
    }

    public void start() {
        new Thread(task).start();
    }

    public void request(String ... request) {
        try {
            requestQueue.put(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String tryToGetAnswer() {
        try {
            return !answerQueue.isEmpty() ? answerQueue.take() : "";
        } catch (InterruptedException e) {
            e.printStackTrace();
            return "";
        }
    }

    protected Task task = new Task<Void>() {

        @Override
        protected Void call() {
            while(true) {
                try {
                    if (!requestQueue.isEmpty()) {
                        clientConnection.sendMessage(requestQueue.take());
                        answerQueue.put(clientConnection.getMessage());
                    }
                } catch (Exception e) {
                    System.exit(1);
                }
          }
        }
    };
}
