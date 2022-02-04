package ru.netology.javacore;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TodoServer {

    public TodoServer(int port, Todos todos) {
        while (true) {
            try (ServerSocket serverSocket = new ServerSocket(port);
                 Socket clientSocket = serverSocket.accept();
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                System.out.println("New connection accepted");


                final String json = in.readLine();

                Gson gson = new Gson();
                todos = new Gson().fromJson(json, Todos.class);
                if ("ADD".equals(todos.getType())) {
                    todos.addTask(todos.getTask());
                } else if ("REMOVE".equals(todos.getType())) {
                    todos.removeTask(todos.getTask());
                }
                System.out.println(todos.getAllTasks());
            } catch (IOException e) {
                System.out.println("Соединение разорвано");
            }
        }
    }

    public void start() throws IOException {
        int port = 8989;
        System.out.println("Starting server at " + port + "...");
    }
}
